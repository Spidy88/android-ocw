package app.ocw;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.ocw.model.Course;

/**
 * Course adapter that displays a custom view for a list of courses.
 * Currently the title and source are shown stacked a centered with
 * the title being larger and the source color being duller.
 * 
 * @author Nick Ferraro
 *
 */
public class CourseAdapter extends ArrayAdapter<Course> {
	public CourseAdapter(Context context, List<Course> objects) {
		super(context, R.layout.list_item_courses, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Create our view if it is not recycled
		if( convertView == null ) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.list_item_courses, parent, false);
		}
		
		// Get a reference to our views
		TextView courseTitle = (TextView)convertView.findViewById(R.id.course_title);
		TextView courseSource = (TextView)convertView.findViewById(R.id.course_source);
		
		// Get the current course
		Course course = getItem(position);
		
		// Set the view text
		courseTitle.setText(course.getTitle());
		courseSource.setText(course.getSource());
		
		return convertView;
	}
}
