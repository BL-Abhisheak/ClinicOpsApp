package com.clinicops;

public class Doctor {

    private static int idCounter = 1;

    private String id;
    private String name;
    private String specialization;
    private int    experience;
    private String shift;

    public Doctor(String name, String specialization, int experience, String shift) {
        this.id             = String.format("D%04d", idCounter++);
        this.name           = name;
        this.specialization = specialization;
        this.experience     = experience;
        this.shift          = shift;
    }

    public String getId()             { return id; }
    public String getName()           { return name; }
    public String getSpecialization() { return specialization; }
    public int    getExperience()     { return experience; }
    public String getShift()          { return shift; }

    @Override
    public String toString() {
        return String.format("| %-6s | %-20s | %-15s | %3d yrs | %-8s |",
                id, name, specialization, experience, shift);
    }
}