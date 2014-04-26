package app.ocw.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import app.ocw.api.CourseSearchAPI;
import app.ocw.model.Course;

/**
 * An async task for querying a list of courses from the OCW data search API.
 * It will only use the first string in the ellipsis array. The background
 * thread will return NULL for a failed query.
 * 
 * @author Nick Ferraro
 *
 */
public abstract class SearchCoursesTask extends AsyncTask<String, Integer, List<Course>> implements CourseSearchAPI {
	@Override
	protected final List<Course> doInBackground(String... params) {
		List<Course> courses = new ArrayList<Course>();

		if(params != null && params.length > 0) {
			String url = params[0];
			
			try {
				// Create our client, our request, and get our response
				HttpClient client = new DefaultHttpClient();
				HttpGet searchCourses = new HttpGet(url);
				HttpResponse searchCoursesResponse = client.execute(searchCourses);
				
				// Parse our response into a list of Category objects
				JSONArray coursesArray = new JSONArray(EntityUtils.toString(searchCoursesResponse.getEntity()));
				for( int i = 0; i < coursesArray.length(); ++i ) {
					JSONObject courseObject = coursesArray.getJSONObject(i);
					Course parsedCourse = new Course(courseObject.getString(KEY_ID),
							courseObject.getString(KEY_TITLE),
							courseObject.getString(KEY_DESCRIPTION),
							courseObject.getString(KEY_LANGUAGE),
							courseObject.getBoolean(KEY_IS_MEMBER),
							courseObject.getString(KEY_SOURCE),
							courseObject.getDouble(KEY_SCORE),
							courseObject.getString(KEY_LINK));
					courses.add(parsedCourse);
				}
			} catch(Exception e) {
				Log.d("SearchCoursesTask", e.getMessage());
				return null;
			}
		}
		
		return courses;
	}
}