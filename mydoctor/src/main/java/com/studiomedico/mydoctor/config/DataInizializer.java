package com.studiomedico.mydoctor.config;

import com.studiomedico.mydoctor.entity.Appointment;
import com.studiomedico.mydoctor.entity.Doctor;
import com.studiomedico.mydoctor.entity.Patient;
import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;
import com.studiomedico.mydoctor.repository.AppointmentRepository;
import com.studiomedico.mydoctor.repository.DoctorRepository;
import com.studiomedico.mydoctor.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInizializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void run(String... args) throws Exception {

        //controllo che il db non contenga gia dottori
        if(doctorRepository.count() == 0) {
            Doctor doctor1 = new Doctor();
            doctor1.setFirstName("Mario");
            doctor1.setLastName("Rossi");
            doctor1.setSpecialization("Cardiologis");
            doctor1.setEmail("mariorossi@example.it");

            Doctor doctor2 = new Doctor();
            doctor2.setFirstName("Anna");
            doctor2.setLastName("Bianchi");
            doctor2.setSpecialization("Dermatologia");
            doctor2.setEmail("annabianchi@example.it");

            doctorRepository.save(doctor1);
            doctorRepository.save(doctor2);

            System.out.println("Medici caricati con successo");
        }


        //controllo che non ci siano pazienti
        if(patientRepository.count() == 0) {
            Patient patient1 = new Patient();
            patient1.setFirstName("Luca");
            patient1.setLastName("Verdi");
            patient1.setFiscalCode("VRDLCU90A01H501Z");
            patient1.setEmail("lucaverdi@example.it");

            Patient patient2 = new Patient();
            patient2.setFirstName("Giulia");
            patient2.setLastName("Neri");
            patient2.setFiscalCode("RIGLL95B41F205");
            patient2.setEmail("giulianeri@example.it");

            patientRepository.save(patient1);
            patientRepository.save(patient2);

            System.out.println("Pazienti caricati con successo");
        }

        //controllo che non ci siano gli appuntamenti
        if(appointmentRepository.count() == 0) {
            List<Doctor> doctors = doctorRepository.findAll();
            List<Patient> patients = patientRepository.findAll();
            if (!doctors.isEmpty() && !patients.isEmpty()) {
                Appointment appointment1 = new Appointment();
                appointment1.setDoctor(doctors.get(0));
                appointment1.setPatient(patients.get(0));
                appointment1.setAppointmentDate(LocalDate.now().plusDays(3));
                appointment1.setAppointmentTime(LocalTime.of(10, 0));
                appointment1.setVisitType(VisitType.CARDIOLOGY);
                appointment1.setStatus(AppointmentStatus.CONFIRMED);

                Appointment appointment2 = new Appointment();
                appointment2.setDoctor(doctors.get(1));
                appointment2.setPatient(patients.get(1));
                appointment2.setAppointmentDate(LocalDate.now().plusDays(7));
                appointment2.setAppointmentTime(LocalTime.of(14, 30));
                appointment2.setVisitType(VisitType.DERMATOLOGY);
                appointment2.setStatus(AppointmentStatus.BOOKED);

                appointmentRepository.saveAll(List.of(appointment1, appointment2));
                System.out.println("Appuntamenti caricati con successo");
            }
        }

    }
}
