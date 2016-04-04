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

/*
 * class for data type operation 
 */
public class HAPOperandDataTypeOperation extends HAPOperandOperation{

	public HAPOperandDataTypeOperation(HAPDataTypeInfo dataTypeInfo, String operation, HAPOperand[] parms, HAPDataTypeManager dataTypeMan){
		super(operation, parms, dataTypeMan);
		this.setBaseDataTypeInfo(dataTypeInfo);
	}

	@Override
	public int getOperandType() {return HAPConstant.CONS_EXPRESSION_OPERAND_DATATYPEOPERATION;}

	@Override
	public HAPData execute(Map<String, HAPData> vars, Map<String, HAPWraper> wraperVars) {
		HAPData[] parms = new HAPData[this.getParameters().length];
		for(int i=0; i<this.getParameters().length; i++){
			parms[i] = this.getParameters()[i].execute(vars, wraperVars);
		}
		HAPServiceData serviceData = this.getBaseDataType().operate(this.getOperationName(), parms);
		return (HAPData)serviceData.getData();
	}

	@Override
	public boolean isScriptRunnable(String script) {
		//check operatio script
		if(!this.isScriptRunnable(script))   return false;
		
		//check parms
		for(HAPOperand parm : this.getParameters()){
			if(!parm.isScriptRunnable(script))  return false;
		}
		return true;
	}

	@Override
	public void preProcess(Map<String, HAPDataTypeInfo> varsInfo, Set<HAPDataTypeInfo> dataTypeInfos){
		super.preProcess(varsInfo, dataTypeInfos);
	}
}

