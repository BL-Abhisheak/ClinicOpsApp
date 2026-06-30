package com.clinicops;

import java.util.ArrayList;

public class FrontDeskMenu {

    private static final ArrayList<Patient> patientList = new ArrayList<>();

    public void show() {
        boolean logout = false;
        while (!logout) {
            displayFrontDeskOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");
            switch (choice) {
                case 1: registerPatient(); break;
                case 2: bookAppointment(); break;
                case 3: viewPatients();    break;
                case 4:
                    System.out.println("\n  Logging out from Front Desk panel. Goodbye!\n");
                    logout = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid option. Please choose between 1 and 4.\n");
            }
        }
    }

    private void displayFrontDeskOptions() {
        System.out.println("  FRONT DESK MENU — TownClinic    ");
        System.out.println("                                     ");
        System.out.println("  1. Register Patient             ");
        System.out.println("  2. Book Appointment             ");
        System.out.println("  3. View Patients                ");
        System.out.println("  4. Logout                       ");
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
        System.out.println("\n  ✓ Patient registered! ID: " + patient.getId() + "\n");
    }

    private Patient findPatientByMobile(String mobile) {
        for (Patient p : patientList) {
            if (p.getMobileNumber().equals(mobile)) {
                return p;
            }
        }
        return null;
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

    private void bookAppointment() {
        System.out.println("\n  [Book Appointment] -- Will be implemented in UC9.\n");
    }

    public static ArrayList<Patient> getPatientList() { return patientList; }
}