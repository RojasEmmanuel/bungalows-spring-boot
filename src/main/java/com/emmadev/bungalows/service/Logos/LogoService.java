package com.emmadev.bungalows.service.Logos;

import com.emmadev.bungalows.entity.Empresa;

public interface LogoService {

    /**
     * Devuelve un data URI Base64 listo para incrustar en HTML/PDF,
     * o cadena vacía si no hay logo o no se pudo obtener.
     */
    String obtenerLogoBase64(Empresa empresa);

    /**
     * Variante que acepta directamente un logoPath (relativo o URL absoluta).
     */
    String obtenerLogoBase64(String logoPath);
}