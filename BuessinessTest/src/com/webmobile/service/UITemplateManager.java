package com.webmobile.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.nosliw.HAPUserContext;
import com.nosliw.entity.data.HAPEntityWraper;
import com.test.business.manager.EntityLoader;
import com.test.business.manager.UserContext;
import com.test.business.util.PathUtility;

public class UITemplateManager extends EntityLoader{

	public UITemplateManager(TemplateUserContext userContext) {
		super(userContext);
		userContext.setUITemplateManager(this);
	}
	
	@Override
	public void load(){
		try{
			String rootPath = PathUtility.getUITemplatePath(); 

			Collection<File> files = FileUtils.listFiles(new File(rootPath), new String[]{"xml"}, true);

			for(File file : files){
				System.out.println("Reading entity from file :  "  + file.toString());
				this.readInputStream(new FileInputStream(file));
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	@Override
	protected String getFileNameByEntity(HAPEntityWraper entityWraper){
		String type = entityWraper.getChildWraperByPath("type").getData().toString();
		String path = PathUtility.getUITemplatePath(type);
		String name = entityWraper.getChildWraperByPath("name").getData().toString();
		return path + "/" + name+ "/" + type + ".xml";
	}
	
	public String getTemplateFolderByEntity(HAPEntityWraper entityWraper){
		String type = entityWraper.getChildWraperByPath("type").getData().toString();
		String path = PathUtility.getUITemplatePath(type);
		String name = entityWraper.getChildWraperByPath("name").getData().toString();
		return path + "/" + name;
	}
	
	public String getResourceFileByEntity(HAPEntityWraper entityWraper){
		String type = entityWraper.getChildWraperByPath("type").getData().toString();
		String path = PathUtility.getUITemplatePath(type);
		String name = entityWraper.getChildWraperByPath("name").getData().toString();
		return path + "/" + name+ "/" + "template" + ".res";
	}
	
	
	@Override
	public void afterLoad(){
		super.afterLoad();
	}

}
