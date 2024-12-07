package com.project.watchit.person_app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User extends SystemUser {
    private static List<User> users = new ArrayList<>(); // Static list of users
    private static int nextUserId = 1; // Static counter for unique IDs
    private static final String UsersFile = "users.txt"; // File path

    // Default constructor
    public User() {}

    // Constructor with parameters
    public User(int id, String username, String password, String firstName, String lastName, String email) {
        super(id, username, password, firstName, lastName, email);
    }

    // Static method to get the next user ID
    public static int getNextUserId() {
        return nextUserId;
    }

    public static List<User> getUsers() {
        return users;
    }

    // Load users from file
    public static void loadUsersFromFile() {
        File file = new File(UsersFile);
        if (!file.exists()) {
            System.out.println("Users file not found. Starting with an empty user list.");
            return;
        }

        users.clear(); // Clear the current list to avoid duplicates

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    String firstName = parts[3];
                    String lastName = parts[4];
                    String email = parts[5];
                    users.add(new User(id, username, password, firstName, lastName, email));
                    if (id >= nextUserId) {
                        nextUserId = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    // Save users to file
    public static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UsersFile))) {
            for (User user : users) {
                writer.write(user.getId() + ";" +
                        user.getUsername() + ";" +
                        user.getPassword() + ";" +
                        user.getFirstName() + ";" +
                        user.getLastName() + ";" +
                        user.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Find a user by username
    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Add a new user
    public static void AddUser(User user) {
        users.add(user);
    }
}
