package br.com.conexasaude.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sintomas")
@Getter
@Setter
@NoArgsConstructor
public class Sintoma {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private String detalhes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

}
