package com.example.inventory;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class About_us extends AppCompatActivity {

    FirebaseAuth mAuth;
    RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = findViewById(R.id.toolbar_about_us);
        mAuth = FirebaseAuth.getInstance();


        relativeLayout1 = findViewById(R.id.relativeLayout_bug_report);
        relativeLayout2 = findViewById(R.id.relativeLayout_source_code);
        relativeLayout3 = findViewById(R.id.relativeLayout_website);
        relativeLayout4 = findViewById(R.id.releative_about);



        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);



        relativeLayout1.setOnClickListener(v -> sendMail());

        relativeLayout2.setOnClickListener(v -> goToUrl());

        relativeLayout3.setOnClickListener(v ->
                Toast.makeText(this, "The Website is not ready yet,We are working on It", Toast.LENGTH_SHORT).show());

        relativeLayout4.setOnClickListener(v -> {
            Intent intent = new Intent(About_us.this, about_app.class);
            startActivity(intent);
        });


        Context context = getApplicationContext(); // or activity.getApplicationContext()
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "not available"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView tvVersionName = findViewById(R.id.text9);
        tvVersionName.setText(myVersionName);
    }

    private void goToUrl() {
        Uri uri = Uri.parse("https://github.com/ANIKETH232323");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void sendMail() {
        String recipent = "aniketh620@gmail.com";

        String subject = "Bug Report";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{recipent});
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);


        intent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(intent,
                    "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.account_info);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.dashboard_menu){
            Intent intent = new Intent(this,Dashboard_Activity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.logout_menu){
            mAuth.signOut();
            sign_out();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sign_out() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}