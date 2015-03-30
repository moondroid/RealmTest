package it.moondroid.realmtest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import it.moondroid.realmtest.R;
import it.moondroid.realmtest.model.Person;

/**
 * Created by marco.granatiero on 26/01/2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private RealmResults<Person> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

//    public void add(int position, Person item) {
//        mDataset.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public void remove(Person item) {
//        int position = mDataset.indexOf(item);
//        mDataset.remove(position);
//        notifyItemRemoved(position);
//    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NoteAdapter(RealmResults<Person> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String name = mDataset.get(position).getName();
        holder.txtHeader.setText(mDataset.get(position).getName());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO item clicklistener
                //remove(name);
            }
        });

        holder.txtFooter.setText("age: " + mDataset.get(position).getAge());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Person getItem(int i) {
        return mDataset.get(i);
    }

    /**
     * Update the RealmResults associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature
     * @param realmResults the new RealmResults coming from the new query.
     */
    public void updateRealmResults(RealmResults<Person> realmResults) {
        this.mDataset = realmResults;
        notifyDataSetChanged();
    }

}
