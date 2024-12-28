package com.project.watchit;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import com.project.watchit.person_app.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class TopWatchedMoviesController {

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
    private GridPane topMoviesGridPane;

    @FXML
    private TextField number;

    private UserWatchRecord userWatchRecord = new UserWatchRecord();

    @FXML
    public void initialize() {
        topMoviesGridPane.setHgap(50);  // Horizontal gap between columns
        topMoviesGridPane.setVgap(10);  // Vertical gap between rows
        topMoviesGridPane.setPadding(new Insets(20));
    }

    @FXML
    public void onSubmit() {
        try {
            int N = Integer.parseInt(number.getText().trim());
            displayTopWatchedMovies(N);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
    }



    public void displayTopWatchedMovies(int N) {
        topMoviesGridPane.getChildren().clear();
        topMoviesGridPane.getRowConstraints().clear(); // Clear previous constraints

        List<String> topMovies = userWatchRecord.topWatchedMovies(N);
        int column = 0;
        int row = 0;
        final int maxColumns = 4;

        for (String movieTitle : topMovies) {
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

                    topMoviesGridPane.add(imageView, column, row);

                    column++;
                    if (column >= maxColumns) {
                        column = 0;
                        row++;

                        RowConstraints rc = new RowConstraints();
                        rc.setMinHeight(20); // Adjust vertical spacing between rows
                        topMoviesGridPane.getRowConstraints().add(rc);
                    }
                }
            }
        }
    }
    private Movie findMovieByTitle (String title){
            for (Movie movie : Movie.getMoviesList()) {
                if (movie.getMovieTitle().equalsIgnoreCase(title)) {
                    return movie;
                }
            }
            return null;
        }

    }
