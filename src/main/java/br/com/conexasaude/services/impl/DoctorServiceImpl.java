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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private HttpServletRequest servletRequest;

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
            throw new ObjectNotFoundException("Doutor não encontrado. E-mail: " + email + ", Tipo: " + Doctor.class.getName());

        return doctor;
    }

    @Override
    public List<Doctor> getAll() {

        return doctorRepository.findAll();
    }

    @Override
    public Doctor save(Doctor doctor) {

        if (doctor.getPassword().equals(doctor.getPasswordConfirmation())){

            Doctor obj = doctorRepository.findByEmailOrCpf(doctor.getEmail(), doctor.getCpf());

            if (obj == null) {
                doctor.setId(null);
                doctor.setEmail(doctor.getEmail());
                doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
                doctor.setPasswordConfirmation(doctor.getPasswordConfirmation());
                doctor.setExpertise(doctor.getExpertise());
                doctor.setCpf(doctor.getCpf());
                doctor.setAge(doctor.getAge());
                doctor.setPhoneNumber(doctor.getPhoneNumber());
                return doctorRepository.save(doctor);
            }else {
                throw new DataIntegrityException("Email ou CPF já existe.");
            }
        }

        else {
            throw new DataIntegrityException("Password e PasswordConfirmation devem ser iguais.");
        }
    }
}
