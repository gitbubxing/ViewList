package com.zcx.viewlist.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.zcx.viewlist.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import androidx.appcompat.widget.AppCompatTextView;

public class ChangeColorView extends AppCompatTextView {

    private String textStr = "但行好事，莫问前程！";
    private Paint paint=new Paint();
    private Paint paint2=new Paint();
    private  int firstTextColor = Color.BLACK;
    private  int secondTextColor = Color.YELLOW;
    private int firstTextSize = dp2px(90) ;
    private int secondTextSize = firstTextSize;
    private int speed= 200;
    private float screenHeight;
    private float screenWidth;
    private float percent;
    float textWidth;
    float right;
    Timer timer;
    public ChangeColorView(Context context) {
        this(context,null);


    }
    public ChangeColorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ChangeColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
        initPaint();
    }

    /**
     * 初始化xml 里面设置的属性
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorView);
        textStr = typedArray.getString(R.styleable.ChangeColorView_text);
        Log.e("zcxcccc","textStr===="+textStr);
       firstTextSize =   typedArray.getDimensionPixelSize(R.styleable.ChangeColorView_first_text_size,firstTextSize);
       secondTextSize = typedArray.getDimensionPixelSize(R.styleable.ChangeColorView_second_text_size,secondTextSize);
       firstTextColor = typedArray.getColor(R.styleable.ChangeColorView_first_text_color,firstTextColor);
       secondTextColor = typedArray.getColor(R.styleable.ChangeColorView_second_text_color,secondTextColor);
       speed = typedArray.getInt(R.styleable.ChangeColorView_speed, speed);
       typedArray.recycle();
    }
    static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
    private void initPaint() {
        paint = new Paint();
        paint.setColor(firstTextColor);
        paint.setTextSize(dp2px(firstTextSize));
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);

        paint2 = new Paint();
        paint2.setColor(secondTextColor);
        paint2.setTextSize(dp2px(secondTextSize));
        paint2.setAntiAlias(true);
        paint2.setTextAlign(Paint.Align.LEFT);

        startAnim(speed);
    }


    private int randomColor(){
        Random random = new Random();
        int red = random.nextInt(200) + 30;
        int gre = random.nextInt(200) + 30;
        int blu = random.nextInt(200) + 30;
        return Color.rgb(red, gre, blu);
    }

    private void startAnim(int spped) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                percent += 0.01;
                percent +=textWidth/textStr.length();
                post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
                if (right > getWidth()) {
//                    timer.cancel();
                    percent = 0f;
                    paint.setColor(paint2.getColor());
                    paint2.setColor(randomColor());
                }
            }
        }, 0, spped);
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (screenHeight <= 0) {
            screenHeight = getHeight();

        }
        if (screenWidth <= 0) {
            screenWidth = getWidth();
        }
//        drawCenterLineX(canvas);
//        drawCenterLineY(canvas);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float baseLine = screenHeight / 2 - (ascent + descent) / 2;
        textWidth = paint.measureText(textStr);

        Log.e("zcx","textStr.length() ===>"+textStr.length());
        Log.e("zcx","width ===>"+textWidth/textStr.length());
        float left = screenWidth / 2 - textWidth / 2;
//        right = left +    textWidth * percent;
        right = left +   percent;


        drawableBottomTV(canvas, baseLine, left, right);


        drawableTopTV(canvas, baseLine, textWidth, left, right);


//        drawCenterText(canvas);
//        drawCenterText1(canvas);

    }

    private void drawableTopTV(final Canvas canvas, float baseLine, float width, float left, float right) {
        canvas.save();

//        Log.e("zcx","right ===>"+right);
//        Log.e("zcx","mPercent ===>"+percent);

        Rect rect = new Rect((int) left, 0, (int) right, (int) screenHeight);
        canvas.clipRect(rect);
        canvas.drawText(textStr, left, baseLine, paint2);
        canvas.restore();
    }

    private void drawableBottomTV(final Canvas canvas, float baseLine, float left, float right) {
        canvas.save();
        //防止过度绘制
        Rect rect = new Rect((int) right, 0, (int) screenWidth, (int) screenHeight);
        canvas.clipRect(rect);

        canvas.drawText(textStr, left, baseLine, paint);
        canvas.restore();
    }

    private void drawCenterText(Canvas canvas) {
        //反面教程
        canvas.save();
//        Paint  paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setAntiAlias(true);
//        paint.setTextSize(80);
        float width = paint.measureText(textStr);
//        paint.setTextAlign(Paint.Align.LEFT);
        float left = getWidth() / 2 - width / 2;
        float left_x = left + width * percent;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = getHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        Rect rect = new Rect((int) left_x, 0, getWidth(), getHeight());
//        canvas.clipRect(rect);
        canvas.drawText(textStr, left, baseline, paint);
        canvas.restore();
    }

    private void drawCenterText1(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
        paint.setTextSize(90);
        float width = paint.measureText(textStr);
//        paint.setTextAlign(Paint.Align.LEFT);
        float left = getWidth() / 2 - width / 2;
        float right = left + width * percent;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = getHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        Rect rect = new Rect((int) left, 0, (int) right, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(textStr, left, baseline, paint);
        canvas.restore();

    }

    private void drawCenterLineX(final Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawLine(screenWidth / 2, 0, screenWidth / 2, screenHeight, paint);
    }

    private void drawCenterLineY(final Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, paint);
    }
}
