package com.bankledger.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bankledger.entity.User;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月4日 下午10:21:48
 */
@Mapper
public interface UserMapper {

	@Select(" select count(0) from user where idcard = #{idCard}")
	int isIdCard(String idCard);

	@Insert(" insert into user(blockaddr, idcard,nickname , password, type,coincount,appid,txid) values(#{blockAddr}, #{idCard},#{nickName}, #{password}, #{type},#{coinCount},#{appId},#{txId})")
	int register(User user);

	@Select(" select blockaddr blockAddr, idcard idCard,nickname ,  password,type,coincount coinCount,sex,appid appId,skills,descript from user where type = #{type} and idcard = #{idCard} and password = #{password} ")
	User login(User user);

	
	
	/**
	 * 更新 用户钱包
	 * @param blockAddr
	 * @param coin
	 * @return
	 */
	@Update(" update user set coincount = coincount + #{coin} where blockaddr = #{blockAddr}" )
	int updateCoinAdd(@Param("blockAddr")String blockAddr, @Param("coin")String coin);

	/**
	 *更新 管理员钱包
	 * @param blockAddr
	 * @param coin
	 * @return
	 */
	@Update(" update user set coincount = coincount - #{coin} where blockaddr = #{blockAddr}" )
	int updateCoinSubtract(@Param("blockAddr")String blockAddr, @Param("coin")String coin);

	@Select(" <script>"
			+ " update user set blockaddr = #{blockAddr} "
			+ "<if test = ' null != nickName and \"\" != nickName '>"
			+ " , nickname = #{nickName} "
			+ "</if>"
			+ "<if test = ' null != descript and \"\" != descript '>"
			+ " , descript = #{descript} "
			+ "</if>"
			+ "<if test = ' null != skills and \"\" != skills '>"
			+ " , skills = #{skills} "
			+ "</if>"
			+ "<if test=' null != sex and \"\" != sex'>"
			+ ", sex = #{sex} "
			+ "</if>"
			+ "<if test=' null != country and \"\" != country'>"
			+ ", country = #{country} "
			+ "</if>"
			+ "<if test=' null != nation and \"\" != nation'>"
			+ ", nation = #{nation} "
			+ "</if>"
			+ "<if test=' null != major and \"\" != major'>"
			+ ", major = #{major} "
			+ "</if>"
			+ "<if test=' null != school and \"\" != school'>"
			+ ", school = #{school} "
			+ "</if>"
			+ "<if test=' null != censusregister and \"\" != censusregister'>"
			+ ", censusregister = #{censusregister} "
			+ "</if>"
			+ "<if test=' null != politicallandscape and \"\" != politicallandscape'>"
			+ ", politicallandscape = #{politicallandscape} "
			+ "</if>"
			+ "<if test=' null != birthdate and \"\" != birthdate'>"
			+ ", birthdate = #{birthdate} "
			+ "</if>"
			+ "where blockaddr = #{blockAddr}" 
			+ "</script> ")
	User changeUser(User userVo);
	
	@Select("select blockaddr blockAddr, idcard idCard,nickname ,"
			+ "  password,type,coincount coinCount,sex,appid appId,"
			+ "skills,descript,country,"
			+ "nation,major,school,censusregister,politicallandscape,birthdate,isopen "
			+ "from user where idcard = #{idCard}")
	User getUserInfo(String idCard);

	@Update(" update user set isopen  = #{isopen} where idcard = #{idCard}")
	int open(@Param("idCard") String idCard, @Param("isopen") String isopen);
}
