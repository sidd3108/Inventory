package com.example.inventory;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class add_items_Activity extends AppCompatActivity {

    EditText item_name,item_cat,item_price;
    Button add_item_button;
    Button scanbutton;
    TextView itembarcode;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    @SuppressLint("StaticFieldLeak")
    public static TextView resulttextview;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        item_name = findViewById(R.id.edititemname);
        item_cat  = findViewById(R.id.editcategory);
        item_price = findViewById(R.id.editprice);
        add_item_button = findViewById(R.id.additembuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        itembarcode= findViewById(R.id.barcodeview);
        resulttextview = findViewById(R.id.barcodeview);
        toolbar = findViewById(R.id.toolbar);


        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


        scanbutton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class)));



        add_item_button.setOnClickListener(view -> {
            String item_value_name = item_name.getText().toString();
            String item_value_Cat  = item_cat.getText().toString();
            String item_value_price = item_price.getText().toString();
            String itembarcodeValue = itembarcode.getText().toString();
            User user = new User(item_value_name,item_value_Cat,item_value_price,itembarcodeValue);
           databaseReference.push().setValue(user);
            Toast.makeText(add_items_Activity.this, "Item Added", Toast.LENGTH_SHORT).show();
        });
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
            Intent intent =new Intent(this,Dashboard_Activity.class);
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