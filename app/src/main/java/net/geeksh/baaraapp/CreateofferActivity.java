package net.geeksh.baaraapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateofferActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    EditText editTextJobTitle, editTextjobDescription, editTextjobRequirement, editTextStartDate, editTextEndDate, editTextCity, editTextCountry;

    Button buttonCreateOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createoffer);

        editTextJobTitle = (EditText) findViewById(R.id.editTextJobTitle);
        editTextjobDescription = (EditText) findViewById(R.id.editTextJobDescription);
        editTextjobRequirement = (EditText) findViewById(R.id.editTextJobRequirement);
        editTextStartDate = (EditText) findViewById(R.id.editTextofferStartDate);
        editTextEndDate = (EditText) findViewById(R.id.editTextofferEndDate);
        editTextCity = (EditText) findViewById(R.id.editTextOfferCity);
        editTextCountry = (EditText) findViewById(R.id.editTextOfferCountry);
        buttonCreateOffer = (Button) findViewById(R.id.buttonCreateOffer2);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        buttonCreateOffer.setOnClickListener(this);


//        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createOffer(){

        String jobTitle = editTextJobTitle.getText().toString().trim();
        String desc = editTextjobDescription.getText().toString().trim();
        String req = editTextjobRequirement.getText().toString().trim();
        String startDate = editTextStartDate.getText().toString().trim();
        String endDate = editTextEndDate.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();

        Offer offer = new Offer(jobTitle, req,city,country,startDate,endDate,desc);
        user = firebaseAuth.getCurrentUser();

//        databaseReference = databaseReference.getRoot();
        databaseReference.child(user.getUid()+"/Offer/"+Offer.getOfferId()).setValue(offer);
            startActivity(new Intent(getApplicationContext(), MyofferActivity.class));
          Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {

        if (view == buttonCreateOffer){

            createOffer();
        }
    }
}
