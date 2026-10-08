package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Responsable.ResponsablePatch;
import com.emmadev.bungalows.DTO.Responsable.ResponsableRequest;
import com.emmadev.bungalows.DTO.Responsable.ResponsableResponse;
import com.emmadev.bungalows.service.ResponsableService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/responsable")
public class ResponsableController {

    private final ResponsableService service;

    @GetMapping("/{colaboradorId}")
    public List<ResponsableResponse> getResponsables(@PathVariable Long colaboradorId){
        return  service.listar(colaboradorId);
    }

    @GetMapping("/responsable/{id}")
    public ResponsableResponse getResponsable(@PathVariable Long id){
        return service.getResponsable(id);
    }

    @PostMapping
    public ResponsableResponse registrarResponsable(@RequestBody @Valid ResponsableRequest request){
       return service.save(request);
    }

    @PatchMapping
    public ResponsableResponse actualizarResponsable(@RequestBody @Valid ResponsablePatch patch){
        return service.update(patch);
    }

    @DeleteMapping("/{id}")
    public void eliminarResponsable(@PathVariable Long id){
        service.eliminar(id);
    }
}
