package factories;

import br.com.conexasaude.models.Patient;

public class PatientFactory {

    public static Patient validPatient(Patient patient) {

        patient.setName("Robson");
        patient.setCpf("996.641.960-88");
        patient.setPhoneNumber("(12) 2222-4444");
        patient.setAge("42");
        patient.setEmail("patient4@email.com");

        return patient;
    }
}
