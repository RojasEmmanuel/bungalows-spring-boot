package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Ubicacion.UbicacionDetail;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionRequest;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionRequestPatch;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionResponse;
import com.emmadev.bungalows.entity.Ubicacion;
import com.emmadev.bungalows.repository.UbicacionRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service

public class UbicacionService {
    private UbicacionRespository respository;
    private LaboralQueryService laboralQuery;

    @Transactional(readOnly = true)
    public List<UbicacionResponse> getUbicaciones(){

        return respository.findAll().stream().map(
                ubicacion -> new UbicacionResponse(
                        ubicacion.getId(),
                        ubicacion.getNombre(),
                        ubicacion.getSlug(),
                        ubicacion.getDireccion(),
                        ubicacion.getImagenPath()
                )
        ).toList();
    }

    public UbicacionResponse saveUbicacion(UbicacionRequest ubicacionDTO){

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombre(ubicacionDTO.nombre());
        ubicacion.setDireccion(ubicacionDTO.direccion());
        ubicacion.setImagenPath(ubicacionDTO.imagenPath());

        return saveUpdate(ubicacion);
    }

    public UbicacionResponse updateUbicacion(UbicacionRequestPatch dto){
        Ubicacion ubicacion = respository.findById(dto.id()).orElseThrow(
                ()->new IllegalArgumentException("")
        );
        ubicacion.setNombre(dto.nombre());
        ubicacion.setDireccion(dto.direccion());
        ubicacion.setImagenPath(dto.imagenPath());

        return saveUpdate(ubicacion);
    }

    public List<UbicacionDetail> ubicacionesDetail(){
        return respository.findAll().stream().map(
                ubicacion -> new UbicacionDetail(
                        ubicacion.getId(),
                        ubicacion.getNombre(),
                        ubicacion.getSlug(),
                        ubicacion.getDireccion(),
                        ubicacion.getImagenPath(),
                        laboralQuery.getTotalColaboradoresByUbicacion(ubicacion),
                        laboralQuery.getTotalColaboradoresActivosByUbicacion(ubicacion)
                )
        ).toList();
    }

    // metodo para registrar y editar una ubicacion
    @Transactional
    private UbicacionResponse saveUpdate(Ubicacion ubicacion){

        respository.save(ubicacion);

        return new UbicacionResponse(
                ubicacion.getId(),
                ubicacion.getNombre(), ubicacion.getSlug(),
                ubicacion.getDireccion(), ubicacion.getImagenPath()
        );
    }

    public void eliminarUbicacion(Long id){

        respository.delete(getUbicacionById(id));
    }

    public UbicacionResponse getUbicacion(Long id){
        Ubicacion ubicacion = getUbicacionById(id);

        return new UbicacionResponse(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getSlug(),
                ubicacion.getDireccion(),
                ubicacion.getImagenPath()
        );
    }

    protected Ubicacion getUbicacionById(Long id){
        return respository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("No existe una ubicación con este ID")
        );
    }
}
