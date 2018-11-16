package net.geeksh.baaraapp;

import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
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
import android.widget.TextView;
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

public class MyofferActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ImageView imgViewOfferOpen;


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

    public void getMyOffers() {

        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getUid() + "/Offer/");

//        final Query query = databaseReference.orderByChild("timeStamp");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Offer> offer = new ArrayList<>();
//                Offer offer = new Offer();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                                String food = String.valueOf(parent.getItemAtPosition(position));
                                int ff = offer.get((int) id).offerId;
//                                Toast.makeText(getApplicationContext(), ff, Toast.LENGTH_LONG).show();
//                                final Offer singleFoodItem = offer.get((int) position);


                                Dialog dialog = new Dialog(MyofferActivity.this);

                                dialog.setContentView(R.layout.offer_dialog);
                                TextView txtViewDialogJobTitle = dialog.findViewById(R.id.txtViewDialogJobTitle);
                                TextView txtViewDialogJobDesc = dialog.findViewById(R.id.txtViewDialogJobDesc);
                                TextView txtViewDialogJobReq = dialog.findViewById(R.id.txtViewDialogJobReq);
                                TextView txtViewDialogJobEndDate = dialog.findViewById(R.id.txtViewDialogJobEndDate);

                                txtViewDialogJobTitle.setText(String.valueOf(offer.get(position).jobTitle));
                                txtViewDialogJobDesc.setText(String.valueOf(offer.get(position).description));
                                txtViewDialogJobReq.setText(String.valueOf(offer.get(position).requirement));
                                txtViewDialogJobEndDate.setText(String.valueOf(offer.get(position).endDate));


                                Button btn = (Button) dialog.findViewById(R.id.buttonDialogDelete);
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        databaseReference.child(String.valueOf(offer.get(position).offerId)).removeValue();
//                                        q.getRef().removeValue();
                                        Toast.makeText(MyofferActivity.this, "Offer removed successfully ", Toast.LENGTH_SHORT).show();
                                    }
                                });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

        if (view == buttonCreateOffer) {
            finish();
            startActivity(new Intent(getApplicationContext(), CreateofferActivity.class));
        }
    }
}
