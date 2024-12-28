package com.project.watchit;

import com.project.watchit.person_app.Admin;
import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AddWatchRecord {
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
        private TextField userIdField;

        @FXML
        private TextField movieNameField;

        @FXML
        private TextField ratingField;

        @FXML
        private DatePicker watchDatePicker;

        private Admin admin = new Admin();

        public void addMovieToUserWatchRecords() {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String movieName = movieNameField.getText();
                int rating = Integer.parseInt(ratingField.getText());
                LocalDate watchDate = watchDatePicker.getValue();

                if (movieName == null || movieName.isBlank()) {
                    throw new IllegalArgumentException("Movie name cannot be blank.");
                }

                if (watchDate == null) {
                    throw new IllegalArgumentException("Watch date must be provided.");
                }

                admin.addMovieToUserWatchRecords(userId, movieName, rating, watchDate.getYear(), watchDate.getMonthValue(), watchDate.getDayOfMonth());

                showAlert(Alert.AlertType.INFORMATION, "Success", "Record added successfully.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }



