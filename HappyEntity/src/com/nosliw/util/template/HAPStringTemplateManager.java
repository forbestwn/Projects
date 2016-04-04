package com.nosliw.util.template;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.HAPApplicationManager;
import com.nosliw.application.HAPUserContext;
import com.nosliw.entity.definition.HAPEntityDefinitionManager;
import com.nosliw.entity.utils.HAPEntityUtility;

public class HAPStringTemplateManager extends HAPApplicationManager{

	private Map<String, String> m_templates;
	
	public final static String TEMPLATE_WRAPER_JSON_ATOM = "WraperJsonAtom";
	public final static String TEMPLATE_WRAPER_JSON_ENTITY = "WraperJsonEntity";
	public final static String TEMPLATE_WRAPER_JSON_CONTAINER = "WraperJsonContainer";
	public final static String TEMPLATE_WRAPER_JSON_ENTITYREFERENCE = "WraperJsonEntityReference";
	
	public HAPStringTemplateManager(HAPApplicationContext applicationContext) {
		super(applicationContext);
		applicationContext.setStringTemplateManager(this);
		this.m_templates = new LinkedHashMap<String, String>();
		this.load();
	}

	@Override
	public void load(){
		Map<String, String> names = new LinkedHashMap<String, String>();
		names.put(TEMPLATE_WRAPER_JSON_ATOM, "TemplateWraperJsonAtom.tmp");
		names.put(TEMPLATE_WRAPER_JSON_ENTITY, "TemplateWraperJsonEntity.tmp");
		names.put(TEMPLATE_WRAPER_JSON_CONTAINER, "TemplateWraperJsonContainer.tmp");
		names.put(TEMPLATE_WRAPER_JSON_ENTITYREFERENCE, "TemplateWraperJsonEntityReference.tmp");
		
		for(String name : names.keySet()){
			InputStream templateStream = HAPStringTemplateManager.class.getResourceAsStream(""+names.get(name));
			this.registerTemplateStream(name, templateStream);
		}
		
	}
	
	
	public void registerTemplate(String name, String template){
		this.m_templates.put(name, template);
	}
	
	public void registerTemplateFile(String name, String file){
		String template = HAPEntityUtility.readFile(file);
		if(HAPEntityUtility.isStringNotEmpty(template)){
			this.registerTemplate(name, template);
		}
	}

	public void registerTemplateStream(String name, InputStream stream){
		String template = HAPEntityUtility.readFile(stream);
		if(HAPEntityUtility.isStringNotEmpty(template)){
			this.registerTemplate(name, template);
		}
	}
	
	public String getStringValue(String name, Map<String, String> value){
		String template = this.m_templates.get(name);
		
		if(HAPEntityUtility.isStringNotEmpty(template)){
			for(String key : value.keySet()){
				String fill = value.get(key);
				if(fill==null)  fill = "";
				template = template.replace("||"+key+"||", fill);
			}
		}
		return template;
	}
	
}
