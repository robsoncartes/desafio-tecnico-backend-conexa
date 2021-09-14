package br.com.conexasaude.services;

import br.com.conexasaude.models.Paciente;

import java.util.List;

public interface PacienteService {

    Paciente getById(Long id);

    Paciente getByEmail(String email);

    Paciente save(Paciente paciente);

    Paciente update(Paciente paciente);

    void delete(Long id);

    void updateData(Paciente novoPaciente, Paciente paciente);

    List<Paciente> getAll();
}
