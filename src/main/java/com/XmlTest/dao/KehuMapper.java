package com.XmlTest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.XmlTest.bean.Aehu;

@Mapper
public interface KehuMapper {
 List<Aehu> SelectkehuAll(@Param("limit")Integer limit,@Param("offset")Integer offset,@Param("Name")String Name);
 Integer KehuBycount();
}
