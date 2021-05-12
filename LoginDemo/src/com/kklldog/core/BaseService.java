package com.kklldog.core;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

public abstract class BaseService<T> {
	
	public abstract String GetUrl();
	
	public T Create(T entity,Class<T> cls ) throws Exception
	{
		String json = JSON.toJSONString(entity);
		String newJson = HttpHelper.doHttpPost(this.GetUrl(), json);
		
		T item= (T) JSON.parseObject(newJson,cls);
		return item;
	}
	
	public void Delete(String id) throws Exception
	{
	     List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	     nameValuePairs.add(new BasicNameValuePair("id",id));
	     
	     HttpHelper.doHttpDelete(this.GetUrl(), nameValuePairs);
	}
	
	public void Update(T entity) throws Exception
	{
		String josn = JSON.toJSONString(entity);
		HttpHelper.doHttpPut(this.GetUrl(), josn);
	}
	
	public T GetById(String id,Class<T> cls) throws Exception
	{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    nameValuePairs.add(new BasicNameValuePair("id",id));
	    String json = HttpHelper.doHttpGet(this.GetUrl(), nameValuePairs);
	    
		T item= (T) JSON.parseObject(json,cls);
		
		return item;
	}
	
	public List<T> GetAll(Class<T> cls) throws Exception
	{
		String json = HttpHelper.doHttpGet(this.GetUrl());
		List<T>  list=  JSON.parseArray(json, cls);
		return list;
	}
}
