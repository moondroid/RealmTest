package it.moondroid.realmtest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import it.moondroid.realmtest.adapters.PersonAdapter;
import it.moondroid.realmtest.model.Person;


public class MainActivity extends ActionBarActivity {

    private Realm mRealm;

    private EditText mEditTextPersonName;
    private ListView mListViewPersons;
    private PersonAdapter mPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open the default mRealm ones for the UI thread.
        mRealm = Realm.getInstance(this);

        mEditTextPersonName = (EditText)findViewById(R.id.etPersonName);
        mEditTextPersonName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                String name = v.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_DONE && !name.isEmpty()) {
                    savePerson(name);

                    v.clearFocus();

                    //close soft keyboard
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    mEditTextPersonName.setText("");

                    handled = true;
                }
                return handled;
            }
        });

        mListViewPersons = (ListView)findViewById(R.id.listView);
        mPersonAdapter = new PersonAdapter(this, getAllPersons(), true);
        mListViewPersons.setAdapter(mPersonAdapter);
    }

    /**
     * creates and saves a new Person object
     * @param name
     */
    private void savePerson(String name){
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        mRealm.beginTransaction();

        // Add a person
        Person person = mRealm.createObject(Person.class);
        person.setName(name);
        //person.setAge(14);

        // When the write transaction is committed, all changes a synced to disk.
        mRealm.commitTransaction();
    }

    /**
     * gets the last created Person object
     * @return
     */
    private Person getLastPerson(){
        // Find the last person (no query conditions)
        RealmResults<Person> results = mRealm.where(Person.class).findAll();
        Person person = results.last();

        return person;
    }

    /**
     * gets all Person objects
     * @return
     */
    private RealmResults<Person> getAllPersons(){
        return mRealm.where(Person.class).findAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close(); // Remember to close Realm when done.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
