package com.service.impl;

import com.service.UserDetailsService;

/**
 * 
 * @Title: MyUserDetailsServiceImpl
 * @Description: 用户登录验证
 * @Author:Administrator
 * @Since:2016年9月5日 上午11:32:57
 * @Version:1.1.0
 */
public class MyUserDetailsServiceImpl implements UserDetailsService {
	// @Resource
	// private UserDao userDao;

	@Override
	public Object loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;

		// Collection<GrantedAuthority> auths = new
		// ArrayList<GrantedAuthority>();
		// UserEntity user = null;
		// try {
		// user = userDao.getUserByName(username);
		// List<String> authStr = userDao.loadUserAuthoritiesByName(username);
		// for (String authName : authStr) {
		// SimpleGrantedAuthority authority = new
		// SimpleGrantedAuthority(authName);
		// auths.add(authority);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return new User(user.getId(), user.getUserName(), user.getPassword(),
		// user.getEmail(), user.getCreateDate(),
		// user.getUserName(), auths, true, true, true);
	}

}
