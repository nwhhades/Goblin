package com.whiner.tool.widget.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class AutoMarqueeTextView extends AppCompatTextView {

    public AutoMarqueeTextView(@NonNull Context context) {
        this(context, null);
    }

    public AutoMarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoMarqueeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}
