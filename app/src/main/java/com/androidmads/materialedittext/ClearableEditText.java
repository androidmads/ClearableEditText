package com.androidmads.materialedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mushtaq on 02/09/2018.
 * Reference 1: https://stackoverflow.com/questions/13135447/setting-onclicklistner-for-the-drawable-right-of-an-edittext
 * Reference 2: https://stackoverflow.com/questions/30953449/design-android-edittext-to-show-error-message-as-described-by-google
 */

public class ClearableEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable drawableTemp;
    private Drawable drawableRight;

    int actionX, actionY;

    private DrawableClickListener clickListener;

    public ClearableEditText(Context context) {
        super(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (right != null) {
            drawableRight = right;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (text.toString().length() > 0) {
            drawableRight = drawableTemp;
            setCompoundDrawables(null, null, drawableRight, null);
        } else {
            drawableTemp = drawableRight;
            setCompoundDrawables(null, null, null, null);
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();
            if (drawableRight != null) {

                bounds = drawableRight.getBounds();
                int x, y;
                int extraTapArea = 13;

                /*
                  IF USER CLICKS JUST OUT SIDE THE RECTANGLE OF THE DRAWABLE
                  THAN ADD X AND SUBTRACT THE Y WITH SOME VALUE SO THAT AFTER
                  CALCULATING X AND Y CO-ORDINATE LIES INTO THE DRAWBABLE
                  BOUND. - this process help to increase the tappable area of
                  the rectangle.
                 */
                x = actionX + extraTapArea;
                y = actionY - extraTapArea;

                /*
                 Since this is right drawable subtract the value of x from the width
                 of view. so that width - tapped_area will result in x co-ordinate in drawable bound.
                */
                x = getWidth() - x;

                 /*x can be negative if user taps at x co-ordinate just near the width.
                  e.g views width = 300 and user taps 290. Then as per previous calculation
                  290 + 13 = 303. So subtract X from getWidth() will result in negative value.
                  So to avoid this add the value previous added when x goes negative.
                 */

                if (x <= 0) {
                    x += extraTapArea;
                }

                 /*
                 If result after calculating for extra tap-able area is negative.
                 assign the original value so that after subtracting
                 extra tapping area value doesn't go into negative value.
                 */

                if (y <= 0)
                    y = actionY;

                /*
                If drawable bounds contains the x and y points then move ahead.
                */
                if (bounds.contains(x, y) && clickListener != null) {
                    clickListener.onClick();
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
                return super.onTouchEvent(event);
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableRight = null;
        super.finalize();
    }

    public void setDrawableClickListener(DrawableClickListener listener) {
        this.clickListener = listener;
    }

    public interface DrawableClickListener {
        void onClick();
    }
}
