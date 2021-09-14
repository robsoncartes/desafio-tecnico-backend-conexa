package br.com.conexasaude.models;

import br.com.conexasaude.models.views.PacienteVisao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pacientes")
@Getter
@Setter
@EqualsAndHashCode
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(PacienteVisao.PacienteSimples.class)
    private Long id;

    @JsonView({PacienteVisao.PacienteUpdate.class, PacienteVisao.PacienteCompleto.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 2, max = 20, message = "O tamanho do campo Nome deve conter entre 2 e 20 caracteres.")
    private String nome;

    @JsonView({PacienteVisao.PacienteUpdate.class, PacienteVisao.PacienteCompleto.class})
    @CPF(message = "Número de CPF inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String cpf;

    @JsonView({PacienteVisao.PacienteUpdate.class, PacienteVisao.PacienteCompleto.class})
    @Size(min = 1, max = 3, message = "O tamanho do campo Idade deve conter entre 1 e 3 caracteres.")
    private String idade;

    @JsonView({PacienteVisao.PacienteUpdate.class, PacienteVisao.PacienteCompleto.class})
    @Email(message = "Não é um e-mail bem formado.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String email;

    @JsonView({PacienteVisao.PacienteUpdate.class, PacienteVisao.PacienteCompleto.class})
    @NotEmpty(message = "Preenchimento obrigatório.")
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "idPaciente")
    private List<Atendimento> atendimentos = new ArrayList<>();

    public Paciente() {
    }

    public Paciente(Long id, String nome, String cpf, String idade, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public String toString() {

        return "Id: " + id
                + "\tNome: " + nome
                + "\tCPF: " + cpf
                + "\tIdade: " + idade
                + "\tEmail: " + email
                + "\tTelefone: " + telefone;
    }
}
