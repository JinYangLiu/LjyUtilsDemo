package com.ljy.ljyutilsdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ljy.util.LjyGlideUtil;
import com.ljy.util.LjyLogUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GlideUtilActivity extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    private String imgUrl1;
    private Context mContext = this;
    private Bitmap bitmap;
    private String imgUrl2;
    private String imgUrl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_util);
        ButterKnife.bind(this);
        initUrl();
    }

    private void initUrl() {
        imgUrl1 = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3035449810,1990226329&fm=173&s=5F024F818A1768CC5BB119E00300B0B1&w=640&h=427&img.JPEG";
        imgUrl2="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3488420919,4057488548&fm=173&s=47006CA366434FFD409DEC3A03000073&w=550&h=413&img.JPEG";
        imgUrl3="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=4063553433,1286266556&fm=173&s=BCB0399D4E126FCC30152ACD0300F0A2&w=499&h=424&img.JPEG";
        LjyLogUtil.i(imgUrl1);
    }

    public void onGlideBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clearMemory:
                LjyGlideUtil.clearMemory(mContext);
                break;
            case R.id.btn_pauseRequests:
                LjyGlideUtil.pauseRequests(mContext);
                break;
            case R.id.btn_resumeRequests:
                LjyGlideUtil.resumeRequests(mContext);
                break;
            case R.id.btn_show_iv:
                LjyGlideUtil.loadImg(mContext,imgUrl1,iv1,new LjyGlideUtil. CircleTransform(mContext));
                setImageView2();
                setImageView3();

                break;
            case R.id.btn_clear_iv:
                LjyGlideUtil.clear(iv1);
                iv2.setImageBitmap(null);
                iv3.setImageBitmap(null);
                break;
            default:
                break;
        }
    }

    private void setImageView3() {
        LjyGlideUtil.getBitmap(mContext, imgUrl3, new LjyGlideUtil.CallBack() {
            @Override
            public void onCall(Bitmap resource) {
                iv3.setImageBitmap(resource);
            }
        });
    }

    private void setImageView2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = LjyGlideUtil.getBitmap(mContext, imgUrl2);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    Handler mHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        private WeakReference<GlideUtilActivity> mOuter;

        public MyHandler(GlideUtilActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final GlideUtilActivity outer = mOuter.get();
            if (outer != null) {
                switch (msg.what) {
                    case 1:
                        if (!outer.isFinishing()) {
                            outer.iv2.setImageBitmap(outer.bitmap);
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }
}
