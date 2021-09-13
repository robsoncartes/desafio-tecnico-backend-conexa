package br.com.conexasaude.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    @NotNull(message = "Data e hora do Atendimento não deve ser nulo.")
    private Date instant;

    @NotNull(message = "Id do Paciente não pode ser nulo.")
    private Long patientId;

    private Long doctorId;
}
