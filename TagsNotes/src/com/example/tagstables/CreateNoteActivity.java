package com.example.tagstables;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.tagstables.databasehelper.DatabaseHelper;
import com.example.tagstables.hashandtags.HashAndMentionTags;
import com.example.tagstables.model.Hash;
import com.example.tagstables.model.Mention;
import com.example.tagstables.model.Note;

public class CreateNoteActivity extends Activity implements OnClickListener {
	 	
		DatabaseHelper db;
	  	private MultiAutoCompleteTextView matv;
	  	private List<String> hash,mention;
	    private HashAndMentionTags hashAndMentionTags;
	    private List<Long> h_id = new ArrayList<Long>();
	    private List<Long> m_id = new ArrayList<Long>();
	    private Button save,cancel;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createote);
        
        db = new DatabaseHelper(getApplicationContext());
        matv=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>
        (this,android.R.layout.simple_list_item_1,combine(getHashs(db.getAllHash()), getMentions(db.getAllMention())));
        matv.setAdapter(adapter);
        matv.setThreshold(1);
        matv.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
			
			@Override
			public CharSequence terminateToken(CharSequence text) {
				int i = text.length();

				while (i > 0 && text.charAt(i - 1) == ' ') {
				    i--;
				}

				if (i > 0 && text.charAt(i - 1) == ' ') {
				    return text;
				} else {
				    if (text instanceof Spanned) {
				        SpannableString sp = new SpannableString(text + " ");
				        TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
				                Object.class, sp, 0);
				        return sp;
				    } else {
				        return text + " ";
				    }
				}
			}
			
			@Override
			public int findTokenStart(CharSequence text, int cursor) {
				int i = cursor;

				while (i > 0 && text.charAt(i - 1) != ' ') {
				    i--;
				}
				while (i < cursor && text.charAt(i) == ' ') {
				    i++;
				}

				return i;
			}
			
			@Override
			public int findTokenEnd(CharSequence text, int cursor) {
				int i = cursor;
				int len = text.length();

				while (i < len) {
				    if (text.charAt(i) == ' ') {
				        return i;
				    } else {
				        i++;
				    }
				}

				return len;
			}
		});
        
        matv.addTextChangedListener(new TextWatcher() {

        	@Override
        	public void onTextChanged(CharSequence s, int start, int before, int count) {
        		// TODO Auto-generated method stub
        		if (s.toString().startsWith("@")) {
        			Toast.makeText(getBaseContext(), "Mention", Toast.LENGTH_LONG).show();
        			
        		}
        		if(s.toString().startsWith("#")) {
        			Toast.makeText(getBaseContext(), "Hash", Toast.LENGTH_LONG).show();
        		}
        		
        		 
        	}

        	@Override
        	public void beforeTextChanged(CharSequence s, int start, int count,
        			int after) {
        		//change the color before editing

        	}

        	@Override
        	public void afterTextChanged(Editable s) {
        		//change the color after edited
        		     		
        	}});
        
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        
        Log.d("Status", " "+db.checkHash("#view"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_createote, menu);
        return true;
    }
    
    public void create(String str)
    {	
    	 Note nt = new Note(str, 1);
    	 hashAndMentionTags = new HashAndMentionTags(nt.getNote());
    	 hash = hashAndMentionTags.getHashTags();
    	 mention = hashAndMentionTags.getMentionTags();

		for(String hashs : hash)
    		if(db.checkHash(hashs)) 
    			h_id.add(db.createHash(new Hash(hashs)));
    		else
    			if(!h_id.contains(db.getHashId(hashs)))
    			h_id.add(db.getHashId(hashs));
		
    	 for(String mentions : mention) 
    		 if(db.checkMention(mentions))
    			 m_id.add(db.createMention(new Mention(mentions)));
    		 else
    			 if(!m_id.contains(db.getMentionId(mentions)))
    				 m_id.add(db.getMentionId(mentions));
    	
    	 db.createNote(nt,convertLongs(h_id) , convertLongs(m_id));	 
    	   	  		 
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save:
			create(matv.getText().toString());
			db.closeDB();
			this.finish();
			break;
		case R.id.cancel:
			db.close();
			this.finish();
			break;
		default:
			break;
		}
		
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
	
	 public static String[] getMentions(List<Mention> mentions)
	    {
	        String[] ret = new String[mentions.size()];
	        for (int i=0; i < ret.length; i++)
	        {
	            ret[i] = mentions.get(i).getMentionName();
	            
	        }
	        return ret;
	    }
	
	 	public static String[] combine(String[] h, String[] m){
	        int length = h.length + m.length;
	        String[] result = new String[length];
	        System.arraycopy(h, 0, result, 0, h.length);
	        System.arraycopy(m, 0, result, h.length, m.length);
	        return result;
	    }	
	 	
	 	
}
	
	