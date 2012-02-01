package johnlepikhin.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDB extends SQLiteOpenHelper {
	public static final String TABLE_RECORDS = "records";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_TEXT = "text";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_LONGTITUDE = "longtitude";
	public static final String COLUMN_LATITUDE = "latitude";

	private static final String DATABASE_NAME = "records.db";
	private static final int DATABASE_VERSION = 3;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_RECORDS + "( "
				+ COLUMN_ID + " integer primary key autoincrement, "
				+ COLUMN_TITLE + " title not null,"
				+ COLUMN_TEXT + " text not null,"
				+ COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
				+ COLUMN_LONGTITUDE + " double,"
				+ COLUMN_LATITUDE + " double"
			+ ");";

	public LocalDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LocalDB.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
		onCreate(db);
	}
};
