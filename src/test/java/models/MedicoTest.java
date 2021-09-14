package models;

import br.com.conexasaude.models.Medico;
import factories.MedicoFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;


public class MedicoTest extends TestUtil {

    @Test
    public void medicoTestPojo() {

        final Class<?> classUnderTest = Medico.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void medicoShouldBeValid() {

        Medico medicoValido = MedicoFactory.medicoValido(new Medico());
        assertEquals(0, getErrorSize(medicoValido));
    }

    @Test
    public void medicoShouldNotBeValidWithInvalidEmail() {

        Medico medico = MedicoFactory.medicoValido(new Medico());

        medico.setEmail(null);
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setEmail("");
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setEmail("jajajaja@blabla.");
        assertEquals(1, getErrorSize(medico));
        assertEquals("Não é um e-mail bem formado.", getErrorMessage(medico));
    }

    @Test
    public void medicoShouldNotBeValidWithInvalidPassword() {

        Medico medico = MedicoFactory.medicoValido(new Medico());

        medico.setSenha(null);
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setSenha("123");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Senha deve conter entre 4 e 60 caracteres.", getErrorMessage(medico));

        medico.setSenha("1234567890123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Senha deve conter entre 4 e 60 caracteres.", getErrorMessage(medico));
    }

    @Test
    public void doctorShouldNotBeValidWithInvalidPasswordConfirmation() {

        Medico medico = MedicoFactory.medicoValido(new Medico());

        medico.setConfirmacaoSenha(null);
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setConfirmacaoSenha("123");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Confirmação Senha deve conter entre 4 e 60 caracteres.", getErrorMessage(medico));

        medico.setConfirmacaoSenha("1234567890123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Confirmação Senha deve conter entre 4 e 60 caracteres.", getErrorMessage(medico));
    }

    @Test
    public void doctorShouldNotBevalidWithInvalidExpertise() {

        Medico medico = MedicoFactory.medicoValido(new Medico());

        medico.setEspecialidade(null);
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setEspecialidade("123");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Especialidade deve conter entre 4 e 50 caracteres.", getErrorMessage(medico));

        medico.setEspecialidade("123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(medico));
        assertEquals("O tamanho do campo Especialidade deve conter entre 4 e 50 caracteres.", getErrorMessage(medico));
    }

    @Test
    public void doctorShouldNotBevalidWithInvalidCPF() {

        Medico medico = MedicoFactory.medicoValido(new Medico());

        medico.setCpf(null);
        assertEquals(1, getErrorSize(medico));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(medico));

        medico.setCpf("123.456.789-00");
        assertEquals(1, getErrorSize(medico));
        assertEquals("Número de CPF inválido.", getErrorMessage(medico));

        medico.setCpf("810.046.140.63");
        assertEquals(1, getErrorSize(medico));
        assertEquals("Número de CPF inválido.", getErrorMessage(medico));

        medico.setCpf("Uma palavra qualquer");
        assertEquals(1, getErrorSize(medico));
        assertEquals("Número de CPF inválido.", getErrorMessage(medico));
    }
}
