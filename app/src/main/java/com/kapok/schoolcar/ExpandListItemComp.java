package com.kapok.schoolcar;

import java.util.regex.Pattern;
 
public class ExpandListItemComp implements Comparable<ExpandListItemComp>{
	public String compator=new String("");
	public String stringBak=new String("");
	ExpandListItemComp(String temp){
		stringBak=temp;		
		Pattern pattern = Pattern.compile("[**]+"); 
    	String [] subresult=pattern.split(temp);
    	/*if(subresult.length>4){
    		
    	}
		System.out.println(subresult[subresult.length-1]);*/
    	compator=subresult[subresult.length-1];
	}
	@Override
	public int compareTo(ExpandListItemComp another) {
		// TODO Auto-generated method stub
		return this.compator.compareTo(another.compator);
	}

	public String getString() {
		// TODO Auto-generated method stub
		return this.stringBak;
	}

}
