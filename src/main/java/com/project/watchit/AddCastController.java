package com.project.watchit;
import com.project.watchit.Moviee.Cast;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddCastController {
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
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nationalityField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private void handleAddCastButton(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String nationality = nationalityField.getText();
        String gender = genderComboBox.getValue();
        String dateOfBirth = dateOfBirthPicker.getValue() != null ? dateOfBirthPicker.getValue().toString() : "";

        if (firstName.isEmpty() || lastName.isEmpty() || nationality.isEmpty() || gender == null || dateOfBirth.isEmpty()) {
            showAlert("Validation Error", "Please fill all the required fields.");
            return;
        }

        try {
            admin.addCast(firstName, lastName, nationality, gender, dateOfBirth);

            showAlert("Success", "actor.txt member added successfully!");

            clearFields();

        } catch (Exception e) {
            showAlert("Error", "Failed to add cast member: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        nationalityField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        dateOfBirthPicker.setValue(null);
    }

    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll("Male", "Female");
    }

}
