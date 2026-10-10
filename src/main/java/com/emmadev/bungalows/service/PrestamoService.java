package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Prestamo.*;
import com.emmadev.bungalows.Enums.EstatusPrestamo;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Prestamo;
import com.emmadev.bungalows.repository.PrestamoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrestamoService {

    private final PrestamoRepository repository;
    private final ColaboradorQueryService colaboradorQuery;

    // registro de un nuevo prestamo
    @Transactional
    public PrestamoResponse savePrestamo(PrestamoRequest request){

        Prestamo prestamo = new Prestamo();
        Colaborador colaborador = colaboradorQuery.getById(request.colaboradorId());

        prestamo.setColaborador(colaborador);
        prestamo.setMonto(request.monto());
        prestamo.setConcepto(request.concepto());
        prestamo.setFechaPrestamo(request.fechaPrestamo());

        if(request.observaciones() != null){
            prestamo.setObservaciones(request.observaciones());
        }

        repository.save(prestamo);
        return getDto(prestamo);
    }

    private void validarAbono(BigDecimal abono) {
        if (abono == null ||
                abono.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El abono debe ser mayor que cero"
            );
        }
    }


    // retorna la lista de prestamos pendientes de pago de un colaborador
    private List<Prestamo> getPrestamosPendientesByColaboradorId(Long colaboradorId){
        Colaborador colaborador = colaboradorQuery.getById(colaboradorId);

        List<Prestamo> prestamos =
                repository.findByColaboradorAndEstatusIn(
                        colaborador,
                        List.of(
                                EstatusPrestamo.PARCIAL,
                                EstatusPrestamo.PRESTADO
                        )
                );

        if (prestamos.isEmpty()) {
            throw new IllegalArgumentException(
                    "El colaborador no tiene préstamos pendientes de pago"
            );
        }

        return prestamos;
    }

    @Transactional
    public void abonarPrestamos(Long colaboradorId, BigDecimal abono) {

        validarAbono(abono);

        List<Prestamo> prestamos = getPrestamosPendientesByColaboradorId(colaboradorId);

        BigDecimal deudaTotal = prestamos.stream()
                .map(Prestamo::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (abono.compareTo(deudaTotal) > 0) {
            throw new IllegalArgumentException(
                    "El abono supera la deuda total del colaborador"
            );
        }

        Map<Long, BigDecimal> asignaciones =
                distribuirAbono(prestamos, abono);

        for (Map.Entry<Long, BigDecimal> asignacion
                : asignaciones.entrySet()) {

            if (asignacion.getValue().compareTo(BigDecimal.ZERO) > 0) {
                abonar(asignacion.getKey(), asignacion.getValue());
            }
        }
    }

    private Map<Long, BigDecimal> distribuirAbono(List<Prestamo> prestamos,  BigDecimal abono) {

        Map<Long, BigDecimal> asignaciones = new LinkedHashMap<>();
        Map<Long, BigDecimal> saldos = new LinkedHashMap<>();

        for (Prestamo prestamo : prestamos) {
            asignaciones.put(prestamo.getId(), BigDecimal.ZERO);
            saldos.put(prestamo.getId(), prestamo.getMonto());
        }

        List<Long> pendientes = new ArrayList<>(saldos.keySet());
        BigDecimal restante = abono;
        BigDecimal centavo = new BigDecimal("0.01");

        while (restante.compareTo(BigDecimal.ZERO) > 0
                && !pendientes.isEmpty()) {

            BigDecimal cuota = restante.divide(
                    BigDecimal.valueOf(pendientes.size()),
                    2,
                    RoundingMode.DOWN
            );

            // Cuando solo quedan centavos, se distribuyen individualmente.
            if (cuota.compareTo(BigDecimal.ZERO) == 0) {
                cuota = centavo;
            }

            for (Long id : new ArrayList<>(pendientes)) {
                if (restante.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }

                BigDecimal saldo = saldos.get(id);
                BigDecimal importe = saldo.min(cuota).min(restante);

                asignaciones.put(
                        id,
                        asignaciones.get(id).add(importe)
                );

                saldos.put(id, saldo.subtract(importe));
                restante = restante.subtract(importe);

                if (saldos.get(id).compareTo(BigDecimal.ZERO) == 0) {
                    pendientes.remove(id);
                }
            }
        }

        return asignaciones;
    }


    @Transactional
    public void abonar(Long id, BigDecimal abono) {

        validarAbono(abono);

        Prestamo prestamo = getById(id);

        if (prestamo.getEstatus() == EstatusPrestamo.PAGADO || prestamo.getEstatus() == EstatusPrestamo.CANCELADO) {
            throw new UnsupportedOperationException(
                    "Este préstamo no recibe más abonos"
            );
        }

        if (abono.compareTo(prestamo.getMonto()) > 0) {
            throw new IllegalArgumentException(
                    "El abono supera el saldo pendiente del préstamo"
            );
        }

        BigDecimal saldoRestante = prestamo.getMonto().subtract(abono);

        prestamo.setMontoPagado(
                prestamo.getMontoPagado().add(abono)
        );

        prestamo.setMonto(saldoRestante);

        if (saldoRestante.compareTo(BigDecimal.ZERO) == 0) {
            prestamo.setEstatus(EstatusPrestamo.PAGADO);
            prestamo.setFechaPago(LocalDate.now());
        } else {
            prestamo.setEstatus(EstatusPrestamo.PARCIAL);
        }

        repository.save(prestamo);
    }


    @Transactional(readOnly = true)
    public List<DeudaResponse> getDeudas() {

        List<Prestamo> prestamos = repository.findByEstatusIn(
                List.of(EstatusPrestamo.PARCIAL, EstatusPrestamo.PRESTADO)
        );

        // Agrupar por colaborador
        Map<Colaborador, List<Prestamo>> porColaborador = prestamos.stream()
                .collect(Collectors.groupingBy(Prestamo::getColaborador));

        return porColaborador.entrySet().stream()
                .map(entry -> {
                    Colaborador c = entry.getKey();
                    List<Prestamo> lista = entry.getValue();

                    BigDecimal prestado = lista.stream()
                            .map(p -> p.getMonto().add(p.getMontoPagado()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal pagado = lista.stream()
                            .map(Prestamo::getMontoPagado)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal pendiente = lista.stream()
                            .map(Prestamo::getMonto)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new DeudaResponse(
                            c.getId(),
                            c.getNombreCompleto(),
                            c.getFotografia(),          // ajusta según tu entidad
                            c.getLaboral().getPuesto().getNombre(),
                            c.getLaboral().getUbicacion().getNombre(),
                            lista.size(),
                            prestado,
                            pagado,
                            pendiente
                    );
                })
                .toList();
    }


    public void eliminarPrestamo(Long id){
        repository.delete(getById(id));
    }

    private Prestamo getById(Long id){
        return repository.findById(id).orElseThrow(()->new IllegalArgumentException(
                "No existe un prestamo con este id"
        ));
    }


    // consulta todos los prestamos de un colaborador y los expone al publico
    @Transactional(readOnly = true)
    public List<PrestamoResponse> getPrestamos(Long colaboradorId){

        return  getPrestamosPendientesByColaboradorId(colaboradorId)
                .stream().map(this::getDto).toList();
    }


    private PrestamoResponse getDto(Prestamo prestamo){
        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getColaborador().getNombreCompleto(),
                prestamo.getColaborador().getLaboral().getPuesto().getNombre(),
                prestamo.getColaborador().getLaboral().getUbicacion().getNombre(),
                prestamo.getMonto(),
                prestamo.getMontoPagado(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaPago(),
                prestamo.getEstatus().getNombre(),
                prestamo.getObservaciones()
        );
    }
}
