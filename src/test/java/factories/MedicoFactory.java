package factories;

import br.com.conexasaude.models.Medico;
import br.com.conexasaude.models.enums.Autorizacao;

public class MedicoFactory {

    public static Medico medicoValido(Medico medico) {

        medico.setEmail("medico1@email.com");
        medico.setSenha("pass");
        medico.setConfirmacaoSenha("pass");
        medico.setEspecialidade("Cardiologista");
        medico.setCpf("810.046.140-63");
        medico.setIdade("42");
        medico.setTelefone("(21) 3232-6565");

        medico.addAutorizacao(Autorizacao.MEDICO);

        return medico;
    }
}
