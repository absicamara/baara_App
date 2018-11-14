package net.geeksh.baaraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyofferActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonCreateOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffer);

        buttonCreateOffer = (Button) findViewById(R.id.buttonCreateOffer);

        buttonCreateOffer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonCreateOffer){
            startActivity(new Intent(getApplicationContext(), CreateofferActivity.class));
        }
    }
}
