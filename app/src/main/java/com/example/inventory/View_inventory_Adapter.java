package com.example.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class View_inventory_Adapter extends FirebaseRecyclerAdapter<User, View_inventory_Adapter.View_inventoryHolder> {

    public View_inventory_Adapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull View_inventoryHolder holder, int position, @NonNull User model) {

                holder.item_name_view.setText(model.getProduct_name());
                holder.item_category_view.setText(model.getCategory());
                holder.item_price_view.setText(model.getPrice());
                holder.itembarcodesd.setText(model.getItembarcode());

    }

    @NonNull
    @Override
    public View_inventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_inventory_dummy_list, parent, false);
        return new View_inventoryHolder(view);
    }

    class View_inventoryHolder extends RecyclerView.ViewHolder{

        TextView item_name_view,item_category_view,item_price_view,itembarcodesd;


        public View_inventoryHolder(@NonNull View itemView) {
            super(itemView);

            item_name_view = itemView.findViewById(R.id.viewitemname);
            item_category_view = itemView.findViewById(R.id.viewitemcategory);
            item_price_view = itemView.findViewById(R.id.viewitemprice);
            itembarcodesd = itemView.findViewById(R.id.viewitembarcoden);
        }
    }
}
