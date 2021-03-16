package com.viomi.waterpurifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvTemperature,tvGPS,tvDate,tvTime,tvState01,tvState02,tvState03,tvState04,tvValue01,tvValue02,tvValue03,tvValue04;
    private static final String TAG="WP_MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }//end onCreate
    public void initView(){
//        初始化控件
        tvState01=(TextView)findViewById(R.id.tvState01);
        tvState02=(TextView)findViewById(R.id.tvState02);
        tvState03=(TextView)findViewById(R.id.tvState03);
        tvState04=(TextView)findViewById(R.id.tvState04);

        tvValue01=(TextView)findViewById(R.id.tvValue01);
        tvValue02=(TextView)findViewById(R.id.tvValue02);
        tvValue03=(TextView)findViewById(R.id.tvValue03);
        tvValue04=(TextView)findViewById(R.id.tvValue04);

        tvTemperature=(TextView)findViewById(R.id.tvTempuratuer);
        tvGPS=(TextView)findViewById(R.id.tvGPS);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvTime=(TextView)findViewById(R.id.tvTime);
        /********************************************设置点击事件监听*****************************************/
        tvState02.setOnClickListener(this);
        tvState03.setOnClickListener(this);
        tvState04.setOnClickListener(this);
        tvValue02.setOnClickListener(this);
        tvValue03.setOnClickListener(this);
        tvValue04.setOnClickListener(this);
    }//end initView
    public void initData(){
//        初始化数据
//     获取状态栏数据
//    获取天气温度
//    从SDK3.0开始，google不再允许网络请求（HTTP、Socket）等相关操作直接在主线程中，其实本来就不应该这样做，直接在UI线程进行网络操作，会阻塞UI、用户体验不好。
//        new Thread(newworkTask).start();


//    获取gps
//    获取日期
        tvDate.setText(getDate());
//    获取当前时间
        new TimeThread().start();   //启动新线程实时显示时间
    }//end initDate
    /**
     * 网络操作子线程
     */
    Runnable newworkTask=new Runnable() {
        @Override
        public void run() {
            String str="http://www.weather.com.cn/data/sk/101010100.html";
            String jsonStr=WeatherUtil.GetJsonString(str);
            Log.i(TAG,"json------------->"+jsonStr);
            WeatherUtil.GetJsonData2(jsonStr);
        }
    };

    public void onClick(View view){
        switch(view.getId()){
            default:
                Log.i(TAG,"open activity-------"+view.getId());
                Intent intent=new Intent(this,MainActivity2.class);
                startActivity(intent);
                break;
        }
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String str=simpleDateFormat.format(date);
        Log.i(TAG,"str:"+str);
        return str==null?"日期为空":str;
    }//end getDate
    //主线程处理消息并更新UI界面
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    long sysTime=System.currentTimeMillis();
                    CharSequence sysTimeStr= DateFormat.format("hh:mm:ss",sysTime);
//                    Log.i(TAG,"sysTimeStr:"+sysTimeStr);
                    tvTime.setText(sysTimeStr);
                    break;
                default:
                    break;
            }//end switch
        }   //end handleMessage
    };//end Handler
    class TimeThread extends Thread{
        public void run(){
            do{
                try{
                    Thread.sleep(1000);
                    Message msg=new Message();
                    msg.what=1;
                    mHandler.sendMessage(msg);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    Log.i(TAG,"TimeThread error:"+e);
                }
            }while (true);
        }//end run()
    }//end TimeThread
}