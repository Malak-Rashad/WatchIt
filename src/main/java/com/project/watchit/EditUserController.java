package com.project.watchit;

import com.project.watchit.person_app.*;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EditUserController {
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
    Admin admin = new Admin();

    @FXML
    private TextField userIDField;
    @FXML
    private TextField newUsernameField;
    @FXML
    private TextField newUserFNameField;
    @FXML
    private TextField newUserLNameField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField newEmailField;
    @FXML
    private ComboBox<String> newPlanComboBox;
    @FXML
    private TextField numOfWatchedMoviesField;

    @FXML
    public void handleEditUser() {
        try {
            int userID = Integer.parseInt(userIDField.getText());
            String newUsername = newUsernameField.getText();
            String newUserFName = newUserFNameField.getText();
            String newUserLName = newUserLNameField.getText();
            String newPassword = newPasswordField.getText();
            String newEmail = newEmailField.getText();
            String newPlan = newPlanComboBox.getValue();
            Integer numOfWatchedMovies = numOfWatchedMoviesField.getText().isEmpty()
                    ? null
                    : Integer.parseInt(numOfWatchedMoviesField.getText());

            admin.editUser(userID, newUsername, newUserFName, newUserLName, newPassword, newEmail, newPlan, numOfWatchedMovies);

            showAlert(AlertType.INFORMATION, "Success", "User information updated successfully.");
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numbers for user ID and number of watched movies.");
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        newPlanComboBox.getItems().addAll("Basic", "Premium", "Standard");
    }
}
