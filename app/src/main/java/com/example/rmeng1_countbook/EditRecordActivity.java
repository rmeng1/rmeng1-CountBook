package com.example.rmeng1_countbook;



import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;


import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static java.lang.Integer.parseInt;

public class EditRecordActivity extends MainActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Record> recordList = new ArrayList<>();
    private Record newRecord;

    private EditText personNameEditText;
    private EditText recordDateEditText;
    private EditText neckSizeEditText;
    private EditText bustSizeEditText;

    private EditText commentEditText;
    // the flag for "edit" method
    private int index = -1;
    private Button completeRecordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Button increaseIb=(Button) this.findViewById(R.id.increase);
        Button decreaseIb=(Button) this.findViewById(R.id.decrease);
        Button resetBt=(Button)this.findViewById(R.id.resetBtn);
        Button completBt=(Button)this.findViewById(R.id.complete);

    }

    public void increase1(View view){
        EditText initialEt=(EditText)this.findViewById(R.id.current_value);
        if(initialEt.getText().length()==0)
        {
            initialEt.setText("0");
        }
        int i;
        i= parseInt(initialEt.getText().toString())+1;
        initialEt.setText(String.valueOf(i));
    }
    public void decrease1(View view){
        EditText initialEt=(EditText)this.findViewById(R.id.current_value);
        if(initialEt.getText().length()!=0 ){
            if(parseInt(initialEt.getText().toString())>0 ) {
                int i;
                i = Integer.parseInt(initialEt.getText().toString()) - 1;
                initialEt.setText(String.valueOf(i));
            }
        }
        else
        {

        }
    }
    public void resetOnClick(View view){
        EditText initialEt=(EditText)this.findViewById(R.id.initial_value);
        EditText currentEt=(EditText)this.findViewById(R.id.current_value);
        currentEt.setText(initialEt.getText().toString());
    }



}
