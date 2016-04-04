package com.web2app.io;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.adiak.ADKResourceManager;
import com.adiak.ADKUserContext;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.xmlimp.entity.HAPXMLEntityImporter;
import com.web2app.entity.datasource.DataSourceContext;

public class EntityXMLImporter extends HAPXMLEntityImporter{
	


//	public void init()
//	{
//		try {
//			DocumentBuilderFactory DOMfactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder DOMbuilder = DOMfactory.newDocumentBuilder();
//			
//			InputStream[] steams = this.m_systemContext.getResourceManager().getFaceInputStreams();
//			for(int i=0; i<steams.length; i++)
//			{
//				Document faceDoc = DOMbuilder.parse(steams[i]);
//				this.readFaces(faceDoc);
//			}
//		} catch (Exception e) {    
//			e.printStackTrace();  
//		}
//	}   
//	
//	
//	private void readFaces(Document doc)
//	{
//		NodeList sources = doc.getElementsByTagName(TAG_FACE);
//		for(int i=0; i<sources.getLength(); i++)
//		{
//			Element node = (Element)sources.item(i);
//			ADKFace face = (ADKFace)HAPValueWraper.createConfigurableComplexEntity(node, this.m_systemContext.getEntityDefinitionManager()).getComplexEntity();
//			String name = face.getAtomAttributeValue(ADKFace.ATTRIBUTE_NAME);
//			if(name==null)  name = DataSourceContext.CONTEXT_DEFAULT;
//			this.m_faces.put(name, face);
//		} 
//	}
	
	private ADKUserContext m_systemContext; 
	public void setEntitySystemManager(ADKUserContext sys){this.m_systemContext = sys;}
	protected ADKUserContext getSystemContext(){return this.m_systemContext;}

	protected ADKResourceManager getResourceManager(){return this.m_systemContext.getResourceManager();}
	
}
