package com.kklldog.core;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

public abstract class BaseService<T> {
	
	public abstract String getUrl();
	
	public T create(T entity,Class<T> cls ) throws Exception
	{
		String json = JSON.toJSONString(entity);
		String newJson = HttpHelper.doHttpPost(this.getUrl(), json);
		
		T item= (T) JSON.parseObject(newJson,cls);
		return item;
	}
	
	public void delete(String id) throws Exception
	{
	     List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	     nameValuePairs.add(new BasicNameValuePair("id",id));
	     
	     HttpHelper.doHttpDelete(this.getUrl(), nameValuePairs);
	}
	
	public void update(T entity) throws Exception
	{
		String josn = JSON.toJSONString(entity);
		HttpHelper.doHttpPut(this.getUrl(), josn);
	}
	
	public T getById(String id,Class<T> cls) throws Exception
	{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    nameValuePairs.add(new BasicNameValuePair("id",id));
	    String json = HttpHelper.doHttpGet(this.getUrl(), nameValuePairs);
	    
		T item= (T) JSON.parseObject(json,cls);
		
		return item;
	}
	
	public List<T> getAll(Class<T> cls) throws Exception
	{
		String json = HttpHelper.doHttpGet(this.getUrl());
		List<T>  list=  JSON.parseArray(json, cls);
		return list;
	}
}
