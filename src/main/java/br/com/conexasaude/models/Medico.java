package br.com.conexasaude.models;

import br.com.conexasaude.models.enums.Autorizacao;
import br.com.conexasaude.models.views.MedicoVisao;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@EqualsAndHashCode
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(MedicoVisao.MedicoCompleto.class)
    private Long id;

    @JsonView(MedicoVisao.MedicoLogin.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Email(message = "Não é um e-mail bem formado.")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 60, message = "O tamanho do campo Senha deve conter entre 4 e 60 caracteres.")
    private String senha;

    @Transient
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 60, message = "O tamanho do campo Confirmação Senha deve conter entre 4 e 60 caracteres.")
    private String confirmacaoSenha;

    @JsonView(MedicoVisao.MedicoCompleto.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 4, max = 50, message = "O tamanho do campo Especialidade deve conter entre 4 e 50 caracteres.")
    private String especialidade;

    @JsonView(MedicoVisao.MedicoCompleto.class)
    @CPF(message = "Número de CPF inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Column(unique = true)
    private String cpf;

    @JsonView(MedicoVisao.MedicoCompleto.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    @Size(min = 1, max = 3, message = "O tamanho do campo Idade deve conter entre 1 e 3 caracteres.")
    private String idade;

    @JsonView(MedicoVisao.MedicoCompleto.class)
    @NotEmpty(message = "Preenchimento obrigatório.")
    private String telefone;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "autorizacoes")
    @JsonView(MedicoVisao.MedicoLogin.class)
    private Set<Integer> autorizacoes = new HashSet<>();

    public Medico() {
        this.addAutorizacao(Autorizacao.MEDICO);
    }

    public Medico(Long id, String email, String senha, String confirmacaoSenha, String especialidade, String cpf, String idade, String telefone) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.confirmacaoSenha = confirmacaoSenha;
        this.especialidade = especialidade;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.addAutorizacao(Autorizacao.MEDICO);
    }

    public void addAutorizacao(Autorizacao autorizacao) {
        this.autorizacoes.add(autorizacao.getCodigo());
    }

    @JsonView(MedicoVisao.MedicoCompleto.class)
    public Set<Autorizacao> getAutorizacoes() {

        return this.autorizacoes.stream().map(Autorizacao::toEnum).collect(Collectors.toSet());
    }

    @Override
    public String toString() {

        return "Doctor: {"
                + "id: " + id
                + "\tEmail: " + email
                + "\tEspecialidade: " + especialidade
                + "\tCPF: " + cpf
                + "\tIdade: " + idade
                + "\tTelefone: " + telefone + "}";
    }
}
