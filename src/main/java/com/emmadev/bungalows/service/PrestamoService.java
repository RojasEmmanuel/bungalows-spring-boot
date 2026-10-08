package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Prestamo.PrestamoPatch;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoRequest;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoResponse;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoSimpleResponse;
import com.emmadev.bungalows.Enums.EstatusPrestamo;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Prestamo;
import com.emmadev.bungalows.repository.PrestamoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PrestamoService {

    private final PrestamoRepository repository;
    private final ColaboradorQueryService colaboradorQuery;

    // registro de un nuevo prestamo
    public PrestamoResponse savePrestamo(PrestamoRequest request){

        Prestamo prestamo = new Prestamo();
        Colaborador colaborador = colaboradorQuery.getById(request.colaboradorId());

        prestamo.setColaborador(colaborador);
        prestamo.setMonto(request.monto());
        prestamo.setFechaPrestamo(request.fechaPrestamo());

        if(request.observaciones() != null){
            prestamo.setObservaciones(request.observaciones());
        }

        repository.save(prestamo);
        return getDto(prestamo);
    }

    // consulta todos los prestamos
    public List<PrestamoResponse> getPrestamos(){
        return repository.findAll().stream()
                .map(this::getDto)
                .toList();
    }

    public PrestamoSimpleResponse getPrestamo(Long id){
        Prestamo prestamo = getById(id);

        return new PrestamoSimpleResponse(
                prestamo.getId(),
                prestamo.getMonto(),
                prestamo.getFechaPrestamo(),
                prestamo.getEstatus().getNombre(),
                prestamo.getObservaciones()
        );
    }

    public PrestamoResponse actualizarPrestamo(PrestamoPatch patch){

        Prestamo prestamo = getById(patch.id());

        // en caso que se pague el prestamo
        if(patch.estatus() != null){

            prestamo.setEstatus(patch.estatus()); // en caso que sea cancelado, parcial o pagado

            if(patch.estatus().equals(EstatusPrestamo.PAGADO)){ // si es pagado, se actualiza la fecha
                prestamo.setFechaPago(LocalDate.now());
            }
        }

        if(patch.monto() != null){
            prestamo.setMonto(patch.monto());
        }

        if(patch.fechaPrestamo() != null){
            prestamo.setFechaPrestamo(patch.fechaPrestamo());
        }

        if(patch.observaciones() != null){
            prestamo.setObservaciones(patch.observaciones());
        }

        repository.save(prestamo);
        return getDto(prestamo);
    }

    public void eliminarPrestamo(Long id){
        repository.delete(getById(id));
    }

    private Prestamo getById(Long id){
        return repository.findById(id).orElseThrow(()->new IllegalArgumentException(
                "No existe un prestamo con este id"
        ));
    }

    private PrestamoResponse getDto(Prestamo prestamo){
        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getColaborador().getNombreCompleto(),
                prestamo.getColaborador().getLaboral().getPuesto().getNombre(),
                prestamo.getColaborador().getLaboral().getUbicacion().getNombre(),
                prestamo.getMonto(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaPago(),
                prestamo.getEstatus().getNombre(),
                prestamo.getObservaciones()
        );
    }
}
