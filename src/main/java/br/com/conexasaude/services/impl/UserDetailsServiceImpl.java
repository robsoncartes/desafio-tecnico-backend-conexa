package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.repositories.DoctorRepository;
import br.com.conexasaude.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Doctor doctor = doctorRepository.findByEmail(email);

        if (doctor == null)
            throw new UsernameNotFoundException(email);

        return new Login(doctor.getId(), doctor.getEmail(), doctor.getPassword(), doctor.getAuthorityNames());
    }
}
