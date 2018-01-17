package com.ljy.ljyutilsdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ljy.ljyutils.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;

    private IMyAidlInterface mAidlInterface;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                mAidlInterface.callByService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private boolean isServiceBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMainBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_app_util:
                startActivity(new Intent(mContext, UseUtilsActivity.class));
                break;
            case R.id.btn_glide_util:
                startActivity(new Intent(mContext, GlideUtilActivity.class));
                break;
            case R.id.btn_sendBroadcast:
                Intent intent2 = new Intent();
                intent2.setAction("com.ljy.ljyutils.broadcast");
                intent2.putExtra("info", "我是广播传送的消息啊");
                MainActivity.this.sendBroadcast(intent2);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
                break;
            case R.id.btn_callService:
                //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
                //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
                Intent intent = new Intent("com.ljy.ljyutils.IMyAidlInterface");

                //Android5.0后无法只通过隐式Intent绑定远程Service
                //需要通过setPackage()方法指定包名
                intent.setPackage("com.ljy.ljyutils");

                //绑定服务,传入intent和ServiceConnection对象
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                isServiceBind=true;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isServiceBind) {
            unbindService(mServiceConnection);
            isServiceBind=false;
        }
    }
}
