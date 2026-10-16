package com.emmadev.bungalows.service;

import com.emmadev.bungalows.dto.Ubicacion.UbicacionDetail;
import com.emmadev.bungalows.dto.Ubicacion.UbicacionRequest;
import com.emmadev.bungalows.dto.Ubicacion.UbicacionPatch;
import com.emmadev.bungalows.dto.Ubicacion.UbicacionResponse;
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
    private EmpresaQueryService empresaQuery;

    @Transactional(readOnly = true)
    public List<UbicacionResponse> getUbicaciones(){

        return respository.findAll().stream().map(
                ubicacion -> new UbicacionResponse(
                        ubicacion.getId(),
                        ubicacion.getNombre(),
                        ubicacion.getSlug(),
                        ubicacion.getDireccion(),
                        ubicacion.getImagenPath(),
                        ubicacion.getEmpresa().getId()
                )
        ).toList();
    }

    public UbicacionResponse saveUbicacion(UbicacionRequest ubicacionDTO){

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombre(ubicacionDTO.nombre());
        ubicacion.setDireccion(ubicacionDTO.direccion());
        ubicacion.setImagenPath(ubicacionDTO.imagenPath());

        ubicacion.setEmpresa(empresaQuery.getById(ubicacionDTO.empresaId()));
        return saveUpdate(ubicacion);
    }

    @Transactional
    public UbicacionResponse updateUbicacion(UbicacionPatch dto){
        Ubicacion ubicacion = getUbicacionById(dto.id());

        if(dto.empresaId() != null){
            ubicacion.setEmpresa(empresaQuery.getById(dto.empresaId()));
        }

        if(dto.nombre() != null){
            ubicacion.setNombre(dto.nombre());
        }

        if(dto.direccion() != null){
            ubicacion.setDireccion(dto.direccion());
        }

        if(dto.imagenPath() != null){
            ubicacion.setImagenPath(dto.imagenPath());
        }

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
                ubicacion.getDireccion(), ubicacion.getImagenPath(),
                ubicacion.getEmpresa().getId()
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
                ubicacion.getImagenPath(),
                ubicacion.getEmpresa().getId()
        );
    }

    protected Ubicacion getUbicacionById(Long id){
        return respository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("No existe una ubicación con este ID")
        );
    }
}
