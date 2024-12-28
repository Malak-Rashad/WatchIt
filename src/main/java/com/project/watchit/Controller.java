package com.project.watchit;

import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Controller {

    private Stage stage;
    private Scene scene;

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        try {
            System.out.println("Attempting to load scene: " + fxmlFile);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlFile);
            e.printStackTrace();
            throw e;
        }
    }

    public void SwitchToSubscription(ActionEvent event) throws IOException {
        System.out.println("Switching to subscription scene...");
        switchScene(event, "Subscription.fxml");
    }

    public void SwitchToLogIn(ActionEvent event) throws IOException {
        switchScene(event, "Login.fxml");
    }

    public void SwitchToSignUp(ActionEvent event) throws IOException {
        switchScene(event, "Signup.fxml");
    }


    // Login and Signup Fields
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Label loginMessage;
    @FXML
    private TextField signupUsername;
    @FXML
    private PasswordField signupPassword;
    @FXML
    private PasswordField signupConfirmPassword;
    @FXML
    private TextField signupFirstName;
    @FXML
    private TextField signupLastName;
    @FXML
    private TextField signupEmail;
    @FXML
    private Label signupMessage;

    // Handle login
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        User user = User.findUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            User.setCurrentUser(user);

            if (user.getId() == 1) { // Check if the user is the admin
                loginMessage.setText("Login successful! Welcome, Admin.");

                switchScene(event, "AdminPage.fxml");
            } else {
                Subscription subscription = user.getSubscription();
                if (subscription == null || !subscription.isSubscriptionActive()) {
                    loginMessage.setText("Login successful, but your subscription is inactive or missing.");
                    switchScene(event, "HomePage.fxml");

                } else {
                    loginMessage.setText("Login successful! Welcome back, " + user.getFirstName() + "!");
                    switchScene(event, "HomePage.fxml");
                }
            }
        } else {
            loginMessage.setText("Invalid username or password.");
        }

        List<User> userList=User.getUsers();
        System.out.println(userList);
    }


    // Handle signup
    @FXML
    private void handleSignup(ActionEvent event) throws IOException {
        String username = signupUsername.getText();
        String password = signupPassword.getText();
        String confirmPassword = signupConfirmPassword.getText();
        String firstName = signupFirstName.getText();
        String lastName = signupLastName.getText();
        String email = signupEmail.getText();


        // Check if username already exists
        if (User.findUserByUsername(username) != null) {
            signupMessage.setText("Username already exists.");
            return;
        }

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            signupMessage.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            signupMessage.setText("Passwords do not match.");
            return;
        }

        if (password.length() < 8) {
            signupMessage.setText("Password must be at least 8 characters long.");
            return;
        }

        if (username.contains(" ")) {
            signupMessage.setText("Username cannot contain spaces.");
            return;
        }

        // Email format validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            signupMessage.setText("Invalid email format.");
            return;
        }
        String defaultSubscriptionPlan = ""; // Empty initially
        int nextID = User.getNextUserId();
        User newUser = new User(nextID, username, password, firstName, lastName, email, defaultSubscriptionPlan);
        User.AddUser(newUser);
        User.setCurrentUser(newUser);
        SwitchToSubscription(event);
        signupMessage.setText("Signup successful! Now, choose your subscription plan.");
    }
}
