package com.jeromet.hotel_menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

/** 
 * @author  作者: Jerome
 * @date 创建时间：2019年1月3日 下午8:10:20 
 * @version 1.0 
 * @description:  
 */

public class Menu {
	protected static Map<String, Double> menu = new HashMap<>();
	static {
		try {
			File menuFile = new File("document"+File.separator+"menu.txt");
			Scanner scan = new Scanner(new FileInputStream(menuFile));
			while(scan.hasNext()) {
				String str = scan.nextLine();
				String[] tempStr = str.split("\\t");
				menu.put(tempStr[0], Double.valueOf(tempStr[2]));
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("文件没找到");
		}
	}
}




