package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Contacto.ContactoPatch;
import com.emmadev.bungalows.DTO.Contacto.ContactoRequest;
import com.emmadev.bungalows.DTO.Contacto.ContactoResponse;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Contacto;
import com.emmadev.bungalows.repository.ContactoRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactoService {

    private final ContactoRespository respository;
    private final ColaboradorQueryService colaboradorQuery;

    public ContactoResponse save(ContactoRequest request){

        Contacto contacto = new Contacto();
        contacto.setTipoContacto(request.tipoContacto());
        contacto.setContacto(request.contacto());
        contacto.setColaborador(colaboradorQuery.getById(request.colaboradorId()));

        respository.save(contacto);

        return getDto(contacto);
    }

    // lista los contactos de un colaborador.
    public List<ContactoResponse> getContactos(Long colaboradorId){

        Colaborador colaborador = colaboradorQuery.getById(colaboradorId);

        return respository.findByColaborador(colaborador)
                .stream()
                .map(this::getDto)
                .toList();
    }

    // las validaciones se hace mendiante el DTO y @ContactoValido
    public void update(ContactoPatch patch){

        Contacto contacto = getById(patch.id());
        contacto.setTipoContacto(patch.tipoContacto());
        contacto.setContacto(patch.contacto());

        respository.save(contacto);
    }

    public void eliminar(Long id){

        respository.delete(getById(id));
    }

    private ContactoResponse getDto(Contacto contacto){

        return new ContactoResponse(
                contacto.getId(),
                contacto.getContacto(),
                contacto.getTipoContacto().getNombre()

        );
    }

    private Contacto getById(Long id){

        return respository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("No existe un contacto con este id")
        );
    }
}
