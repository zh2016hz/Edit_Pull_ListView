package com.example.administrator.pull_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class custom_Pull_ListView extends RelativeLayout {

    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.edit)
    EditText edit;
    private PopupWindow popupWindow;
    private ArrayList al = new ArrayList();

    public custom_Pull_ListView(Context context) {
        this(context,null);
    }

    public custom_Pull_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
      View   view  =  LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,this);
        ButterKnife.bind(this);
        mockData();
    }
    private void mockData() {
        for (int i = 0; i <50 ; i++) {
            al.add(String.valueOf(i));
        }
    }

    @OnClick(R.id.setting)
    public void onViewClicked() {
        if(popupWindow == null){
            int width  =  edit.getWidth();
            int height =  300;
            popupWindow = new PopupWindow(width,height);
         /*   TextView  tv =  new TextView(this);
            tv.setBackgroundColor(Color.GREEN);*/
            ListView lv =   new ListView(getContext());
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //设置光标位置
                    String  s = al.get(i) +"";
                    edit.setText(s);
                    edit.setSelection(s.length());
                    popupWindow.dismiss();
                }
            });
            lv.setAdapter(baseAdapter);
            popupWindow.setContentView(lv);

            //获得焦点
            popupWindow.setFocusable(true);

            //消失popwindow
            popupWindow.setOutsideTouchable(true);
            //popupWindow.setBackgroundDrawable(new ColorDrawable());
        }

        //显示popWindow  ,这个要放在最后面不然，上面的点击pop 外部不会响应事件
        popupWindow.showAsDropDown(edit);


    }
    private BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_layout,null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final String data = al.get(i) +"";
            viewHolder.num.setText(al.get(i)+"");
            viewHolder.delate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    al.remove(data);
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    };
    public  class ViewHolder{
        ImageView delate;
        TextView num;
        public  ViewHolder(View view){
            delate = view.findViewById(R.id.delate);
            num =  view.findViewById(R.id.num);
        }
    }
}
