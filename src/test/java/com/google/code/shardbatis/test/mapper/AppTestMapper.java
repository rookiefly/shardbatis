package com.google.code.shardbatis.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.google.code.shardbatis.test.domain.AppTestDO;

public interface AppTestMapper {
	Integer insert(AppTestDO domain);

	@Insert("insert into app_test (ID,CNT )values (#{id},#{cnt})")
	@SelectKey(statement = "select seq_app_test_id.nextval from dual", 
			keyProperty = "id", before = true, resultType = int.class)
	Integer insert2(AppTestDO domain);
	
//	@Insert("insert into app_test (ID,CNT )values (#{id},#{cnt})")
//	@SelectKey(statement = "SELECT @@IDENTITY AS ID", 
//			keyProperty = "id", before = false, resultType = int.class)
//	Integer insert3(AppTestDO domain);
	
	@Insert("insert into app_test (ID,CNT )values (#{id},#{cnt})")
	@SelectKey(statement = "select seq_app_test_id.nextval from dual", 
			keyProperty = "id", before = true, resultType = int.class)
	Integer insertNoShard(AppTestDO domain);
	
	List<AppTestDO> getList(AppTestDO domain);
}
