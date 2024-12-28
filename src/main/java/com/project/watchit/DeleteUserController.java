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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DeleteUserController {

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
    private TextField userIdField;

    @FXML
    private void handleDeleteUserButton(ActionEvent event) {
        String userIdText = userIdField.getText();

        if (userIdText.isEmpty()) {

            showAlert("Validation Error", "Please enter a User ID.");
            return;
        }

        try {
            int userID = Integer.parseInt(userIdText);
            if (!DeleteUser(userID)) {

                showAlert("Error", "User with ID " + userID + " not found.");
            } else {
                admin.deleteUser(userID);
                showAlert("Success", "User with ID " + userID + " has been deleted.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid numeric User ID.");
        }
    }

    private boolean DeleteUser(int userID) {

        boolean userDeleted = false;
        for (User u : User.getUsers()) {
            if (u.getId() == userID) {
               return true;
            }
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
