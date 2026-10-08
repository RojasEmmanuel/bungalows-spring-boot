package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Contacto.ContactoPatch;
import com.emmadev.bungalows.DTO.Contacto.ContactoRequest;
import com.emmadev.bungalows.DTO.Contacto.ContactoResponse;
import com.emmadev.bungalows.service.ContactoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/contacto")
public class ContactoController {

    private final ContactoService service;

    @PostMapping
    public ContactoResponse registrar(@RequestBody @Valid ContactoRequest request){
        return service.save(request);
    }

    @PatchMapping
    public void actualizar(@RequestBody @Valid ContactoPatch patch){
        service.update(patch);
    }

    @GetMapping("/{colaboradorId}")
    public List<ContactoResponse> listar(@PathVariable Long colaboradorId){
        return service.getContactos(colaboradorId);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
