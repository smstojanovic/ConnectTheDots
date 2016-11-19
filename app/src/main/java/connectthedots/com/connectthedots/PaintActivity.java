package connectthedots.com.connectthedots;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class PaintActivity extends AppCompatActivity {

    DrawingView drawView;

    LinearLayout layoutOfPopup;
    PopupWindow popUpWindow;
    Button popupButton, close;
    TextView popupText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String tag = intent.getStringExtra("key");

        //popUpWindow = new PopupWindow(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawView = (DrawingView) findViewById(R.id.drawing);
        drawView.setLevelTag(tag);

    }

    public void completeLevel(View view){

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // to allow any handling of the pop up not running
                // see: http://blackriver.to/2012/08/android-annoying-exception-unable-to-add-window-is-your-activity-running/
                // for more details.
                if(!isFinishing()) {
                    LayoutInflater inflater = (LayoutInflater) PaintActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.popup,
                            (ViewGroup) findViewById(R.id.popup_1));

                    popUpWindow = new PopupWindow(layout, 300, 370, true);
                    popUpWindow.setTouchable(true);
                    popUpWindow.setFocusable(false);
                    popUpWindow.setOutsideTouchable(false);
                    popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                    close = (Button) layout.findViewById(R.id.close_popup);
                    close.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            popUpWindow.dismiss();
                        }
                    }
                    );
                }
            }
        }, 100);

        //sampleText.se
    }

//    public void showPopUp(){
//
//        try{
//            LayoutInflater inflater = (LayoutInflater) PaintActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View layout = inflater.inflate(R.layout.popup,
//                    (ViewGroup) findViewById(R.id.popup_1));
//
//            popUpWindow = new Dialog(layout, 300, 370, true);
//            popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
//            close = (Button) layout.findViewById(R.id.close_popup);
//
//
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//    }

}
