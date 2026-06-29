package com.redisPrac.Repositiry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redisPrac.Repositiry.StudentRepository;
import com.redisPrac.entity.Student;

@RestController
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@PostMapping("/save")
	public void saveStudent(@RequestBody Student s) {
		int saved = studentRepository.save(s);
		System.out.println("No.of rows effected : " + saved);
	}
}
