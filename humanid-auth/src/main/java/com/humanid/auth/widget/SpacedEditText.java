package com.humanid.auth.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.humanid.auth.R;

/**
 * This element inserts spaces between characters in the edit text and expands the width of the
 * spaces using spannables. This is required since Android's letter spacing is not available until
 * API 21.
 */
public final class SpacedEditText extends TextInputEditText {
    private float mProportion;
    private SpannableStringBuilder mOriginalText = new SpannableStringBuilder("");

    public SpacedEditText(Context context) {
        super(context);
    }

    public SpacedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpacedEditText);
        // Controls the ScaleXSpan applied on the injected spaces
        mProportion = array.getFloat(R.styleable.SpacedEditText_spacingProportion, 1);
        array.recycle();
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        mOriginalText = new SpannableStringBuilder(text);
        super.setText(getSpacedOutString(text), TextView.BufferType.SPANNABLE);
    }

    /**
     * Set the selection after recalculating the index intended by the caller.
     */
    @Override
    public void setSelection(int index) {
        // Desired mapping:
        // 0 --> 0
        // 1 --> 1
        // 2 --> 3
        // 3 --> 5
        // 4 --> 7
        // 5 --> 9
        // 6 --> 11

        // Naive transformation
        int newIndex = (index * 2) - 1;

        // Lower bound is 0
        newIndex = Math.max(newIndex, 0);

        // Upper bound is original length * 2 - 1
        newIndex = Math.min(newIndex, (mOriginalText.length() * 2) - 1);

        try {
            super.setSelection(newIndex);
        } catch (IndexOutOfBoundsException e) {
            // For debug purposes only
            throw new IndexOutOfBoundsException(e.getMessage() +
                    ", requestedIndex=" + index +
                    ", newIndex= " + newIndex +
                    ", originalText=" + mOriginalText);
        }
    }

    private SpannableStringBuilder getSpacedOutString(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int textLength = text.length();
        int lastSpaceIndex = -1;

        //Insert a space in front of all characters upto the last character
        //Scale the space without scaling the character to preserve font appearance
        for (int i = 0; i < textLength - 1; i++) {
            builder.append(text.charAt(i));
            builder.append(" ");
            lastSpaceIndex += 2;
            builder.setSpan(new ScaleXSpan(mProportion), lastSpaceIndex, lastSpaceIndex + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        //Append the last character
        if (textLength != 0) builder.append(text.charAt(textLength - 1));

        return builder;
    }

    public Editable getUnspacedText() {
        return mOriginalText;
    }
}
