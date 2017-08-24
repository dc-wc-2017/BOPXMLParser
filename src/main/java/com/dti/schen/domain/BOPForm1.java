package com.dti.schen.domain;

public class BOPForm1 extends BOPForm {
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Form1=")
			.append("\nID:").append(this.id)
			.append("\nTableName:").append(this.tableName)
			.append("\nType:").append(this.type)
			.append("\nSequence:").append(this.sequence)
			.append("\nAddChangeDeleteFlag:").append(this.addChangeDeleteFlag)
			.append("\nName:").append(this.name)
			.append("\nParentName:").append(this.parentName)
			.append("\nHidePremium:").append(this.hidePremium)
			.append("\nCondition:").append(this.condition)
			.append("\nNumber:").append(this.number)
			.append("\nMinOccurs:").append(this.minOccurs)
			.append("\nMaxOccurs:").append(this.maxOccurs)
			.append("\nBureauRuleNumber:").append(this.bureauRuleNumber)
			.append("\nComment:").append(this.comment)
			.append("\n");
			
		return sb.toString();
	}

}
