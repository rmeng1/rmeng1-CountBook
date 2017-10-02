package com.example.rmeng1_countbook;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class CreateRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String FILENAME = "file.sav";
    private ArrayList<Record> recordList = new ArrayList<>();
    private Record newRecord;
    private EditText nameEditText;
    private EditText initialEditText;
    private EditText currentEditText;
    private EditText commentEditText;


    private int index = -1;
    private Button completeRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);


        nameEditText = (EditText) findViewById(R.id.name);
        initialEditText = (EditText) findViewById(R.id.initial_value);
        currentEditText = (EditText) findViewById(R.id.current_value);

        commentEditText = (EditText) findViewById(R.id.comment);


        completeRecordButton = (Button) findViewById(R.id.complete);
        completeRecordButton.setOnClickListener(this);


        loadAllRecord();

        Gson gson = new Gson();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {

            String jsonStr = extras.getString("EDIT");

            newRecord = gson.fromJson(jsonStr, Record.class);

            index = getIndex(newRecord);

            nameEditText.setText(newRecord.getName());

            if (newRecord.getComment() != null) {
                commentEditText.setText(newRecord.getComment());
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == completeRecordButton) {
            createRecord();
        }
    }

    protected void createRecord() {
        Intent intent = new Intent(this, MainActivity.class);
        // Get record name from user input
        String name = nameEditText.getText().toString();
        Record newRecord = new Record();

        if (name.isEmpty()) {
            // show the warning
            nameEditText.setError("name is empty!");
            return;
        }

        if (!initialEditText.getText().toString().isEmpty()) {
            String initial =initialEditText.getText().toString();

        }
        if (!currentEditText.getText().toString().isEmpty()) {
            String current = currentEditText.getText().toString();

        }

        if (!commentEditText.getText().toString().isEmpty()) {
            String comment = commentEditText.getText().toString();
            newRecord.setComment(comment);
        }

        try {

            newRecord.setName(name);


                recordList.add(newRecord);

            saveInFile();
            finish();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            // save the record list to Json
            Gson gson = new Gson();
            gson.toJson(recordList, out);

            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void loadAllRecord() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            // load the records to record list
            Gson gson = new Gson();

            recordList = gson.fromJson(in, new TypeToken<ArrayList<Record>>(){}.getType());
            fis.close();
        } catch (FileNotFoundException e) {
            recordList = new ArrayList<Record>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    private int getIndex(Record record) {
        int index = 0;
        for (Record r : recordList) {
            if (r.getName().equals(record.getName())) {

                break;
            }
            ++index;
        }
        return index;
    }



}
