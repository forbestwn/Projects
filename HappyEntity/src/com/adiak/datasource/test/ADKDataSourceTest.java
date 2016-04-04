package com.adiak.datasource.test;

import com.adiak.datasource.ADKDataSourcePoint;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.data.exchange.HAPMapData;
import com.nosliw.data.exchange.HAPTableData;
import com.nosliw.data.exchange.HAPTableRowData;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;
import com.nosliw.entity.imp.datadefinition.HAPDataTypeEntity;

public class ADKDataSourceTest  extends HAPEntityData implements ADKDataSourcePoint{

	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_INPUTTYPE = "inputtype";
	private static final String ATTRIBUTE_OUTPUTTYPE = "outtype";

	public ADKDataSourceTest(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}
	
	@Override
	public String getName() {
		return this.getAttributeValue(ATTRIBUTE_NAME).toString();
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public HAPDataTypeEntity getInputDataDefinition() {
		return null;
	}

	@Override
	public HAPDataTypeEntity getOutputDataDefinition() {
		return (HAPDataTypeEntity)this.getAttributeEntityValue(ATTRIBUTE_OUTPUTTYPE);
	}

	@Override
	public HAPData process(HAPData data) {
		if(this.getName().equals("table")){
			HAPTableData out = new HAPTableData();
			out.addColumn("text");
			out.addColumn("image");

			HAPTableRowData row1 = new HAPTableRowData();
			row1.put("image", this.getUserContext().getDataTypeManager().parseString("{dataType:{categary:'simple',type:'image'}, data:{url='http://www.goensaving.com/images/stories/logo_armstrong.jpg'}}", null, null));
			row1.put("text", this.getUserContext().getDataTypeManager().parseString("hello1", null, null));

			HAPTableRowData row2 = new HAPTableRowData();
			row2.put("image", this.getUserContext().getDataTypeManager().parseString("{dataType:{categary:'simple',type:'image'}, data:{url='http://www.goensaving.com/images/stories/logo_goodman.jpg'}}", null, null));
			row2.put("text", this.getUserContext().getDataTypeManager().parseString("hello2", null, null));
			
			out.addRow(row1);
			out.addRow(row2);
			return out;
		}
		else if(this.getName().equals("inf")){
			HAPMapData out = new HAPMapData();
			out.put("image", this.getUserContext().getDataTypeManager().parseString("{dataType:{categary:'simple',type:'image'}, data:{url='http://www.goensaving.com/images/stories/logo_armstrong.jpg'}}", null, null));
			out.put("text", this.getUserContext().getDataTypeManager().parseString("hello", null, null));
			return out;
		}
		return null;
	}

	@Override
	public String getID() {
		return null;
	}

}
