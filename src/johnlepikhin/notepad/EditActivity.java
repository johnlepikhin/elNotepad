package johnlepikhin.notepad;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
	private EditText editTitle;
	private EditText editText;
	private Button saveButton;
    private RecordsDataSource ds;
    private long id;
    private Record rec = new Record();
    
    protected LocationManager locationManager;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		editText = (EditText) findViewById(R.id.edit_text);
		editTitle = (EditText) findViewById(R.id.edit_title);
		saveButton = (Button) findViewById(R.id.save_button);

		ds = new RecordsDataSource(this);
		
		saveButton.setOnClickListener(new SaveListener());

		id = getIntent().getLongExtra("id", -1);
		if (id != -1) {
			rec = ds.getById(id);
			editTitle.setText(rec.getTitle().toString());
			editText.setText(rec.getText().toString());
		} else {
			// Update location only when new record is to be created
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates (
				LocationManager.GPS_PROVIDER,
				1000, // msecs
				10, // meters
				new MyLocationListener()
			);
		}
		
    };
	
    private class SaveListener implements OnClickListener {
    	public void onClick(View v)
    	{
			rec.setTitle(editTitle.getText().toString());
			rec.setText(editText.getText().toString());
    		if (rec.getId() == -1) {
    			ds.insert(rec);
    		} else {
    			ds.update(rec);
    		}
    		setResult(RESULT_OK);
    		finish();
    	}
    }
    
    private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location l) {
			rec.setLocation(l.getLongitude(), l.getLatitude());
		}

		public void onProviderDisabled(String s) {
		}

		public void onProviderEnabled(String s) {
		}

		public void onStatusChanged(String s, int i, Bundle b) {
		}
    }
};