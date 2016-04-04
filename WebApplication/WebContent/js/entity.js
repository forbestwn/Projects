/**
group:
	by default, attribute group name is entity type name
	we can name group
	disable, enable by group
 * 
 */

var entityManager = function(){
	
	var allEntities = {};
	
	createWidgetByName = function(name){
		var func = window[name];
		var widget = new func();
		return widget;
	}
	
	handleWraper = function(wraper){
		
		if(wraper.categary=="simple" || wraper.categary=="block"){
			return handleAtom(wraper);
		}
		else if(wraper.categary=="entity"){
			return handleEntity(wraper);
		}
		else{
			return handleContainer(wraper);
		}
	}
	
	
	handleEntity = function(entityWraper){
		
		var entityWidget = createWidgetByName(entityWraper.widget.name);
		entityWidget.widgetConfigure = entityWraper.widget.parms;
		entityWidget.categary = entityWraper.categary;
		entityWidget.type = entityWraper.type;
		entityWidget.attrName = entityWraper.attrName;
		entityWidget.ID = entityWraper.ID;
		
		var entityData = entityWraper.data;
		for (var attr in entityData) {
		    if (entityData.hasOwnProperty(attr)) {
		    	var attrWraper = entityData[attr];
		    	
		    	if(attrWraper.categary=="simple" || attrWraper.categary=="block"){
		    		entityWidget.addChild(attr, handleAtom(attrWraper));
		    	}
		    	else if(attrWraper.categary=="container"){
		    		
		    		entityWidget.addChild(attr, handleContainer(attrWraper));
		    	}
		    	else if(attrWraper.categary=="entity"){
		    		entityWidget.addChild(attr, handleEntity(attrWraper));
		    	}
		    }
		}
		return entityWidget;
	};
	
	handleContainer = function(containerWraper){
		var containerWidget = createWidgetByName(containerWraper.widget.name);
		containerWidget.widgetConfigure = containerWraper.widget.parms;
		containerWidget.categary = containerWraper.categary;
		containerWidget.type = containerWraper.type;
		containerWidget.attrName = containerWraper.attrName;

		if(containerWraper.type=="map"){
			for (var attr in containerWraper.data) {
			    if (containerWraper.data.hasOwnProperty(attr)) {
					var widget = handleWraper(containerWraper.data[attr]);
					containerWidget.data[attr]=widget;
			    }
			}
		}
		else{
			for(var i=0; i<containerWraper.length; i++){
				var widget = handleWraper(containerWraper[i]);
				containerWidget.add(widget);
			}
		}
		return containerWidget;
	};
	
	handleAtom = function(atomWraper){
		var atomWidget = createWidgetByName(atomWraper.widget.name);

		atomWidget.widgetConfigure = atomWraper.widget.parms;
		atomWidget.categary = atomWraper.categary;
		atomWidget.type = atomWraper.type;
		atomWidget.attrName = atomWraper.attrName;
		
		atomWidget.data = atomWraper.data;
		return atomWidget;
	};
	
	var manager = {
		addEntity : function(entityWraper){
//			var widget = handleWraper(entityWraper);
			allEntities[entityWraper.ID]=entityWraper;
		},
		
		getEntity : function(id){
			return allEntities[id];
		},
	};
	return manager;
	
}();

