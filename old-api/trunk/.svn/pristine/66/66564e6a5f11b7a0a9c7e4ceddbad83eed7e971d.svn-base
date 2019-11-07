package appcloud.api.manager;

import java.sql.Timestamp;
import java.util.List;

import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.enums.AcStrategyTypeEnum;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;

public interface AcResourceStrategyManager {

	public List<AcResourceStrategy> getList(String adminId) throws Exception;

	public AcResourceStrategy get(String adminId, Integer resourceStrategyId)
			throws Exception;

	public AcResourceStrategy create(String adminId,
			AcResourceStrategy acResourceStrategy) throws Exception;

	public AcResourceStrategy update(String adminId,
			Integer resourceStrategyId, AcResourceStrategy acResourceStrategy)
			throws Exception;

	public void delete(String adminId, Integer resourceStrategyId)
			throws Exception;

	public List<AcResourceStrategy> searchByProperties(String adminId,
			Integer id, String uuid, StrategyTypeEnum type, String name,
			String description, String clazzs, String params, Timestamp time,
			Integer page, Integer size) throws Exception;

	public String countByProperties(String adminId, Integer id, String uuid,
			AcStrategyTypeEnum type, String name, String description,
			String clazzs, String params, Timestamp time) throws Exception;
}
