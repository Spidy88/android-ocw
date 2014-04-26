package app.ocw.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for an OCW Course
 * 
 * @author Nick Ferraro
 *
 */
public class Course implements Parcelable {
	private final String id;
	private final String title;
	private final String description;
	private final String language;
	private final boolean isMember;
	private final String source;
	private final double score;
	private final String link;
	
	public Course(String id, String title, String description, String language, boolean isMember, String source, double score, String link) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.language = language;
		this.isMember = isMember;
		this.source = source;
		this.score = score;
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public boolean isMember() {
		return isMember;
	}

	public String getSource() {
		return source;
	}

	public double getScore() {
		return score;
	}

	public String getLink() {
		return link;
	}
	
	@Override
	public String toString() {
		return this.title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(language);
		dest.writeInt(isMember ? 1 : 0);
		dest.writeString(source);
		dest.writeDouble(score);
		dest.writeString(link);
	}
	
	public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
		@Override
		public Course createFromParcel(Parcel source) {
			String id = source.readString();
			String title = source.readString();
			String description = source.readString();
			String language = source.readString();
			int isMember = source.readInt();
			String _source = source.readString();
			double score = source.readDouble();
			String link = source.readString();
			
			Course palette = new Course(id, title, description, language, isMember == 1, _source, score, link);
			return palette;
		}

		@Override
		public Course[] newArray(int size) {
			return new Course[size];
		}
	};
}
