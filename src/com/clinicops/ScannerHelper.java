package com.clinicops;

import java.util.Scanner;

public class ScannerHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Please enter a valid number.");
            }
        }
    }

    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("  [ERROR] Input cannot be empty. Please try again.");
        }
    }

    public static <T extends Enum<T>> T readEnumChoice(String prompt, Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        System.out.println(prompt);
        for (int i = 0; i < values.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, values[i].name());
        }
        while (true) {
            int choice = readInt("  Enter choice (1-" + values.length + "): ");
            if (choice >= 1 && choice <= values.length) {
                return values[choice - 1];
            }
            System.out.println("  [ERROR] Invalid choice. Try again.");
        }
    }

    public static String readMobileNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String mobile = scanner.nextLine().trim();
            if (mobile.matches("^[6-9]\\d{9}$")) {
                return mobile;
            }
            System.out.println("  [ERROR] Invalid Indian mobile number. Must be 10 digits starting with 6-9.");
        }


    }
}