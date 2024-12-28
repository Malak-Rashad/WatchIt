package com.project.watchit.person_app;
import com.project.watchit.UserWatchRecord;
import com.project.watchit.Movie;
import com.project.watchit.Moviee.Cast;
import com.project.watchit.Moviee.Director;
import com.project.watchit.Subscription;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.ArrayList;



public class Admin extends SystemUser {


    public Admin() {
    }

    public Admin(int id, String username, String password, String firstName, String lastName, String email) {
        super(id, username, password, firstName, lastName, email);
    }


    private Cast getOrCreateCast(String firstName, String lastName) {
        Cast cast = Cast.search(firstName, lastName);
        if (cast == null) {
            cast = new Cast(firstName, lastName, " ", "Male", "No Date");
            Cast.casts.add(cast);
        }
        return cast;
    }


    public void addMovie(String movieTitle,int year,int month, int day,int duration ,String cast1FName,String cast1LName,String cast2FName, String cast2LName,String cast3FName,String cast3LName,String genre1,String genre2,String language,String directorFName,String directorLName,String country,int budget,int revenue,float imdb,int rating ,String description,String posterPath) {
        if (movieTitle == null || movieTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty.");
        }

        int id = Movie.moviesList.getLast().getMovieID()+1;
        Movie addmovie = new Movie(id, movieTitle, null, duration, null, null, language, null, country, budget, revenue, imdb, rating, description,posterPath);



        LocalDate releaseDate = LocalDate.of(year, month, day);
        addmovie.setReleaseDate(releaseDate);


        ArrayList<Cast> castList = new ArrayList<Cast>();

        castList.add(getOrCreateCast(cast1FName, cast1LName));
        castList.add(getOrCreateCast(cast2FName, cast2LName));
        castList.add(getOrCreateCast(cast3FName, cast3LName));

        addmovie.setCast(castList);


        ArrayList<String> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);

        addmovie.setGenres(genres);


        Director director = new Director(directorFName, directorLName);
        addmovie.setDirector(director);


