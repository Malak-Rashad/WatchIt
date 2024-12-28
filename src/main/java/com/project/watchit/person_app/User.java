package com.project.watchit.person_app;
import com.project.watchit.Subscription;
import java.io.*;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeParseException;


public class User extends SystemUser {
    private static List<User> users = new ArrayList<>();
    private static int nextUserId = 1;
    private static final String UsersFile = "users.txt";
    private static User currentUser;
    private Subscription subscription;

    public User(int id, String username, String password, String firstName, String lastName, String email, String subscriptionPlan) {
        super(id, username, password, firstName, lastName, email);
        if (subscriptionPlan != null && !subscriptionPlan.isEmpty()) {
            this.subscription = new Subscription(this, subscriptionPlan);
        } else {
            this.subscription = new Subscription(this, "No Plan");
        }
    }




    // Getters and Setters
    public static int getNextUserId() {
        return nextUserId;
    }

    public static List<User> getUsers() {
        return users;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void loadUsersFromFile() {
        File file = new File(UsersFile);
        if (!file.exists()) {
            System.out.println("Users file not found. Starting with an empty user list.");
            return;
        }

        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    String firstName = parts[3];
                    String lastName = parts[4];
                    String email = parts[5];
                    String subscriptionPlan = parts.length > 6 ? parts[6] : null;

                    LocalDate subscriptionStartDate = null;
                    if (parts.length > 8 && !parts[8].isEmpty()) {
                        try {
                            subscriptionStartDate = LocalDate.parse(parts[8]);
                        } catch (DateTimeParseException e) {
                            System.err.println("Invalid date format: " + parts[8]);
                        }
                    }

                    int numOfWatchedMovies = parts.length > 9 ? Integer.parseInt(parts[9]) : 0;

                    User user = new User(id, username, password, firstName, lastName, email, subscriptionPlan);
                    if (subscriptionPlan != null && subscriptionStartDate != null) {
                        user.subscription = new Subscription(user, subscriptionPlan, subscriptionStartDate, numOfWatchedMovies);
                    }

                    users.add(user);
                    if (id >= nextUserId) {
                        nextUserId = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
    public static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UsersFile))) {
            for (User user : users) {
                writer.write(user.getId() + "," +
                        user.getUsername() + "," +
                        user.getPassword() + "," +
                        user.getFirstName() + "," +
                        user.getLastName() + "," +
                        user.getEmail());

                if (user.subscription != null) {
                    writer.write("," + user.subscription.getPlan() + "," +
                            user.subscription.getPrice() + "," +
                            user.subscription.getStartDate() + "," +
                            user.subscription.getNumOfWatchedMovies());
                } else {
                    writer.write(",No Plan,0,,0");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void AddUser(User user) {
        users.add(user);
    }
    public static User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
