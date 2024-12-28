package com.project.watchit;
import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ControllerSubscription {

    private Stage stage;
    private Scene scene;

    @FXML
    private RadioButton standardOption;
    @FXML
    private RadioButton premiumOption;
    @FXML
    private RadioButton basicOption;
    @FXML
    private ToggleGroup subscriptionPlans;
    @FXML
    private Label subscriptionMessage;
    @FXML
    private     Label    signupMessage;
    private User currentUser = User.getCurrentUser();
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
    public void SwitchToHomePageController(ActionEvent event) throws IOException {
        System.out.println("Switching to ProfilePage scene...");
        switchScene(event, "HomePage.fxml");
    }

    @FXML
    private void onSubmit(ActionEvent event) throws IOException {
        if (currentUser == null) {
            subscriptionMessage.setText("No user is logged in. Please log in to subscribe.");
            return;
        }

        String selectedPlan = getSelectedPlan();

        if (selectedPlan == null) {
            subscriptionMessage.setText("You must select a subscription plan!");
            return;
        }

        Subscription subscription = new Subscription(currentUser, selectedPlan);
        currentUser.setSubscription(subscription);
        subscriptionMessage.setText("You have successfully subscribed to the " + selectedPlan + " plan! Start date: " + subscription.getStartDate());
        SwitchToHomePageController(event);

    }

    private String getSelectedPlan() {
        if (basicOption.isSelected()) return "Basic";
        if (premiumOption.isSelected()) return "Premium";
        if (standardOption.isSelected()) return "Standard";
        return null;
    }
}