package com.bwie.yinzhiyou.denglu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bwie.yinzhiyou.denglu.bean.LoginBean;
import com.bwie.yinzhiyou.denglu.presenter.ImplIPresenter;
import com.bwie.yinzhiyou.denglu.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    /**
     * 输入手机号
     */
    private EditText mEditPhone;
    /**
     * 输入密码
     */
    private EditText mEditPwd;
    /**
     * 记住密码
     */
    private CheckBox mRemember;
    /**
     * 自动登录
     */
    private CheckBox mOutlogin;
    /**
     * 提交
     */
    private Button mCommit;
    private ImageButton mQq;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private ImplIPresenter mImplIPresenter;
    private String phone;
    private String pwd;
    private UMAuthListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */ /**
          * @desc 授权成功的回调
          * @param platform 平台名称
          * @param action 行为序号，开发者用不上
          * @param data 用户资料返回
          */ /**
           * @desc 授权失败的回调
           * @param platform 平台名称
           * @param action 行为序号，开发者用不上
           * @param t 错误原因
           */ /**
            * @desc 授权取消的回调
            * @param platform 平台名称
            * @param action 行为序号，开发者用不上
            */authListener = new UMAuthListener() {
                /**
                 * @desc 授权开始的回调
                 * @param platform 平台名称
                 */
                @Override
                public void onStart(SHARE_MEDIA platform) {

                }

                /**
                 * @desc 授权成功的回调
                 * @param platform 平台名称
                 * @param action 行为序号，开发者用不上
                 * @param data 用户资料返回
                 */
                @Override
                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                    Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                /**
                 * @desc 授权失败的回调
                 * @param platform 平台名称
                 * @param action 行为序号，开发者用不上
                 * @param t 错误原因
                 */
                @Override
                public void onError(SHARE_MEDIA platform, int action, Throwable t) {

                    Toast.makeText(MainActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
                }

                /**
                 * @desc 授权取消的回调
                 * @param platform 平台名称
                 * @param action 行为序号，开发者用不上
                 */
                @Override
                public void onCancel(SHARE_MEDIA platform, int action) {
                    Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
                }
            };

    }

    private void initView() {
        mImplIPresenter = new ImplIPresenter(this);
        preferences = getSharedPreferences("user",MODE_PRIVATE);
        edit = preferences.edit();

        mEditPhone = (EditText) findViewById(R.id.edit_phone);
        mEditPwd = (EditText) findViewById(R.id.edit_pwd);
        mRemember = (CheckBox) findViewById(R.id.remember);
        mOutlogin = (CheckBox) findViewById(R.id.outlogin);
        mCommit = (Button) findViewById(R.id.commit);
        mCommit.setOnClickListener(this);
        mQq = (ImageButton) findViewById(R.id.qq);
        mQq.setOnClickListener(this);
        //
        boolean remember = preferences.getBoolean("remember", false);
        if (remember){
            String name = preferences.getString("phone", null);
            String pwd = preferences.getString("pwd", null);
            mEditPhone.setText(name);
            mEditPwd.setText(pwd);
            mRemember.setChecked(true);

        }
        boolean outlogin = preferences.getBoolean("outlogin", false);
        if (outlogin){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            //intent.putExtra("phone",preferences.getString("phone",null));
            startActivity(intent);
            finish();
        }
        mOutlogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mRemember.setChecked(true);
                    edit.commit();
                }
            }
        });
    }

    @Override
    public void showResponseData(Object data) {
    LoginBean loginBean= (LoginBean) data;
    if (loginBean.getCode()==100){
        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        intent.putExtra("phone",loginBean.getData().getName());

        startActivity(intent);
        finish();
        return;

    }else
        if (loginBean.getCode()==101){
            Toast.makeText(MainActivity.this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else
        if (loginBean.getCode()==102){
            Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else
        if (loginBean.getCode()==103){
            Toast.makeText(MainActivity.this,"手机号码和密码错误",Toast.LENGTH_SHORT).show();
        return;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.commit:
                phone = mEditPhone.getText().toString();
                pwd = mEditPwd.getText().toString();
                String path="http://www.xieast.com/api/user/login.php?username="+phone+"&password="+pwd;
                if (mRemember.isChecked()){
                    edit.putString("phone", phone);
                    edit.putString("pwd", pwd);
                    edit.putBoolean("remember",true);
                    edit.commit();
                }else {

                    edit.clear();
                    edit.commit();
                }
                if (mOutlogin.isChecked()){
                    edit.putBoolean("outlogin",true);
                    edit.commit();
                }
                if (phone.isEmpty()|| pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"手机号码和密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                mImplIPresenter.startResult(path,null,LoginBean.class);
                break;
            case R.id.qq:
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
