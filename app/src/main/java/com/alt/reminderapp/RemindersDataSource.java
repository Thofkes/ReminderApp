package com.alt.reminderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 5/5/2015.
 */
public class RemindersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_REMINDER};

    public RemindersDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public RemindersDataSource(ReminderListFragment reminderListFragment) {

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Reminder createReminder(String reminder) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_REMINDER, reminder);
        long insertId = database.insert(MySQLiteHelper.TABLE_REMINDERS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_REMINDERS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Reminder newReminder = cursorToReminder(cursor);
        cursor.close();
        return newReminder;
    }

    public void deleteReminder(Reminder reminder) {
        long id = reminder.getId();
        Log.i("Reminder deleted- ID: ", "" + id);
        database.delete(MySQLiteHelper.TABLE_REMINDERS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_REMINDERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reminder reminder = cursorToReminder(cursor);
            reminders.add(reminder);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return reminders;
    }

    private Reminder cursorToReminder(Cursor cursor) {
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getLong(0));
        reminder.setReminder(cursor.getString(1));
        return reminder;
    }
}
