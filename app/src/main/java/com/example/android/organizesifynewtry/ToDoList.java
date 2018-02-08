package com.example.android.organizesifynewtry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ToDoList extends AppCompatActivity {



   ListView lvItems;
   Button btnAdd;
   EditText inputTask;

   DatabaseHandler db;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.notepad:
                Toast.makeText(this, "NotePad", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, NoteList.class));
                finish();
                return true;
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, About.class));
                finish();
                return true;
            case R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            case R.id.toDoList:
                Toast.makeText(this, "TodoList", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ToDoList.class));
                finish();
                return true;
            case R.id.alarm:
                Toast.makeText(this, "Alarm", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Alarms.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);



        lvItems =  findViewById(R.id.lvItems);
        btnAdd = findViewById(R.id.btn_add);
        inputTask = findViewById(R.id.input_task);

       // readItems();

        loadListData();

        db = new DatabaseHandler(getApplicationContext());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = inputTask.getText().toString();

                if (task.trim().length() > 0){

                    db.insertTask(task);

                    inputTask.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputTask.getWindowToken(), 0);

                    loadListData();

                }
                else {
                    Toast.makeText(getApplicationContext(), "PLEASE ENTER TASK NAME", Toast.LENGTH_LONG).show();

                }
            }
        });
        setupListViewListener();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


/*
    public void onAddItem(View view){
        EditText etNewItem =  findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }*/



    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                String selectedFromList =(lvItems.getItemAtPosition(pos).toString());
                Toast.makeText(getApplicationContext(), selectedFromList +" done",Toast.LENGTH_LONG).show();
                db.deleteTask(selectedFromList);
                loadListData();

                return true;
            }
        });
    }

    private void loadListData() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        List<String> tasks = db.getAllTasks();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);

        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lvItems.setAdapter(dataAdapter);
    }



}
