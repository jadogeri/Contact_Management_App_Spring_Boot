package com.josephadogeri.contact_management_app.documentations;

import com.josephadogeri.contact_management_app.dtos.request.*;
import com.josephadogeri.contact_management_app.dtos.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Contacts", description = "the Contact Api")
@SecurityRequirements
public interface ContactDocumentation {

    @Operation(
            summary = "Register New User",
            description = "Creates new user entity and their data to data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserRegistrationRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "User Creation Example",
                            summary = "Example for creating a new user",
                            value = "{\"username\": \"johndoe\", \"email\": \"john.doe@example.com\", \"passowrd\": \"P@5sw0rd@1\"}"
                    )
            )
    )
    public UserRegistrationResponseDTO register(   @RequestBody()
                                                   UserRegistrationRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Login Existing User",
            description = "Logs in created user entity using their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserLoginRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "User Login Example",
                            summary = "Example for login a user",
                            value = "{\"username\": \"johndoe\", \"passowrd\": \"P@s5w@rd1\"}"
                    )
            )
    )
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Reset User Password",
            description = "Resets user password and unlocks account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResetPasswordRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "User Reset Password Example",
                            summary = "Example for resetting password of a user",
                            value = "{\"username\": \"johndoe\", \"oldPassword\": \"@0ldP@55word\", \"newPassowrd\": \"P@5sw0rd@1\", \"confirmNewPassowrd\": \"P@5sw0rd@1\"}"
                    )
            )
    )
    public UserResetPasswordResponseDTO resetPassword(@RequestBody UserResetPasswordRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Forgot User Password",
            description = "Generates temporary password for a user entity with a locked account or unknown password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserForgotPasswordRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "User Forgot Password Example",
                            summary = "Example for forgot password of a user",
                            value = "{\"email\": \"johndoe@gmail.com\"}"
                    )
            )
    )
    public UserForgotPasswordResponseDTO forgotPassword(@RequestBody UserForgotPasswordRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Deactivate User Account",
            description = "Removes created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDeactivateResponseDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "User Deactivation Example",
                            summary = "Example for deactivating a user",
                            value = "{\"username\": \"johndoe\", \"passowrd\": \"P@s5w@rd1\", \"confirm\": \"true\"}"
                    )
            )
    )
    public UserDeactivateResponseDTO deactivate(@RequestBody UserDeactivateRequestDTO user) throws MessagingException, IOException;
}