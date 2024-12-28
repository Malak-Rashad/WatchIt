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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DeleteCastController {
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

        @FXML
        private TextField firstNameField;

        @FXML
        private TextField lastNameField;

        private Admin admin=new Admin();

        @FXML
        public void handleDeleteCast(ActionEvent event) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            try {
                admin.deleteCast(firstName, lastName);
                showAlert(AlertType.INFORMATION, "Success", "actor.txt member deleted successfully!");
            } catch (IllegalArgumentException e) {
                showAlert(AlertType.ERROR, "Error", e.getMessage());
            }
        }

        private void showAlert(AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }


