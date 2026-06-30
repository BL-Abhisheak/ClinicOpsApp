package com.clinicops;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private static int idCounter = 1;

    private String         id;
    private String         name;
    private Specialization specialization;
    private int            experience;
    private Shift          shift;

    private final List<String> bookedSlots = new ArrayList<>();

    public Doctor(String name, Specialization specialization, int experience, Shift shift) {
        this.id             = String.format("D%04d", idCounter++);
        this.name           = name;
        this.specialization = specialization;
        this.experience     = experience;
        this.shift          = shift;
    }

    public String         getId()             { return id; }
    public String         getName()           { return name; }
    // UC10: getter needed so Stream API can filter by specialization
    public Specialization getSpecialization() { return specialization; }
    public int            getExperience()     { return experience; }
    public Shift          getShift()          { return shift; }

    public boolean isSlotAvailable(String slot) {
        return !bookedSlots.contains(slot);
    }

    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

    @Override
    public String toString() {
        return String.format("| %-6s | %-20s | %-14s | %3d yrs | %-8s |",
                id, name, specialization.name(), experience, shift.name());
    }
}