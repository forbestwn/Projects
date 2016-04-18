/*
 * service information object 
 * this structor can be used in different senario: romote task, service request 
 * 		command: service name
 * 		data:    input data
 */
var NosliwServiceInfo = function(command, parms){
	this.command = command;
	this.parms = parms;
};




