package com.kklldog.logindemo;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.kklldog.po.User;
import com.kklldog.util.Uuid;
import com.kklldog.util.Dialog;
import com.kklldog.services.UserService;


public class UserEditActivity extends Activity {

	private User _user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_edit);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		Intent intent = this.getIntent();
		_user = (User)intent.getSerializableExtra("user");
		loadUser();
		initListener();
	}

	private void initListener() {
		final EditText txtName = (EditText)this.findViewById(R.id.txtEditName);
		final EditText pswName = (EditText)this.findViewById(R.id.txtEditPassword);
		final RadioButton rbSexF= (RadioButton)this.findViewById(R.id.rbSexF);
		final RadioButton rbSexM= (RadioButton)this.findViewById(R.id.rbSexM);
		
		Button btn = (Button)this.findViewById(R.id.btnSave);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(_user==null)
				{
					_user=new User();
				}
				_user.setName(txtName.getText().toString());
				_user.setPassword(pswName.getText().toString());
				if(rbSexF.isChecked())
				{
					_user.setSex(rbSexF.getText().toString());
				}
				else if	(rbSexM.isChecked())
				{
					_user.setSex(rbSexM.getText().toString());
				}
				UserService service = new UserService();
				if(_user.getId()==null)
				{
					// add
					_user.setId(Uuid.GetUUID());
					try {
						service.create(_user, User.class);
						Dialog.showToast("保存成功", UserEditActivity.this);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					//update
					try {
						service.update(_user);
						Dialog.showToast("保存成功", UserEditActivity.this);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
	}

	private void loadUser()
	{
		if(_user!=null)
		{
			EditText txtName = (EditText)this.findViewById(R.id.txtEditName);
			EditText pswName = (EditText)this.findViewById(R.id.txtEditPassword);
			RadioButton rbSexF= (RadioButton)this.findViewById(R.id.rbSexF);
			RadioButton rbSexM= (RadioButton)this.findViewById(R.id.rbSexM);
			
			txtName.setText(_user.getName());
			pswName.setText(_user.getPassword());
	
			if(rbSexF.getText().equals(_user.getSex()))
			{
				rbSexF.setChecked(true);
			}
			else if(rbSexM.getText().equals(_user.getSex()))
			{
				rbSexM.setChecked(true);
			}
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		
		
		return true;
	}

}
