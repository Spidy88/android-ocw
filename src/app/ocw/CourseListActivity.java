package app.ocw;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.ocw.model.Course;
import app.ocw.task.SearchCoursesTask;

/**
 * Lists a collection of courses obtained through the search API.
 * Requires that a search string is passed in the calling intent.
 * 
 * @author Nick Ferraro
 *
 */
public class CourseListActivity extends Activity {
	public static final String EXTRA_SEARCH = "search";
	
	private DownloadSearchCoursesTask downloadSearchCoursesTask = new DownloadSearchCoursesTask();
	
	private String searchStr = null;
	private String searchUrl = null;
	private final List<Course> courses = new ArrayList<Course>();
	private ArrayAdapter<Course> coursesAdapter = null;
	private RelativeLayout loadingLayout = null;
	private RelativeLayout failedLayout = null;
	private ListView coursesListView = null;
	private Button retryButton = null;
	private TextView emptyText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_list);
		
		// Get a reference to our views
		loadingLayout = (RelativeLayout)findViewById(R.id.loading_layout);
		failedLayout = (RelativeLayout)findViewById(R.id.failed_layout);
		retryButton = (Button)findViewById(R.id.retry_button);
		emptyText = (TextView)findViewById(R.id.empty_text);
		coursesListView = (ListView)findViewById(R.id.courses_list);
		coursesAdapter = new CourseAdapter(this, courses);
		coursesListView.setAdapter(coursesAdapter);
		
		// Get our search string and create our API search url
		searchStr = getIntent().getStringExtra(EXTRA_SEARCH);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("q", searchStr));
		searchUrl = String.format("%1$s%2$s?%3$s", getString(R.string.url_base_url), getString(R.string.url_search_courses), URLEncodedUtils.format(params, "utf-8"));
		
		// Create a callback for the retry button click
		retryButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startLoadingResults();
			}
		});
		
		// Create a callback for item selection on the courses list
		coursesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showCourseDetails(courses.get(position));
			}
		});
		
		// Query the API for a list of courses using the search parameter
		startLoadingResults();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		downloadSearchCoursesTask.cancel(true);
	}
	
	private void startLoadingResults() {
		// Hide the failed to load views and show the loading in progress views
		failedLayout.setVisibility(View.GONE);
		loadingLayout.setVisibility(View.VISIBLE);

		// Cancel, recreate, and start an async task for querying the API
		downloadSearchCoursesTask.cancel(true);
		downloadSearchCoursesTask = new DownloadSearchCoursesTask();
		downloadSearchCoursesTask.execute(searchUrl);
	}
	
	private void showCourseDetails(Course course) {
		// Launch the course details intent
		Intent courseDetailIntent = new Intent(this, CourseDetailActivity.class);
		courseDetailIntent.putExtra(CourseDetailActivity.EXTRA_COURSE, course);
		startActivity(courseDetailIntent);
	}
	
	/**
	 * An async task for querying the API.
	 * 
	 * @author Nick Ferraro
	 *
	 */
	private class DownloadSearchCoursesTask extends SearchCoursesTask {
		@Override
		public void onPostExecute(List<Course> courses) {
			// Hide the loading in progress views
			loadingLayout.setVisibility(View.GONE);
			
			// If no courses were returned, an error occured, show the failed views and toast the screen
			if(courses == null) {
				failedLayout.setVisibility(View.VISIBLE);
				Toast.makeText(CourseListActivity.this, "Failed to retrieve course list", Toast.LENGTH_LONG).show();
			} else {
				// If we received courses, clear our current course list and add the new courses
				CourseListActivity.this.courses.clear();
				
				// If the course list is empty, show our empty message, otherwise update the list view
				if( courses.isEmpty() ) {
					emptyText.setVisibility(View.VISIBLE);
				} else {
					coursesListView.setVisibility(View.VISIBLE);
					CourseListActivity.this.courses.addAll(courses);
					coursesAdapter.notifyDataSetChanged();
				}
			}
		}
	}
}
