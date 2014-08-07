package com.example.tagstables;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.example.tagstables.databasehelper.DatabaseHelper;
import com.example.tagstables.hashandtags.HashAndMentionTags;
import com.example.tagstables.model.Hash;
import com.example.tagstables.model.Mention;
import com.example.tagstables.model.Note;

public class UpdateNoteActivity extends Activity implements OnClickListener{
	
	DatabaseHelper update_db;
	private MultiAutoCompleteTextView macmptv;
  	private List<String> hash,mention;
    private HashAndMentionTags hashAndMentionTags;
    private List<Long> h_id = new ArrayList<Long>();
    private List<Long> m_id = new ArrayList<Long>();
	private Button update,cancel;
	private  Intent getId,back;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_createote);
	        
	        macmptv=(MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
	        update=(Button)findViewById(R.id.save);
	        update.setOnClickListener(this);
	        
	        cancel = (Button)findViewById(R.id.cancel);
	        cancel.setOnClickListener(this);
	        
	        update_db = new DatabaseHelper(getApplicationContext());
	         getId= getIntent();
	        macmptv.setText((update_db.getNote(getId.getLongExtra("NoteId", 5))).getNote().toString());
	       
	        
	       update_db.closeDB();
	 }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.save:
			
			update_db.deleteNote(getId.getLongExtra("NoteId", 5));
			
			Note nt = new Note(macmptv.getText().toString(), 1);
	    	 hashAndMentionTags = new HashAndMentionTags(nt.getNote());
	    	 hash = hashAndMentionTags.getHashTags();
	    	 mention = hashAndMentionTags.getMentionTags();

			for(String hashs : hash)
	    		if(update_db.checkHash(hashs)) 
	    			h_id.add(update_db.createHash(new Hash(hashs)));
	    		else
	    			if(!h_id.contains(update_db.getHashId(hashs)))
	    			h_id.add(update_db.getHashId(hashs));
			
	    	 for(String mentions : mention) 
	    		 if(update_db.checkMention(mentions))
	    			 m_id.add(update_db.createMention(new Mention(mentions)));
	    		 else
	    			 if(!m_id.contains(update_db.getMentionId(mentions)))
	    				 m_id.add(update_db.getMentionId(mentions));
	    	
	    	 update_db.createNote(nt,convertLongs(h_id) , convertLongs(m_id));	 
	    	 
	    	 this.finish();
	    	 back = new Intent(this,AllNotesActivity.class);
	 		 startActivity(back);
	 		 
			break;
			
		case R.id.cancel:
			
			this.finish();
			
			break;
			
		default:
			break;
		}
		
	}
	
	public long[] convertLongs(List<Long> ids)
    {
        long[] ret = new long[ids.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = ids.get(i).longValue();
            
            Log.d("IDS",""+ret[i]);
           
        }
        return ret;
    }
	
	@Override
	public void onBackPressed() {
		
		back = new Intent(this,AllNotesActivity.class);
		startActivity(back);
	
	}
}
