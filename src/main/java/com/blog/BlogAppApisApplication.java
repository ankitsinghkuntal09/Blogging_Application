package com.blog;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@EnableAsync //it will enable async on API level.
//To enable async support in our Spring Boot application,
//we need to add the @EnableAsync annotation to our BlogAppApisApplication class.
//This annotation will tell Spring to enable async support and create a pool of threads to handle async requests.
// After adding the @EnableAsync annotation, Spring will create a pool of threads to handle async requests.
//By default, Spring will create a pool of size 8 threads.
//This pool can be configured using the spring.task.execution.pool.core-size property.

@EnableTransactionManagement //For using transaction management, we need to use @EnableTransactionManagement
// in the main class of our spring boot application and then we need to use @Transactional in the Service method..
//https://www.geeksforgeeks.org/spring-boot-transaction-management-using-transactional-annotation/
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	//@bean : it will create an object that can be used later.
	//we can create this ModelMapper bean in new Class file with annotation of @Config.
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	//this run method comes after implementing CommandLineRunner
	//this run method will help us in inserting roles in role table since role will be limited only.
	@Override
	public void run(String... args) throws Exception {
		try{
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_USER");

			roleRepo.saveAll(List.of(role,role1));
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
