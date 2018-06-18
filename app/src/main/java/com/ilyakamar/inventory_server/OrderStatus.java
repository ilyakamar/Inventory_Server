package com.ilyakamar.inventory_server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyakamar.inventory_server.Common.Common;
import com.ilyakamar.inventory_server.Interface.ItemClickListener;
import com.ilyakamar.inventory_server.Model.Request;
import com.ilyakamar.inventory_server.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        // Firebase
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        // Init
        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(); // load all Orders

    }// end onCreate

    private void loadOrders() {// loadOrders
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {// populateViewHolder
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {// setItemClickListener
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // just implement it to fix Crash when click to this item
                    }
                });// end setItemClickListener


            }// end populateViewHolder
        };// FirebaseRecyclerAdapter
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }// end loadOrders


    @Override
    public boolean onContextItemSelected(MenuItem item) {// onContextItemSelected
        return super.onContextItemSelected(item);
    }// end onContextItemSelected
}// END
