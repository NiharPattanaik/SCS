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
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Beat extends BusinessEntity {

private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int beatID;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "AREA_ID")
	private int areaID;
	
	@Column(name = "COVERAGE_SCHEDULE")
	private int coverageSchedule;
	
	@Column(name = "DISTANCE")
	private int distance;

	@Column(name = "RESELLER_ID")
	private long resellerID;
	
	@Transient
	private List<Area> areas;
	
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

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public int getCoverageSchedule() {
		return coverageSchedule;
	}

	public void setCoverageSchedule(int coverageSchedule) {
		this.coverageSchedule = coverageSchedule;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public long getResellerID() {
		return resellerID;
	}

	public void setResellerID(long resellerID) {
		this.resellerID = resellerID;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	
}
