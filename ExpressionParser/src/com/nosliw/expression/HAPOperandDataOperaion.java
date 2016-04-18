package com.nosliw.expression;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPOperand;
import com.nosliw.data.HAPWraper;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.expression.utils.HAPAttributeConstant;

/*
 * class for data operation 
 */
public class HAPOperandDataOperaion extends HAPOperandOperation{
	//data to run the operation
	private HAPOperand m_baseData;

	public HAPOperandDataOperaion(HAPOperand baseData, String operation, HAPOperand[] parms, HAPDataTypeManager dataTypeMan){
		super(operation, parms, dataTypeMan);
		this.m_baseData = baseData;
	}
	
	@Override
	public int getOperandType() {	return HAPConstant.CONS_EXPRESSION_OPERAND_DATAOPERATION;	}

	@Override
	public HAPData execute(Map<String, HAPData> vars, Map<String, HAPWraper> wraperVars){
		HAPData[] parms = new HAPData[this.getParameters().length+1];
		parms[0] = this.m_baseData.execute(vars, wraperVars);
		for(int i=0; i<this.getParameters().length; i++){
			parms[i+1] = this.getParameters()[i].execute(vars, wraperVars);
		}
		HAPServiceData serviceData = parms[0].getDataType().operate(this.getOperationName(), parms);
		return (HAPData)serviceData.getData();
	}

	@Override
	public boolean isScriptRunnable(String script)
	{
		//check base data
		if(!this.m_baseData.isScriptRunnable(script))   return false;
		
		//check parms
		for(HAPOperand parm : this.getParameters()){
			if(!parm.isScriptRunnable(script))  return false;
		}

		//check operation script
		if(!this.isOperationScriptAvailable(script))   return false;
		
		return true;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class> jsonDataTypeMap){
		super.buildJsonMap(jsonMap, jsonDataTypeMap);
		jsonMap.put(HAPAttributeConstant.ATTR_OPERAND_DATAOPERATION_BASEDATA, this.m_baseData.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
	}
	
	@Override
	public void preProcess(Map<String, HAPDataTypeInfo> varsInfo, Set<HAPDataTypeInfo> dataTypeInfos){
		this.m_baseData.preProcess(varsInfo, dataTypeInfos);
		//try figure out base data type info
		if(this.getBaseDataType()==null){
			HAPDataTypeInfo baseDataTypeInfo = this.m_baseData.getOutDataTypeInfo();
			if(baseDataTypeInfo!=null){
				this.setBaseDataTypeInfo(baseDataTypeInfo);
			}
		}
		super.preProcess(varsInfo, dataTypeInfos);
	}
	
	public HAPOperand getBaseData(){	return this.m_baseData;	}
	public void setBaseData(HAPOperand operand){  this.m_baseData = operand; }
}
