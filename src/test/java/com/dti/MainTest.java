package com.dti;
import java.io.File;

import com.dti.schen.domain.BOPForm1;
import com.dti.schen.domain.BOPForm2;
import com.dti.schen.domain.BOPForm3;
import com.dti.schen.parser.BOPFormParser;

public class MainTest {
	
	public static void main(String...args){		
		ClassLoader classLoader = Main.class.getClassLoader();
		File file = new File(classLoader.getResource("dchen_test.xml").getFile());
		
		BOPFormParser parser = new BOPFormParser();
		parser.parseForm(file);
        
		for (BOPForm1 form : parser.getForm1List()){
        	System.out.println(form.toString());
        }
		
		for (BOPForm2 form : parser.getForm2List()){
        	System.out.println(form.toString());
        }
		
		for (BOPForm3 form : parser.getForm3List()){
        	System.out.println(form.toString());
        }
	}
	

}
