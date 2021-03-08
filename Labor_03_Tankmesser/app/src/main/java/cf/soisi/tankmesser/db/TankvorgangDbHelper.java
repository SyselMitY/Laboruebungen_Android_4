package cf.soisi.tankmesser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TankvorgangDbHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public static final String TABLE_NAME = "tankvorgang";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_KM_OLD = "kmAlt";
    public static final String COLUMN_KM_NEW = "kmNeu";
    public static final String COLUMN_FUEL_AMOUNT = "fuelAmount";
    public static final String COLUMN_FUEL_PRICE = "fuelPrice";

    private static final String DB_CREATE =
            String.format("create table %s (" +
                            "%s integer primary key," +
                            "%s text not null," +
                            "%s integer not null," +
                            "%s integer not null," +
                            "%s integer not null," +
                            "%s integer not null)",
                    TABLE_NAME,
                    COLUMN_ID,
                    COLUMN_DATE,
                    COLUMN_KM_OLD,
                    COLUMN_KM_NEW,
                    COLUMN_FUEL_AMOUNT,
                    COLUMN_FUEL_PRICE
            );

    public TankvorgangDbHelper(Context context) {
        super(context, "Tankmesser", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
