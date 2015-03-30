package it.moondroid.realmtest.workers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import io.realm.Realm;
import it.moondroid.realmtest.model.Person;

/**
 * Created by marco.granatiero on 27/01/2015.
 */
public class WorkerHandler extends Handler {

    public static final int ADD_TIMESTAMP = 1;
    public static final int REMOVE_TIMESTAMP = 2;

    public static final String ACTION = "action";
    public static final String TIMESTAMP = "timestamp";

    private Realm realm;

    public WorkerHandler(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void handleMessage(Message msg) {
        final Bundle bundle = msg.getData();

        final int action = bundle.getInt(ACTION);
        final String timestamp = bundle.getString(TIMESTAMP);

        switch (action) {
            case ADD_TIMESTAMP:
                realm.beginTransaction();
                // Add a person
                Person person = realm.createObject(Person.class);
                person.setName(timestamp);
                realm.commitTransaction();
                Log.d("WorkerHandler", "ADD_TIMESTAMP "+timestamp);
                break;
            case REMOVE_TIMESTAMP:
//                realm.beginTransaction();
//                realm.where(TimeStamp.class).equalTo("timeStamp", timestamp).findAll().clear();
//                realm.commitTransaction();
                Log.d("WorkerHandler", "REMOVE_TIMESTAMP "+timestamp);
                break;
        }
    }
}
