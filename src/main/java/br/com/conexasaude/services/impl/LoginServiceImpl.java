package br.com.conexasaude.services.impl;

import br.com.conexasaude.security.Login;
import br.com.conexasaude.services.LoginService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public Login getAuthenticated() {

        try {
            return (Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
