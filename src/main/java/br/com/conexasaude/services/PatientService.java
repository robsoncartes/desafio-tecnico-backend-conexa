package br.com.conexasaude.services;

import br.com.conexasaude.models.Patient;

import java.util.List;

public interface PatientService {

    Patient getById(Long id);

    Patient getByEmail(String email);

    Patient save(Patient patient);

    List<Patient> getAll();
}
