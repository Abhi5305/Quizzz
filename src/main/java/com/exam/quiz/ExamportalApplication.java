package com.exam.quiz;


import com.exam.quiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ExamportalApplication implements CommandLineRunner {
	@Autowired
    private UserServiceImpl userService;
	public static void main(String[] args) {
		SpringApplication.run(ExamportalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		List<User> users = Arrays.asList(
//				new User(1L,"abhinabk","Abhi@987","Abhinab","Kumar","abhinab@tcs.com","abhi.jpg",false),
//				new User(2L,"anandk","Anan@786","Anand","Kumar","anand@inosys.com","anand.jpg",true),
//				new User(3L,"kritikak","kritika@5305","Kritika","Kumari","kritika@amazon.com","kritika.jpg",true)
//
//		);
//		List<Role> roles = Arrays.asList(
//				new Role(1L,"Admin"),
//				new Role(2L,"Teacher"),
//				new Role(3L,"Student")
//		);
//		Set<UserRole> userRoleSet = new HashSet<>();
//		UserRole userRole = new UserRole();
//		userRole.setRole(roles.get(0));
//		userRole.setUser(users.get(0));
//		userRoleSet.add(userRole);
//
//		User user1 = this.userService.createUser(users.get(0),userRoleSet);
//		System.out.println(user1.getUserName());
	}
}
