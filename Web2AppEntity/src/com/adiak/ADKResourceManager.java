package com.adiak;

import java.io.InputStream;

public abstract class ADKResourceManager {

	abstract public InputStream getLocalHtmlStream(String file);    

	abstract public Object getLocalImageObject(String file);
	
	abstract public Object getUrlImageObject(String url) throws Exception;

	abstract public InputStream[] getEntityDefinitionInputStreams();

	abstract public InputStream[] getDataSourceInputStreams();
	abstract public InputStream[] getDataSourceContextInputStreams();
	abstract public InputStream[] getFaceInputStreams();
	abstract public InputStream[] getTemplateInputStreams();
	abstract public InputStream[] getLayoutDefInputStreams();
	abstract public InputStream[] getBorderDefInputStreams();
	
	abstract public InputStream getOrganizationInputStream();
	abstract public InputStream getLayoutConfigureInputStream();
	
}
