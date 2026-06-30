package com.clinicops;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu {

    private static final ArrayList<Doctor> doctorList = new ArrayList<>();

    public void show() {
        boolean logout = false;
        while (!logout) {
            displayAdminOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");
            switch (choice) {
                case 1: registerDoctor();  break;
                case 2: bulkEntry();       break;
                case 3: viewAuditLogs();   break;
                case 4: displayDoctors();  break;
                case 5:
                    System.out.println("\n  Logging out from Admin panel. Goodbye, Admin!\n");
                    logout = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid option. Please choose between 1 and 5.\n");
            }
        }
    }

    private void displayAdminOptions() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      ADMIN MENU — TownClinic ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Doctor Entry             ║");
        System.out.println("║  2. Bulk Data Entry          ║");
        System.out.println("║  3. View Audit Logs          ║");
        System.out.println("║  4. View Doctors             ║");
        System.out.println("║  5. Logout                   ║");
        System.out.println("╚══════════════════════════════╝");
    }

    private void registerDoctor() {
        System.out.println("\n--- Register New Doctor ---");
        String         name  = ScannerHelper.readString("  Name            : ");
        Specialization spec  = ScannerHelper.readEnumChoice("  Select Specialization:", Specialization.class);
        int            exp   = ScannerHelper.readInt("  Experience (yrs): ");
        Shift          shift = ScannerHelper.readEnumChoice("  Select Shift:", Shift.class);

        Doctor doctor = new Doctor(name, spec, exp, shift);
        doctorList.add(doctor);
        System.out.println("\n  ✓ Doctor registered! ID: " + doctor.getId() + "\n");
    }

    private void bulkEntry() {
        System.out.println("\n--- Bulk Doctor Import from CSV (OpenCSV) ---");
        String filePath = ScannerHelper.readString("  Enter full file path (.csv): ");

        FileHandler fileHandler = new FileHandler();
        List<Doctor> imported = fileHandler.readDoctorsFromCSV(filePath, doctorList);

        if (imported.isEmpty()) {
            System.out.println("  No valid new records imported.\n");
            return;
        }

        doctorList.addAll(imported);
        System.out.println("  ✓ " + imported.size() + " doctor(s) added to the system.\n");
    }

    private void viewAuditLogs() {
        System.out.println("\n  [Audit Logs] -- Will be implemented in UC12.\n");
    }

    void displayDoctors() {
        System.out.println("\n--- Registered Doctors ---");
        if (doctorList.isEmpty()) {
            System.out.println("  No doctors registered yet.\n");
            return;
        }
        System.out.println("+--------+----------------------+----------------+---------+----------+");
        System.out.println("| ID     | Name                 | Specialization |   Exp   | Shift    |");
        System.out.println("+--------+----------------------+----------------+---------+----------+");
        for (Doctor d : doctorList) System.out.println(d);
        System.out.println("+--------+----------------------+----------------+---------+----------+\n");
    }

    public static ArrayList<Doctor> getDoctorList() { return doctorList; }
}