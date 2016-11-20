package connectthedots.com.connectthedots.LevelClasses;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import connectthedots.com.connectthedots.R;

/**
 * Created by Stevan on 1/18/2016.
 */
public class Dot {

    public int dotID;
    public int nextDotID;

    public double x;
    public double y;

    public int size;
    public int color;

    public Dot nextDot;

    public String label;

    public boolean edgeActive;

    public Dot(int id, double X, double Y){
        this.x = X;
        this.y = Y;
        this.dotID = id;
        size = R.integer.dot_size;
        color = R.color.dotColour;
        label = Integer.toString(id);
        edgeActive = true;
    }

    public Dot(int id, int nextID, double X, double Y){
        this.x = X;
        this.y = Y;
        this.dotID = id;
        size = 50;//R.integer.dot_size;
        color = R.color.dotColour;
        label = Integer.toString(id);
        nextDotID = nextID;
        edgeActive = false;
        if (nextID == id)
            edgeActive = true;

    }

    public Dot(int id, double X, double Y, int size, int color){
        this.x = X;
        this.y = Y;
        this.dotID = id;
        this.size = size;
        this.color = color;
        label = Integer.toString(id);
        edgeActive = true;

    }

    public Dot(int id, int nextID, double X, double Y, int size, int color){
        this.x = X;
        this.y = Y;
        this.dotID = id;
        this.size = size;
        this.color = color;
        label = Integer.toString(id);
        nextDotID = nextID;
        edgeActive = false;
        if (nextID == id)
            edgeActive = true;
    }

    //public void addConnectedDot(Dot newDot){
    //    connectedDots.add(newDot);
    //}

    public void addConnectedDot(Dot newDot){
        nextDot = newDot;
    }

    public boolean hasConnection(){
        return ( nextDot != null );
    }

    public Dot getNextDot(){
        if (hasConnection())
            return nextDot;
        else
            return null;
    }

    public void drawDot(double w, double h, Canvas drawCanvas, Paint canvasPaint){
        int tempColor = canvasPaint.getColor();
        canvasPaint.setColor(color);
        drawCanvas.drawCircle( (float) (w*x), (float) (h*y), size, canvasPaint );
        drawCanvas.drawText(label, (float) (w*x - 1.5*size), (float) (h*y - 1.5*size), canvasPaint);
        canvasPaint.setColor(tempColor);
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setColor(int color, boolean changeNextDotColor){
        this.color = color;
        if (changeNextDotColor && dotID != nextDotID)
            nextDot.setColor(color);
    }

    public void setNextDot(Dot d){
        nextDot = d;
    }

    public void setNextDot(ArrayList<Dot> dots){
        for(Dot dot : dots){
            if(dot.dotID == nextDotID && dotID != dot.dotID)
                setNextDot(dot);
        }
    }

    public Vector2 getNextDotNorm(double w, double h){
        if (hasConnection()){
            Dot next = getNextDot();
            return next.getPosVect().scale(w,h).subtract(getPosVect().scale(w,h)).getNormalVect();
        }
        else{
            return null;
        }
    }

    public double distanceFrom(double newX, double newY){
        return Math.sqrt((x - newX)*(x - newX) + (y-newY)*(y-newY));
    }

    public double distanceFrom(double newX, double newY, double screenWidth, double screenHeight){
        return Math.sqrt((x*screenWidth - newX)*(x*screenWidth - newX) + (y*screenHeight-newY)*(y*screenHeight-newY));
    }

    public Vector2 getPosVect(){
        return new Vector2(x, y);
    }

    public void setEdgeActive(boolean setEdgeActiveValue){
        edgeActive = setEdgeActiveValue;
    }

    public boolean getEdgeActiveState(){
        return edgeActive;
    }

}
