package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.enums.AuthorityName;
import br.com.conexasaude.repositories.DoctorRepository;
import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.DoctorService;
import br.com.conexasaude.services.exceptions.AuthorizationException;
import br.com.conexasaude.services.exceptions.DataIntegrityException;
import br.com.conexasaude.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Doctor getById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null)
            throw new ObjectNotFoundException("Doutor não encontrado. Id: " + id + ", Tipo: " + Doctor.class.getName());

        return doctor;
    }

    @Override
    public Doctor getByEmail(String email) {

        Login login = loginService.getAuthenticated();

        if (login == null || !login.hasRole(AuthorityName.DOCTOR) && !email.equals(login.getEmail()))
            throw new AuthorizationException("Acesso negado.");

        Doctor doctor = doctorRepository.findByEmail(email);

        if (doctor == null)
            throw new ObjectNotFoundException("E-mail: " + email + " não encontrado. Tipo: " + Doctor.class.getName());

        return doctor;
    }

    @Override
    public List<Doctor> getAll() {

        return doctorRepository.findAll();
    }

    @Override
    public Doctor save(Doctor doctor) {

        if (!doctor.getPassword().equals(doctor.getPasswordConfirmation()))
            throw new DataIntegrityException("Senha e Confirmação de senha não são iguais.");

        List<Doctor> doctors = doctorRepository.findByCpfOrEmail(doctor.getCpf(), doctor.getEmail());

        for (Doctor doc : doctors) {
            System.err.println(doc.getCpf() + " " + doc.getEmail());
        }

        if (doctors.isEmpty()) {
            doctor.setId(null);
            doctor.setEmail(doctor.getEmail());
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctor.setPasswordConfirmation(doctor.getPasswordConfirmation());
            doctor.setExpertise(doctor.getExpertise());
            doctor.setCpf(doctor.getCpf());
            doctor.setAge(doctor.getAge());
            doctor.setPhoneNumber(doctor.getPhoneNumber());

            return doctorRepository.save(doctor);
        }

        if (doctors.size() == 2) {
            throw new DataIntegrityException("CPF e E-mail já estão sendo utilizados.");
        }

        Doctor doc = doctors.get(0);

        if (doctor.getCpf().equals(doc.getCpf()) && doctor.getEmail().equals(doc.getEmail()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF e E-mail informados.");

        if (doctor.getCpf().equals(doc.getCpf()))
            throw new DataIntegrityException("Já existe um cadastro com o CPF informado.");
        else
            throw new DataIntegrityException("Já existe um cadastro com o E-mail informado.");
    }
}
