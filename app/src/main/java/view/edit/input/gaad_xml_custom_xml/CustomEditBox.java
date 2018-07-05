package view.edit.input.gaad_xml_custom_xml;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.TintTypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mimo on 1/29/2018.
 */

public class CustomEditBox extends LinearLayout {

    private EditText inputEditBox;
    private TextView countTextBox;
    private View underlineEditBox;

    private int unFocusedColor = R.color.colorPrimary,
            focusedColor = R.color.colorPrimaryDark,
            errorColor = R.color.colorAccent;

    private int editTextCount = 0, errorMaxLength;
    private Context context;

    @SuppressLint("RestrictedApi")
    public CustomEditBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.CustomEditBoxEliment);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        editTextCount = tintTypedArray.getInt(R.styleable.CustomEditBoxEliment_inputCounts, 0);
        if (editTextCount > 0) {
            errorMaxLength = (int) (editTextCount * 0.9);
        }

        inputEditBox = new EditText(context);
        countTextBox = new TextView(context);
        underlineEditBox = new View(context);

        initAllViews();
    }

    private void initAllViews() {

        setAllViews();
        controlFocusView();

        addView(inputEditBox);
        addView(underlineEditBox);

        underlineEditBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));

        if (editTextCount > 0) {
            addView(countTextBox);
        }

        unFocusView();
    }

    private void controlFocusView() {
        inputEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                controlCount(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputEditBox.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    focusView();
                } else {
                    unFocusView();
                }
            }
        });
    }

    private void controlCount(int updatingLength) {
        if (editTextCount > 0) {
            countTextBox.setText(updatingLength + "/" + editTextCount);

            if (updatingLength >= errorMaxLength) {
                errorView();
            } else {
                focusView();
            }
        } else {
            focusView();
        }
    }

    private void setAllViews() {

        if (editTextCount > 0) {
            inputEditBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(editTextCount)});
            inputEditBox.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        }

        inputEditBox.setBackgroundColor(Color.TRANSPARENT);

        if (editTextCount > 0) {
            countTextBox.setText("0/" + editTextCount);
            countTextBox.setGravity(Gravity.RIGHT);
        }
    }

    private void unFocusView() {
        int color = unFocusedColor;

        underlineEditBox.setBackgroundColor(context.getResources().getColor(color));
        if (editTextCount <= 0) return;
        countTextBox.setTextColor(context.getResources().getColor(color));
    }

    private void focusView() {
        int color = focusedColor;

        underlineEditBox.setBackgroundColor(context.getResources().getColor(color));
        if (editTextCount <= 0) return;
        countTextBox.setTextColor(context.getResources().getColor(color));
    }

    private void errorView() {
        int color = errorColor;

        underlineEditBox.setBackgroundColor(context.getResources().getColor(color));
        if (editTextCount <= 0) return;
        countTextBox.setTextColor(context.getResources().getColor(color));
    }
}
