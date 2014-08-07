package com.example.tagstables;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.example.tagstables.databasehelper.DatabaseHelper;
import com.example.tagstables.model.Note;

public class AllNotesActivity extends Activity implements OnItemClickListener, OnMenuItemClickListener{
	
	private ListView allnotes;
	DatabaseHelper dbs;
    final int option_edit_id = 0;
    final int option_delete_id = option_edit_id+1;
	int itemPosition;
	static long[] ids;
	private String[] str ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        dbs = new DatabaseHelper(getApplicationContext());
        allnotes=(ListView)findViewById(R.id.list);
        
        str =new String[getNotes(dbs.getAllNotes()).length];
        str = getNotes(dbs.getAllNotes());
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_list_item_1,android.R.id.text1,str){ 
        	 @Override
             public View getView(int position, View convertView, ViewGroup parent) 
             {
                 View row;
                 LayoutInflater mInflater = LayoutInflater.from(getContext()); 

                 if (null == convertView) {
                 row = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                 } else {
                 row = convertView;
                 }

                 TextView tv = (TextView) row.findViewById(android.R.id.text1);
 	             String[] str_array = str[position].split(" ");

 	            for (int j = 0; j < str_array.length; j++) {
 	                if (str_array[j].startsWith("@")) {
 	                    str[position]=str[position].replaceAll
 	                    		(str_array[j], "<strong><font color='#E9967A'>" + str_array[j] + "</font></strong>");
 	                } else if (str_array[j].startsWith("#")) {
 	                    str[position]=str[position].replaceAll
 	                    		(str_array[j], "<strong><font color='#7B68EE'>" + str_array[j] + "</font></strong>");
 	                }
 	            }
                tv.setText(Html.fromHtml(str[position]));

                 return row;
             }

        };
        
        allnotes.setAdapter(adapter);
        allnotes.setOnItemClickListener(this);
        registerForContextMenu(allnotes);
        
        //search
        
      
        
        
        dbs.closeDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_all_notes, menu);
    	
    	final SearchView searchView = new SearchView(getActionBar().getThemedContext());
        searchView.setQueryHint("Search");

        menu.add(Menu.NONE,Menu.NONE,1,"Search")
            .setIcon(android.R.drawable.ic_menu_search)
            .setActionView(searchView)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    	
        	searchView.setOnQueryTextListener(new OnQueryTextListener() { 

            @Override 
            public boolean onQueryTextChange(String query) {
            	
            	/*List<Note> data = dbs.searchNote(query);
                //
                for(Note nt:data) {
                	Log.d("Note",nt.getNote());
                }*/
            	
            	loadDataSets(query);
            	
                return true; 

            }

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				return false;
			} 

        });
        
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
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
	  public void onCreateContextMenu(ContextMenu menu, View v,
	          ContextMenuInfo menuInfo) {
	      super.onCreateContextMenu(menu, v, menuInfo);
	     
	     AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	     itemPosition=(int)info.id;
	     menu.setHeaderTitle("Options");
	     menu.add(0, option_edit_id, 0, "Edit").setOnMenuItemClickListener(this);
	     menu.add(0, option_delete_id, 1, "Delete").setOnMenuItemClickListener(this);
	    
	 }

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub

		 switch (item.getItemId()) {
			case option_edit_id:
				//Log.d("IDS",""+ ids[itemPosition]);
				Intent updateNote = new Intent (this,UpdateNoteActivity.class);
				updateNote.putExtra("NoteId", ids[itemPosition]); 
				startActivity(updateNote);
				this.finish();
				break;
				
			case option_delete_id:
				new DatabaseHelper(getApplicationContext()).deleteNote(ids[itemPosition]);
				new DatabaseHelper(getApplicationContext()).closeDB();
				this.finish();
				Intent refresh = new Intent(this,AllNotesActivity.class);
				startActivity(refresh);
				break;
				
			default:
				break;
			}
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.NONE:
			
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	

	public void loadDataSets(final String query) {
		
		 str =new String[getNotes(dbs.searchNote(query)).length];
	        str = getNotes(dbs.searchNote(query));
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>
	        (this, android.R.layout.simple_list_item_1,android.R.id.text1,str){ 
	        	 @Override
	             public View getView(int position, View convertView, ViewGroup parent) 
	             {
	                 View row;
	                 LayoutInflater mInflater = LayoutInflater.from(getContext()); 

	                 if (null == convertView) {
	                 row = mInflater.inflate(android.R.layout.simple_list_item_1, null);
	                 } else {
	                 row = convertView;
	                 }

	                 TextView tv = (TextView) row.findViewById(android.R.id.text1);
	 	             String[] str_array = str[position].split(" ");

	 	            for (int j = 0; j < str_array.length; j++) {
	 	                if (str_array[j].startsWith("@")) {
	 	                    str[position]=str[position].replaceAll
	 	                    		(str_array[j], "<strong><font color='#E9967A'>" + str_array[j] + "</font></strong>");
	 	                } else if (str_array[j].startsWith("#")) {
	 	                    str[position]=str[position].replaceAll
	 	                    		(str_array[j], "<strong><font color='#7B68EE'>" + str_array[j] + "</font></strong>");
	 	                }
	 	            }
	                tv.setText(Html.fromHtml(str[position]));

	                 return row;
	             }

	        };
	        
	        allnotes.setAdapter(adapter);
	        allnotes.setOnItemClickListener(this);
	        registerForContextMenu(allnotes);
	        
	}
	
}


