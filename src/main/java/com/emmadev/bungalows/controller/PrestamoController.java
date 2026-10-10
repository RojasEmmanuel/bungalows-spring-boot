package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Prestamo.*;
import com.emmadev.bungalows.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    @PostMapping
    public PrestamoResponse registrar(@RequestBody @Valid PrestamoRequest request){
        return service.savePrestamo(request);
    }

    //lista las deudas en info general
    @GetMapping
    public List<DeudaResponse> getDeudas(){
        return service.getDeudas();
    }

    // detalla cada uno de los prestaos de un colaborador.
    @GetMapping("/{colaboradorId}")
    public List<PrestamoResponse> getPrestamosByColaborador(@PathVariable Long colaboradorId){
        return service.getPrestamos(colaboradorId);
    }

    @PatchMapping("/colaborador/{colaboradorId}/{abono}")
    public void abonarByColaborador(@PathVariable Long colaboradorId, @PathVariable BigDecimal abono){
        service.abonarPrestamos(colaboradorId, abono);
    }

    @PatchMapping("/{id}/{abono}")
    public void abonarToPrestamo(@PathVariable Long id, @PathVariable BigDecimal abono){
        service.abonar(id, abono);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
         service.eliminarPrestamo(id);
    }
}
