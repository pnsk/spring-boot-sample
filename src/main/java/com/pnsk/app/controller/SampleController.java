package com.pnsk.app.controller;

import com.pnsk.app.data.dao.SampleDao;
import com.pnsk.app.data.entity.Sample;
import com.pnsk.app.logger.SampleLogger;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 *
 * Created by okuda_junko on 14/09/16.
 */
@RestController
public class SampleController {

    private static SampleLogger LOG = new SampleLogger();

	@Autowired
	SampleDao sampleDao;

	@RequestMapping(value = {"/sample"}, method= {RequestMethod.GET})
	public List<Map<String, Object>> execute() throws Exception {

		List<Sample> list = sampleDao.getSampleList(10, 0);

		List<Map<String, Object>> view = new ArrayList<>();

		for (Sample sample : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", sample.getId());
			map.put("name", sample.getName());
			map.put("status", sample.getStatus());
			map.put("create_at", sample.getCreateDatetime());
			map.put("update_at", sample.getUpdateDatetime());
			view.add(map);
		}
		return view;
	}


	@RequestMapping(value={"/sample/{name}"},method= {RequestMethod.POST})
	public Map<String, Object> post(@PathVariable String name) throws Exception {

		Map<String, Object> view = new HashMap<>();

		if (sampleDao.createSample(name)) {
			view.put("status", HttpStatus.SC_CREATED);
		} else {
			view.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}

		return view;
	}





}
