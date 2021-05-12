package com.kklldog.logindemo;

import com.kklldog.po.User;
import com.kklldog.services.UserService;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		Button btn = (Button)this.findViewById(R.id.btnLogin);
		
		final EditText tbxUser=(EditText)this.findViewById(R.id.txtUser);
		final EditText tbxPsw=(EditText)this.findViewById(R.id.txtPassword);
		
		btn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					
					UserService service=new UserService();
					User user = service.Login(tbxUser.getText().toString(), tbxPsw.getText().toString());
					
					
					if(user!=null){
						//go to user list
						Intent intent = new Intent();  
			            intent.setClass(LoginActivity.this,UserActivity.class);  
			            startActivity(intent);  
			            finish();
					}
					else{
						String msg="";
						msg="userName or psw is worng!";
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); 
						builder.setMessage(msg);
						builder.show(); 
					}
				
					
					
				} catch (Exception e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); 
					builder.setMessage(e.getMessage());
					builder.show(); 
					
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