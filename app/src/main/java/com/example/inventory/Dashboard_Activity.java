package com.example.inventory;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Dashboard_Activity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser muser;

    CardView add_items,delete_items,view_inventory,about;
    TextView firebase_name;
    Toolbar toolbar;

     String nam;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth =FirebaseAuth.getInstance();
        muser = mAuth.getCurrentUser();

        add_items = findViewById(R.id.addItems);
        delete_items =  findViewById(R.id.deleteItems);
        view_inventory =  findViewById(R.id.viewInventory);
        firebase_name = findViewById(R.id.fire_basename);
        about = findViewById(R.id.about_us);
        toolbar =  findViewById(R.id.toolbar_dash);

        nam = getIntent().getStringExtra("gmail");


/*/       assert muser != null;
//        String final_user = muser.getEmail();
//        assert final_user != null;
//        String user_sub = final_user.substring(0,final_user.indexOf('@'));
////        String result = user_sub.replace(".","");
//        firebase_name.setText("Welcome,"+user_sub);
*/


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);

        add_items.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard_Activity.this,add_items_Activity.class);
            startActivity(intent);
        });

        delete_items.setOnClickListener(view -> {

            Intent intent = new Intent(Dashboard_Activity.this,delete_items_activity.class);
            startActivity(intent);
        });

        view_inventory.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard_Activity.this,View_inventory_activity.class);
            startActivity(intent);
        });

        about.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Activity.this,About_us.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.dashboard_menu){

            Toast.makeText(this, "You are already in Dashboard", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.logout_menu){
            mAuth.signOut();
            sign_out();
            finish();
        }

        else if (id == R.id.account_info) {
            Intent intent = new Intent(this, profile.class);
            String g = nam;
            intent.putExtra("ns",g);

            startActivity(intent);
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