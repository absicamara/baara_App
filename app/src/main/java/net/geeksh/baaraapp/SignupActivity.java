package net.geeksh.baaraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;

    private EditText editTexteditTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private Button buttonSignup;
    private TextView textViewForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Let's initialize by getting layouts
        editTexteditTextUsername = (EditText) findViewById(R.id.signup_username_edtxt);
        editTexteditTextUsername

    }

    @Override
    public void onClick(View view) {
        if (view == textViewForgot){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
