package com.losung.assignment.repository;

import java.util.List;

import com.losung.assignment.models.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class GenericRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Contact> search(String key, List<String> columns, String entity) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ".concat(entity).concat(" entity WHERE"));
		for(String column: columns){
			sb.append(" entity.");
			sb.append(column);
			sb.append(" LIKE ");
			sb.append("\"%".concat(key).concat("%\""));
			sb.append(" OR");
		}
		sb.delete(sb.length()-3, sb.length());
		sb.append(";");

		String query = sb.toString();
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Contact>(Contact.class));
	}

}
