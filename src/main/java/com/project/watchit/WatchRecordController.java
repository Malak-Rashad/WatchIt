package com.project.watchit;

import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WatchRecordController {
    private boolean isInitialized = false;

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
        switchScene(event, "HomePage.fxml");
    }
    @FXML
    private GridPane posterGridPane;

    ArrayList<Movie> moviesList = Movie.getMoviesList();
    private UserWatchRecord userWatchRecord = new UserWatchRecord();
    public void displayPostersForUser(User user) {
        posterGridPane.getChildren().clear();
        List<String> watchedMovies = userWatchRecord.displayWatchedMovies(user);
        int column = 0;
        int row = 0;
        final int maxColumns = 4;

        for (String movieTitle : watchedMovies) {
            Movie movie = findMovieByTitle(movieTitle);
            if (movie != null) {
                String posterPath = "/com/project/watchit/" + movie.getPosterPath();
                URL url = getClass().getResource(movie.getPosterPath());

                if (url != null) {
                    Image image = new Image(url.toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(120);
                    imageView.setFitHeight(180);
                    imageView.setPreserveRatio(true);

                    posterGridPane.add(imageView, column, row);
                    System.out.println("Movie added at column " + column + " and row " + row);

                    column++;
                    if (column >= maxColumns) {
                        column = 0;
                        row++;
                    }
                } else {
                    System.out.println("Image not found for movie: " + movieTitle);
                }
            }
        }
    }

    private Movie findMovieByTitle(String title) {
        for (Movie movie : moviesList) {
            if (movie.getMovieTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
        return null;
    }


    public void initialize() {
        if (!isInitialized) {
            isInitialized = true;
            posterGridPane.setHgap(50);
            posterGridPane.setVgap(10);
            posterGridPane.setPadding(new Insets(10));
            displayPostersForUser(User.getCurrentUser());
        }
    }


}