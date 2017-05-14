package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.DeliveryExecutive;

public interface DeliveryExecDAO {
	
	List<DeliveryExecutive> getDelivExecutivesHavingBeatsAssigned(int resellerID);
	
	List<DeliveryExecutive> getDeliveryExecutives(int resellerID);
	
	void assignBeats(int salesExecID, List<Integer> beatIDs) throws Exception;

}
