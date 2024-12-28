package com.project.watchit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AllMoviesController {
    @FXML
   private GridPane gridPane;
    @FXML
    private Button backButton;

    public void initialize(){
        ArrayList<Movie> movies = Movie.getMoviesList();
//        System.out.println("Movies list size :"+(movies==null?"null":movies.size()));
//        System.out.println("GridPane : "+(gridPane==null?"null":"initialize"));
        Movies(gridPane, movies);
    }

    private void Movies(GridPane gridPane, ArrayList<Movie> movies) {

        int columns=6; //Number of cloumn per row
        int column=0;
        int row=0;
        try {



        for (Movie movie:movies)

        {

//            System.out.println("Poster path: " + movie.getPosterPath());
//            System.out.println("Resolved path: " + getClass().getResource(movie.getPosterPath()));


            URL path=getClass().getResource(movie.getPosterPath());
            if(path==null){
                System.out.println("Error: File not found - " + movie.getPosterPath());

            }
            else{
            Image poster=new Image(path.toExternalForm());
            ImageView moviePoster=new ImageView(poster);

            moviePoster.setFitWidth(150);
            moviePoster.setFitHeight(200);
            moviePoster.setPreserveRatio(true);

            StackPane posterPane = new StackPane(moviePoster);
            posterPane.setStyle("-fx-cursor: hand;");
            posterPane.setOnMouseClicked(event -> {
                navigateToMovieDetails(event,movie);
            });
            gridPane.add(posterPane, column, row);

            column ++;

            if (column==columns)
            {
                column=0;
                row++;
            }}



        }
    }catch (NullPointerException e)
        {
            System.err.println("Error loading poster for movie : ");
            e.printStackTrace();
        }
    }

    private void navigateToMovieDetails(MouseEvent event, Movie movies)  {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("moviedetails.fxml"));
            Parent root = loader.load();
            MovieDetailsController controller=loader.getController();
            controller.setMovie(movies);
            controller.setPreviousPage("allMovies.fxml");
            Stage stage=(Stage)  ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,1000));
            stage.setResizable(false);

        }catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    public void goToMainPage()
    {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root =loader.load();
            Stage stage=(Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
