package com.test.business.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import com.nosliw.HAPApplicationContext;
import com.nosliw.HAPApplicationInfo;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextInfo;
import com.nosliw.configure.HAPApplicationConfigure;
import com.nosliw.configure.HAPApplicationConfigureImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.log.HAPLogger;
import com.nosliw.options.HAPOptionsManager;
import com.nosliw.query.HAPQueryInfo;
import com.nosliw.service.HAPOperationAllResult;
import com.nosliw.service.HAPServicesImp;
import com.nosliw.service.HAPWebServiceTester;
import com.nosliw.service.HAPServices;
import com.nosliw.transaction.HAPEntityLoader;
import com.nosliw.transaction.HAPEntityManager;
import com.nosliw.transaction.HAPOperationInfo;
import com.nosliw.ui.HAPUIResource;
import com.nosliw.ui.HAPUIResourceManager;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;
import com.nosliw.util.HAPUtility;
import com.nosliw.util.template.HAPStringTemplateManager;
import com.test.business.manager.ApplicationContext;
import com.test.business.manager.ApplicationManager;
import com.test.business.manager.EntityDefinitionManager;
import com.test.business.manager.UIResourceManager;
import com.test.business.manager.UserContext;
import com.test.business.manager.UserEntityLoader;
import com.test.business.util.PathUtility;
import com.webmobile.service.AppService;
import com.webmobile.service.FaceService;
import com.webmobile.service.TemplateUserContext;
import com.webmobile.service.UITemplateManager;

public class BootStrap extends ApplicationManager{

	public static void main(String[] args) throws Exception {
		ApplicationContext appContext = new ApplicationContext();
		BootStrap bs = new BootStrap(appContext);
		
		bs.init();
//		bs.runTest(appContext);

		String applicationID = "default#application.application:appgood";
			String applicationJson = AppService.getApplicationData(new HAPEntityID(applicationID), appContext);
			System.out.println(HAPJsonUtility.formatJson(applicationJson));
			
//			FaceService.readFace(null, null, appContext);
	}	
	
	
	public void runTest(ApplicationContext appContext){
		HAPServices services = appContext.getWebServices();
		HAPUserContext defaultUserContext = appContext.getUserContext("default");
		HAPUserContextInfo defaultUserContextInfo = defaultUserContext.getUserContextInfo();

		HAPEntityData faceStartWraper = (HAPEntityData)defaultUserContext.getEntityWraperRequest(new HAPEntityID("default#ui.face_start:start1000"), null).getData();
		
//		HAPQuery query = new HAPQuery("allBusiness", "test.business");
//		Set<HAPEntityWraper> entitys = services.query(query, null, defaultUserContextInfo);
//		for(HAPEntityWraper entity : entitys){
//			System.out.println(HAPJsonUtility.formatJson(entity.toStringValue(HAPConstant.FORMAT_JSON)));
//		}
		
		String transactionId = null;

//		new HAPWebServiceTester("/Users/ningwang/Desktop/WebToApp/CoreProject/Application/test_business/testscript/employee.cmd", 
//				defaultUserContext)
//			.run();;
		
		
//		HAPUIResource resource = appContext.getWebServices().getUIResource("business");
//		if(resource!=null)  System.out.println(HAPJsonUtility.formatJson(resource.toStringValue(HAPConstant.FORMAT_JSON)));

		
		
//  new entity
//			HAPEntityOperation newOperation = HAPEntityOperation.createEntityNewOperation("test.employee", false, transactionId);
//			transactionId = ((HAPOperationAllResult)services.operate(newOperation, defaultUserContext.getUserContextInfo()).getData()).getTransactionId();
//			services.commit(transactionId, defaultUserContextInfo);
		
		
//  new entity
//		HAPEntityOperation newOperation = HAPEntityOperation.createEntityNewOperation("test.business", true, null);
//		services.operate(new HAPEntityOperation[]{newOperation}, defaultUserContext.getUserContextInfo());
//		services.commit(null, defaultUserContextInfo);
		
//  delete entity		
//		HAPEntityID deleteID = new HAPEntityID("default", "test.business", "1411094836184");
//		HAPEntityOperation deleteOperation = HAPEntityOperation.createEntityDeleteOperation(deleteID, null);
//		services.operate(new HAPEntityOperation[]{deleteOperation}, defaultUserContext.getUserContextInfo());
	
//add element
//		HAPEntityID newEleID = new HAPEntityID("default", "test.business", "1411100956536");
//		HAPEntityOperation newEleOperation = HAPEntityOperation.createContainerElementNewOperation(newEleID, "employees", true, null);
//		services.operate(new HAPEntityOperation[]{newEleOperation}, defaultUserContext.getUserContextInfo());
		
//		HAPEntityOperation delEleOperation = HAPEntityOperation.createEntityAttributeOperation(newEleID, "employees", HAPConstant.OPERATION_ATTR_ELEMENT_DELETE, "1411101236138", null);
//		services.operate(new HAPEntityOperation[]{delEleOperation}, defaultUserContext.getUserContextInfo());
		
		
	}
	
