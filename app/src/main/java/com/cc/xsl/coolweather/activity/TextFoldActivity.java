package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;

public class TextFoldActivity extends BaseActivity implements View.OnClickListener {
    public static final String CONTENT = "content";
    private Button moreBtn;
    private TextView descShortTv, descLongTv, originalTv, resultTv;
    private FrameLayout descFl;
    private String content = "";
    private ImageView moreLineIv;
    private boolean isInited = false;
    private boolean isShowShortText = true;
    private String s = "";

    public static Intent getIntent(Context context, String content) {
        Intent intent = new Intent(context, TextFoldActivity.class);
        intent.putExtra(CONTENT, content);
        return intent;
    }

    @Override
    protected void loadData() {
        s = "012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789";
        descShortTv.setText(content);
        descLongTv.setText(content);
        originalTv.setText(s);
        originalTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LogUtil.e("onGlobalLayout");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    originalTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    originalTv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

//                initResultTv();
            }
        });

        ViewTreeObserver viewTreeObserver = descFl.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                LogUtil.e("onPreDraw:" + isInited);
                if (isInited) {
                    return true;
                }
                if (measureDescription(descShortTv, descLongTv)) {
                    moreBtn.setVisibility(View.VISIBLE);
                    moreLineIv.setVisibility(View.VISIBLE);
                } else {
                    moreBtn.setVisibility(View.GONE);
                    moreLineIv.setVisibility(View.GONE);
                }
                isInited = true;
                return true;
            }
        });
    }

    private void initResultTv() {
        TextPaint paint = originalTv.getPaint();
        int paddingLeft = originalTv.getPaddingLeft();
        int paddingRight = originalTv.getPaddingRight();
        //给省略号留的长度（但是，因为字符占位问题，获取的这个长度，要比省略号的三个点的长度大一些）
        float moreTxt = originalTv.getTextSize() * 3;
        //乘2，是代表2行的意思，减去moreText，是给省略号预留一点位置
        float availableTextWidth = (originalTv.getWidth() - paddingLeft - paddingRight) * 2 ;
        CharSequence ellipsizeStr = TextUtils.ellipsize(s, paint, availableTextWidth, TextUtils.TruncateAt.END);
        resultTv.setText(ellipsizeStr);

        LogUtil.e("paddingLeft: " + paddingLeft + "\tpaddingRight: "
                + paddingRight + "\tmoreTxt:" + moreTxt + "\tellipsizeStr: " + ellipsizeStr);
    }

    /**
     * 计算描述信息是否过长
     *
     * @return
     */
    private boolean measureDescription(TextView shortView, TextView longView) {
        int shortHeight = shortView.getHeight();
        int longHeight = longView.getHeight();
        if (longHeight > shortHeight) {
            shortView.setVisibility(View.VISIBLE);
            longView.setVisibility(View.GONE);
            return true;
        }
        shortView.setVisibility(View.GONE);
        longView.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    protected void initView() {
        moreBtn = (Button) findViewById(R.id.more_btn);
        descLongTv = (TextView) findViewById(R.id.desc_long_tv);
        descShortTv = (TextView) findViewById(R.id.desc_short_tv);
        descFl = (FrameLayout) findViewById(R.id.desc_fl);
        moreLineIv = (ImageView) findViewById(R.id.more_line_iv);
        originalTv = (TextView) findViewById(R.id.original_tv);
        resultTv = (TextView) findViewById(R.id.result_tv);

        moreBtn.setOnClickListener(this);
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            content = intent.getStringExtra(CONTENT);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text_fold;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_btn: {
                if (isShowShortText) {
                    descShortTv.setVisibility(View.GONE);
                    descLongTv.setVisibility(View.VISIBLE);
                } else {
                    descLongTv.setVisibility(View.GONE);
                    descShortTv.setVisibility(View.VISIBLE);
                }
                toggleMoreBtn();
                isShowShortText = !isShowShortText;
                break;
            }
        }
    }

    /**
     * 更改更多按钮的文本
     */
    private void toggleMoreBtn() {
        String text = moreBtn.getText().toString();
        String moreText = getString(R.string.more);
        String lessText = getString(R.string.less);
        if (moreText.equals(text)) {
            moreBtn.setText(lessText);
        } else {
            moreBtn.setText(moreText);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.e("onWindowFocusChanged");
        initResultTv();
    }
}
