package service.mac.com.org.servicestests.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import service.mac.com.org.servicestests.MainActivity;
import service.mac.com.org.servicestests.model.Car;

/**
 * Created by User on 9/13/2016.
 */
public class CreateCarService extends IntentService{
    private static final String TAG = "TAG - ";

    public static final String TEXT_INPUT = "inText";
    public static final String TEXT_OUTPUT = "outText";

    public List<Car> lstCar;


    public CreateCarService(String name) {
        super(name);
        Log.d(TAG, "CreateCarService::CreateCarService");
    }

    public CreateCarService(){
        super(CreateCarService.class.getName());
        Log.d(TAG, "CreateCarService::CreateCarService");
        lstCar = new ArrayList<Car>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "CreateCarService::onHandleIntent");

        if(intent != null){
            lstCar.add(new Car((intent.getStringExtra("CAR_INFO").split(","))[0], (intent.getStringExtra("CAR_INFO").split(","))[1]));
            SystemClock.sleep(5000);
            Intent callBackIntent = new Intent();
            callBackIntent.setAction(MainActivity.ResponseReceiver.LOCAL_ACTION);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("lstCar", (ArrayList)lstCar);
            callBackIntent.putExtras(bundle);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(callBackIntent);
        }

    }

}
