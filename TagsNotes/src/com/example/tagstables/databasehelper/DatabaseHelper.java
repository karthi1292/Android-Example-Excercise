package com.example.tagstables.databasehelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tagstables.model.Hash;
import com.example.tagstables.model.Mention;
import com.example.tagstables.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper {

		 
	    // Logcat tag
	    private static final String LOG = "DatabaseHelper";
	 
	    // Database Version
	    private static final int DATABASE_VERSION = 1;
	 
	    // Database Name
	    private static final String DATABASE_NAME = "NoteTagsManager";
	 
	    // Table Names
	    private static final String TABLE_NOTE = "notes";
	    private static final String TABLE_HASH = "hashs";
	    private static final String TABLE_MENTION = "mentions";
	    private static final String TABLE_NOTE_HASH = "note_hashs";
	    private static final String TABLE_NOTE_MENTION = "note_mentions";
	    
	    // Common column names
	    private static final String KEY_ID = "id";
	    private static final String KEY_CREATED_AT = "created_at";
	 
	    // NOTES Table - column nmaes
	    private static final String KEY_NOTE = "note";
	    private static final String KEY_COLOR= "color";
	 
	    // HASHS AND MENTIONS Table - column names
	    private static final String KEY_HASH_NAME = "hash_name";
	    private static final String KEY_MENTION_NAME = "mention_name";
	 
	    // NOTE_HASHS AND NOTE_MENTIONS Table - column names
	    private static final String KEY_NOTE_ID = "note_id";
	    private static final String KEY_HASH_ID = "hash_id";
	    private static final String KEY_MENTION_ID = "mention_id";
	 
	    // Table Create Statements
	    // Note table create statement
	    private static final String CREATE_TABLE_NOTE = "CREATE TABLE "
	            + TABLE_NOTE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOTE
	            + " TEXT," + KEY_COLOR + " INTEGER," + KEY_CREATED_AT
	            + " DATETIME" + ")";
	 
	    // Hash table create statement
	    private static final String CREATE_TABLE_HASH = "CREATE TABLE " + TABLE_HASH
	            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HASH_NAME + " TEXT,"
	            + KEY_CREATED_AT + " DATETIME" + ")";
	 
	    // note_hashs table create statement
	    private static final String CREATE_TABLE_NOTE_HASH = "CREATE TABLE "
	            + TABLE_NOTE_HASH + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
	            + KEY_NOTE_ID + " INTEGER," + KEY_HASH_ID + " INTEGER,"
	            + KEY_CREATED_AT + " DATETIME" + ")";
	    
	 // Mention table create statement
	    private static final String CREATE_TABLE_MENTION = "CREATE TABLE " + TABLE_MENTION
	            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MENTION_NAME + " TEXT,"
	            + KEY_CREATED_AT + " DATETIME" + ")";
	 
	    // note_mentions table create statement
	    private static final String CREATE_TABLE_NOTE_MENTION = "CREATE TABLE "
	            + TABLE_NOTE_MENTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
	            + KEY_NOTE_ID + " INTEGER," + KEY_MENTION_ID + " INTEGER,"
	            + KEY_CREATED_AT + " DATETIME" + ")";
	 
	    public DatabaseHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	 
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	 
	        // creating required tables
	        db.execSQL(CREATE_TABLE_NOTE);
	        db.execSQL(CREATE_TABLE_HASH);
	        db.execSQL(CREATE_TABLE_NOTE_HASH);
	        db.execSQL(CREATE_TABLE_MENTION);
	        db.execSQL(CREATE_TABLE_NOTE_MENTION);
	    }
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // on upgrade drop older tables
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HASH);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_HASH);
	 
	        // create new tables
	        onCreate(db);
	    }
	    
	    public long createNote(Note note, long[] hash_ids,long[] mention_ids) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_NOTE, note.getNote());
	        values.put(KEY_COLOR, note.getcolor());
	        values.put(KEY_CREATED_AT, getDateTime());
	 
	        // insert row
	        long note_id = db.insert(TABLE_NOTE, null, values);
	 
	        // insert tag_ids
	        for (long hash_id : hash_ids) {
	             createNoteHash(note_id, hash_id);
	        }
	        
	        for (long mention_id : mention_ids) {
	             createNoteMeniton(note_id, mention_id);
	        }
	        
	 
	        return note_id;
	    }
	    
	    public long createHash(Hash hash) {
	        SQLiteDatabase db = this.getWritableDatabase();
	     
	        ContentValues values = new ContentValues();
	        values.put(KEY_HASH_NAME, hash.getHashName());
	        values.put(KEY_CREATED_AT, getDateTime());
	     
	        // insert row
	        long hash_id = db.insert(TABLE_HASH, null, values);
	     
	        return hash_id;
	    }
	    
	    public long createMention(Mention mention) {
	        SQLiteDatabase db = this.getWritableDatabase();
	     
	        ContentValues values = new ContentValues();
	        values.put(KEY_MENTION_NAME, mention.getMentionName());
	        values.put(KEY_CREATED_AT, getDateTime());
	     
	        // insert row
	        long mention_id = db.insert(TABLE_MENTION, null, values);
	     
	        return mention_id;
	    }
	    
	    public long createNoteHash(long note_id, long hash_id) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_NOTE_ID, note_id);
	        values.put(KEY_HASH_ID, hash_id);
	        values.put(KEY_CREATED_AT, getDateTime());
	 
	        long id = db.insert(TABLE_NOTE_HASH, null, values);
	 
	        return id;
	    }
	    
	    public long createNoteMeniton(long note_id, long mention_id) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_NOTE_ID, note_id);
	        values.put(KEY_MENTION_ID, mention_id);
	        values.put(KEY_CREATED_AT, getDateTime());
	 
	        long id = db.insert(TABLE_NOTE_MENTION, null, values);
	 
	        return id;
	    }
	    
	    public boolean checkHash(final String hash_name) {
	    	Boolean hashStatus = false;
	        SQLiteDatabase db = this.getReadableDatabase();
	     
	       Cursor c= db.query(
	        	    TABLE_HASH ,
	        	    new String[] { "hash_name" },
	        	    "hash_name = ?" ,
	        	    new String[] { hash_name } ,
	        	    null ,
	        	    null ,
	        	    null
	        	);

			if (c.getCount() > 0) {
				hashStatus = false;
				Log.d("HashStatus",String.valueOf(hashStatus));
			}
			else {
				hashStatus=true;
				Log.d("HashStatus",String.valueOf(hashStatus));
			}
	            
			c.close();
	     
	        return hashStatus;
	    }
	    
	    public boolean checkMention(String mention_name) {
	    	Boolean menStatus = false;
	        SQLiteDatabase db = this.getReadableDatabase();
	     
	        Cursor c= db.query(
	        	    TABLE_MENTION ,
	        	    new String[] { "mention_name" },
	        	    "mention_name = ?" ,
	        	    new String[] { mention_name } ,
	        	    null ,
	        	    null ,
	        	    null
	        	);
	        
	   
			if (c.getCount() > 0){
				menStatus = false;
				Log.d("MentionStatus",String.valueOf(menStatus));
			}
			else {
				menStatus=true;
				Log.d("MentionStatus",String.valueOf(menStatus));
			}
			c.close();
			
	        return menStatus;
	    }
	    
	    
	 // closing database
	    public void closeDB() {
	        SQLiteDatabase db = this.getReadableDatabase();
	        if (db != null && db.isOpen())
	            db.close();
	    }
	    
	    private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	    }
	    
	    public List<Note> getAllNotes() {
	        List<Note> notes = new ArrayList<Note>();
	        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;
	     
	        Log.e(LOG, selectQuery);
	     
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	            	Note nt = new Note();
	                nt.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	                nt.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
	                nt.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	     
	                // adding to notes list
	                notes.add(nt);
	            } while (c.moveToNext());
	        }
	     
	        return notes;
	    }
	    
	    public List<Hash> getAllHash() {
	        List<Hash> hashs = new ArrayList<Hash>();
	        String selectQuery = "SELECT  * FROM " + TABLE_HASH;
	 
	        Log.e(LOG, selectQuery);
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	                Hash h = new Hash();
	                h.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	                h.setHashName(c.getString(c.getColumnIndex(KEY_HASH_NAME)));
	 
	                // adding to hash list
	               hashs.add(h);
	            } while (c.moveToNext());
	        }
	        return hashs;
	     }


	    public List<Mention> getAllMention() {
	        List<Mention> mentions = new ArrayList<Mention>();
	        String selectQuery = "SELECT  * FROM " + TABLE_MENTION;
	 
	        Log.e(LOG, selectQuery);
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	                Mention m = new Mention();
	                m.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	                m.setMentionName(c.getString(c.getColumnIndex(KEY_MENTION_NAME)));
	 
	                // adding to mention list
	               mentions.add(m);
	            } while (c.moveToNext());
	        }
	        return mentions;
	    }
	    
	    public List<Note> getAllNotesByHash(String hash_name) {
	        List<Note> nts = new ArrayList<Note>();
	 
	        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " td, "
	                + TABLE_HASH + " tg, " + TABLE_NOTE_HASH + " tt WHERE tg."
	                + KEY_HASH_NAME + " = '" + hash_name + "'" + " AND tg." + KEY_ID
	                + " = " + "tt." + KEY_HASH_ID + " AND td." + KEY_ID + " = "
	                + "tt." + KEY_NOTE_ID;
	 
	        Log.e(LOG, selectQuery);
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	               Note nt = new Note();
	                nt.setId(c.getInt((c.getColumnIndex(KEY_NOTE_ID))));
	                nt.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
	                nt.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	 
	                // adding to notes list
	                nts.add(nt);
	            } while (c.moveToNext());
	        }
	 
	        return nts;
	    }
	    
	    
	    public List<Note> getAllNotesByMention(String mention_name) {
	        List<Note> nts = new ArrayList<Note>();
	 
	        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " td, "
	                + TABLE_MENTION + " tg, " + TABLE_NOTE_MENTION + " tt WHERE tg."
	                + KEY_MENTION_NAME + " = '" + mention_name + "'" + " AND tg." + KEY_ID
	                + " = " + "tt." + KEY_MENTION_ID + " AND td." + KEY_ID + " = "
	                + "tt." + KEY_NOTE_ID;
	 
	        Log.e(LOG, selectQuery);
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	               Note nt = new Note();
	                nt.setId(c.getInt((c.getColumnIndex(KEY_NOTE_ID))));
	                nt.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
	                nt.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	 
	                // adding to notes list
	                nts.add(nt);
	            } while (c.moveToNext());
	        }
	 
	        return nts;
	    }
	    public Note getNote(long note_id) {
	        SQLiteDatabase db = this.getReadableDatabase();
	 
	        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE "
	                + KEY_ID + " = " + note_id;
	 
	        Log.e(LOG, selectQuery);
	 
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        if (c != null)
	            c.moveToFirst();
	 
	        Note nt = new Note();
	        nt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	        nt.setNote((c.getString(c.getColumnIndex(KEY_NOTE))));
	        nt.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	 
	        return nt;
	    }
	    public int updateToDo(Note note) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_NOTE, note.getNote());
	       
	        // updating row
	        return db.update(TABLE_NOTE, values, KEY_ID + " = ?",
	                new String[] { String.valueOf(note.getId()) });
	    }
	    
	    public long getHashId(final String hash_name) {
	        SQLiteDatabase db = this.getReadableDatabase();
	     
	       Cursor c= db.query(
	        	    TABLE_HASH ,
	        	    new String[] { "id" },
	        	    "hash_name = ?",
	        	    new String[] { hash_name },
	        	    null ,
	        	    null,
	        	    null 
	        	);
	  
	       if (c != null)
	            c.moveToFirst();
	 
	       int id = c.getInt(c.getColumnIndex(KEY_ID));
	            
			c.close();
	     
	        return id;
	    }
	    
	    public long getMentionId(final String men_name) {
	        SQLiteDatabase db = this.getReadableDatabase();
	     
	       Cursor c= db.query(
	        	    TABLE_MENTION ,
	        	    new String[] { "id" },
	        	    "mention_name = ?",
	        	    new String[] { men_name },
	        	    null,
	        	    null,
	        	    null 
	        	);
	  
	       if (c != null)
	            c.moveToFirst();
	 
	       int id = c.getInt(c.getColumnIndex(KEY_ID));
	            
			c.close();
	     
	        return id;
	    }
	    
	    public void deleteNote(long note_id) {
	    		    	
	        SQLiteDatabase db = this.getWritableDatabase();
	        
	        db.delete(TABLE_NOTE, KEY_ID + " = ?",
	                new String[] { String.valueOf(note_id) });
	        
	        deleteHashByNoteId(note_id);
	        deleteMentionByNoteId(note_id);
	        
	    }
	    
	    public void deleteHashByNoteId(long note_id) {
	    	
	    	List<Hash> array = new ArrayList<Hash>();
	    	
	        SQLiteDatabase del_db = this.getWritableDatabase();
	        SQLiteDatabase read_db = this.getReadableDatabase();
	        
	        String selectQurey = " SELECT * FROM "+TABLE_NOTE_HASH + " WHERE " + KEY_NOTE_ID +" = " + note_id; 
	        Cursor c= read_db.rawQuery(selectQurey, null);
	        
	        if(c!=null) {
	        	if(c.moveToFirst()) {
	        		do {
	        			Hash h = new Hash();
	        			h.setId(c.getInt(c.getColumnIndex(KEY_HASH_ID)));
	        			array.add(h);
	        		}while(c.moveToNext());
	        	}
	        	del_db.delete(TABLE_NOTE_HASH, KEY_NOTE_ID + " = ? ", new String[]{String.valueOf(note_id)});
	        }
	        
	        for(Hash id : array){
	        	Cursor hash_cursor = read_db.query(
		        	    TABLE_NOTE_HASH ,
		        	    new String[] { "id" },
		        	    KEY_HASH_ID +"= ?",
		        	    new String[] { String.valueOf(id.getId()) },
		        	    null ,
		        	    null,
		        	    null 
		        	);
	        	
	        	if(c!=null) {
	        		if(hash_cursor.getCount() > 0) {
	        			        		
	        		}
	        		else {
	        		
	        			del_db.delete(TABLE_HASH, KEY_ID+ " = ? ", new String[] {String.valueOf(id.getId())});
	        		}
	        		
	        	}
	        	
	        	hash_cursor.close();
	        }
	        
	        c.close();
	       
	    }
	    
	    public void deleteMentionByNoteId(long note_id) {
	    	
	    	List<Mention> array = new ArrayList<Mention>();
	    	
	        SQLiteDatabase del_db = this.getWritableDatabase();
	        SQLiteDatabase read_db = this.getReadableDatabase();
	        
	        String selectQurey = " SELECT * FROM "+TABLE_NOTE_MENTION + " WHERE " + KEY_NOTE_ID +" = " + note_id; 
	        Cursor c= read_db.rawQuery(selectQurey, null);
	        
	        if(c!=null) {
	        	if(c.moveToFirst()) {
	        		do {
	        			Mention m = new Mention();
	        			m.setId(c.getInt(c.getColumnIndex(KEY_MENTION_ID)));
	        			array.add(m);
	        		}while(c.moveToNext());
	        	}
	        	del_db.delete(TABLE_NOTE_MENTION, KEY_NOTE_ID + " = ? ", new String[]{String.valueOf(note_id)});
	        }
	        
	        for(Mention id : array){
	        	Cursor men_cursor = read_db.query(
		        	    TABLE_NOTE_MENTION ,
		        	    new String[] { "id" },
		        	    KEY_MENTION_ID +"= ?",
		        	    new String[] { String.valueOf(id.getId()) },
		        	    null ,
		        	    null,
		        	    null 
		        	);
	        	
	        	if(c!=null) {
	        		if(men_cursor.getCount() > 0) {
	        			        		
	        		}
	        		else {
	        		
	        			del_db.delete(TABLE_MENTION, KEY_ID+ " = ? ", new String[] {String.valueOf(id.getId())});
	        		}
	        		
	        	}
	        	
	        	men_cursor.close();
	        }
	        
	        c.close();
	       
	    }


	public List<Note> searchNote(final String word) {
		
		List<Note> filter = new ArrayList<Note>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_NOTE, new String[]{KEY_ID,KEY_NOTE}
	    ,KEY_NOTE+" LIKE ?" ,new String[]{"%"+ word+"%" }, null, null, null);
	   
		if(c!=null) {
		if(c.moveToFirst()) {
			do {
				Note nt = new Note();
				nt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				nt.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
				filter.add(nt);
			}while(c.moveToNext());
		}
	   }
		
		return filter;
	}

}