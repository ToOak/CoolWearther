package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;

public class WidgetActivity extends BaseActivity {
    private TextView resultTextView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private AutoCompleteTextView addressCompleteTextView;
    private RadioGroup marriageRadioGroup;
    private CheckBox sportsCheckBox;
    private CheckBox readCheckBox;
    private CheckBox gameCheckBox;
//    private static final String[] ADDRESS = {"shanghai", "wuxi", "beijing",
//            "anhui", "hebei", "henan", "xuzhou", "yizhou"};
    private static final String[] ADDRESS = {"jiaxian","baliying","wangji","zhuyuanzhai",
            "huangdao","cipa","zhongtou","yezhugou","baimiao","yulintou","fengzhuang","wolou","guangtian"};

    public static Intent getAction(Context context) {
        return new Intent(context, WidgetActivity.class);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        resultTextView = (TextView) findViewById(R.id.resultText);
        nameEditText = (EditText) findViewById(R.id.nameEdit);
        phoneEditText = (EditText) findViewById(R.id.phoneEdit);
        addressCompleteTextView = (AutoCompleteTextView) findViewById(R.id.addressEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ADDRESS);
        addressCompleteTextView.setAdapter(adapter);
        marriageRadioGroup = (RadioGroup) findViewById(R.id.marriageRadioGroup);
        sportsCheckBox = (CheckBox) findViewById(R.id.sportsCheckBox);
        readCheckBox = (CheckBox) findViewById(R.id.readCheckBox);
        gameCheckBox = (CheckBox) findViewById(R.id.gameCheckBox);
    }

    public void buttonClick(View v) {
        if (v.getId() == R.id.okButton) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Name:");
            stringBuilder.append(nameEditText.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("Phone:");
            stringBuilder.append(phoneEditText.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("come from:");
            stringBuilder.append(addressCompleteTextView.getText().toString());
            stringBuilder.append("\n");
            stringBuilder.append("marriage:");
            RadioButton radioButton = (RadioButton) findViewById(marriageRadioGroup
                    .getCheckedRadioButtonId());
            String marriage = radioButton.getText().toString();
            stringBuilder.append(marriage);
            stringBuilder.append("\n");
            stringBuilder.append("hobby:");
            if (sportsCheckBox.isChecked()) {
                stringBuilder.append(sportsCheckBox.getText());
            }
            if (readCheckBox.isChecked()) {
                if (sportsCheckBox.isChecked()) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(readCheckBox.getText());
            }
            if (gameCheckBox.isChecked()) {
                if (sportsCheckBox.isChecked() || readCheckBox.isChecked()) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(gameCheckBox.getText());
            }
            stringBuilder.append("\n");

            resultTextView.setGravity(Gravity.START);
            resultTextView.setTextSize(20);
            resultTextView.setText(stringBuilder);
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_widget;
    }
}
