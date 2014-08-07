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
import com.example.tagstables.model.Hash;

public class AllHashActivity extends Activity implements OnItemClickListener {

	DatabaseHelper hash_db;
	private ListView allhash;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        allhash = (ListView)findViewById(R.id.list);
        hash_db = new DatabaseHelper(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1,android.R.id.text1,getHashs(hash_db.getAllHash()));
        allhash.setAdapter(adapter);
        hash_db.closeDB();
        allhash.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_all_hash, menu);
        return true;
    }
    
    public static String[] getHashs(List<Hash> hashs)
    {
        String[] ret = new String[hashs.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = hashs.get(i).getHashName();
            
        }
        return ret;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent notesByHash = new Intent(this, AllNotesbyHash.class);
		notesByHash.putExtra("hashname", (String) arg0.getItemAtPosition(arg2));
		//LOG
		Log.d("HashName", (String) arg0.getItemAtPosition(arg2));
		startActivity(notesByHash);
		
	}
}