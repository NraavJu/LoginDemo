package com.kklldog.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.kklldog.app.Config;
import com.kklldog.core.BaseService;
import com.kklldog.core.HttpHelper;
import com.kklldog.po.User;

/** 
 * @author mj.zhou 
 * @version create：2013-12-1 下午4:03:48 
 * UserService
 */
public class UserService extends BaseService<User> {

	@Override
	public String getUrl() {
		
		return Config.USER_SERVICE_URL;
	}
	
	/**
	 * login
	 * @param userName 用户名
	 * @param password 密码
	 * @return 返回对象实体
	 * @throws Exception
	 */
	public User login(String userName,String password) throws Exception{
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
		nameValuePairs.add(new BasicNameValuePair("userName",userName));
		nameValuePairs.add(new BasicNameValuePair("password",password));
		String str=HttpHelper.doHttpGet(this.getUrl(), nameValuePairs);
		User user = JSON.parseObject(str,User.class);
		return user;
	}
	
}
