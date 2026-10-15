package com.emmadev.bungalows.service.Documento;

import com.emmadev.bungalows.Enums.TipoDocumento;

import java.util.Map;

public interface DocumentoPdfService {

    byte[] generarPdf(TipoDocumento tipo, Object modelo);
    byte[] generarPdfDesdeHtml(String plantilla, Map<String, Object> modelo);
}
