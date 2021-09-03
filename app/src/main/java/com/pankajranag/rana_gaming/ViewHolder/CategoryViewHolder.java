package com.pankajranag.rana_gaming.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pankajranag.rana_gaming.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView id,name,amount;


    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        id = (TextView)itemView.findViewById(R.id.id);
        name = (TextView)itemView.findViewById(R.id.name);
        amount = (TextView)itemView.findViewById(R.id.amount);
    }

    @Override
    public void onClick(View v) {

    }
}
