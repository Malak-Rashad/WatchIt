package com.project.watchit;
import com.project.watchit.person_app.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class EditMovieController {
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
    private TextField movieIDField;

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker releaseDatePicker;

    @FXML
    private TextField durationField;

    @FXML
    private TextField cast1FNameField;
    @FXML
    private TextField  cast1LNameField;
    @FXML
    private TextField cast2FNameField;
    @FXML
    private TextField  cast2LNameField;
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

    @FXML
    private Button updateButton;

    private Admin admin;

    public EditMovieController() {
        this.admin = new Admin();
    }

    @FXML
    private void initialize() {
        updateButton.setOnAction(event -> editMovie());
    }
    private void editMovie() {
        try {
            int movieID = Integer.parseInt(movieIDField.getText().trim());
            String newMovieTitle = titleField.getText().trim();
            LocalDate releaseDate = releaseDatePicker.getValue();
            Integer year = releaseDate != null ? releaseDate.getYear() : null;
            Integer month = releaseDate != null ? releaseDate.getMonthValue() : null;
            Integer day = releaseDate != null ? releaseDate.getDayOfMonth() : null;

            int newDuration = Integer.parseInt(durationField.getText().trim());

            String newCast1FName = cast1FNameField.getText().trim();
            String newCast1LName = cast1LNameField.getText().trim();
            String newCast2FName = cast2FNameField.getText().trim();
            String newCast2LName = cast2LNameField.getText().trim();
            String newCast3FName = cast3FNameField.getText().trim();
            String newCast3LName = cast3LNameField.getText().trim();

            String newGenre1 = genre1Field.getText().trim();
            String newGenre2 = genre2Field.getText().trim();

            String newLanguage = languageField.getText().trim();

            String newDirectorFName = directorFNameField.getText().trim();
            String newDirectorLName = directorLNameField.getText().trim();

            String newCountry = countryField.getText().trim();

            int newBudget = Integer.parseInt(budgetField.getText().trim());
            int newRevenue = Integer.parseInt(revenueField.getText().trim());

            float newImdb = Float.parseFloat(imdbField.getText().trim());
            int newRating = Integer.parseInt(ratingField.getText().trim());

            String newDescription = descriptionArea.getText().trim();
            String newPath = posterPathField.getText().trim();

            admin.editMovie(movieID, newMovieTitle, year, month, day, newDuration,
                    newCast1FName, newCast1LName, newCast2FName, newCast2LName,
                    newCast3FName, newCast3LName, newGenre1, newGenre2,
                    newLanguage, newDirectorFName, newDirectorLName,
                    newCountry, newBudget, newRevenue, newImdb, newRating,
                    newDescription, newPath);

            showAlert("Success", "Movie updated successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Please ensure all numeric fields are correctly filled.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Failed to update movie: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