        Movie.moviesList.add(addmovie);
    }



    public void addUser(String username, String password, String firstName, String lastName, String email,String plan,int numOfWatchedMovies,int year,int month,int day) {


        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and email cannot be null or empty.");
        }
        if(User.findUserByUsername(username)!=null){
            throw new IllegalArgumentException( "Username already exists.");
        }
        if (User.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User addUser = new User(0, username, password, firstName, lastName, email, plan);


        Subscription userSubscription = new Subscription(addUser,"No plan",null,numOfWatchedMovies);
        addUser.setId(User.getNextUserId());

        LocalDate startDate = LocalDate.of(year, month, day);
        userSubscription.setStartDate(startDate);

        userSubscription.setPlan(plan);
        addUser.setSubscription(userSubscription);

        double planPrice= userSubscription.planPrice();
        addUser.getSubscription().setPrice(planPrice);
        addUser.setSubscription(userSubscription);

        User.AddUser(addUser);
    }


    public void addCast(String firstname, String lastname, String nationality, String gender, String dateOfBirth){
        Cast cast = new Cast (firstname,lastname,nationality,gender,dateOfBirth);
        Cast.casts.add(cast);
    }




    public void addDirector(String firstName, String lastName, String nationality, String gender, String dateOfBirth){
        Director director = new Director (firstName,lastName,nationality,gender,dateOfBirth);
        Director.directors.add(director);
    }



    public void editUser(int userID, String newUsername,String newUserFName,String newUserLName,String newPassword,String newEmail, String newPlan,Integer numOfWatchedMovies) {
        // Find the user by ID
        User user = null;
        for (User u : User.getUsers()) {
            if (u.getId() == userID) {
                user = u;
                break;
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userID + " does not exist.");
        }

        // Update username if provided
        if (newUsername != null && !newUsername.trim().isEmpty()) {
            if (newUsername.equalsIgnoreCase(user.getUsername()) && User.findUserByUsername(newUsername) != null) {
                throw new IllegalArgumentException("Username " + newUsername + " is already user's username.");
            }
            for (User u : User.getUsers()) {
                if (u.getUsername().equals(newUsername)) {
                    throw new IllegalArgumentException("Username " + newUsername + " already exists.");
                }
            }
            user.setUsername(newUsername);
        }

        if (newUserFName != null && !newUserFName.trim().isEmpty()) {
            user.setFirstName(newUserFName);
        }
        if (newUserLName != null && !newUserLName.trim().isEmpty()) {
            user.setLastName(newUserLName);
        }
        if(newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            user.setEmail(newEmail);
        }
        // Update plan if provided
        if (newPlan != null && !newPlan.trim().isEmpty()) {
            user.getSubscription().setPlan(newPlan);
            double newPlanPrice = user.getSubscription().planPrice();
            user.getSubscription().setPrice(newPlanPrice);
        }

        if (numOfWatchedMovies != null ){
            user.getSubscription().setNumOfWatchedMovies(numOfWatchedMovies);
        }

        for(User u : User.getUsers()) {
            if (u.getId() == userID) {
                int index = User.getUsers().indexOf(u);
                User.getUsers().set(index, user);
            }
        }

    }



    public void editMovie(int movieID, String newMovieTitle, Integer year, Integer month, Integer day,
                          Integer newDuration, String newCast1FName, String newCast1LName,
                          String newCast2FName, String newCast2LName, String newCast3FName,
                          String newCast3LName, String newGenre1, String newGenre2,
                          String newLanguage, String newDirectorFName, String newDirectorLName,
                          String newCountry, Integer newBudget, Integer newRevenue,
                          Float newImdb, Integer newRating, String newDescription,String newPath) {
        // Find the movie by ID
        Movie movie = null;
        for (Movie m : Movie.moviesList) {
            if (m.getMovieID() == movieID) {
                movie = m;
                break;
            }
        }

        if (movie == null) {
            throw new IllegalArgumentException("Movie with ID " + movieID + " does not exist.");
        }

        // Update movie title if provided
        if (newMovieTitle != null && !newMovieTitle.trim().isEmpty()) {
            movie.setMovieTitle(newMovieTitle);
        }

        // Update release date if year, month, and day are provided
        if (year != null && month != null && day != null) {
            LocalDate newReleaseDate = LocalDate.of(year, month, day);
            movie.setReleaseDate(newReleaseDate);
        }

        // Update duration if provided
        if (newDuration != null && newDuration > 0) {
            movie.setDuration(newDuration);
        }

        // Update cast

        ArrayList<Cast> movieNewCasts = new ArrayList<Cast>();
        if (newCast1FName != null && newCast1LName != null) {
            Cast cast1=getOrCreateCast(newCast1FName, newCast1LName);

           movie.getCastList().set(0,cast1);
        }

        if (newCast2FName != null && newCast2LName != null) {
            Cast cast2=getOrCreateCast(newCast2FName, newCast2LName);

            movie.getCastList().set(1,cast2);
        }
        if (newCast3FName != null && newCast3LName != null) {
            Cast cast3=getOrCreateCast(newCast3FName, newCast3LName);

            movie.getCastList().set(2,cast3);
        }


        // Update genres

        if (newGenre1 != null && !newGenre1.trim().isEmpty()) {
            movie.getGenres().set(0, newGenre1);
        }
        if (newGenre2 != null && !newGenre2.trim().isEmpty()) {
            movie.getGenres().set(1, newGenre2);
        }


        // Update language
        if (newLanguage != null && !newLanguage.trim().isEmpty()) {
            movie.setLanguages(newLanguage);
        }

        // Update director
        if (newDirectorFName != null && newDirectorLName != null) {
            Director newDirector = new Director(newDirectorFName, newDirectorLName);
            movie.setDirector(newDirector);
        }

        // Update country
        if (newCountry != null && !newCountry.trim().isEmpty()) {
            movie.setCountry(newCountry);
        }

        // Update budget
        if (newBudget != null && newBudget >= 0) {
            movie.setBudget(newBudget);
        }

        // Update revenue
        if (newRevenue != null && newRevenue >= 0) {
            movie.setRevenue(newRevenue);
        }

        // Update IMDb score
        if (newImdb != null && newImdb >= 0 && newImdb <= 10) {
            movie.setImdbScore(newImdb);
        }

        // Update user rating
        if (newRating != null && newRating >= 0 && newRating <= 10) {
            movie.setUserRating(newRating);
        }

        // Update description
        if (newDescription != null && !newDescription.trim().isEmpty()) {
            movie.setDescription(newDescription);
        }
        if(newPath != null && !newPath.trim().isEmpty()) {
            movie.setPosterPath(newPath);
        }
        for (Movie m : Movie.moviesList) {
            if (m.getMovieID() == movieID) {
                int index = Movie.moviesList.indexOf(m);
                Movie.moviesList.set(index, movie);
            }
        }

    }


    public void editCast(String firstName, String lastName,String newFirstName,String newLastName, String nationality, String gender, String dateOfBirth){
            // Find the cast member by first name and last name
            Cast cast = null;
            if (Cast.search(firstName, lastName) == null) {
                throw new IllegalArgumentException("actor.txt member " + firstName + " " + lastName + " not found.");
            }

            if (Cast.search(firstName, lastName)!=null) {
                cast = Cast.search(firstName, lastName);
            }


            // Update cast details if new values are provided
            if (newFirstName != null && !newFirstName.trim().isEmpty()) {
                cast.setFirstname(newFirstName);
            }

            if (newLastName != null && !newLastName.trim().isEmpty()) {
                cast.setLastname(newLastName);
            }

            if (nationality != null && !nationality.trim().isEmpty()) {
                cast.setNationality(nationality);
            }

            if (gender != null && !gender.trim().isEmpty()) {
                cast.setGender(gender);
            }

            if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
                // Assuming the dateOfBirth is in "yyyy-MM-dd" format
                cast.setDateOfBirth(dateOfBirth);
            }
            for (Cast c : Cast.casts){
              if (c==Cast.search(firstName, lastName)) {
                int index =Cast.casts.indexOf(c);
                  Cast.casts.set(index, cast);
              }
}

    }


    public void editDirector(String firstName,String lastName,String NewFirstName,String NewLastName,String nationality,String gender,String birthDate){
        Director director = new Director(firstName,lastName);
        //String name = firstName+" "+LastName;
        if (Director.Search(firstName,lastName)==null) {
            throw new IllegalArgumentException("Director not found.");
        }
     if (Director.Search(firstName,lastName) != null) {
         director = Director.Search(firstName,lastName);
     }

     if (NewFirstName != null && !NewFirstName.trim().isEmpty()) {
         director.setFirstname(NewFirstName);
     }
     if (NewLastName != null && !NewLastName.trim().isEmpty()) {
         director.setLastname(NewLastName);
     }
     if (nationality != null && !nationality.trim().isEmpty()) {
         director.setNationality(nationality);
     }
     if (gender != null && !gender.trim().isEmpty()) {
         director.setGender(gender);
     }
     if (birthDate != null && !birthDate.trim().isEmpty()) {
         director.setDateOfBirth(birthDate);
     }
     for (Director d : Director.directors){
         if (d.equals(Director.Search(firstName,lastName))) {
            int index= Director.directors.indexOf(d);
            Director.directors.set(index,director);
         }
     }

    }



    public void deleteUser(int userID) {
        boolean userFound = false;
        for (User u : User.getUsers()) {
            if (u.getId() == userID) {
                User.getUsers().remove(u);
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            throw new IllegalArgumentException("User with ID " + userID + " does not exist.");
        }
    }




    public void deleteMovie( int movieID) {
        boolean movieFound = false;
        for (Movie movie : Movie.moviesList) {
            if (movie.getMovieID() == movieID) {
                Movie.moviesList.remove(movie);
                movieFound = true;
                break;
            }
        }
        if (!movieFound) {
            throw new IllegalArgumentException("Movie " + movieID + " not found.");
        }
    }


    public void deleteCast(String firstName, String lastName) {
        if (Cast.search(firstName, lastName) == null) {
            throw new IllegalArgumentException("actor.txt member " + firstName + " " + lastName + " not found.");
        }
        if(Cast.search(firstName, lastName)!=null) {
            Cast.casts.remove(Cast.search(firstName, lastName));
        }
    }


    public void deleteDirector(String firstName, String lastName) {

        Director directorToDelete = Director.Search(firstName, lastName);

        if (directorToDelete != null) {
            Director.delete(firstName, lastName);
            System.out.println("Director " + firstName + " " + lastName + " has been deleted.");
        }
       if(directorToDelete == null) {
           throw new IllegalArgumentException("Director " + firstName + " " + lastName + " not found.");
       }
    }


    public void deleteMovieFromUserWatchRecords(int userId, String movieName) {
        boolean recordFound = false;

        for (UserWatchRecord record : UserWatchRecord.userWatchRecords) {
            if (record.getUser().getId() == userId && record.getMovie().getMovieTitle().equalsIgnoreCase(movieName)) {
                UserWatchRecord.userWatchRecords.remove(record);
                recordFound = true;
                record.getUser().getSubscription().setNumOfWatchedMovies(record.getUser().getSubscription().getNumOfWatchedMovies()-1);
                break;
            }
        }

        if (!recordFound) {
            throw new IllegalArgumentException("No watch record found for user ID " + userId + " and movie name '" + movieName + "'.");
        }
    }


    public void addMovieToUserWatchRecords(int userId, String movieName, int rating, int year, int month, int day) {
        User user = null;
        Movie movie = null;

        // Find the user by ID
        for (User u : User.getUsers()) {
            if (u.getId() == userId) {
                user = u;
                break;
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        // Find the movie by title
        for (Movie m : Movie.moviesList) {
            if (movieName.equalsIgnoreCase(m.getMovieTitle())) {
                movie = m;
                break;
            }
        }

        if (movie == null) {
            throw new IllegalArgumentException("Movie with name '" + movieName + "' does not exist.");
        }

        // Create and populate the UserWatchRecord
        UserWatchRecord addRecord = new UserWatchRecord();
        addRecord.setUser(user);
        addRecord.setMovie(movie);
        addRecord.setRating(rating);

        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Validate and set the watch date
        try {
            LocalDate watchTime = LocalDate.of(year, month, day);
            addRecord.setDateOfWatching(watchTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date provided: " + year + "-" + month + "-" + day, e);
        }

        // Update user's watched movies count
        user.getSubscription().setNumOfWatchedMovies(user.getSubscription().getNumOfWatchedMovies() + 1);

        // Add the record to the watch records list
        UserWatchRecord.userWatchRecords.add(addRecord);

    }


    public void editMovieWatchRecords(int userId, String movieName, String newMovieName, Integer rating, Integer year, Integer month, Integer day) {
        boolean recordFound = false;

        for (UserWatchRecord record : UserWatchRecord.userWatchRecords) {
            if (record.getUser().getId() == userId && record.getMovie().getMovieTitle().equalsIgnoreCase(movieName)) {
                recordFound = true;

                try {
                    // Handle new movie name
                    if (newMovieName != null && !newMovieName.trim().isEmpty()) {
                        boolean movieExists = false;
                        for (Movie m : Movie.moviesList) {
                            if (m.getMovieTitle().equalsIgnoreCase(newMovieName)) {
                                record.setMovie(m);
                                movieExists = true;
                                break;
                            }
                        }
                        if (!movieExists) {
                            throw new IllegalArgumentException("Movie with name '" + newMovieName + "' does not exist in the movie list.");
                        }
                    }

                    // Handle rating
                    if (rating != null) {
                        if (rating < 1 || rating > 5) {
                            throw new IllegalArgumentException("Rating must be between 1 and 5.");
                        }
                        record.setRating(rating);
                    }

                    // Handle date of watching
                    if (year != null && month != null && day != null) {
                        try {
                            record.setDateOfWatching(LocalDate.of(year, month, day));
                        } catch (DateTimeException e) {
                            throw new IllegalArgumentException("Invalid date provided: " + year + "-" + month + "-" + day, e);
                        }
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        if (!recordFound) {
            throw new IllegalArgumentException("No watch record found for user ID " + userId + " and movie name '" + movieName + "'.");
        }

    }


    public static void updateMoviesFile(String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Movie movie : Movie.moviesList) {
                StringBuilder line = new StringBuilder();

                // Writing movie details in the expected format
                line.append(movie.getMovieID()).append(",");
                line.append(movie.getMovieTitle()).append(",");
                line.append(movie.getReleaseDate().format(DateTimeFormatter.ofPattern("d-M-yyyy"))).append(",");
                line.append(movie.getDuration()).append(",");

                // Serialize cast
                for (int i = 0; i < movie.getCast().size(); i++) {
                    line.append(movie.getCast().get(i));
                    if (i < movie.getCast().size() - 1) {
                        line.append(",");
                    }
                }

                // Serialize genres
                for (String genre : movie.getGenres()) {
                    line.append(",").append(genre);
                }

                line.append(",").append(movie.getLanguages());
                line.append(",").append(movie.getDirector());
                line.append(",").append(movie.getCountry());
                line.append(",").append(movie.getBudget());
                line.append(",").append(movie.getRevenue());
                line.append(",").append(movie.getImdbScore());
                line.append(",").append(movie.getUserRating());
                line.append(",").append(movie.getDescription());
                line.append(",").append(movie.getPosterPath());

                // Write the line to the file
                bufferedWriter.write(line.toString());
                bufferedWriter.newLine();
            }
            System.out.println("Movies saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

 public static String mostSubscribedPlan() {
        int basicCount = 0;
        int standardCount = 0;
        int premiumCount = 0;
        for (User user : User.getUsers()) {
                if (user.getSubscription().getPlan().equalsIgnoreCase("basic")) {
                    basicCount++;
                } else if (user.getSubscription().getPlan().equalsIgnoreCase("standard")) {
                    standardCount++;
                } else if (user.getSubscription().getPlan().equalsIgnoreCase("premium")) {
                    premiumCount++;
                } else if (user.getSubscription().getPlan().equalsIgnoreCase("No subscription")) {
                    continue;
                }

        }
        if (basicCount == standardCount && basicCount == premiumCount) {
            return "premium ,standard and basic";
        } else if (basicCount > standardCount && basicCount > premiumCount) {
            return "basic";
        } else if (standardCount > basicCount && standardCount > premiumCount) {
            return "standard";
        } else if (premiumCount > basicCount && premiumCount > standardCount) {
            return "premium";
        } else if (premiumCount == standardCount) {
            return "standard and premium";
        } else if (standardCount == basicCount) {
            return "basic and standard";
        } else if (basicCount == premiumCount) {
            return "premium and basic";
        }
        return "No plans subscribed";
    }


    public static String monthWithHighestRevenue() {
        double [] monthRevenue = new double[12];
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        for (User user  : User.getUsers() ) {

            for (int i = 1; i <= 12; i++) {
                if (user.getSubscription().getStartDate().getMonthValue() == i) {
                    monthRevenue[user.getSubscription().getStartDate().getMonthValue()-1] += user.getSubscription().planPrice();
               break;
                }
                else
                    continue;
            }
        }

        int highestRevenueIndex = 0;
        int highestRevenue =(int) monthRevenue[0];
        for (int i = 1; i < monthRevenue.length; i++) {
            if (monthRevenue[i] > highestRevenue) {
                highestRevenue = (int)monthRevenue[i];
                highestRevenueIndex = i;
            }

        }
        return  monthNames[highestRevenueIndex];
    }


   }



