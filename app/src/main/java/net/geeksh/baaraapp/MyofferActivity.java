package net.geeksh.baaraapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView imgViewOfferOpen;
    android.app.AlertDialog alertDialog;

    Button buttonCreateOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffer);

        buttonCreateOffer = (Button) findViewById(R.id.buttonCreateOffer);
        imgViewOfferOpen = (ImageView) findViewById(R.id.imgViewOfferOpen);


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

        final Query query = databaseReference.orderByChild("timeStamp");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Offer> offer = new ArrayList<>();
//                Offer offer = new Offer();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    offer.add(new Offer(
                            Integer.parseInt(ds.child("offerId").getValue().toString()),
                            ds.child("jobTitle").getValue().toString(),
                            ds.child("requirement").getValue().toString(),
                            ds.child("city").getValue().toString(),
                            ds.child("country").getValue().toString(),
                            ds.child("startDate").getValue().toString(),
                            ds.child("endDate").getValue().toString(),
                            ds.child("description").getValue().toString()
                            ));


                }


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

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MyofferActivity.this);
                                dialog.setTitle("Offer details");
                                dialog.setMessage(offer.get((int)id).description);

                                dialog.show();

                            }
                        }
                );





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void removeOffer(DatabaseReference node){

        node.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(MyofferActivity.this, "Done", Toast.LENGTH_SHORT).show();
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
