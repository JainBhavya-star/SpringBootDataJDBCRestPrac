package com.redisPrac.Repositiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.redisPrac.entity.Student;

@Repository
public class StudentRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int save(Student s) {
		String sql = "insert into student values(?,?,?)";
		return jdbcTemplate.update(sql, s.getId(), s.getName(), s.getCity());
	}
}
