package com.ilyakamar.inventory_server;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyakamar.inventory_server.Common.Common;
import com.ilyakamar.inventory_server.Interface.ItemClickListener;
import com.ilyakamar.inventory_server.Model.Request;
import com.ilyakamar.inventory_server.ViewHolder.OrderViewHolder;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    MaterialSpinner spinner;

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
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {// populateViewHolder
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {// setItemClickListener
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        if (!isLongClick){
                            Intent orderDetail = new Intent(OrderStatus.this,OrderDetail.class);
                            Common.currentRequest = model;
                            orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                            startActivity(orderDetail);
                        }else {


                        }

                    }
                });// end setItemClickListener


            }// end populateViewHolder
        };// FirebaseRecyclerAdapter
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }// end loadOrders


    @Override
    public boolean onContextItemSelected(MenuItem item) {// onContextItemSelected
        if (item.getTitle().equals(Common.UPDATE))
        {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE))
        {
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }// end onContextItemSelected

    private void deleteOrder(String key) {// deleteOrder
        requests.child(key).removeValue();
    }// end deleteOrder

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("עדכן הזמנה");
        alertDialog.setMessage("אנא בחר סטטוס");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);

        spinner = view.findViewById(R.id.statusSpinner);
//        spinner.setItems("Placed","On my way","Shipped");
        spinner.setItems("ההזמנה התקבלה","ההזמנה בטיפול","המשלוח בדרך");

        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
            }
        });// end setPositiveButton

        alertDialog.setNegativeButton("לא", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });// end setNegativeButton

        alertDialog.show();
    }// end showUpdateDialog
}// END
