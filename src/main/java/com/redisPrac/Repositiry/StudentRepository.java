package com.redisPrac.Repositiry;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.redisPrac.entity.Student;
import com.redisPrac.utils.ExcelHelper;

@Repository
public class StudentRepository {
	
	ExcelHelper e = new ExcelHelper();

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int save(Student s) {
		// specify columns explicitly to avoid dependency on table column order
		String sql = "insert into student(id, name, city) values(?,?,?)";
		return jdbcTemplate.update(sql, s.getId(), s.getName(), s.getCity());
	}
	
	public int updateStudent(Student s) {
		String sql = "update student set name = ?, city = ? where id = ?";
		// parameter order must match the placeholders: name, city, id
		return jdbcTemplate.update(sql, s.getName(), s.getCity(), s.getId());
	}
	
	public Student findById(int id) {
		String sql="select * from student where id=?";
		return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
	}
	
	public List<Student> findAll(){
		String sql = "select * from student";
		return jdbcTemplate.query(sql, new StudentRowMapper());
	}
	
	public void deleteStudent(int id) {
		String sql = "delete from student where id=?";
		jdbcTemplate.update(sql,id);
	}
	
	public void saveAll(MultipartFile file) throws IOException {
		List<Student> students = ExcelHelper.excelToProducts(file.getInputStream());
		String sql = "insert into student(id, name, city) values(?,?,?)";
		for (Student s : students) {
			jdbcTemplate.update(sql, s.getId(), s.getName(), s.getCity());
		}
	}
	
}
