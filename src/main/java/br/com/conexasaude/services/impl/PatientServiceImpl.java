package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Patient;
import br.com.conexasaude.repositories.PatientRepository;
import br.com.conexasaude.services.PatientService;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HttpServletRequest servletRequest;

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

        List<Patient> patients = patientRepository.findByCpfOrEmail(patient.getCpf(), patient.getEmail());

        for (Patient pat : patients)
            System.err.println(pat.getCpf() + " " + pat.getEmail());

        if (patients.isEmpty()) {
            patient.setId(null);
            patient.setName(patient.getName());
            patient.setCpf(patient.getCpf());
            patient.setAge(patient.getAge());
            patient.setEmail(patient.getEmail());
            patient.setPhoneNumber(patient.getPhoneNumber());

            return patientRepository.save(patient);
        }

        if (patients.size() == 2) {
            throw new DataIntegrityException("CPF e  E-mail já estão sendo utilizados.");
        }

        Patient pat = patients.get(0);

        if (patient.getCpf().equals(pat.getCpf()) && patient.getEmail().equals(pat.getEmail()))
            throw new DataIntegrityException("Já existe um Paciente com o CPF e E-mail informados.");

        if (patient.getCpf().equals(pat.getCpf()))
            throw new DataIntegrityException("Já existe um Paciente com o CFP informado.");

        else
            throw new DataIntegrityException("Já existe um Paciente com o E-mail informado.");
    }

    @Override
    public Patient update(Patient patient) {

        /**
         * TODO
         * Not working as well. It's not updating as it should. Try other implementation and modeling alternatives.
         * Note: use DTO if the difficulty with JsonView persists.
         */

        Map<String, String> map = (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));
        System.err.println(uriId);

        // Patient newPatient = patientRepository.findByEmail(patient.getEmail());
        Patient newPatient = getByEmail(patient.getEmail());
        System.err.println(newPatient);


        if (newPatient != null && newPatient.getId().equals(uriId)) {
            updateData(newPatient, patient);
            return patientRepository.save(newPatient);
        }

        if (newPatient != null && newPatient.getCpf().equals(patient.getCpf())) {
            throw new DataIntegrityException("CPF já existe");
        } else {
            throw new DataIntegrityException("SEI LA Já existe um cadastro com o Email informado.");
        }
    }

    @Override
    public void updateData(Patient newPatient, Patient patient) {

        newPatient.setName(patient.getName());
        newPatient.setCpf(patient.getCpf());
        newPatient.setAge(patient.getAge());
        newPatient.setEmail(patient.getEmail());
        newPatient.setPhoneNumber(patient.getPhoneNumber());
    }

    @Override
    public List<Patient> getAll() {

        return patientRepository.findAll();
    }

}
