package service.mac.com.org.servicestests.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import service.mac.com.org.servicestests.model.Student;

/**
 * Created by User on 9/13/2016.
 */
public class CreateStudentService extends Service {
    private static final String TAG = "TAG - ";
    private List<Student> lstStudent;

    public CreateStudentService(){
        lstStudent = new ArrayList<Student>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "CreateStudentService::onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "CreateStudentService::onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "CreateStudentService::onStartCommand");

        //Create Multithreading
        new Thread(new Runnable() {
            @Override
            public void run() {
//        return super.onStartCommand(intent, flags, startId);
                lstStudent.add(new Student("Jonathan", "Sandoval", 32));
                stopSelf();
            }
        }).start();


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "CreateStudentService::onDestroy");
        printStudents();
        super.onDestroy();
    }

    public void printStudents(){
        Log.d(TAG, "CreateStudentService::printStudents");
        if(lstStudent.size() > 0){
//            for(Iterator<Student>it = lstStudent.iterator(); it.hasNext();){
//            }
            for(Student obj : lstStudent){
                Log.d(TAG, "CreateStudentService::printStudents -- " + obj.getName() + " " + obj.getLastName()
                        + " " + "\n" + obj.getAge());
            }
        }else{
            Log.d(TAG, "CreateStudentService::printStudents -- LIST IS EMPTY");
        }
    }

    /**
     * START_STICKY: Crea de nuevo el servicio después de haber sido destruido por el sistema.
    En este caso llamará a onStartCommand() referenciando un intent nulo.

    START_REDELIVER_INTENT: Crea de nuevo el servicio si el sistema lo destruyó.
    A diferencia de START_STICKY, esta vez sí se retoma el último intent que recibió el servicio.
    */
}
