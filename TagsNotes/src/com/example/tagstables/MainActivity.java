package com.example.tagstables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagstables.databasehelper.DatabaseHelper;

public class MainActivity extends Activity implements OnItemClickListener {
	 DatabaseHelper db;
    private ListView list;
    private String[] items= new String[]{ "Create Note","All Notes", "Hash Tags", "Mention Tags" };
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=(ListView)findViewById(R.id.tags);
        
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        list.setOnItemClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		StartMyActivity s =new StartMyActivity(this) ;
		switch (arg2) {
		case 0:
			s.getIntent(CreateNoteActivity.class);
			break;
		case 1:
			Intent allNotes = new Intent(this,AllNotesActivity.class);
			startActivity(allNotes);
			break;
		case 2:
			Intent allHashs =  new 	Intent(this,AllHashActivity.class);
			startActivity(allHashs);
			break;
		case 3:
			Intent allMentions = new Intent(this,AllMentionActivity.class);
			startActivity(allMentions);
			break;
		default:
			break;
		}
		
	}
		
}
