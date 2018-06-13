package com.bankledger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bankledger.entity.Project;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午9:16:02
 */

@Mapper
public interface ProjectMapper {

	@Insert(" insert into project("
			+ "projectid ,projecttype ,projectname ,address,"
			+ "organize,starttime ,endtime ,"
			+ "limitcount ,coin,limitorganizeflag ,"
			+ "discript,status,blockaddr,created,txId"
			+ ")values("
			+ "#{projectId},#{projectType},#{projectName},#{address},"
			+ "#{organize},#{startTime},#{endTime},"
			+ "#{limitCount},#{coin},#{limitOrganizeFlag},"
			+ "#{discript},#{status},#{blockAddr},#{created},#{txId}"
			+ ") ")
	int add(Project project);

	@Select(" select "
			+ "projectid projectId,projecttype projectType,projectname projectName,address,"
			+ "organize,starttime startTime,endtime endTime,"
			+ "limitcount limitCount,coin,limitorganizeflag limitOrganizeFlag,"
			+ "discript,status,created "
			+ "from project where projectid = #{projectId} and blockaddr = #{blockAddr}")
	Project findOne(@Param("projectId")String projectId, @Param("blockAddr")String blockAddr);

	@Update(" update project set status = #{status} where projectid = #{projectId} and blockaddr = #{blockAddr}")
	int updateStatus(@Param("projectId")String projectId, @Param("blockAddr")String blockAddr, @Param("status")String status);

	
	@Select("<script>  SELECT  p.projectid projectId,p.projecttype projectType,p.projectname projectName,p.address, "
			+ "p.organize,p.starttime startTime,p.endtime endTime,"
			+ "p.limitcount limitCount,p.coin,p.limitorganizeflag limitOrganizeFlag,"
			+ "p.discript,p.status,p.created ,IFNULL(j.iscome,'') isCome "
			+ "FROM project p "
			+ "LEFT JOIN join_project j ON p.projectid = j.projectid "
			+ " WHERE 1=1 "
			+ "<if test='null != blockAddr'>"
			+ " and p.blockaddr = #{blockAddr} "
			+ "</if>"
			+ "<if test='null != status'>"
			+ " and p.status = #{status}"
			+ "</if></script>")
	List<Project> getList(Project project);
	
	
	/* ----------------------------------------------------- 管理员操作 --------------------------------------------------------------*/
	@Select(" select "
			+ "projectid projectId,projecttype projectType,projectname projectName,address,"
			+ "organize,starttime startTime,endtime endTime,"
			+ "limitcount limitCount,coin,limitorganizeflag limitOrganizeFlag,"
			+ "discript,status,created  "
			+ "from project where projectid = #{projectId}")
	Project getProjectByProjectId(String projectId);

	
	@Update(" update project set status = #{status} where projectid = #{projectId}")
	int updateStatusByProjectId(@Param("projectId")String projectId, @Param("status")String status);

	/**
	 * @return
	 */
	@Select(" select count(0) from project where status= '1'")
	int authProjectCount();

	/**
	 * @return
	 */
	@Select(" select count(0)"
			+ "from join_project j "
			+ "left join project p on j.projectid = p.projectid "
			+ "where j.blockaddr = #{blockAddr} and p.status = '2' and j.iscome = '0' "
			+ "order by j.created ")
	int toSignProjectCount(String blockAddr);

	
}
