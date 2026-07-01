package com.clinicops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FrontDeskMenu {

    private static final ArrayList<Patient>     patientList     = new ArrayList<>();
    private static final ArrayList<Appointment> appointmentList = new ArrayList<>();

    public void show() {
        boolean logout = false;
        while (!logout) {
            displayFrontDeskOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");
            switch (choice) {
                case 1: registerPatient();    break;
                case 2: bookAppointment();    break;
                case 3: viewPatients();       break;
                case 4: viewAppointments();   break;
                case 5:
                    System.out.println("\n  Logging out from Front Desk panel. Goodbye!\n");
                    logout = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid option. Please choose between 1 and 5.\n");
            }
        }
    }

    private void displayFrontDeskOptions() {
        System.out.println("  FRONT DESK MENU — TownClinic    ");
        System.out.println("                                         ");
        System.out.println("  1. Register Patient             ");
        System.out.println("  2. Book Appointment             ");
        System.out.println("  3. View Patients                ");
        System.out.println("  4. View Appointments            ");
        System.out.println("  5. Logout                       ");
    }

    private void registerPatient() {
        System.out.println("\n--- Patient Registration ---");
        String mobile = ScannerHelper.readMobileNumber("  Mobile Number   : ");

        Patient existing = findPatientByMobile(mobile);
        if (existing != null) {
            System.out.println("\n  Welcome back, " + existing.getName() + "! (ID: " + existing.getId() + ")");
            System.out.println("  Patient is already registered. No new entry created.\n");
            return;
        }

        String name   = ScannerHelper.readString("  Name            : ");
        String gender = ScannerHelper.readString("  Gender (M/F/O)  : ");
        int    age    = ScannerHelper.readInt(   "  Age             : ");

        Patient patient = new Patient(name, gender, age, mobile);
        patientList.add(patient);

        AuditLogger.log("Patient registered: " + patient.getName()
                + " [" + patient.getId() + "]", AuditLogger.Level.INFO);

        System.out.println("\n  ✓ Patient registered! ID: " + patient.getId() + "\n");
    }

    private Patient findPatientByMobile(String mobile) {
        for (Patient p : patientList) {
            if (p.getMobileNumber().equals(mobile)) return p;
        }
        return null;
    }

    private void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");

        String mobile = ScannerHelper.readMobileNumber("  Patient Mobile Number: ");
        Patient patient = findPatientByMobile(mobile);
        if (patient == null) {
            System.out.println("  [ERROR] Patient not registered. Please register first.\n");
            return;
        }
        System.out.println("  Patient found: " + patient.getName());

        Specialization requiredSpec = ScannerHelper.readEnumChoice(
                "\n  Select required Specialization:", Specialization.class);

        String slot = ScannerHelper.readSlotChoice("\n  Select preferred appointment slot:");

        ArrayList<Doctor> allDoctors = AdminMenu.getDoctorList();

        List<Doctor> matchingDoctors = allDoctors.stream()
                .filter(d -> d.getSpecialization() == requiredSpec)
                .filter(d -> d.isShiftCompatible(slot))
                .filter(d -> d.isSlotAvailable(slot))
                .collect(Collectors.toList());

        if (matchingDoctors.isEmpty()) {
            System.out.println("\n  [INFO] No " + requiredSpec.name()
                    + " doctor available (matching shift) at " + slot + ".\n");
            return;
        }

        Random rand = new Random();
        Doctor assignedDoc = matchingDoctors.get(rand.nextInt(matchingDoctors.size()));

        assignedDoc.bookSlot(slot);
        Appointment appointment = new Appointment(patient, assignedDoc, slot);
        appointmentList.add(appointment);

        AuditLogger.log("Appointment booked: " + appointment.getId()
                + " | Patient: " + patient.getName()
                + " | Doctor: " + assignedDoc.getName()
                + " | Specialization: " + assignedDoc.getSpecialization()
                + " | Slot: " + slot, AuditLogger.Level.INFO);

        System.out.println("\n  ✓ Appointment Booked Successfully!");
        System.out.println("  Appointment ID : " + appointment.getId());
        System.out.println("  Doctor Assigned: " + assignedDoc.getName() + " (" + assignedDoc.getId() + ")");
        System.out.println("  Specialization : " + assignedDoc.getSpecialization());
        System.out.println("  Shift          : " + assignedDoc.getShift());
        System.out.println("  Slot           : " + slot + "\n");
    }

    private void viewPatients() {
        System.out.println("\n--- Registered Patients ---");
        if (patientList.isEmpty()) {
            System.out.println("  No patients registered yet.\n");
            return;
        }
        System.out.println("+--------+----------------------+--------+-----+--------------+");
        System.out.println("| ID     | Name                 | Gender | Age | Mobile       |");
        System.out.println("+--------+----------------------+--------+-----+--------------+");
        for (Patient p : patientList) System.out.println(p);
        System.out.println("+--------+----------------------+--------+-----+--------------+\n");
    }

    private void viewAppointments() {
        System.out.println("\n--- Booked Appointments ---");
        if (appointmentList.isEmpty()) {
            System.out.println("  No appointments booked yet.\n");
            return;
        }
        System.out.println("+--------+----------------------+----------------------+---------+");
        System.out.println("| ID     | Patient              | Doctor               | Slot    |");
        System.out.println("+--------+----------------------+----------------------+---------+");
        for (Appointment a : appointmentList) System.out.println(a);
        System.out.println("+--------+----------------------+----------------------+---------+\n");
    }

    public static ArrayList<Patient> getPatientList() { return patientList; }
}