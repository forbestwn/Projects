package com.webmobile.manager;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.nosliw.entity.HAPContainerWraper;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.util.HAPUtility;
import com.nosliw.xmlimp.entity.HAPXMLEntityLoader;
import com.webmobile.util.PathUtility;
import com.webmobile.util.Utility;

public class TemplateEntityLoader extends EntityLoader{

	public TemplateEntityLoader(UITemplateUserContext userContext) {
		super(userContext);
	}
	
	@Override
	public void load(){
		try{
			String rootPath = PathUtility.getUITemplatePaht(); 

			Collection<File> files = FileUtils.listFiles(new File(rootPath), new String[]{"xml"}, true);

			for(File file : files){
				this.readInputStream(new FileInputStream(file));
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	@Override
	public void afterLoad()
	{
		super.afterLoad();
	}
	
}
