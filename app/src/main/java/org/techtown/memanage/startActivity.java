package org.techtown.memanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;


import android.widget.TextView;
import android.widget.Toast;

public class startActivity extends AppCompatActivity {
    public static final String SELECTED_MODE = "mode";
    Timer timer_;
    private TextView seconds_remaining_curr_;
    private TextView current_status;
    private TextView current_mode_name;
    private String current_mode_text;
    int curr_count;
    int curr_iter_goal;
    int goal_const;
    int curr_resting_time;
    List<TabataData> todoList;
    Iterator<TabataData> iter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button stopbutton = findViewById(R.id.stopbutton);
        stopbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        seconds_remaining_curr_ = (TextView) findViewById(R.id.currentSetText);
        current_status = (TextView) findViewById(R.id.currentSetTextOutofTotal);
        current_mode_name = (TextView) findViewById(R.id.currentMode);

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        if (intent != null){
            Bundle bundle = intent.getExtras();
            MainToStartData data = bundle.getParcelable(SELECTED_MODE);
            if(intent != null){

                todoList = setMode(data.mode_);
                iter = todoList.iterator();

                if (todoList.size() > 0){
                    start_block(iter.next());
                }


            }
        }
    }

    private List<TabataData> setMode(String inputMode)
    {
        List<TabataData> finallist = new ArrayList<TabataData>();

        // [TBD] query from DB using "inputMode"
        // a,b,c

        // [TBD] initialize using db result on the constructor
        //TabataData warmup_tabata = new TabataData(a,b,c);
        TabataData warmup_tabata = new TabataData("Warm-Up");
        TabataData main_tabata = new TabataData("Weight");
        TabataData relax_tabata = new TabataData("Yoga");

        finallist.add(warmup_tabata);
        finallist.add(main_tabata);
        finallist.add(relax_tabata);

        return finallist;
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1)
            {
                // update string
                String displayStr = String.format("%d", curr_count)  + " Seconds";
                seconds_remaining_curr_.setText(displayStr);
                seconds_remaining_curr_.setTextColor(Color.BLUE);
                curr_count--;

                // update progress bar

            }
            else if(msg.what == 2)
            {
                String displayStr = "Rest: "+ String.format("%d", curr_resting_time+curr_count)  + " Seconds";
                seconds_remaining_curr_.setText(displayStr);
                seconds_remaining_curr_.setTextColor(Color.GREEN);
                curr_count--;
            }
            else if(msg.what == 4)
            {
                // end of today's exercise
                Toast toastView = Toast.makeText(getApplicationContext(),"end of today",Toast.LENGTH_LONG );
                toastView.setGravity(Gravity.CENTER, 0, 0);
                toastView.show();

                // show it in a message box and finish current activity

            }
            else if(msg.what == 5){
                current_status.setText(String.format("%d/%d", curr_iter_goal, goal_const));
            }
            else if(msg.what == 6)
            {
                current_mode_name.setText(current_mode_text);
            }

        }
    };

    private void start_exercise(final int timespan)
    {
        mHandler.obtainMessage(5).sendToTarget();
        curr_count = timespan; //seconds
        timer_ = new Timer();
        timer_.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if ((curr_count+curr_resting_time) >= 0) {
                    if (curr_count >= 0) {
                        mHandler.obtainMessage(1).sendToTarget(); // update UI in separate Thread
                    }
                    else{
                        mHandler.obtainMessage(2).sendToTarget();
                    }
                }
                else {
                    timer_.cancel();

                    curr_iter_goal--;
                    if (curr_iter_goal == 0){
                        end_of_curr_block();
                    }
                    else {
                        start_exercise(timespan); // iterate
                    }

                }
            }}, 0, 1000);
    }

    private void start_block(TabataData current)
    {
        curr_resting_time = current.getTime_between_session(); //
        //curr_count = current.getTime_per_session_(); //seconds
        curr_iter_goal = current.getIterate_goal(); // times
        current_mode_text = current.getMode_name();
        goal_const = curr_iter_goal;

        mHandler.obtainMessage(6).sendToTarget();

        start_exercise(current.getTime_per_session_());
    }

    private void end_of_curr_block()
    {
        if (iter.hasNext()){
            start_block(iter.next()); // restart
        }
        else
        {
            mHandler.obtainMessage(4).sendToTarget();
        }
    }

    private void update_overall_progress()
    {
        // update progress bar

        // ----------[update total tabata state]
        // counting current session out of total goal(n/m)

        // update total progress bar

        // take a rest
    }




}
