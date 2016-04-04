	var createFaceInfoFunction = function(parms, parent){
		
		var m_faceUnitStructureFunction;
		var m_unitInfoFunction;

		var m_faceEntity;
		
		var that = new NosliwAppFunction('FaceInfo', parms, parent, 
		{
			m_doInit : function(){
				$( "#faceinfo" ).append( $( "<table><tr><td><div id='unitstructure'></div></td><td><div id='unitinfo'></div></td></tr></table>" ) );

				m_faceUnitStructureFunction = createFaceUnitStructureFunction();
				m_unitInfoFunction = createUnitInfoFunction();

				m_faceUnitStructureFunction.on("unitInfo", function(unitInfo){
					m_unitInfoFunction.command("setUnitInfo", {
						faceEntity : m_faceEntity,
						unitPath : unitInfo.unitPath,
					});
				});
			},

			m_doCommand : function(name, data){
				if(name=="setFaceInfo"){
		    		m_faceEntity = NosliwEntityManager.getEntity(data.faceID);
					m_faceUnitStructureFunction.command("setFaceInfo", {
						faceEntity : m_faceEntity,
						facePath : data.facePath,
					});
				}
			},				
		});

		return that;
	};
