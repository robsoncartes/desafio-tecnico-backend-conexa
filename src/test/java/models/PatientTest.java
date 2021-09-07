package models;

import br.com.conexasaude.models.Patient;
import factories.PatientFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class PatientTest extends TestUtil {


    @Test
    public void patientTestPojo() {

        final Class<?> classUnderTest = Patient.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void patientShouldBeValid() {

        Patient validPatient = PatientFactory.validPatient(new Patient());
        assertEquals(0, getErrorSize(validPatient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidName() {

        Patient patient = PatientFactory.validPatient(new Patient());

        patient.setName(null);
        assertEquals(1, getErrorSize(patient));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(patient));

        patient.setName("1");
        assertEquals(1, getErrorSize(patient));
        assertEquals("O tamanho do campo Nome deve conter entre 2 e 20 caracteres.", getErrorMessage(patient));

        patient.setName("123456789012345678901");
        assertEquals(1, getErrorSize(patient));
        assertEquals("O tamanho do campo Nome deve conter entre 2 e 20 caracteres.", getErrorMessage(patient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidCPF() {

        Patient patient = PatientFactory.validPatient(new Patient());

        patient.setCpf(null);
        assertEquals(1, getErrorSize(patient));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(patient));

        patient.setCpf("123.456.789-00");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Número de CPF inválido.", getErrorMessage(patient));

        patient.setCpf("810.046.140.63");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Número de CPF inválido.", getErrorMessage(patient));

        patient.setCpf("Uma palavra qualquer");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Número de CPF inválido.", getErrorMessage(patient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidAge() {

        Patient patient = PatientFactory.validPatient(new Patient());

        patient.setAge("");
        assertEquals(1, getErrorSize(patient));
        assertEquals("O tamanho do campo Idade deve conter entre 1 e 3 caracteres.", getErrorMessage(patient));

        patient.setAge("1234");
        assertEquals(1, getErrorSize(patient));
        assertEquals("O tamanho do campo Idade deve conter entre 1 e 3 caracteres.", getErrorMessage(patient));
    }

    @Test
    public void patientShouldNotBeValidWithInvalidEmail() {

        Patient patient = PatientFactory.validPatient(new Patient());

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

        Patient patient = PatientFactory.validPatient(new Patient());

        patient.setPhoneNumber("");
        assertEquals(1, getErrorSize(patient));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(patient));
    }
}
