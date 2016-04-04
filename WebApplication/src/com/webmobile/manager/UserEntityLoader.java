package com.webmobile.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.nosliw.HAPUserContext;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.HAPContainerWraper;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.entity.HAPDataWraperTask;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.util.HAPUtility;
import com.nosliw.xmlimp.entity.HAPXMLEntityLoader;
import com.webmobile.util.PathUtility;
import com.webmobile.util.Utility;

public class UserEntityLoader extends EntityLoader{

	public UserEntityLoader(UserContext userContext) {
		super(userContext);
	}

	@Override
	protected String getFileNameByEntity(HAPEntityWraper entityWraper){
		String path = PathUtility.getUserDataPath(this.getUserContext().getId());
		String type = entityWraper.getEntityType();
		String id = entityWraper.getId();
		return path + "/" + type + "/" + id + ".xml";
	}
	
	
	@Override
	public void load(){
		
		try{
			String path = PathUtility.getUserDataPath(this.getUserContext().getId());
			File rootFolder = new File(path);
			
			for(File file : FileUtils.listFiles(rootFolder, new String[]{"xml"}, true)){
				this.readInputStream(new FileInputStream(file));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterLoad()
	{
		super.afterLoad();
	}
	
}
