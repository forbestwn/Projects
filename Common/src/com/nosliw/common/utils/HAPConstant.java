package com.nosliw.common.utils;

public class HAPConstant {


		public static final String CONS_SERIALIZATION_JSON = "json";
		public static final String CONS_SERIALIZATION_JSON_DATATYPE = "json_data";
		public static final String CONS_SERIALIZATION_XML = "xml";
		public static final String CONS_SERIALIZATION_TEXT = "text";



		public static final int CONS_SERVICECODE_SUCCESS = 200;
		public static final int CONS_SERVICECODE_FAILURE = 400;
		public static final int CONS_SERVICECODE_EXCEPTION = 5000;
		public static final int CONS_ERRORCODE_NEWDATAOPERATION_NOTDEFINED = 1000;
		public static final int CONS_ERRORCODE_DATAOPERATION_NOTDEFINED = 1005;
		public static final int CONS_ERRORCODE_DATAOPERATION_NOTEXIST = 1010;
		public static final int CONS_ERRORCODE_DATATYPE_WRONGTYPE = 1100;
		public static final int CONS_ERRORCODE_DATATYPE_WRONGVERSION = 1101;
		public static final int CONS_ERRORCODE_APPLICATION_INVALIDCLIENTID = 5201;
		public static final int CONS_ERRORCODE_DATA_INVALID = 1102;
		public static final int CONS_SERVICECODE_ENTITYOPERATION_FORWARD = 100;
		public static final int CONS_ERRORCODE_ENTITYOPERATION_AUTOCOMMIT = 2001;
		public static final int CONS_ERRORCODE_ENTITYOPERATION_INVALIDTRANSACTION = 2002;
		public static final int CONS_ERRORCODE_ENTITYOPERATION_INVALIDSCOPE = 2003;
		public static final int CONS_ERRORCODE_REMOTESERVICE_SUSPEND = 9000;
		public static final int CONS_ERRORCODE_REMOTESERVICE_EXCEPTION = 5000;



		public static final String CONS_OPERATIONDEF_SCRIPT_JAVASCRIPT = "javascript";
		public static final String CONS_OPERATIONDEF_PATH_VERSION = "version";
		public static final String CONS_OPERATIONDEF_PATH_PARENT = "parent";



		public static final int CONS_ENTITYOPERATION_SCOPE_UNDEFINED = -1;
		public static final int CONS_ENTITYOPERATION_SCOPE_GLOBAL = 1;
		public static final int CONS_ENTITYOPERATION_SCOPE_ENTITY = 2;
		public static final int CONS_ENTITYOPERATION_SCOPE_OPERATION = 3;



		public static final String CONS_SYMBOL_KEYWORD = "#";
		public static final String CONS_SYMBOL_GROUP = "@";
		public static final String CONS_SYMBOL_ENTITYNAME_COMMON = "..";
		public static final String CONS_SYMBOL_ENTITYNAME_CURRENT = ".";
		public static final String CONS_SEPERATOR_ELEMENT = ";";
		public static final String CONS_SEPERATOR_PART = ":";
		public static final String CONS_SEPERATOR_PATH = ".";
		public static final String CONS_SEPERATOR_FULLNAME = ".";
		public static final String CONS_SEPERATOR_DETAIL = "|";
		public static final String CONS_SEPERATOR_SURFIX = "_";
		public static final String CONS_SEPERATOR_PREFIX = "_";
		public static final String CONS_SEPERATOR_ARRAYSTART = "[";
		public static final String CONS_SEPERATOR_ARRAYEND = "]";
		public static final String CONS_SEPERATOR_VARSTART = "{{";
		public static final String CONS_SEPERATOR_VAREND = "}}";



