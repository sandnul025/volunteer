package com.bankledger.service;

import com.bankledger.entity.User;
import com.bankledger.utils.ResultHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午11:02:55
 */

public interface IJoinProjectService {

	/**
	 * @param projectId
	 * @param blockAddr
	 * @return
	 */
	ResultHelper join(String projectId, String blockAddr);

	/**
	 * @param projectId
	 * @param blockAddr
	 * @return
	 */
	ResultHelper toSign(String projectId, User user);

}
