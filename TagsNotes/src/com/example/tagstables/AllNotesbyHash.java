package com.example.tagstables;

import java.util.List;

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
import com.example.tagstables.model.Note;

public class AllNotesbyHash extends Activity  implements OnItemClickListener{
	DatabaseHelper nh_db;
	private ListView notebyhash;
	private static long[] ids;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        notebyhash =  (ListView) findViewById(R.id.list);
        nh_db =  new DatabaseHelper(getApplicationContext());
        Intent gethash = getIntent();
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this,android.R.layout.simple_list_item_1,android.R.id.text1,
        		getNotes(nh_db.getAllNotesByHash(gethash.getStringExtra("hashname"))));
        notebyhash.setAdapter(adapter);
        nh_db.closeDB();
        notebyhash.setOnItemClickListener(this);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_all_notesby_hash, menu);
        return true;
    }
    
    public static String[] getNotes(List<Note> notes)
    {
        String[] ret = new String[notes.size()];
        ids = new long[notes.size()];
        for (int i=0; i < ret.length; i++)
        {
        	ids[i]= notes.get(i).getId();
            ret[i] = notes.get(i).getNote();
            
        }
        return ret;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
		Intent updateNote = new Intent (this,UpdateNoteActivity.class);
		updateNote.putExtra("NoteId", ids[arg2]); 
		startActivity(updateNote);
		
	}
}
