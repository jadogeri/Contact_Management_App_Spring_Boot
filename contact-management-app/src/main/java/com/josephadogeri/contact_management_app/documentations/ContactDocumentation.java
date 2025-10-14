package com.josephadogeri.contact_management_app.documentations;

import com.josephadogeri.contact_management_app.dtos.request.*;
import com.josephadogeri.contact_management_app.dtos.response.*;
import com.josephadogeri.contact_management_app.entities.Contact;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Tag(name = "Contacts", description = "the Contact Api")
@SecurityRequirements
public interface ContactDocumentation {

    @Operation(
            summary = "Retrieve All Contacts",
            description = "Retrieves all contacts and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public List<Contact> getAllContacts();

    @Operation(
            summary = "Retrieve Single Contact",
            description = "Retrieves single contact and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public Contact getSingleContact(@Parameter(description = "The unique identifier of the contact",
            required = true,
            schema = @Schema(type = "integer", format = "int64", example = "123"))
            @PathVariable Integer id);

    @Operation(
            summary = "Update Single Contact",
            description = "Modifies single contact data to data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ContactUpdateRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "Contact Create Request Example",
                            summary = "Example for creating a new contact",
                            value = "{\"name\": \"john doe\", \"email\": \"johndoe@gmail.com\", \"phone\": \"504-1234-567\"}"
                    )
            )
    )
    public Contact updateContact(@PathVariable("id") Integer id,@RequestBody ContactUpdateRequestDTO contactUpdateRequestDTO);

    @Operation(
            summary = "Create A Contact",
            description = "Creates a contact entity and stores to data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ContactAddRequestDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "Create Contact Example",
                            summary = "Example for creating a contact",
                            value = "{\"name\": \"john doe\", \"email\": \"johndoe@gmail.com\", \"phone\": \"504-1234-567\"}"
                    )
            )
    )
    public Contact createContact(@RequestBody ContactAddRequestDTO contactAddRequestDTO);


    @Operation(
            summary = "Remove Single Contact",
            description = "Removes a contact entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })

    public ContactDeleteSingleResponseDTO deleteSingleContact(@Parameter(
            name="id",
            description = "The unique identifier of the contact",
            required = true,
            schema = @Schema(type = "integer", format = "int64", example = "123"))
                                                        @PathVariable Integer id);

    @Operation(
            summary = "Remove All Contacts",
            description = "Removes all contacts entity and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })

    public ContactDeleteAllResponseDTO deleteAllContacts();

}


