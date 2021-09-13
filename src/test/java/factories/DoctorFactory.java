package factories;

import br.com.conexasaude.models.Doctor;
import br.com.conexasaude.models.enums.AuthorityName;

public class DoctorFactory {

    public static Doctor validDoctor(Doctor doctor) {

        doctor.setEmail("medico1@email.com");
        doctor.setPassword("pass");
        doctor.setPasswordConfirmation("pass");
        doctor.setExpertise("Cardiologista");
        doctor.setCpf("810.046.140-63");
        doctor.setAge("42");
        doctor.setPhoneNumber("(21) 3232-6565");

        doctor.addAuthorityName(AuthorityName.DOCTOR);

        return doctor;
    }
}
