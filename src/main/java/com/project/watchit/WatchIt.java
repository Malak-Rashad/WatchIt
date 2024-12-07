package com.project.watchit;
import com.project.watchit.person_app.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.Objects;
public class WatchIt extends Application {
    private static User userManager;
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize User Manager
        User.loadUsersFromFile();
            // Load and set the Login screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/WatchIt/Login.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() throws Exception {
        // Save users when the application closes
        User.saveUsersToFile();
        System.out.println("Users saved successfully.");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
