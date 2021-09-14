package models;

import br.com.conexasaude.models.Atendimento;
import factories.AtendimentoFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AtendimentoTest extends TestUtil {

    @Test
    public void attendanceTestPojo() {

        final Class<?> classUnderTest = Atendimento.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.TO_STRING).areWellImplemented();
    }

    @Test
    public void attendanceShouldBeValid() {

        Atendimento atendimentoValido = AtendimentoFactory.atendimentoValido(new Atendimento());
        assertEquals(0, getErrorSize(atendimentoValido));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidPatientId() {

        Atendimento atendimento = AtendimentoFactory.atendimentoValido(new Atendimento());
        atendimento.setIdPaciente(null);
        assertEquals(1, getErrorSize(atendimento));
        assertEquals("Id do Paciente não pode ser nulo.", getErrorMessage(atendimento));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidInstant() {

        Atendimento atendimento = AtendimentoFactory.atendimentoValido(new Atendimento());
        atendimento.setDataHora(null);
        assertEquals(1, getErrorSize(atendimento));
        assertEquals("Data e hora do Atendimento não deve ser nulo.", getErrorMessage(atendimento));
    }
}
