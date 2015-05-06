package com.alt.reminderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

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
    static EditText da;
    static String date = "";
    static EditText ti;
    static String time = "";
    static long randomID;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private static long GenerateId() {
        long randomId;
        Random rand = new Random();
        randomId = rand.nextLong();

        return randomId;
    }

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

        MyEditTextDatePicker metdp1 = new MyEditTextDatePicker();
        MyEditTextTimePicker metdp2 = new MyEditTextTimePicker();

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
            da = (EditText) v.findViewById(R.id.datePick);
            ti = (EditText) v.findViewById(R.id.timePick);
            n = (EditText) v.findViewById(R.id.noteTxt);

            da.setOnClickListener(metdp1);
            ti.setOnClickListener(metdp2);

            Button positiveButton = (Button) v.findViewById(R.id.saveBtn);
            positiveButton.setOnClickListener(this);
            Button negativeButton = (Button) v.findViewById(R.id.cancelBtn);
            negativeButton.setOnClickListener(this);

            builder.setView(v);
            AlertDialog alert = builder.create();
            alert.show();
            return alert;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveBtn:
                    title = t.getText().toString();
                    date = da.getText().toString();
                    time = ti.getText().toString();
                    note = n.getText().toString();

                    if (title.matches("")) {
                        Toast.makeText(getActivity(), "You did not enter a title!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (date.matches("")) {
                        Toast.makeText(getActivity(), "You did not enter a date!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (time.matches("")) {
                        Toast.makeText(getActivity(), "You did not enter a time!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        randomID = GenerateId();
                        String ID = String.valueOf(randomID);
                        ReminderContent.ReminderItem reminder = new ReminderContent.ReminderItem(ID, title, date, time, note);
                        ReminderContent.addItem(reminder);
                        Log.i("FragmentAlertDialog", "Save button click!");
                        getDialog().dismiss();
                        getActivity().recreate();
                    }
                    break;
                case R.id.cancelBtn:
                    getDialog().cancel();
                    Log.i("FragmentAlertDialog", "Cancel button click!");
                    break;
            }
        }

        public class MyEditTextDatePicker implements View.OnClickListener {

            Calendar myCalendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }

            private void updateLabel() {

                String myFormat = "E, MMM d, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                da.setText(sdf.format(myCalendar.getTime()));
            }
        }

        public class MyEditTextTimePicker implements View.OnClickListener {

            Calendar myCalendar = Calendar.getInstance();

            TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                    myCalendar.set(Calendar.MINUTE, minute);
                    updateLabel();
                }
            };

            @Override
            public void onClick(View v) {

                new TimePickerDialog(getActivity(), time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), false).show();
            }

            private void updateLabel() {

                String myFormat = "h:mm a";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ti.setText(sdf.format(myCalendar.getTime()));
            }
        }
    }
}
