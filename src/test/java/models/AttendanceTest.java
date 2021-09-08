package models;

import br.com.conexasaude.models.Attendance;
import factories.AttendanceFactory;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.TestUtil;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AttendanceTest extends TestUtil {

    @Test
    public void attendanceTestPojo() {

        final Class<?> classUnderTest = Attendance.class;

        assertPojoMethodsFor(classUnderTest).testing(Method.GETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.SETTER).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.EQUALS).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.HASH_CODE).areWellImplemented();
        assertPojoMethodsFor(classUnderTest).testing(Method.TO_STRING).areWellImplemented();
    }

    @Test
    public void attendanceShouldBeValid() {

        Attendance validAttendance = AttendanceFactory.validAttendance(new Attendance());
        assertEquals(0, getErrorSize(validAttendance));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidPatientId() {

        Attendance attendance = AttendanceFactory.validAttendance(new Attendance());
        attendance.setPatientId(null);
        assertEquals(1, getErrorSize(attendance));
        assertEquals("Id do Paciente não pode ser nulo.", getErrorMessage(attendance));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidDoctorId() {

        Attendance attendance = AttendanceFactory.validAttendance(new Attendance());
        attendance.setDoctorId(null);
        assertEquals(1, getErrorSize(attendance));
        assertEquals("Id do Médico não pode ser nulo.", getErrorMessage(attendance));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidDoctorIdAndInvalidPatientId() {

        Attendance attendance = AttendanceFactory.validAttendance(new Attendance());
        attendance.setDoctorId(null);
        attendance.setPatientId(null);
        assertEquals(2, getErrorSize(attendance));
    }

    @Test
    public void attendanceShouldNotBeValidWithInvalidInstant() {

        Attendance attendance = AttendanceFactory.validAttendance(new Attendance());
        attendance.setInstant(null);
        assertEquals(1, getErrorSize(attendance));
        assertEquals("Data e hora do Atendimento não deve ser nulo.", getErrorMessage(attendance));
    }
}
