package connectthedots.com.connectthedots;

/**
 * Created by Stevan on 2/20/2016.
 */


import org.junit.Test;
import java.util.ArrayList;

import connectthedots.com.connectthedots.DataRetrieve.LevelBuilder;
import connectthedots.com.connectthedots.LevelClasses.DrawingLimits;


public class DrawerTest {

    @Test
    public void basicTest(){

        String test = "hello";

        test = test + " there...";

        assert(true == true);
    }

    @Test
    public void drawingLimitDefaultConstructor(){

        DrawingLimits limits = new DrawingLimits(1, true);

        assert(limits != null);
    }

    @Test
    public void canActivateDefault(){

        DrawingLimits limits = new DrawingLimits(1, true);

        //boolean tester = limits.activateDot(160, 460);
        boolean tester = limits.activateDot(160, 1150);

        assert(tester == true);
    }

    @Test
    public void isInLine(){

        DrawingLimits limits = new DrawingLimits(1, true);

        //boolean tester = limits.activateDot(160, 460);
        //boolean test2 = limits.isInLine(800, 930);
        boolean tester = limits.activateDot(160, 1150);
        boolean test2 = limits.isInLine(300, 1100);

        assert(test2 == true);
    }

    @Test
    public void isNotInLine(){

        DrawingLimits limits = new DrawingLimits(1, true);

        //boolean tester = limits.activateDot(160, 460);
        //boolean test2 = limits.isInLine(1000, 930);
        boolean tester = limits.activateDot(160, 1150);
        boolean test2 = limits.isInLine(350, 523);

        assert(test2 == false);
    }

    @Test
    public void isGoingForward(){
        DrawingLimits limits = new DrawingLimits(1,true);

        boolean tester = limits.activateDot(160, 1150);
        boolean test2 = limits.isGoingForward(216, 1100, 220, 1120);

        assert(test2 == true);
    }

    @Test
    public void getData(){
        LevelBuilder build = new LevelBuilder();

    }


}
