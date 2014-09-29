package com.pnsk.app.data.dao;

import com.pnsk.app.config.DatabaseConfiguration;
import com.pnsk.app.data.entity.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by okuda_junko on 14/09/22.
 */
@Component
public class SampleDao {

    @Autowired
    private DatabaseConfiguration sampleDatabase;
    private JdbcTemplate jdbcTemplate;

	public List<Sample> getSampleList(Integer limit, Integer offset) throws SQLException {
		jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(sampleDatabase.getSlaveDataSource());

		List<Sample> list = this.jdbcTemplate.query("select * from sample limit ? offset ?", new RowMapper<Sample>() {
			@Override
			public Sample mapRow(ResultSet resultSet, int i) throws SQLException {
				Sample s = new Sample();
				s.setId(resultSet.getLong("id"));
				s.setName(resultSet.getString("name"));
				s.setStatus(resultSet.getInt("status"));
				s.setCreateDatetime(resultSet.getDate("create_at"));
				s.setUpdateDatetime(resultSet.getDate("update_at"));
				return s;
			}
		}, limit, offset);

		return list;
	}

	public boolean createSample(String name) throws SQLException {
		jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(sampleDatabase.getMasterDataSource());

		Date now = new Date();
		int ret = jdbcTemplate.update("INSERT INTO sample (name, create_at, update_at, status) VALUES (?, ?, ?, ?)", name, now, now, 0);
		return ret == 1;
	}

}
