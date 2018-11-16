package net.geeksh.baaraapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyofferActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    Button buttonCreateOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffer);

        buttonCreateOffer = (Button) findViewById(R.id.buttonCreateOffer);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        getMyOffers();

        String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
//        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getMyOffers());
//        ListView buckysListView = (ListView) findViewById(R.id.myOfferListView);
//        buckysListView.setAdapter(buckysAdapter);
//
//        buckysListView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String food = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(getApplicationContext(), food, Toast.LENGTH_LONG).show();
//                    }
//                }
//        );



        buttonCreateOffer.setOnClickListener(this);
    }

    public void getMyOffers(){

        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid()+"/Offer/");

        Query query = databaseReference.orderByChild("created_at");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Offer> offer = new ArrayList<>();
//                Offer offer = new Offer();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    offer.add(new Offer(
                            ds.child("jobTitle").getValue().toString(),
                            ds.child("requirement").getValue().toString(),
                            ds.child("city").getValue().toString(),
                            ds.child("country").getValue().toString(),
                            ds.child("startDate").getValue().toString(),
                            ds.child("endDate").getValue().toString(),
                            ds.child("description").getValue().toString()
                            ));


                }
//                    Toast.makeText(MyofferActivity.this, offer.jobTitle, Toast.LENGTH_SHORT).show();
//                final ArrayList<String> arr2 = new ArrayList();
//                for ( Offer o : offer
//                     ) {
//                    arr2.add(o.jobTitle +"@"+ o.description);
////                    arr2.add(o.description);
////                    arr2.add(o.requirement);
//                }


                ListAdapter buckysAdapter = new CustomViewAdapter(MyofferActivity.this, R.layout.custom_listview, offer);
                ListView buckysListView = (ListView) findViewById(R.id.myOfferListView);
                buckysListView.setAdapter(buckysAdapter);

                buckysListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String food = String.valueOf(parent.getItemAtPosition(position));
                                String ff = offer.get((int)id).requirement;
                                Toast.makeText(getApplicationContext(), ff, Toast.LENGTH_LONG).show();
                            }
                        }
                );





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onClick(View view) {

        if(view == buttonCreateOffer){
            startActivity(new Intent(getApplicationContext(), CreateofferActivity.class));
        }
    }
}
