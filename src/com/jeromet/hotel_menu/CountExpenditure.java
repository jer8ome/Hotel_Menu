package com.jeromet.hotel_menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/** 
 * @author  作者: Jerome
 * @date 创建时间：2019年1月3日 下午8:28:47 
 * @version 1.0 
 * @description:  
 */

public class CountExpenditure {
	protected static void main(String[] args) {
		CountExpenditure countExpenditure = new CountExpenditure();
		ArrayList<Customer> customers=countExpenditure.getTopFive();
		for(Customer customer:customers) {
			System.out.println(customer);
		}
	}
	
	private ArrayList<Customer> customsers = new ArrayList<>();
	
	protected CountExpenditure() {
		init();
	}
	
	protected ArrayList<Customer> getOrders() {
		return customsers;
	}


	//建立顾客列表
	protected void init() {
		File file = new File("document"+File.separator+"orders.txt");
		try(Scanner scan = new Scanner(new FileInputStream(file))) {	//写在try里面，文件能够自动关闭
			Map<String, Integer> orders = new HashMap<>();
			String name = null;
			String date = null;
			Customer customer = new Customer();
			while(scan.hasNext()) {
				//字符串较乱，所有去除空白符
				String str = scan.nextLine().trim().replaceAll("\\t", "");
				if(str.matches("^name.*")) {	//当字符串以name开头时
					String[] nameStr = str.split("=");
					if(customer.getDate().contains(date)) {	//如果顾客一天在饭店吃了两顿，第二顿记在当天吃的
						int dateSeat = customer.getDate().indexOf(date);
						for(Entry<String, Integer> dish:orders.entrySet()) {
							if(customer.getOrderList().get(dateSeat).containsKey(dish.getKey())) {	//如果顾客之前已经点过这个菜，就在该菜的数量上加
								int num = customer.getOrderList().get(dateSeat).get(dish.getKey());
								customer.getOrderList().get(dateSeat).put(dish.getKey(), dish.getValue()+num);
							} else {	//如果顾客之前没点过这个菜
								customer.getOrderList().get(dateSeat).put(dish.getKey(), dish.getValue());
							}
						}
					} else {	//顾客的第一顿
						customer.getOrderList().add(orders);
					}
					if(customer!=null&&customer.getName()!=null&&findOrder(name)==null) {	//如果顾客不为空，名字不为空，且为第一次消费
						customer.getDate().add(date);
						customsers.add(customer);
					} else if(customer!=null&&customer.getName()!=null&&findOrder(name)!=null) {	//如果顾客不为空，名字不为空，不是第一次消费
						if(!customer.getDate().contains(date)) {	//不是第一次消费就要判断是否为当天第一次消费
							customer.getDate().add(date);
						}
						customsers.set(customsers.indexOf(findOrder(name)), customer);		//更新顾客列表的顾客
					}
					name = nameStr[1].trim();
					if((customer=findOrder(name))!=null){	//如果顾客列表中有该顾客
					} else {
						customer = new Customer();
						customer.setName(name);
					}
					orders = new HashMap<>();
				} else if (str.matches(".*date.*")) {	//当字符串以date开头时,由于文件首行有编码方式标记字符的影响
					String[] dateStr = str.split("=");	//故采取了只要存在该字符就匹配，本题无一定要匹配字符开头的要求
					date = dateStr[1].trim();
				} else {
					Map<String, Integer> order = getOrder(str);
					for(Entry<String, Integer> dish:order.entrySet()) {
						if(orders.containsKey(dish.getKey())) {		//如果顾客之前已经点过这个菜，就在该菜的数量上加
							orders.put(dish.getKey(), dish.getValue()+orders.get(dish.getKey()));
						} else {	//如果没有则新增订单
							orders.put(dish.getKey(), dish.getValue());
						}
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			System.err.println("文件未找到");
		}
		
	}
	
	//处理订单，分割菜名和数量
	private Map<String, Integer> getOrder(String dishStr) {
		char[] dishArr = dishStr.toCharArray();
		int numSeat = 0;
		//遍历字符串获取数字的位置
		for (int i = 0; i < dishArr.length; i++) {
			if(Character.isDigit(dishArr[i])) {
				numSeat = i;
			}
		}
		String dishName = dishStr.substring(0, numSeat).trim();
		String dishNum = dishStr.substring(numSeat).trim();
		Map<String, Integer> dishMap = new HashMap<>();
		dishMap.put(dishName, Integer.valueOf(dishNum));
		return dishMap;
	}
	
	//按名字查找列表中的顾客
	private Customer findOrder(String name) {
		if(!customsers.isEmpty()) {		//如果顾客列表不为空
			for(Customer order:customsers) {
				if(order.getName()!=null&&order.getName().equals(name)) {
					return order;
				}
			}
		}
		return null;
	}

	
	//获取消费额前五名
	private ArrayList<Customer> getTopFive() {
		for(Customer order:customsers) {	//计算所有顾客总消费额
			order.count();
		}
		//给顾客总消费额排序
		Collections.sort(customsers, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
				return -(int) (o1.getTotalExpenditure()-o2.getTotalExpenditure());
			}
		});
		ArrayList<Customer> topFiveOrders = new ArrayList<>();
		int count = 0;
		//排好的序截取前五名
		for(Customer order:customsers) {
			topFiveOrders.add(order);
			count++;
			if(count==5) {
				break;
			}
		}
		return topFiveOrders;
	}
	
}

//可以自定义排序类名
class SortByTotalExp implements Comparator<Customer>{

	@Override
	public int compare(Customer o1, Customer o2) {
		return (int) (o1.getTotalExpenditure()- o1.getTotalExpenditure());
	}
	
}

