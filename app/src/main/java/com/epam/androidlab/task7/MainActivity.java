package com.epam.androidlab.task7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_TIME_VALUE = 60;

    private NumberPicker secondsPicker;
    private NumberPicker minutesPicker;
    private Button btnOk;
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondsPicker = (NumberPicker) findViewById(R.id.seconds_picker);
        minutesPicker = (NumberPicker) findViewById(R.id.minutes_picker);
        secondsPicker.setMaxValue(MAX_TIME_VALUE);
        minutesPicker.setMaxValue(MAX_TIME_VALUE);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setEnabled(false);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pickedSeconds = secondsPicker.getValue();
                int pickedMinutes = minutesPicker.getValue();
                if (pickedMinutes == 0 && pickedSeconds == 0) {
                    Toast.makeText(getApplicationContext(), R.string.choose_time, Toast.LENGTH_SHORT).show();
                } else {
                    Intent serviceIntent = new Intent(getApplicationContext(), TimerService.class);
                    serviceIntent.putExtra(getString(R.string.minutes_extra), pickedMinutes);
                    serviceIntent.putExtra(getString(R.string.seconds_extra), pickedSeconds);
                    startService(serviceIntent);

                    Toast.makeText(getApplicationContext(),
                            getString(R.string.set_timer)+ " " + pickedMinutes+ ":" + pickedSeconds,
                            Toast.LENGTH_SHORT).show();
                    btnOk.setEnabled(false);
                    btnCancel.setEnabled(true);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), TimerService.class));
                btnOk.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        });

    }
}
