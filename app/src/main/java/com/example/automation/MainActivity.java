package com.example.automation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestSurfaceView(this));
    }
}
class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    final String TAG="TEST_SPACE";
    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    public boolean onTouchEvent(MotionEvent event){
        return true;}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new DrawThread(holder).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private volatile boolean running = true;
        Paint paint = new Paint();

        int celler(int a, int b, int c, int[] cod) {
            int sum = a * 4 + b * 2 + c;
            return cod[sum];
        }

        DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void requestStop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    paint.setColor(Color.RED);
                    int rule = 126;
                    int[] cod = new int[8];
                    int a = 500;
                    int b = 1000;
                    for (int z = 0; z < 8; z++) {
                        cod[z] = rule % 2;
                        rule = rule / 2;
                    }
                    int[][] y = new int[a][b + 5];
                    int z = 0;
                    y[0][b / 2 + 1] = 1;
                    for (int i = 0; i < b + 1; i++) {
                        if (y[0][i] == 1) {
                            canvas.drawPoint(0, i, paint);
                        }
                    }
                    for (int i = 1; i < a; i++) {
                        for (int j = 1; j < b + 2; j++) {
                            if (j + 1 == b) {
                                y[i][j]=celler(y[i - 1][j - 1], y[i - 1][j], 0, cod);
                            } else if (j == b) {
                                y[i][j]=celler(y[i - 1][j - 1], 0, 0, cod);
                            } else if (j - 1 == b) {
                                y[i][j]=celler(0, 0, 0, cod);
                            } else {
                                y[i][j] = celler(y[i - 1][j - 1], y[i - 1][j], y[i - 1][j + 1], cod);
                            }
                            if (y[i][j] == 1) {
                                canvas.drawPoint(i, j, paint);

                            }
                        }

                    }
                    getHolder().unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }




            }


        }
    }}

