package net.geeksh.baaraapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseException firebaseException;

    private EditText editTexteditTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private Button buttonSignup;
    private TextView textViewForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        //Let's initialize by getting layouts
        editTexteditTextUsername = (EditText) findViewById(R.id.signup_username_edtxt);
        editTextPassword = (EditText) findViewById(R.id.signup_passwd_edtxt);
        editTextPasswordConfirm = (EditText) findViewById(R.id.signup_passwdC_edtxt);
        buttonSignup = (Button) findViewById(R.id.signup_register_btn);
        textViewForgot = (TextView) findViewById(R.id.signin_txt);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    private void registerUser() {
        String email = editTexteditTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordC = editTextPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordC)) {
            Toast.makeText(this, "Confirm password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordC)) {
            Toast.makeText(this, R.string.password_unmatch, Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        }else {
                            try {
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e){
                                Toast.makeText(SignupActivity.this, e.getReason(), Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignupActivity.this, "Error - User collision", Toast.LENGTH_SHORT).show();

                            }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                });

    }


}
