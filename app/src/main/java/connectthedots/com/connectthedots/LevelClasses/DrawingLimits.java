package connectthedots.com.connectthedots.LevelClasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import connectthedots.com.connectthedots.DataRetrieve.LevelBuilder;

/**
 * Created by Stevan on 2/14/2016.
 */
public class DrawingLimits {

    private static double regionWidth = 150;

    private float lastX;
    private float lastY;

    private double screenWidth;
    private double screenHeight;

    private int activeDotID;

    //ArrayList<Dot> dots = new ArrayList<Dot>();
    ArrayList<Dot> dots = new ArrayList<Dot>();

    public DrawingLimits(){
        setupDots();
        screenHeight = 2310;
        screenWidth = 1270;
        activeDotID = -1;
    }

    public DrawingLimits(String application_tag_id){
        setupDots(application_tag_id);
        screenHeight = 2310;
        screenWidth = 1270;
        activeDotID = -1;
    }

    public DrawingLimits(String application_tag_id, boolean isTest){
        setupDots(application_tag_id, isTest);
        screenHeight = 2310;
        screenWidth = 1270;
        activeDotID = -1;
    }

    public DrawingLimits(double width, double height){
        setupDots();
        screenHeight = height;
        screenWidth = width;
        activeDotID = -1;
    }

    public DrawingLimits(String application_tag_id, double width, double height){
        setupDots(application_tag_id);
        screenHeight = height;
        screenWidth = width;
        activeDotID = -1;
    }

    public void updateDimensions(double width, double height){
        screenHeight = height;
        screenWidth = width;
    }

    /*public DrawingLimits(int activeDotID){
        setupDots();
        this.activeDotID = activeDotID;
    }*/

    public void storeLastTouch(float lastx, float lasty){
        lastX = lastx;
        lastY = lasty;
    }

    public boolean activateDot(double touchX, double touchY){

        for(Dot dot : dots){
            if(dot.distanceFrom(touchX, touchY, screenWidth, screenHeight) < dot.size + 5 && dot.getEdgeActiveState() == false){
                System.out.println(dot.x*screenWidth);
                System.out.println(dot.y*screenHeight);
                activeDotID = dot.dotID;
                dot.setColor(Color.GREEN,true);
                return true;
            }

        }

        return false;
    }

    public boolean activateDot(double touchX, double touchY, Canvas drawCanvas, Paint drawPaint){

        for(Dot dot : dots){
            if(dot.distanceFrom(touchX, touchY, screenWidth, screenHeight) < dot.size + 5 && dot.getEdgeActiveState() == false){
                System.out.println(dot.x*screenWidth);
                System.out.println(dot.y*screenHeight);
                activeDotID = dot.dotID;
                dot.setColor(Color.GREEN,true);
                redrawDots(screenWidth, screenHeight, drawCanvas, drawPaint);
                return true;
            }

        }

        return false;
    }

    public boolean isAllEdgesActive(){

        for(Dot dot : dots){
            if (dot.getEdgeActiveState() == false)
                return false;
        }

        return true;
    }

    public void setDotsActiveColor(Canvas drawCanvas, Paint drawPaint){
        for(Dot dot : dots){
            dot.setColor(Color.GREEN, true);
        }
        redrawDots(screenWidth, screenHeight, drawCanvas, drawPaint);
    }

    public boolean isAtNextDot(double touchX, double touchY){

        return getDotByID(activeDotID).getNextDot().distanceFrom(touchX, touchY, screenWidth, screenHeight) < getDotByID(activeDotID).getNextDot().size + 5;

    }

    public void setActiveDotActiveState(boolean activatorVariable){
        getDotByID(activeDotID).setEdgeActive(activatorVariable);
    }

    public Dot getDotByID(int dotID){
        for(Dot dot : dots){
            if(dot.dotID == dotID)
                return dot;
        }
        return null;
    }

