package com.example.arithmos.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arithmos.R;

public class CustomDialog extends Dialog {

    interface FullNameListener {
        public void fullNameEntered(String fullName);
    }

    public Context context;

    private TextView editTextFullName;
    private Button buttonOK;
    private Button buttonCancel;



    private  TextView text_type;
    private RadioGroup radio_type;
    private RadioButton RadioButton_simple;
    private RadioButton RadioButton_dd;

    public static final int SCROLL_DELTA = 15; // Pixel.
    private ScrollView scrollView;
    private TextView text_difficulty;

    private RadioGroup radio_diff;
    private RadioButton RadioButton_facile;
    private RadioButton RadioButton_moyen;
    private RadioButton RadioButton_difficile;

    private TextView text_format;

    private RadioGroup radio_rep ;
    private RadioButton RadioButton_chiffres;
    private RadioButton RadioButton_lettres;


    private Button valider;

    private CustomDialog.FullNameListener listener;

    public CustomDialog(Context context, CustomDialog.FullNameListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dial);

        RadioGroup group_exo = (RadioGroup) findViewById(R.id.group_exo);
        RadioButton button_add_choose = (RadioButton) findViewById(R.id.add_button_choose);
        RadioButton button_sous_choose = (RadioButton) findViewById(R.id.sous_button_choose);
        RadioButton button_div_choose = (RadioButton) findViewById(R.id.div_button_choose);
        RadioButton button_mult_choose = (RadioButton) findViewById(R.id.mult_button_choose);
        button_add_choose.setChecked(true);
        group_exo.setVisibility(View.INVISIBLE);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        text_type = (TextView) findViewById(R.id.type);
        text_type.setText("type d'exercice");
        radio_type = (RadioGroup) findViewById(R.id.group_type);
        RadioButton_simple = (RadioButton) findViewById(R.id.simple);
        RadioButton_dd = (RadioButton) findViewById(R.id.draganddrop);
        RadioButton_simple.setChecked(true);


        text_difficulty = (TextView) findViewById(R.id.difficulty);
        text_difficulty.setText("choix de la difficulté");

        radio_diff = (RadioGroup) findViewById(R.id.group_diff);
        RadioButton_facile = (RadioButton) findViewById(R.id.facile);
        RadioButton_facile.setChecked(true);
        RadioButton_moyen = (RadioButton) findViewById(R.id.moyen);
        RadioButton_difficile = (RadioButton) findViewById(R.id.difficile);

        TextView text_format = (TextView) findViewById(R.id.answer);
        text_difficulty.setText("choix du format de la réponse");

        radio_rep = (RadioGroup) findViewById(R.id.group_rep);
        RadioButton_chiffres = (RadioButton) findViewById(R.id.chiffre);
        RadioButton_lettres = (RadioButton) findViewById(R.id.lettres);
        RadioButton_chiffres.setChecked(true);


        valider = (Button) findViewById(R.id.valider);;

        this.valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValiderClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonValiderClick()  {
        this.dismiss(); // Close Dialog
    }

    private void doScrollUp() {

        int x = this.scrollView.getScrollX();
        int y = this.scrollView.getScrollY();

        if(y - SCROLL_DELTA >= 0) {
            this.scrollView.scrollTo(x, y-SCROLL_DELTA);
        }

    }

    private void doScrollDown() {
        int maxAmount = scrollView.getMaxScrollAmount();

        int x = this.scrollView.getScrollX();
        int y = this.scrollView.getScrollY();

        if(y + SCROLL_DELTA <= maxAmount) {
            this.scrollView.scrollTo(x, y + SCROLL_DELTA);
        }
    }

}
