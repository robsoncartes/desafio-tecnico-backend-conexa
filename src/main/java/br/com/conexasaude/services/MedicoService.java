package br.com.conexasaude.services;

import br.com.conexasaude.models.Medico;

import java.util.List;

public interface MedicoService {

    Medico getById(Long id);

    Medico getByEmail(String email);

    Medico save(Medico medico);

    List<Medico> getAll();
}
