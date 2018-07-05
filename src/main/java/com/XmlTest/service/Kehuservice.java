package com.XmlTest.service;

import java.util.List;

import com.XmlTest.bean.Aehu;

public interface Kehuservice {
 public List<Aehu> SelectKehuAll(Integer limit,Integer offset,String Name);
 public Integer selectKehuBycount();
}
