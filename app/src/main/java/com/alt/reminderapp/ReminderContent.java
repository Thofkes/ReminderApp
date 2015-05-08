package com.alt.reminderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for providing the reminder content for the user interface.
 * <p/>
 */
public class ReminderContent {

    /**
     * An array of sample (reminder) items.
     */
    public static List<ReminderItem> ITEMS = new ArrayList<ReminderItem>();

    /**
     * A map of sample (reminder) items, by ID.
     */
    public static Map<String, ReminderItem> ITEM_MAP = new HashMap<String, ReminderItem>();

    static {

        //Add 3 sample reminder items.
        addItem(new ReminderItem("1", "Tap the plus to get started!", "Mon, May 8th, 2015", "11:15 AM", "Android rocks!"));
        addItem(new ReminderItem("2", "Long press a reminder to delete.", "Mon, May 4th, 2015", "1:00 PM", "We love Android."));
    }

    public static void addItem(ReminderItem item) {

        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

    }

    /**
     * A reminder item.
     */
    public static class ReminderItem {
        public String id;
        public String reminder_name;
        public String reminder_date;
        public String reminder_time;
        public String reminder_note;

        public ReminderItem(String id, String reminder_name, String reminder_date,
                            String reminder_time, String reminder_note) {
            this.id = id;
            this.reminder_name = reminder_name;
            this.reminder_date = reminder_date;
            this. reminder_time = reminder_time;
            this.reminder_note = reminder_note;
        }

        public long getId() {
            return Long.parseLong(id);
        }

        @Override
        public String toString() {
            return reminder_name;
        }
    }
}
