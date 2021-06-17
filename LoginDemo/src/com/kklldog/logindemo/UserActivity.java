package com.kklldog.logindemo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kklldog.po.User;
import com.kklldog.services.UserService;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/** 
 * @author mj.zhou 
 * @version create：2013-12-1 下午4:03:48 
 * User List
 */
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
		//加载用户列表
		loadUserListView();
	
	}

	/**
	 * 加载用户到ListView
	 */
	private void loadUserListView() {
		ListView list = (ListView) findViewById(R.id.userListView);  
		_userInfo=getUserInfoList();
		SimpleAdapter adapter = new SimpleAdapter(this,_userInfo,R.layout.user_item,
				new String[]{"userInfo"},
				new int[]{R.id.txtInfo});
		list.setAdapter(adapter);
	}
	
	/**
	 * 获取用户数据
	 * @return
	 */
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
				map.put("userObj",u);
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
		//添加菜单
		menu.add(Menu.NONE,1,1,"添加");
		menu.add(Menu.NONE,2,2,"删除");
		menu.add(Menu.NONE,3,3,"编辑");
		menu.add(Menu.NONE,4,4,"刷新");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		ListView list = (ListView) findViewById(R.id.userListView);  
	    SimpleAdapter adapter = (SimpleAdapter)list.getAdapter();
	    
		switch(item.getItemId())
		{
		    case 1://add
		    	Intent intent = new Intent();  
		    	intent.putExtra("user",new User());//传参
	            intent.setClass(UserActivity.this,UserEditActivity.class);  
	            startActivity(intent);  
	           // this.finish();
				  break;
		    case 2://delete
		   		UserService service=new UserService();
		   		List<Map<String, Object>> deletList =new ArrayList<Map<String, Object>>();
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
							service.delete(id);//删除
							deletList.add(map);
							cbx.setChecked(false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
	             }
		    	 for(Map<String, Object> m : deletList)
		    	 {
		    		 this._userInfo.remove(m);//从数据集里移除数据
		    	 }
		    	 adapter.notifyDataSetChanged();//通知UI更新
				  break;
		    case 3://edit
		    	for(int i = 0; i < list.getCount(); i++)//获取ListView的所有Item数目  
	            {  
		    		View view = list.getChildAt(i);
		    		CheckBox cbx =(CheckBox) view.findViewById(R.id.cbxSelected);
		    		if(cbx.isChecked())
		    		{
		    			@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) adapter.getItem(i);
		    			User u =(User) map.get("userObj");
		    			Intent intentEdit = new Intent();  
		    			intentEdit.putExtra("user",u);
		    			intentEdit.setClass(UserActivity.this,UserEditActivity.class);  
			            startActivity(intentEdit); 
		    			break;
		    		}
	            }
				  break;
		    case 4://刷新
		    	this.loadUserListView();
				  break;
			default:
				break;
		}
		
		return true;
	}

}
