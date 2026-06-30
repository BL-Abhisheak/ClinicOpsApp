package com.clinicops;

public class ClinicApp {

    private static final int ROLE_ADMIN      = 1;
    private static final int ROLE_FRONT_DESK = 2;
    private static final int EXIT            = 3;

    public static void main(String[] args) {
        System.out.println("   Welcome to TownClinic — ClinicOps  ");

        boolean exitSystem = false;

        while (!exitSystem) {
            displayMainMenu();
            int choice = ScannerHelper.readInt("Select your role: ");

            switch (choice) {
                case ROLE_ADMIN:
                    new AdminMenu().show();
                    break;
                case ROLE_FRONT_DESK:
                    new FrontDeskMenu().show();
                    break;
                case EXIT:
                    System.out.println("\n  Thank you for using ClinicOps. Stay healthy! Goodbye!\n");
                    exitSystem = true;
                    break;
                default:
                    System.out.println("\n  [ERROR] Invalid choice. Please enter 1, 2, or 3.\n");
            }
        }
    }

    private static void displayMainMenu() {

        System.out.println("        SELECT USER ROLE      ");
        System.out.println("                                ");
        System.out.println("  1. Clinic Admin             ");
        System.out.println("  2. Front Desk Executive     ");
        System.out.println("  3. Exit                     ");
    }
}