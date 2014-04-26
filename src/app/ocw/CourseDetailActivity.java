package app.ocw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import app.ocw.model.Course;

/**
 * Displays course information with a link to the course's website.
 * Requires a Course object to be passed in with the intent. 
 * 
 * For clarities sake, this activity does not retrieve the course details
 * information from the API as it is so similar to the information
 * already obtained in the original course query.
 * 
 * @author Nick Ferraro
 *
 */
public class CourseDetailActivity extends Activity {
	public static final String EXTRA_COURSE = "course";
	
	private TextView titleText = null;
	private TextView descriptionText = null;
	private Button linkButton = null;
	private TextView providerText = null;
	private TextView languageText = null;
	//private TextView datePublishedText = null;
	private TextView scoreText = null;
	private Course course = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);
		
		// Get the course parameter
		course = (Course)getIntent().getParcelableExtra(EXTRA_COURSE);
		
		// If no course was found, end this activity
		if(course == null) {
			Toast.makeText(this, "Unable to load course", Toast.LENGTH_LONG).show();
			finish();
		}
		
		// Get a reference to our views
		titleText = (TextView)findViewById(R.id.course_title);
		descriptionText = (TextView)findViewById(R.id.course_description);
		linkButton = (Button)findViewById(R.id.course_link);
		providerText = (TextView)findViewById(R.id.course_provider);
		languageText = (TextView)findViewById(R.id.course_language);
		//datePublishedText = (TextView)findViewById(R.id.course_date_published);
		scoreText = (TextView)findViewById(R.id.course_score);
		
		// Set our views display data
		titleText.setText(course.getTitle());
		descriptionText.setText(course.getDescription());
		providerText.setText(course.getSource());
		languageText.setText(course.getLanguage());
		//datePublishedText.setText(course.getDatePublished());
		scoreText.setText(Double.toString(course.getScore()));
		
		// Set a link button click listener
		linkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Launch an intent for viewing the course's website
				Uri uriUrl = Uri.parse(course.getLink());
		        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		        startActivity(launchBrowser);
			}
		});
	}
}
