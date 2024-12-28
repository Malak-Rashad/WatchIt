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

public class RenewSubscriptionController {

    @FXML
    private Label PlanLbl;
    @FXML

    private Label msgLbl;

    @FXML
    private Label PriceLbl;

    @FXML
    private Label StartDateLbl;
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

    public void SwitchToNewSubscription(ActionEvent event) throws IOException {
        System.out.println("Switching to New subscription scene...");
        switchScene(event, "NewSubscription.fxml");
    }
    public void Back(ActionEvent event) throws IOException {
        System.out.println("Switching to New subscription scene...");
        switchScene(event, "HomePage.fxml");
    }

    public void initialize() {
        User currentUser = User.getCurrentUser();
        if (currentUser != null && currentUser.getSubscription() != null) {
            PlanLbl.setText(currentUser.getSubscription().getPlan());
            PriceLbl.setText(String.valueOf(currentUser.getSubscription().getPrice()));
            StartDateLbl.setText(String.valueOf(currentUser.getSubscription().getStartDate()));
            if(!currentUser.getSubscription().isSubscriptionActive())
            {
                msgLbl.setText("You Have To Renew Your Plan");
            }
        } else {
            PlanLbl.setText("No Plan");
            PriceLbl.setText("0");
            StartDateLbl.setText("N/A");
        }
    }

}
