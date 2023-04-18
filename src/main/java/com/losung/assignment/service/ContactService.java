package com.losung.assignment.service;

import com.losung.assignment.exception.ContactException;
import com.losung.assignment.mapper.ContactMapper;
import com.losung.assignment.models.dto.ContactDto;
import com.losung.assignment.models.entity.Contact;
import com.losung.assignment.models.vo.GenericResponseVo;
import com.losung.assignment.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private FilterService filterService;

    public GenericResponseVo addContact(ContactDto contactDto) {

        GenericResponseVo genericResponseVo = null;
        try {
            if (contactDto != null) {
                Contact contact = contactMapper.DtoToEntity(contactDto);
                contactRepository.save(contact);
            }

            genericResponseVo = GenericResponseVo.builder()
                    .success(true)
                    .message("contact added successfully")
                    .data(contactDto)
                    .build();
            return genericResponseVo;
        }
        catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing add request");
        }
    }

    public GenericResponseVo viewAllContacts() {

        GenericResponseVo genericResponseVo = null;
        try {
            List<Contact> contactList = contactRepository.findAll();
            List<ContactDto> contactDtoList = contactList.stream().map(contactMapper::EntityToDto).toList();

            genericResponseVo = GenericResponseVo.builder()
                    .success(true)
                    .message("Total number of records : ".concat(String.valueOf(contactList.size())))
                    .data(contactDtoList)
                    .build();
        }
        catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing view all request");
        }
        return genericResponseVo;
    }

    public GenericResponseVo viewContactById(Long id) {
        GenericResponseVo genericResponseVo = null;
        try {
            Optional<Contact> contactOptional = contactRepository.findById(id);

            if (contactOptional.isPresent()) {
                Contact contact = contactOptional.get();
                ContactDto contactDto = contactMapper.EntityToDto(contact);

                genericResponseVo = GenericResponseVo.builder()
                        .success(true)
                        .message("Contact fetched successfully : ")
                        .data(contactDto)
                        .build();
            }
        } catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing view request");
        }

        return genericResponseVo;
    }

    public GenericResponseVo updateContact(Long id, ContactDto updatedContactDto, Boolean overwrite) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        GenericResponseVo genericResponseVo = null;
        try {
            Optional<Contact> contactOptional = contactRepository.findById(id);

            if (contactOptional.isPresent()) {
                Contact existingcontact = contactOptional.get();
                ContactDto existingcontactDto = contactMapper.EntityToDto(existingcontact);

                updatedContactDto = this.updateFields(existingcontactDto, updatedContactDto, overwrite);
                Contact updatedContact = contactMapper.DtoToEntity(updatedContactDto);
                updatedContact.setId(existingcontact.getId());

                contactRepository.save(updatedContact);

                genericResponseVo = GenericResponseVo.builder()
                        .success(true)
                        .message("Contact updated successfully")
                        .data(updatedContactDto)
                        .build();
            }
        }
        catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing update request");
        }

        return genericResponseVo;
    }

    private ContactDto updateFields(ContactDto existingUserDto, ContactDto updatedUserDto, boolean overwrite) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = ContactDto.class.getDeclaredFields();

        // overwrite flag : to overwrite whole object with existing one or to update partial fields present in update request
        if (!overwrite) {
            for (Field field : fields) {

                field.setAccessible(true);

                Method setterMethod = ContactDto.class.getMethod("get" + StringUtils.capitalize(field.getName()));
                Object updateValue = setterMethod.invoke(updatedUserDto);

                if (updateValue != null) {
                    field.set(existingUserDto, updateValue);
                }
            }
            return existingUserDto;
        }
        else {
            return updatedUserDto;
        }
    }

    public GenericResponseVo deleteContactById(Long id) {

        GenericResponseVo genericResponseVo = null;
        try {

            contactRepository.deleteById(id);

            genericResponseVo = GenericResponseVo
                    .builder()
                    .success(true)
                    .message("Contact deleted successfully").build();
        }
        catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing delete request");
        }
        return genericResponseVo;
    }

    public GenericResponseVo search(String key) {

        GenericResponseVo genericResponseVo = null;
        try {
            // columns to be searched into
            List<String> columns = List.of("first_name", "last_name", "email");

            List<Contact> contacts = filterService.search(key, columns, "contact");
            List<ContactDto> contactDtos = contacts.stream().map(contactMapper::EntityToDto).toList();

            genericResponseVo = GenericResponseVo.builder()
                    .success(true)
                    .message("Total number of records : ".concat(String.valueOf(contactDtos.size())))
                    .data(contactDtos)
                    .build();
        }
        catch (Exception e) {
            log.error("error : ", e);
            throw new ContactException("contact : error occurred while processing search request");
        }
        return genericResponseVo;
    }
}
