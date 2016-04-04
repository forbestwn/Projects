	var createFaceEmulationFunction = function(parms, parent){
		
		var m_view;
		
		var that = new NosliwAppFunction('Storyboard', parms, parent, 
		{
			m_doInit : function(){
				m_view = $('<iframe id="faceEmulation" src="faceemulate.html" height="442" width="294"><br/>Your brouwser does not support inline frames.<br /></iframe>');
				$("#faceemulation").append(m_view);
			},
		
			m_postInit : function(){
			},

			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='showFace'){
					var url = "faceemulate.html?facePath="+data.facePath;
					m_view.attr('src', url);
				}
			},
		});

		return that;
	};
	
