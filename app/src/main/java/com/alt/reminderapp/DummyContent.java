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

//    static {
//        DateFormat df = new SimpleDateFormat("HH:mm:ss");
//        DateFormat daf = new SimpleDateFormat("MM/dd/yyyy");
//        Date cal = Calendar.getInstance().getTime();
//        Date call = Calendar.getInstance().getTime();
//        String c  = df.format(cal);
//        String ca = daf.format(call);
//
//        Add 3 sample items.
//        addItem(new DummyItem("1", "Call Alex about project.", ca, c, "He missed class on Friday."));
//        addItem(new DummyItem("2", "Item 2"));
//        addItem(new DummyItem("3", "Item 3"));
//    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String reminder_name;
        public String reminder_date;
        public String reminder_time;
        public boolean reminder_status;
        public String reminder_note;

        public DummyItem(String id, String reminder_name, String reminder_date,
                         String reminder_time, String reminder_note) {
            this.id = id;
            this.reminder_name = reminder_name;
            this.reminder_date = reminder_date;
            this. reminder_time = reminder_time;
            this.reminder_status = false;
            this.reminder_note = reminder_note;
        }

        @Override
        public String toString() {
            return reminder_name;
        }
    }
}
