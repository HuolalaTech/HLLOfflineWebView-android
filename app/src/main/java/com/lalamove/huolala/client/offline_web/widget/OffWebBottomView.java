package com.lalamove.huolala.client.offline_web.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lalamove.huolala.client.offline_web.R;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OffWebBottomView
 * @author: kelvin
 * @date: 2022/5/13
 * @description: 底部弹出dialog
 * @history:
 */

public class OffWebBottomView extends BottomView {
    private String mTitle;
    private String mSubTitle1;
    private String mContent1;
    private String mSubTitle2;
    private String mContent2;

    public static OffWebBottomView show(Activity activity, String title, String subTitle1, String content1) {
        return show(activity, title, subTitle1, content1, null, null);
    }

    public static OffWebBottomView show(Activity activity, String title, String subTitle1, String content1, String subTitle2, String content2) {
        OffWebBottomView bottomView = new OffWebBottomView(activity, title, subTitle1, content1, subTitle2, content2);
        bottomView.show(true);
        return bottomView;
    }

    public OffWebBottomView(Activity activity, String title, String subTitle1, String content1, String subTitle2, String content2) {
        super(activity, R.style.BottomViewTheme_Defalut, R.layout.off_web_bottom_dialog_layout);
        mTitle = title;
        mSubTitle1 = subTitle1;
        mContent1 = content1;
        mSubTitle2 = subTitle2;
        mContent2 = content2;
        setAnimation(R.style.BottomToTopAnim);
    }

    @Override
    public void show(boolean canceledOnTouchOutside) {
        super.show(canceledOnTouchOutside);
        initText();
        intiCloseView();
    }

    private void initText() {
        TextView titleTv = convertView.findViewById(R.id.title_tv);
        TextView subTitle1Tv = convertView.findViewById(R.id.sub_title_one_tv);
        TextView content1Tv = convertView.findViewById(R.id.content_one_tv);
        TextView subTitle2Tv = convertView.findViewById(R.id.sub_title_two_tv);
        TextView content2Tv = convertView.findViewById(R.id.content_two_tv);
        setText(titleTv, mTitle);
        setText(subTitle1Tv, mSubTitle1);
        setText(content1Tv, mContent1);
        setText(subTitle2Tv, mSubTitle2);
        setText(content2Tv, mContent2);
        HllRoundBackground.with(activity)
                .corner(16, 16, 0, 0)
                .solid(R.color.white).into(convertView);
    }

    private void setText(TextView textView, String str) {
        if (TextUtils.isEmpty(str)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(str);
        }
    }

    private void intiCloseView() {
        View closeIv = convertView.findViewById(R.id.close_iv);
        View closeTv = convertView.findViewById(R.id.close_tv);
        HllRoundBackground.with(activity)
                .stroke(1,R.color.color_D8DEEB)
                .corner(8)
                .into(closeTv);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        closeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
