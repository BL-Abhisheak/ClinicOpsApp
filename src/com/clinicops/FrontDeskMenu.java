package com.clinicops;

public class FrontDeskMenu {

    public void show() {
        boolean logout = false;

        while (!logout) {
            displayFrontDeskOptions();
            int choice = ScannerHelper.readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("\n  [Patient Registration] -- Logic will be implemented in UC7.\n");
                    break;
                case 2:
                    System.out.println("\n  [Book Appointment] -- Logic will be implemented in UC9.\n");
                    break;
                case 3:
                    System.out.println("\n  Logging out from Front Desk panel. Goodbye!\n");
                    logout = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid option. Please choose between 1 and 3.\n");
            }
        }
    }

    private void displayFrontDeskOptions() {
        System.out.println("        FRONT DESK MENU — TownClinic    ");
        System.out.println("                                ");
        System.out.println("  1. Register Patient             ");
        System.out.println("  2. Book Appointment             ");
        System.out.println("  3. Logout                       ");
        System.out.println("                                ");
    }
}