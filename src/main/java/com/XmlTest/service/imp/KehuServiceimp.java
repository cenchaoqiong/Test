package com.XmlTest.service.imp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.XmlTest.bean.Aehu;
import com.XmlTest.dao.KehuMapper;
import com.XmlTest.service.Kehuservice;

@Service
public class KehuServiceimp implements Kehuservice {

	@Autowired
	private KehuMapper kh;

	@Override
	public List<Aehu> SelectKehuAll(Integer limit,Integer offset,String Name) {

		return kh.SelectkehuAll(limit,offset,Name);
	}

	@Override
	public Integer selectKehuBycount() {
		
		return kh.KehuBycount();
	}

}
