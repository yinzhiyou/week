package com.bwie.yinzhiyou.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bwie.yinzhiyou.denglu.adapter.LoginAdapter;
import com.bwie.yinzhiyou.denglu.model.MyCallBack;
import com.bwie.yinzhiyou.denglu.presenter.ImplIPresenter;
import com.bwie.yinzhiyou.denglu.view.IView;

public class LoginActivity extends AppCompatActivity implements IView {
    private ImplIPresenter mImplIPresenter;
    private ViewPager mViewpager;
    private TabLayout mTablayout;
    private MyCallBack myCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {

        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager.setAdapter(new LoginAdapter(getSupportFragmentManager()));
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void showResponseData(Object data) {

    }



    public void setMyCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        Intent intent=getIntent();
        String name = intent.getStringExtra("phone");
        myCallBack.setData(name);
    }
}
