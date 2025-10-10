package com.josephadogeri.contact_management_app.documentation;

import com.josephadogeri.contact_management_app.dto.request.UserForgotPasswordRequestDTO;
import com.josephadogeri.contact_management_app.dto.request.UserLoginRequestDTO;
import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.request.UserResetPasswordRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserForgotPasswordResponseDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.dto.response.UserResetPasswordResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Users", description = "the User Api")
@SecurityRequirements
public interface UserDocumentation {

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
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
                            value = "{\"username\": \"johndoe\", \"email\": \"john.doe@example.com\"}"
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
    public String login(@RequestBody UserLoginRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public UserResetPasswordResponseDTO resetPassword(@RequestBody UserResetPasswordRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public UserForgotPasswordResponseDTO forgotPassword(@RequestBody UserForgotPasswordRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public String deactivate(@RequestBody User user);
}