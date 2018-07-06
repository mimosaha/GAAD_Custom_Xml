package view.edit.input.gaad_xml_custom_xml;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.TintTypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Mimo on 1/29/2018.
 */

public class CustomEditBox extends LinearLayout {

    private EditText inputEditBox;

    private RelativeLayout bottomArea;

    private TextView countTextBox;
    private TextView inputTextBox;
    private View underlineEditBox;

    private int unFocusedColor = R.color.colorPrimary,
            focusedColor = R.color.colorPrimaryDark,
            errorColor = R.color.colorAccent;

    private int editTextCount = 0, errorMaxLength;
    private String inputString;
    private int inputTextColor;
    private float inputTextSize;
    private Context context;

    @SuppressLint("RestrictedApi")
    public CustomEditBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.CustomEditBoxEliment);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        editTextCount = tintTypedArray.getInt(R.styleable.CustomEditBoxEliment_inputCounts, 0);
        inputString = tintTypedArray.getString(R.styleable.CustomEditBoxEliment_inputText);
        inputTextSize = tintTypedArray.getDimension(R.styleable.CustomEditBoxEliment_inputTextSize, 0);
        inputTextColor = tintTypedArray.getColor(R.styleable.CustomEditBoxEliment_inputTextColor, 0);

        if (editTextCount > 0) {
            errorMaxLength = (int) (editTextCount * 0.9);
        }

        inputEditBox = new EditText(context);

        bottomArea = new RelativeLayout(context);

        countTextBox = new TextView(context);
        inputTextBox = new TextView(context);
        underlineEditBox = new View(context);

        initAllViews();
    }

    private void initAllViews() {

        setAllViews();
        controlFocusView();

        addView(inputEditBox);
        addView(underlineEditBox);

        underlineEditBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));

        if (editTextCount > 0 || !TextUtils.isEmpty(inputString)) {
            operateBottomArea();
        }

        unFocusView();
    }

    private void operateBottomArea() {
        Random random = new Random();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        bottomArea.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams paramsInput = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsCount = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        countTextBox.setId(random.nextInt());
        inputTextBox.setId(random.nextInt());

        paramsInput.addRule(RelativeLayout.ALIGN_PARENT_LEFT, inputTextBox.getId());
        paramsInput.addRule(RelativeLayout.LEFT_OF, countTextBox.getId());

        paramsCount.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, countTextBox.getId());
        paramsCount.setMargins(10, 0, 0, 0);

        if (editTextCount > 0) {
            bottomArea.addView(countTextBox, paramsCount);
        }

        if (!TextUtils.isEmpty(inputString)) {
            bottomArea.addView(inputTextBox, paramsInput);
        }

        addView(bottomArea);
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

        if (TextUtils.isEmpty(inputString)) {
            inputString = "Hello";
        }

        inputTextBox.setText(inputString);
        inputTextBox.setMaxLines(1);
        inputTextBox.setEllipsize(TextUtils.TruncateAt.END);

        if (inputTextColor != 0) {
            inputTextBox.setTextColor(inputTextColor);
        }

        if (inputTextSize > 0) {
            inputTextBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputTextSize);
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
