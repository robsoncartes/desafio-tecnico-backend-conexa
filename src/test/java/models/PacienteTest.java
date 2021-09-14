package models;

import br.com.conexasaude.models.Paciente;
import factories.PacienteFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class PacienteTest extends TestUtil {


    @Test
    public void patientTestPojo() {

        final Class<?> classUnderTest = Paciente.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void patientShouldBeValid() {

        Paciente validPatient = PacienteFactory.pacienteValido(new Paciente());
        assertEquals(0, getErrorSize(validPatient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidName() {

        Paciente paciente = PacienteFactory.pacienteValido(new Paciente());

        paciente.setNome(null);
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(paciente));

        paciente.setNome("1");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("O tamanho do campo Nome deve conter entre 2 e 20 caracteres.", getErrorMessage(paciente));

        paciente.setNome("123456789012345678901");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("O tamanho do campo Nome deve conter entre 2 e 20 caracteres.", getErrorMessage(paciente));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidCPF() {

        Paciente paciente = PacienteFactory.pacienteValido(new Paciente());

        paciente.setCpf(null);
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(paciente));

        paciente.setCpf("123.456.789-00");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Número de CPF inválido.", getErrorMessage(paciente));

        paciente.setCpf("810.046.140.63");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Número de CPF inválido.", getErrorMessage(paciente));

        paciente.setCpf("Uma palavra qualquer");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Número de CPF inválido.", getErrorMessage(paciente));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidAge() {

        Paciente paciente = PacienteFactory.pacienteValido(new Paciente());

        paciente.setIdade("");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("O tamanho do campo Idade deve conter entre 1 e 3 caracteres.", getErrorMessage(paciente));

        paciente.setIdade("1234");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("O tamanho do campo Idade deve conter entre 1 e 3 caracteres.", getErrorMessage(paciente));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidEmail() {

        Paciente patient = PacienteFactory.pacienteValido(new Paciente());

        patient.setEmail(null);
        assertEquals(1, getErrorSize(patient));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(patient));

        patient.setEmail("");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(patient));

        patient.setEmail("jajajaja@blabla.");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Não é um e-mail bem formado.", getErrorMessage(patient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidPhoneNumber() {

        Paciente paciente = PacienteFactory.pacienteValido(new Paciente());

        paciente.setTelefone("");
        assertEquals(1, getErrorSize(paciente));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(paciente));
    }
}
