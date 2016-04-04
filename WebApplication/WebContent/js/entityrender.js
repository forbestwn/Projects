/**
 * 
 */

function createWidgetByName(name){
	var func = window[name];
	var widget = new func();
	return widget;
}


function getEntityWidgetSetByType(entityType){
	return entityWidgets[entityType];
}

function getChildWraperByAttribute(parentWraper, attribute){
	if(attribute=='')  return parentWraper;
	
	var attrs = attribute.split(":");
	var wraper = parentWraper;
	var i = 0;
	while(i<attrs.length){
		if(attrs[i]=='#critical'){
			i = i + 1;
		}
		else{
			if(wraper == undefined)  break;
			wraper = wraper.data[attrs[i]];
		}
		i = i + 1;
	}
	return wraper;
}

function handleChildren(parentWidget, widgetConfigure, state, baseattr, parentWraper, basePath){
	var childWidgetSet;
	var childWidget;
	
	for(var i=0; i<widgetConfigure.children.length; i++){
    	childWidgetSet = widgetConfigure.children[i];
    	childWidget = createWidget(childWidgetSet, state, baseattr, parentWraper, basePath);
    	if(childWidget!=undefined)    	parentWidget.children.push(childWidget);
	}
}

function getReferencedEntity(referenceWraper){
	var ID = referenceWraper.ID;
	if(ID==undefined)   return undefined;
	return entityManager.getEntity(ID);
}

function getAllStates(widgetSet){
	var states = widgetSet.states;
	var out = [];
	if(states==undefined)   return out;
	for (var state in states) {
	    if (states.hasOwnProperty(state)) {
	    	out.push(state);
	    }
	}
	return out;
}

function getAllReferenceStates(widgetSet){
	var states = widgetSet.statereferences;
	var out = [];
	if(states==undefined)   return out;
	for (var state in states) {
	    if (states.hasOwnProperty(state)) {
	    	out.push(state);
	    }
	}
	return out;
}

function getWidgetConfigureComplexByState(widgetSet, state){

	var widgetConfigureComplex = {};
	if(state==undefined)  widgetConfigureComplex.state='default';
	var widgetConfigure = widgetSet.states[widgetConfigureComplex.state];
	if(widgetConfigure==undefined){
		widgetConfigureComplex.state='default';
		widgetConfigure = widgetSet.states[widgetConfigureComplex.state];
	}
	widgetConfigureComplex.widgetConfigure = widgetConfigure;
	widgetConfigureComplex.widgetset = widgetSet;
	widgetConfigureComplex.isReference = false;
	return widgetConfigureComplex;
}

function getReferenceWidgetConfigureComplexByState(widgetSet, refWidgetSet, state){
	var widgetConfigureComplex = getWidgetConfigureComplexByState(refWidgetSet, state);
	widgetConfigureComplex.widgetset = widgetSet;
	widgetConfigureComplex.refwidgetset = refWidgetSet;
	widgetConfigureComplex.isReference = true;
	return widgetConfigureComplex;
}

function createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper){
	var widgetConfigure = widgetConfigureComplex.widgetConfigure;
	
	var widget = createWidgetByName(widgetConfigure.name);		//create widget object
	widget.configure = widgetConfigure.parms;					//set widget parms
	widget.datawraper = dataWraper;								//set widget data wraper
	widget.widgetcomplex = widgetConfigureComplex;
	widget.contextwraper = contextWraper;
	widget.children = [];
	return widget;
}


