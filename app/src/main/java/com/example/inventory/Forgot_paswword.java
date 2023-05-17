package com.example.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Forgot_paswword extends AppCompatActivity {

    private EditText gmail_box;
    private String email;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_paswword);

        auth = FirebaseAuth.getInstance();
        Button send_mail = findViewById(R.id.send_mail);
        gmail_box = findViewById(R.id.email_id_forgot);

        send_mail.setOnClickListener(v -> validateData());
    }

    private void validateData() {
        email = gmail_box.getText().toString();
        if(email.isEmpty()){
            gmail_box.setError("Required");
        }
        else {
            forger_password();
        }
    }

    private void forger_password() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isComplete()){
                Toast.makeText(Forgot_paswword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Forgot_paswword.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(Forgot_paswword.this, "Error:"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back_to_login(View view) {
        Intent intent = new Intent(Forgot_paswword.this,LoginActivity.class);
        startActivity(intent);
    }
}