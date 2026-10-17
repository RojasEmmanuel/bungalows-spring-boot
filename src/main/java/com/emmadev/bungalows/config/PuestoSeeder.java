package com.emmadev.bungalows.config;

import com.emmadev.bungalows.entity.Puesto; // Ajusta tu paquete
import com.emmadev.bungalows.repository.PuestoRepository; // Ajusta tu paquete
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@Order(1) // Opcional: si tienes varios seeders, esto asegura que este corra primero
public class PuestoSeeder {

    @Bean
    CommandLineRunner initPuestos(PuestoRepository puestoRepository) {
        return args -> {
            // Verificamos si la tabla está vacía para no duplicar datos en cada arranque
            if (puestoRepository.count() == 0) {

                List<Puesto> puestosIniciales = List.of(
                        crearPuesto("Gerente General", "Responsable de la dirección y estrategia del complejo."),
                        crearPuesto("Recepcionista", "Atención al cliente, check-in y check-out."),
                        crearPuesto("Camarista", "Limpieza y mantenimiento de las habitaciones."),
                        crearPuesto("Mantenimiento", "Reparaciones y cuidado de las instalaciones."),
                        crearPuesto("Chef", "Preparación de alimentos y gestión de cocina."),
                        crearPuesto("Mesero", "Atención en restaurante y servicio a mesas."),
                        crearPuesto("Guardia de Seguridad", "Vigilancia y control de accesos.")
                );

                puestoRepository.saveAll(puestosIniciales);
                System.out.println("✅ Seeder: Se han insertado " + puestosIniciales.size() + " puestos por defecto.");
            } else {
                System.out.println("ℹ️ Seeder: La tabla de puestos ya tiene datos. No se insertó nada.");
            }
        };
    }

    // Método auxiliar para crear el objeto más limpio
    private Puesto crearPuesto(String nombre, String descripcion) {
        Puesto puesto = new Puesto();
        puesto.setNombre(nombre);
        puesto.setDescripcion(descripcion);
        return puesto;
    }
}