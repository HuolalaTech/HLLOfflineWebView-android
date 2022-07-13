package com.lalamove.huolala.client.offline_web.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lalamove.huolala.client.offline_web.R;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OffwebButton
 * @author: kelvin
 * @date: 2022/5/13
 * @description: demo 首页按钮
 * @history:
 */

public class OffWebButton extends FrameLayout {
    private int mImageResource = R.drawable.off_web_logo_icon;
    private String mTitle = "";

    public OffWebButton(@NonNull Context context) {
        this(context, null);
    }

    public OffWebButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OffWebButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OffWebButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OffWebButton);
            mImageResource = ta.getResourceId(R.styleable.OffWebButton_image, R.drawable.off_web_logo_icon);
            mTitle = ta.getString(R.styleable.OffWebButton_text);
            ta.recycle();
        }
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.off_web_btn_layout, this);
        ImageView iconIv = inflate.findViewById(R.id.item_icon);
        iconIv.setImageResource(mImageResource);
        TextView titleTv = inflate.findViewById(R.id.title_tv);
        titleTv.setText(mTitle);
        HllRoundBackground.with(getContext())
                .corner(4)
                .stroke(1, R.color.white)
                .into(inflate);
    }
}
