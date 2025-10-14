package com.josephadogeri.contact_management_app.services;

import com.josephadogeri.contact_management_app.dtos.request.*;
import com.josephadogeri.contact_management_app.dtos.response.*;
import com.josephadogeri.contact_management_app.entities.Contact;
import com.josephadogeri.contact_management_app.entities.User;
import com.josephadogeri.contact_management_app.exceptions.AccountLockedException;
import com.josephadogeri.contact_management_app.exceptions.ResourceNotFoundException;
import com.josephadogeri.contact_management_app.handlers.CustomAuthenticationFailureHandler;
import com.josephadogeri.contact_management_app.handlers.CustomAuthenticationSuccessHandler;
import com.josephadogeri.contact_management_app.repositories.ContactRepository;
import com.josephadogeri.contact_management_app.repositories.UserRepository;
import com.josephadogeri.contact_management_app.utils.CredentialsValidatorUtil;
import com.josephadogeri.contact_management_app.utils.PasswordGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContactService {

    @Autowired
    private final ContactRepository contactRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private EmailService emailService;
    private Map<String, Object> context;
    private CredentialsValidatorUtil credentialsValidatorUtil;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public ContactService(ContactRepository contactRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {

        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.credentialsValidatorUtil = new CredentialsValidatorUtil();


    }

    public String getAuthenticatedUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            return username;
        }

        return "Anonymous";
    }

    public UserRegistrationResponseDTO register(UserRegistrationRequestDTO userRegistrationRequestDTO) throws MessagingException, IOException {
        String username = userRegistrationRequestDTO.getUsername();
        String password = userRegistrationRequestDTO.getPassword();
        String email = userRegistrationRequestDTO.getEmail();

        if(email == null || username == null || password == null){
            throw new IllegalArgumentException("Username, email and password are required");
        }
        //Validate credentials
        if(!credentialsValidatorUtil.isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email address");
        }
        if(!credentialsValidatorUtil.isValidUsername(username)){
            throw new IllegalArgumentException("Invalid username");
        }
        if(!credentialsValidatorUtil.isValidPassword(password)){
            throw new IllegalArgumentException("Invalid password");
        }

        User user = new User();
        user.setUsername(userRegistrationRequestDTO.getUsername());
        user.setEmail(userRegistrationRequestDTO.getEmail());
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationRequestDTO.getPassword());
        user.setPassword(encodedPassword);

        // send welcome email to user
        emailService.sendWelcomeEmail(user);

        //return user;
        User savedUser =  userRepository.save(user);
        UserRegistrationResponseDTO userRegistrationResponseDTO
                = new UserRegistrationResponseDTO(savedUser.getUsername(), savedUser.getEmail());
        userRegistrationResponseDTO.setCreatedAt(savedUser.getCreatedAt());
        userRegistrationResponseDTO.setLastModifiedDate(savedUser.getLastModifiedDate());

        return userRegistrationResponseDTO;

    }

    public UserLoginResponseDTO verify(UserLoginRequestDTO userLoginRequestDTO) throws MessagingException, IOException {
        User user = null;

        try{
            user = userRepository.findByUsername(userLoginRequestDTO.getUsername());
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()
                )
            );
            if(!user.isEnabled()){
                throw new AccountLockedException("Account is locked");
            }

            if (authenticate.isAuthenticated()) {
                customAuthenticationSuccessHandler.onAuthenticationSuccess(user.getUsername());
                String accessToken = jwtService.generateToken(user);
                return new UserLoginResponseDTO(accessToken);
            }else{
                throw new BadCredentialsException("Invalid username or password");
            }
        } catch (BadCredentialsException e) {
            // Handle invalid password specifically
            customAuthenticationFailureHandler.onAuthenticationFailure(user.getUsername());
            throw new BadCredentialsException("resting data Invalid username or password");
        } catch (AuthenticationException e) {
            // Handle other authentication errors
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public UserForgotPasswordResponseDTO forgotPassword(UserForgotPasswordRequestDTO userResetPasswordRequestDTO) throws MessagingException, IOException {
        String email = userResetPasswordRequestDTO.getEmail();

        if(email == null ){
            throw new IllegalArgumentException("email is required");
        }
        //Validate credentials
        if(!credentialsValidatorUtil.isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email address");
        }

        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("user not found by email");
        }
        //generate password and encode it , then save to
        //database and send user the new password to reset account
        String generatedPassword = PasswordGenerator.generateStrongPassword(10);
        System.out.println("genrated password ==" +  generatedPassword);
        //encode password
        String encodedPassword = bCryptPasswordEncoder.encode(generatedPassword);
        //update user password and save to database
        user.setPassword(encodedPassword);
        userRepository.save(user);
        // send new password to user

        emailService.sendForgotPasswordEmail(user, generatedPassword);
        
        //send http response
        UserForgotPasswordResponseDTO userResetPasswordResponseDTO = new UserForgotPasswordResponseDTO(generatedPassword);

        return userResetPasswordResponseDTO;
    }

    public UserResetPasswordResponseDTO resetPassword(UserResetPasswordRequestDTO userResetPasswordRequestDTO) throws MessagingException, IOException {
        User user = null;
        if(userResetPasswordRequestDTO.getNewPassword() == null || userResetPasswordRequestDTO.getOldPassword() == null
        || userResetPasswordRequestDTO.getConfirmNewPassword() == null || userResetPasswordRequestDTO.getUsername() == null){
            throw new IllegalArgumentException("all fields are required");
        }
        if(!userResetPasswordRequestDTO.getNewPassword().equals(userResetPasswordRequestDTO.getConfirmNewPassword())){
            throw new IllegalArgumentException("passwords do not match");
        }

        if(!credentialsValidatorUtil.isValidPassword(userResetPasswordRequestDTO.getNewPassword())){
            throw new IllegalArgumentException("not a valid password");
        }

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userResetPasswordRequestDTO.getUsername(), userResetPasswordRequestDTO.getOldPassword()
                    )
            );
            if (!authenticate.isAuthenticated()) {
                throw new IllegalArgumentException("invalid username or old password");
            }

        }catch (BadCredentialsException e){
            throw new BadCredentialsException("bad credentials");
        }
        user = userRepository.findByUsername(userResetPasswordRequestDTO.getUsername());
        String encodedPassword = bCryptPasswordEncoder.encode(userResetPasswordRequestDTO.getNewPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setFailedAttempts(0);
        userRepository.save(user);

        //send successful reset email to user
        emailService.sendResetPasswordEmail(user);

        return new UserResetPasswordResponseDTO("Successfully reset your password");
    }

    public UserDeactivateResponseDTO deactivate(UserDeactivateRequestDTO userDeactivateRequestDTO) throws ResourceNotFoundException, MessagingException, IOException {

        User user = null;
        if(!userDeactivateRequestDTO.isConfirm()  || userDeactivateRequestDTO.getPassword() == null ||
           userDeactivateRequestDTO.getUsername() == null){
            throw new IllegalArgumentException("all fields are required");
        }

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDeactivateRequestDTO.getUsername(), userDeactivateRequestDTO.getPassword()
                    )
            );
            if (!authenticate.isAuthenticated()) {
                throw new IllegalArgumentException("invalid username or old password");
            }

        }catch (BadCredentialsException e){

            throw new BadCredentialsException("bad credentials");
        }

        //retrieve user before deactivation and get email
        user = userRepository.findByUsername(userDeactivateRequestDTO.getUsername());
        //delete user from database
        userRepository.deleteById(user.getId());

        User deleteUserFound = userRepository.findByUsername(userDeactivateRequestDTO.getUsername());

        if(deleteUserFound != null){

            throw new ResourceNotFoundException("user not found !! did not deactivate the user");

        }else{
            //send email to user of account deactivation
            emailService.sendDeactivatedAccountEmail(user);

            //send http response using DTO
            return new UserDeactivateResponseDTO("Successfully deactivated your password");

        }

    }

    public List<Contact> getAllContacts() {
        User user = userRepository.findByUsername(getAuthenticatedUsername());
        ArrayList<Contact> contactList = (ArrayList<Contact>) contactRepository.getAllContactsByUserId(user.getId());

        return contactList;

    }

    public Contact createContact(ContactAddRequestDTO contactAddRequestDTO) {
        if(contactAddRequestDTO.getEmail() == null && contactAddRequestDTO.getName() == null &&
           contactAddRequestDTO.getPhone() == null) {

            throw new IllegalArgumentException("at least one field is required");
        }
        User user = userRepository.findByUsername(getAuthenticatedUsername());
        Contact contact = new Contact();
        contact.setName(contactAddRequestDTO.getName());
        contact.setPhone(contactAddRequestDTO.getPhone());
        contact.setEmail(contactAddRequestDTO.getEmail());

        contact.setUser(user);

        return contactRepository.save(contact);
    }

    public Contact getSingleContact(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("contact not found"));

        return contact;
    }

    public ContactDeleteSingleResponseDTO deleteSingleContact(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("contact not found"));
        contactRepository.deleteById(id);
        ContactDeleteSingleResponseDTO contactDeleteSingleResponseDTO
                = new ContactDeleteSingleResponseDTO("Successfully deleted the contact with id " + id);

        return contactDeleteSingleResponseDTO;

    }

    @Transactional
    public ContactDeleteAllResponseDTO deleteAllContacts() {

        User user = userRepository.findByUsername(getAuthenticatedUsername());
        if(user == null){
            throw new ResourceNotFoundException("user '" + user.getUsername()+"'with id " + user.getId()+ " not found");
        }

        user.getContacts().clear();
        userRepository.save(user);
        ContactDeleteAllResponseDTO contactDeleteAllResponseDTO
                = new ContactDeleteAllResponseDTO("Successfully deleted all the contacts");

        return contactDeleteAllResponseDTO;



    }

    public Contact updateContact(Integer id, ContactUpdateRequestDTO contactUpdateRequestDTO) {

        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("contact not found with id " + id));

        contact.setName(contactUpdateRequestDTO.getName());
        contact.setPhone(contactUpdateRequestDTO.getPhone());
        contact.setEmail(contactUpdateRequestDTO.getEmail());

        contactRepository.save(contact);

        //return updated contact
        return contact;
    }
}

