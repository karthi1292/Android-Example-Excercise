package com.example.androiddatabase;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listview=(ListView)findViewById(R.id.list_item);
		
		DatabaseManipulation db=new DatabaseManipulation(this);

		Log.d("insert","inserting");
		db.addStudent(new StudentData("ravi","aja","klm"));
		db.addStudent(new StudentData("ram","manu","mahe"));
		db.addStudent(new StudentData("jainesh","raju","maveran"));

		Log.d("Read:","reading");
		
		List<StudentData> students= new ArrayList<StudentData>(); 
		students = db.getAllContacts();

		String[] arr= new String[students.size()];  
		for(int i=0; i<students.size();i++) {
			arr[i]=students.get(i).getName() +students.get(i).getFathername() +students.get(i).getMothername();
		Toast.makeText(MainActivity.this,arr[i],3000).show();
		}
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>
		(this,android.R.layout.simple_list_item_1, arr);
		
	listview.setAdapter(adapter);
		

	for (StudentData an:students) {
		
	
		String log="Id:"+an.getID()+",Name:"+an.getName()+",fathername:"+an.getFathername()+",mothername:"+an.getMothername();
		
		Log.d("Name:",log);
		
		
	}
	}
	

}
