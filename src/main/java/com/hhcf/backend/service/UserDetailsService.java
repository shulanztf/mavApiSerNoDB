package com.hhcf.backend.service;


/**
 * 
 * 
 * @ClassName: UserDetailsService
 * @Description:
 * @author: zhaotf
 * @date: 2017年5月28日 下午3:29:57
 */
public interface UserDetailsService {
	public Object loadUserByUsername(String username);
}
