package com.bwie.yinzhiyou.denglu.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.yinzhiyou.denglu.R;
import com.bwie.yinzhiyou.denglu.adapter.NewsAdapter;
import com.bwie.yinzhiyou.denglu.bean.NewsBean;
import com.bwie.yinzhiyou.denglu.presenter.ImplIPresenter;
import com.bwie.yinzhiyou.denglu.view.IView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements IView {

    private View view;
    private FlyBanner mBanner;
    private TextView mTextview;
    private PullToRefreshListView mPullListview;
    private ImplIPresenter mImplIPresenter;
    private int page;
    private NewsAdapter newsAdapter;
    private NewsBean newsBean;
    private String pic_s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmenthome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        page=1;
        initView(view);
        initData();

    }

    private void initData() {
        String path="";
        String path1="http://www.xieast.com/api/news/news.php?page="+page;
        mImplIPresenter.startResult(path1,null,NewsBean.class);
    }


    private void initView(View view) {


        mImplIPresenter = new ImplIPresenter(this);
        mBanner = view.findViewById(R.id.banner);
        mTextview = view.findViewById(R.id.textview);
        mPullListview = view.findViewById(R.id.pull_listview);
        mPullListview.setMode(PullToRefreshBase.Mode.BOTH);
        newsAdapter = new NewsAdapter(getActivity());
        mPullListview.setAdapter(newsAdapter);
       // initData();
        mPullListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });

    }

    @Override
    public void showResponseData(Object data) {
        newsBean = (NewsBean) data;


        if (newsBean ==null||!newsBean.isChecked()){
        Toast.makeText(getActivity(),"无服务",Toast.LENGTH_SHORT).show();
    }else {
        if (page==1){
           newsAdapter.setMlist(newsBean.getData());
        }else {
            newsAdapter.addMlist(newsBean.getData());
        }
        page++;
    }
    mPullListview.onRefreshComplete();
        List<String> list=new ArrayList<>();
        for (int i = 0; i< newsBean.getData().size(); i++){
            pic_s = newsBean.getData().get(i).getThumbnail_pic_s();
            String pic_s02 = newsBean.getData().get(i).getThumbnail_pic_s02();
            String pic_s03 = newsBean.getData().get(i).getThumbnail_pic_s03();
            list.add(pic_s);
            list.add(pic_s02);
            list.add(pic_s03);
        }

        mBanner.setImagesUrl(list);

    }

}
