package connectthedots.com.connectthedots;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import connectthedots.com.connectthedots.LevelClasses.DrawingLimits;


/**
 * TODO: document your custom view class.
 */
public class DrawingView extends View {


    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    private int tempColor = paintColor;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;
    private boolean erase=false;

    // drawing specific points
    private boolean drawing = false;
    private float lastX;
    private float lastY;


    DrawingLimits limits;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            setupDrawing();
        }
    }


    private void setupDrawing() {
        // get drawing area setup for interaction
        brushSize = getResources().getInteger(R.integer.small_size);
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        //setupDots();
        //limits = new DrawingLimits(1);
        limits = new DrawingLimits();
    }


    private void redrawDots(double w, double h){
        limits.redrawDots(w, h, drawCanvas, canvasPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // should be h and w
        drawCanvas = new Canvas(canvasBitmap);
        redrawDots(w, h);
        /*drawCanvas.drawCircle(w / 8, h / 2, 40, canvasPaint);
        canvasPaint.setTextSize(75);
        drawCanvas.drawText("1", w / 8 - 75, h / 2 - 75, canvasPaint);
        drawCanvas.drawCircle(7 * w / 8, h / 2, 40, canvasPaint);
        drawCanvas.drawText("2", 7 * w / 8 - 75, h/2 - 75, canvasPaint);*/


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public void setColor(String newColor){
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newSize){
        //update size
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void setErase(boolean isErase){
        //set erase true or false
        erase = isErase;
        if(erase){
            this.setColor("#FFFFFFFF");//set the color to white
            //drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else{
            this.setColor(tempColor);
            //drawPaint.setXfermode(null);
        }

    }

    public void setColor(int newColor){
        invalidate();
        paintColor = newColor;
        drawPaint.setColor(paintColor);
        tempColor = paintColor;
    }

    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        // currently locks into the two dots. Will need to change all of this to be dynamic in the future.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(limits.activateDot(touchX, touchY)) {
                    drawPath.moveTo(touchX, touchY);
                    drawing = true;
                    lastX = touchX;
                    lastY = touchY;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(drawing
                        && limits.isInLine(touchX, touchY)//(Math.abs(touchY - drawCanvas.getHeight()/2) < 150)
                        && limits.isGoingForward(touchX, touchY, lastX, lastY)//(touchX >= lastX-5)
                        ){
                    drawPath.lineTo(touchX, touchY);
                    lastX = touchX;
                    lastY = touchY;
                    //System.out.println(touchX);
                    //System.out.println(touchY);
                }
                else{
                    drawPath.rewind();
                    drawing = false;
                    //drawPath.reset();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(drawing
                        && limits.isAtNextDot(touchX, touchY)//(Math.abs(touchX - 7*drawCanvas.getWidth()/8) < 40) && (Math.abs(touchY - drawCanvas.getHeight()/2) < 40)
                        ) {
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    drawing = false;
                }
                else if(drawing){
                    drawPath.rewind();
                    drawPath.reset();
                }
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

}
