package com.example.inventory;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public  class My_Adapter extends FirebaseRecyclerAdapter<User, My_Adapter.PostViewHolder> {

    public My_Adapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder,final int position, @NonNull final User model) {
            holder.items_name.setText(model.getProduct_name());
            holder.items_category.setText(model.getCategory());
            holder.items_price.setText(model.getPrice());
            holder.itembarcode.setText(model.getItembarcode());

            String key = getRef(position).getKey();
            holder.delete.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.items_name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("The action can't be undone");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    assert key != null;
                    FirebaseDatabase.getInstance().getReference().child("User")
                            .child(key).removeValue().addOnCompleteListener(task -> {

                            });
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> {

                });
                builder.show();


            });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_delete_list,parent,false);
        return new PostViewHolder(v);
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView items_name,items_category,items_price,itembarcode;
                ImageView delete;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            items_name = itemView.findViewById(R.id.item_name);
            items_category = itemView.findViewById(R.id.item_category);
            items_price = itemView.findViewById(R.id.item_price);
            itembarcode = itemView.findViewById(R.id.barco);
            delete = itemView.findViewById(R.id.item_delete_button);
        }
    }
}