		public static final String CONS_DATAOPERATION_NEWDATA = "new";
		public static final String CONS_DATAOPERATION_TOPARENTTYPE = "toParentType";
		public static final String CONS_DATAOPERATION_FROMPARENTTYPE = "fromParentType";
		public static final String CONS_DATAOPERATION_TOVERSION = "toVersion";
		public static final String CONS_DATAOPERATION_FROMVERSION = "fromVersion";
		public static final String CONS_DATAOPERATION_GETCHILD = "getChild";
		public static final String CONS_DATAOPERATION_GETCHILDDATATYPE = "getChildDatatype";
		public static final String CONS_DATAOPERATION_COMPARE = "compare";
		public static final String CONS_DATAOPERATION_PARSELITERAL = "parseLiteral";



		public static final int CONS_EXPRESSION_OPERAND_CONSTANT = 1;
		public static final int CONS_EXPRESSION_OPERAND_VARIABLE = 2;
		public static final int CONS_EXPRESSION_OPERAND_DATAOPERATION = 3;
		public static final int CONS_EXPRESSION_OPERAND_DATATYPEOPERATION = 4;
		public static final int CONS_EXPRESSION_OPERAND_ATTRIBUTEOPERATION = 5;
		public static final int CONS_EXPRESSION_OPERAND_PATHOPERATION = 6;
		public static final int CONS_EXPRESSION_OPERAND_NEWOPERATION = 7;



		public static final String CONS_EXPRESSION_VARIABLE_THIS = "this";
		public static final String CONS_EXPRESSION_VARIABLE_PARENT = "parent";
		public static final String CONS_EXPRESSION_VARIABLE_ENTITY = "entity";
		public static final String CONS_EXPRESSION_VARIABLE_DATA1 = "data1";
		public static final String CONS_EXPRESSION_VARIABLE_DATA2 = "data2";



		public static final String CONS_DATATYPE_CATEGARY_SIMPLE = "simple";
		public static final String CONS_DATATYPE_CATEGARY_BLOCK = "block";
		public static final String CONS_DATATYPE_CATEGARY_CONTAINER = "container";
		public static final String CONS_DATATYPE_CATEGARY_ENTITY = "entity";
		public static final String CONS_DATATYPE_CATEGARY_QUERYENTITY = "queryentity";
		public static final String CONS_DATATYPE_CATEGARY_REFERENCE = "reference";
		public static final String CONS_DATATYPE_CATEGARY_OBJECT = "object";
		public static final String CONS_DATATYPE_CATEGARY_DATA = "data";
		public static final String CONS_DATATYPE_CATEGARY_SERVICE = "service";



		public static final String CONS_DATATYPE_TYPE_INTEGER = "integer";
		public static final String CONS_DATATYPE_TYPE_FLOAT = "float";
		public static final String CONS_DATATYPE_TYPE_BOOLEAN = "boolean";
		public static final String CONS_DATATYPE_TYPE_STRING = "string";
		public static final String CONS_DATATYPE_TYPE_CONTAINER_ENTITYATTRIBUTE = "normal";
		public static final String CONS_DATATYPE_TYPE_CONTAINER_OPTIONS = "options";
		public static final String CONS_DATATYPE_TYPE_CONTAINER_QUERY = "query";
		public static final String CONS_DATATYPE_TYPE_CONTAINER_WRAPPER = "wrapper";
		public static final String CONS_DATATYPE_TYPE_REFERENCE_NORMAL = "normal";
		public static final String CONS_DATATYPE_TYPE_QUERYENTITY_NORMAL = "normal";



		public static final String CONS_UIRESOURCE_TYPE_RESOURCE = "resource";
		public static final String CONS_UIRESOURCE_TYPE_TAG = "tag";



		public static final String CONS_ENTITY_CRITICALVALUE_OTHER = "other";



		public static final String CONS_ATTRIBUTE_PATH_CRITICAL = "critical";
		public static final String CONS_ATTRIBUTE_PATH_ELEMENT = "element";
		public static final String CONS_ATTRIBUTE_PATH_ENTITY = "entity";
		public static final String CONS_ATTRIBUTE_PATH_EACH = "each";



