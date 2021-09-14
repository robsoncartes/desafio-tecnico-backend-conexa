package factories;

import br.com.conexasaude.models.Atendimento;

import java.util.Date;

public class AtendimentoFactory {

    public static Atendimento atendimentoValido(Atendimento atendimento) {

        atendimento.setIdPaciente(1L);
        atendimento.setIdMedico(1L);
        atendimento.setDataHora(new Date());

        return atendimento;
    }
}
