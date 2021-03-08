package cf.soisi.tankmesser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TankvorgangDataSource {

    private SQLiteDatabase database;
    private TankvorgangDbHelper dbHelper;

    public static String[] allColumns = {
            TankvorgangDbHelper.COLUMN_ID,
            TankvorgangDbHelper.COLUMN_DATE,
            TankvorgangDbHelper.COLUMN_KM_OLD,
            TankvorgangDbHelper.COLUMN_KM_NEW,
            TankvorgangDbHelper.COLUMN_FUEL_AMOUNT,
            TankvorgangDbHelper.COLUMN_FUEL_PRICE
    };

    public TankvorgangDataSource(Context context) {
        dbHelper = new TankvorgangDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Tankvorgang insertTankvorgang(Tankvorgang tv) {

        ContentValues values = new ContentValues();
        values.put(TankvorgangDbHelper.COLUMN_DATE, tv.getDatum().format(DateTimeFormatter.ISO_DATE));
        values.put(TankvorgangDbHelper.COLUMN_KM_OLD, tv.getKmAlt());
        values.put(TankvorgangDbHelper.COLUMN_KM_NEW, tv.getKmNeu());
        values.put(TankvorgangDbHelper.COLUMN_FUEL_AMOUNT, ((int) (tv.getMenge() * 1000)));
        values.put(TankvorgangDbHelper.COLUMN_FUEL_PRICE, ((int) (tv.getPreis() * 1000)));

        long insertId = database.insert(TankvorgangDbHelper.TABLE_NAME, null, values);

        return getTankvorgang(insertId);
    }

    public Tankvorgang getTankvorgang(long id) {
        try (Cursor cursor = getCursorFromQuery(
                TankvorgangDbHelper.COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)})) {
            List<Tankvorgang> list = getListFromCursor(cursor);
            return list.stream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public List<Tankvorgang> getAll() {
        try (Cursor cursor = getCursorFromQuery(null, null)) {
            return getListFromCursor(cursor);
        }
    }

    private Cursor getCursorFromQuery(String where, String[] whereArgs) {
        return database.query(
                TankvorgangDbHelper.TABLE_NAME,
                allColumns,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    private List<Tankvorgang> getListFromCursor(Cursor c) {
        List<Tankvorgang> list = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(TankvorgangDbHelper.COLUMN_ID));
            LocalDate date = LocalDate.parse(c.getString(c.getColumnIndex(TankvorgangDbHelper.COLUMN_DATE)));
            int kmAlt = c.getInt(c.getColumnIndex(TankvorgangDbHelper.COLUMN_KM_OLD));
            int kmNeu = c.getInt(c.getColumnIndex(TankvorgangDbHelper.COLUMN_KM_NEW));
            double menge = c.getInt(c.getColumnIndex(TankvorgangDbHelper.COLUMN_FUEL_AMOUNT)) / 1000.0;
            double preis = c.getInt(c.getColumnIndex(TankvorgangDbHelper.COLUMN_FUEL_PRICE)) / 1000.0;
            list.add(new Tankvorgang(date, kmAlt, kmNeu, menge, preis, id));
        }
        return list;
    }
}
