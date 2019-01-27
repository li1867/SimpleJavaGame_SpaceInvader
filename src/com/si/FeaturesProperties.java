package com.si;

import java.util.*;
import java.io.*;

public class FeaturesProperties {
	public static void main(String args[]) {
		System.out.println(getRowsOfInvaders());
		System.out.println(getColumnsOfInvaders());
		System.out.println(getRateInvadersDropDown());
		System.out.println(getNumberOfDefenderLives());
	}

	public static int getRowsOfInvaders() {
		InputStream ins = FeaturesProperties.class.getResourceAsStream("features.properties");
		Properties p = new Properties();
		try {
			p.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String rowsStr = p.getProperty("Rows_of_invaders");
		int rows = 1;

		try {
			rows = Integer.parseInt(rowsStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (rows < 1) {
			rows = 1;
		}
		return rows;

	}

	public static int getColumnsOfInvaders() {
		InputStream ins = FeaturesProperties.class.getResourceAsStream("features.properties");
		Properties p = new Properties();
		try {
			p.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String columnsStr = p.getProperty("Columns_of_invaders");
		int columns = 1;

		try {
			columns = Integer.parseInt(columnsStr);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}

		if (columns < 1) {
			columns = 1;
		}
		return columns;
	}

	public static int getRateInvadersDropDown() {
		InputStream ins = FeaturesProperties.class.getResourceAsStream("features.properties");
		Properties p = new Properties();
		try {
			p.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String s = p.getProperty("Rate_invaders_drop_down");
		int rate = 1;

		try {
			rate = Integer.parseInt(s);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}

		if (rate < 15) {
			rate = 15;
		}
		return rate;
	}

	public static int getNumberOfDefenderLives() {
		InputStream ins = FeaturesProperties.class.getResourceAsStream("features.properties");
		Properties p = new Properties();
		try {
			p.load(ins);
		} catch (Exception e) {
//			e.printStackTrace();
		}

		String s = p.getProperty("Number_of_defender_lives");
		int lifeCount = 1;

		try {
			lifeCount = Integer.parseInt(s);
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}

		if (lifeCount < 1) {
			lifeCount = 1;
		}
		return lifeCount;
	}
}