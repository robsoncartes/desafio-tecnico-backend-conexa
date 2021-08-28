package br.com.conexasaude.services;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.repositories.DoctorRepository;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor getById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null)
            throw new ObjectNotFoundException("Doutor não encontrado. Id: " + id + ", Tipo: " + Doctor.class.getName());

        return doctor;
    }

    @Override
    public Doctor getByEmail(String email) {

        Doctor doctor = doctorRepository.findByEmail(email);

        if (doctor == null)
            throw new ObjectNotFoundException("Doutor não encontrado. E-mail: " + email + ", Tipo: " + Doctor.class.getName());

        return doctor;
    }

    @Override
    public List<Doctor> getAll() {

        return doctorRepository.findAll();
    }

    @Override
    public Doctor save(Doctor doctor) {

        Doctor obj = doctorRepository.findByEmail(doctor.getEmail());

        if (obj == null) {
            doctor.setId(null);
            doctor.setEmail(doctor.getEmail());
            doctor.setPassword(doctor.getPassword());
            doctor.setPasswordConfirmation(doctor.getPasswordConfirmation());
            doctor.setExpertise(doctor.getExpertise());
            doctor.setCpf(doctor.getCpf());
            doctor.setAge(doctor.getAge());
            doctor.setPhoneNumber(doctor.getPhoneNumber());

            return doctorRepository.save(doctor);
        } else {
            throw new DataIntegrityException("Email já existe.");
        }
    }
}
