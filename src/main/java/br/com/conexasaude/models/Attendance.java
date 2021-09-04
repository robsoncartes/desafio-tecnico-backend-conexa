package br.com.conexasaude.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "attendances")
@Getter
@Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    private Long patientId;

    public Attendance() {
    }

    public Attendance(Long id, Date instant, Long patientId) {
        this.id = id;
        this.instant = instant;
        this.patientId = patientId;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        return "Atendimento Id: " + getId() +
                "\tInstante: " + sdf.format(getInstant()) +
                "\tPaciente: " + getPatientId();
    }
}
