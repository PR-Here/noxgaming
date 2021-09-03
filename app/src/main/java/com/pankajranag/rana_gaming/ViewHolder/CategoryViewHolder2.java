package com.pankajranag.rana_gaming.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pankajranag.rana_gaming.R;

public class CategoryViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView paymentType,mobileno,pubgname;


    public CategoryViewHolder2(@NonNull View itemView) {
        super(itemView);
        paymentType = (TextView)itemView.findViewById(R.id.type);
        mobileno = (TextView)itemView.findViewById(R.id.mobileno_dialog);
        pubgname = (TextView)itemView.findViewById(R.id.name);
    }

    @Override
    public void onClick(View v) {

    }
}
