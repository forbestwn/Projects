package com.test.business.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.nosliw.entity.data.HAPEntityWraper;
import com.test.business.util.PathUtility;

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
		loadTemplate();
		
		String path = PathUtility.getUserDataPath(this.getUserContext().getId());
		File rootFolder = new File(path);
		for(File file : FileUtils.listFiles(rootFolder, new String[]{"xml"}, true)){
			FileInputStream fi=null;
			try {
				fi = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			this.readInputStream(fi);
		}
	}
	
	private void loadTemplate(){
		String rootPath = PathUtility.getUITemplatePath(); 
		
		Collection<File> files = FileUtils.listFiles(new File(rootPath), new String[]{"xml"}, true);

		for(File file : files){
			FileInputStream fi=null;
			try {
				fi = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			this.readInputStream(fi);
		}
	}
	
	@Override
	public void afterLoad()
	{
		super.afterLoad();
	}
	
}
