package com.example.tagstables;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagstables.databasehelper.DatabaseHelper;
import com.example.tagstables.model.Note;

public class AllNotesByMention extends Activity implements OnItemClickListener {
	DatabaseHelper nm_db;
	private ListView notesbymention;
	private static long[] ids;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        notesbymention = (ListView) findViewById(R.id.list);
        nm_db = new DatabaseHelper(getApplicationContext());
        Intent mention = getIntent();
        
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1,android.R.id.text1,
        		getNotes(nm_db.getAllNotesByMention(mention.getStringExtra("mentionname"))));
        notesbymention.setAdapter(adapter);
        
        nm_db.close();
        
        notesbymention.setOnItemClickListener(this);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_all_notes_by_mention, menu);
        return true;
    }
    
    public static String[] getNotes(List<Note> notes)
    {
        String[] ret = new String[notes.size()];
        ids = new long[notes.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = notes.get(i).getNote();
            ids[i]=notes.get(i).getId();
            
        }
        return ret;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Log.d("Note Description", (String) arg0.getItemAtPosition(arg2) + ids[arg2]);
		Intent updateNote = new Intent (this,UpdateNoteActivity.class);
		updateNote.putExtra("NoteId", ids[arg2]);
		startActivity(updateNote);
		
	}
}
