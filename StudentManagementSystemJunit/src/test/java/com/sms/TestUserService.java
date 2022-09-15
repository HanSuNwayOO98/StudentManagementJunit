package com.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.sms.dao.UserRepository;
import com.sms.dao.UserService;
import com.sms.dto.User;



@SpringBootTest
public class TestUserService {
	@Mock
	UserRepository repo;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void selectAllUserTest() {
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setId("USR001");
		u1.setEmail("a@gmail.com");
		u1.setName("Han Su Nway Oo");
		u1.setPassword("123");
		u1.setRole("1");
		
		User u2=new User();
		u2.setId("USR002");
		u2.setEmail("nway@gmail.com");
		u2.setName("Jisoo");
		u2.setPassword("123");
		u2.setRole("1");
		
		User u3=new User();
		u3.setId("USR003");
		u3.setEmail("a@gmail.com");
		u3.setName("Rose");
		u3.setPassword("123");
		u3.setRole("1");
		
		list.add(u1);
		list.add(u2);
		list.add(u3);
		
		when(repo.findAll()).thenReturn(list);
		List<User> bookList=userService.selectAllUser();
		assertEquals(3,bookList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getUserbyIdTest() {
		User setUser=new User();
		setUser.setId("USR001");
		setUser.setEmail("a@gmail.com");
		setUser.setName("Han Su Nway Oo");
		setUser.setPassword("123");
		setUser.setRole("1");
		
		when(repo.findById("USR001")).thenReturn(Optional.ofNullable(setUser));
		Optional<User> getUser=userService.getUserbyId("USR001");
		assertEquals("a@gmail.com",getUser.get().getEmail());
		assertEquals("Han Su Nway Oo",getUser.get().getName());
		assertEquals("123",getUser.get().getPassword());
		assertEquals("1",getUser.get().getRole());
	}
	

}
