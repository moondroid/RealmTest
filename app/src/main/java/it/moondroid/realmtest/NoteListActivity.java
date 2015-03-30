package it.moondroid.realmtest;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import it.moondroid.realmtest.adapters.NoteAdapter;
import it.moondroid.realmtest.model.Person;
import it.moondroid.realmtest.workers.WorkerHandler;
import it.moondroid.realmtest.workers.WorkerThread;


public class NoteListActivity extends ActionBarActivity {

    private Realm mRealm;
    private WorkerThread mWorkerThread;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        // Open the default mRealm ones for the UI thread.
        mRealm = Realm.getInstance(this);
        mRealm.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new NoteAdapter(getAllPersons());
        mRecyclerView.setAdapter(mAdapter);


        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = buildMessage(WorkerHandler.ADD_TIMESTAMP, "test");
                mWorkerThread.workerHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWorkerThread.workerHandler.getLooper().quit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWorkerThread = new WorkerThread(this);
        mWorkerThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * gets all Person objects
     * @return
     */
    private RealmResults<Person> getAllPersons(){
        return mRealm.where(Person.class).findAll();
    }


    private static Message buildMessage(int action, String timeStamp) {
        Bundle bundle = new Bundle(2);
        bundle.putInt(WorkerHandler.ACTION, action);
        bundle.putString(WorkerHandler.TIMESTAMP, timeStamp);
        Message message = new Message();
        message.setData(bundle);
        return message;
    }
}
