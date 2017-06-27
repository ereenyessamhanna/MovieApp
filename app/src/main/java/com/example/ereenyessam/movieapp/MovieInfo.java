package com.example.ereenyessam.movieapp;



public class MovieInfo   {
   public MovieInfo()
    {

    }

   private String imageurl;
   private String Overview;
   private String date;
   private String Tittle;
   private double average;
   private int Id;

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }
}
