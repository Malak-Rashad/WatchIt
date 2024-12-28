package com.project.watchit;
import com.project.watchit.Moviee.Cast;
import com.project.watchit.Moviee.Director;
import com.project.watchit.person_app.Admin;
import com.project.watchit.person_app.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class WatchIt extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
          User.loadUsersFromFile();
          List<User> users =User.getUsers();
          Movie.loadMoviesFromFile("movie.txt");
          ArrayList<Movie> moviesList = Movie.getMoviesList();
            UserWatchRecord.loadWatchRecords(users,moviesList);

            Cast.readFile("actor.txt");
            Director.readFromFile("directors.txt");

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/WatchIt/Login.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Watch it App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() throws Exception {
        User.saveUsersToFile();
        System.out.println("Users saved successfully.");
        Cast.WriteCastToFile("actor.txt");
        Director.writeToFile("directors.txt");
        UserWatchRecord userWatchRecord=new UserWatchRecord();
        userWatchRecord.saveWatchRecords();
        System.out.println("userWatchRecord saved successfully.");
        Admin.updateMoviesFile("movie.txt");
    }


    public static void main(String[] args) {
        launch(args);
    }
}

