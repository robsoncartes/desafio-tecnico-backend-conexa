package br.com.conexasaude.services;

import br.com.conexasaude.models.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor getById(Long id);

    Doctor getByEmail(String email);

    Doctor save(Doctor doctor);

    List<Doctor> getAll();
}
