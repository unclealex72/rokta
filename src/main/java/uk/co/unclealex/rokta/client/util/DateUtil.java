package uk.co.unclealex.rokta.client.util;

import java.util.Date;

public final class DateUtil {

	private static int gregdaynumber(int year, int month, int day){ 
	  
	// computes the day number since 0 January 0 CE (Gregorian) 
	  
	int y=year; 
	int m=month; 
	if(month < 3) y=y-1; 
	if(month < 3) m=m+12; 
	return (int) (Math.floor(365.25*y)-Math.floor(y/100)+Math.floor(y/400)+Math.floor(30.6*(m+1))+day-62); 
	}
	
	private static int isocalendardaynumber(int isoyear, int isoweeknr, int isoweekday){ 
	  
	// computes the day number since 0 January 0 CE (Gregorian) from the ISO 8601 calendar date 
	  
	int q=(int) Math.floor(isoyear/400); 
	int z=isoyear-400*q; 
	int weeksum=(int) (20871*q+52*z+Math.floor((5*z+7-4*Math.floor((z-1)/100))/28)+isoweeknr); 
	return 7*weeksum+isoweekday-5; 
	}

	@SuppressWarnings("deprecation")
	public static int weekOf(Date date){ 
		int year=date.getYear() + 1900; 
		int month=date.getMonth()+1; // 1=January, 2=February, etc. 
		int day=date.getDay(); 
		  
		int daynumber= gregdaynumber(year,month,day);  
		  
		int isoyear=(int) Math.floor(daynumber/365.2425)+2; 
		
		int daynumber0 = 0;
		int i = 0;
		for(;i<3;i++){ 
		  daynumber0=isocalendardaynumber(isoyear-i,1,1); 
		  if(daynumber > daynumber0){ 
		    break; 
		  } 
		} 
		  
		isoyear=isoyear-i; 
		return (int) Math.floor((daynumber-daynumber0)/7)+1; 
	}

	@SuppressWarnings("deprecation")
	public static int yearOf(Date date) {
		return date.getYear() + 1900;
	}

	@SuppressWarnings("deprecation")
	public static int monthOf(Date date) {
		return date.getMonth() + 1900;
	}
}