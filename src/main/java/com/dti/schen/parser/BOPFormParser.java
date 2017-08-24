package com.dti.schen.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.dti.schen.domain.BOPForm;
import com.dti.schen.domain.BOPForm1;
import com.dti.schen.domain.BOPForm2;
import com.dti.schen.domain.BOPForm3;


public class BOPFormParser {
	private List<BOPForm1> form1List;
	private List<BOPForm2> form2List;
	private List<BOPForm3> form3List;
	private int formLevel;

	public BOPFormParser() {
		form1List = new ArrayList<>();
		form2List = new ArrayList<>();
		form3List = new ArrayList<>();
		formLevel = 0;
	}

	public void parseForm(File file) {
		form1List.clear();
		form2List.clear();
		form3List.clear();
		
		BOPForm1 form1 = null;
		BOPForm2 form2 = null;
		BOPForm3 form3 = null;
		
		boolean isFieldNode = false;
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
		
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file), "UTF-8");
			
			
			while (xmlEventReader.hasNext()) {
				
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
										
					// when reading <Form>
					if (startElement.getName().getLocalPart().equals("Form")) {
						formLevel++;
						if (formLevel == 1){
							form1 = new BOPForm1();
						}
						if (formLevel == 2){
							form2 = new BOPForm2();
						}
						if (formLevel == 3){
							form3 = new BOPForm3();
						}
					}
					
					// when reading <Field>
					else if (startElement.getName().getLocalPart().equals("Field")) {
						isFieldNode = true;
					}
					
					// parse form1
					parseFormInstance(xmlEventReader, startElement, isFieldNode, form1, 1);
					parseFormInstance(xmlEventReader, startElement, isFieldNode, form2, 2);
					parseFormInstance(xmlEventReader, startElement, isFieldNode, form3, 3);
					
				}
				
				// if </Form> end is reached, add BOPForm1 to list
				if (xmlEvent.isEndElement()) {
					
					EndElement endElement = xmlEvent.asEndElement();
					// when reading </Form> 
					if (endElement.getName().getLocalPart().equals("Form")) {
						formLevel--;
						if (formLevel == 0){
							form1List.add(form1);
						}
						if (formLevel == 1){
							form2List.add(form2);
						}
						if (formLevel == 2){
							form3List.add(form3);
						}
					}
					
					// when reading </Field>
					if (endElement.getName().getLocalPart().equals("Field")) {
						isFieldNode = false;
					}
				}
			}

		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFormInstance(XMLEventReader xmlEventReader, StartElement startElement, boolean isFieldNode, BOPForm form, int formLevelTarget) throws XMLStreamException{
		XMLEvent xmlEvent = null;
		
		// read <FormId>...</FormId>
		if (startElement.getName().getLocalPart().equals("Id") && formLevel == formLevelTarget && !isFieldNode) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setId(xmlEvent.asCharacters().getData());
			}
		} 
		
		// read <TableName>...</TableName>
		if (startElement.getName().getLocalPart().equals("TableName") && formLevel == formLevelTarget && !isFieldNode) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setTableName(xmlEvent.asCharacters().getData());
			}
		} 
		// read <Type>...</Type>
		else if (startElement.getName().getLocalPart().equals("Type") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setType(xmlEvent.asCharacters().getData());
			}
		}
		// read <Sequence>...</Sequence>
		else if (startElement.getName().getLocalPart().equals("Sequence") && formLevel == formLevelTarget && !isFieldNode) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setSequence(xmlEvent.asCharacters().getData());
			}
		} 
		// read <AddChangeDeleteFlag>...</AddChangeDeleteFlag>
		else if (startElement.getName().getLocalPart().equals("AddChangeDeleteFlag") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setAddChangeDeleteFlag(xmlEvent.asCharacters().getData());
			}
		}
		// read <Name>...</Name>
		else if (startElement.getName().getLocalPart().equals("Name") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setName(xmlEvent.asCharacters().getData());
			}
		}
		// read <ParentName>...</ParentName>
		else if (startElement.getName().getLocalPart().equals("ParentName") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setParentName(xmlEvent.asCharacters().getData());
			}
		}
		// read <HidePremium>.../<HidePremium>
		else if (startElement.getName().getLocalPart().equals("HidePremium") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setHidePremium(xmlEvent.asCharacters().getData());
			}
		}
		// read <Condition>...</Condition>
		else if (startElement.getName().getLocalPart().equals("Condition")  && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setCondition(xmlEvent.asCharacters().getData());
			}
		}
		// read <Number>...</Number>
		else if (startElement.getName().getLocalPart().equals("Number") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setNumber(xmlEvent.asCharacters().getData());
			}
		}
		// read <MinOccurs>...</MinOccurs>
		else if (startElement.getName().getLocalPart().equals("MinOccurs") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setMinOccurs(xmlEvent.asCharacters().getData());
			}
		}
		// read <MaxOccurs>...</MaxOccurs>
		else if (startElement.getName().getLocalPart().equals("MaxOccurs")  && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setMaxOccurs(xmlEvent.asCharacters().getData());
			}
		}
		// read <BureauRuleNumber>...</BureauRuleNumber>
		else if (startElement.getName().getLocalPart().equals("BureauRuleNumber")  && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setBureauRuleNumber(xmlEvent.asCharacters().getData());
			}
		}
		// read <Comment>...</Comment>
		else if (startElement.getName().getLocalPart().equals("Comment") && formLevel == formLevelTarget && !isFieldNode ) {
			xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isCharacters()){
				form.setComment(xmlEvent.asCharacters().getData());
			}
		}
	}
	
	public List<BOPForm1> getForm1List(){
		return form1List;
	}
	
	public List<BOPForm2> getForm2List(){
		return form2List;
	}
	
	public List<BOPForm3> getForm3List(){
		return form3List;
	}
}