	public BootStrap(ApplicationContext appContext) {
		super(appContext);
	}

	public void init()  throws Exception
	{
		InputStream configureStream=null;
		try {
			configureStream = new FileInputStream(PathUtility.getApplicationPath()+"/application.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HAPApplicationConfigure appConfigure = new HAPApplicationConfigureImp(configureStream, this.getApplicationContext());
		
		HAPServices services = new HAPServicesImp(this.getApplicationContext());
		
//		HAPStringTemplateManager stringTemplateManager = new HAPStringTemplateManager(this.getApplicationContext());
		
		if(this.getDataTypeManager()==null)  new HAPDataTypeManager(this.getApplicationContext());
		this.getDataTypeManager().init();

		HAPApplicationInfo configureMan = new HAPApplicationInfo(this.getApplicationContext());
		
		HAPOptionsManager optionMan = new HAPOptionsManager(this.getApplicationContext());

		
		if(this.getEntityDefinitionManager()==null)  new EntityDefinitionManager((ApplicationContext)this.getApplicationContext()); 
		this.getEntityDefinitionManager().init();
//		System.out.println(HAPJsonUtility.formatJson((this.getEntityDefinitionManager().toStringValue(HAPConstant.FORMAT_JSON))));
		
		HAPUIResourceManager resourceMan = new UIResourceManager(this.getApplicationContext());
//		System.out.println(HAPJsonUtility.formatJson((resourceMan.toStringValue(HAPConstant.FORMAT_JSON))));
		
		//for template user context
		TemplateUserContext templateUserContext = new TemplateUserContext((ApplicationContext)this.getApplicationContext());
		HAPEntityLoader templateLoader = new UITemplateManager(templateUserContext);
		HAPEntityManager templateEntityManager = new HAPEntityManager(templateUserContext, templateLoader);
		templateEntityManager.init();
		
		//for default user context
		UserContext userContext = new UserContext((ApplicationContext)this.getApplicationContext());
		HAPLogger logger = new FileLogger(userContext);
		HAPEntityLoader loader = new UserEntityLoader(userContext);
		HAPEntityManager entityManager = new HAPEntityManager(userContext, loader);
		entityManager.init();
		
//		System.out.print(HAPJsonUtility.formatJson(loader.toStringValue(HAPConstant.FORMAT_JSON)));
		
		this.getApplicationContext().addUserContext(templateUserContext);
		this.getApplicationContext().addUserContext(userContext);
	}
	
	private String readWidget(String name){
	     String webRootPath=
			        System.getProperty("user.home",
			          File.separatorChar + "home" +
			          File.separatorChar + "zelda") +
			          File.separatorChar;
	     return HAPUtility.readFile(webRootPath + "template/valuewraper/"+name+".html", "\n");
	}
	
}
