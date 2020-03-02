package com.rival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddDialog.OnInputListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public List<CardModel> listItems;


    private Button remove_button;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.add_button);
        remove_button = (Button) findViewById(R.id.remove_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


        for (int i = 0; i <13; i++){
            CardModel listItem = new CardModel("Купить маркер серии " + i);
            listItems.add(listItem);
            System.out.println(i);
        }

        adapter = new MyAdapter(listItems, this,remove_button, recyclerView);
        recyclerView.setAdapter(adapter);



        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder dragged,
                                  @NonNull RecyclerView.ViewHolder target) {

                int posotion_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Toast.makeText(getApplicationContext(),posotion_dragged + "В "+position_target,Toast.LENGTH_LONG).show();


                Collections.swap(listItems,posotion_dragged, position_target);
                adapter.notifyItemMoved(posotion_dragged, position_target);


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(getApplicationContext(),direction + "В33",Toast.LENGTH_LONG).show();

            }
        });
        helper.attachToRecyclerView(recyclerView);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

//                    CardModel listItem = new CardModel("1");
//                    listItems.add(listItem);
//                //dapter = new MyAdapter(listItems, this);
//                recyclerView.setAdapter(adapter);
//                recyclerView.scrollToPosition(listItems.size() - 1);



//                Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
//                remove_button.startAnimation(animShake);
                AddDialog dialogFragment = new AddDialog();
                dialogFragment.show(getSupportFragmentManager(), "");




            }
            });





        remove_button.setVisibility(View.GONE);




    }

    @Override
    public void sendInput(String input) {

        CardModel listItem = new CardModel(input);

        listItems.add(listItem);
        adapter.notifyItemRemoved(adapter.getItemCount());


       // recyclerView.scrollToPosition(listItems.size()-1);
        recyclerView.smoothScrollToPosition(listItems.size()-1);
    }
}
