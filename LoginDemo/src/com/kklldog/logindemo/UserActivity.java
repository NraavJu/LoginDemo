package com.kklldog.logindemo;


import java.util.ArrayList;
import java.util.List;

import com.kklldog.po.User;
import com.kklldog.services.UserService;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		ListView list = (ListView) findViewById(R.id.userListView);  
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,GetUserInfoList()));
			

	}
	
	private List<String> GetUserInfoList() 
	{
		UserService service=new UserService();
		List<String> list =new ArrayList<String>();
		try {
			List<User> users= service.GetAll(User.class);
			for(User u : users)
			{
				list.add(u.getName()+"-"+u.getPassword()+"-"+u.getSex());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
