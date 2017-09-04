package com.framework.mapping.excel;

import java.util.Date;

public class beanEntity {

	private String line;
	private String station_name;
	private String route;
	private String node;
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String stationName) {
		station_name = stationName;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getStation_address() {
		return station_address;
	}
	public void setStation_address(String stationAddress) {
		station_address = stationAddress;
	}
	public String getZcode() {
		return zcode;
	}
	public void setZcode(String zcode) {
		this.zcode = zcode;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getLicheng() {
		return licheng;
	}
	public void setLicheng(Integer licheng) {
		this.licheng = licheng;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	private String station_address;
	private String zcode;
	private Date time;
	private Integer licheng;
	private String level;
	private String condition;
	
}