		public static final String CONS_OPTIONS_TYPE_STATIC = "static";
		public static final String CONS_OPTIONS_TYPE_DYNAMIC = "dynamic";
		public static final String CONS_OPTIONS_TYPE_DYNAMIC_EXPRESSION = "expression";
		public static final String CONS_OPTIONS_TYPE_DYNAMIC_EXPRESSION_ATTRIBUTE = "attribute";
		public static final String CONS_OPTIONS_TYPE_QUERY = "query";
		public static final String CONS_OPTIONS_TYPE_COMPLEX = "complex";



		public static final String CONS_CONFIGUREITEM_ENTITY_ISEMPTYONINIT = "emptyOnInit";



		public static final int CONS_EVENTTYPE_ENTITY_OPERATION = 1;
		public static final int CONS_EVENTTYPE_ENTITY_MODIFY = 2;
		public static final int CONS_EVENTTYPE_ENTITY_NEW  = 3;
		public static final int CONS_EVENTTYPE_ENTITY_CLEARUP  = 4;
		public static final String CONS_EVENT_ENTITY_CHANGE  = "entityChange";



		public static final String CONS_WRAPECLEARUP_PARM_SCOPE = "scope";



		public static final int CONS_SORTING_ORDER_ASCEND = 0;
		public static final int CONS_SORTING_ORDER_DESCEND = 1;



		public static final String CONS_SORTING_TYPE_EXPRESSION = "expression";
		public static final String CONS_SORTING_TYPE_ATTRIBUTE = "attribute";
		public static final String CONS_SORTING_TYPE_PROGRAMMING = "programming";



		public static final int CONS_COMPARE_LESS = -1;
		public static final int CONS_COMPARE_LARGER = 1;
		public static final int CONS_COMPARE_EQUAL = 0;



		public static final String CONS_SERVICEDATA_METADATA_TRANSACTIONID = "transactionId";



		public static final String CONS_ENTITYOPERATION_ATTR_ATOM_SET = "ENTITYOPERATION_ATTR_ATOM_SET";
		public static final int CONS_ENTITYOPERATIONCODE_ATTR_ATOM_SET = 101;
		public static final String CONS_ENTITYOPERATION_ATTR_CRITICAL_SET = "ENTITYOPERATION_ATTR_CRITICAL_SET";
		public static final int CONS_ENTITYOPERATIONCODE_ATTR_CRITICAL_SET = 112;
		public static final String CONS_ENTITYOPERATION_ATTR_ELEMENT_NEW = "ENTITYOPERATION_ATTR_ELEMENT_NEW";
		public static final int CONS_ENTITYOPERATIONCODE_ATTR_ELEMENT_NEW = 113;
		public static final String CONS_ENTITYOPERATION_ATTR_ELEMENT_DELETE = "ENTITYOPERATION_ATTR_ELEMENT_DELETE";
		public static final int CONS_ENTITYOPERATIONCODE_ATTR_ELEMENT_DELETE = 153;
		public static final String CONS_ENTITYOPERATION_ENTITY_OPERATIONS = "ENTITYOPERATION_ENTITY_OPERATIONS";
		public static final int CONS_ENTITYOPERATIONCODE_ENTITY_OPERATIONS = 114;
		public static final String CONS_ENTITYOPERATION_ENTITY_NEW = "ENTITYOPERATION_ENTITY_NEW";
		public static final int CONS_ENTITYOPERATIONCODE_ENTITY_NEW = 102;
		public static final String CONS_ENTITYOPERATION_ENTITY_DELETE = "ENTITYOPERATION_ENTITY_DELETE";
		public static final int CONS_ENTITYOPERATIONCODE_ENTITY_DELETE = 103;
		public static final String CONS_ENTITYOPERATION_TRANSACTION_START = "ENTITYOPERATION_TRANSACTION_START";
		public static final int CONS_ENTITYOPERATIONCODE_TRANSACTION_START = 104;
		public static final String CONS_ENTITYOPERATION_TRANSACTION_COMMIT = "ENTITYOPERATION_TRANSACTION_COMMIT";
		public static final int CONS_ENTITYOPERATIONCODE_TRANSACTION_COMMIT = 105;
		public static final String CONS_ENTITYOPERATION_TRANSACTION_ROLLBACK = "ENTITYOPERATION_TRANSACTION_ROLLBACK";
		public static final int CONS_ENTITYOPERATIONCODE_TRANSACTION_ROLLBACK = 106;
		public static final String CONS_ENTITYOPERATION_QUERY_ENTITY_ADD = "ENTITYOPERATION_QUERY_ENTITY_ADD";
		public static final int CONS_ENTITYOPERATIONCODE_QUERY_ENTITY_ADD = 107;
		public static final String CONS_ENTITYOPERATION_QUERY_ENTITY_REMOVE = "ENTITYOPERATION_QUERY_ENTITY_REMOVE";
		public static final int CONS_ENTITYOPERATIONCODE_QUERY_ENTITY_REMOVE = 108;
		public static final String CONS_ENTITYOPERATION_QUERY_ENTITY_MODIFY = "ENTITYOPERATION_QUERY_ENTITY_MODIFY";
		public static final int CONS_ENTITYOPERATIONCODE_QUERY_ENTITY_MODIFY = 109;
		public static final String CONS_ENTITYOPERATION_ATTR_REFERENCE_SET = "ENTITYOPERATION_ATTR_REFERENCE_SET";
		public static final int CONS_ENTITYOPERATIONCODE_ATTR_REFERENCE_SET = 110;
		public static final String CONS_ENTITYOPERATION_ENTITYATTR_ADD = "ENTITYOPERATION_ENTITYATTR_ADD";
		public static final int CONS_ENTITYOPERATIONCODE_ENTITYATTR_ADD = 111;
		public static final String CONS_ENTITYOPERATION_ENTITYATTR_REMOVE = "ENTITYOPERATION_ENTITYATTR_REMOVE";
		public static final int CONS_ENTITYOPERATIONCODE_ENTITYATTR_REMOVE = 113;



