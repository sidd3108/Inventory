package com.example.inventory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class View_inventory_activity extends AppCompatActivity {
        RecyclerView recyclerView;
        View_inventory_Adapter adapter;
        TextView total_no_items,total_price;
        DatabaseReference databaseReference;

        FirebaseAuth mAuth;

        Toolbar toolbar;
        int count_total_no_item = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        total_no_items = findViewById(R.id.totalnoitem);
        total_price = findViewById(R.id.totalsum);

        recyclerView = findViewById(R.id.recyclerViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar =findViewById(R.id.toolbar_view_in);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);

        mAuth =FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    count_total_no_item = (int) snapshot.getChildrenCount();
                    total_no_items.setText(Integer.toString(count_total_no_item));
                }else{
                    total_no_items.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int sum=0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object price = map.get("price");
                        assert price != null;
                        int pValue = Integer.parseInt(String.valueOf(price));
                        sum += pValue;
                        total_price.setText(String.valueOf(sum));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User"), User.class)
                        .build();

                adapter = new View_inventory_Adapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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