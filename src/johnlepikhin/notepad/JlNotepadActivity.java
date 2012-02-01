package johnlepikhin.notepad;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class JlNotepadActivity extends Activity {
	private ListView listView;
    private MyListAdapter listAdapter;
    private RecordsDataSource ds;
	private ArrayList<Record> recs = new ArrayList<Record>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ds = new RecordsDataSource(this);

        ShowMain ();
        Update ();
    };

    private class MyListAdapter extends ArrayAdapter<Record> {
    	private Activity activity;

    	public MyListAdapter(Activity a, int textViewResourceId) {
    		super(a, textViewResourceId);
    		this.activity = a;
    	}
    	
    	public class ViewHolder{
            public TextView title;
            public TextView date;
        }
    	
    	@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                LayoutInflater vi =
                    (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.main_list_row, null);
                holder = new ViewHolder();
                holder.title = (TextView) v.findViewById(R.id.TEXT_CELL);
                holder.date = (TextView) v.findViewById(R.id.DATE_CELL);
                v.setTag(holder);
            }
            else
                holder=(ViewHolder)v.getTag();
     
            final Record rec = recs.get(position);
            if (rec != null) {
                holder.title.setText(rec.getTitle());
                holder.date.setText(rec.getDate() + " " + rec.getLongtitude() + " " + rec.getLatitude());
            }
            return v;
        }
    }
    
    public void ShowMain() {
		setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.notes_list);
        final Button add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new AddListener());

        listAdapter = new MyListAdapter(this, R.layout.main_list_row);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new ViewListener());
    }

    public void Update() {
    	listAdapter.clear();
    	recs = ds.list();
    	Iterator<Record> i = recs.iterator();
    	while (i.hasNext()) {
    		Record r = i.next();
    		if (r != null)
    			listAdapter.add(r);
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        Update ();
    }
    
    private void Edit (long id) {
		Intent intent = new Intent(JlNotepadActivity.this, EditActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
    }

    private void Edit (Record r) {
    	Edit (r.getId());
    }
    
    private class AddListener implements OnClickListener {
    	public void onClick(View v)
    	{
    		Intent intent = new Intent(JlNotepadActivity.this, EditActivity.class);
    		startActivityForResult(intent, 0);
    	}
    }
    
    private class ViewListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
			Record r = (Record) listView.getItemAtPosition(pos);
			Edit(r);
		}
    }
}