//data : current data
//baseattr:  for attribute widgetSet,
//basepath:  path from root entity wraper
function createWidget(widgetSet, state, baseAttr, contextWraper, basePath){
	var widgetSetType = widgetSet.type;
	var realState = state;
	
	var widget;
	var widgetConfigureComplex;
	var widgetConfigure;
	var dataWraper;
	
	if(widgetSetType=='container'){
		widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, undefined, baseAttr, contextWraper);
		widget = createWidgetByConfigureComplex(widgetConfigureComplex, undefined, contextWraper);
		widgetConfigure = widgetConfigureComplex.widgetConfigure;
		handleChildren(widget, widgetConfigure, realState, baseAttr, contextWraper, basePath);
	}
	else if(widgetSetType=="group"){
		widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, undefined, baseAttr, contextWraper);
		widgetConfigure = widgetConfigureComplex.widgetConfigure;
		if(widgetConfigure!=undefined){
			widget = createWidgetByConfigureComplex(widgetConfigureComplex, undefined, contextWraper);
			handleChildren(widget, widgetConfigure, realState, baseAttr, contextWraper, basePath);
		}
	}
	else if(widgetSetType=="entity"){
		widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, state, baseAttr, contextWraper);		
		dataWraper = getChildWraperByAttribute(contextWraper, baseAttr);
		widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
		widgetConfigure = widgetConfigureComplex.widgetConfigure;
		handleChildren(widget, widgetConfigure, realState, baseAttr, contextWraper, basePath);
	}
	else if(widgetSetType=="element"){
		
	}
	else if(widgetSetType=="attribute"){
		var widgetSetAttr = widgetSet.attribute;
		
		if(baseAttr!='')  widgetSetAttr = baseAttr + ':' + widgetSetAttr;
		dataWraper = getChildWraperByAttribute(contextWraper, widgetSetAttr);

		var datacategary = dataWraper.categary;
		var datatype = dataWraper.type;
		
		var widgetId = widgetSet.attribute;
		if(basePath!='')  widgetId = basePath + ':' + widgetId;
		
		if(datacategary=='simple' || datacategary=='block'){
			widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, realState, baseAttr, contextWraper);		
			widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
		}
		else if(datacategary=='entity'){
			var parentRelation = dataWraper.childrelation;
			if(parentRelation=='reference'){
				var refWraper = getReferencedEntity(dataWraper);
				if(refWraper==undefined){
					widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, 'empty', baseAttr, contextWraper);		
					widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
				}
				else{
					widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, realState, baseAttr, contextWraper);		
					widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
					if(widgetConfigureComplex.isReference==false){
						//direct defined
						handleChildren(widget, widgetConfigureComplex.widgetConfigure, realState, widgetConfigureComplex.attrbase, dataWraper, widgetId);
					}
					else{
						handleChildren(widget, widgetConfigureComplex.widgetConfigure, realState, widgetConfigureComplex.widgetSetAttr, dataWraper, widgetId);
					}
				}
			}
			else{
				widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, undefined, baseAttr, contextWraper);	
				widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
				
				if(dataWraper.data!=undefined){
					if(widgetConfigureComplex.isReference==false){
						//direct defined
						handleChildren(widget, widgetConfigureComplex.widgetConfigure, realState, widgetConfigureComplex.attrbase, contextWraper, basePath);
					}
					else{
						handleChildren(widget, widgetConfigureComplex.widgetConfigure, realState, widgetSetAttr, contextWraper, widgetId);
					}
				}
			}
		}
		else if(datacategary=='container'){
			
			widgetConfigureComplex = getWidgetConfigureComplex(widgetSet, undefined, baseAttr, contextWraper);	
			widget = createWidgetByConfigureComplex(widgetConfigureComplex, dataWraper, contextWraper);
			
			var eleWidgetConfigureComplex;
			var eleRealState;

			var eleDataCategary;
			var eleDataType;
			var eleParentRelation;
			
			var elementWidgetSet = widgetConfigureComplex.widgetConfigure.children[0];
			var eleWidget;
			var elements = getContainerElements(dataWraper);
			
			var eleId;
			
			for(var i=0; i<elements.length; i++){
				
				eleId = widgetId + ":" + elements[i].id;
				
				eleDataCategary = dataWraper.attrconfigure.childdatacategary;
				eleDataType = dataWraper.attrconfigure.childdatatype;
				if(eleDataCategary=='simple' || eleDataCategary=='block'){
					eleWidgetConfigureComplex = getWidgetConfigureComplex(elementWidgetSet, eleRealState, elements[i]);		
					eleWidget = createWidgetByConfigureComplex(eleWidgetConfigureComplex, elements[i], elements[i]);
				}
				else if(eleDataCategary=='entity'){
					eleParentRelation = dataWraper.attrconfigure.childrelation;
					if(eleParentRelation=='own'){
						eleWidgetConfigureComplex = getWidgetConfigureComplex(elementWidgetSet, eleRealState, '', elements[i]);		
						eleWidget = createWidgetByConfigureComplex(eleWidgetConfigureComplex, elements[i], elements[i]);
						handleChildren(eleWidget, eleWidgetConfigureComplex.widgetConfigure, eleRealState, '', elements[i], eleId);
					}
					else{
						eleWidgetConfigureComplex = getWidgetConfigureComplex(elementWidgetSet, eleRealState, '', elements[i]);		
						eleWidget = createWidgetByConfigureComplex(eleWidgetConfigureComplex, elements[i], elements[i]);
						handleChildren(eleWidget, eleWidgetConfigureComplex.widgetConfigure, eleRealState, '', elements[i], eleId);
					}
				}

				eleWidget.id = eleId;
				widget.children.push(eleWidget);
			}
		}
		
		widget.id = widgetId;
	}
	return widget;
}

function getContainerElements(containerWraper){
	var elements = [];
	if(containerWraper.type=='map'){
		for (var key in containerWraper.data) {
		    if (containerWraper.data.hasOwnProperty(key)) {
		    	elements.push(containerWraper.data[key]);
		    }
		}
	}
	else{
		for(var i=0; i<containerWraper.data.length; i++){
			elements.push(containerWraper[i]);
		}
	}
	return elements;
}


