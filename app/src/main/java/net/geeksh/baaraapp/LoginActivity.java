package net.geeksh.baaraapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {



    // UI references.
    private EditText mProgressView;
    private EditText mPasswordEditView;
    private EditText mLoginFormEditView;
    private TextView mForgortPasswordEditView;
    private Button btnSignup;
    private Button btnSignin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

         mLoginFormEditView = (EditText) findViewById(R.id.email);
//         mProgressView = (EditText) findViewById(R.id.email);;
         mPasswordEditView = (EditText) findViewById(R.id.password);;
         btnSignup = (Button) findViewById(R.id.btn_signup);;
         btnSignin = (Button) findViewById(R.id.email_sign_in_button);
         mForgortPasswordEditView = (TextView) findViewById(R.id.editTextForgortPassword);


        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
        mForgortPasswordEditView.setOnClickListener(this);
    }

    private void signUser(){

            String emailData = mLoginFormEditView.getText().toString().trim();
            String passwordData = mPasswordEditView.getText().toString().trim();

            if(TextUtils.isEmpty(emailData)){
                Toast.makeText(LoginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
            }

            if(TextUtils.isEmpty(emailData)){
                Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            }

            firebaseAuth.signInWithEmailAndPassword(emailData, passwordData)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }else {
                                try {
                                    throw task.getException();

                                } catch (FirebaseAuthWeakPasswordException e){
                                    Toast.makeText(LoginActivity.this, e.getReason(), Toast.LENGTH_SHORT).show();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(LoginActivity.this, "Error - User collision", Toast.LENGTH_SHORT).show();

                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    }

    @Override
    public void onClick(View view) {
        if (view == btnSignup){
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }
        if (view == mForgortPasswordEditView){
            Toast.makeText(this, R.string.Function_not_active, Toast.LENGTH_SHORT).show();
        }

        if (view == btnSignin){
            signUser();
        }
    }
}

