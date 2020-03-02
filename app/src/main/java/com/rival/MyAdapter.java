package com.rival;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<CardModel> listItems;
    private Context context;
    private Button remove_button;
    private RecyclerView recyclerView;



    SparseBooleanArray selecteditems;
    public MyAdapter(List<CardModel> listItem, Context context,Button button, RecyclerView rView) {
        this.listItems = listItem;
        this.context = context;
        this.remove_button = button;
        selecteditems = new SparseBooleanArray();
        this.recyclerView = rView;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_activity,parent,false);

        return new ViewHolder(v);
    }
    public void toggleSelection(int pos) {
        if (selecteditems.get(pos, false)) {
            selecteditems.delete(pos);
        }
        else {
            selecteditems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void removeItem(int position){
        listItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardModel cardItem = listItems.get(position);

        holder.textViewHead.setText(cardItem.getText());
        holder.checkBox.setChecked(cardItem.getChecked());
        holder.itemView.setActivated(selecteditems.get(position,false));
        //holder.


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Position " + position);
                if(holder.checkBox.isChecked()){
                    holder.textViewHead.setPaintFlags(holder.textViewHead.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    remove_button.setVisibility(View.VISIBLE);
                    Animation animShake = AnimationUtils.loadAnimation(context, R.anim.anim);
                    remove_button.startAnimation(animShake);
//                    listItems.remove(position);
//                    notifyItemRemoved(position);
                }
                else{
                    int k = 0;
                    for(int i=0; i<listItems.size(); i++)
                        if (listItems.get(i).getChecked() == false)
                            k++;
                     if(k==listItems.size()-1)
                         remove_button.setVisibility(View.GONE);

                    holder.textViewHead.setPaintFlags(0);
                }


                listItems.get(position).setChecked(holder.checkBox.isChecked());

                if (position == getItemCount()-1)
                    recyclerView.scrollToPosition(position);



            }
        });
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                System.out.println("listItems size " + listItems.size());

//                listItems.remove(2);
//
//                //notifyItemRemoved(2);
//                removeItem(2);

//                    CardModel listItem = new CardModel("1");
//                    listItems.add(listItem);
//                //dapter = new MyAdapter(listItems, this);
//                recyclerView.setAdapter(adapter);
//                recyclerView.scrollToPosition(listItems.size() - 1);
                //notifyItemRemoved(position);
                  for(int i = getItemCount()-1; i>=0; i--){


                    if (listItems.get(i).getChecked() == true) {
                        listItems.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, getItemCount());
                    }

                }


                // recyclerView.setAdapter(adapter);
                remove_button.setVisibility(View.GONE);


            }
        });

        if(holder.checkBox.isChecked())
            holder.textViewHead.setPaintFlags(holder.textViewHead.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.textViewHead.setPaintFlags(0);

//        remove_button.setVisibility(View.VISIBLE);
//
//        Animation animShake = AnimationUtils.loadAnimation(this.context, R.anim.anim);
//        remove_button.startAnimation(animShake);




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

//            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
//
//            if(itemView.isActivated())
//                itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorBlack));
//            else
//                itemView.setBackgroundColor(Color.parseColor("#cccccc"));










        }


    }
}
