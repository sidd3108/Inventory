package com.example.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText editTextEmail, editTextPassword;
        TextView forgot_password;
        ProgressDialog progressDialog;
        Button login;

        FirebaseAuth mAuth;


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        editTextEmail = findViewById(R.id.emailSignIn);
        editTextPassword =findViewById(R.id.password);
        forgot_password = findViewById(R.id.forgotpassword);
        login = findViewById(R.id.Login);
        progressDialog = new ProgressDialog(this);
        mAuth =FirebaseAuth.getInstance();
        mAuth.getCurrentUser();



        forgot_password.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,Forgot_paswword.class);
            startActivity(intent);
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_to_next_activity();
        }

            private void login_to_next_activity() {
                String email = Objects.requireNonNull(editTextEmail.getText()).toString();
                String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();

                if (!email.matches(emailPattern))
                {
                    editTextEmail.setError("Enter correct Email");
                    editTextEmail.requestFocus();
                }
                else if (password.isEmpty()||password.length()<6)
                {
                    editTextPassword.setError("Enter Proper Password");
                    editTextPassword.requestFocus();
                }

                else {
                    progressDialog.setMessage("Please wait while Login ");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){



                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this,Dashboard_Activity.class);

                             String gm = editTextEmail.getText().toString();
                             intent.putExtra("gmail",gm);
                             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                             finish();


                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });



}


    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

}