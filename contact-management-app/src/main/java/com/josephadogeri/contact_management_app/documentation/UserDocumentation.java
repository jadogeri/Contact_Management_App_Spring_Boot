package com.josephadogeri.contact_management_app.documentation;

import com.josephadogeri.contact_management_app.dto.request.UserRegistrationRequestDTO;
import com.josephadogeri.contact_management_app.dto.response.UserRegistrationResponseDTO;
import com.josephadogeri.contact_management_app.entity.User;
import io.swagger.v3.oas.annotations.Operation;
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
    public UserRegistrationResponseDTO register(@RequestBody UserRegistrationRequestDTO user) throws MessagingException, IOException;

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public String login(@RequestBody User user);

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public String resetPassword(@RequestBody User user);

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public String forgotPassword(@RequestBody User user);

    @Operation(
            summary = "Fetch New User",
            description = "fetches created user entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public String deactivate(@RequestBody User user);
}