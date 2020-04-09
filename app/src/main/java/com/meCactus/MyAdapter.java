package com.meCactus;

import android.content.Context;
import android.graphics.Paint;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



    private List<CardModel> listItems;
    private Fragment fragmentManager;
    private Context context;
    private Button remove_button;
    private RecyclerView recyclerView;
    private Database database;


    SparseBooleanArray selecteditems;
    public MyAdapter(List<CardModel> listItem, Context context, Button button, RecyclerView rView) {
        this.listItems = listItem;
        this.context = context;
        this.remove_button = button;
        selecteditems = new SparseBooleanArray();
        this.recyclerView = rView;
        //this.fragmentManager  =fragmentManager;
        database = new Database(context);
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

        holder.titleView.setText(cardItem.getTitle());
        holder.checkBox.setChecked(cardItem.getCheck());
        holder.itemView.setActivated(selecteditems.get(position,false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(true);

                    Boolean status = holder.checkBox.isChecked();
                    System.out.println("Status: " +  status.toString());

                    holder.titleView.setPaintFlags(holder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    remove_button.setVisibility(View.VISIBLE);

                    remove_button.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim));
                    listItems.get(position).setChecked(holder.checkBox.isChecked());

//                    listItems.remove(position);
//                    notifyItemRemoved(position);
                } else {
                    holder.checkBox.setChecked(false);
                    int k = 0;
                    for(int i=0; i<listItems.size(); i++)
                        if (listItems.get(i).getCheck() == false)
                            k++;




                    if(k == listItems.size()-1)
                        remove_button.setVisibility(View.GONE);

                    holder.titleView.setPaintFlags(0);

                }


                listItems.get(position).setChecked(holder.checkBox.isChecked());

                if (position == getItemCount()-1)
                    recyclerView.scrollToPosition(position);

                database.update(listItems);

            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(holder.checkBox.isChecked()){
                  //  holder.checkBox.setChecked(false);

                    Boolean status = holder.checkBox.isChecked();
                    System.out.println("Status: " +  status.toString());

                    holder.titleView.setPaintFlags(holder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    remove_button.setVisibility(View.VISIBLE);

                    remove_button.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim));
                    listItems.get(position).setChecked(holder.checkBox.isChecked());

//                    listItems.remove(position);
//                    notifyItemRemoved(position);
                } else {
                   // holder.checkBox.setChecked(true);
                    int k = 0;
                    for(int i=0; i<listItems.size(); i++)
                        if (listItems.get(i).getCheck() == false)
                            k++;




                     if(k == listItems.size()-1)
                         remove_button.setVisibility(View.GONE);

                    holder.titleView.setPaintFlags(0);

                }


                listItems.get(position).setChecked(holder.checkBox.isChecked());

                if (position == getItemCount()-1)
                    recyclerView.scrollToPosition(position);

                database.update(listItems);

            }
        });
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                  for(int i = getItemCount()-1; i>=0; i--){

                    if (listItems.get(i).getCheck() == true) {
                        listItems.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, getItemCount());

                        database.update(listItems);
                    }

                }


                remove_button.setVisibility(View.GONE);


            }
        });
        holder.editCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AddDialog dialogFragment = new AddDialog(database.getIdbyIndex(listItems.size()-position-1),listItems.get(position).getTitle(),listItems.get(position).getText());
                dialogFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "DIALOG");

            }
        });

        if(holder.checkBox.isChecked()) {
            holder.titleView.setPaintFlags(holder.titleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.checkBox.setChecked(true);
            remove_button.setVisibility(View.VISIBLE);
        }
        else {
            holder.titleView.setPaintFlags(0);
            holder.checkBox.setChecked(false);

        }


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


        public TextView titleView;
        public ImageButton editCard;
        public CheckBox checkBox;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.textView);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
            editCard = (ImageButton)itemView.findViewById(R.id.editCard);

        }


    }
}