    public boolean isInLine(double touchX, double touchY){
        Vector2 touchVect = new Vector2(touchX, touchY);
        Vector2 activeDotVect = getDotByID(activeDotID).getPosVect().scale(screenWidth, screenHeight);
        Vector2 normVect = getDotByID(activeDotID).getNextDotNorm(screenWidth, screenHeight);

        // next try and decipher whats going on.
        Vector2 vect = normVect.rotate(Math.PI / 2);
        Vector2 vect1 = normVect.rotate(Math.PI / 2).scalarMultiply(regionWidth).add(activeDotVect);
        Vector2 vect2 = normVect.rotate(-1*Math.PI / 2).scalarMultiply(regionWidth).add(activeDotVect);

        Vector2 v1Norm = touchVect.subtract(vect1).getNormalVect();
        Vector2 v2Norm = touchVect.subtract(vect2).getNormalVect();

        double z1 = normVect.crossProd(v1Norm);
        double z2 = normVect.crossProd(v2Norm);

        return (z1 <= 0) && (z2 >= 0);
        //activeDotVect.add(dots.get(activeDotID).getNextDotNorm().rotate(Math.PI / 2).scalarMultiply(regionWidth)).angleBetween(touchVect);
    }

    public boolean isGoingForward(double touchX, double touchY){
        Vector2 touchVect = new Vector2(touchX, touchY);
        Vector2 lastTouchVect = new Vector2(lastX, lastY);
        Vector2 normVect = getDotByID(activeDotID).getNextDotNorm(screenWidth, screenHeight);

        Vector2 difference = touchVect.subtract(lastTouchVect);

        return normVect.dot(difference) >= -5;

    }

    public boolean isGoingForward(double touchX, double touchY, double lastTouchX, double lastTouchY){
        Vector2 touchVect = new Vector2(touchX, touchY);
        Vector2 lastTouchVect = new Vector2(lastTouchX, lastTouchY);
        Vector2 normVect = getDotByID(activeDotID).getNextDotNorm(screenWidth, screenHeight);

        Vector2 difference = touchVect.subtract(lastTouchVect);

        return normVect.dot(difference) >= -5;

    }


    private void setupDots(){
        try {
            /*LevelBuilder builder = new LevelBuilder();
            dots = builder.buildLevel(1);*/
            dots = new ArrayList<Dot>();
            dots.add( new Dot(0, 0.125, 0.2, 50, 0));
            dots.add( new Dot(1, 0.875, 0.55, 50, 0));

            dots.get(0).addConnectedDot(dots.get(1));
        }
        catch(Exception e) {
            System.out.print(e.toString());
        }

    }
    private void setupDots(String application_tag_id){
        try {
            dots = new LevelBuilder().execute(application_tag_id).get();
            //dots = builder.buildLevel(subCategory_ID);

        }
        catch(Exception e) {
            System.out.print(e.toString());
        }

    }

    private void setupDots(String application_tag_id, boolean runTest){
        try {
            if (runTest) {
                LevelBuilder build = new LevelBuilder();
                dots = build.buildLevel(application_tag_id);
            }
            else
                setupDots(application_tag_id);

        }
        catch(Exception e) {
            System.out.print(e.toString());
        }

    }

    public void resetColor(Canvas drawCanvas, Paint canvasPaint){
        for(Dot dot :  dots){
            // TODO: set colour.
            dot.setColor(Color.BLACK);
        }
        redrawDots(screenWidth,screenHeight,drawCanvas,canvasPaint);
    }

    public void redrawDots(double w, double h, Canvas drawCanvas, Paint canvasPaint){
        canvasPaint.setTextSize(75);
        updateDimensions(w,h);
        for(Dot dot :  dots){
            // TODO: set colour.
            dot.drawDot(w,h,drawCanvas,canvasPaint);
        }
        System.out.println(w);
        System.out.println(h);
    }

    public ArrayList<Dot> getDots(){
        return dots;
    }

}
