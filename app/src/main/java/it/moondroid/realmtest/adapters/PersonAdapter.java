package it.moondroid.realmtest.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

import it.moondroid.realmtest.model.Person;

/**
 * Created by marco.granatiero on 26/01/2015.
 */
public class PersonAdapter extends RealmBaseAdapter<Person> {

    public PersonAdapter(Context context, RealmResults<Person> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            TextView textView = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            textView.setTag(viewHolder);
            viewHolder.personName = textView;
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.personName.setText(realmResults.get(position).getName());

        return viewHolder.personName;
    }


    private static class ViewHolder {
        TextView personName;
    }
}
