package com.howtodoinjava.countdownlatch.example.service;

/**
 * I have simulated an application startup class which starts N threads that will check for external systems
 * and report back to latch, on which startup class is waiting.
 * As soon as all services are verified and checked, startup proceeds.
 */
public class Main {
	public static void main(String[] args) 
	{
		boolean result = false;
		try {
			result = ApplicationStartupUtil.checkExternalServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("External services validation completed !! Result was :: "+ result);
	}
}
