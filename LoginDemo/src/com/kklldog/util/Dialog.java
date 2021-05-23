package com.kklldog.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class  Dialog {
	
	public static void showToast(String message,Context context)
	{
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public static void showAlert(String title,String message,Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setMessage(message);
		builder.setTitle(title);
		builder.show(); 
	}
	
	public static void showAlert(String message,Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setMessage(message);
		builder.show(); 
	}
}
