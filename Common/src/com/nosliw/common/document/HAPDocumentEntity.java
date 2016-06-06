package com.nosliw.common.document;

import java.lang.reflect.Field;
import java.util.Map;

import com.nosliw.common.pattern.HAPPatternManager;
import com.nosliw.common.utils.HAPConstant;

/*
 * this is parent class for class that store static information for document
 */
public class HAPDocumentEntity {

	/*
	 * update document element by replacing the varible with variable value
	 */
	public void updateDocument(Map<String, String> vars){
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for(Field field : fields){
				if(field.getType()==String.class){
					String fieldValue = (String)field.get(this);
					fieldValue = (String)HAPPatternManager.getInstance().getPatternProcessor(HAPConstant.CONS_PATTERN_VARIABLE).parse(fieldValue, vars);
					field.set(this, fieldValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
