package br.com.conexasaude.models;

import br.com.conexasaude.models.views.PatientView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PatientView.PatientSimple.class)
    private Long id;

    @JsonView({PatientView.PatientUpdate.class, PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 2, max = 20, message = "O tamanho do campo name deve conter entre 2 e 20 caracteres.")
    private String name;

    @JsonView({PatientView.PatientUpdate.class, PatientView.PatientComplete.class})
    @CPF(message = "CPF inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String cpf;

    @JsonView({PatientView.PatientUpdate.class, PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 1, max = 3, message = "O tamanho do campo age deve conter entre 1 e 3 caracteres.")
    private String age;

    @JsonView({PatientView.PatientUpdate.class, PatientView.PatientComplete.class})
    @Email(message = "Email inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String email;

    @JsonView({PatientView.PatientUpdate.class, PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório")
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "patientId")
    private List<Attendance> attendance = new ArrayList<>();

    public Patient() {
    }

    public Patient(Long id, String name, String cpf, String age, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        return "Id: " + getId()
                + "\tNome: " + getName()
                + "\tCPF: " + getCpf()
                + "\tIdade: " + getAge()
                + "\tEmail: " + getEmail()
                + "\tTelefone: " + getPhoneNumber();
    }
}
