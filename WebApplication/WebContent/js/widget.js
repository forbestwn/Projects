/**
group:
	by default, attribute group name is entity type name
	we can name group
	disable, enable by group
 * 
 */

var Widget = Backbone.View.extend({
/*	
	//data type for data wraper in this widget
	categary : '',
	type : '',

	//configure for this widget
	widgetConfigure : {},

	//attribute definition for this wraper if it is an attribute of entity
	attrDefinition : {},
	attrName : '',
	attrDisplayName : '',
	
	//ui configure : visible, editable, block, ...
	configure : {},

	//data of the wraper
	//atom : data
	//container : container for child widget
	//entity : attribute wraper value
	data : {},
*/
	
	render : function(parentdom){
	},

	
	/*
	 * get widget path from root
	 */
	getPath : function(){
		
	},

	
	setState : function(){
		
	},
});

var WidgetAtom = Widget.extend({
	render : function(parentdom){
//		var value = JSON.stringify(this.datawraper.data);
		var value = this.datawraper.data;
		var mid = jq(this.id);
		var tid = this.id;
		var context = this.contextwraper;
		var q = "#"+mid;
		var ele = $("<input type='text' id='"+ mid +"' value='"+value+"'>");
		parentdom.append(ele);
		ele.blur(function() {
			var wraper = getChildWraperByAttribute(context, tid);
			wraper.data = ele.val();
		});
	},
});

function jq( myid ) {
//    return myid.replace( /(:|\.|\[|\])/g, "\\$1" );
    return myid.replace(/([;&,\.\+\*\~':"\!\^#$%@\[\]\(\)=>\|])/g, '\\$1');
}


var WidgetSimple = WidgetAtom.extend({
});

var WidgetBlock = WidgetAtom.extend({
});

var WidgetContainer = Widget.extend({
	initialize: function() {
	},
	  
	add : function(child){
		
	},
	
	remove: function(){
		
	},
});

var WidgetList = WidgetContainer.extend({
	initialize: function() {
		this.data = [];
	},

	render : function(parentdom){
		var contDom = $("<div></div>");

		var title = this.attrName;
		if(title==undefined){
			title = this.ID;
			contDom.append($("<h1>" + title +"</h1>"));
		}
		
		var listDom = $("<ul></ul>");
		for (var i=0; i<this.datawraper.data.length; i++) {
		    	var childWidget = this.datawraper.data[i];
		    	var attrDom = $("<li></li>");
		    	attrDom.append($("<h2>"+childWidget.attrName+"</h2>"));
		    	childWidget.render(attrDom);
		    	listDom.append(attrDom);
		}
		contDom.append(listDom);
		
		parentdom.append(contDom);
	},
});

var WidgetMap = WidgetContainer.extend({
	
	initialize: function() {
		this.data = {};
	},
	
	render : function(parentdom){
		
		var contDom = $("<div></div>");

		var listDom = $("<ul></ul>");
		
		for(var i=0; i<this.children.length; i++){
	    	var childWidget = this.children[i];
	    	var eleDom = $("<li></li>");
	    	childWidget.render(eleDom);
	    	listDom.append(eleDom);
		}
		
		contDom.append(listDom);
		parentdom.append(contDom);
	},
});


var WidgetEntity = Widget.extend({

	initialize: function() {
		this.data = {};
	},
	
	render : function(parentdom){
		
		var title = this.attrName;

		var contDom = $("<fieldset title=\""+ title +"\"></fieldset>");
		
		if(title==undefined){
			title = this.datawraper.ID;
			contDom.append($("<h1>" + title +"</h1>"));
		}
		
		var listDom = $("<div></div>");
		
		for(var i=0; i<this.children.length; i++){
	    	var attrDom = $("<div></div>");
	    	var childWidget = this.children[i];
	    	
	    	if(childWidget.widgetcomplex.widgetset.type=='attribute'){
		    	attrDom.append($("<span>"+childWidget.widgetcomplex.widgetset.title+" : </span>"));
	    	}
	    	childWidget.render(attrDom);
	    	listDom.append(attrDom);
		}
		contDom.append(listDom);
		
		parentdom.append(contDom);
	},
	
	addChild : function(attr, child){
		this.datawraper.data[attr] = child;
	},
	
	getChild : function(attr){
		return this.datawraper.data[attr];
	}
});


var WidgetContainer = Widget.extend({

	initialize: function() {
		this.data = {};
	},
	
	render : function(parentdom){
		
		var contDom = $("<fieldset></fieldset>");
		
		var listDom = $("<div></div>");
		
		for(var i=0; i<this.children.length; i++){
	    	var attrDom = $("<div></div>");
	    	var childWidget = this.children[i];
	    	
	    	attrDom.append($("<span>"+childWidget.widgetcomplex.widgetset.title+" : </span>"));
	    	childWidget.render(attrDom);
	    	listDom.append(attrDom);
		}
		contDom.append(listDom);
		
		parentdom.append(contDom);
	},
	
	addChild : function(attr, child){
		this.datawraper.data[attr] = child;
	},
	
	getChild : function(attr){
		return this.datawraper.data[attr];
	}
});

/*
var widgetFactory = function(){
	
	var widgets = {};
	
	var factory = {
		
		registerWidget : function(name, widget){
			widegts[name] = widget;
		},
		
		createWidget : function(data){
			var widget = widgets[data.widget];
			return new widget();
		},
			
	};

	factory.registerWidget('simple', WidgetSimple);
	factory.registerWidget('block', WidgetBlock);
	factory.registerWidget('list', WidgetList);
	factory.registerWidget('map', WidgetMap);
	factory.registerWidget('entity', WidgetEntity);
	
	return factory;
}();

*/