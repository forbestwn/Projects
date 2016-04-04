var FaceManager = function(){
		var m_faceConfigures = {};
		
		var m_thisFace = {};
		
		var m_transferManager = function(){
			var m_faceStacks = [];
			var m_currentStack = {};
			var m_currentFace = {};
			
			var man = {
				next : function(face){
					face.previousFace = m_currentFace;
					
					m_faceStacks.push(face);
					
					m_currentFace = face;
				},

				command: function(name, data, parms){
					$.mobile.changePage('main?face=productinfo&data='+JSON.stringify(data));
				},
				
				getCurrentFace : function(){
					return m_currentFace;
				},
			};
			return man;
		}();
		
		
		var m_faceManager = {
				
			init : function(){
			},

			getFace : function(faceId, faceData){
				return MobileServiceManager.getFace(faceId, faceData);
			},

			getThisFace : function(){return m_thisFace;},
			setThisFace : function(face){m_thisFace = face;},
			
			
			
//			getCurrentFace : function(){return m_currentFace;},
//			setCurrentFace : function(name){m_currentFace = name;},
			
			addFaceConfigure : function(faceConfigure){
				m_faceConfigures[faceConfigure.name] = faceConfigure;
			},
				
			getFaceConfigure : function(name){
				return m_faceConfigures[name];
			},
			
			
			getTransferManager : function(){return m_transferManager;},

		};
		return m_faceManager;
}();
