package br.com.conexasaude.services.impl;

import br.com.conexasaude.models.Medico;
import br.com.conexasaude.repositories.MedicoRepository;
import br.com.conexasaude.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MedicoRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Medico doctor = doctorRepository.findByEmail(email);

        if (doctor == null)
            throw new UsernameNotFoundException(email);

        return new Login(doctor.getId(), doctor.getEmail(), doctor.getSenha(), doctor.getAutorizacoes());
    }
}
