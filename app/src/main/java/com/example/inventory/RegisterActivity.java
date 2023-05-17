package com.example.inventory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText  editTextEmail, editTextPassword, editTextcPassword;
        Button UserRegisterBtn;
        ProgressDialog progressDialog;

        FirebaseAuth mAuth;

        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextcPassword = findViewById(R.id.confirmPassword);
        UserRegisterBtn = findViewById(R.id.button_register);
        progressDialog = new ProgressDialog(this);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_user();
            }

            private void register_user() {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString().trim();
                String cpassword = editTextcPassword.getText().toString().trim();

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
                else if (!password.equals(cpassword))
                {
                    editTextcPassword.setError("Password Not Matched");
                    editTextcPassword.requestFocus();
                }
                else {
                    progressDialog.setMessage("Please wait while Registration ");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();   
                                send_user_next_activity();
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void send_user_next_activity() {
                            Intent intent = new Intent(RegisterActivity.this,Dashboard_Activity.class);
                            String gm1 = editTextEmail.getText().toString();
                            intent.putExtra("gmail1",gm1);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }

            }
        });



    }
    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}