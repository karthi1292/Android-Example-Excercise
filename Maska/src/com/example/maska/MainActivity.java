package com.example.maska;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class MainActivity extends Activity {

	String classes[]={"thanikai","vijay","praveen","mohideen"};
	
	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listview=(ListView)findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,classes);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	               
	        	  String product = ((TextView) view).getText().toString();
	              
//	        		Intent intent=new Intent(MainActivity.this,Next.class);
//	        		intent.putExtra("product", product);
//	        		startActivity(intent);
	        	
	             
	          }
	        });
		
	
	
	}
}
