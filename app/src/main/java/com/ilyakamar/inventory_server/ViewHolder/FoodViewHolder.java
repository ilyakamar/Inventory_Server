package com.ilyakamar.inventory_server.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilyakamar.inventory_server.Common.Common;
import com.ilyakamar.inventory_server.Interface.ItemClickListener;
import com.ilyakamar.inventory_server.R;


    public class FoodViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,
            View.OnCreateContextMenuListener{

        public TextView food_name;
        public ImageView food_image;

        private ItemClickListener itemClickListener;


        public FoodViewHolder(View itemView) {// cons
            super(itemView);

            food_name = itemView.findViewById(R.id.food_name);
            food_image = itemView.findViewById(R.id.food_image);


            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);


        }// end cons


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {// onClick
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }// end onClick


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select the action");

            contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
            contextMenu.add(0,1,getAdapterPosition(), Common.DELETE);

        }// end onCreateContextMenu
    }