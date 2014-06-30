package com.example.arogyamitra;

public class Utils {
	public static boolean isAlpha(String name) {
		//strip spaces to eliminate possibility of string containing only spaces
		String strippedName = name.replaceAll("\\s+","");
	    char[] chars = strippedName.toCharArray();
	    if(name.length() <= 0){
	    	return false;
	    }

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
	public static boolean isPhoneNumber(String number) {
		//Phone number formats : +91(10 digits) or 10 digits
		String strippedNumber = number.replaceAll("\\s+", "");
		
		if(strippedNumber.length() == 0){
			return true;
		}
		
	    String phoneRegex = "(\\+91)?\\d{10}";
	    if(number.matches(phoneRegex)){
	    	return true;
	    }

	    return false;
	}

}