		public static final String CONS_UIRESOURCE_ATTRIBUTE_CONTEXT = "context";
		public static final String CONS_UIRESOURCE_ATTRIBUTE_UIID = "nosliwid";
		public static final String CONS_UIRESOURCE_ATTRIBUTE_EVENT = "event";
		public static final String CONS_UIRESOURCE_ATTRIBUTE_DATABINDING = "data";
		public static final String CONS_UIRESOURCE_TAG_PLACEHOLDER = "nosliw";
		public static final String CONS_UIRESOURCE_TAG_SCRIPT = "script";
		public static final String CONS_UIRESOURCE_TAG_CONSTANT = "constants";
		public static final String CONS_UIRESOURCE_CUSTOMTAG_TAG_PREFIX = "nosliw-";
		public static final String CONS_UIRESOURCE_CUSTOMTAG_KEYATTRIBUTE_PREFIX = "nosliw-";
		public static final String CONS_UIRESOURCE_CUSTOMTAG_WRAPER_START_POSTFIX = "-tag-start";
		public static final String CONS_UIRESOURCE_CUSTOMTAG_WRAPER_END_POSTFIX = "-tag-end";
		public static final String CONS_UIRESOURCE_SCRIPTBLOCK_TOKEN_OPEN = "{";
		public static final String CONS_UIRESOURCE_SCRIPTBLOCK_TOKEN_CLOSE = "}";
		public static final String CONS_UIRESOURCE_UIEXPRESSION_TOKEN_OPEN = "<%=";
		public static final String CONS_UIRESOURCE_UIEXPRESSION_TOKEN_CLOSE = "%>";
		public static final String CONS_UIRESOURCE_EXPRESSION_TOKEN_OPEN = "#|";
		public static final String CONS_UIRESOURCE_EXPRESSION_TOKEN_CLOSE = "|#";
		public static final String CONS_UIRESOURCE_FUNCTION_EXCECUTEEXPRESSION = "excecuteExpression";
		public static final String CONS_UIRESOURCE_UIEXPRESSIONFUNCTION_PREFIX = "expression";



