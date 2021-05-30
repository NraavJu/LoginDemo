package com.kklldog.logindemo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kklldog.po.User;
import com.kklldog.services.UserService;
import com.kklldog.util.Dialog;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserActivity extends Activity {

	private List<Map<String, Object>> _userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		LoadUserListView();
		//list.geti
	}

	private void LoadUserListView() {
		ListView list = (ListView) findViewById(R.id.userListView);  
		_userInfo=getUserInfoList();
		SimpleAdapter adapter = new SimpleAdapter(this,_userInfo,R.layout.user_item,
				new String[]{"userInfo"},
				new int[]{R.id.txtInfo});
		list.setAdapter(adapter);
	}
	
	private List<Map<String, Object>> getUserInfoList() 
	{
		UserService service=new UserService();
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		
		try {
			List<User> users= service.getAll(User.class);
			String info="姓名：%s 密码：%s 性别：%s";
			for(User u : users)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userInfo",String.format(info,u.getName(),u.getPassword(),u.getSex()));
				map.put("userId",u.getId());
				list.add(map);
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
		//getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE,1,1,"添加");
		menu.add(Menu.NONE,2,2,"删除");
		menu.add(Menu.NONE,3,3,"编辑");
		menu.add(Menu.NONE,4,4,"刷新");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		    case 1:
		    	Dialog.showToast("Add", this.getApplicationContext() );
				  break;
		    case 2:
		    	
		    	ListView list = (ListView) findViewById(R.id.userListView);  
		    	SimpleAdapter adapter = (SimpleAdapter)list.getAdapter();
		   		UserService service=new UserService();
		   		
		    	for(int i = 0; i < list.getCount(); i++)//获取ListView的所有Item数目  
	            {  
		    		View view = list.getChildAt(i);
		    		CheckBox cbx =(CheckBox) view.findViewById(R.id.cbxSelected);
		    		if(cbx.isChecked())
		    		{
		    			@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) adapter.getItem(i);
		    			String id =(String) map.get("userId");
		    			try {
							service.delete(id);
							_userInfo.remove(map);
							cbx.setChecked(false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
	             }
		    	 
		    	 adapter.notifyDataSetChanged();
				  break;
		    case 3:
		    	Dialog.showToast("Edit", this.getApplicationContext() );
				  break;
		    case 4:
		    	this.LoadUserListView();
				  break;
			default:
				break;
		}
		
		return true;
	}

}
