package com.webmobile.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.adiak.ADKApplicationContext;
import com.adiak.ADKEntityDefinitionManager;
import com.adiak.datasource.ADKDataSourceManager;
import com.adiak.datasource.test.ADKTestDataSourceProvider;
import com.nosliw.HAPApplicationInfo;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextInfo;
import com.nosliw.configure.HAPApplicationConfigure;
import com.nosliw.configure.HAPApplicationConfigureImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.HAPEntityID;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPValueFactoryImp;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.options.HAPOptionsManager;
import com.nosliw.service.HAPWebServiceImp;
import com.nosliw.service.HAPWebServices;
import com.nosliw.transaction.HAPEntityLoader;
import com.nosliw.transaction.HAPEntityManager;
import com.nosliw.transaction.HAPEntityOperation;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPUtility;
import com.nosliw.util.template.HAPStringTemplateManager;
import com.nosliw.widget.HAPWidgetManager;
import com.webmobile.manager.ApplicationContext;
import com.webmobile.manager.ApplicationManager;
import com.webmobile.manager.EntityDefinitionManager;
import com.webmobile.manager.TemplateEntityLoader;
import com.webmobile.manager.UITemplateUserContext;
import com.webmobile.manager.UserContext;
import com.webmobile.manager.UserEntityLoader;
import com.webmobile.util.PathUtility;

public class BootStrap extends ApplicationManager{

	public static void main(String[] args) {
		ApplicationContext appContext = new ApplicationContext();
		BootStrap bs = new BootStrap(appContext);
		
		bs.init();
		
		bs.runTest(appContext);
		
//		UserContext userContext = (UserContext)bs.getApplicationContext().getUserContext("default");
//		
//		String html = userContext.getFaceService().getFaceInterface("products", null);
//		System.out.println(html);
//		
//		String entityId = "datasource.test.datasource:datasource_table";
//		HAPEntityDataWraper entityWraper = (HAPEntityDataWraper)userContext.getEntityManager().getEntityWraper(entityId).getSuccessData();
//		String entityJson = entityWraper.toStringValue(HAPDataTypeManager.FORMAT_JSON_WRAPER);
//		
//		HAPWidgetManager widgetMan = bs.getApplicationContext().getWidgetManager();
//		String entityWidgets = widgetMan.toStringValue("json");
//		
//		HAPXMLValueExporter.writeData(entityWraper, null);
	}	
	
	public void runTest(ApplicationContext appContext){
		HAPWebServices services = appContext.getWebServices();
		HAPUserContext defaultUserContext = appContext.getUserContext("default");
		HAPUserContextInfo defaultUserContextInfo = defaultUserContext.getUserContextInfo();
		
//		HAPEntityID entity1 = new HAPEntityID("ui.face:1000", defaultUserContext.getId());
//		HAPEntityID entity2 = new HAPEntityID("default#ui.face:1000", defaultUserContext.getId());
//		HAPEntityWraper[] entitys = services.getEntityWrapers(new HAPEntityID[]{entity2}, null, defaultUserContext.getUserContextInfo());
//		for(HAPEntityWraper entity : entitys){
//			System.out.println(HAPJsonUtility.formatJson(entity.toStringValue(HAPConstant.FORMAT_JSON_WRAPER)));
//		}

//  new entity
//		HAPEntityOperation newOperation = HAPEntityOperation.createEntityNewOperation("test.business", true, null);
//		services.operate(new HAPEntityOperation[]{newOperation}, defaultUserContext.getUserContextInfo());
//		services.commit(null, defaultUserContextInfo);
		
//  delete entity		
//		HAPEntityID deleteID = new HAPEntityID("default", "test.business", "1411094836184");
//		HAPEntityOperation deleteOperation = HAPEntityOperation.createEntityDeleteOperation(deleteID, null);
//		services.operate(new HAPEntityOperation[]{deleteOperation}, defaultUserContext.getUserContextInfo());
	
//add element
		HAPEntityID newEleID = new HAPEntityID("default", "test.business", "1411100956536");
//		HAPEntityOperation newEleOperation = HAPEntityOperation.createContainerElementNewOperation(newEleID, "employees", true, null);
//		services.operate(new HAPEntityOperation[]{newEleOperation}, defaultUserContext.getUserContextInfo());
		
		HAPEntityOperation delEleOperation = HAPEntityOperation.createEntityAttributeOperation(newEleID, "employees", HAPConstant.OPERATION_ATTR_ELEMENT_DELETE, "1411101236138", null);
		services.operate(new HAPEntityOperation[]{delEleOperation}, defaultUserContext.getUserContextInfo());
		
		
	}
	
	public BootStrap(ApplicationContext appContext) {
		super(appContext);
	}

	public void init(){
		InputStream configureStream=null;
		try {
			configureStream = new FileInputStream(PathUtility.getApplicationPath()+"/application.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HAPApplicationConfigure appConfigure = new HAPApplicationConfigureImp(configureStream, this.getApplicationContext());
		
		HAPWebServices services = new HAPWebServiceImp(this.getApplicationContext());
		
		HAPStringTemplateManager stringTemplateManager = new HAPStringTemplateManager(this.getApplicationContext());
		
		if(this.getDataTypeManager()==null)  new HAPDataTypeManager(this.getApplicationContext());
		this.getDataTypeManager().init();

		HAPApplicationInfo configureMan = new HAPApplicationInfo(this.getApplicationContext());
		
		HAPOptionsManager optionMan = new HAPOptionsManager(this.getApplicationContext());

		HAPWidgetManager widgetMan = new HAPWidgetManager(this.getApplicationContext());
		String[] widgets = {};
		for(String widget : widgets){
			widgetMan.registerWidget(widget, this.readWidget(widget));
		}
		
		
		if(this.getEntityDefinitionManager()==null)  new EntityDefinitionManager((ADKApplicationContext)this.getApplicationContext()); 
		this.getEntityDefinitionManager().init();
//		System.out.println(this.getEntityDefinitionManager().toStringValue(HAPAttributeConstant.FORMAT_JSON));
		

		
		//for template user context
		UITemplateUserContext templateUserContext = new UITemplateUserContext((ApplicationContext)this.getApplicationContext());
		if(templateUserContext.getValueFactory()==null)  new HAPValueFactoryImp(templateUserContext);
		TemplateEntityLoader templateLoader = new TemplateEntityLoader(templateUserContext);
		HAPEntityManager templateEntityManager = new HAPEntityManager(templateUserContext, templateLoader);
		templateEntityManager.init();
		this.getApplicationContext().addUserContext(templateUserContext);
		
		
		//for default user context
		UserContext userContext = new UserContext((ApplicationContext)this.getApplicationContext());
		if(userContext.getValueFactory()==null)  new HAPValueFactoryImp(userContext);
		HAPEntityLoader loader = new UserEntityLoader(userContext);
		HAPEntityManager entityManager = new HAPEntityManager(userContext, loader);
		entityManager.init();
		
		ADKDataSourceManager dataSourceMan = new ADKDataSourceManager(userContext);
		ADKTestDataSourceProvider provider = new ADKTestDataSourceProvider(userContext);
		dataSourceMan.registerDataSourceProvider(provider);
		
		AppService appService = new AppService(userContext);
		FaceService faceService = new FaceService(userContext);
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


