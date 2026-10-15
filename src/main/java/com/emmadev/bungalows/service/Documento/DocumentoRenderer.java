package com.emmadev.bungalows.service.Documento;

import com.emmadev.bungalows.Enums.TipoDocumento;

import java.util.Map;

public interface DocumentoRenderer<T>{

    TipoDocumento tipo();
    Map<String, Object> mapear(T fuente);
}
