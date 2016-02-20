package connectthedots.com.connectthedots.LevelClasses;

/**
 * Created by Stevan on 1/30/2016.
 */
public class Vector2 {
    public double X;
    public double Y;

    public Vector2(double x, double y){
        X = x;
        Y = y;
    }

    public double dot(Vector2 newVect){
        return newVect.X * X + newVect.Y * Y;
    }

    public Vector2 add(Vector2 newVect){
        return new Vector2(X + newVect.X, Y + newVect.Y);
    }

    public Vector2 subtract(Vector2 newVect){
        return new Vector2(X - newVect.X, Y - newVect.Y);
    }

    public Vector2 rotate(double theta){
        return new Vector2(Math.cos(theta)*X + -1*Math.sin(theta)*Y, Math.sin(theta)*X + Math.cos(theta)*Y);
    }

    public double norm(){
        return Math.sqrt(X*X + Y*Y);
    }

    public Vector2 getNormalVect(){
        if (norm() == 0)
            return new Vector2(0,0);
        else
            return new Vector2(X / norm(), Y / norm());
    }

    public double absAngleBetween(Vector2 newVect){
        if ( newVect.norm() == 0 || norm() == 0)
            return 0;
        else
            return Math.acos(dot(newVect) / norm() / newVect.norm());
    }

    public double crossProd(Vector2 newVect){
        return X*newVect.Y - Y*newVect.X;
    }

    public Vector2 scalarMultiply(double scalar){
        return new Vector2(scalar*X, scalar*Y);
    }

    public Vector2 scale(double scaleX, double scaleY){
        return new Vector2(scaleX*X, scaleY*Y);
    }

    public boolean isZero(){
        return (X == 0) && (Y == 0);
    }


}
