package com.test.business.manager;

import com.nosliw.HAPApplicationContext;
import com.nosliw.ui.HAPUIResourceImporter;
import com.nosliw.ui.HAPUIResourceManager;
import com.test.business.util.PathUtility;

public class UIResourceManager extends HAPUIResourceManager{

	
	public UIResourceManager(HAPApplicationContext applicationContext) throws Exception{
		super(applicationContext);
		this.loadUIResource();
	}

	public void loadUIResource() throws Exception{
//		String resourcePath = "/Users/ningwang/Desktop/WebToApp/CoreProject/Application/test_business/uiresource/";
//		String resourcePath = "D:/MyWork/CoreProject/Application/test_business/uiresource/";
		String resourcePath = PathUtility.getUIResourcePath();
//		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"employeelist.res"));
		
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"business.res"));

		

		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"businesslist.res"));

		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"mobile/face.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"mobile/application.res"));

		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasourcetypelists.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasourcelists.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/storyboard.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/templatelists.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uifaceunitstructure.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uiunitinfo.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplateinfo.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplatedata.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplateevent.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplatecommand.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplatecomponent.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/uitemplatecomponentsel.res"));
		
		
		
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/module/datatype.res"));

		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasource/newdatasourcerss.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasource/editdatasourcerss.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasource/newdatasource.res"));
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"web2app/datasource/editdatasource.res"));
	
		this.addUIResource(HAPUIResourceImporter.readUIResource(resourcePath+"uiresource/uiresourceconfigure.res"));
	}
	
}
