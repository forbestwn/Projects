package com.nosliw.application.pattern;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nosliw.common.configure.HAPConfigurableImp;
import com.nosliw.common.configure.HAPConfigureManager;
import com.nosliw.common.pattern.HAPPatternManager;
import com.nosliw.common.pattern.HAPPatternProcessorInfo;
import com.nosliw.common.test.HAPResult;
import com.nosliw.common.test.HAPTestDescription;
import com.nosliw.common.test.HAPTestSuiteInfo;
import com.nosliw.common.test.HAPTestUtility;
import com.nosliw.common.test.export.html.HAPTestResultExporter;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPPatternProcessorApp {

	public static void main(String[] args){

		//configure
		HAPConfigurableImp configure = HAPConfigureManager.getInstance().newConfigure();
		configure.addConfigureItem("loadMode", "scan");

		InputStream input = HAPFileUtility.getInputStreamOnClassPath(HAPPatternProcessorApp.class, "patternprocess.properties");
		configure.importFromFile(input);
		
		//prepare pattern processor
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

		//create root test suite
		HAPTestSuiteInfo testSuite = new HAPTestSuiteInfo(new HAPTestDescription("PatternProcessors", "all pattern processors"));
		//create test suite for each pattern process and add to root suite
		for(HAPPatternProcessorInfo info : processorInfos){
			HAPTestSuiteInfo testSuiteInfo = HAPTestUtility.processTestSuiteClass(info.getClassName());
			if(testSuiteInfo!=null)			testSuite.addTest(testSuiteInfo);
		}
		
		HAPResult testResult = testSuite.run(null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		Date now = new Date();
	    String subfix = sdf.format(now);
	    subfix = "result";
		String file = configure.getConfigureValue("testResultPath").getStringValue()+"/"+subfix+".html";
		HAPTestResultExporter.export(testResult, file);
	}

}
