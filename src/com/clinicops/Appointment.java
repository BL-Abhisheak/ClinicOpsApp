package com.clinicops;

public class Appointment {

    private static int idCounter = 1;

    private String  id;
    private Patient patient;
    private Doctor  doctor;
    private String  slot;

    public Appointment(Patient patient, Doctor doctor, String slot) {
        this.id      = String.format("A%04d", idCounter++);
        this.patient = patient;
        this.doctor  = doctor;
        this.slot    = slot;
    }

    public String  getId()      { return id; }
    public Patient getPatient() { return patient; }
    public Doctor  getDoctor()  { return doctor; }
    public String  getSlot()    { return slot; }

    @Override
    public String toString() {
        return String.format("| %-6s | %-20s | %-20s | %-7s |",
                id, patient.getName(), doctor.getName(), slot);
    }
}