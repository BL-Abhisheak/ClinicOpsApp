package com.clinicops;

public class Patient {

    private static int idCounter = 1;

    private String id;
    private String name;
    private String gender;
    private int    age;
    private String mobileNumber;

    public Patient(String name, String gender, int age, String mobileNumber) {
        this.id           = String.format("P%04d", idCounter++);
        this.name         = name;
        this.gender       = gender;
        this.age          = age;
        this.mobileNumber = mobileNumber;
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public String getGender()       { return gender; }
    public int    getAge()          { return age; }
    public String getMobileNumber() { return mobileNumber; }

    @Override
    public String toString() {
        return String.format("| %-6s | %-20s | %-6s | %3d | %-12s |",
                id, name, gender, age, mobileNumber);
    }
}