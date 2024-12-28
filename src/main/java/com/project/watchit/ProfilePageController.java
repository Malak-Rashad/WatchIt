package com.project.watchit;

import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfilePageController {
    private User currentUser = User.getCurrentUser();
    @FXML
    private Label NumberOfWatchedMovies;
    @FXML
    private Label DateOfSubscription;

    @FXML
    private Label Email;

    @FXML
    private Label Name;

    @FXML
    private Label Plan;

    @FXML
    private Label User_Name;

    private Stage stage;
    private Scene scene;

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
    public void Back(ActionEvent event) throws IOException {
        System.out.println("Switching to New subscription scene...");
        switchScene(event, "HomePage.fxml");
    }
    public void initialize() {
        User currentUser = User.getCurrentUser();

        if (currentUser != null) {
            User_Name.setText(currentUser.getUsername());
            Email.setText(currentUser.getEmail());
            Name.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            Plan.setText(currentUser.getSubscription().getPlan());
            if (currentUser.getSubscription().getStartDate()!= null) {
                DateOfSubscription.setText(currentUser.getSubscription().getStartDate().toString());
            } else {
                DateOfSubscription.setText("No subscription date");
            }
            if(currentUser.getSubscription().getNumOfWatchedMovies()>=0)
            {
                NumberOfWatchedMovies.setText(String.valueOf(currentUser.getSubscription().getNumOfWatchedMovies()));
            }

        } else {
            User_Name.setText("No user logged in");
            Email.setText("");
            Name.setText("");
            Plan.setText("");
            DateOfSubscription.setText("");
        }
    }
}