/*
 * input:
 * 		state: 
 * 			undefined:   use default state
 * 			value: 		use this state
 * 
 * output: widgetconfigure complex 
 *     state:   current state
 *     widgetConfigure: actual configure
 *     widgetset:  
 *     isReference:  
 *     refwidgetset
 *     baseattr
 */
function getWidgetConfigureComplex(widgetSet, state, baseAttr, contextDataWraper){
	var widgetConfigureComplex = {};
	var widgetSetType = widgetSet.type;
	var realState = state;
	var allStates;
	if(widgetSetType=='container'){
		widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, undefined);		
	}
	else if(widgetSetType=='group'){
		var criticalAttr = widgetSet.criticalattr;
		if(baseAttr!='')  criticalAttr = baseAttr + ':'+criticalAttr;
		var criticalVal = getChildWraperByAttribute(contextDataWraper, criticalAttr).data;
		widgetConfigureComplex.widgetConfigure = widgetSet.states[criticalVal];
		widgetConfigureComplex.state = criticalVal;
		if(widgetConfigureComplex.widgetConfigure==undefined){
			widgetConfigureComplex.widgetConfigure = widgetSet.states['other'];
			widgetConfigureComplex.state = 'other';
		}
	}
	else if(widgetSetType=='entity'){
		widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, state);		
	}
	else if(widgetSetType=="element"){
		var datacategary = widgetSet.datacategary;
		var datatype = widgetSet.datatype;
		var refWidgetSet;
		if(datacategary=='simple' || datacategary=='block'){
			if(state==undefined)	realState = widgetSet.defaultstate;
			widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, realState);		
		}
		else if(datacategary=='entity'){
			var parentRelation = widgetSet.parentrelation;
			var elementDataType = datatype;
			if(parentRelation=='reference'){
				elementDataType = contextDataWraper.type;
			}
			
			if(realState==undefined)  realState = widgetSet.defaultstate;
			if(realState==undefined)  realState = 'default';
			if(widgetSet.states[realState]!=undefined){
				widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, realState);
			}
			else{
				refWidgetSet = getEntityWidgetSetByType(elementDataType);
				widgetConfigureComplex = getReferenceWidgetConfigureComplexByState(widgetSet.statereferences[realState], refWidgetSet);
			}
		}
	}
	else if(widgetSetType=="attribute"){
		var datacategary = widgetSet.datacategary;
		var datatype = widgetSet.datatype;
		var refWidgetSet;
		var attribute = widgetSet.attribute;
		if(baseAttr!='')  attribute = baseAttr + ':'+ attribute;
		var dataWraper = getChildWraperByAttribute(contextDataWraper, attribute).data;

		if(datacategary=='simple' || datacategary=='block'){
			if(state==undefined)	realState = widgetSet.defaultstate;
			widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, realState);		
		}
		else if(datacategary=='entity'){
			var parentRelation = widgetSet.parentrelation;

			if(parentRelation=='reference'){
				var refWraper = getReferencedEntity(dataWraper);
				if(refWraper==undefined){
					widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, 'empty');
				}
				else{
					if(realState==undefined)  realState = widgetSet.defaultstate;
					if(realState==undefined)  realState = 'default';
					if(widgetSet.states[realState]!=undefined){
						widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, realState);
					}
					else{
						realState = widgetSet.referencestates[realState];
						widgetConfigureComplex = getReferenceWidgetConfigureComplexByState(widgetSet, getEntityWidgetSetByType(refWraper.type), realState);
					}
				}
			}
			else{
				allStates = getAllStates(widgetSet);
				if(allStates.length>=1){
					widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, undefined);
				}
				else{
					allStates = getAllReferenceStates(widgetSet);
					if(allStates.length>=1){
						refWidgetSet = getEntityWidgetSetByType(datatype);
						widgetConfigureComplex = getReferenceWidgetConfigureComplexByState(widgetSet, refWidgetSet, widgetSet.statereferences[allStates[0]]);
					}
				}
			}
		}
		else if(datacategary=='container'){
			widgetConfigureComplex = getWidgetConfigureComplexByState(widgetSet, undefined);
		}
	}
	
	widgetConfigureComplex.widgetset = widgetSet;
	widgetConfigureComplex.baseattr = baseAttr;
	return widgetConfigureComplex;
}



function renderEntity(entityID, state, parent){	
	var entityWraper = entityManager.getEntity(entityID);   //get entity wraper
	var widgetSet = entityWidgets[entityWraper.type];       //get widget configure by entity type  
	
	var widget = createWidget(widgetSet, state, "", entityWraper, "");
	widget.render(parent);
	
	return widget;
}

function renderEntityWraper(entityWraper, state, parent){	
	var widgetSet = entityWidgets[entityWraper.type];       //get widget configure by entity type  
	
	var widget = createWidget(widgetSet, state, "", entityWraper, "");
	widget.render(parent);
	
	return widget;
}

