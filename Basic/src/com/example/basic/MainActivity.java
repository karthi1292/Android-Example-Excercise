package com.example.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	  EditText e1;
	  Button b1;
	  final static int reqCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        e1=(EditText)findViewById(R.id.editText1);
        b1=(Button)findViewById(R.id.button1);
      
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,SubActivity.class);
				startActivityForResult(i, reqCode);
				
				
			}
		});
    
    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// TODO Auto-generated method stub

		if(requestCode == reqCode){
			if (resultCode == RESULT_OK){
				e1.setText(" ");
			}else if(resultCode == RESULT_CANCELED){


			}

		}

	}
  
    
}

