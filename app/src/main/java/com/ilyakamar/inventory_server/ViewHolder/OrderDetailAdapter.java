package com.ilyakamar.inventory_server.ViewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilyakamar.inventory_server.Model.Order;
import com.ilyakamar.inventory_server.R;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name,quantity,price,discount;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.product_name);
        quantity = itemView.findViewById(R.id.product_quantity);
        price = itemView.findViewById(R.id.product_price);
        discount = itemView.findViewById(R.id.product_discount);
    }


}// END


public class OrderDetailAdapter extends RecyclerView.Adapter<MyViewHolder>{


    List<Order> myOrders;

    public OrderDetailAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_layout,parent,false);

        return new MyViewHolder(itemView);
    }// end onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Order order = myOrders.get(position);
        holder.name.setText(String.format("שם : %s",order.getProductName()));
        holder.quantity.setText(String.format("כמות : %s",order.getQuantity()));
        holder.price.setText(String.format("מכיר : %s",order.getPrice()));
//        holder.discount.setText(String.format("discount : %s",order.getDiscount()));
    }// end onBindViewHolder

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}// END
