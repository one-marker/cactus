package com.meCactus;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class AddDialog extends DialogFragment {


    private static final String TAG = "DIALOG" ;
    private String title = "";
    private String text = "";
    private int id;
    public interface OnInputListener{
        void sendInput(int id, String title,String text,Boolean edit);
    }
    public OnInputListener mOnInputListener;

    private Boolean edit = false;
    Button addButton;
    EditText Title;
    EditText Text;
    //TextView errorText;
    String sTitle;
    String sText;
    View v;

    public AddDialog(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.edit = true;

        System.out.println(this.id);
    }

    public AddDialog() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.dialog_layout, container, false);
        int screenSize =
                getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK;
       // screenSize = 1080 + 1080*8/10;
        System.out.println(screenSize);
        addButton = (Button) v.findViewById(R.id.add);
        Title = (EditText) v.findViewById(R.id.title_field);
        Title.setWidth(screenSize);
        Title.setText(title);
        Title.requestFocus();

        Text = (EditText) v.findViewById(R.id.text_field);
        Text.setText(text);
        KeyboardUtil.initFocusAndShowKeyboard(Title,getContext());

//
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//       // errorText = (TextView) v.findViewById(R.id.errorText);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                sTitle = Title.getText().toString();
                sText = Text.getText().toString();
                Log.e(TAG, "Text: : " + sText );


                if (sTitle.trim().equalsIgnoreCase("")) {

                    Toast.makeText(getActivity().getApplicationContext(),getString(R.string.inputText),Toast.LENGTH_LONG).show();

                }
                else {
                    if (sTitle.length() <= 255){
                        if (sText.length() <= 1000){
                            mOnInputListener.sendInput(id, sTitle,sText, edit);
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.errText),Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),getString(R.string.errTitle),Toast.LENGTH_LONG).show();
                    }
                   // errorText.setText("");


                }



//                AddDialog dialogFragment = new AddDialog();
//                dialogFragment.show(getSupportFragmentManager(), "");
//

            }
        });


    // getDialog().setTitle("Simple Dialog");
        return v;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
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
