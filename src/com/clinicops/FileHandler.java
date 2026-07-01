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
                    continue;
                }

                if (row.length != 4) {
                    String msg = "Line " + lineNum + ": Expected 4 fields, found " + row.length;
                    System.out.println("  [SKIP] " + msg);
                    AuditLogger.log("CSV import - " + msg, AuditLogger.Level.WARNING);
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
                    String msg = "Line " + lineNum + ": Unknown specialization '" + specStr + "' for doctor '" + name + "'";
                    System.out.println("  [SKIP] " + msg);
                    AuditLogger.log("CSV import - " + msg, AuditLogger.Level.WARNING);
                    skippedCount++;
                    continue;
                }

                Shift shift;
                try {
                    shift = Shift.valueOf(shiftStr);
                } catch (IllegalArgumentException e) {
                    String msg = "Line " + lineNum + ": Unknown shift '" + shiftStr + "' for doctor '" + name + "'";
                    System.out.println("  [SKIP] " + msg);
                    AuditLogger.log("CSV import - " + msg, AuditLogger.Level.WARNING);
                    skippedCount++;
                    continue;
                }

                int exp;
                try {
                    exp = Integer.parseInt(expStr);
                } catch (NumberFormatException e) {
                    String msg = "Line " + lineNum + ": Experience must be a number, got '" + expStr + "' for doctor '" + name + "'";
                    System.out.println("  [SKIP] " + msg);
                    AuditLogger.log("CSV import - " + msg, AuditLogger.Level.WARNING);
                    skippedCount++;
                    continue;
                }

                String fingerprint = buildFingerprint(name, spec.name(), exp);
                if (existingFingerprints.contains(fingerprint)) {
                    String msg = "Line " + lineNum + ": Duplicate doctor '" + name + "' (" + spec + ", " + exp + " yrs)";
                    System.out.println("  [SKIP-DUP] " + msg);
                    AuditLogger.log("CSV import - " + msg, AuditLogger.Level.WARNING);
                    duplicateCount++;
                    continue;
                }

                Doctor doctor = new Doctor(name, spec, exp, shift);
                importedDoctors.add(doctor);
                existingFingerprints.add(fingerprint);
                successCount++;
            }

        } catch (IOException e) {
            String msg = "Could not read file '" + filePath + "': " + e.getMessage();
            System.out.println("  [ERROR] " + msg);
            AuditLogger.log("CSV import - " + msg, AuditLogger.Level.ERROR);
        } catch (CsvValidationException e) {
            String msg = "Invalid CSV format in '" + filePath + "': " + e.getMessage();
            System.out.println("  [ERROR] " + msg);
            AuditLogger.log("CSV import - " + msg, AuditLogger.Level.ERROR);
        }

        System.out.println("\n  --- Import Summary ---");
        System.out.println("  Successfully imported : " + successCount);
        System.out.println("  Skipped (bad format)  : " + skippedCount);
        System.out.println("  Skipped (duplicates)  : " + duplicateCount);

        AuditLogger.log("CSV import completed - Success: " + successCount
                        + ", Skipped: " + skippedCount + ", Duplicates: " + duplicateCount,
                AuditLogger.Level.INFO);

        return importedDoctors;
    }

    private String buildFingerprint(String name, String specialization, int experience) {
        return name.trim().toLowerCase() + "|" + specialization.trim().toUpperCase() + "|" + experience;
    }
}