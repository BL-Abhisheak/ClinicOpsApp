package com.clinicops;

import java.util.ArrayList;

public class AdminMenu {

    private static final ArrayList<Doctor> doctorList = new ArrayList<>();

    public void show() {
        boolean logout = false;

        while (!logout) {
            displayAdminOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");

            switch (choice) {
                case 1: registerDoctor();  break;
                case 2:
                    System.out.println("\n  [Bulk Data Entry] -- Logic will be implemented in UC5.\n");
                    break;
                case 3:
                    System.out.println("\n  [View Audit Logs] -- Logic will be implemented in UC12.\n");
                    break;
                case 4: displayDoctors(); break;
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
        System.out.println("                                ");
        System.out.println("                                ");
        System.out.println("  1. Doctor Entry             ");
        System.out.println("  2. Bulk Data Entry          ");
        System.out.println("  3. View Audit Logs          ");
        System.out.println("  4. View Doctors             ");
        System.out.println("  5. Logout                   ");
    }

    private void registerDoctor() {
        System.out.println("\n--- Register New Doctor ---");
        String name  = ScannerHelper.readString("  Name            : ");
        String spec  = ScannerHelper.readString("  Specialization  : ");
        int    exp   = ScannerHelper.readInt(   "  Experience (yrs): ");
        String shift = ScannerHelper.readString("  Shift (Morning/Evening/Both): ");

        Doctor doctor = new Doctor(name, spec, exp, shift);
        doctorList.add(doctor);

        System.out.println("\n  ✓ Doctor registered successfully! ID: " + doctor.getId() + "\n");
    }

    private void displayDoctors() {
        System.out.println("\n--- Registered Doctors ---");
        if (doctorList.isEmpty()) {
            System.out.println("  No doctors registered yet.\n");
            return;
        }
        System.out.println("+--------+----------------------+-----------------+---------+----------+");
        System.out.println("| ID     | Name                 | Specialization  |   Exp   | Shift    |");
        System.out.println("+--------+----------------------+-----------------+---------+----------+");
        for (Doctor d : doctorList) {
            System.out.println(d);
        }
        System.out.println("+--------+----------------------+-----------------+---------+----------+\n");
    }

    public static ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }
}