package br.com.conexasaude.security;

import br.com.conexasaude.models.enums.Autorizacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Login implements UserDetails {

    @Getter
    private Long id;

    @Getter
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> autorizacoes;

    public Login(Long id, String email, String password, Set<Autorizacao> autorizacoes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.autorizacoes = autorizacoes.stream().map(autorizacao ->
                new SimpleGrantedAuthority(autorizacao.getDescricao())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.autorizacoes;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Autorizacao autorizacao) {

        return this.getAuthorities().contains(new SimpleGrantedAuthority(autorizacao.getDescricao()));
    }
}
