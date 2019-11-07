package com.appcloud.vm.fe.manager;

import java.util.List;

import com.appcloud.vm.fe.model.User;
import com.appcloud.vm.fe.model.UserDAO;

@Deprecated
public class UserManager {
	private UserDAO dao = new UserDAO();
	
	/**
	 * 取得所有用户列表
	 * @return
	 */
	public List<User> getAllUsers() {
		return dao.findAll();
	}
	
	/**
	 * 检查某个用户是否存在，不存在的话，保存一条记录
	 * @param id
	 * @param email
	 */
	public void checkAndSaveUser(Integer id, String email) {
		User user = dao.findById(id);
		if (user == null) {
			dao.save(new User(id, email));
		}
	}
	
	public User getUser(int userId){
	    return dao.findById(userId);
	}
}
