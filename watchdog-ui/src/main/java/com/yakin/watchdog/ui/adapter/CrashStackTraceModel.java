package com.yakin.watchdog.ui.adapter;

import android.text.SpannableStringBuilder;
import android.util.TypedValue;

import com.yakin.watchdog.ui.utils.ContextUtil;
import com.yakin.watchdog.ui.utils.DensityUtil;
import com.yakin.watchdog.ui.utils.StringUtil;

public class CrashStackTraceModel extends ListViewBaseModel {

    private String stackTrace;

    public CrashStackTraceModel(String stack) {
        this.stackTrace = stack;
    }

    @Override
    protected ListViewBaseHolderView createItemView() throws InstantiationException, IllegalAccessException {
        return CrashStackTraceHolderView.class.newInstance();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void handleView(ListViewBaseHolderView.ListViewBaseHolder holder) {
        CrashStackTraceHolderView.StackTraceHolder h = (CrashStackTraceHolderView.StackTraceHolder) holder;
        h.textView.setTextColor(0xffef4545);
        if(stackTrace.startsWith("at ")) {
            h.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.dp2px(12));
        } else {
            h.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.dp2px(16));
        }
        if(stackTrace.startsWith("at ") && stackTrace.contains(ContextUtil.getContext().getPackageName())) {
            int index = stackTrace.indexOf("(");
            String atPackage = stackTrace.substring(0, index);
            SpannableStringBuilder builder = new SpannableStringBuilder(atPackage);
            builder.append(StringUtil.foregroundColorSpan(stackTrace.substring(index),
                    0xff4978dd));
            h.textView.setText(builder.subSequence(0, builder.length()));
            h.textView.setBackgroundColor(0xff000000);
        } else {
            h.textView.setText(stackTrace);
            h.textView.setBackgroundColor(0xff373636);
        }
    }
}
