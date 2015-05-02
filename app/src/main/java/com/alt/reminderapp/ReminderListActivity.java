package com.alt.reminderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * An activity representing a list of Reminders. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReminderDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ReminderListFragment} and the item details
 * (if present) is a {@link ReminderDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ReminderListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ReminderListActivity extends ActionBarActivity
        implements ReminderListFragment.Callbacks {

    static EditText t;
    static String title = "";
    static EditText n;
    static String note = "";
    static DatePicker date;
    static TimePicker time;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        if (findViewById(R.id.reminder_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ReminderListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.reminder_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ReminderListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ReminderDetailFragment.ARG_ITEM_ID, id);
            ReminderDetailFragment fragment = new ReminderDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.reminder_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ReminderDetailActivity.class);
            detailIntent.putExtra(ReminderDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.new_reminder:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }


    public static class MyAlertDialogFragment extends DialogFragment implements View.OnClickListener {

        public static MyAlertDialogFragment newInstance() {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", R.string.add);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the custom dialog
            // Pass null as the parent view because its going in the dialog layout
            View v = inflater.inflate(R.layout.dialog_reminder, null);

            t = (EditText) v.findViewById(R.id.titleTxt);
            date = (DatePicker) v.findViewById(R.id.datePick);
            time = (TimePicker) v.findViewById(R.id.timePick);
            n = (EditText) v.findViewById(R.id.noteTxt);

            Button positiveButton = (Button) v.findViewById(R.id.saveBtn);
            positiveButton.setOnClickListener(this);
            Button negativeButton = (Button) v.findViewById(R.id.cancelBtn);
            negativeButton.setOnClickListener(this);

            builder.setView(v);
            AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();
            return alert;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveBtn:
                    title = t.getText().toString();
                    note = n.getText().toString();

                    Integer year = date.getYear();
                    Integer month = date.getMonth();
                    Integer day = date.getDayOfMonth();
                    StringBuilder sbd = new StringBuilder();
                    sbd.append(month.toString()).append("/").append(day.toString()).append("/").append(year.toString());
                    String strDate = sbd.toString();

                    Integer hour = time.getCurrentHour();
                    Integer minute = time.getCurrentMinute();
                    StringBuilder sbt = new StringBuilder();
                    sbt.append(hour.toString()).append(":").append(minute.toString());
                    String strTime = sbt.toString();

                    if (title.matches("")) {
                        Toast.makeText(getActivity(), "You did not enter a title", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        DummyContent.DummyItem reminder = new DummyContent.DummyItem("1", title, strDate, strTime, note);
                        DummyContent.addItem(reminder);
                        Log.i("FragmentAlertDialog", "Positive click!");
                        getDialog().dismiss();
                        getActivity().recreate();
                    }
                    break;
                case R.id.cancelBtn:
                    getDialog().cancel();
                    break;
            }
        }
    }
}
