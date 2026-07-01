package com.redisPrac.Repositiry.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redisPrac.Repositiry.StudentRepository;
import com.redisPrac.entity.Student;

@RestController
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping("/save")
	public ResponseEntity<String> saveStudent(@RequestBody Student s) {
		try {
			int saved = studentRepository.save(s);
			return new ResponseEntity<>("Student added successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to add student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student s) {
		// ensure the student object has the id from the path variable
		s.setId(id);
		int studentUpdated = studentRepository.updateStudent(s);
		if (studentUpdated > 0) {
			return new ResponseEntity<>("Student Updated", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Student Updation failed", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Student> findById(@PathVariable("id") int id) {
		try {
			Student student = studentRepository.findById(id);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<Student>> findAll() {
		try {
			List<Student> allStudents = studentRepository.findAll();
			return ResponseEntity.status(200).body(allStudents);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id) {
		try {
			Student byId = studentRepository.findById(id);
			if (byId != null) {
				studentRepository.deleteStudent(id);
				return new ResponseEntity<>("Student deleted successfully.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/saveAll")
	public ResponseEntity<String> saveStudents(@RequestParam MultipartFile file) throws IOException {
		try {
			studentRepository.saveAll(file);
			return new ResponseEntity<>("Students added successfully.", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to add students: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
