package com.viomi.waterpurifier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView tvTemperature01,tvTemperature02,tvTemperature03,tvBcak;
    private SeekBar sb01,sb02,sb03;
    private static final String TAG = "WP_MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_detail_layout);
        initView();
        initDate();
    }

    /**
     * 冷藏室：0-10
     * 冷冻室：-18 - 0
     * 变温室：-18- 10
     */
    private void initDate() {
        tvBcak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sb01.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //冷藏室：0~10
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTemperature01.setText(Integer.toString(progress)+"°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb02.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //冷冻室：-18 ~ 0
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTemperature02.setText((progress-18)+"°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb03.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //变温室：-18 ~ 10
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTemperature03.setText((progress-18)+"°C");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        tvTemperature01=(TextView)findViewById(R.id.temperature01);
        tvTemperature02=(TextView)findViewById(R.id.temperature02);
        tvTemperature03=(TextView)findViewById(R.id.temperature03);
        tvBcak=(TextView)findViewById(R.id.tvBack);
        sb01=(SeekBar)findViewById(R.id.seekBar01);
        sb02=(SeekBar)findViewById(R.id.seekBar02);
        sb03=(SeekBar)findViewById(R.id.seekBar03);
        tvBcak=(TextView)findViewById(R.id.tvBack);
    }

}
