package com.clinicops;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {


    public List<Doctor> readDoctorsFromCSV(String filePath) {
        List<Doctor> importedDoctors = new ArrayList<>();
        int lineNum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Expected 4 fields, found " + parts.length);
                    continue;
                }

                String name    = parts[0].trim();
                String specStr = parts[1].trim().toUpperCase();
                String expStr  = parts[2].trim();
                String shiftStr= parts[3].trim().toUpperCase();

                Specialization spec;
                try {
                    spec = Specialization.valueOf(specStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Unknown specialization '" + specStr + "'");
                    continue;
                }

                int exp;
                try {
                    exp = Integer.parseInt(expStr);
                } catch (NumberFormatException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Experience must be a number, got '" + expStr + "'");
                    continue;
                }

                Shift shift;
                try {
                    shift = Shift.valueOf(shiftStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Unknown shift '" + shiftStr + "'");
                    continue;
                }

                importedDoctors.add(new Doctor(name, spec, exp, shift));
            }
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not read file: " + e.getMessage());
        }

        return importedDoctors;
    }
}