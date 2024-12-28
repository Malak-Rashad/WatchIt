package com.project.watchit;

import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MovieDetailsController {

    @FXML
    private ImageView moviePoster;
    @FXML
    private Label movieTitle;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label movieGenre;
    @FXML
    private Label movieCast;
    @FXML
    private Label movieDirector;
    @FXML
    private Label movieImdbScoreLabel;
    @FXML
    private Label releaseDateLabel;
    @FXML
    private Label lanuagesLabel;
    @FXML
    private Label DaurationLabel;

    @FXML
    private Button watchButton;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<Integer> Rate;

    @FXML
    private Button submitRating;


    private Movie currentMovie;
    private User currentUser;
    private String previousPage;

    public void initialize(URL location, ResourceBundle resources) {

        Rate.getItems().addAll(1, 2, 3, 4, 5);
        Rate.setPromptText("Rate 1-5");
        displayExistingRating(currentUser, currentMovie);
        submitRating.setDisable(true); // Initially disabled
        Rate.valueProperty().addListener((obs, oldVal, newVal) -> submitRating.setDisable(newVal == null));

    }


    public void setPreviousPage(String previousPage)
    {
        this.previousPage=previousPage;
    }
    private String formatCast(List<String> cast) {
        return (cast == null || cast.isEmpty()) ? "actor.txt: Not Available" : "actor.txt: " + String.join(", ", cast);
    }


    // Method to set the movies details
    public void setMovie(Movie currentMovie) {
        this.currentMovie=currentMovie;


        try {
            String imagePoster=currentMovie.getPosterPath();
            System.out.println("loading image :"+imagePoster);
            Image image=new Image(getClass().getResource(imagePoster).toExternalForm());
            moviePoster.setImage(image);
          //  System.out.println("actor.txt:\n " + currentMovie.getCast());
            //System.out.println("Director:\n " + currentMovie.getDirector());
            movieTitle.setText(currentMovie.getMovieTitle());
           descriptionLabel.setText(currentMovie.getDescription());
           movieGenre.setText("Genre: " + currentMovie.getGenres());
            movieCast.setText("actor.txt:  " + String.join(", ", currentMovie.getCast()));
            movieImdbScoreLabel.setText("IMDB: "+currentMovie.getImdbScore());
            releaseDateLabel.setText("Release Date: "+currentMovie.getReleaseDate());
            DaurationLabel.setText("Dauration: "+currentMovie.getDuration());
            lanuagesLabel.setText(currentMovie.getLanguages());
            movieDirector.setText(currentMovie.getDirector());
            //style
            releaseDateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold;");
            DaurationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold;");
            lanuagesLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold;");
            movieImdbScoreLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold;");
            movieGenre.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold;");
            movieCast.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;-fx-font-weight: bold italic;");
            movieDirector.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;-fx-font-weight: bold italic;");
            descriptionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;-fx-font-weight: bold italic; -fx-font-family: Sitka Text;");
            Rate.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;-fx-font-weight: bold italic; -fx-font-family: Sitka Text;");



        }
        catch (IllegalArgumentException e)
           {
             System.out.println("faild to load movie poster :"+e.getMessage());
           }
    }


    // Handle the "Watch" button action
    @FXML
    public void watchMovie(javafx.event.ActionEvent event) {
        System.out.println("Watch button clicked!");
        User currentUser=User.getCurrentUser();
        if (currentUser!=null){
            Subscription userSubscription = currentUser.getSubscription();
            if(userSubscription.isSubscriptionNotValid())
            {
                showSubscriptionExpiredAlert();

            }else
            {
                userSubscription.addMovie(currentMovie);
                showWatchConfirmation();
            }
            User.saveUsersToFile();
        }

        else {
            System.err.println("No user logged in.");
        }
    }

    private void showSubscriptionExpiredAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Subscription Invalid");
        alert.setHeaderText("Your subscription is either expired or you have reached the movie limit.");
        alert.setContentText("Would you like to renew your subscription?");

        ButtonType renewButton = new ButtonType("Renew Subscription");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(renewButton, cancelButton);

        // Handle the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == renewButton) {
                showPlanSelection();

            } else {
                System.out.println("User canceled the renewal.");
            }
        });

    }
    private void showPlanSelection() {

        ChoiceDialog<String> planDialog = new ChoiceDialog<>( "Basic", "Standard", "Premium");
        planDialog.setTitle("Select Your Plan");
        planDialog.setHeaderText("Choose a plan for your renewed subscription.");
        planDialog.setContentText("Choose a plan:");
        planDialog.showAndWait().ifPresent(selectedPlan -> {
            renewSubscription(selectedPlan);
        });
    }

    private void renewSubscription(String selectedPlan) {
        Subscription userSubscription = User.getCurrentUser().getSubscription();

        userSubscription.renewSubscription(selectedPlan);

        Alert renewalAlert = new Alert(Alert.AlertType.INFORMATION);
        renewalAlert.setTitle("Subscription Renewed");
        renewalAlert.setHeaderText("Your subscription has been successfully renewed!");
        renewalAlert.setContentText("Your new plan is: " + selectedPlan);
        renewalAlert.showAndWait();
    }


    private void showWatchConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Watch Movie");
        alert.setHeaderText(null);
        alert.setContentText("Enjoy Watching The Movie!");
        alert.showAndWait();
    }




    // Handle the "Back" button action
@FXML
    public void navigateBack(javafx.event.ActionEvent event) throws IOException {
    // switching scenes

    FXMLLoader loader = new FXMLLoader(getClass().getResource(previousPage));
    Parent mainPage=loader.load();
    Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow() ;
    // Scene scene = new Scene(loader.load());
    Scene mainPageScene= new Scene(mainPage,1400,1000);
    stage.setScene(mainPageScene);
    stage.setResizable(false);
    if (previousPage.equals("HomePage.fxml")){
        HomePageController controller=loader.getController();
        controller.initialize();
    }
    else if(previousPage.equals("allMovies.fxml"))
    {
        AllMoviesController controller=loader.getController();
        controller.initialize();
    }

    }

@FXML
   public void handleSubmitRating(ActionEvent event) {

        Integer selectedRating=Rate.getValue();

        if (Rate==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Rating Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select rating before submitting!!!!!!");
            alert.showAndWait();
            return;
        }

        User currentUser=User.getCurrentUser();
//        Movie currentmovie= currentMovie;

    UserWatchRecord existingRecord = null;
    for (UserWatchRecord record : UserWatchRecord.getUserWatchRecords()) {
        if (record.getUser().equals(currentUser) && record.getMovie().equals(currentMovie)) {
            existingRecord = record;
            break;
        }
    }

    if (existingRecord != null) {
        existingRecord.setRating(selectedRating);
    } else {
        UserWatchRecord newRecord = new UserWatchRecord(currentUser, currentMovie, selectedRating);
        UserWatchRecord.getUserWatchRecords().add(newRecord);
    }
    new UserWatchRecord().saveWatchRecords();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Rating Submitted");
    alert.setHeaderText(null);
    alert.setContentText("Your rating has been submitted.");
    alert.showAndWait();
    }

    private void displayExistingRating(User currentUser, Movie currentMovie) {
        for (UserWatchRecord record : UserWatchRecord.getUserWatchRecords()) {
            if (record.getUser().equals(currentUser) && record.getMovie().equals(currentMovie)) {
                Rate.setValue(record.getRating());
                break;
            }
        }
    }




}


