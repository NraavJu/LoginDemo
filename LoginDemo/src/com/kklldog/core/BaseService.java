package com.kklldog.core;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
/** 
 * @author mj.zhou 
 * @version create：2013-12-3 下午4:03:48 
 * 服务的基类 默认实现CRUD操作
 */
public abstract class BaseService<T> {
	/**
	 * 返回服务对应的URL地址
	 * @return
	 */
	public abstract String getUrl();
	
	/**
	 * Create entity 
	 * @param entity 实体
	 * @param cls 类型
	 * @return 服务器端返回的实体
	 * @throws Exception
	 */
	public T create(T entity,Class<T> cls ) throws Exception
	{
		String json = JSON.toJSONString(entity);
		String newJson = HttpHelper.doHttpPost(this.getUrl(), json);
		
		T item= (T) JSON.parseObject(newJson,cls);
		return item;
	}
	
	/**
	 * 删除一个实体
	 * @param id 实体的ID
	 * @throws Exception
	 */
	public void delete(String id) throws Exception
	{
	     List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	     nameValuePairs.add(new BasicNameValuePair("id",id));
	     
	     HttpHelper.doHttpDelete(this.getUrl(), nameValuePairs);
	}
	
	/**
	 * 更新一个实体
	 * @param entity 实体
	 * @throws Exception
	 */
	public void update(T entity) throws Exception
	{
		String josn = JSON.toJSONString(entity);
		HttpHelper.doHttpPut(this.getUrl(), josn);
	}
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public T getById(String id,Class<T> cls) throws Exception
	{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    nameValuePairs.add(new BasicNameValuePair("id",id));
	    String json = HttpHelper.doHttpGet(this.getUrl(), nameValuePairs);
	    
		T item= (T) JSON.parseObject(json,cls);
		
		return item;
	}
	
	/**
	 * 获取所有的实体
	 * @param cls 类型
	 * @return
	 * @throws Exception
	 */
	public List<T> getAll(Class<T> cls) throws Exception
	{
		String json = HttpHelper.doHttpGet(this.getUrl());
		List<T>  list=  JSON.parseArray(json, cls);
		return list;
	}
}
