package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/uploads")
public class StorageController {

    private final StorageService service;

    @PostMapping("/ubicaciones")
    public ResponseEntity<Map<String, String>> subirImagenUbicacion(@RequestParam("file") MultipartFile file) {
        String url = service.guardar(file, "ubicaciones", "ubicacion");
        return ResponseEntity.ok(Map.of("path", url));
    }

    @PostMapping("/colaboradores/fotos")
    public ResponseEntity<Map<String, String>>subirFotoColaborador(@RequestParam("file") MultipartFile file){
        String url = service.guardar(file, "colaboradores/fotos", "foto");
        return ResponseEntity.ok(Map.of("path", url));
    }

    @PostMapping("/colaboradores/documentos")
    public ResponseEntity<Map<String, String>>subirDocumentoColaborador(@RequestParam("file") MultipartFile file){
        String url = service.guardar(file, "colaboradores/documentos", "documento");
        return ResponseEntity.ok(Map.of("path", url));
    }
}
