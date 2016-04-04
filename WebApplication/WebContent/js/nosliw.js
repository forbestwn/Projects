
var Nosliw = {
	
	EntityDefinitionManager : {},
		
	WidgetManager : {},
	
	EntityManager : {},
	
	ModuleManager : {},
		
};

Nosliw.ModuleManager = function(){
	
	
	var loc_manager = {
		startFunction : function(name){
			
		},
		removeFunction : function(name){
			
		},
		
			
	};
	return loc_manager;
}();

Module = {
		
	init : function(){
		
	},
	
	show : function(parent){
		
	},
	
	finish : function(){
		
	},
		
	
}



Nosliw.EntityManager = function(){
	var loc_globalEntitys = {};
	var loc_entitys = {};
	
	var loc_manager = {
		getEntity : function(ID){
				
		},
		
		updateEntity : function(entity){
			
		},
		
		
			

		
		
	};
	return loc_manager;
}();

Nosliw.EntityDefinitionManager = function(){
	var loc_allEntityDefinitions = {};
	
	var loc_manager = {
		getEntityDefinition : function(entityName){
			return loc_allEntityDefinitions[entityName];
		},
		addEntityDefinition : function(entityDefinition){
			loc_allEntityDefinitions[entityDefinition.name]=entityDefinition;
		},
	};
	return loc_manager;
};

Nosliw.WidgetManager = function(){
	var loc_allWidgetDefinitions = {};
	
	var loc_manager = {
		getWidgetDefinition : function(widgetName){
			return loc_allWidgetDefinitions[widgetName];
		},
		addWidgetDefinition : function(widgetDefinition){
			loc_allWidgetDefinitions[widgetDefinition.name]=widgetDefinition;
		},
	};
	return loc_manager;
};












