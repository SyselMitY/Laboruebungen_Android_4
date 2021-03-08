package cf.soisi.tankmesser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        values.put(TankvorgangDbHelper.COLUMN_DATE, tv.getDate().format(DateTimeFormatter.ISO_DATE));
        values.put(TankvorgangDbHelper.COLUMN_KM_OLD, tv.getKmOld());
        values.put(TankvorgangDbHelper.COLUMN_KM_NEW, tv.getKmNew());
        values.put(TankvorgangDbHelper.COLUMN_FUEL_AMOUNT, ((int) (tv.getAmount() * 1000)));
        values.put(TankvorgangDbHelper.COLUMN_FUEL_PRICE, ((int) (tv.getPrice() * 1000)));

        long insertId = database.insert(TankvorgangDbHelper.TABLE_NAME, null, values);

        return getTankvorgang(insertId);
    }

    public Tankvorgang getTankvorgang(long id) {
        try (Cursor cursor = getCursorFromQuery(
                TankvorgangDbHelper.COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)}, TankvorgangDbHelper.COLUMN_DATE + " desc")) {
            List<Tankvorgang> list = getListFromCursor(cursor);
            return list.stream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public List<Tankvorgang> getAll() {
        try (Cursor cursor = getCursorFromQuery(null, null, TankvorgangDbHelper.COLUMN_DATE + " desc")) {
            return getListFromCursor(cursor);
        }
    }

    public Integer getLatestKmValue() {
        try (Cursor cursor = getCursorFromQuery(null, null, TankvorgangDbHelper.COLUMN_KM_NEW + " desc")) {
            return getListFromCursor(cursor)
                    .stream()
                    .findFirst()
                    .map(Tankvorgang::getKmNew)
                    .orElse(null);
        }
    }

    private Cursor getCursorFromQuery(String where, String[] whereArgs, String orderBy) {
        return database.query(
                TankvorgangDbHelper.TABLE_NAME,
                allColumns,
                where,
                whereArgs,
                null,
                null,
                orderBy);
    }

    private Cursor getCursorFromQuery(String where, String[] whereArgs) {
        return getCursorFromQuery(where, whereArgs, null);
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
