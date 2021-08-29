package br.com.conexasaude.security;

import br.com.conexasaude.models.enums.AuthorityName;
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

    private Collection<? extends GrantedAuthority> authorities;

    public Login(Long id, String email, String password, Set<AuthorityName> authorityNames) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorityNames.stream().map(authorityName ->
                new SimpleGrantedAuthority(authorityName.getDescription())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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

    public boolean hasRole(AuthorityName authorityName) {

        return this.getAuthorities().contains(new SimpleGrantedAuthority(authorityName.getDescription()));
    }
}
