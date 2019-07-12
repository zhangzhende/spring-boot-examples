package com.zzd.spring.demoone.dao;

import com.zzd.spring.demoone.entity.Test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by lorne on 2017/6/28.
 */
@Mapper
public interface TestMapper {


    @Select("SELECT * FROM T_TEST")
    List<Test> findAll();

    @Insert("INSERT INTO T_TEST(NAME,CREATED) VALUES(#{name},#{created})")
    int save(@Param("name") String name, @Param("created") Date created);

}
