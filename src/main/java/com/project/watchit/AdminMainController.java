package com.project.watchit;

import com.project.watchit.person_app.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminMainController {
    private Stage stage;
    private Scene scene;
    @FXML
    private Label resultLabel;
    @FXML
    private Label revenueResultLabel;


    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        try {
            System.out.println("Attempting to load scene: " + fxmlFile);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlFile);
            e.printStackTrace();
            throw e;
        }
    }


    public void goToAddMovie(ActionEvent event) throws IOException {
        System.out.println("Switching to AddMoviePage scene...");
        switchScene(event, "AddMoviePage.fxml");
    }

    public void goToDeleteMovie(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "DeleteMoviePage.fxml");
    }
    public void EditMovie(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "EditMovie.fxml");
    }
    public void goToDeleteUser(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "DeleteUser.fxml");
    }

    public void goToAddUser(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "AddUser.fxml");
    }
    @FXML
    private void handleMostSubscribedPlan(ActionEvent event)throws IOException  {
        String mostSubscribedPlan = Admin.mostSubscribedPlan();
        resultLabel.setText("Most Subscribed Plan: " + mostSubscribedPlan);
    }
    @FXML
    private void handleCheckRevenue() {
        String highestRevenueMonth = Admin.monthWithHighestRevenue();
        revenueResultLabel.setText("Month with Highest Revenue: " + highestRevenueMonth);
    }
    @FXML
    public void goToEditUser(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "EditUser.fxml");
    }
    @FXML
    public void goToAddCast(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "AddCast.fxml");
    }
    @FXML
    public void goToEditCast(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "EditCast.fxml");
    }
    @FXML
    public void goToDeleteCast(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "DeleteCast.fxml");
    }
    @FXML
    public void goToAddDirector(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "AddDirector.fxml");
    }
    @FXML
    public void goToEditDirector(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "EditDirector.fxml");
    }
    @FXML
    public void goToDeleteDirector(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "DeleteDirector.fxml");
    }
    @FXML
    public void goToAddWatchRecord(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "AddWatchRecord.fxml");
    }
    @FXML
    public void goToEditWatchRecord(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "EditWatchRecord.fxml");
    }
    @FXML
    public void goToDeleteWatchRecord(ActionEvent event) throws IOException {
        System.out.println("Switching to DeleteMoviePage scene...");
        switchScene(event, "DeleteWatchRecord.fxml");
    }
}
