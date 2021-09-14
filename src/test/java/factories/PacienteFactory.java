package factories;

import br.com.conexasaude.models.Paciente;

public class PacienteFactory {

    public static Paciente pacienteValido(Paciente paciente) {

        paciente.setNome("Robson");
        paciente.setCpf("996.641.960-88");
        paciente.setTelefone("(12) 2222-4444");
        paciente.setIdade("42");
        paciente.setEmail("patient4@email.com");

        return paciente;
    }
}
