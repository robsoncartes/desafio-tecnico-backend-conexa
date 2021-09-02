package br.com.conexasaude.models;

import br.com.conexasaude.models.views.PatientView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PatientView.PatientComplete.class)
    private Long id;

    @JsonView({PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 2, max = 20, message = "O tamanho do campo name deve conter entre 2 e 20 caracteres.")
    private String name;

    @JsonView({PatientView.PatientComplete.class})
    @CPF(message = "CPF inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String cpf;

    @JsonView({PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 1, max = 3, message = "O tamanho do campo age deve conter entre 1 e 3 caracteres.")
    private String age;

    @JsonView({PatientView.PatientComplete.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Email(message = "Email inválido.")
    @Column(unique = true)
    private String email;

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
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", idade='" + age + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
