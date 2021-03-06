package com.kklldog.logindemo;

import com.kklldog.po.User;
import com.kklldog.services.UserService;
import com.kklldog.util.Dialog;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/** 
 * @author mj.zhou 
 * @version create：2013-12-1 下午4:03:48 
 * Login
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		initListener();
	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		
		Button btn = (Button)this.findViewById(R.id.btnLogin);
		final EditText tbxUser=(EditText)this.findViewById(R.id.txtUser);
		final EditText tbxPsw=(EditText)this.findViewById(R.id.txtPassword);
		
		btn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					
					UserService service=new UserService();
					//登陆
					User user = service.login(tbxUser.getText().toString(), tbxPsw.getText().toString());
					if(user!=null){
						//go to user list
						Intent intent = new Intent();  
			            intent.setClass(LoginActivity.this,UserActivity.class);  
			            startActivity(intent);  
			            finish();
					}
					else{
						String msg="";
						msg="用户名或密码错误!";
						Dialog.showAlert(msg,LoginActivity.this );
					}

				} catch (Exception e) {
					Dialog.showAlert("异常", e.getMessage(),LoginActivity.this );	
				}
			};
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
