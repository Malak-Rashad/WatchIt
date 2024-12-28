package com.project.watchit;

import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageController {
    private Stage stage;
    private Scene scene;
    @FXML
    private VBox topRecent;
    @FXML
    private Button allMoviesButton;
    @FXML
    private ScrollPane recentMoviesScrollPane;
    @FXML
    private ScrollPane recommended;
    @FXML
    private VBox foryou;

    private  User currentUser;
    private List<Movie> movies;
    private List<Movie> recentMovies;
    private List<Movie> recommendedForYou;

    private Recommendation recommendation;




    public void initialize (){
        try {
            currentUser=User.getCurrentUser();
            if(currentUser==null){
                System.err.println("No current user found");
                return;
            }
            movies=Movie.getMoviesList();
            recommendation=new Recommendation();


            if(movies.isEmpty()){
                System.err.println("No movies found");
                recentMovies=new ArrayList<>();
            }else {
                recentMovies=Movie.recent5Movie();
            }
           // recommendedForYou=recommendation.RecommendByCast_Genre(currentUser.getId());
            if (currentUser.getId()==0)
            {
                System.err.println("current user is not found");
                recommendedForYou=new ArrayList<>();
            }
            else {
                recommendedForYou = recommendation.RecommendByCast_Genre(currentUser.getId());
            }
            loadRecentMovies();
            loadRecommendedForYou();
        } catch (Exception e) {
            System.err.println("Error during the initialization");
            e.printStackTrace();
        }

    }
//    ArrayList<Movie> recentMovies=Movie.recent5Movie();
//    Recommendation recommendation=new Recommendation();
//    //User currentUser=User.getCurrentUser();
  // List<User> users = User.getUsers();
//    List<Movie> movies = Movie.getMoviesList();
//   List<UserWatchRecord> watchRecords = UserWatchRecord.loadWatchRecords(users, movies);
//    List<Movie> recommendedForYou=  recommendation.RecommendByCast_Genre(currentUser.getId());



    public void  loadRecentMovies(){

        topRecent.getChildren().clear();
        for (Movie movie:recentMovies) {
            if(movie.getReleaseDate().isBefore(LocalDate.now())) {
                HBox movieBox = styleMovie(movie);
                topRecent.getChildren().add(movieBox);
            }
            if (topRecent.getChildren().isEmpty()) {
                Label placeholder = new Label("No recent movies available.");
                placeholder.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
                topRecent.getChildren().add(placeholder);
            }
        }
    }
    public void loadRecommendedForYou(){
        foryou.getChildren().clear();
        if(recommendedForYou.isEmpty()||recommendedForYou==null)
        {
            Label alert=new Label("No recommendations available ,we working on it");
            alert.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            foryou.getChildren().add(alert);
            return;
        }

        for(Movie movie: recommendedForYou)
        {
            HBox recommendedBox=recommendedStyle(movie);
            foryou.getChildren().add(recommendedBox);
        }

    }

    private HBox recommendedStyle(Movie movie) {
        HBox hBox=new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");
        URL path= getClass().getResource(movie.getPosterPath());
        if(path==null)
            System.out.println("Error: File not found - " + movie.getPosterPath());
        else
        {
            Image poster =new Image(path.toExternalForm());
            ImageView movieposter=new ImageView(poster);
            movieposter.setFitWidth(100);
            movieposter.setFitHeight(175);

            Label label=new Label(movie.getMovieTitle());
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;; -fx-text-fill: #f2eded;");
            movieposter.setOnMouseClicked(mouseEvent -> openMovieDetailsPage(mouseEvent,movie));
            hBox.getChildren().addAll(movieposter,label);
        }
        return hBox;
    }

    private HBox styleMovie(Movie movie) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");
        URL path = getClass().getResource(movie.getPosterPath());
        if (path == null) {
            System.out.println("Error: File not found - " + movie.getPosterPath());
        }
        else {
            Image poster = new Image(path.toExternalForm());
            ImageView moviePoster = new ImageView(poster);
            moviePoster.setFitWidth(100);
            moviePoster.setFitHeight(175);

            Label titleLabel = new Label(movie.getMovieTitle());
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;; -fx-text-fill: #f2eded;");
            Label releaseDateLabel = new Label("Release Date: " + movie.getReleaseDate());
            releaseDateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
            moviePoster.setOnMouseClicked(mouseEvent -> openMovieDetailsPage(mouseEvent,movie));
            hBox.getChildren().addAll(moviePoster, titleLabel);
        }

        return hBox;
    }

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        try {
            System.out.println("Attempting to load scene: " + fxmlFile);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load(); // Load fresh Parent

            // Retrieve the stage and set a new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 1400, 1000);

            stage.setScene(newScene); // Set the new Scene
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML file: " + fxmlFile);
            e.printStackTrace();
            throw e;
        }
    }

    public void SwitchToProfilePageController(ActionEvent event) throws IOException {
            System.out.println("Switching to ProfilePage scene...");
            switchScene(event, "ProfilePage.fxml");
        }

    public void SwitchToRenewSubscriptionController(ActionEvent event) throws IOException {
        System.out.println("Switching to  scene...");
        switchScene(event, "RenewSubscription.fxml");
    }
    public void SwitchToWatchRecordController(ActionEvent event) throws IOException {
        System.out.println("Switching to WatchedMovies  scene...");
        switchScene(event, "WatchedMovies.fxml");
    }
    public void SwitchToTopWatchedMoviesController(ActionEvent event) throws IOException {
        System.out.println("Switching to ProfilePage scene...");
        switchScene(event, "TopWatchedMovies.fxml");
    }


    private void openMovieDetailsPage(MouseEvent event, Movie movie)  {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("moviedetails.fxml"));
            Parent movieDetailsPage = loader.load();

            MovieDetailsController detailsController = loader.getController();
            detailsController.setMovie(movie);
            detailsController.setPreviousPage("HomePage.fxml");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene movieDetailsScene = new Scene(movieDetailsPage);
            stage.setScene(movieDetailsScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void navigateToMovieDetailsAllMovies() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("allMovies.fxml"));
            Parent root = loader.load();

            // Get the controller for the Movie Details page
            AllMoviesController movieController = loader.getController();


            // Navigate to the Movie Details page
            Stage stage = (Stage) allMoviesButton.getScene().getWindow();
            stage.setScene(new Scene(root,1400,1000));

            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
