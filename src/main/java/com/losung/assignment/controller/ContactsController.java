package com.losung.assignment.controller;

import com.losung.assignment.models.dto.ContactDto;
import com.losung.assignment.models.vo.GenericResponseVo;
import com.losung.assignment.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("contact")
@Tag(name = "Contact", description = "Create Read Update Delete contacts")
@SecurityRequirement(name = "authBearer")
@Slf4j
public class ContactsController {

    @Autowired
    private ContactService contactService;

    @GetMapping()
    @Operation(summary = "View all contacts")
    public ResponseEntity<GenericResponseVo> getAllContacts() {
        log.info("incoming req : getAllContacts");

        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;

        try {
            genericResponseVo = contactService.viewAllContacts();
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e){
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "View contact by id")
    public ResponseEntity<GenericResponseVo> getContactById(@PathVariable Long id) {
        log.info("incoming req : getContactById");

        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;

        try {
            genericResponseVo = contactService.viewContactById(id);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e){
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }

    @PostMapping()
    @Operation(summary = "Add contact")
    public ResponseEntity<GenericResponseVo> addContact(@Valid @RequestBody ContactDto contactDto, BindingResult bindingResult) {
        log.info("incoming req : addContact");

        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;

        try {
            String errorMessage = null;
            if (bindingResult.hasErrors()) {
                errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
                genericResponseVo = GenericResponseVo.builder()
                        .success(false)
                        .message("failed to save contacts")
                        .errors(errorMessage)
                        .data(contactDto)
                        .build();
                httpStatus = HttpStatus.CONFLICT;
            }
            else {
                genericResponseVo = contactService.addContact(contactDto);
                httpStatus = HttpStatus.CREATED;
            }
        }
        catch (Exception e){
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update contact by id")
    public ResponseEntity<GenericResponseVo> updateContactById(@Valid @PathVariable Long id, @RequestBody ContactDto contactDto, @RequestParam Boolean overwrite, BindingResult bindingResult) {
        log.info("incoming req : updateContactById");
        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;

        try {
            String errorMessage = null;
            if (bindingResult.hasErrors()) {
                errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
                genericResponseVo = GenericResponseVo.builder()
                        .success(false)
                        .message("failed to save contacts")
                        .errors(errorMessage)
                        .data(contactDto)
                        .build();
                httpStatus = HttpStatus.CONFLICT;
            }
            else {
                genericResponseVo = contactService.updateContact(id, contactDto, overwrite);
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete contact by id")
    public ResponseEntity<GenericResponseVo> deleteContactsByIds(@PathVariable Long id) {
        log.info("incoming req : deleteContactsByIds");

        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;
        try {
            genericResponseVo = contactService.deleteContactById(id);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e) {
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }

    @GetMapping("/search")
    @Operation(summary = "Search contacts")
    public ResponseEntity<GenericResponseVo> deleteContactsByIds(@RequestParam String key) {
        log.info("incoming req : search contacts");

        GenericResponseVo genericResponseVo = null;
        HttpStatus httpStatus = null;
        try {
            genericResponseVo = contactService.search(key);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e) {
            genericResponseVo = GenericResponseVo.builder()
                    .success(false)
                    .message("unknown error occurred")
                    .errors(e.getMessage())
                    .build();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(genericResponseVo, httpStatus);
    }


}
