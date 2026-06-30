package com.clinicops;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileHandler {

    public List<Doctor> readDoctorsFromCSV(String filePath, List<Doctor> existingDoctors) {
        List<Doctor> importedDoctors = new ArrayList<>();

        Set<String> existingFingerprints = new HashSet<>();
        for (Doctor d : existingDoctors) {
            existingFingerprints.add(buildFingerprint(d.getName(), d.getSpecialization().name(), d.getExperience()));
        }

        int lineNum = 0;
        int successCount = 0;
        int skippedCount = 0;
        int duplicateCount = 0;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] row;

            while ((row = reader.readNext()) != null) {
                lineNum++;

                if (row.length == 0 || (row.length == 1 && row[0].trim().isEmpty())) {
                    continue; // skip blank lines
                }

                if (row.length != 4) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Expected 4 fields, found " + row.length);
                    skippedCount++;
                    continue;
                }

                String name    = row[0].trim();
                String specStr = row[1].trim().toUpperCase();
                String expStr  = row[2].trim();
                String shiftStr= row[3].trim().toUpperCase();

                Specialization spec;
                try {
                    spec = Specialization.valueOf(specStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Unknown specialization '" + specStr + "' for doctor '" + name + "'");
                    skippedCount++;
                    continue;
                }

                Shift shift;
                try {
                    shift = Shift.valueOf(shiftStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Unknown shift '" + shiftStr + "' for doctor '" + name + "'");
                    skippedCount++;
                    continue;
                }

                int exp;
                try {
                    exp = Integer.parseInt(expStr);
                } catch (NumberFormatException e) {
                    System.out.println("  [SKIP] Line " + lineNum + ": Experience must be a number, got '" + expStr + "' for doctor '" + name + "'");
                    skippedCount++;
                    continue;
                }

                String fingerprint = buildFingerprint(name, spec.name(), exp);
                if (existingFingerprints.contains(fingerprint)) {
                    System.out.println("  [SKIP-DUP] Line " + lineNum + ": Duplicate doctor '" + name + "' (" + spec + ", " + exp + " yrs) already exists.");
                    duplicateCount++;
                    continue;
                }

                Doctor doctor = new Doctor(name, spec, exp, shift);
                importedDoctors.add(doctor);
                existingFingerprints.add(fingerprint); // prevent duplicates within same file too
                successCount++;
            }

        } catch (IOException e) {
            System.out.println("  [ERROR] Could not read file: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.out.println("  [ERROR] CSV format is invalid: " + e.getMessage());
        }

        System.out.println("\n  --- Import Summary ---");
        System.out.println("  Successfully imported : " + successCount);
        System.out.println("  Skipped (bad format)  : " + skippedCount);
        System.out.println("  Skipped (duplicates)  : " + duplicateCount);

        return importedDoctors;
    }

    private String buildFingerprint(String name, String specialization, int experience) {
        return name.trim().toLowerCase() + "|" + specialization.trim().toUpperCase() + "|" + experience;
    }
}