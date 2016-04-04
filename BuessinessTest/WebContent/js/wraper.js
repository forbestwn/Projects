/**
 * 
 */
function getAttributeValue(wraper, attribute){
	return getWraperData(wraper)[attribute];
}

function getWraperDataCategary(wraper){
	return wraper.dataType.categary;
}

function getWraperDataType(wraper){
	return wraper.dataType.type;
}

function getWraperDataTypeInfo(wraper){
	return wraper.dataType;
}

function getWraperData(wraper){
	return wraper.data;
}

