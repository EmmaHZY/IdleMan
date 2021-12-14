package com.example.idleman;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RadioButtonImgUtil {
    /**
     *设置图片显示在控件的上方
     */
    private static void setCompoun(RadioButton button, Drawable drawable) {
        button.setCompoundDrawables(null, drawable, null, null);//只放上面
    }

    /**
     *传递图片资源，设置大小
     */
    private static Drawable getResouDrawable(Context context, int drawables, int size) {
        Drawable drawable = ContextCompat.getDrawable(context,drawables);
        drawable.setBounds(0, 0, size, size);
        return drawable;
    }
    public static void setRadioButtonImg(Context context, int[] drawables, int size,
                                         RadioButton... radioButton) {
        //drawable代表图片集合，size就是想设置的图片大小，radioButton这里代表控件集合
        //可变形参必须放在参数列表最后面,否则系统将无法识别形参个数，参数列表中也不可以同时出现多个可变参数
        for (int i = 0; i < radioButton.length; i++) {
            setCompoun(radioButton[i], getResouDrawable(context, drawables[i],size));
        }
    }
}
