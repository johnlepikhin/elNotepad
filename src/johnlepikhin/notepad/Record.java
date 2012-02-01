package johnlepikhin.notepad;

public class Record {
	private long id;
	private String text;
	private String date;
	private double longtitude = 1000;
	private double latitude = 1000;
	private String title;

	public Record(long in_id, String in_title, String in_text, String in_date) {
		id = in_id;
		title = in_title;
		text = in_text;
		date = in_date;
	}

	public Record() {
		id = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setLocation(double longtitude, double latitude) {
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public String getDate() {
		return date;
	}

	public double getLongtitude() {
		return longtitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setTitle(String t) {
		this.title = t;
	}

	public void setText(String t) {
		this.text = t;
	}

//	@Override
//	public String toString() {
//		return text;
//	}
}
