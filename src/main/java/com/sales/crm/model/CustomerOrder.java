package com.sales.crm.model;

import java.util.List;

public class CustomerOrder {
	
	private TrimmedCustomer customer;
	
	private List<Order> orders;

	public TrimmedCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TrimmedCustomer customer) {
		this.customer = customer;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "CustomerOrder [customer=" + customer + ", orders=" + orders + "]";
	}
	
	

}
