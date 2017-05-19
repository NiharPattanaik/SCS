package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.DeliveryExecDAO;
import com.sales.crm.model.Beat;
import com.sales.crm.model.DeliveryBookingSchedule;
import com.sales.crm.model.DeliveryExecutive;
import com.sales.crm.model.TrimmedCustomer;

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
	
	public DeliveryExecutive getDelivExecutive(int delivExecID){
		return deliveryDAO.getDelivExecutive(delivExecID);
	}
	
	public void updateAssignedBeats(int delivExecID, List<Integer> beatIDs) throws Exception{
		deliveryDAO.updateAssignedBeats(delivExecID, beatIDs);
	}
	
	public void deleteBeatAssignment(int delivExecID) throws Exception{
		deliveryDAO.deleteBeatAssignment(delivExecID);
	}
	
	public List<DeliveryExecutive> getDeliveryExecutivesScheduled(int resellerID){
		return deliveryDAO.getDeliveryExecutivesScheduled(resellerID);
	}
	
	public List<Beat> getAssignedBeats(int delivExecID){
		return deliveryDAO.getAssignedBeats(delivExecID);
	}
	
	public List<Beat> getScheduledVisitDelivExecBeats(int delivExecID, Date visitDate){
		return deliveryDAO.getScheduledVisitDelivExecBeats(delivExecID, visitDate);
	}
	
	public List<DeliveryExecutive> getScheduledVisitDelivExecs(Date visitDate, int resellerID){
		return deliveryDAO.getScheduledVisitDelivExecs(visitDate, resellerID);
	}
	
	public List<TrimmedCustomer> getScheduledCustomersForDelivery(int delivExecID, Date visitDate, int beatID){
		return deliveryDAO.getScheduledCustomersForDelivery(delivExecID, visitDate, beatID);
	}
	
	public List<String> alreadyDeliveryBookingScheduledCustomer(DeliveryBookingSchedule deliveryBookingSchedule) throws Exception{
		return deliveryDAO.alreadyDeliveryBookingScheduledCustomer(deliveryBookingSchedule);
	}
	
	public void scheduleDeliveryBooking(DeliveryBookingSchedule deliveryBookingSchedule) throws Exception{
		deliveryDAO.scheduleDeliveryBooking(deliveryBookingSchedule);
	}
	
}
