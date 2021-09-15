package factories;


import br.com.conexasaude.models.Atendimento;

import java.time.LocalDateTime;

public class AtendimentoFactory {

    public static Atendimento atendimentoValido(Atendimento atendimento) {
        atendimento.setIdPaciente(1L);
        atendimento.setIdMedico(1L);
        atendimento.setDataHora(LocalDateTime.now());

        return atendimento;
    }
}