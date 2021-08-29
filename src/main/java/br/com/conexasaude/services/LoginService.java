package br.com.conexasaude.services;

import br.com.conexasaude.security.Login;

public interface LoginService {

    Login getAuthenticated();
}
