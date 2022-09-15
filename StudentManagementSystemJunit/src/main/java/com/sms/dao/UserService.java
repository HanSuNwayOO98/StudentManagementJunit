package com.sms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.User;



@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public List<User> selectAllUser(){
		List<User> list= (List<User>) userRepo.findAll();
		return list;
	}
	
	public Optional<User> getUserbyId(String id) {
		return userRepo.findById(id);
		
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public void delete(String id) {
		userRepo.deleteById(id);
	}
	
	public void update(User user,String id) {
		userRepo.save(user);
	}
	
	public List<User> searchUser(String id, String name){
		List<User> list=userRepo.searchUser(id, name);
		return list;
	}
	
	
}