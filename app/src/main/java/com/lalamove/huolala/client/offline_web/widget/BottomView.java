package com.lalamove.huolala.client.offline_web.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: BottomView
 * @author: kelvin
 * @date: 2022/5/13
 * @description: 底部弹出dialog
 * @history:
 */

public class BottomView {

    protected Activity activity;
    protected int theme;
    protected Dialog dialog;
    protected int animationStyle;
    protected int resource;
    protected View convertView;
    private OnDismissListener mDismissListener;

    public BottomView(Activity activity, int theme, int resource) {
        this.theme = theme;
        this.activity = activity;
        this.resource = resource;

    }

    public void show(boolean canceledOnTouchOutside) {
        try {
            if (dialog == null) {
                if (this.theme == 0) {
                    this.dialog = new Dialog(activity);
                } else {
                    this.dialog = new Dialog(activity, this.theme);
                }
                this.dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
                this.dialog.setCancelable(canceledOnTouchOutside);
                this.dialog.getWindow().requestFeature(1);

                if (convertView == null) {
                    convertView = activity.getLayoutInflater().inflate(resource, null, false);
                }
                if (convertView.getParent() != null && convertView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) convertView.getParent()).removeView(convertView);
                }

                this.dialog.setContentView(convertView);
                this.dialog.setOnDismissListener(new OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface arg) {
                        onDestroy();
                        dialog = null;
                    }
                });
            }
            Window wm = this.dialog.getWindow();
            WindowManager m = wm.getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = wm.getAttributes();
            p.width = d.getWidth();
            p.gravity = 80;
            p.height = WindowManager.LayoutParams.WRAP_CONTENT;

            if (this.animationStyle != 0) {
                wm.setWindowAnimations(this.animationStyle);
            }
            wm.setAttributes(p);

            if (activity != null && !activity.isFinishing()) {
                this.dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnimation(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    public View getView() {
        return this.convertView;
    }

    public boolean isShown() {
        return (dialog != null && dialog.isShowing());
    }

    public void dismiss() {
        if (!isShown()) {
            return;
        }
        this.dialog.dismiss();
    }

    public void onDestroy() {
        if (mDismissListener != null) {
            mDismissListener.onDismiss(dialog);
        }
    }

    public void setDismissListener(OnDismissListener mDismissListener) {
        this.mDismissListener = mDismissListener;
    }


    public Dialog getDialog() {
        return dialog;
    }
}
