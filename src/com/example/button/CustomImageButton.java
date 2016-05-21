package com.example.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class CustomImageButton extends ImageButton {
    private String _text = "";
    private int _color = 0;
    private float _textsize = 0f;
    
    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setText(String text){
        this._text = text;
    }
    
    public void setColor(int color){
        this._color = color;
    }
    
    public void setTextSize(float textsize){
        this._textsize = textsize;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextAlign(Align.CENTER);
        paint.setColor(_color);
        paint.setTextSize(_textsize);
       /* int x = (this.getMeasuredWidth() - bitmap.getWidth())/2;
        int y = 0;
        canvas.drawBitmap(bitmap, x, y, null);
        // ������Ҫת������ΪĬ�������Button�е����־�����ʾ
        // ������Ҫ�������ڵײ���ʾ
        canvas.translate(0,(this.getMeasuredHeight()/2) - (int) this.getTextSize());*/
        
        
       //canvas.drawText(_text, canvas.getWidth()/2, (canvas.getHeight()/2)+12, paint);
        canvas.drawText(_text, canvas.getWidth()/2, 0, paint);
    }
}