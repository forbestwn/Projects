/*
 * method to create ui resource view according to 
 * 		uiresource object
 * 	 	name space id
 * 		parent uiresource
 */
var nosliwCreateUIResourceView = function(uiResource, id, parent, contextElementInfoArray, requestInfo){
	//temporately store uiResource
	var loc_uiResource = uiResource;

	//parent ui resource view
	var loc_parentResourveView = parent;
	//name space for this ui resource view
	//every element/customer tag have unique ui id within a web page
	//during compilation, ui id is unique within ui resoure, however, not guarenteed between different ui resource view within same web page
	//name space make sure of it as different ui resource view have different name space
	var loc_idNameSpace = id;

	//all constants defined. they are used in expression
	var loc_constants = loc_uiResource.constant;
	
	//all content expression objects
	var loc_expressionContents = [];
	
	//all customer tags
	var loc_uiTags = {};
	//all events on regular elements
	var loc_elementEvents = [];
	//all events on customer tag elements
	var loc_tagEvents = [];
	
	//all the attributes on this ui resource
	var loc_attributes = {};
	
	//context object for this ui resource view
	var loc_context = undefined;

	//ui resource view wraper element
	var loc_startEle = undefined;
	var loc_endEle = undefined;

	//temporary object for ui resource view container
	var	loc_fragmentDocument = undefined;
	var loc_parentView = undefined;
	
	//the ui resource view created 
	var loc_out = undefined;

	//if this ui resource is defined within a customer tag, then this object store all the information about that parent tag
	var loc_parentTagInfo = undefined;

	//object store all the functions for js block and expression
	var loc_scriptObject = undefined;
	
	//event source used to register and trigger event
	var loc_eventSource = undefined;
	
	/*
	 * init element event object
	 */
	var loc_initElementEvent = function(eleEvent){
		//get element for this event
		var ele = loc_out.prv_getLocalElementByUIId(loc_out.prv_getUpdateUIId(eleEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_UIID]));
		var subEle = ele;
		//if have sel attribute set, then find sub element according to sel
		var selection = eleEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_SELECTION];
		if(!nosliwCommonUtility.isStringEmpty(selection))		subEle = ele.find(selection);

		//register event
		var eventValue = eleEvent;
		var eventName = eleEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_EVENT];
		subEle.bind(eventName, function(event){
			var info = {
				event : event, 
				element : subEle,
			};
			loc_scriptObject.callEventFunction(eventValue[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_FUNCTION], undefined, info);
		});
		
		return {
			source : subEle,
			event :  eventName,
		};
	};

	/*
	 * init ui tag event object
	 */
	var loc_initTagEvent = function(tagEvent){
		var tag = loc_uiTags[loc_out.prv_getUpdateUIId(tagEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_UIID])];
		var eventName = tagEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_EVENT];
		
		var listener = tag.registerEvent(eventName, function(event, data, requestInfo){
			var info = {
				event : event,
				tag : tag,
				requestInfo: requestInfo,
			};
			loc_scriptObject.callEventFunction(tagEvent[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_ELEMENTEVENT_FUNCTION], data, info);
		});
		
		return {
			source : tag,
			event :  eventName,
			listener: listener,
		};
	};
	
	
	/*
	 * find matched element according to attribute value
	 */
	var loc_getLocalElementByAttributeValue = function(name, value){return loc_findLocalElement("["+name+"='"+value+"']");};
	
	/*
	 * find matched elements according to selection
	 */
	var loc_findLocalElement = function(select){return loc_startEle.nextUntil(loc_endEle.next()).find(select).addBack(select);};
	
	
	/*
	 * update everything again
	 */
	var loc_refresh = function(){
		loc_setContext(loc_context);
	};

	/*
	 * get all views for this resource view
	 */
	var loc_getViews = function(){	return loc_startEle.add(loc_startEle.nextUntil(loc_endEle)).add(loc_endEle);  };

	var loc_resourceLifecycleObj = {};
	loc_resourceLifecycleObj["NOSLIWCONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT"] = function(uiResource, id, parent, contextElementInfoArray, requestInfo){
		//build context element first
		loc_context = nosliwUIResourceUtility.buildUIResourceContext(loc_uiResource, contextElementInfoArray);
		
		//create script object
		loc_scriptObject=  nosliwCreateUIResourceScriptObject(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_SCRIPTFACTORYNAME], loc_out);
		
		//wrap html by start and end element
		var resourceStartId = "-resource-start";
		var resourceEndId = "-resource-end";
		var html = nosliwUIResourceUtility.createPlaceHolderWithId(resourceStartId) + _.unescape(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_HTML]) + nosliwUIResourceUtility.createPlaceHolderWithId(resourceEndId);
		
		//update all uiid within html by adding space name to uiid
		html = nosliwUIResourceUtility.updateHtmlUIId(html, loc_idNameSpace);
		
		//render html to temporary document fragment
		loc_fragmentDocument = $(document.createDocumentFragment());
		loc_parentView = $("<div></div>");
		loc_fragmentDocument.append(loc_parentView);
		var views = $($.parseHTML(html));
		loc_parentView.append(views);
		
		//get wraper dom element (start and end element)
		loc_startEle = loc_parentView.find("["+NOSLIWCOMMONCONSTANT.CONS_UIRESOURCE_ATTRIBUTE_UIID+"='"+loc_out.prv_getUpdateUIId(resourceStartId)+"']");
		loc_endEle = loc_parentView.find("["+NOSLIWCOMMONCONSTANT.CONS_UIRESOURCE_ATTRIBUTE_UIID+"='"+loc_out.prv_getUpdateUIId(resourceEndId)+"']");
		
		//init attributes of ui resource
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_ATTRIBUTES], function(value, key, list){
			loc_attributes[key] = value;			return list;
		});
		
		//init expression content
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_EXPRESSIONCONTENTS], function(expressionContent, key, list){
			loc_expressionContents.push(nosliwCreateUIResourceExpressionContent(expressionContent, "text", loc_out, requestInfo));
		});

		//init normal expression attribute
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_EXPRESSIONATTRIBUTES], function(expressionAttr, key, list){
			loc_expressionContents.push(nosliwCreateUIResourceExpressionContent(expressionAttr, "attribute", loc_out, requestInfo));
		});

		//init customer tags
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_UITAGS], function(uiTag, tagUiId, list){
			var uiTagId = loc_out.prv_getUpdateUIId(uiTag[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_ID]);
			var uiTagObj = nosliw.getUITagManager().createUITagObject(uiTagId, uiTag, loc_out);
			loc_uiTags[uiTagId] =  uiTagObj;
		});

		//init customer tag expression attribute
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_EXPRESSIONTAGATTRIBUTES], function(expressionTagAttr, key, list){
			loc_expressionContents.push(nosliwCreateUIResourceExpressionContent(expressionTagAttr, "tagAttribute", loc_out, requestInfo));
		});
		
		//init element event
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_ELEMENTEVENTS], function(eleEvent, key, list){
			loc_elementEvents.push(loc_initElementEvent(eleEvent));
		});

		//init customer tag event
		_.each(loc_uiResource[NOSLIWATCOMMONTRIBUTECONSTANT.ATTR_UIRESOURCE_TAGEVENTS], function(tagEvent, key, list){
			loc_tagEvents.push(loc_initTagEvent(tagEvent));
		});
		
		//call init funtion in uiresource definitio
		loc_out.prv_getScriptObject().prv_callLocalFunction(NOSLIWCONSTANT.UIRESOURCE_FUNCTION_INIT);
		
		loc_eventSource = nosliwCreateRequestEventSource();
		
		loc_uiResource = undefined;
	};

	loc_resourceLifecycleObj["NOSLIWCONSTANT.LIFECYCLE_RESOURCE_EVENT_DESTROY"] = function(requestInfo){
		
		//call destroy funtion in uiresource definition
		loc_out.prv_getScriptObject().prv_callLocalFunction(NOSLIWCONSTANT.UIRESOURCE_FUNCTION_DESTROY);
		
		//detach view from dom
		loc_out.detachViews();
		
		loc_attributes = {};
		
		_.each(loc_uiTags, function(uiTag, tag, list){
			uiTag.destroy();
		});
		loc_uiTags = undefined;

		_.each(loc_expressionContents, function(expressionContent, key, list){
			expressionContent.destroy();
		});
		loc_expressionContents = undefined;
		
		_.each(loc_elementEvents, function(eleEvent, key, list){
			eleEvent.source.unbind(eleEvent.event);
		});
		loc_elementEvents = undefined;

		loc_eventSource.clearup();
		
		loc_tagEvents = undefined;

		loc_startEle = undefined;
		loc_endEle = undefined;
		
		loc_parentResourveView = undefined;
		loc_resource = undefined;
		loc_idNameSpace = undefined;
		
		loc_fragmentDocument = undefined;
		loc_parentView = undefined;

		loc_context = undefined;
	};

	loc_out = {
		ovr_getResourceLifecycleObject : function(){	return loc_resourceLifecycleObj;	},
		
		prv_getScriptObject : function(){return loc_scriptObject;},
		//get the parent resource view that contain this resource view, when this resource is within tag
		prv_getParentResourceView : function(){		return loc_parentResourveView;		},
		//get root resource view: the resource view that don't have parent
		prv_getRootResourceView : function(){
			var view = this;
			var parent = view.prv_getParentResourceView();
			while(parent!=undefined){
				view = parent;
				parent = view.prv_getParentResourceView();
			}
			return view;
		},
		
		prv_trigueEvent : function(eventName, data, requestInfo){loc_eventSource.triggerEvent(eventName, data, requestInfo); },
		
		prv_getTagByUIId : function(uiId){ return loc_uiTags[uiId];  },
		
		/*
		 * update ui id by adding space name ahead of them
		 */
		prv_getUpdateUIId : function(uiId){	return loc_idNameSpace+NOSLIWCOMMONCONSTANT.CONS_SEPERATOR_FULLNAME+uiId;	},

		/*
		 * find matched element according to uiid
		 */
		prv_getLocalElementByUIId : function(id){return loc_findLocalElement("["+NOSLIWCOMMONCONSTANT.CONS_UIRESOURCE_ATTRIBUTE_UIID+"='"+id+"']");},
		
		getContext : function(){return loc_context;},
		updateContext : function(wrappers, requestInfo){		loc_context.updateContext(wrappers, requestInfo);		},

		//get all elements of this ui resourve view
		getViews : function(){	return loc_startEle.add(loc_startEle.nextUntil(loc_endEle)).add(loc_endEle).get();	},

		//append this views to some element as child
		appendTo : function(ele){  loc_getViews().appendTo(ele);   },
		//insert this resource view after some element
		insertAfter : function(ele){	loc_getViews().insertAfter(ele);		},

		//remove all elements from outsiders parents and put them back under parentView
		detachViews : function(){	loc_parentView.append(loc_getViews());		},

		//trigue event from this ui resource view
		trigueEvent : function(eventName, data, requestInfo){	
			//for all the child resource view, it will use root resource view to trigue the event 
			this.prv_getRootResourceView().triggerEvent(eventName, data, requestInfo); 
		},
		registerEvent : function(handler, thisContext){	return loc_eventSource.registerEventHandler(handler, thisContext);},

		
		//return dom element
		getElementByUIId : function(uiId){return this.prv_getLocalElementByUIId(uiId)[0];},
		getElementsByAttributeValue : function(attr, value){return loc_getLocalElementByAttributeValue(attr, value).get();},
		getElementByAttributeValue : function(attr, value){return loc_getLocalElementByAttributeValue(attr, value).get(0);},
		
		//return jquery object
		get$EleByUIId : function(uiId){return this.prv_getLocalElementByUIId(uiId);},
		get$EleByAttributeValue : function(attr, value){return loc_getLocalElementByAttributeValue(attr, value);},

		//find tag object according to tag name
		getTagsByName : function(name){
			var tagsOut = [];
			_.each(loc_uiTags, function(uiTag, tagId, list){
				var tagName = uiTag.getTagName();
				if(tagName==name){
					tagsOut.push(uiTag);
				}
			});
			return tagsOut;
		},

		//find tag object that have tag name and particular attribute/value
		getTagsByNameAttribute : function(name, attr, value){
			var tagsOut = [];
			_.each(loc_uiTags, function(uiTag, tagId, list){
				var tagName = uiTag.getTagName();
				if(tagName==name){
					if(uiTag.getAttribute(attr)==value){
						tagsOut.push(uiTag);
					}
				}
			});
			return tagsOut;
		},
		
		//return map containing value/tag pair for particular tag name and its attribute
		getTagsAttrMapByName : function(name, attr){
			var tagsOut = {};
			_.each(loc_uiTags, function(uiTag, tagId, list){
				var tagName = uiTag.getTagName();
				if(tagName==name){
					tagsOut[uiTag.getAttribute(attr)] = uiTag;
				}
			});
			return tagsOut;
		},
		

		
		setAttribute : function(attribute, value){loc_attributes[attribute]=value;},
		getAttribute : function(attribute){return loc_attributes[attribute];},
		
		getIdNameSpace : function(){return loc_idNameSpace;},
		getParentTagInfo : function(){	return loc_parentTagInfo;	},
		setParentTagInfo : function(info){		loc_parentTagInfo = info;	},
	};

	
	//append resource and object life cycle method to out obj
	loc_out = nosliwLifecycleUtility.makeResourceObject(loc_out);
	loc_out = nosliwTypedObjectUtility.makeTypedObject(loc_out, NOSLIWCONSTANT.TYPEDOBJECT_TYPE_UIRESOURCEVIEW);
	
	loc_out.init(uiResource, id, parent, contextElementInfoArray, requestInfo);
	
	return loc_out;
};
