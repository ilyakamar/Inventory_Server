package com.ilyakamar.inventory_server;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ilyakamar.inventory_server.Interface.ItemClickListener;
import com.ilyakamar.inventory_server.Model.Food;
import com.ilyakamar.inventory_server.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton fab;


    // firebase
    FirebaseDatabase db;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId = "";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate *********
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Firebase
        db = FirebaseDatabase.getInstance();
        foodList  = db.getReference("Foods");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // Init
        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code late
            }
        });// end setOnClickListener

        if (getIntent() !=null)
        categoryId = getIntent().getStringExtra("CategoryId");

        if (!categoryId.isEmpty())
        loadListFood(categoryId);


    }// end onCreate ******

    private void loadListFood(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.food_image);


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Code late
                    }
                });// end setItemClickListener
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }// end loadListFood


}// END
