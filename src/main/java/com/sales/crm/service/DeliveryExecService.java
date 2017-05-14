package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.DeliveryExecDAO;
import com.sales.crm.model.DeliveryExecutive;

@Service("deliveryService")
public class DeliveryExecService {
	
	@Autowired
	DeliveryExecDAO deliveryDAO;
	
	public List<DeliveryExecutive> getDelivExecutivesHavingBeatsAssigned(int resellerID){
		return deliveryDAO.getDelivExecutivesHavingBeatsAssigned(resellerID);
	}

	public List<DeliveryExecutive> getDeliveryExecutives(int resellerID){
		return deliveryDAO.getDeliveryExecutives(resellerID);
	}
	
	public void assignBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception{
		deliveryDAO.assignBeats(salesExecID, beatIDs);
	}
}
