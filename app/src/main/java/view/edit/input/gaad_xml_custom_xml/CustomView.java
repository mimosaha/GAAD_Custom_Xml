package view.edit.input.gaad_xml_custom_xml;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hp on 6/22/2018.
 */

public class CustomView extends LinearLayout {

    TextView textView;
    EditText editText;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        textView = new TextView(context);
        editText = new EditText(context);

        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(textView);
        addView(editText);

        textView.setText("Title");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.Options, 0, 0);
//        String titleText = a.getString(R.styleable.Options_titleText);
//        a.recycle();
    }
}
