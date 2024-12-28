package com.project.watchit.Moviee;

import java.io.*;
import java.util.*;

public class Director extends Person {
    public static ArrayList<Director> directors = new ArrayList<Director>();

    //Constructor

    public Director(String firstName, String lastName, String nationality, String gender, String dateOfBirth, ArrayList<String> movies) {
        super(firstName, lastName, nationality, gender, dateOfBirth);
        this.movies = movies;
    }
    public Director(String firstName, String lastName, String nationality, String gender, String dateOfBirth) {
        super(firstName, lastName, nationality, gender, dateOfBirth);
    }
    public Director(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void add(String firstname, String lastname, String nationality, String gender, String dateOfBirth, ArrayList<String> movies) {
        Director director=new Director( firstname,lastname,nationality,gender,dateOfBirth);
        setFirstname(firstname);
        setLastname(lastname);
        setNationality(nationality);
        setGender(gender);
        setDateOfBirth(dateOfBirth);
        setMovies(movies);
        directors.add(director);
    }

    //Edit Method
    public void edit(String field, String newValue) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Field can not be empty.");
        }
        if (newValue == null || newValue.isEmpty()) {
            throw new IllegalArgumentException("New value can not be empty.");
        }
        switch (field.toLowerCase()) {
            case "firstname":
                setFirstname(newValue);
                break;
            case "lastname":
                setLastname(newValue);
                break;
            case "nationality":
                setNationality(newValue);
                break;
            case "gender":
                setGender(newValue);
                break;
            case "dateofbirth":
                setDateOfBirth(newValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + field);
        }
    }

    @Override
    public String toString() {
        String movieString = String.join(",", movies);
        return getFirstname() + " " + getLastname() + "," + getNationality() + "," + getGender() + "," + getDateOfBirth() + "," + movieString;
    }

    public static Director findDirectorByName(String fullName) {
        for (Director director : directors) {
            if (director.getName().equalsIgnoreCase(fullName)) {
                return director;
            }
        }
        return null;
    }
    //Reading data from file method
    public static ArrayList<Director> readFromFile(String fileName) {
        //ArrayList<Director> directors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String firstname = parts[0];
                String lastname = parts[1];
                String nationality = parts[2];
                String gender = parts[3];
                String dateOfBirth = parts[4];
                ArrayList<String> movies = new ArrayList<>();
                for (int i = 5; i < parts.length; i++) {
                    movies.add(parts[i]);
                }
                directors.add(new Director(firstname, lastname, nationality, gender, dateOfBirth, movies));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return directors;
    }
    //Writing data to file method
    public static void writeToFile(String fileName
    ) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Director director : directors) {
                StringBuilder line = new StringBuilder();
                line.append(director.getFirstname()).append(",")
                        .append(director.getLastname()).append(",")
                        .append(director.getNationality()).append(",")
                        .append(director.getGender()).append(",")
                        .append(director.getDateOfBirth());

                for (String movie : director.getMovies()) {
                    line.append(",").append(movie);
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    //Search Method
    public static Director Search(String firstname, String lastname) {

        if (firstname == null || lastname == null ||
                firstname.trim().isEmpty() || lastname.trim().isEmpty()) {
            return null;
        }


        firstname = firstname.trim();
        lastname = lastname.trim();

        for (Director d : directors) {

            if (d.getFirstname() != null && d.getLastname() != null &&
                    d.getFirstname().equalsIgnoreCase(firstname) &&
                    d.getLastname().equalsIgnoreCase(lastname)) {
                return d;
            }
        }
        return null;
    }
    //Delete Method
    public static void delete(String firstname, String lastname) {
        Iterator<Director> iterator = directors.iterator();
        while (iterator.hasNext()) {
            Director director = iterator.next();
            if (director.getFirstname().equalsIgnoreCase(firstname) &&
                    director.getLastname().equalsIgnoreCase(lastname)) {
                iterator.remove();
                return;
            }
        }
    }


}


