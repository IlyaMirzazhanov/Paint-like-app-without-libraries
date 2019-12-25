package com.example.draw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
//@author Ilya Mirzazhanov
public class MainActivity extends Activity {
    Paint btn;
    Paint myPaint;

    private class myView extends View {
        Display disp = ((WindowManager) getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int b = 13;
        int b1;
        int cx = (disp.getWidth() / 6);
        int fg = cx;
        Bitmap fic;
        boolean fill = false;
        int g = 13;
        int g1;
        private Paint hBitmap;
        private final float h_f = ((float) dp(1));
        private Bitmap help;
        private Bitmap mBitmap;
        private Paint mBitmapPaint;
        Canvas mCanvas;
        Path mPath;
        private float mX;
        private float mY;
        int n = 0;
        Bitmap picker2;
        int r = 232;
        int r1;
        int rad = 20;
        Bitmap ru;
        //Rect saved = new Rect(cx * 2, w - cx, cx * 3, w);
        Rect ttr = new Rect(0, 0, disp.getWidth(), disp.getHeight() / 9);
        int ty = (cx * 2);
        int w = disp.getHeight();
        int wi = disp.getWidth();
        int xs = 0;
        int ys = 100;
        Rect filled = new Rect(fg,(w - cx), ty, w);
        Rect del = new Rect(0, w - cx, cx, w);

        public myView(Context context) {
            super(context);
            myPaint = new Paint();
            btn = new Paint();
            myPaint.setAntiAlias(true);
            myPaint.setDither(true);
            myPaint.setColor(Color.rgb(r, g, b));
            myPaint.setStyle(Style.STROKE);
            myPaint.setStrokeJoin(Join.ROUND);
            myPaint.setStrokeCap(Cap.ROUND);
            myPaint.setStrokeWidth(18.0f);
            btn.setAntiAlias(true);
            btn.setDither(true);
            btn.setColor(Color.GREEN);
            btn.setStyle(Style.FILL);
            mPath = new Path();
            mBitmapPaint = new Paint(4);
            hBitmap = new Paint(4);
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            help = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(-1);
            canvas.drawBitmap(mBitmap, 0.0f, 0.0f, mBitmapPaint);
            mBitmapPaint.setColor(Color.WHITE);
            picker2 = BitmapFactory.decodeResource(getResources(), R.drawable.pick);
            Bitmap pic = Bitmap.createScaledBitmap(picker2, disp.getWidth(), disp.getHeight() / 9, true);
            mBitmapPaint.setColor(Color.rgb(r, g, b));
            canvas.drawCircle((float) dp(40), (float) dp(120), (float) dp(20), mBitmapPaint);
            if (n == 1) {
                myPaint.setStrokeWidth(18.0f);
                myPaint.setColor(Color.rgb(r, g, b));
            }
            if (n == 7) {
                myPaint.setStrokeWidth(40.0f);
                myPaint.setColor(-1);
            }
            canvas.drawPath(mPath, myPaint);
            mBitmapPaint.setColor(Color.WHITE);
            btn.setColor(Color.GRAY);
            canvas.drawRect(del, btn);
            btn.setColor(Color.GREEN);
            canvas.drawRect(filled, btn);
            mCanvas.drawBitmap(pic, 0.0f, 0.0f, hBitmap);
            mBitmapPaint.setTextSize(20.0f);
        }

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= h_f || dy >= h_f) {
                mPath.quadTo(mX, mY, (mX + x) / 2.0f, (mY + y) / 2.0f);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, myPaint);
            mPath.reset();
        }

        public void floodFill(Bitmap image, Point node, int targetColor, int replacementColor) {
            int width = image.getWidth();
            int height = image.getHeight();
            int target = targetColor;
            int replacement = replacementColor;
            if (target != replacement) {
                Queue<Point> queue = new LinkedList();
                do {
                    int x = node.x;
                    int y = node.y;
                    while (x > 0 && image.getPixel(x - 1, y) == target) {
                        x--;
                    }
                    boolean spanUp = false;
                    boolean spanDown = false;
                    while (x < width && image.getPixel(x, y) == target) {
                        image.setPixel(x, y, replacement);
                        if (!spanUp && y > 0 && image.getPixel(x, y - 1) == target) {
                            queue.add(new Point(x, y - 1));
                            spanUp = true;
                        } else if (spanUp && y > 0 && image.getPixel(x, y - 1) != target) {
                            spanUp = false;
                        }
                        if (!spanDown && y < height - 1 && image.getPixel(x, y + 1) == target) {
                            queue.add(new Point(x, y + 1));
                            spanDown = true;
                        } else if (spanDown && y < height - 1 && image.getPixel(x, y + 1) != target) {
                            spanDown = false;
                        }
                        x++;
                    }
                    node = (Point) queue.poll();
                } while (node != null);
            }
        }

        public int dp(int n) {
            return (int) ((((float) n) * getResources().getDisplayMetrics().density) + 0.5f);
        }

        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = new Point(x, y);
            int f = mBitmap.getPixel(x, y);
            try {
                int pixel = mBitmap.getPixel(x, y);
                switch (event.getAction()) {
                    case 0:
                        if (del.contains(x, y)) {
                            n = 7;
                        }
                        if (ttr.contains(x, y)) {
                            n = 1;
                            fill = false;
                            r = Color.red(pixel);
                            g = Color.green(pixel);
                            b = Color.blue(pixel);
                        }
                        if (filled.contains(x, y)) {
                            fill = true;
                            ty = cx * 2;
                            fg = cx;
                        }
                        if (!(!fill || filled.contains(x, y) || del.contains(x, y))) {
                            floodFill(mBitmap, p, f, Color.rgb(r, g, b));
                        }
                        touch_start((float) x, (float) y);
                        invalidate();
                        break;
                    case 1:
                        touch_up();
                        invalidate();
                        break;
                    case 2:
                        if (ttr.contains(x, y)) {
                            fill = false;
                            r = Color.red(pixel);
                            g = Color.green(pixel);
                            b = Color.blue(pixel);
                        } else {
                            touch_move((float) x, (float) y);
                        }
                        invalidate();
                        break;
                }
            } catch (IllegalArgumentException e) {
            }
            return true;
        }
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(new myView(this));
    }
}
