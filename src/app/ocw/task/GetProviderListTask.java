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
import app.ocw.api.ProviderListAPI;
import app.ocw.model.Provider;

/**
 * An async task for getting a list of providers from the OCW data API.
 * It will only use the first string in the ellipsis array. The background
 * thread will return NULL for a failed query.
 * 
 * @author Nick Ferraro
 *
 */
public abstract class GetProviderListTask extends AsyncTask<String, Integer, List<Provider>> implements ProviderListAPI {
	@Override
	protected final List<Provider> doInBackground(String... params) {
		List<Provider> providers = new ArrayList<Provider>();
		
		if(params != null && params.length > 0) {
			String url = params[0];
			
			try {
				// Create our client, our request, and get our response
				HttpClient client = new DefaultHttpClient();
				HttpGet getProviderList = new HttpGet(url);
				HttpResponse providerListResponse = client.execute(getProviderList);
				
				// Parse our response into a list of Provider objects
				JSONArray providerListArray = new JSONArray(EntityUtils.toString(providerListResponse.getEntity()));
				for( int i = 0; i < providerListArray.length(); ++i ) {
					JSONObject providerObject = providerListArray.getJSONObject(i);
					Provider parsedProvider = new Provider(providerObject.getInt(KEY_ID), providerObject.getString(KEY_NAME), providerObject.getString(KEY_EXTERNAL_ID));
					providers.add(parsedProvider);
				}
			} catch(Exception e) {
				Log.e("GetProviderListTask", e.getMessage());
				return null;
			}
		}
		
		return providers;
	}
}
