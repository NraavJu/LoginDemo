package com.kklldog.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/** 
 * @author mj.zhou 
 * @version create：2013-12-1 下午4:03:48 
 * 对话框工具类
 */
public class  Dialog {
	
	/**
	 * 显示一个Toast 
	 * @param message 内容
	 * @param context
	 */
	public static void showToast(String message,Context context)
	{
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	/**
	 * 显示一个提示对话框
	 * @param title 标题
	 * @param message 内容
	 * @param context
	 */
	public static void showAlert(String title,String message,Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setMessage(message);
		builder.setTitle(title);
		builder.show(); 
	}
	
	/**
	 * 显示一个提示对话框
	 * @param message 内容
	 * @param context
	 */
	public static void showAlert(String message,Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setMessage(message);
		builder.show(); 
	}
}
