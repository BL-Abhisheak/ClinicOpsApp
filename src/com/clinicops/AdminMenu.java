package com.clinicops;

public class AdminMenu {

    public void show() {
        boolean logout = false;

        while (!logout) {
            displayAdminOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("\n  [Doctor Entry] -- Logic will be implemented in UC2.\n");
                    break;
                case 2:
                    System.out.println("\n  [Bulk Data Entry] -- Logic will be implemented in UC5.\n");
                    break;
                case 3:
                    System.out.println("\n  [View Audit Logs] -- Logic will be implemented in UC12.\n");
                    break;
                case 4:
                    System.out.println("\n  Logging out from Admin panel. Goodbye, Admin!\n");
                    logout = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid option. Please choose between 1 and 4.\n");
            }
        }
    }

    private void displayAdminOptions() {
        System.out.println("        ADMIN MENU — TownClinic ");
        System.out.println("                                ");
        System.out.println("  1. Doctor Entry             ");
        System.out.println("  2. Bulk Data Entry          ");
        System.out.println("  3. View Audit Logs          ");
        System.out.println("  4. Logout                   ");
    }
}