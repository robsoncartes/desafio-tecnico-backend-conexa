package br.com.conexasaude.models;

import br.com.conexasaude.models.views.PatientView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
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
    @JsonView(PatientView.PatientSimple.class)
    private Long id;

    @JsonView({PatientView.PatientComplete.class})
    private String name;

    @CPF
    @JsonView({PatientView.PatientComplete.class})
    private String cpf;

    @JsonView({PatientView.PatientComplete.class})
    private String age;

    @JsonView({PatientView.PatientComplete.class})
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
