package br.com.conexasaude.services;

import br.com.conexasaude.models.Atendimento;

import java.util.List;

public interface AtendimentoService {

    Atendimento getById(Long id);

    List<Atendimento> getByIdPaciente(Long id);

    Atendimento getByIdAndIdPaciente(Long id, Long patientId);

    Atendimento save(Atendimento attendance);

    void delete(Long id);

    List<Atendimento> getAll();
}
