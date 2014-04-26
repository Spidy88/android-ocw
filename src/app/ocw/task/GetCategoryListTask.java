package app.ocw.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import app.ocw.api.CategoryListAPI;
import app.ocw.model.Category;

/**
 * An async task for getting a list of categories from the OCW data API.
 * It will only use the first string in the ellipsis array. The background
 * thread will return NULL for a failed query.
 * 
 * @author Nick Ferraro
 *
 */
public abstract class GetCategoryListTask extends AsyncTask<String, Integer, List<Category>> implements CategoryListAPI {
	@Override
	protected final List<Category> doInBackground(String... params) {
		List<Category> categories = new ArrayList<Category>();
		
		if(params != null && params.length > 0) {
			String url = params[0];
			
			try {
				// Create our client, our request, and get our response
				HttpClient client = new DefaultHttpClient();
				HttpGet getCategoryList = new HttpGet(url);
				HttpResponse categoryListResponse = client.execute(getCategoryList);
				
				// Parse our response into a list of Category objects
				JSONArray categoryListArray = new JSONArray(EntityUtils.toString(categoryListResponse.getEntity()));
				for( int i = 0; i < categoryListArray.length(); ++i ) {
					JSONObject categoryObject = categoryListArray.getJSONObject(i);
					Category category = parseCategory(categoryObject, null);
					categories.add(category);
				}
			} catch(Exception e) {
				Log.e("GetCategoryListTask", e.getMessage());
				return null;
			}
		}
		
		return categories;
	}
	
	private Category parseCategory(JSONObject categoryObject, Category parent) throws JSONException {
		Category parsedCategory = new Category(categoryObject.getString(KEY_NAME), categoryObject.getInt(KEY_COURSE_COUNT), parent);
		
		List<Category> childrenList = parsedCategory.getChildren();
		JSONArray childrenArray = categoryObject.getJSONArray(KEY_CHILDREN);
		for( int i = 0; i < childrenArray.length(); ++i ) {
			childrenList.add(parseCategory(childrenArray.getJSONObject(i), parsedCategory));
		}
		
		return parsedCategory;
	}
}
