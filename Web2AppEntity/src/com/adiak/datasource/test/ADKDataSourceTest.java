package com.adiak.datasource.test;

import com.adiak.datasource.ADKDataSource;
import com.nosliw.data.HAPData;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;
import com.nosliw.entity.datadefinition.data.HAPMapData;
import com.nosliw.entity.datadefinition.data.HAPTableData;

public class ADKDataSourceTest  extends HAPEntityData implements ADKDataSource{

	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_INPUTTYPE = "inputtype";
	private static final String ATTRIBUTE_OUTPUTTYPE = "outtype";

	@Override
	public String getName() {
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
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

			HAPMapData row1 = new HAPMapData();
			row1.put("image", this.getUserContext().getDataTypeManager().parseString("{categary:'block',type:'image', data:{dialect:'url', value='http://www.goensaving.com/images/stories/logo_armstrong.jpg'}}"));
			row1.put("text", this.getUserContext().getDataTypeManager().parseString("hello1"));

			HAPMapData row2 = new HAPMapData();
			row2.put("image", this.getUserContext().getDataTypeManager().parseString("{categary:'block',type:'image', data:{dialect:'url', value='http://www.goensaving.com/images/stories/logo_goodman.jpg'}}"));
			row2.put("text", this.getUserContext().getDataTypeManager().parseString("hello2"));
			
			out.addRow(row1);
			out.addRow(row2);
			return out;
		}
		else if(this.getName().equals("inf")){
			HAPMapData out = new HAPMapData();
			out.put("image", this.getUserContext().getDataTypeManager().parseString("{categary:'block',type:'image', data:{dialect:'url', value='http://www.goensaving.com/images/stories/logo_armstrong.jpg'}}"));
			out.put("text", this.getUserContext().getDataTypeManager().parseString("hello"));
			return out;
		}
		return null;
	}

	@Override
	public String getID() {
		return null;
	}

}
