package models;

import br.com.conexasaude.models.Doctor;
import factories.DoctorFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;


public class DoctorTest extends TestUtil {

    @Test
    public void accountTestPojo() {

        final Class<?> classUnderTest = Doctor.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void doctorShouldBeValid() {

        Doctor validDoctor = DoctorFactory.validDoctor(new Doctor());
        assertEquals(0, getErrorSize(validDoctor));
    }

    @Test
    public void doctorShouldNotBeValidWithInvalidEmail() {

        Doctor doctor = DoctorFactory.validDoctor(new Doctor());

        doctor.setEmail(null);
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setEmail("");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setEmail("jajajaja@blabla.");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Não é um e-mail bem formado.", getErrorMessage(doctor));
    }

    @Test
    public void doctorShouldNotBeValidWithInvalidPassword() {

        Doctor doctor = DoctorFactory.validDoctor(new Doctor());

        doctor.setPassword(null);
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setPassword("123");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo password deve conter entre 4 e 60 caracteres.", getErrorMessage(doctor));

        doctor.setPassword("1234567890123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo password deve conter entre 4 e 60 caracteres.", getErrorMessage(doctor));
    }

    @Test
    public void doctorShouldNotBeValidWithInvalidPasswordConfirmation() {

        Doctor doctor = DoctorFactory.validDoctor(new Doctor());

        doctor.setPasswordConfirmation(null);
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setPasswordConfirmation("123");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo passwordConfirmation deve conter entre 4 e 60 caracteres.", getErrorMessage(doctor));

        doctor.setPasswordConfirmation("1234567890123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo passwordConfirmation deve conter entre 4 e 60 caracteres.", getErrorMessage(doctor));
    }

    @Test
    public void doctorShouldNotBevalidWithInvalidExpertise() {

        Doctor doctor = DoctorFactory.validDoctor(new Doctor());

        doctor.setExpertise(null);
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setExpertise("123");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo expertise deve conter entre 4 e 50 caracteres.", getErrorMessage(doctor));

        doctor.setExpertise("123456789012345678901234567890123456789012345678901");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("O tamanho do campo expertise deve conter entre 4 e 50 caracteres.", getErrorMessage(doctor));
    }

    @Test
    public void doctorShouldNotBevalidWithInvalidCPF() {

        Doctor doctor = DoctorFactory.validDoctor(new Doctor());

        doctor.setCpf(null);
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Preenchimento obrigatório.", getErrorMessage(doctor));

        doctor.setCpf("123.456.789-00");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Número de CPF inválido.", getErrorMessage(doctor));

        doctor.setCpf("810.046.140.63");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Número de CPF inválido.", getErrorMessage(doctor));

        doctor.setCpf("Uma palavra qualquer");
        assertEquals(1, getErrorSize(doctor));
        assertEquals("Número de CPF inválido.", getErrorMessage(doctor));
    }
}
