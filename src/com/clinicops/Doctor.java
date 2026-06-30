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
    public Specialization getSpecialization() { return specialization; }
    public int            getExperience()     { return experience; }
    public Shift          getShift()          { return shift; }

    public boolean isSlotAvailable(String slot) {
        return !bookedSlots.contains(slot);
    }

    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

    public boolean isShiftCompatible(String slot) {
        int hour = Integer.parseInt(slot.split(":")[0]);
        return switch (shift) {
            case MORNING -> hour >= 9  && hour < 13;
            case EVENING -> hour >= 16 && hour < 20;
            case BOTH    -> (hour >= 9 && hour < 13) || (hour >= 16 && hour < 20);
        };
    }

    @Override
    public String toString() {
        return String.format("| %-6s | %-20s | %-14s | %3d yrs | %-8s |",
                id, name, specialization.name(), experience, shift.name());
    }
}