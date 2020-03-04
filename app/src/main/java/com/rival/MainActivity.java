package com.rival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity implements AddDialog.OnInputListener {

    private static final String LOG_TAG = "FIle" ;
    final String FILENAME = "file";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public List<CardModel> listItems;

    SQLiteDatabase database;
    DBHelper dbHelper;
    SQL sql;
    private Button remove_button;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.add_button);
        remove_button = (Button) findViewById(R.id.remove_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        dbHelper = new DBHelper(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems = new ArrayList<>();

        database = dbHelper.getWritableDatabase();

        sql = new SQL(dbHelper);

        listItems = sql.read();

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);





        adapter = new MyAdapter(listItems, this,remove_button, recyclerView);
        recyclerView.setAdapter(adapter);



        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean isLongPressDragEnabled() {

                return true;
            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                final int position = viewHolder.getAdapterPosition();
//
//                int sectionIndex = listAdapter.getSectionForAdapterPosition(position);
//                int itemIndex = listAdapter.getPositionOfItemInSection(sectionIndex, position);
//
//                myModelObject temp = filteredArrayList.get(sectionIndex).get(itemIndex);
//
//                if(!temp.getMarkedType().equals("1")){
//                    return 0;
//                }
                //return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //recyclerViewAdapter.onItemDismiss(viewHolder.AdapterPosition);
            }



            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

//                Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.dragged_item);
//                viewHolder.itemView.startAnimation(animShake);

                RotateAnimation rotate = new RotateAnimation(0, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                rotate.setFillAfter(true);
                rotate.setDuration(80);

                rotate.setRepeatCount(Animation.ABSOLUTE);
                //viewHolder.itemView.setcolo
                viewHolder.itemView.setAnimation(rotate);
                viewHolder.itemView.animate().scaleY(1.05f).scaleX(1.05f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(80);
                //recyclerView.setBackgroundColor(Color.GRAY);

                return makeMovementFlags(dragFlags, 0);
            }
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
               // AdapterAddedCities.ViewHolder holder = (AdapterAddedCities.ViewHolder) viewHolder;
                viewHolder.itemView.setBackgroundColor(0);
                RotateAnimation rotate = new RotateAnimation(0, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                rotate.setFillAfter(true);
                rotate.setDuration(100);

                rotate.setRepeatCount(Animation.ABSOLUTE);

                viewHolder.itemView.setAnimation(rotate);

                viewHolder.itemView.setAnimation(rotate);
                viewHolder.itemView.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(80);

            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder dragged,
                                  @NonNull RecyclerView.ViewHolder target) {

                int posotion_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                //Toast.makeText(getApplicationContext(),posotion_dragged + "В "+position_target,Toast.LENGTH_LONG).show();



                Collections.swap(listItems,posotion_dragged, position_target);

                adapter.notifyItemMoved(posotion_dragged, position_target);
                ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                adapter.notifyItemRangeChanged(0,adapter.getItemCount());


                return false;
            }

        });
        helper.attachToRecyclerView(recyclerView);




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                listItems.remove(0);
                sql.update(listItems);
//                AddDialog dialogFragment = new AddDialog();
//                dialogFragment.show(getSupportFragmentManager(), "");
//
            }
            });
        remove_button.setVisibility(View.GONE);
    }

    @Override
    public void sendInput(String input) {

        CardModel listItem = new CardModel(input);

        listItems.add(listItem);
        sql.add(input,"false");

        adapter.notifyItemRemoved(adapter.getItemCount());


       // recyclerView.scrollToPosition(listItems.size()-1);
        recyclerView.smoothScrollToPosition(listItems.size()-1);
    }

    void writeFile() {
        try {
            // отрываем поток для записи
            BufferedWriter bw;
            bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            bw.write("Содержимое файла");
            bw.write("Содержимое файла3");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
