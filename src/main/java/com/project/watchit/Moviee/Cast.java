package com.project.watchit.Moviee;

import java.io.*;
import java.util.ArrayList;

public class Cast extends Person {
   public static ArrayList<Cast> casts= new ArrayList<Cast>();

    public Cast(String firstname, String lastname, String nationality, String gender, String dateOfBirth) {
        super(firstname, lastname, nationality, gender, dateOfBirth);

    }
    public Cast(String firstName, String lastName) {
        super(firstName, lastName);
    }


    public static void readFile(String filePath ){
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                String[] data=line.split(",");
                String firstname=data[0];
                String lastname=data[1];
                String nationality =data[2];
                String gender=data[3];
                String dateOfBirth=data[4];
                ArrayList<String>movies=new ArrayList<>();

                for(int i=5;i<data.length;i++)
                {
                    movies.add(data[i]);
                }

                Cast cast=new Cast(firstname,lastname,nationality,gender,dateOfBirth);
                cast.setMovies(movies);
                casts.add(cast);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File"+e.getMessage());
        }

    }

    @Override
    public String toString() {
        String movieString=String.join(",",movies);
        return getFirstname()+","+getLastname()+","+getNationality()+","+getGender()+","+getDateOfBirth()+","+movieString;
    }

    public static void WriteCastToFile(String filePath)
    {
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(filePath)))
        {
            for (Cast cast:casts){
                bufferedWriter.write(cast.toString());
                bufferedWriter.newLine();

            }


        }
        catch (IOException e)
        {
            System.out.println("Error Writing File"+e.getMessage());
        }
    }

    //overriding add method
    @Override
    public void add(String firstname, String lastname, String dateOfBirth, String nationality, String gender, ArrayList<String> movie) {
        Cast cast=new Cast( firstname,lastname,nationality,gender,dateOfBirth);
        cast.setFirstname(firstname);
        cast.setLastname(lastname);
        cast.setDateOfBirth(dateOfBirth);
        cast.setGender(gender);
        cast.setNationality(nationality);
        cast.setMovies(movie);
        casts.add(cast);
    }
//overriding update method

    public void update(int index, String movie) {
        casts.get(index).movies.add(movie);

    }
    //delete method
    public void delete(String firstname, String lastname)
    {

        for (Cast cast:casts)
        {
            if (cast.getFirstname().equalsIgnoreCase(firstname)&&cast.getLastname().equalsIgnoreCase(lastname))
            {
                casts.remove(cast);
                break;
            }
        }

    }
    //search method
    public static Cast search(String firstname,String lastname)
    {
        for (Cast c:casts)
        {
            if(c.getFirstname().equalsIgnoreCase(firstname)&&c.getLastname().equalsIgnoreCase(lastname))
            {
                return c;
            }
        }
        return null;

    }

}
