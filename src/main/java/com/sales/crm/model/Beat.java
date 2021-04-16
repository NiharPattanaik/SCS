package com.sales.crm.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BEAT")
@AttributeOverrides({
	@AttributeOverride(name = "statusID", column = @Column(name = "STATUS_ID")),
	@AttributeOverride(name = "tenantID", column = @Column(name = "TENANT_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Beat extends BusinessEntity {

	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int beatID;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "COVERAGE_SCHEDULE")
	private String coverageSchedule;
	
	@Column(name = "DISTANCE")
	private int distance;

	
	@Transient
	private List<Area> areas;
	
	@Transient
	private List<TrimmedCustomer> customers;
	
	@Transient
	private List<Integer> customerIDs;
	
	@Transient
	private List<Integer> areaIDs;
	
	@Transient
	private int manufacturerID;
	
	@Transient
	private List<Manufacturer> manufacturers;
	
	//HACK FOR UI, PLEASE DON'T USE
	@Transient
	private String manufacturerIDStr;
	
	public int getBeatID() {
		return beatID;
	}

	public void setBeatID(int beatID) {
		this.beatID = beatID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getCoverageSchedule() {
		return coverageSchedule;
	}

	public void setCoverageSchedule(String coverageSchedule) {
		this.coverageSchedule = coverageSchedule;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	
	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<TrimmedCustomer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<TrimmedCustomer> customers) {
		this.customers = customers;
	}

	public List<Integer> getCustomerIDs() {
		return customerIDs;
	}

	public void setCustomerIDs(List<Integer> customerIDs) {
		this.customerIDs = customerIDs;
	}

	public List<Integer> getAreaIDs() {
		return areaIDs;
	}

	public void setAreaIDs(List<Integer> areaIDs) {
		this.areaIDs = areaIDs;
	}
	
	public int getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public String getManufacturerIDStr() {
		return manufacturerIDStr;
	}

	public void setManufacturerIDStr(String manufacturerIDStr) {
		this.manufacturerIDStr = manufacturerIDStr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaIDs == null) ? 0 : areaIDs.hashCode());
		result = prime * result + ((areas == null) ? 0 : areas.hashCode());
		result = prime * result + beatID;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((coverageSchedule == null) ? 0 : coverageSchedule.hashCode());
		result = prime * result + ((customerIDs == null) ? 0 : customerIDs.hashCode());
		result = prime * result + ((customers == null) ? 0 : customers.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + distance;
		result = prime * result + manufacturerID;
		result = prime * result + ((manufacturerIDStr == null) ? 0 : manufacturerIDStr.hashCode());
		result = prime * result + ((manufacturers == null) ? 0 : manufacturers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beat other = (Beat) obj;
		if (areaIDs == null) {
			if (other.areaIDs != null)
				return false;
		} else if (!areaIDs.equals(other.areaIDs))
			return false;
		if (areas == null) {
			if (other.areas != null)
				return false;
		} else if (!areas.equals(other.areas))
			return false;
		if (beatID != other.beatID)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (coverageSchedule == null) {
			if (other.coverageSchedule != null)
				return false;
		} else if (!coverageSchedule.equals(other.coverageSchedule))
			return false;
		if (customerIDs == null) {
			if (other.customerIDs != null)
				return false;
		} else if (!customerIDs.equals(other.customerIDs))
			return false;
		if (customers == null) {
			if (other.customers != null)
				return false;
		} else if (!customers.equals(other.customers))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (distance != other.distance)
			return false;
		if (manufacturerID != other.manufacturerID)
			return false;
		if (manufacturerIDStr == null) {
			if (other.manufacturerIDStr != null)
				return false;
		} else if (!manufacturerIDStr.equals(other.manufacturerIDStr))
			return false;
		if (manufacturers == null) {
			if (other.manufacturers != null)
				return false;
		} else if (!manufacturers.equals(other.manufacturers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Beat [beatID=" + beatID + ", code=" + code + ", name=" + name + ", description=" + description
				+ ", coverageSchedule=" + coverageSchedule + ", distance=" + distance + ", areas=" + areas
				+ ", customers=" + customers + ", customerIDs=" + customerIDs + ", areaIDs=" + areaIDs
				+ ", manufacturerID=" + manufacturerID + ", manufacturers=" + manufacturers + ", manufacturerIDStr="
				+ manufacturerIDStr + ", tenantID=" + tenantID + ", dateCreated=" + dateCreated + ", dateModified="
				+ dateModified + ", statusID=" + statusID + "]";
	}

	
	
}
