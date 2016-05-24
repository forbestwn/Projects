package com.nosliw.application.pattern;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.reflect.ClassPath;
import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.configure.HAPConfigurableImp;
import com.nosliw.common.pattern.HAPPatternManager;
import com.nosliw.common.pattern.HAPPatternProcessor;
import com.nosliw.common.pattern.HAPPatternProcessorInfo;
import com.nosliw.common.test.HAPTestCaseInfo;
import com.nosliw.common.test.HAPTestSuiteInfo;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPPatternProcessorApp {

	public static void main(String[] args){
		//init 
		HAPConfigurableImp configure = new HAPConfigurableImp();
		configure.addStringValue("loadMode", "scan");
		
		HAPPatternManager patternMan = HAPPatternManager.getInstance(configure);
		Map<String, HAPPatternProcessorInfo> processorsInfoMap = patternMan.getAllPatternProcesssorInfos();
		List<HAPPatternProcessorInfo> processorInfos = new ArrayList<HAPPatternProcessorInfo>();
		for(String name : processorsInfoMap.keySet()){
			processorInfos.add(processorsInfoMap.get(name));
		}
		
		//sort by name
		Collections.sort(processorInfos, new Comparator<HAPPatternProcessorInfo>() {
	        public int compare(HAPPatternProcessorInfo p1, HAPPatternProcessorInfo p2) {
	           return p1.getName().compareTo(p2.getName());
	        }
		});
		
		HAPTestSuiteInfo testSuite = new HAPTestSuiteInfo("PatternProcessors", "all pattern processors");
		for(HAPPatternProcessorInfo info : processorInfos){
			testSuite.addTestCases(testCases);
		}
		
		patternMan.getAllPatternProcesssorInfos()
		
		
		for(String name : processors.keySet()){
			boolean result = processors.get(name).test();
			String resultStr = result ? "Pass" : "Fail";
			System.out.println(resultStr + "    " + name);
			System.out.println("              " + patternMan.getPatterProcessorInfo(name).getClassName());
		}
		
	}

}
