package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Patient;
import br.com.conexasaude.repositories.PatientRepository;
import br.com.conexasaude.services.PatientService;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient getById(Long id) {

        Patient patient = patientRepository.findById(id).orElse(null);

        if (patient == null)
            throw new ObjectNotFoundException("Paciente não encontrado. Id: " + id + ", Tipo: " + Patient.class.getName());

        return patient;
    }

    @Override
    public Patient getByEmail(String email) {

        Patient patient = patientRepository.findByEmail(email);

        if (patient == null)
            throw new ObjectNotFoundException("Paciente não encontrado. Email: " + email + ", Tipo: " + Patient.class.getName());

        return patient;
    }

    @Override
    public Patient save(Patient patient) {

        Patient obj = patientRepository.findByCpfOrEmail(patient.getCpf(), patient.getEmail());

        if (obj == null) {
            patient.setId(null);
            patient.setName(patient.getName());
            patient.setCpf(patient.getCpf());
            patient.setAge(patient.getAge());
            patient.setEmail(patient.getEmail());

            return patientRepository.save(patient);
        }

        if (patient.getCpf().equals(obj.getCpf()) && patient.getEmail().equals(obj.getEmail()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF e Email informados.");

        if (patient.getCpf().equals(obj.getCpf()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF informado.");
        else
            throw new DataIntegrityException("Já existe um cadastro com o Email informado.");
    }

    @Override
    public List<Patient> getAll() {

        return patientRepository.findAll();
    }

}
