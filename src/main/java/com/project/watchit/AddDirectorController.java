package com.project.watchit;

import com.project.watchit.person_app.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AddDirectorController {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nationalityField;

    @FXML
    private TextField genderField;

    @FXML
    private DatePicker dateOfBirthField;

    private final Admin admin = new Admin();

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
        System.out.println("Switching to Admin Page...");
        switchScene(event, "AdminPage.fxml");
    }

    @FXML
    public void handleAddDirector(ActionEvent event) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String nationality = nationalityField.getText().trim();
        String gender = genderField.getText().trim();
        LocalDate dateOfBirth = dateOfBirthField.getValue();

        try {
            validateInputs(firstName, lastName, nationality, gender, dateOfBirth);

            // Call the Admin class's addDirector method
            admin.addDirector(firstName, lastName, nationality, gender, dateOfBirth.toString());

            showAlert(Alert.AlertType.INFORMATION, "Success", "Director added successfully!");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add director. Please check the input fields.");
            e.printStackTrace();
        }
    }

    private void validateInputs(String firstName, String lastName, String nationality, String gender, LocalDate dateOfBirth) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("First and last name cannot be empty.");
        }
        if (nationality.isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty.");
        }
        if (gender.isEmpty() || !(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other"))) {
            throw new IllegalArgumentException("Gender must be 'Male', 'Female', or 'Other'.");
        }
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth must be selected.");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
