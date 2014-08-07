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
import com.example.tagstables.model.Mention;

public class AllMentionActivity extends Activity implements OnItemClickListener {
	
	DatabaseHelper m_db;
	private ListView mentionlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        m_db = new DatabaseHelper(getApplicationContext());
        
        mentionlist = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1,android.R.id.text1,getMention(m_db.getAllMention()));
        mentionlist.setAdapter(adapter);
        m_db.closeDB();
        mentionlist.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_all_mention, menu);
        return true;
    }
    
    public static String[] getMention(List<Mention> mentions)
    {
        String[] ret = new String[mentions.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = mentions.get(i).getMentionName();
            
        }
        return ret;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent notesByMention = new Intent(this,AllNotesByMention.class);
		notesByMention.putExtra("mentionname", (String) arg0.getItemAtPosition(arg2));
		startActivity(notesByMention);
		
	}
}
