package com.project.watchit;

import com.project.watchit.Moviee.Cast;
import com.project.watchit.Moviee.Director;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class Movie {
    public static ArrayList<Movie> moviesList = new ArrayList<>();
    private int movieID;
    private String movieTitle;
    private LocalDate releaseDate;
    private int duration;
    private ArrayList<Cast> cast;
    private ArrayList<String> genres;
    private String languages;
    private Director director;
    private String country;
    private float budget;
    private float revenue;
    private float imdbScore;
    private int userRating = 0;
    private double avarageRating = 0.0;
    private String description;
    private String posterPath;

    // Constructor
    public Movie(int movieID, String movieTitle, LocalDate releaseDate, int duration,
                 ArrayList<Cast> cast, ArrayList<String> genres, String languages, Director director,
                 String country, float budget, float revenue,
                 float imdbScore, double avarageRating, String description, String posterPath) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.cast = cast;
        this.genres = genres;
        this.languages = languages;
        this.director = director;
        this.country = country;
        this.budget = budget;
        this.revenue = revenue;
        this.imdbScore = imdbScore;
        this.avarageRating = avarageRating;
        this.description = description;
        this.posterPath = posterPath;
    }

    public Movie(int movieID, String movieTitle, float imdbScore) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.imdbScore = imdbScore;
    }

    public Movie() {
    }

    //getters and setters
    public int getMovieID() {
        return movieID;
    }
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getCast() {
        return cast.stream().map(c -> c.getFirstname() + " " + c.getLastname()).toList();
    }
    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Cast> getCastList() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getLanguages() {
        return languages;
    }
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getDirector() {
        return director != null ? director.getName() : "Unknown Director";
    }
    public void setDirector(Director director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getRevenue() {
        return revenue;
    }
    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getImdbScore() {
        return imdbScore;
    }
    public void setImdbScore(float imdbScore) {
        this.imdbScore = imdbScore;
    }

    public int getUserRating() {
        return userRating;
    }
    public void setUserRating(int userRating) {
        if (userRating >= 1 && userRating <= 5) {
            this.userRating = userRating;
        } else {
            System.out.println("Rating must be between 1 and 5.");
        }
    }

    public double getAvarageRating() {
        return avarageRating;
    }
    public void setAvarageRating(double avarageRating) {
        this.avarageRating = avarageRating;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public static ArrayList<Movie> getMoviesList() {
        return moviesList;
    }
    public static void setMoviesList(ArrayList<Movie> moviesList) {
        Movie.moviesList = moviesList;
    }

    //load movies from a file
    public static void loadMoviesFromFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                //check the data
                //System.out.println(line + "\n");

                try {
                    int movieID = Integer.parseInt(data[0].trim());
                    String movieTitle = data[1].trim();
                    LocalDate releaseDate = LocalDate.parse(data[2].trim(), DateTimeFormatter.ofPattern("d-M-yyyy"));
                    int duration = Integer.parseInt(data[3].trim());


                    ArrayList<Cast> cast = new ArrayList<>();
                    for (int i = 4; i < 7; i++) {
                        String fullName = data[i].trim();
                        String[] nameParts = fullName.split(" ");
                        String firstName = nameParts[0];
                        String lastName = nameParts[1];
                        Cast actor = new Cast(firstName, lastName);
                        cast.add(actor);
                    }

                    ArrayList<String> genres = new ArrayList<>();
                    genres.add(data[7].trim());
                    genres.add(data[8].trim());

                    String languages = data[9].trim();

                    String fullName = data[10].trim();
                    String[] nameParts = fullName.split(" ");
                    String firstName = nameParts[0];
                    String lastName = nameParts[1];
                    Director director = new Director(firstName, lastName);

                    String country = data[11].trim();
                    float budget = Float.parseFloat(data[12].trim());
                    float revenue = Float.parseFloat(data[13].trim());
                    float imdbScore = Float.parseFloat(data[14].trim());
                    double avarageRating = Double.parseDouble(data[15].trim());
                    String description = data[16].trim();
                    String posterPath = data[17].trim();

                    Movie movie = new Movie(movieID, movieTitle, releaseDate, duration, cast, genres, languages,
                            director, country, budget, revenue, imdbScore, avarageRating, description, posterPath);

                    moviesList.add(movie);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error processing line: " + line);
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    //display all movies' IDs and titles\poster if GUI
    public static void displayAllMovies() {
        for (Movie movie : moviesList) {
            System.out.println("ID: " + movie.getMovieID() + ", Title: " + movie.getMovieTitle());
        }
    }
    //topRated movies based on imdbScore
    public static void Top5Movies() {
        ArrayList<Movie> sortedMovies = new ArrayList<>(moviesList);
        sortedMovies.sort(Comparator.comparingDouble(Movie::getImdbScore).reversed());
        int numberOfMoviesToDisplay = Math.min(5, sortedMovies.size());
        for (int i = 0; i < numberOfMoviesToDisplay; i++) {
            Movie movie = sortedMovies.get(i);
            System.out.println("ID: " + movie.getMovieID() + ", Name: " + movie.getMovieTitle() + ", IMDb Score: " + movie.getImdbScore());
        }
    }
    //recent movies
    public static ArrayList<Movie> recent5Movie() {
        ArrayList<Movie> sortedMovies = new ArrayList<>(moviesList);
        sortedMovies.sort(Comparator.comparing(Movie::getReleaseDate).reversed());
        int numberOfMovies = Math.min(5, sortedMovies.size());
        ArrayList<Movie> topRecentMovies = new ArrayList<>(sortedMovies.subList(0, numberOfMovies));
        for (int i = 0; i < numberOfMovies; i++) {
            Movie movie = topRecentMovies.get(i);
            System.out.println("ID: " + movie.getMovieID() + ", Name: " + movie.getMovieTitle() + ", Release date: " + movie.getReleaseDate());
        }
        return topRecentMovies;
    }
    //Search movieTitle
    public static void searchMovieByTitle(String movieTitle) {
        for (Movie movie : moviesList) {
            if (movie.getMovieTitle().equalsIgnoreCase(movieTitle)) {
                System.out.println(movie.toString());
                return;
            }
        }
        System.out.println(movieTitle + " not found.");
    }
    //Search movieID
    public static void searchMovieByID(int id) {
        for (Movie movie : moviesList) {
            if (movie.getMovieID() == id) {
                System.out.println(movie.toString());
                return;
            }
        }
        System.out.println("Movie with ID " + id + " not found.");
    }
    //search by genres
    public static void searchMovieByGenre(String genre) {
        ArrayList<Movie> matchgenre = new ArrayList<>();
        for (Movie movie : moviesList) {
            if (movie.getGenres().stream().anyMatch(g -> g.trim().equalsIgnoreCase(genre.trim()))) {
                matchgenre.add(movie);
            }
        }

        if (!matchgenre.isEmpty()) {
            for (Movie moviee : matchgenre) {
                System.out.println("ID: " + moviee.getMovieID() + " Title: " + moviee.getMovieTitle());
            }
        } else
            System.out.println("This genre is not available");
    }
    //filter based on language
    public static void filterMoviesByLanguage(String language) {
        boolean found = false;
        for (Movie movie : moviesList) {
            if (movie.languages != null && movie.languages.equalsIgnoreCase(language.trim())) {
                System.out.println("ID: " + movie.movieID + " Title: " + movie.movieTitle);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No movies available in this language: " + language);
        }
    }
    //update rating
    public static void updateMovieRatings(List<UserWatchRecord> userWatchRecords) {
        for (Movie movie : moviesList) {
            int totalRating = 0;
            int ratingCount = 0;

            for (UserWatchRecord record : userWatchRecords) {
                if (record.getMovie().equals(movie) && record.getRating() > 0) {
                    totalRating += record.getRating();
                    ratingCount++;
                }
            }

            if (ratingCount > 0) {
                double newRating = (double) totalRating / ratingCount;
                movie.setAvarageRating(newRating);
            } else {
                movie.setAvarageRating(0);
            }
        }
    }

    //movieDetails
    @Override
    public String toString() {
        return "Movie Details:\n" + "ID: " + movieID + "\n" +
                "Title: " + movieTitle + "\n" +
                "Release Date: " + releaseDate + "\n" +
                "Duration: " + duration + " minutes\n" +
                "actor.txt: " + getCast() + "\n" +
                "Genres: " + genres + "\n" +
                "Language: " + languages + "\n" +
                "Director: " + director.getName() + "\n" +
                "Country: " + country + "\n" +
                "Budget: $" + (budget / 1000000) + " M\n" +
                "Revenue: $" + (revenue / 1000000) + " M\n" +
                "IMDb Score: " + imdbScore + "\n" +
                "AVG User Rating: " + avarageRating + "\n" +
                "Description: " + description + ".\n";

    }

}

