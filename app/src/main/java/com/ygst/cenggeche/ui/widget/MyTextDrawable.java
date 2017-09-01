package com.ygst.cenggeche.ui.widget;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MyTextDrawable {

    private static TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();
    private static ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    public static TextDrawable getTextDrawable(String text) {
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(text.charAt(0)), mColorGenerator.getColor(text));
        return drawable;
    }
}
