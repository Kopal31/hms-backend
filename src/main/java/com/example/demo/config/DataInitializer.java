package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private LabReportRepository labReportRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (appUserRepository.count() > 0) {
            System.out.println("Database already seeded. Skipping initialization.");
            return;
        }

        System.out.println("Seeding database with sample HMS records...");

        // 1. App Users
        AppUser admin = new AppUser();
        admin.setEmail("admin@hms.com");
        admin.setPassword(encoder.encode("root123"));
        admin.setRole("ROLE_ADMIN");
        appUserRepository.save(admin);

        AppUser staff = new AppUser();
        staff.setEmail("staff@hms.com");
        staff.setPassword(encoder.encode("root123"));
        staff.setRole("ROLE_STAFF");
        appUserRepository.save(staff);

        // 2. Patients
        Patient p1 = new Patient();
        p1.setName("John Doe");
        p1.setAge(35);
        p1.setGender("Male");
        p1.setContact("+1 555-0199");
        p1.setAddress("123 Main St, New York");
        p1 = patientRepository.save(p1);

        Patient p2 = new Patient();
        p2.setName("Jane Smith");
        p2.setAge(28);
        p2.setGender("Female");
        p2.setContact("+1 555-0144");
        p2.setAddress("456 Elm St, Boston");
        p2 = patientRepository.save(p2);

        Patient p3 = new Patient();
        p3.setName("Robert Johnson");
        p3.setAge(50);
        p3.setGender("Male");
        p3.setContact("+1 555-0177");
        p3.setAddress("789 Pine St, Chicago");
        p3 = patientRepository.save(p3);

        // 3. Doctors
        Doctor d1 = new Doctor();
        d1.setName("Dr. Alice Gregory");
        d1.setSpecialization("Cardiology");
        d1.setEmail("alice.gregory@hms.com");
        d1.setPhone("+1 555-0211");
        d1 = doctorRepository.save(d1);

        Doctor d2 = new Doctor();
        d2.setName("Dr. Bob Vance");
        d2.setSpecialization("General Medicine");
        d2.setEmail("bob.vance@hms.com");
        d2.setPhone("+1 555-0222");
        d2 = doctorRepository.save(d2);

        Doctor d3 = new Doctor();
        d3.setName("Dr. Charlie Brown");
        d3.setSpecialization("Pediatrics");
        d3.setEmail("charlie.brown@hms.com");
        d3.setPhone("+1 555-0233");
        d3 = doctorRepository.save(d3);

        // 4. Appointments
        Appointment app1 = new Appointment();
        app1.setPatient(p1);
        app1.setDoctor(d1);
        app1.setDate("2026-06-01");
        app1.setTime("10:00 AM");
        app1.setStatus("Scheduled");
        appointmentRepository.save(app1);

        Appointment app2 = new Appointment();
        app2.setPatient(p2);
        app2.setDoctor(d2);
        app2.setDate("2026-06-02");
        app2.setTime("11:30 AM");
        app2.setStatus("Completed");
        appointmentRepository.save(app2);

        Appointment app3 = new Appointment();
        app3.setPatient(p3);
        app3.setDoctor(d3);
        app3.setDate("2026-06-03");
        app3.setTime("02:00 PM");
        app3.setStatus("Cancelled");
        appointmentRepository.save(app3);

        // 5. Lab Tests
        LabTest t1 = new LabTest();
        t1.setTestName("Blood Panel");
        t1.setCost(120.00);
        t1 = labTestRepository.save(t1);

        LabTest t2 = new LabTest();
        t2.setTestName("Chest X-Ray");
        t2.setCost(250.00);
        t2 = labTestRepository.save(t2);

        LabTest t3 = new LabTest();
        t3.setTestName("Lipid Profile");
        t3.setCost(80.00);
        t3 = labTestRepository.save(t3);

        // 6. Lab Reports
        LabReport rep1 = new LabReport();
        rep1.setPatient(p2);
        rep1.setLabTest(t3);
        rep1.setResult("Normal cholesterol levels. Total: 180 mg/dL.");
        rep1.setDate("2026-05-24");
        labReportRepository.save(rep1);

        LabReport rep2 = new LabReport();
        rep2.setPatient(p3);
        rep2.setLabTest(t2);
        rep2.setResult("Clear lungs. No abnormalities detected.");
        rep2.setDate("2026-05-23");
        labReportRepository.save(rep2);

        // 7. Medicines
        Medicine m1 = new Medicine();
        m1.setName("Amoxicillin");
        m1.setPrice(15.50);
        m1.setQuantity(100);
        m1 = medicineRepository.save(m1);

        Medicine m2 = new Medicine();
        m2.setName("Lipitor");
        m2.setPrice(45.00);
        m2.setQuantity(150);
        m2 = medicineRepository.save(m2);

        Medicine m3 = new Medicine();
        m3.setName("Ibuprofen");
        m3.setPrice(5.99);
        m3.setQuantity(200);
        m3 = medicineRepository.save(m3);

        // 8. Prescriptions
        Prescription pres1 = new Prescription();
        pres1.setPatient(p2);
        pres1.setDoctor(d2);
        pres1.setDate("2026-05-24");
        List<Medicine> medList = new ArrayList<>();
        medList.add(m2);
        medList.add(m3);
        pres1.setMedicines(medList);
        prescriptionRepository.save(pres1);

        // 9. Billing
        Billing bill1 = new Billing();
        bill1.setPatient(p2);
        bill1.setTotalAmount(320.00);
        bill1.setPaymentStatus("Paid");
        bill1.setDate("2026-05-24");
        bill1 = billingRepository.save(bill1);

        Billing bill2 = new Billing();
        bill2.setPatient(p3);
        bill2.setTotalAmount(250.00);
        bill2.setPaymentStatus("Pending");
        bill2.setDate("2026-05-23");
        bill2 = billingRepository.save(bill2);

        // 10. Payments
        Payment pay1 = new Payment();
        pay1.setBilling(bill1);
        pay1.setAmount(320.00);
        pay1.setPaymentMode("Credit Card");
        paymentRepository.save(pay1);

        // 11. Schedules
        Schedule s1 = new Schedule();
        s1.setDoctor(d1);
        s1.setDate(LocalDate.parse("2026-06-01"));
        s1.setTimeSlot("09:00 AM - 01:00 PM");
        s1.setStatus("Available");
        scheduleRepository.save(s1);

        Schedule s2 = new Schedule();
        s2.setDoctor(d2);
        s2.setDate(LocalDate.parse("2026-06-02"));
        s2.setTimeSlot("09:00 AM - 05:00 PM");
        s2.setStatus("Available");
        scheduleRepository.save(s2);

        System.out.println("Database seeding completed successfully!");
    }
}
