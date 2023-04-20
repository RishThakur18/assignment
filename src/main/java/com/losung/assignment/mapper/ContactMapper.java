package com.losung.assignment.mapper;

import com.losung.assignment.models.dto.ContactDto;
import com.losung.assignment.models.entity.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactMapper {

    public ContactDto EntityToDto(Contact contact){
        return ContactDto.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    public Contact DtoToEntity(ContactDto contactDto){
        return Contact.builder()
                .id(contactDto.getId())
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .email(contactDto.getEmail())
                .phone(contactDto.getPhone())
                .build();
    }
}
