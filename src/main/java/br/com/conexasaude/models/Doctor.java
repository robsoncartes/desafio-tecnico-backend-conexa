package br.com.conexasaude.models;

import br.com.conexasaude.models.enums.AuthorityName;
import br.com.conexasaude.models.views.DoctorView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DoctorView.DoctorComplete.class)
    private Long id;

    @JsonView(DoctorView.DoctorLogin.class)
    private String email;

    private String password;

    @Transient
    private String passwordConfirmation;

    @JsonView(DoctorView.DoctorComplete.class)
    private String expertise;

    @JsonView(DoctorView.DoctorComplete.class)
    private String cpf;

    @JsonView(DoctorView.DoctorComplete.class)
    private String age;

    @JsonView(DoctorView.DoctorComplete.class)
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authority_names")
    @JsonView(DoctorView.DoctorLogin.class)
    private Set<Integer> authorities = new HashSet<>();

    public Doctor() {
        this.addAuthorityName(AuthorityName.DOCTOR);
    }

    public Doctor(Long id, String email, String password, String passwordConfirmation, String expertise, String cpf, String age, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.expertise = expertise;
        this.cpf = cpf;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.addAuthorityName(AuthorityName.DOCTOR);
    }

    public void addAuthorityName(AuthorityName authorityName) {
        this.authorities.add(authorityName.getCode());
    }

    @JsonView(DoctorView.DoctorComplete.class)
    public Set<AuthorityName> getAuthorityNames() {

        return this.authorities.stream().map(AuthorityName::toEnum).collect(Collectors.toSet());
    }
}
