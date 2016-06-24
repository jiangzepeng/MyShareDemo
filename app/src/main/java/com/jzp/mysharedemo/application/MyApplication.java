package com.jzp.mysharedemo.application;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Description :
 * Author : jzp
 * Date   : 16/6/23
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        //友盟分享
        initShareKey();
    }
    /**
     * 配置友盟分享的key
     */
    public void initShareKey()
    {
        //微信
        PlatformConfig.setWeixin("wxc3e91b6cab249214", "a4720a29d393b5f1d7a93d7ac8679310");
        //新浪微博
        PlatformConfig.setSinaWeibo("3500461164", "f02dbbd5c1239b41e52b4b2105410f79");
        //QQ
        PlatformConfig.setQQZone("1105124602", "oVW25mws3sE5Sfym");
    }
}
