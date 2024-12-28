package com.project.watchit;

import com.project.watchit.person_app.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserWatchRecord {

    private User user;
    private Movie movie;
    private int rating;
    private LocalDate dateOfWatching;

    public static List<UserWatchRecord> userWatchRecords = new ArrayList<>();

    public UserWatchRecord() {
    }

    public UserWatchRecord(User user, Movie movie, int rating, LocalDate dateOfWatching) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.dateOfWatching = dateOfWatching;
    }

    public UserWatchRecord(User user, Movie movie, int rating) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.dateOfWatching = LocalDate.now();
    }

    public UserWatchRecord(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        this.rating = -1;
        this.dateOfWatching = LocalDate.now();
    }

    public static List<UserWatchRecord> getUserWatchRecords() {
        return userWatchRecords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getDateOfWatching() {
        return dateOfWatching;
    }

    public void setDateOfWatching(LocalDate dateOfWatching) {
        this.dateOfWatching = dateOfWatching;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            System.out.println("Please provide a rating between 1 and 5.");
        }
    }


    public static List<UserWatchRecord> loadWatchRecords(List<User> users, List<Movie> movies) {
        try (BufferedReader reader = new BufferedReader(new FileReader("watched_movies.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    int userId = Integer.parseInt(parts[0].trim());
                    User user = null;


                    for (User u : users) {
                        if (u.getId() == userId) {
                            user = u;
                            break;
                        }
                    }

                    if (user != null) {

                        for (int i = 1; i < parts.length; i += 3) {
                            int movieTitleIndex = i;
                            int ratingIndex = i + 1;
                            int dateOfWatchingIndex = i + 2;


                            String movieTitle = parts[movieTitleIndex].trim();
                            int rating = parts[ratingIndex].equals("-1") ? -1 : Integer.parseInt(parts[ratingIndex].trim());
                            LocalDate dateOfWatching = LocalDate.parse(parts[dateOfWatchingIndex].trim());
                            Movie movie = null;
                            for (Movie m : movies) {
                                if (m.getMovieTitle().equalsIgnoreCase(movieTitle)) {
                                    movie = m;
                                    break;
                                }
                            }

                            if (movie != null) {

                                UserWatchRecord record = new UserWatchRecord(user, movie, rating, dateOfWatching);
                                userWatchRecords.add(record);
                            }

                            else{
                                System.out.println("User ID " + userId + " not found in the users list.");
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading from file" + e.getMessage());
        }
        return userWatchRecords;
    }

    public void saveWatchRecords() {
        List<String> allRecords = new ArrayList<>();
        List<Integer> seenUsers = new ArrayList<>();

        for (UserWatchRecord record : userWatchRecords) {
            int userId = record.getUser().getId();

            if (seenUsers.contains(userId)) {
                continue;
            }

            seenUsers.add(userId);
            String line = userId + "";

            for (UserWatchRecord userRecord : userWatchRecords) {
                if (userRecord.getUser().getId() == userId) {
                    line += "," + userRecord.getMovie().getMovieTitle()
                            + "," + userRecord.getRating()
                            + "," + userRecord.getDateOfWatching();
                }
            }

            allRecords.add(line);
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("watched_movies.txt"))) {
            for (String record : allRecords) {
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save to file: " + e.getMessage());
        }
    }

    public List<String> displayWatchedMovies(User user) {
        List<String> watchedMovies = new ArrayList<>();

        for (UserWatchRecord record : userWatchRecords) {
            if (record.getUser().equals(user)) {
                watchedMovies.add(record.getMovie().getMovieTitle());
            }
        }
        System.out.println(watchedMovies);
        return watchedMovies;
    }

    public List<String> topWatchedMovies(int N) {
        List<Movie> sortedMovies = new ArrayList<>(Movie.moviesList);
        int[] watchCounts = new int[sortedMovies.size()];


        for (UserWatchRecord record : userWatchRecords) {
            for (int i = 0; i < sortedMovies.size(); i++) {
                if (record.getMovie().getMovieTitle().equals(sortedMovies.get(i).getMovieTitle())) {
                    watchCounts[i]++;
                    break;
                }
            }
        }

        // in descending order
        for (int i = 0; i < watchCounts.length - 1; i++) {
            for (int j = 0; j < watchCounts.length - i - 1; j++) {
                if (watchCounts[j] < watchCounts[j + 1]) {

                    int tempCount = watchCounts[j];
                    watchCounts[j] = watchCounts[j + 1];
                    watchCounts[j + 1] = tempCount;


                    Movie tempMovie = sortedMovies.get(j);
                    sortedMovies.set(j, sortedMovies.get(j + 1));
                    sortedMovies.set(j + 1, tempMovie);
                }
            }
        }

        List<String> topMovies = new ArrayList<>();
        for (int i = 0; i < sortedMovies.size() && i < N; i++) {
            if (watchCounts[i] > 0) {
                String top = sortedMovies.get(i).getMovieTitle() ;
                topMovies.add(top);
                System.out.println(top);
            }
        }

        return topMovies;
    }


}


