package service.mac.com.org.servicestests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import service.mac.com.org.servicestests.model.Car;
import service.mac.com.org.servicestests.service.CreateCarService;
import service.mac.com.org.servicestests.service.CreateStudentService;


/**
 * REFERENCE:
 * http://www.101apps.co.za/articles/using-an-intentservice-to-do-background-work.html
 *
 * **/


public class MainActivity extends AppCompatActivity {

    private Button mButtonStart, mButtonStop, mButtonStartIntent;
    private TextView mTextView;
    private static final String TAG = "TAG - ";
    ResponseReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity::onCreate");
        setContentView(R.layout.activity_main);
        setViewControls();
    }

    public void setViewControls(){
        Log.d(TAG, "MainActivity::setViewControls");
        mButtonStart = (Button)findViewById(R.id.mButtonStart);
        mButtonStop = (Button)findViewById(R.id.mButtonStop);
        mButtonStartIntent = (Button)findViewById(R.id.mButtonStartIntent);
        mTextView = (TextView)findViewById(R.id.mTextView);
    }

    public void startService(View view) {
        Log.d(TAG, "INI -- MainActivity::startService");
        startService(new Intent(this, CreateStudentService.class));
//        bindService(new Intent(this, CreateCarService.class), null, )
        stopService(new Intent(this, CreateStudentService.class));
        Log.d(TAG, "FIN -- MainActivity::startService");

    }

    public void stopService(View view) {
        Log.d(TAG, "INI -- MainActivity::stopService");
        stopService(new Intent(this, CreateStudentService.class));
        Log.d(TAG, "FIN -- MainActivity::stopService");
    }

    public void startIntentService(View view) {
        Log.d(TAG, "INI -- MainActivity::startIntentService");
        Intent carIntent = new Intent(this, CreateCarService.class);
        carIntent.putExtra("CAR_INFO", "AUDI, A4");
        startService(carIntent);
        Log.d(TAG, "FIN -- MainActivity::startIntentService");
    }

    public class ResponseReceiver extends BroadcastReceiver{
        public static final String LOCAL_ACTION = "MainActivity.ResponseReceiver.CALL_BACK_OK";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "INI - MainActivity::ResponseReceiver");
            if(intent != null) {
                ArrayList<?> lstCar = intent.getStringArrayListExtra("lstCar");
                Log.d(TAG, "RECEIVE ---------- >>>  MainActivity::ResponseReceiver" + lstCar.getClass().toString() + " " +lstCar.get(0));
            }
            Log.d(TAG, "FIN - MainActivity::ResponseReceiver");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "MainActivity::onPostResume");
        IntentFilter broadcastFilter = new IntentFilter(ResponseReceiver.LOCAL_ACTION);
        receiver = new ResponseReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, broadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity::onPause");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
