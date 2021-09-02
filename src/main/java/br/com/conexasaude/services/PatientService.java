package br.com.conexasaude.services;

import br.com.conexasaude.models.Patient;

import java.util.List;

public interface PatientService {

    Patient getById(Long id);

    Patient getByEmail(String email);

    Patient save(Patient patient);

    Patient update(Patient patient);

    void updateData(Patient newPatient, Patient patient);

    List<Patient> getAll();
}
