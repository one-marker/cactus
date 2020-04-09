package com.meCactus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddDialog.OnInputListener {

    private List<CardModel> listItems;
    private Database database;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private EditText textView;
    private Button remove_button;
    private Button addButton;
    private Button done;
     private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay(); Point size = new Point(); display.getSize(size); int width = size.x;
        System.out.println(width);
        settings = getSharedPreferences("CactusSettings", MODE_PRIVATE);

        listItems = new ArrayList<>();
        database = new Database(this);
        textView = (EditText) findViewById(R.id.textView);




        textView.setText(settings.getString("ListName",getString(R.string.myList)));
        textView.setCursorVisible(false);

        textView.clearFocus();
        addButton = (Button) findViewById(R.id.add_button);
        done = (Button) findViewById(R.id.done);
        remove_button = (Button) findViewById(R.id.remove_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      //  database.upadteByTitl1e("7");
       // database.swapById(12, 13);
       // database.removeById(9);
        database.read(listItems);

        //System.out.println(database.getCardModelbyId(11).toString());


        adapter = new MyAdapter(listItems,this,remove_button, recyclerView);

        recyclerView.setAdapter(adapter);



        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean isLongPressDragEnabled() {

                return true;
            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //recyclerViewAdapter.onItemDismiss(viewHolder.AdapterPosition);
            }



            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
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
                viewHolder.itemView.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(20);



                database.update(listItems);
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder dragged,
                                  @NonNull RecyclerView.ViewHolder target) {

                int posotion_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
//                Toast.makeText(getApplicationContext(),posotion_dragged + " -> "+position_target,Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(), listItems.get(posotion_dragged).getId()  + " !!! -> " +  listItems.get(position_target).getId(),Toast.LENGTH_LONG).show();

           //     database.swapById( listItems.get(posotion_dragged).getId(), listItems.get(position_target).getId());
                Collections.swap(listItems,posotion_dragged, position_target);

                adapter.notifyItemMoved(posotion_dragged, position_target);
                ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                adapter.notifyItemRangeChanged(0,adapter.getItemCount());



                return false;
            }

        });
        helper.attachToRecyclerView(recyclerView);
//        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AddDialog dialogFragment = new AddDialog();
                dialogFragment.show(getSupportFragmentManager(), "DIALOG");
            }
            });
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {


                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    done.setVisibility(View.GONE);
                    textView.setCursorVisible(false);
                } else {
                    //done.setVisibility(View.VISIBLE);
                    //done.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.done_anim));
                    done.requestFocus();

                }
            }
        });


        textView.setOnEditorActionListener(doneMethodAction);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                textView.setCursorVisible(true);



            }
        });
        remove_button.setVisibility(View.GONE);
    }

    private TextView.OnEditorActionListener doneMethodAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    if (textView.length() <=  10) {
                        done.setVisibility(View.GONE);

                        SharedPreferences.Editor prefEditor = settings.edit();
                        prefEditor.putString("ListName", textView.getText().toString());
                        prefEditor.apply();
                        textView.clearFocus();
                    } else {
                        AddDialog.KeyboardUtil.initFocusAndShowKeyboard(textView,v.getContext());
                        Toast.makeText(v.getContext(),getString(R.string.errListName),Toast.LENGTH_LONG).show();

                    }
                    break;

            }
            return false;
        }
    };
    @Override
    public void sendInput(int id, String title,String text,Boolean edit) {

        System.out.println(id+" " + edit);
        if (edit){

            database.updateById(id, title, text);
            database.read(listItems);


        } else {
            CardModel listItem = new CardModel(title,text,0);

            listItems.add(0,listItem);

            database.add(title,text,false);
        }

        adapter.notifyDataSetChanged();


    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static class KeyboardUtil {
        public static void initFocusAndShowKeyboard(final EditText et, final Context context) {
            et.requestFocus();
            et.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 300);
        }
    }
}
