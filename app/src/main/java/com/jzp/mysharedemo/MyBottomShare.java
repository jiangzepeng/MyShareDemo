package com.jzp.mysharedemo;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.sina.weibo.sdk.WeiboAppManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Description :在application里配置主要参数
 * Author : jzp
 * Date   : 16/3/30
 */
public class MyBottomShare implements View.OnClickListener
{
    private Button share_weixin;
    private Button share_weixin_circle;
    private Button share_weibo;
    private PopupWindow mPopupWindow;
    private Activity activity;

    public MyBottomShare(Activity activity)
    {
        this.activity = activity;
    }


    public void show()
    {
        initBottomView();
    }

    private void initBottomView()
    {
        View view = activity.getLayoutInflater().inflate(com.jzp.mylibrary.R.layout.pop_bottomshare, null);

        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setAnimationStyle(com.jzp.mylibrary.R.style.mypopwindow_anim_style);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 在底部显示
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setBackgroundDrawable(dw);
        //设置其他地方变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        //popWindow消失监听方法
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                //当消失后复原变暗的部分
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });

        share_weixin = (Button) view.findViewById(com.jzp.mylibrary.R.id.share_weixin);
        share_weixin_circle = (Button) view.findViewById(com.jzp.mylibrary.R.id.share_weixin_circle);
        share_weibo = (Button) view.findViewById(com.jzp.mylibrary.R.id.share_weibo);

        share_weixin.setOnClickListener(this);
        share_weixin_circle.setOnClickListener(this);
        share_weibo.setOnClickListener(this);

    }

    private UMShareListener umShareListener = new UMShareListener()
    {
        @Override
        public void onResult(SHARE_MEDIA platform)
        {
            Toast.makeText(activity, platform + "分享成功啦", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t)
        {
            Toast.makeText(activity, platform + "分享失败啦", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform)
        {
            Toast.makeText(activity, platform + "分享取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v)
    {
        UMImage image = new UMImage(activity, "http://7xnmrr.com1.z0.glb.clouddn.com/111jianhse.jpg");

        if (v.getId() == com.jzp.mylibrary.R.id.share_weixin)
        {
            mPopupWindow.dismiss();
            new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).withTitle("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image).share();
        }
        else if (v.getId() == com.jzp.mylibrary.R.id.share_weixin_circle)
        {
            mPopupWindow.dismiss();
            new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener).withTitle("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image).share();
        }
        else if (v.getId() == com.jzp.mylibrary.R.id.share_weibo)
        {
            mPopupWindow.dismiss();
            if (WeiboAppManager.getInstance(activity).getWeiboInfo() == null)
            {
                Toast.makeText(activity, "请安装微博客户端", Toast.LENGTH_LONG).show();
            }
            new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener).withTitle("点击网络分享").withText("分享测试").withTargetUrl("https://www.baidu.com").withMedia(image).share();
        }
    }
}
