package com.project.watchit;

public class RecommendedBy {

  private     String name ;
  private    int count ;

    public RecommendedBy( String name) {
        this.count = 1;
        this.name = name;
    }
    public RecommendedBy()
    {
        this.count=0;
    }
    public  void incrementCount ()
    {
        this.count++;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
