package com.example.sam.android_study.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam.android_study.R;
import com.example.sam.android_study.view.ChatActivity;
import com.example.sam.android_study.view.MainActivity;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * Created by sam on 2017/9/28.
 */

public class GroupAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private ProgressDialog progressDialog;

    //ViewHolder静态类
    static class ViewHolder
    {
        public TextView tvGroupId;
        public SwipeMenuLayout swipe_menu_layout;
        public Button btnDelete;
    }

    public GroupAdapter(Context context) {
        this.context = context;
    }

    public GroupAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_group, null);
            holder.tvGroupId = (TextView) convertView.findViewById(R.id.tvGroupId);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            holder.swipe_menu_layout = (SwipeMenuLayout) convertView.findViewById(R.id.swipe_menu_layout);

            holder.tvGroupId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("group item click.");
                    ChatActivity.title = list.get(i);
                    Intent intent = new Intent(context, ChatActivity.class);
                    context.startActivity(intent);
                }
            });
            final ViewHolder finalHolder = holder;
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalHolder.swipe_menu_layout.quickClose();

                    //显示ProgressDialog
                    progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", true, false);

                    //新建线程
                    new Thread(){

                        @Override
                        public void run() {
                            //需要花时间计算的方法
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //向handler发消息
                            handler.sendEmptyMessage(i);
                        }}.start();

                }
            });
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvGroupId.setText(list.get(i));

        return convertView;
    }

    /**
     * 用Handler来更新UI
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            //关闭ProgressDialog
            progressDialog.dismiss();

            int i = msg.what;
            //更新UI
            list.remove(i);
            notifyDataSetChanged();
        }};
}
