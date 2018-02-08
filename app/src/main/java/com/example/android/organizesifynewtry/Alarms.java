package com.example.android.organizesifynewtry;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Tahir on 09.12.2017.
 */

public class Alarms extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pending_intent;

    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    private AlarmReceiver alarm;

    Alarms inst;
    Context context;


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


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_activity);

        this.context = this;


        alarmTextView = (TextView) findViewById(R.id.alarmText);

        final Intent myIntent = new Intent (this.context, AlarmReceiver.class);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);


        Button start_alarm = (Button) findViewById(R.id.start_alarm);
        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.add(Calendar.SECOND, 3);

                final int hour = alarmTimePicker.getCurrentHour();
                final int minute = alarmTimePicker.getCurrentMinute();

                setAlarmText("You clicked a" + hour + " and " + minute);

                calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                myIntent.putExtra("extra","yes");

                pending_intent = PendingIntent.getBroadcast(Alarms.this,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

                setAlarmText("Alarm set to " + hour + ":" + minute);


            }
        });

        Button stop_alarm = (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int min = 1;
                int max = 9;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                myIntent.putExtra("extra", "no");
                sendBroadcast(myIntent);

                alarmManager.cancel(pending_intent);
                setAlarmText("Alarm canceled");

            }
        });

    }
    public void setAlarmText(String alarmText){
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onStart(){
        super.onStart();
        inst = this;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }
}
