package com.emmadev.bungalows.validation;

import com.emmadev.bungalows.DTO.Contacto.ContactoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactoValidator implements ConstraintValidator<ContactoValido, ContactoRequest> {

    @Override
    public boolean isValid(
            ContactoRequest request,
            ConstraintValidatorContext context
    ) {

        if (request == null) {
            return true;
        }

        if (request.tipoContacto() == null) {
            return true;
        }

        String contacto = request.contacto();

        if (contacto == null || contacto.isBlank()) {
            return true;
        }

        return switch (request.tipoContacto()) {

            case TELEFONO -> contacto.matches("\\d{10}");

            case EMAIL -> contacto.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        };
    }
}