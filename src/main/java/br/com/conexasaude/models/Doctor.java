package br.com.conexasaude.models;

import br.com.conexasaude.models.enums.AuthorityName;
import br.com.conexasaude.models.views.DoctorView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 60, message = "O tamanho do campo password deve conter entre 4 e 60 caracteres.")
    private String password;

    @Transient
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 60, message = "O tamanho do campo passwordConfirmation deve conter entre 4 e 60 caracteres.")
    private String passwordConfirmation;

    @JsonView(DoctorView.DoctorComplete.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 50, message = "O tamanho do campo expertise deve conter entre 4 e 50 caracteres.")
    private String expertise;

    @JsonView(DoctorView.DoctorComplete.class)
    @CPF(message = "CPF deve ser único.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String cpf;

    @JsonView(DoctorView.DoctorComplete.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 1, max = 3, message = "O tamanho do campo age deve conter entre 1 e 3 caracteres.")
    private String age;

    @JsonView(DoctorView.DoctorComplete.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
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
