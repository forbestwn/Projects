package com.nosliw.common.pattern;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import com.google.common.reflect.ClassPath;

public class HAPPatternUtility {

	/*
	 * search for all the patther processors classes in classpath
	 */
	public static Set<HAPPatternProcessorInfo> searchProcessors(){
		Set<HAPPatternProcessorInfo> processorInfos = new HashSet<HAPPatternProcessorInfo>();
		
		try {
			ClassLoader cl = HAPPatternUtility.class.getClassLoader();
			ClassPath classPath;
			classPath = ClassPath.from(cl);
		    Set<ClassPath.ClassInfo> classeInfos = classPath.getAllClasses();
			Class rootClass = HAPPatternProcessor.class;
		    //loop all the classes
			for(ClassPath.ClassInfo classInfo : classeInfos){
				if(classInfo.getName().contains("com.nosliw")){
					//only check nosliw package
					Class checkClass = classInfo.load();
					if(!checkClass.isInterface() && !Modifier.isAbstract(checkClass.getModifiers())){
						//only real class, no interface or abstract class
						if(rootClass.isAssignableFrom(checkClass)){
							//for Class that inherented from HAPProcessor
							HAPPatternProcessorInfo processInfo = new HAPPatternProcessorInfo();
							processInfo.setClassName(classInfo.getName());
							processorInfos.add(processInfo);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processorInfos;
	}
}
