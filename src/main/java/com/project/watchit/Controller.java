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
import java.io.*;
import java.util.Objects;
public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Switch to other scenes (Login, Signup,sup)
    public void SwitchToLogIn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Signup.fxml")));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
    private void handleLogin(ActionEvent event) {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        User user = User.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            loginMessage.setText("Login successful! Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        } else {
            loginMessage.setText("Invalid username or password.");
        }
    }

    // Handle signup
    @FXML
    private void handleSignup(ActionEvent event) {
        String username = signupUsername.getText();
        String password = signupPassword.getText();
        String confirmPassword = signupConfirmPassword.getText();
        String firstName = signupFirstName.getText();
        String lastName = signupLastName.getText();
        String email = signupEmail.getText();

        User user = new User();
        user = user.findUserByUsername(username);

        if (user != null) {
            signupMessage.setText("Username already exists.");
            return;
        }

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
        //a regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            signupMessage.setText("Invalid email format.");
            return;
        }
        int nextID = user.getNextUserId();
        User newUser = new User(nextID++, username, password, firstName, lastName, email);
        user.AddUser(newUser);
        signupMessage.setText("Signup successful! You can now log in.");
    }
}
