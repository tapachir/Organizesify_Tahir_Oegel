package com.example.android.organizesifynewtry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class About extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutactivity);


    }
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
}
