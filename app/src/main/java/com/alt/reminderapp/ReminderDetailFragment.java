package com.alt.reminderapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Reminder detail screen.
 * This fragment is either contained in a {@link ReminderListActivity}
 * in two-pane mode (on tablets) or a {@link ReminderDetailActivity}
 * on handsets.
 */
public class ReminderDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {

            TextView editText = ((TextView) rootView.findViewById(R.id.reminder_detail));

            editText.append("Reminder: " + mItem.reminder_name);
            editText.append("\n");
            editText.append("Date:     " + mItem.reminder_date);
            editText.append("\n");
            editText.append("Time:     " + mItem.reminder_time);
            editText.append("\n");
            editText.append("Note:     " + mItem.reminder_note);
        }
        return rootView;
    }
}
