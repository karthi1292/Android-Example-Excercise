package com.example.tagstables;

import android.content.Context;
import android.content.Intent;

public class StartMyActivity {
	
	private Context context;
	
	public StartMyActivity(Context context)
	{
		this.context=context;
	}
	
	public void getIntent(Class<?> class1) {
		Intent intent = new Intent(context,class1);
		context.startActivity(intent);
		
	}

}
