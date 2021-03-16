package com.viomi.waterpurifier;

import android.util.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;

/**
 * 天气工作类
 */
public class WeatherUtil {
    private static String TAG="wp_WeatherUtil_tag";
    private static String weatherUrl = "https://wthrcdn.etouch.cn/weather_mini?city=";
    /**
     * 通过城市名称获取该城市的天气信息
     */
    public static String GetWeatherData(String cityname){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
//            String jsonURL=weatherUrl + cityname;
            String jsonURL="https://www.weather.com.cn/data/sk/101010100.html" ;
            Log.i(TAG,"json========="+jsonURL);
            URL url = new URL(weatherUrl + cityname);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            reader = new BufferedReader(isr);
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line + " ");
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i(TAG,"UnsupportedEncodingException:"+e.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i(TAG,"MalformedURLException:"+e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"Exception:"+e.toString());
        }
        return sb.toString();
    }//end GetWeatherData

    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     * @param weatherInfobyJson
     * @return
     */
    public static WeatherInfo GetWeather(String weatherInfobyJson){
        JSONObject dataOfJson = JSONObject.fromObject(weatherInfobyJson);   // json天气数据
        if(dataOfJson.getInt("status") != 1000){
            return null;
        }
        // 创建WeatherInfo对象，提取所需的天气信息
        WeatherInfo weatherInfo = new WeatherInfo();

        // 获取当前日期：日期、星期
        Calendar cal = Calendar.getInstance();    							     // Calendar类的实例化
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");  // 时间的格式化
        weatherInfo.setDate(sdf1.format(cal.getTime()));                // 时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        weatherInfo.setWeek(sdf2.format(cal.getTime()));                // 星期

        // 从json数据中提取数据：城市、温度、小提醒
        dataOfJson = JSONObject.fromObject(dataOfJson.getString("data"));
        weatherInfo.setCityname(dataOfJson.getString("city"));            // 城市
        weatherInfo.setTemp(dataOfJson.getString("wendu"));               // 温度
        weatherInfo.setTips(dataOfJson.getString("ganmao"));              // 小提示

        // 获取今天的天气预报信息：最高温度、最低温度、天气
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        JSONObject result = forecast.getJSONObject(0);
        weatherInfo.setHighTemp(result.getString("high").substring(2));   // 最高气温
        weatherInfo.setLowTemp(result.getString("low").substring(2));     // 最低气温
        weatherInfo.setWeather(result.getString("type"));                 // 天气
        return weatherInfo;
    }//end GetWeather

    /**
     * //获取json字符串，参考：https://blog.csdn.net/yayun0516/article/details/45765893
     * @param urlStr    String str="http://www.weather.com.cn/data/sk/101010100.html";
     * @return
     */
    public static String GetJsonString(String urlStr){
        URL url=null;
        StringBuffer sb =new StringBuffer();
        String line=null;
        BufferedReader buffer=null;
        try{
            url=new URL(urlStr);
            HttpURLConnection urlConn =(HttpURLConnection)url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setConnectTimeout(8000);
            urlConn.setReadTimeout(8000);
            buffer=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line=buffer.readLine())!=null){
                sb.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG,"Exception-----------"+e);
        }finally {
            try {
                buffer.close();
            }catch (IOException e){
                e.printStackTrace();
                Log.i(TAG,"IOException-----------"+e);
            }
        }
        return sb.toString();
    }//end GetJsonString

    /**
     * //    解析json字符串
     * @param jonString
     */
    public static void GetJsonData2(String jonString){
        try {
            org.json.JSONObject jsonObject=new org.json.JSONObject(jonString);
            org.json.JSONObject weatherinfo=jsonObject.getJSONObject("weatherinfo");
            Log.i(TAG,"city---------"+weatherinfo.optString("city"));
            Log.i(TAG,"temp---------"+weatherinfo.optString("temp"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG,"JSONException---------"+e);
        }
        return;
    }//end GetJsonData2
}
