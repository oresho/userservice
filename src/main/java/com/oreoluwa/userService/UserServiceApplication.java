package com.oreoluwa.userService;

import com.oreoluwa.userService.domain.AppUser;
import com.oreoluwa.userService.domain.Role;
import com.oreoluwa.userService.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {
			userService.saveRole(new Role(null, "role_user"));
			userService.saveRole(new Role(null, "role_manager"));
			userService.saveRole(new Role(null, "role_admin"));
			userService.saveRole(new Role(null, "role_super_admin"));

			userService.saveUser(new AppUser(null,"abc","a","123",new ArrayList<>()));
			userService.saveUser(new AppUser(null,"def","d","123",new ArrayList<>()));
			userService.saveUser(new AppUser(null,"ghi","g","123",new ArrayList<>()));
			userService.saveUser(new AppUser(null,"jkl","j","123",new ArrayList<>()));

			userService.addRoleToUser("a","role_user");
			userService.addRoleToUser("a","role_manager");
			userService.addRoleToUser("d","role_manager");
			userService.addRoleToUser("g","role_admin");
			userService.addRoleToUser("j","role_super_admin");
			userService.addRoleToUser("j","role_admin");
			userService.addRoleToUser("j","role_user");

		};
	}

}
