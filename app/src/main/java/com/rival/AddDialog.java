package com.rival;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class AddDialog extends DialogFragment {


    private static final String TAG = "AddDialog" ;

    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;

    Button addButton;
    EditText myText;
    //TextView errorText;
    String sText;
    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.dialog_layout, container, false);

        addButton = (Button) v.findViewById(R.id.add);
        myText = (EditText) v.findViewById(R.id.myText);
        myText.requestFocus();
        KeyboardUtil.initFocusAndShowKeyboard(myText,getContext());

//
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//       // errorText = (TextView) v.findViewById(R.id.errorText);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                sText = myText.getText().toString();
                Log.e(TAG, "Text: : " + sText );


                if (sText.trim().equalsIgnoreCase("")) {


                    Toast.makeText(getActivity().getApplicationContext(),"Введите текст... ",Toast.LENGTH_LONG).show();



                  //  Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
                   // errorText.startAnimation(animShake);
                   // errorText.setText("Введите текст");
                   // myText.setError("Enter FirstName");
                }
                else {
                   // errorText.setText("");
                    mOnInputListener.sendInput(sText);
                    getDialog().dismiss();

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
