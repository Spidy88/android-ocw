package app.ocw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * The main screen to the app for searching through OCW
 * 
 * @author Nick Ferraro
 *
 */
public class CourseSearchActivity extends Activity {
	private EditText searchText = null;
	private Button searchButton = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_search);
		
		// Get a reference to our views
		searchText = (EditText)findViewById(R.id.search);
		searchButton = (Button)findViewById(R.id.search_button);
		
		// Set a text changed callback for search to enable/disable our search button
		searchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				searchButton.setEnabled(searchText.getText().toString().length() > 0);
			}
		});
		
		// Set a key listener callback so that users can search by pressing "Enter"
		searchText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {	
				if( keyCode == KeyEvent.KEYCODE_ENTER ) {
					if( event.getAction() == KeyEvent.ACTION_UP ) {
						searchFor(searchText.getText().toString());
					}
					return true;
				}
				return false;
			}
		});
		
		// Set a search button callback
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchFor(searchText.getText().toString());
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		searchText.requestFocus();
	}
	
	private void searchFor(String search) {
		// If the search string is not empty, launch the course list intent and hand in the search string
		if( search != null && search.length() > 0 ) {
			Intent searchIntent = new Intent(this, CourseListActivity.class);
			searchIntent.putExtra(CourseListActivity.EXTRA_SEARCH, search);
			startActivity(searchIntent);
		}
	}
}
