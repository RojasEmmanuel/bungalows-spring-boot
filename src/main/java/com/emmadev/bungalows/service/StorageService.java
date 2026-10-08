package com.emmadev.bungalows.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.uploads.dir}")
    private String directorioGeneral;

    @Value("${app.uploads.url-base}")
    private String urlBase;

    // TIPOS DE DIRECTORIOS
    private static final Set<String> TIPOS_VALIDOS = Set.of(
            "ubicaciones",
            "colaboradores/fotos",
            "colaboradores/documentos",
            "documentos/nominas",
            "documentos/constancias-vacaciones",
            "documentos/recibos-vacaciones"
    );

    /**
     * Guarda un archivo en uploads/{tipo}/{uuid}{ext}
     * y devuelve la URL pública: {urlBase}/{tipo}/{uuid}{ext}
     *
     * @param file   archivo subido
     * @param tipo   subcarpeta lógica (ej: "ubicaciones", "colaboradores/fotos")
     * @param prefijo prefijo para el nombre (ej: "ubicacion", "colaborador")
     */
    public String guardar(MultipartFile file, String tipo, String prefijo) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException("Tipo de upload no permitido: " + tipo);
        }

        try {
            // uploads/ubicaciones, uploads/colaboradores/fotos, etc.
            Path carpeta = Paths.get(directorioGeneral).resolve(tipo);
            Files.createDirectories(carpeta);

            String extension = obtenerExtension(file.getOriginalFilename());
            String nombre = prefijo + "-" + UUID.randomUUID() + extension;

            Path destino = carpeta.resolve(nombre);
            file.transferTo(destino.toAbsolutePath());

            // {urlBase}/{tipo}/{nombre}
            return urlBase + "/" + tipo + "/" + nombre;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo en " + tipo, e);
        }
    }

    private String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            return ".bin";
        }
        return nombreOriginal.substring(nombreOriginal.lastIndexOf(".")).toLowerCase();
    }
}
