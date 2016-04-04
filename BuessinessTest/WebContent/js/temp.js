/**
 * 
 */
var setTransactionId = function(serviceData){
	Nosliw.setTransactionId(serviceData.data.transactionId);
};


var unregisterDataListener = function(listner){
	listner.stopListening();
};

		
/****************************     Transaction    **********************************/
		startTransaction : function(handler){
			serviceCall({
					command : "startTransaction",
					handler : handler,
			});
		},
		
		commit : function(handler){
			serviceCall({
				command : "commit",
				handler : handler,
			});
		},

		rollBack : function(handler){
			serviceCall({
				command : "rollBack",
				handler : handler,
			});
		},
		


		
/****************************     Operation    **********************************/
		operate : function(operation, handler){
			serviceCall({
				command : "operate",
				data : {"operation":operation},
				handler : handler,
			});
		},
		
		entityOperate : function(request, handler){
			serviceCall({
				command : "operateEntity",
				data : {"operations":request.entityOperations, 'requestId':request.id},
				handler : handler,
			});
		},


