package br.com.conexasaude.services;

import br.com.conexasaude.models.Doctor;

public interface DoctorService {

    Doctor getById(Long id);

    Doctor getByEmail(String email);

    Doctor save(Doctor doctor);
}
