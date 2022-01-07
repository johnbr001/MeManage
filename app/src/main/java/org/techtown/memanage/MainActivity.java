package org.techtown.memanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_START_ACTIVITY = 101;
    public static final int REQUEST_FOOTPRINT_ACTIVITY = 102;
    public static final int REQUEST_SETTING_ACTIVITY = 103;
    public static final String SELECTED_MODE = "mode";

    private RadioGroup mode_group_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mode_group_ = (RadioGroup) findViewById(R.id.mode_group);

        Button startButton = findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), startActivity.class);
                MainToStartData data = new MainToStartData(checkCurrentMode());
                intent.putExtra(SELECTED_MODE, data);

                startActivityForResult(intent, REQUEST_START_ACTIVITY);
            }
        });

        Button footprintButton = findViewById(R.id.footprintbutton);
        footprintButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                startActivityForResult(intent, REQUEST_FOOTPRINT_ACTIVITY);
            }
        });

        Button settingButton = findViewById(R.id.setbutton);
        settingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, REQUEST_SETTING_ACTIVITY);
            }
        });
    }

    private String checkCurrentMode(){
        int id = mode_group_.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(id);
        return rb.getText().toString();
    }
}
