//$Id$
package com.libman.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	
	public static boolean ValidateEmailId(String emailId){
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailId);
		if(!matcher.matches())
		{
			return false;
		}
		return true;
	}
	
	public static boolean validateString(String string)
	 {
		try {
			Integer.parseInt(string);
			return false;
		}
		catch(Exception e)
		{
			
		}
		 String regex = "\\b[a-zA-Z][a-zA-Z0-9-_\\s\\s]{3,}\\b";
		 Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(string);
			if(!matcher.matches())
			{
				return false;
			}
			return true;
	 }
	
	public static boolean validatePassword(String passWord)
	{
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		 Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(passWord);
			if(!matcher.matches())
			{
				return false;
			}
			return true;
	}
	
	public static boolean validateInteger(int integer)
	{
		if(integer==(int)integer)
		{
			return true;
			//invalid req
		}
		return false;
	}
	
	public static boolean validateISBN(long longNum)
	{
		if(longNum == (long)longNum)
		{
			if(longNum/100000000000000L == 0)
				if(longNum/1000000000000L!=0)
					return true;
		}
		return false;
	}
	
	public static boolean validateStringHasInt(String string)
	{
		String regex ="[0-9]+";
		 Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(string);
			if(!matcher.matches())
			{
				return false;
			}
			return true;
	}
	
	public static void main(String a[])
	{
		if(Validate.validatePassword("cNNrcs2422@"))
		{
			System.out.println("regex");
		}
	}
}
