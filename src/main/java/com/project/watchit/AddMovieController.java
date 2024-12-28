package com.project.watchit;

import com.project.watchit.person_app.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class AddMovieController {
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
    private TextField titleField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField durationField;
    @FXML
    private TextField cast1FNameField;
    @FXML
    private TextField cast1LNameField;
    @FXML
    private TextField cast2FNameField;
    @FXML
    private TextField cast2LNameField;
    @FXML
    private TextField cast3FNameField;
    @FXML
    private TextField cast3LNameField;
    @FXML
    private TextField genre1Field;
    @FXML
    private TextField genre2Field;
    @FXML
    private TextField languageField;
    @FXML
    private TextField directorFNameField;
    @FXML
    private TextField directorLNameField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField budgetField;
    @FXML
    private TextField revenueField;
    @FXML
    private TextField imdbField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField descriptionArea;
    @FXML
    private TextField posterPathField;

    private Admin admin = new Admin();

    @FXML
    private void addMovie(ActionEvent event) {
        try {
            String movieTitle = titleField.getText().trim();
            LocalDate releaseDate = releaseDatePicker.getValue();
            if (releaseDate == null) throw new IllegalArgumentException("Release date is required.");
            int year = releaseDate.getYear();
            int month = releaseDate.getMonthValue();
            int day = releaseDate.getDayOfMonth();

            int duration = Integer.parseInt(durationField.getText().trim());
            String cast1FName = cast1FNameField.getText().trim();
            String cast1LName = cast1LNameField.getText().trim();
            String cast2FName = cast2FNameField.getText().trim();
            String cast2LName = cast2LNameField.getText().trim();
            String cast3FName = cast3FNameField.getText().trim();
            String cast3LName = cast3LNameField.getText().trim();
            String genre1 = genre1Field.getText().trim();
            String genre2 = genre2Field.getText().trim();
            String language = languageField.getText().trim();
            String directorFName = directorFNameField.getText().trim();
            String directorLName = directorLNameField.getText().trim();
            String country = countryField.getText().trim();
            int budget = Integer.parseInt(budgetField.getText().trim());
            int revenue = Integer.parseInt(revenueField.getText().trim());
            float imdb = Float.parseFloat(imdbField.getText().trim());
            int rating = Integer.parseInt(ratingField.getText().trim());
            String description = descriptionArea.getText().trim();
            String posterPath = posterPathField.getText().trim();

            admin.addMovie(movieTitle, year, month, day, duration,
                    cast1FName, cast1LName, cast2FName, cast2LName,
                    cast3FName, cast3LName, genre1, genre2,
                    language, directorFName, directorLName,
                    country, budget, revenue, imdb, rating,
                    description, posterPath);

            showAlert("Success", "Movie added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Please ensure all numeric fields are correctly filled.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