		public static final String CONS_UIRESOURCEMAN_SETTINGNAME_SCRIPTLOCATION = "temp.scriptLocation";



		public static final String CONS_DATATYPEMAN_SETTINGNAME_SCRIPTLOCATION = "temp.scriptLocation";



		public static final String CONS_REMOTESERVICE_LOGIN = "login";
		public static final String CONS_REMOTESERVICE_GETUIRESOURCE = "getUIResource";
		public static final String CONS_REMOTESERVICE_GETDATATYPES = "getDataTypes";
		public static final String CONS_REMOTESERVICE_EXECUTEEXPRESSION = "executeExpression";
		public static final String CONS_REMOTESERVICE_GETALLENTITYDEFINITIONS = "getAllEntityDefinitions";
		public static final String CONS_REMOTESERVICE_GETENTITYDEFINITIONBYNAMES = "getEntityDefinitionByNames";



		public static final int CONS_DATAACCESS_ENTITYSTATUS_NORMAL = 0;
		public static final int CONS_DATAACCESS_ENTITYSTATUS_CHANGED = 1;
		public static final int CONS_DATAACCESS_ENTITYSTATUS_NEW = 2;
		public static final int CONS_DATAACCESS_ENTITYSTATUS_DEAD = 3;



		public static final String CONS_APPLICATION_CONFIGURE_DATATYPE = "dataType";
		public static final String CONS_APPLICATION_CONFIGURE_ENTITYDEFINITION = "entityDefinition";
		public static final String CONS_APPLICATION_CONFIGURE_UIRESOURCE = "uiResource";
		public static final String CONS_APPLICATION_CONFIGURE_UITAG = "uiTag";
		public static final String CONS_APPLICATION_CONFIGURE_QUERYDEFINITION = "queryDefinition";
		public static final String CONS_APPLICATION_CONFIGURE_USERENV = "userEnv";
		public static final String CONS_APPLICATION_CONFIGURE_LOGGER = "logger";



		public static final String CONS_REMOTESERVICE_TASKTYPE_NORMAL = "normal";
		public static final String CONS_REMOTESERVICE_TASKTYPE_GROUP = "group";
		public static final String CONS_REMOTESERVICE_TASKTYPE_GROUPCHILD = "groupchild";



		public static final String CONS_REMOTESERVICE_GROUPTASK_MODE_ALL = "all";
		public static final String CONS_REMOTESERVICE_GROUPTASK_MODE_ALWAYS = "always";



		public static final String CONS_SERVICENAME_LOGIN = "login";
		public static final String CONS_SERVICENAME_SERVICE = "service";



		public static final String CONS_SERVICECOMMAND_GROUPREQUEST = "groupRequest";



		public static final String CONS_SCRIPTTYPE_UIRESOURCE = "uiResource";
		public static final String CONS_SCRIPTTYPE_DATAOPERATIONS = "dataOperations";
		public static final String CONS_SCRIPTTYPE_UITAGS = "uiTags";



		public static final int CONS_REFERENCE_TYPE_ABSOLUTE = 0;
		public static final int CONS_REFERENCE_TYPE_RELATIVE = 1;



		public static final String CONS_PATTERN_DATATYPEINFO = "pattern_datatypeinfo";
		public static final String CONS_PATTERN_VARIABLE = "pattern_variable";



		public static final String CONS_TESTRESULT_TYPE_SUITE = "SUITE";
		public static final String CONS_TESTRESULT_TYPE_CASE = "CASE";



		public static final String CONS_TEST_TYPE_SUITE = "SUITE";
		public static final String CONS_TEST_TYPE_CASE = "CASE";



		public static final String CONS_CATEGARY_NAME = "CONS_CATEGARY_NAME";


}
