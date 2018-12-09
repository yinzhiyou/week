package com.bwie.yinzhiyou.denglu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.yinzhiyou.denglu.R;
import com.bwie.yinzhiyou.denglu.bean.NewsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsBean.DataBean> mlist;

    public NewsAdapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setMlist(List<NewsBean.DataBean> list) {
        mlist.clear();
        if (list!=null){
            mlist.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addMlist(List<NewsBean.DataBean> list) {

        if (list!=null){
            mlist.addAll(list);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public NewsBean.DataBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder=new ViewHolder(convertView);
        }else {
            holder= (ViewHolder) convertView.getTag();

        }
        holder.bindData(position);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view){
            imageView=view.findViewById(R.id.image);
            textView=view.findViewById(R.id.text_title);
            view.setTag(this);
        }
        public void bindData(int position){
            textView.setText(mlist.get(position).getAuthor_name());
            ImageLoader.getInstance().displayImage(mlist.get(position).getThumbnail_pic_s(),imageView);
        }
    }
}
