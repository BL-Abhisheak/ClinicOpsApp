package com.clinicops;

public class AdminMenu {

    private static String doc1Name, doc1Spec, doc1Shift;
    private static int    doc1Exp;

    private static String doc2Name, doc2Spec, doc2Shift;
    private static int    doc2Exp;

    private static String doc3Name, doc3Spec, doc3Shift;
    private static int    doc3Exp;

    public void show() {
        boolean logout = false;

        while (!logout) {
            displayAdminOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerDoctors();
                    break;
                case 2:
                    System.out.println("\n  [Bulk Data Entry] -- Logic will be implemented in UC5.\n");
                    break;
                case 3:
                    System.out.println("\n  [View Audit Logs] -- Logic will be implemented in UC12.\n");
                    break;
                case 4:
                    displayDoctors();
                    break;
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
        System.out.println("      ADMIN MENU — TownClinic ");
        System.out.println("                                 ");
        System.out.println("  1. Doctor Entry             ");
        System.out.println("  2. Bulk Data Entry          ");
        System.out.println("  3. View Audit Logs          ");
        System.out.println("  4. View Doctors             ");
        System.out.println("  5. Logout                   ");
    }

    private void registerDoctors() {
        System.out.println("\n--- Registering Doctor 1 ---");
        doc1Name = ScannerHelper.readString("  Enter Name        : ");
        doc1Spec = ScannerHelper.readString("  Enter Specialization: ");
        doc1Exp  = ScannerHelper.readInt(   "  Enter Experience (yrs): ");
        doc1Shift= ScannerHelper.readString("  Enter Shift (Morning/Evening/Both): ");

        System.out.println("\n--- Registering Doctor 2 ---");
        doc2Name = ScannerHelper.readString("  Enter Name        : ");
        doc2Spec = ScannerHelper.readString("  Enter Specialization: ");
        doc2Exp  = ScannerHelper.readInt(   "  Enter Experience (yrs): ");
        doc2Shift= ScannerHelper.readString("  Enter Shift (Morning/Evening/Both): ");

        System.out.println("\n--- Registering Doctor 3 ---");
        doc3Name = ScannerHelper.readString("  Enter Name        : ");
        doc3Spec = ScannerHelper.readString("  Enter Specialization: ");
        doc3Exp  = ScannerHelper.readInt(   "  Enter Experience (yrs): ");
        doc3Shift= ScannerHelper.readString("  Enter Shift (Morning/Evening/Both): ");

        System.out.println("\n  ✓ 3 Doctors registered successfully!\n");
    }

    private void displayDoctors() {
        System.out.println("\n--- Registered Doctors ---");
        if (doc1Name == null) {
            System.out.println("  No doctors registered yet.");
            return;
        }
        System.out.printf("  Doctor 1 | %-20s | %-15s | %d yrs | %s%n",
                doc1Name, doc1Spec, doc1Exp, doc1Shift);
        System.out.printf("  Doctor 2 | %-20s | %-15s | %d yrs | %s%n",
                doc2Name, doc2Spec, doc2Exp, doc2Shift);
        System.out.printf("  Doctor 3 | %-20s | %-15s | %d yrs | %s%n",
                doc3Name, doc3Spec, doc3Exp, doc3Shift);
        System.out.println();
    }
}