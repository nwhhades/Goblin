package com.whiner.tool.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.whiner.tool.R;
import com.whiner.tool.databinding.FragmentAlertDialogBinding;
import com.whiner.tool.ui.base.BaseDialogFragment;

public class AlertFragment extends BaseDialogFragment<FragmentAlertDialogBinding> implements View.OnClickListener {

    protected String title;
    protected String msg;
    protected String btn1_text;
    protected String btn2_text;

    public AlertFragment(String title, String msg, String btn1_text, String btn2_text) {
        this.title = title;
        this.msg = msg;
        this.btn1_text = btn1_text;
        this.btn2_text = btn2_text;
    }

    @Override
    public FragmentAlertDialogBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAlertDialogBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        viewBinding.tvTitle.setText(title);
        viewBinding.tvMsg.setText(msg);
        viewBinding.btn1.setText(btn1_text);
        viewBinding.btn2.setText(btn2_text);

        viewBinding.btn1.setOnClickListener(this);
        viewBinding.btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onActionListener != null) {
            int id = v.getId();
            if (id == R.id.btn1) {
                onActionListener.onAction(this, 1);
            } else if (id == R.id.btn2) {
                onActionListener.onAction(this, 2);
            } else {
                onActionListener.onAction(this, -1);
            }
        }
    }

}
