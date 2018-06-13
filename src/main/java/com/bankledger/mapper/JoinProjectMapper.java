package com.bankledger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bankledger.entity.Project;
import com.bankledger.entity.User;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午11:05:53
 */

@Mapper
public interface JoinProjectMapper {

	@Insert(" insert into join_project( projectid, blockAddr, created,iscome) values (#{projectId}, #{blockAddr}, #{created},#{isCome})")
	int join(@Param("projectId")String projectId, @Param("blockAddr")String blockAddr,@Param("created")String created,@Param("isCome")String isCome);

	@Select(" select count(0) from join_project where projectid = #{projectId} and blockaddr=#{blockAddr}")
	int isExist(@Param("projectId")String projectId, @Param("blockAddr")String blockAddr);

	@Select(" select "
			+ "p.projectid projectId,p.projecttype projectType,p.projectname projectName,p.address, "
			+ "p.organize,p.starttime startTime,p.endtime endTime, "
			+ "p.limitcount limitCount ,p.coin,p.limitorganizeflag limitOrganizeFlag, "
			+ "p.discript,p.status,j.iscome isCome "
			+ "from join_project j "
			+ "left join project p on j.projectid = p.projectid "
			+ "where j.blockaddr = #{blockAddr} "
			+ "order by j.created ")
	List<Project> getListProject(String blockAddr);

	@Select(" select u.blockaddr blockAddr, u.idcard idCard, j.iscome isCome "
			+ "from join_project j "
			+ "left join user u on j.blockaddr = u.blockaddr "
			+ "where j.projectid = #{projectId}")
	List<User> getListPerson(String projectId);

	
	@Update(" update join_project set iscome = #{isCome} ,txid = #{txId} where projectid = #{projectId} and blockaddr=#{blockAddr} ")
	int toSign(@Param("projectId")String projectId, @Param("blockAddr")String blockAddr, @Param("isCome")String isCome, @Param("txId")String txId);

}
