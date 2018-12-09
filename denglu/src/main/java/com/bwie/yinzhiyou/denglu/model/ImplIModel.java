package com.bwie.yinzhiyou.denglu.model;

import com.bwie.yinzhiyou.denglu.utils.NetUtils;

public class ImplIModel implements IModel {

    @Override
    public void setResultData(String trl, String pramams, Class clazz, final MyCallBack myCallBack) {
        NetUtils.getInstance().getResult(trl, clazz, new NetUtils.CallBack() {
            @Override
            public void onSuccess(Object o) {
                myCallBack.setData(o);
            }
        });
    }
}
