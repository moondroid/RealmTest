package it.moondroid.realmtest.workers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import io.realm.Realm;

/**
 * Created by marco.granatiero on 27/01/2015.
 */
public class WorkerThread extends Thread {

    public Handler workerHandler;

    private Context context;

    public WorkerThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Realm realm = null;
        try {
            Looper.prepare();
            realm = Realm.getInstance(context);
            workerHandler = new WorkerHandler(realm);
            Looper.loop();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}
