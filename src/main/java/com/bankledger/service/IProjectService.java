package com.bankledger.service;

import com.bankledger.entity.Project;
import com.bankledger.utils.ResultHelper;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2018年6月5日 上午9:12:47
 */
public interface IProjectService {

	ResultHelper add(Project project);

	/**
	 * @param projectId
	 * @param blockAddr
	 * @return
	 */
	ResultHelper submitAuth(String projectId, String blockAddr);

	/**
	 * @param projectId
	 * @param blockAddr
	 * @return
	 */
	ResultHelper passAuth(String projectId);

	/**
	 * @param projectId
	 * @param blockAddr
	 * @return
	 */
	ResultHelper offProject(String projectId, String blockAddr);
	
}
