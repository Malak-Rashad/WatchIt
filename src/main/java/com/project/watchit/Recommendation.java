package com.project.watchit;

import com.project.watchit.RecommendedBy;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {

    RecommendedBy mostFrequent = new RecommendedBy();
    List<Movie> recommendedMovies;


    //update the count of a genre in list countGenres
    /*
    if the genre passed exist already in the list just increment the counter
    return -> to exit the method immediately   without return after incrementing the count of the genre it will
    make a new object with the same genre name and a new count initialized with 1
    if it doesn't exist create a new object of RecommendedBy (constructor count initialized to one )
    [Action , 1]
    */

    private static void updateGenreCounter(List<RecommendedBy> genreCounts, String genre) {
        for (RecommendedBy movieGenre : genreCounts) {
            if (movieGenre.getName().equals(genre)) {
                movieGenre.incrementCount();
                return;
            }
         //   System.out.println(movieGenre.name + movieGenre.count);
        }


        //if the genre doesn't exist in the list create a new RecommendedBy object and add it to the list genreCounts

        genreCounts.add(new RecommendedBy(genre));
    }


    //      for cast recommendation make list contains name , occurrencesCount to the actor to know favorite cast

    private static void updateCastCounter(List<RecommendedBy> ActorCounts, String actorName) {
        for (RecommendedBy Actor : ActorCounts) {

            // to handle case sensitivity and trim spaces [ actorName = "John " , Actor.name = " john"  fails as trim , case sensitivity ]

            if (Actor.getName().equalsIgnoreCase(actorName.trim())) {
                Actor.incrementCount();
                return;
            }
        }

        //if the Actor doesn't exist in the list create a new RecommendedBy object and add it to the list ActorCounts

        ActorCounts.add(new RecommendedBy(actorName));
    }


    public List<Movie> RecommendByCast_Genre(int userID) {
        List<RecommendedBy> genreCounts = new ArrayList<>();
        List<RecommendedBy> ActorCounts = new ArrayList<>();


        boolean recordFound = false;
        for (UserWatchRecord record : UserWatchRecord.getUserWatchRecords()) {
            if (record.getUser().getId() == userID) {
                recordFound = true;
                for (String genre : record.getMovie().getGenres()) {
                    updateGenreCounter(genreCounts, genre);

                }
                for (String actorName : record.getMovie().getCast()) {

                    updateCastCounter(ActorCounts, actorName);
                }
            }
        }
        if (!recordFound) {
            System.out.println("No Watch record for the user to recommend based on it\n");
            return DefaultRecommendations();
        }

        RecommendedBy mostFrequentGenre = findMostFrequent(genreCounts);
        RecommendedBy mostFrequentActor = findMostFrequent(ActorCounts);


if(mostFrequentGenre!=null || mostFrequentActor != null)
{
    System.out.println("      ** Recommendations By Genre and/or Cast : **    ");
    return RecommendBasedOnCriteria(userID,mostFrequentGenre,mostFrequentActor);
}else
{
    System.out.println("No recommendations found ");
    return DefaultRecommendations();
}

    }

    private List<Movie> DefaultRecommendations() {
       return Movie.recent5Movie();

    }

    private  RecommendedBy findMostFrequent(List<RecommendedBy> counts )
{
    mostFrequent = null ;
    boolean tie = false ;
    for (RecommendedBy item : counts)
    {
        if(mostFrequent==null || item.getCount()> mostFrequent.getCount())
        {
            mostFrequent= item ;
            tie = false;
        }else if (item.getCount() == mostFrequent.getCount())
        {
            tie = true;
        }
    }
    return tie ? null: mostFrequent;
}

   private List<Movie> RecommendBasedOnCriteria(int userID, RecommendedBy genre , RecommendedBy cast)
    {
        recommendedMovies = new ArrayList<>();
for(Movie movie: Movie.getMoviesList())
{
   boolean matchesGenre = genre !=null && movie.getGenres().contains(genre.getName());
    boolean matchesCast = cast !=null && movie.getCast().contains(cast.getName());

    if(matchesGenre||matchesCast)
    {
        boolean alreadyWatched = false ;
        for (UserWatchRecord record : UserWatchRecord.getUserWatchRecords()) {
            if (record.getUser().getId() == userID) {
                if (record.getMovie().getMovieTitle().trim().equalsIgnoreCase(movie.getMovieTitle().trim())) {
                    alreadyWatched = true;
                    break;
                }
            }
        }

        if (!alreadyWatched) {
            if(matchesCast && matchesGenre)
            {
                recommendedMovies.add(0,movie);
            }
        recommendedMovies.add(movie);
        }
    }
}

// display recommended movies

        if (recommendedMovies.isEmpty())
        {
            System.out.println("No new Recommendations found ");
            return null;
        }
        for (Movie movie : recommendedMovies)
        {
            System.out.println("Recommended : "+ movie.getMovieTitle());
        }
        return recommendedMovies;

    }
}
