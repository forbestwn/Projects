
	var m_createUnitDataTag = function(id, uiTag, uiResourceView){
		var m_view;
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_preInit : function(){
			},

			tag_initViews : function(){
			},
				
			tag_updateContext : function(){
				var dataDef = getContextPathWraper(this.context, 'dataconfigure').data;
				var name = dataDef.name.data;
				var datatypeInfo = dataDef.datatypeinfo.data;
				var datatype = datatypeInfo.datatype.data;
				var datatypetype = datatype.type.data;
				if(datatypetype=='atom'){
					var atomType = datatype.atomtype.data;
					var categary = atomType.categary.data;
					var type = atomType.type.data;
					var defaultValue = atomType.defaultvalue;
					if(categary=='simple'){
						if(type=='text'){
							var html = "<br>"+name + ":<input type='text'/> <br>";
							m_view = $(html);
							this.startEle.after(m_view);
						}
					}
				}
			},
		});

		return m_uiTagView;
	};

	