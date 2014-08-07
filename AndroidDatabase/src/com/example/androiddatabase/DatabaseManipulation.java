package com.example.androiddatabase;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManipulation extends SQLiteOpenHelper{

	
	private static int DATABASE_VERSION=1;
	
	private static final String DATABASE_NAME ="studentdata";
	
	private static final String DATABASE_TABLE="karthi";
	
	private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_FATHER_NAME="fathername";
    private static final String KEY_MOTHER_NAME="mothername";
	
    
	

	public DatabaseManipulation(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_BIODATA=" CREATE TABLE "+ DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
				KEY_NAME + " TEXT," + KEY_FATHER_NAME  + " TEXT," +
				KEY_MOTHER_NAME + " TEXT" + ")";
		db.execSQL(CREATE_BIODATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(" DROP TABLE IF EXISTS "+ DATABASE_TABLE);	
		onCreate(db);
	}
	
	public void addStudent(StudentData data){
		
		SQLiteDatabase db=this.getWritableDatabase();
		
		ContentValues values=new ContentValues();
		values.put(KEY_NAME, data.getName());
		values.put(KEY_FATHER_NAME, data.getFathername());
		values.put(KEY_MOTHER_NAME, data.getMothername());
		
		db.insert(DATABASE_TABLE,null,values);
		db.close();
	}

	
	public StudentData getStudent(int id){
		
		SQLiteDatabase db=this.getWritableDatabase();
		
		Cursor cursor=db.query(DATABASE_TABLE, new String[]{ KEY_ID, KEY_NAME, KEY_FATHER_NAME, KEY_MOTHER_NAME }, KEY_ID  + "=",
				new String[] {String.valueOf(id)},null,null,null,null);
		
		        if(cursor != null)
		        	cursor.moveToFirst();
	
		        StudentData data=new StudentData(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
	
		        return data;
	}
	
	public List<StudentData> getAllContacts(){
		
		List<StudentData> studentlist=new ArrayList<StudentData>();
		
		String selectQuery="SELECT * FROM "+ DATABASE_TABLE;
		SQLiteDatabase ab=this.getWritableDatabase();
		Cursor cursor=ab.rawQuery(selectQuery,null);
		if(cursor.moveToFirst()){
			do{
				
			
			StudentData student=new StudentData();
			student.setID(Integer.parseInt(cursor.getString(0)));
			student.setName(cursor.getString(1));
			student.setFathername(cursor.getString(2));
			student.setMothername(cursor.getString(3));
			studentlist.add(student); }while(cursor.moveToNext());
		}
		
		return studentlist;
		
	}
}
