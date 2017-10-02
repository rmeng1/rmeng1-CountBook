package com.example.rmeng1_countbook;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Record> recordList = new ArrayList<>();
    private ArrayAdapter<Record> adapter;
    private ListView oldRecordList;
    private TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createButton = (Button) findViewById(R.id.create);

        oldRecordList = (ListView) findViewById(R.id.oldRecordList);
        countTextView = (TextView) findViewById(R.id.count_of_people);

        oldRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openedit(position);

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        adapter = new ArrayAdapter<Record>(this,
                R.layout.list_item, recordList);
        oldRecordList.setAdapter(adapter);
        // load all records form Json
        loadAllRecord();
        //String[] tweets = loadFromFile();
        // show the count num of records
        countTextView.setText(String.format("Total: %s", recordList.size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        recordList.clear();
        loadAllRecord();

        adapter.notifyDataSetChanged();
    }

    private void openedit(final int position) {
        Record editRecord = recordList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Edit record")

                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    // when delete is chose
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        adapter.remove(recordList.get(position));
                        recordList.remove(position);
                        adapter.notifyDataSetChanged();
                        saveInFile();
                        countTextView.setText(String.format("Count: %s", recordList.size()));
                    }
                })

                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    // when edit is chose
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, EditRecordActivity.class);

                        Gson gson = new Gson();
                        String recordStr = gson.toJson(recordList.get(position));

                        intent.putExtra("EDIT",recordStr);
                        startActivity(intent);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadAllRecord() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            recordList = gson.fromJson(in, new TypeToken<ArrayList<Record>>(){}.getType());


            adapter.clear();

            adapter.addAll(recordList);

            adapter.notifyDataSetChanged();
            fis.close();
        } catch (FileNotFoundException e) {
            recordList = new ArrayList<Record>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * saves tweet to file in Json format
     *
     * */
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

}
