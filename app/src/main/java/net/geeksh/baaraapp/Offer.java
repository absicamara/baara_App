package net.geeksh.baaraapp;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Offer  {

    DatabaseReference databaseRef;
    FirebaseDatabase firebaseDatabase;



    public static int id = 0;
    public int offerId;
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
    public long created_at;




    public Offer() {

    }

    public Offer(int offerId, String jobTitle, String requirement, String city, String country, String startDate, String endDate, String description) {
        this.jobTitle = jobTitle;
        this.requirement = requirement;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
//        created_at = getTimeStamp();
        this.offerId = ++id;

    }

    public Offer(String jobTitle, String requirement, String city, String country, String startDate, String endDate, String description) {
        this.jobTitle = jobTitle;
        this.requirement = requirement;
        this.city = city;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
//        created_at = getTimeStamp();
        this.offerId = ++id;

    }


    public  String getOfferId(){
        String r;
        return r =  Integer.toString(this.offerId);

    }

    public long getTimeStamp(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        long r = Long.parseLong(timeStamp);
        return r;
    }



}
