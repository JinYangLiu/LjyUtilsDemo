package com.ljy.ljyutilsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ljy.util.LjyLogUtil;
import com.ljy.util.LjyStringUtil;
import com.ljy.util.LjySystemUtil;
import com.ljy.util.LjyTimeUtil;
import com.ljy.util.LjyViewUtil;

import java.math.BigDecimal;

public class UseUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_util);
        //log:
        LjyLogUtil.i("log.i----");
        LjyLogUtil.w("log.w----");
        LjyLogUtil.e("log.e----");
        //判断当前栈顶的activity
        boolean isForeground= LjySystemUtil.isForeground(this,UseUtilsActivity.class.getSimpleName());
        LjyLogUtil.i("isForeground:"+isForeground);
        //screen
        int screenWidth= LjyViewUtil.getScreenWidth(this);
        LjyLogUtil.i("screenWidth:"+screenWidth);
        int screenHeight= LjyViewUtil.getScreenHeight(this);
        LjyLogUtil.i("screenHeight:"+screenHeight);
        //string
        LjyLogUtil.i("123asd:"+ LjyStringUtil.isNumber("123asd"));
        LjyLogUtil.i("123:"+ LjyStringUtil.isNumber("123"));

        BigDecimal decimal=new BigDecimal(Double.toString(12.5d));
        LjyLogUtil.i("12.5d:"+ LjyStringUtil.keepAfterPoint(decimal,3));
        //time
        long t1=System.currentTimeMillis();
        LjyLogUtil.i("时间："+ LjyTimeUtil.timestampToDate(t1,null));
        long t2=t1+1000*60*60*1;//+1小时
        long t3=t1+1000*60*60*24;//+24小时
        LjyLogUtil.i("+1是否同一天："+LjyTimeUtil.isSameDay(t1,t2));
        LjyLogUtil.i("+24是否同一天："+LjyTimeUtil.isSameDay(t1,t3));

    }
}
