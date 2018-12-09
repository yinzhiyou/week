package com.bwie.yinzhiyou.denglu.presenter;

import com.bwie.yinzhiyou.denglu.model.IModel;
import com.bwie.yinzhiyou.denglu.model.ImplIModel;
import com.bwie.yinzhiyou.denglu.model.MyCallBack;
import com.bwie.yinzhiyou.denglu.view.IView;

public class ImplIPresenter implements IPresenter {
    private IView mIView;
    private ImplIModel model;

    public ImplIPresenter(IView mIView) {
        this.mIView = mIView;
        model=new ImplIModel();
    }

    @Override
    public void startResult(String str, String pramams, Class clazz) {
        model.setResultData(str, null, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.showResponseData(data);
            }
        });
    }
    public void onDetach(){
        if (mIView!=null){
            mIView=null;
        }
        if (model!=null){
            model=null;
        }
    }
}
