package com.dti;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dti.schen.domain.BOPForm;
import com.dti.schen.domain.BOPForm1;
import com.dti.schen.domain.BOPForm2;
import com.dti.schen.domain.BOPForm3;
import com.dti.schen.excel.MyExcelUtil;
import com.dti.schen.parser.BOPFormParser;

public class Main {
	
	public static void main(String...args) throws Exception{
		if (args.length < 2){
			System.err.println("In correct input parameter.");
			System.err.println("You need to give 1st parameter as xml path, like: D:\\xxx\\test.xml");
			System.err.println("You need to give 2nd parameter as xml parsing excel output path, like: D:\\xxx\\output.xls");
			System.exit(1);
		}
		
		// verify args
		if (!args[0].contains(".xml")){
			System.err.println("You need to give 1st parameter as xml path, like: D:\\xxx\\test.xml");
			System.exit(1);
		}
		
		if (!args[1].contains(".xls")){
			System.err.println("You need to give 2nd parameter as xml parsing excel output path, like: D:\\xxx\\output.xls");
			System.exit(1);
		}
		
		System.out.println("Parsing " + args[0] + " ....");
		File file = new File(args[0]);
		if (!file.exists()){
			throw new RuntimeException("Unable to find xml which needs parsing...");
		}
		
		BOPFormParser parser = new BOPFormParser();
		parser.parseForm(file);
		
		// output excel
		MyExcelUtil util = new MyExcelUtil(args[1]);
		HSSFWorkbook workbook = util.createWorksheet(new String[]{"Forms"});
		// set header
		util.writeRow(workbook, "Forms", 0, MyExcelUtil.HEADERS);
		int rowIndex = 1;
		
		List<BOPForm> typeFormList = new ArrayList<BOPForm>();
		
		List<BOPForm1> list1 = parser.getForm1List();
		for (BOPForm1 form : list1){
			if (form.getType().equals("Form")){
				typeFormList.add(form);
			}
			String[] content = MyExcelUtil.transferFormContent(form);
			util.writeRow(workbook, "Forms", rowIndex, content);
        	//System.out.println(form.toString());
			rowIndex++;
        }
		
		List<BOPForm2> list2 = parser.getForm2List();
		for (BOPForm2 form : list2){
			if (form.getType().equals("Form")){
				typeFormList.add(form);
			}
			String[] content = MyExcelUtil.transferFormContent(form);
			util.writeRow(workbook, "Forms", rowIndex, content);
        	//System.out.println(form.toString());
			rowIndex++;
        }
		
		List<BOPForm3> list3 = parser.getForm3List();
		for (BOPForm3 form : list3){
			if (form.getType().equals("Form")){
				typeFormList.add(form);
			}
			String[] content = MyExcelUtil.transferFormContent(form);
			util.writeRow(workbook, "Forms", rowIndex, content);
        	//System.out.println(form.toString());
			rowIndex++;
        }
		
		System.out.println("output " + args[1] + " generation is DONE!");
		System.out.println("Form1 size:" + list1.size());
		System.out.println("Form2 size:" + list2.size());
		System.out.println("Form3 size:" + list3.size());
		System.out.println("Forms with <Type>Form</Type> size:" + typeFormList.size());
		
		
		
		
	}
	
}
