package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.DeliveryBookingSchedule;
import com.sales.crm.model.DeliveryExecutive;

public interface DeliveryExecDAO {
	
	List<DeliveryExecutive> getDelivExecutivesHavingBeatsAssigned(int resellerID);
	
	List<DeliveryExecutive> getDeliveryExecutives(int resellerID);
	
	void assignBeats(int salesExecID, List<Integer> beatIDs) throws Exception;
	
	DeliveryExecutive getDelivExecutive(int delivExecID);
	
	void updateAssignedBeats( int salesExecID,  List<Integer> beatIDs) throws Exception;
	
	void deleteBeatAssignment(int delivExecID) throws Exception;
	
	List<DeliveryExecutive> getDeliveryExecutivesScheduled(int resellerID);
	
	List<Beat> getAssignedBeats(int delivExecID);
	
	List<Beat> getScheduledVisitDelivExecBeats(int delivExecID, Date visitDate);
	
	List<DeliveryExecutive> getScheduledVisitDelivExecs(Date visitDate, int resellerID);
	
	List<CustomerOrder> getScheduledCustomersOrdersForDelivery(int delivExecID, Date visitDate, int beatID);
	
	List<String> alreadyDeliveryBookingScheduledCustomer(DeliveryBookingSchedule deliveryBookingSchedule) throws Exception;
	
	void scheduleDeliveryBooking(DeliveryBookingSchedule deliveryBookingSchedule) throws Exception;
	
	void unscheduleDeliveryBooking(List<Integer> customerIDs, Date visitDate) throws Exception;
	
	int getDeliveryExecutiveCount(int resellerID);
}
