package com.nosliw.common.pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.configure.HAPConfigurableImp;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPPatternManager {

	private static HAPPatternManager m_instance;

	private Map<String, HAPPatternProcessor> m_processors;

	private Map<String, HAPPatternProcessorInfo> m_processorInfos;
	
	private HAPConfigurable m_configure;

	public static HAPPatternManager getInstance(){
		return getInstance(null);
	}

	public static HAPPatternManager getInstance(HAPConfigurable configure){
		if(m_instance==null){
			m_instance=new HAPPatternManager(configure);
		}
		return m_instance;
	}
	
	public HAPPatternManager(HAPConfigurable configure){
		this.m_processorInfos = new LinkedHashMap<String, HAPPatternProcessorInfo>();
		this.m_processors = new LinkedHashMap<String, HAPPatternProcessor>();
		this.init(configure);
	}
	
	public Map<String, HAPPatternProcessor> getAllProcessors(){
		return this.m_processors;
	}
	
	public void registerPatternProcessor(HAPPatternProcessor processor){
		this.m_processors.put(processor.getName(), processor);
	}
	
	public Object process(String text, String processorName, Object obj){
		HAPPatternProcessor processor = this.getPatternProcessor(processorName);
		if(processor==null)  return null;
		return processor.parse(text, obj);
	}
	
	public HAPPatternProcessor getPatternProcessor(String name){
		return this.m_processors.get(name);
	}
	
	public HAPPatternProcessorInfo getPatterProcessorInfo(String name){
		return this.m_processorInfos.get(name);
	}
	
	public Map<String, HAPPatternProcessorInfo> getAllPatternProcesssorInfos(){
		return this.m_processorInfos;
	}
	
	private void init(HAPConfigurable configure){	
		try {
			this.initConfigure(configure);
			
			String loadMode = this.getConfigure().getStringValue("loadMode");
			if(HAPBasicUtility.isStringEmpty(loadMode)){
				loadMode = "scan";
			}
			
			//load processor infos
			Set<HAPPatternProcessorInfo> processorInfos = this.loadPatternProcessorInfos(loadMode);
			//register processor according to processorInfos
			for(HAPPatternProcessorInfo processorInfo : processorInfos){
				HAPPatternProcessor processor = processorInfo.createPatternProcessor();
				this.registerPatternProcessor(processor);
				
				this.m_processorInfos.put(processor.getName(), processorInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * load all processor infos by mode
	 * 		scan mode: scan class path and export found processor infos to file 
	 * 		file mode: load processor infos from file, if no file found, then fallback to scan mode
	 */
	private Set<HAPPatternProcessorInfo> loadPatternProcessorInfos(String loadMode){
		Set<HAPPatternProcessorInfo> processorInfos = null;
		switch(loadMode){
		case "scan":
			processorInfos = HAPPatternUtility.searchProcessors();
			try {
				HAPPatternXmlResourceUtility.exportProcessorInfos(processorInfos, new FileOutputStream(new File(this.getExportFileByConfigure())));
			} catch (FileNotFoundException e1) {				e1.printStackTrace();			}
			break;
		case "file":
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(new File(this.getExportFileByConfigure()));
				processorInfos = HAPPatternXmlResourceUtility.importProcessorInfos(inputStream);
			} catch (FileNotFoundException e) {
				//if no file found, then load using scan mode
				processorInfos = this.loadPatternProcessorInfos("scan");
			}
			break;
		}
		return processorInfos;
	}

	/*
	 * final configure will be configure softMerge with default configure(from properties file)
	 */
	private void initConfigure(HAPConfigurable configure){
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(HAPPatternUtility.class, "patternprocess.properties");
		if(input!=null)  m_configure = new HAPConfigurableImp().importFromFile(input);
		else	m_configure = new HAPConfigurableImp();
		
		if(configure!=null){
			//configure override m_configure
			this.m_configure = configure.softMerge(this.m_configure);
		}
	}
	
	private String getExportFileByConfigure(){
		return this.getConfigure().getStringValue("exportFile");
	}
	
	private HAPConfigurable getConfigure(){
		return this.m_configure;
	}
}
