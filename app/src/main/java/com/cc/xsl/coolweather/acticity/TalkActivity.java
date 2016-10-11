package com.cc.xsl.coolweather.acticity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.adapter.MsgAdapter;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.model.Msg;
import com.cc.xsl.coolweather.util.ResUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushuailong on 2016/10/11.
 */

public class TalkActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_layout);
        initView();
        viewEvent();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.msg_list_view);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        adapter = new MsgAdapter(this,R.layout.msg_item,getMsgs());
        listView.setAdapter(adapter);
    }

    private List<Msg> getMsgs() {
        msgs.add(new Msg("Hello guy.",Msg.TYPE_RECEIVED));
        msgs.add(new Msg("Hello. Who is that?",Msg.TYPE_SEND));
        msgs.add(new Msg("This is Tom. Nice talking to you.",Msg.TYPE_RECEIVED));
        return msgs;
    }

    private void viewEvent() {
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:{
                String content = inputText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    msgs.add(msg);
                    adapter.notifyDataSetChanged();// 当有新消息时，刷新ListView中的显示
                    listView.setSelection(msgs.size());// 将ListView定位到最后一行
                    inputText.setText("");
                }else {
                    ToastUtil.showMessage(ResUtil.getString(R.string.unempty));
                }
                break;
            }
        }
    }
}
