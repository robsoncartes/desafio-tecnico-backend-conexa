package factories;

import br.com.conexasaude.models.Attendance;

import java.util.Date;

public class AttendanceFactory {

    public static Attendance validAttendance(Attendance attendance) {

        attendance.setPatientId(1L);
        attendance.setDoctorId(1L);
        attendance.setInstant(new Date());

        return attendance;
    }
}
