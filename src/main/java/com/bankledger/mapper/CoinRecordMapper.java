package com.bankledger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bankledger.entity.CoinRecord;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 下午3:13:13
 */
@Mapper
public interface CoinRecordMapper {

	
	@Select(" <script>  select "
			+ "blockaddr_from blockAddrFrom, "
			+ "blockaddr_to blockaddrTo, "
			+ "coinnum coinNum, "
			+ "projectid projectId,"
			+ "projectname projectName,"
			+ "organize, "
			+ "address,created,txId "
			+ "from coin_record "
			+ "where 1=1 "
			+ "<if test = 'blockAddrFrom != null'>"
			+ " and blockaddr_from = #{blockAddrFrom}"
			+ "</if>"
			+ "<if test = 'blockAddrTo != null'>"
			+ " and blockaddr_to = #{blockAddrTo}"
			+ "</if>"
			+ "<if test = 'projectId != null and projectId != \"\" '>"
			+ " and projectid = #{projectId}"
			+ "</if>"
			+ "</script>")
	List<CoinRecord> getList(CoinRecord record);

	@Insert(" insert into coin_record(blockaddr_from, blockaddr_to,coinnum,projectid,projectname,organize,address,created,txid) "
			+ "values "
			+ "(#{blockAddrFrom},#{blockAddrTo},#{coinNum},#{projectId},#{projectName},#{organize},#{address},#{created},#{txId})")
	int insert(CoinRecord record);
}
