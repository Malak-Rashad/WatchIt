package com.project.watchit.Moviee;

import java.util.ArrayList;

public abstract class Person {
    // Attributes
    protected String firstname;
    protected String lastname;
    protected String nationality;
    protected String gender;
    protected String dateOfBirth;
    ArrayList<String>movies=new ArrayList<String>();

    // Constructor
    public Person(String firstname, String lastname, String nationality, String gender, String dateOfBirth) {
        // Validations
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("First name can not be empty.");
        }
        if (lastname == null || lastname.isEmpty()) {
            throw new IllegalArgumentException("Last name can not be empty.");
        }

        this.firstname = firstname;
        this.lastname = lastname;
        this.nationality = nationality;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    public Person(String firstname, String lastname) {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        if (lastname == null || lastname.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }
        this.firstname = firstname;
        this.lastname = lastname;
    }


    public Person(String fullName) {
        String[] nameParts = fullName.split(" ");
        this.firstname = nameParts[0];
        this.lastname = nameParts[1];
    }
    // Getters and Setters
    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("First name can not be empty.");
        }
        this.firstname = firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname) {
        if (lastname == null || lastname.isEmpty()) {
            throw new IllegalArgumentException("Last name can not be empty.");
        }
        this.lastname = lastname;
    }
    public String getGender(){
        return gender;
    }

    public void setGender(String gender) {
        if (!gender.equalsIgnoreCase(("male")) && !gender.equalsIgnoreCase(("female")) ){
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'.");
        }
        this.gender = gender;
    }
    public String getNationality(){
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<String> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    public abstract void add (String firstname, String lastname, String dateOfBirth, String nationality, String gender, ArrayList<String>movie);

    public abstract String toString();
    // public abstract String getRoleInfo();
    //  public abstract String displayProfile();
    public String getName(){
        return firstname + " " + lastname;
    }

}
