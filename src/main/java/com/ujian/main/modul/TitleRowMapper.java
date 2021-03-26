package com.ujian.main.modul;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitleRowMapper implements RowMapper<Title> {

	@Override
	public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Title title = new Title();
		title.setWorker_ref_id(rs.getInt("worker_ref_id"));
		title.setWorker_title(rs.getString("worker_title"));
		title.setAffected_from(rs.getString("affected_from"));
		// TODO Auto-generated method stub
		return title;
	}

		
		
		
}
