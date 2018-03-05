package com.example.weixin;

import com.example.weixin.dao.UserMapper;
import com.example.weixin.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {
	@Autowired
	private UserMapper userMapper;
	@Test
	public void contextLoads() {
	}
	@Test
	public void testAdd(){
		User user = new User();
		user.setAge(18);
		user.setName("czp");
		System.out.println(userMapper.insert(user));
	}
	@Test
	public void query(){
		userMapper.selectByPrimaryKey(1);
	}
}
