package com.project.watchit;
import com.project.watchit.person_app.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Subscription {

    private User user;
    private String plan;
    private double price;
    private LocalDate startDate;
    private int numOfWatchedMovies;
    private boolean subscriptionNotValid = false;

    public static final int basicLimit = 5;
    public static final int standardLimit = 10;
    public static final int premiumLimit = 30;


    public Subscription()
{}
    public Subscription(User user, String plan) {
        this.user = user;
        this.plan = plan;
        this.price = planPrice();
        this.startDate = LocalDate.now();
        this.numOfWatchedMovies = 0;

    }

    public Subscription(User user, String plan, LocalDate startDate, int numOfWatchedMovies) {
        this.user = user;
        this.plan = plan;
        this.price = planPrice();
        this.startDate = startDate;
        this.numOfWatchedMovies = numOfWatchedMovies;

    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNumOfWatchedMovies() {
        return numOfWatchedMovies;
    }

    public void setNumOfWatchedMovies(int numOfWatchedMovies) {
        this.numOfWatchedMovies = numOfWatchedMovies;
    }

    public int planLimit() {
        if (plan.equalsIgnoreCase("Basic")) {
            return basicLimit;
        } else if (plan.equalsIgnoreCase("Standard")) {
            return standardLimit;
        } else if (plan.equalsIgnoreCase("Premium")) {
            return premiumLimit;
        } else {
            return -1;
        }
    }

    public double planPrice() {
        if (plan.equalsIgnoreCase("Basic")) {
            return 20;
        } else if (plan.equalsIgnoreCase("Standard")) {
            return 35;
        } else if (plan.equalsIgnoreCase("Premium")) {
            return 50;
        } else {
            return -1.0;
        }
    }

    public boolean isSubscriptionActive() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = startDate.plusDays(30);
        return (currentDate.isBefore(expirationDate));
    }
    public boolean isSubscriptionNotValid() {
        // Check if the subscription has expired
        if (!isSubscriptionActive()) {
            return true; // Subscription is expired
        }

        if (numOfWatchedMovies >= planLimit()) {
            return true; // Subscription is invalid
        }

        return false; // Subscription is valid
    }


    public void addMovie(Movie movie) {
        if (isSubscriptionActive()) {
            numOfWatchedMovies++;
            UserWatchRecord record=new UserWatchRecord(user,movie);
            UserWatchRecord.getUserWatchRecords().add(record);
            if (numOfWatchedMovies >= planLimit()) {
                subscriptionNotValid = true;
            }

        }
    }

    public void renewSubscription(String plan) {
        this.plan = plan;
        this.price = planPrice();
        this.startDate = LocalDate.now();
        this.numOfWatchedMovies = 0;
        this.subscriptionNotValid = false;
        System.out.println("You renewed your subscription successfully.Your new plan is " + plan);
    }
}
