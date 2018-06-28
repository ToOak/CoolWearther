package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cc.xsl.coolweather.MyApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.data.ViewSwitcherItemData;

import java.util.ArrayList;
import java.util.List;

public class CarrouselActivity extends BaseActivity implements View.OnClickListener {

    private static final int NUMBER_PER_SCREEN = 12;
    private int screenNo = -1;
    private int screenCount;

    private ViewSwitcher viewSwitcher;
    private Button preBtn, nextBtn;
    private List<ViewSwitcherItemData> itemData = new ArrayList<>();
    private ViewSwitcherBaseAdapter viewSwitcherBaseAdapter;

    public static Intent getAction(Context context) {
        return new Intent(context, CarrouselActivity.class);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        preBtn = findViewById(R.id.pre_btn);
        nextBtn = findViewById(R.id.next_btn);
        viewSwitcher = findViewById(R.id.view_switcher);

        viewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return CarrouselActivity.this.getLayoutInflater()
                        .inflate(R.layout.slide_gridview, null);
            }
        });

        for (int i = 0; i < 35; i++) {
            ViewSwitcherItemData item = new ViewSwitcherItemData(
                    "item" + i,
                    R.mipmap.ic_launcher
            );
            itemData.add(item);
        }

        screenCount = itemData.size() % NUMBER_PER_SCREEN == 0 ?
                itemData.size() / NUMBER_PER_SCREEN :
                itemData.size() / NUMBER_PER_SCREEN + 1;

        viewSwitcherBaseAdapter = new ViewSwitcherBaseAdapter();

        nextBtn.setOnClickListener(this);
        preBtn.setOnClickListener(this);

        nextViewSwitcher();
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_carrousel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pre_btn: {
                preViewSwitcher();
                break;
            }
            case R.id.next_btn: {
                nextViewSwitcher();
                break;
            }
        }
    }

    private void preViewSwitcher() {
        if (screenNo > 0) {
            screenNo--;
            viewSwitcher.setInAnimation(this, R.anim.slid_in_left);
            viewSwitcher.setOutAnimation(this, R.anim.slid_out_right);
            ((GridView) viewSwitcher.getNextView()).setAdapter(viewSwitcherBaseAdapter);
            viewSwitcher.showPrevious();
        }
    }

    private void nextViewSwitcher() {
        if (screenNo < screenCount - 1) {
            screenNo++;
            viewSwitcher.setInAnimation(this, R.anim.slid_in_right);
            viewSwitcher.setOutAnimation(this, R.anim.slid_out_left);
            ((GridView) viewSwitcher.getNextView()).setAdapter(viewSwitcherBaseAdapter);
            viewSwitcher.showNext();
        }
    }


    class ViewSwitcherBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (screenNo == screenCount - 1
                    && itemData.size() % NUMBER_PER_SCREEN != 0) {
                return itemData.size() % NUMBER_PER_SCREEN;
            }
            return NUMBER_PER_SCREEN;
        }

        @Override
        public ViewSwitcherItemData getItem(int position) {
            return itemData.get(
                    screenNo * NUMBER_PER_SCREEN + position
            );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(CarrouselActivity.this).inflate(
                        R.layout.item_slid_gridview, null
                );
                holder = new ViewHolder();
                holder.iconImg = convertView.findViewById(R.id.icon_img);
                holder.nameTv = convertView.findViewById(R.id.name_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iconImg.setImageResource(getItem(position).getIcon());
            holder.nameTv.setText(getItem(position).getName());
            return convertView;
        }

        class ViewHolder {
            ImageView iconImg;
            TextView nameTv;
        }
    }
}
