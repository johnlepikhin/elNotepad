package johnlepikhin.notepad;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

final class RecordsDataSource {
	private SQLiteDatabase database;
	private LocalDB dbHelper;
	private String[] allColumns = {
			LocalDB.COLUMN_ID,
			LocalDB.COLUMN_TITLE,
			LocalDB.COLUMN_TEXT,
			LocalDB.COLUMN_DATE,
			LocalDB.COLUMN_LONGTITUDE,
			LocalDB.COLUMN_LATITUDE
	};

	public RecordsDataSource(Context context) {
		dbHelper = new LocalDB(context);
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Record getById(long id) {
		Record r = null;
		Cursor cursor = database.query(LocalDB.TABLE_RECORDS,
				allColumns, LocalDB.COLUMN_ID + "=" + id, null, null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			r = cursorToRecord(cursor);
		}
		
		return r;
	}
	
	public void insert(Record r) {
		ContentValues values = new ContentValues();
		values.put(LocalDB.COLUMN_TITLE, r.getTitle());
		values.put(LocalDB.COLUMN_TEXT, r.getText());
		values.put(LocalDB.COLUMN_LONGTITUDE, r.getLongtitude());
		values.put(LocalDB.COLUMN_LATITUDE, r.getLatitude());
		long insertId = database.insert(LocalDB.TABLE_RECORDS, null, values);
		r.setId(insertId);
	}

	public void update(Record r) {
		ContentValues values = new ContentValues();
		values.put(LocalDB.COLUMN_TITLE, r.getTitle());
		values.put(LocalDB.COLUMN_TEXT, r.getText());
		values.put(LocalDB.COLUMN_LONGTITUDE, r.getLongtitude());
		values.put(LocalDB.COLUMN_LATITUDE, r.getLatitude());
		database.update(LocalDB.TABLE_RECORDS, values, LocalDB.COLUMN_ID + "=" + r.getId(), null);
	}

	public void delete(Record Record) {
		long id = Record.getId();
		System.out.println("Record deleted with id: " + id);
		database.delete(LocalDB.TABLE_RECORDS, LocalDB.COLUMN_ID + " = " + id, null);
	}

	public ArrayList<Record> list() {
		ArrayList<Record> r = new ArrayList<Record>();
		Cursor cursor = database.query(LocalDB.TABLE_RECORDS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Record Record = cursorToRecord(cursor);
			r.add(Record);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return r;
	}

	private Record cursorToRecord(Cursor cursor) {
		Record r = new Record(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
		r.setLocation(cursor.getDouble(4), cursor.getDouble(5));
		return r;
	}
}
