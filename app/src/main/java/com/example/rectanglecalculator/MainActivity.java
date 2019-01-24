package com.example.rectanglecalculator;

import java.math.RoundingMode;
import java.text.NumberFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements OnEditorActionListener{

    //define variables for the widgets
    private EditText widthField;
    private EditText heightField;
    private TextView areaDisplay;
    private TextView perimeterDisplay;

    //define instance variables that should be saved
    private String widthString = "";
    private String heightString = "";

    //define the sharedpreference object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references to the widgets
        widthField = (EditText) findViewById(R.id.widthField);
        heightField = (EditText) findViewById(R.id.heightField);
        areaDisplay = (TextView) findViewById(R.id.areaDisplay);
        perimeterDisplay = (TextView) findViewById(R.id.perimeterDisplay);

        //set listeners
        widthField.setOnEditorActionListener(this);
        heightField.setOnEditorActionListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save instance variables
        Editor editor = savedValues.edit();
        editor.putString("widthString", widthString);
        editor.putString("heightString", heightString);
        editor.apply();

        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        widthString = savedValues.getString("widthString", "");
        heightString = savedValues.getString("heightString", "");

        // set the bill amount on its widget
        widthField.setText(widthString);
        heightField.setText(heightString);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        //get width amount
        widthString = widthField.getText().toString();
        float widthAmount;
        if (widthString.equals("")) {
            widthAmount = 0;
        } else {
            widthAmount = Float.parseFloat(widthString);
        }

        //get height amount
        heightString = heightField.getText().toString();
        float heightAmount;
        if (heightString.equals("")) {
            heightAmount = 0;
        } else {
            heightAmount = Float.parseFloat(heightString);
        }

        //calculate area and perimeter
        float areaAmount = widthAmount * heightAmount;
        float perimeterAmount = 2 * widthAmount + 2 * heightAmount;

        //display formatting
        NumberFormat number = NumberFormat.getNumberInstance();

        number.setRoundingMode(RoundingMode.DOWN);
        number.setMinimumFractionDigits(2);
        number.setMaximumFractionDigits(2);

        areaDisplay.setText(number.format(areaAmount));
        perimeterDisplay.setText(number.format(perimeterAmount));

    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }
}
