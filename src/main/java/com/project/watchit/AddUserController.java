package com.project.watchit;
import com.project.watchit.person_app.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;


public class AddUserController {


    private Stage stage;
    private Scene scene;

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        try {
            System.out.println("Attempting to load scene: " + fxmlFile);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlFile);
            e.printStackTrace();
            throw e;
        }
    }
    public void Back(ActionEvent event) throws IOException {
        System.out.println("Switching to New subscription scene...");
        switchScene(event, "AdminPage.fxml");
    }
    Admin admin=new Admin();
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> planComboBox;

    @FXML
    private TextField numOfWatchedMoviesField;

    @FXML
    private DatePicker datePicker;

    // Called when the "Add User" button is clicked
    @FXML
    private void handleAddUserButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String plan = planComboBox.getValue();
        int numOfWatchedMovies = Integer.parseInt(numOfWatchedMoviesField.getText());
        LocalDate startDate = datePicker.getValue();

        if (username.isEmpty() || email.isEmpty() || plan == null || startDate == null) {
            showAlert("Validation Error", "Please fill all required fields.");
            return;
        }

        try {
           admin.addUser(username, password, firstName, lastName, email, plan,
                    numOfWatchedMovies, startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());

            showAlert("Success", "User added successfully!");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        planComboBox.getItems().addAll("Basic", "Premium", "Standard");
    }
}
