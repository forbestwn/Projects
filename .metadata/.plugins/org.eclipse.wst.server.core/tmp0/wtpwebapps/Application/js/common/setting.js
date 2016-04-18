/**
 * 
 */
/*
 * store settring for sync task, (service, command)
 */
var NosliwConfigure = function(defaultConfigure){
	this.defaultConfigure = defaultConfigure;
};

NosliwConfigure.prototype = {
	createConfigure : function(configure){
		var out = nosliwCommonUtility.mergeObjects(this.defaultConfigure, configure);
		return out;
	},
	
	getDefaultConfigure : function(){
		return this.defaultConfigure;
	},
};
