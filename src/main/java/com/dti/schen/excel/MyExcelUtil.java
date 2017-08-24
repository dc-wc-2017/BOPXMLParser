package com.dti.schen.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;

import com.dti.schen.domain.BOPForm;
import com.dti.schen.domain.BOPForm1;
import com.dti.schen.domain.BOPForm2;
import com.dti.schen.domain.BOPForm3;

public class MyExcelUtil {
	private String path;
	private static final String ERROR_VALUE = "##ERROR##";
	public static final String[] HEADERS = new String[]{
			"Form Category", 
			"Form ID", 
			"Form Name", 
			"Form Number", 
			"Form Level", 
			"Form Condition", 
			"Form Type", 
			"Min Occurs", 
			"Max Occurs"
	};
	
	public MyExcelUtil(String path){
		this.path = path;
	}
	
	
	public HSSFWorkbook createWorksheet(String[] sheetNames){
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (String sheetName : sheetNames){
			workbook.createSheet(sheetName);
		}
		return output(workbook);
	}
	
	
	public HSSFWorkbook writeRow(HSSFWorkbook workbook, String sheetName, int rowIndex, String[] content){
		HSSFSheet sheet = workbook.getSheet(sheetName);
		if (sheet == null){
			throw new RuntimeException("Unable to find sheet: " + sheetName);
		}
		
		HSSFRow row = sheet.createRow(rowIndex);		
		for (int i=0; i<content.length; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(content[i]);
			if (content[i].equals(ERROR_VALUE)){
				setRedFont(workbook, cell);
			}
		}
		return output(workbook);
	}
	
	public HSSFCell setRedFont(HSSFWorkbook workbook, HSSFCell cell){
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColorPredefined.RED.getIndex());
		style.setFont(font);
		cell.setCellStyle(style);
		return cell;
	}
	
	private HSSFWorkbook output(HSSFWorkbook worksheet) {
		try{
			FileOutputStream out = new FileOutputStream(path);
			worksheet.write(out);
			out.close();
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
		return worksheet;
	}
	
	public static <T extends BOPForm> String[] transferFormContent(T t) {
		if (t instanceof BOPForm1){
			BOPForm1 form = (BOPForm1) t;
			return handleForm1(form);
		} else if (t instanceof BOPForm2){
			BOPForm2 form = (BOPForm2) t;
			return handleForm2(form);
		} else if (t instanceof BOPForm3){
			BOPForm3 form = (BOPForm3) t;
			return handleForm3(form);
		}
		
		throw new RuntimeException("Unknown type for " + t.getClass().toString());
	}
	
	public static String[] handleForm1(BOPForm1 form){	
		List<String> result = new ArrayList<>();
		result.add("Form1");  // "Form Category"
		result = handleCommon(result, form);
		return result.toArray(new String[result.size()]);
	}
	
	public static String[] handleForm2(BOPForm2 form){	
		List<String> result = new ArrayList<>();
		result.add("Form2"); // "Form Category"
		result = handleCommon(result, form);
		return result.toArray(new String[result.size()]);
	}
	
	public static String[] handleForm3(BOPForm3 form){	
		List<String> result = new ArrayList<>();
		result.add("Form3"); // "Form Category"
		result = handleCommon(result, form);
		return result.toArray(new String[result.size()]);
	}
	
	public static List<String> handleCommon(List<String> result, BOPForm form){
		// "Form ID"
		result.add(form.getId());
		// "Form Name"
		result.add(form.getName());
		// "Form Number"
		result.add(form.getNumber());
		// "Form Level"
		result.add(form.getParentName());
		// "Form Condition"
		result.add(form.getCondition());
		// "Form Type"
		if (form.getCondition().length() == 0){
			if (Integer.parseInt(form.getMinOccurs()) == 0 && Integer.parseInt(form.getMaxOccurs()) == 1){
				result.add("Optional");
			} else if (Integer.parseInt(form.getMinOccurs()) == 1 && Integer.parseInt(form.getMaxOccurs()) == 1) {
				result.add("Mandatory");
			} else {
				result.add(ERROR_VALUE);
			}
		}else{
			result.add(form.getCondition());
		}
		// "Min Occurs"
		result.add(form.getMinOccurs());
		// "Max Occurs"
		result.add(form.getMaxOccurs());
		
		return result;
	}
	
	

}
