package com.clinicops;

import java.util.Scanner;

public class ScannerHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static final String[] MORNING_SLOTS = {
            "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30"
    };

    public static final String[] EVENING_SLOTS = {
            "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30"
    };

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

    public static String readSlotChoice(String prompt) {
        System.out.println(prompt);
        System.out.println("  --- Morning Slots ---");
        for (int i = 0; i < MORNING_SLOTS.length; i++) {
            System.out.printf("  %2d. %s%n", i + 1, MORNING_SLOTS[i]);
        }
        System.out.println("  --- Evening Slots ---");
        for (int i = 0; i < EVENING_SLOTS.length; i++) {
            System.out.printf("  %2d. %s%n", i + MORNING_SLOTS.length + 1, EVENING_SLOTS[i]);
        }
        int total = MORNING_SLOTS.length + EVENING_SLOTS.length;
        while (true) {
            int choice = readInt("  Select slot (1-" + total + "): ");
            if (choice >= 1 && choice <= MORNING_SLOTS.length) {
                return MORNING_SLOTS[choice - 1];
            } else if (choice <= total) {
                return EVENING_SLOTS[choice - MORNING_SLOTS.length - 1];
            }
            System.out.println("  [ERROR] Invalid slot choice.");
        }
    }
}