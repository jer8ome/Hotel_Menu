package com.jeromet.hotel_menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * @author  作者: Jerome
 * @date 创建时间：2019年1月3日 下午8:22:23 
 * @version 1.0 
 * @description:  
 */

public class Customer {
	private String name;
	private ArrayList<String> dateList;
	private ArrayList<Map<String, Integer>> orderList;
	private Map<String, Double> dayMoneyMap;
	private double totalExpenditure;
	
	protected Customer() {
		name = null;
		dateList = new ArrayList<>();
		orderList = new ArrayList<>();
		dayMoneyMap = new HashMap<>();
		totalExpenditure = 0.0;
	}
	
	
	
	protected void count() {
		if(name!=null&&dateList!=null&&orderList!=null) {
			int i = 0;
			for(Map<String, Integer> order: orderList) {
				Double count = 0.0;
				Double money = 0.0;
				for(Entry<String, Integer> en:order.entrySet()) {
					money=Menu.menu.get(en.getKey());
					if(money==null) {
						money = 10.0;
					}
					count += money*en.getValue();
				}
				dayMoneyMap.put(dateList.get(i), count);
				i++;
			}
			totalExpenditure = 0.0;
			for(Entry<String, Double> en:dayMoneyMap.entrySet()) {
				totalExpenditure += en.getValue();
			}
		}
	}
	
	
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected ArrayList<String> getDate() {
		return dateList;
	}
	protected void setDate(ArrayList<String> date) {
		this.dateList = date;
	}
	protected ArrayList<Map<String, Integer>> getOrderList() {
		return orderList;
	}
	protected void setOrderList(ArrayList<Map<String, Integer>> orderList) {
		this.orderList = orderList;
	}
	protected Map<String, Double> getDayMoneyMap() {
		return dayMoneyMap;
	}
	protected void setDayMoneyMap(Map<String, Double> dayMoneyMap) {
		this.dayMoneyMap = dayMoneyMap;
	}
	protected double getTotalExpenditure() {
		return totalExpenditure;
	}
	protected void setTotalExpenditure(double totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}



	@Override
	public String toString() {
		return "Order [name=" + name + ", dateList=" + dateList + ", orderList=" + orderList + ", dayMoneyMap="
				+ dayMoneyMap + ", totalExpenditure=" + totalExpenditure + "]";
	}
	
}
