package net.geeksh.baaraapp;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Offer  {

    DatabaseReference databaseRef;
    FirebaseDatabase firebaseDatabase;



    public static int offerId = 0;
    public String jobTitle;
    public String company;
    public String requirement;
    public String city;
    public String country;
    public String startDate;
    public String endDate;
    public String description;
    public double latitude;
    public double longitude;
    public boolean expired;


    public Offer(String jobTitle, String requirement, String city, String country, String startDate, String endDate, String description) {
        this.jobTitle = jobTitle;
        this.requirement = requirement;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        offerId++;
    }


    public static String getOfferId(){
        String id =  Integer.toString(offerId);
        return id;
    }



}
