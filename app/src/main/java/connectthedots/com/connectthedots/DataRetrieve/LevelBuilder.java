package connectthedots.com.connectthedots.DataRetrieve;

import android.graphics.Color;
import android.os.AsyncTask;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import connectthedots.com.connectthedots.LevelClasses.Dot;



public class LevelBuilder extends AsyncTask<Integer,Void,ArrayList<Dot>> {

    String connectionString;

    public LevelBuilder(){
        connectionString = "jdbc:jtds:sqlserver://geodesic.database.windows.net:1433/SS_DEV;user=drawingSandbox@geodesic;password=P1u7on1cX;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=5;";
        buildLevel(1);
    }
    // IPs allowed: 101.173.64.0 to 101.173.64.255
    @Override
    protected ArrayList<Dot> doInBackground(Integer... params) {
        int sub_category_id = params[0].intValue();

        ArrayList<Dot> dots = new ArrayList<Dot>();

        try {

            Driver d = (Driver) Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conn = d.connect(connectionString, new Properties());
            System.out.println("test");
            Statement sta = conn.createStatement();
            String Sql = "SELECT [Dot_ID],[ComponentID],[Sub_Category_ID],[Next_Dot_ID],[X_Coord],[Y_Coord],[Size_override],[label],[colour_override] FROM SS_DEV.[dbo].[dots] WHERE Sub_Category_ID = " + Integer.toString(sub_category_id) + " Order By dot_id ASC";
            ResultSet rs = sta.executeQuery(Sql);

            while (rs.next()) {

                int dotID = Integer.parseInt(rs.getString("Dot_ID"));
                int nextDotID = Integer.parseInt(rs.getString("Next_Dot_ID"));
                double X_Coord = Double.parseDouble(rs.getString("X_Coord"));
                double Y_Coord = Double.parseDouble(rs.getString("Y_Coord"));

                Dot newDot = new Dot(dotID,nextDotID, X_Coord, Y_Coord,50, Color.BLACK );

                dots.add(newDot);

            }

            for(Dot dot : dots){
                dot.setNextDot(dots);
            }



        }
        catch(Exception e){
            System.out.println("Something Happened.");
        }

        return dots;
    }

    public LevelBuilder(String connection){
        connectionString = connection;
        //buildLevel(1);
    }

    public ArrayList<Dot> buildLevel(int sub_category_id) {

        ArrayList<Dot> dots = doInBackground(sub_category_id);

        return dots;
    }

}
