package com.rival;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<CardModel> listItems;
    private Context context;

    public MyAdapter(List<CardModel> listItem, Context context) {
        this.listItems = listItem;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_activity,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardModel cardItem = listItems.get(position);

        holder.textViewHead.setText(cardItem.getText());
        holder.checkBox.setChecked(cardItem.getChecked());
        //holder.
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked())
                    holder.textViewHead.setPaintFlags(holder.textViewHead.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    holder.textViewHead.setPaintFlags(0);

                listItems.get(position).setChecked(holder.checkBox.isChecked());


            }
        });

        if(holder.checkBox.isChecked())
            holder.textViewHead.setPaintFlags(holder.textViewHead.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.textViewHead.setPaintFlags(0);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("2", "o22");
            textViewHead = (TextView) itemView.findViewById(R.id.textView);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);










        }


    }
}
