package com.alt.reminderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {

        //Add 3 sample items.
        addItem(new DummyItem("1", "Tap the plus to get started!", "This is where the date will be displayed.", "This is where the time will be displayed.", "This is where the note will be displayed."));
        addItem(new DummyItem("2", "Long press reminder to delete", "Mon, May 4th, 2015", "1:00 PM", "He loves Android."));
        addItem(new DummyItem("3", "Reminder 1", "Mon, May 4th, 2015", "1:00 PM", "He loves Android."));
        addItem(new DummyItem("4", "Reminder 2", "Mon, May 4th, 2015", "1:00 PM", "He loves Android."));
        addItem(new DummyItem("5", "Reminder 3", "Mon, May 4th, 2015", "1:00 PM", "He loves Android."));
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void deleteItem(DummyItem item) {
        ITEMS.remove(item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String reminder_name;
        public String reminder_date;
        public String reminder_time;
        public String reminder_note;

        public DummyItem(String id, String reminder_name, String reminder_date,
                         String reminder_time, String reminder_note) {
            this.id = id;
            this.reminder_name = reminder_name;
            this.reminder_date = reminder_date;
            this. reminder_time = reminder_time;
            this.reminder_note = reminder_note;
        }

        @Override
        public String toString() {
            return reminder_name;
        }
    }
}
