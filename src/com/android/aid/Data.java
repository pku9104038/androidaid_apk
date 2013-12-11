/**
 * 
 */
package com.android.aid;

/**
 * @author wangpeifeng
 *
 */
public class Data {
	
	public static final String APP_REQUEST = "com.android.aid.app_request";
	public static final String INTENT_ACTION = "com.android.aid.action";
	public static final String INTENT_ACTION_DOWNLOAD_REPORTER = "com.android.aid.action.download_reporter";
	public static final String INTENT_ACTION_INSTALL_REPORTER = "com.android.aid.action.install_reporter";
	public static final String INTENT_ACTION_PACKAGE_CHANGE = "com.android.aid.action.package_change";
	public static final String INTENT_ACTION_START_DOWNLOADING = "com.android.aid.action.start_downloading";
	public static final String INTENT_ACTION_LAUNCH_REPORTER = "com.android.aid.action.launch_reporter";
	public static final String INTENT_ACTION_RECENT_TASK = "com.android.aid.action.recent_task";
	
	public static final String INTENT_DATA_PACKAGE = "com.android.aid.data.package";
	public static final String INTENT_DATA_LIST_INDEX = "list_index";
	public static final String INTENT_DATA_APP_LABEL = "app_label";
	
	public static final int ACTIVITY_REQUEST_MY_CATEGORY = 1;
	
	
	public static final int REQUEST_IM = 0;
	public static final int REQUEST_GAME = REQUEST_IM+1;
	public static final int REQUEST_MUSIC = REQUEST_GAME+1;
	public static final int REQUEST_VIDEO = REQUEST_MUSIC+1;
	public static final int REQUEST_SHOPPING = REQUEST_VIDEO+1;
	public static final int REQUEST_TRANSPORT = REQUEST_SHOPPING+1;
	public static final int REQUEST_WEATHER = REQUEST_TRANSPORT+1;
	public static final int REQUEST_WEB = REQUEST_WEATHER+1;
	public static final int REQUEST_READING = REQUEST_WEB+1;
	public static final int REQUEST_TOOLS = REQUEST_READING+1;
	public static final int REQUEST_PICTURE = REQUEST_TOOLS+1;
	public static final int REQUEST_LIFE = REQUEST_PICTURE+1;
	public static final int REQUEST_CAMERA = REQUEST_LIFE+1;
	public static final int REQUEST_NEWS = REQUEST_CAMERA+1;
	public static final int REQUEST_RECENT = REQUEST_NEWS+1;
	public static final int REQUEST_MY = REQUEST_RECENT+1;
	
	public static final String SERVICE_REQUEST_APP_CLOUD = "app_cloud";
	public static final String SERVICE_REQUEST_APP_DOWNLOADED = "app_downloaded";
	public static final String SERVICE_REQUEST_APP_INSTALLED = "app_installed";
	public static final String SERVICE_REQUEST_APP_DOWNLOADING = "app_downloading";
	public static final String SERVICE_REQUEST_DOWNLOAD_REPORTER = "download_reporter";
	public static final String SERVICE_REQUEST_PACKAGE_CHANGED = "package_change";
	public static final String SERVICE_REQUEST_INSTALL_REPORTER = "install_reporter";
	public static final String SERVICE_REQUEST_LAUNCH_REPORTER = "launch_reporter";
	public static final String SERVICE_REQUEST_RECENT_TASK = "recent_task";
	public static final String SERVICE_REQUEST_CURRENT_TASK_START = "current_task_start";
	public static final String SERVICE_REQUEST_CURRENT_TASK_STOP = "current_task_stop";

	
	public static final int HANDLER_MSG_WHAT_DOWNLOADING = 1;
	public static final int HANDLER_MSG_WHAT_RENEW = HANDLER_MSG_WHAT_DOWNLOADING + 1;
	public static final int HANDLER_MSG_WHAT_WAITING = HANDLER_MSG_WHAT_RENEW + 1;
	public static final int HANDLER_MSG_WHAT_NULL = HANDLER_MSG_WHAT_WAITING + 1;
	

	
	public static final String ACTIVITY_IM = "社交聊天";
	public static final String ACTIVITY_GAME = "��������";
	public static final String ACTIVITY_MUSIC = "��������";
	public static final String ACTIVITY_VIDEO = "ϵͳ����";
	public static final String ACTIVITY_SHOPPING = "����ͨ��";
	public static final String ACTIVITY_TRANSPORT = "��֧��";
	public static final String ACTIVITY_WEATHER = "�Ķ���֪";
	public static final String ACTIVITY_WEB = "��������";
	public static final String ACTIVITY_READING = "�����黭";
	public static final String ACTIVITY_TOOLS = "��ͨ����";
	public static final String ACTIVITY_PICTURE = "�Ժ�����";
	public static final String ACTIVITY_LIFE = "����ҽ��";
	public static final String ACTIVITY_CAMERA = "����";
	public static final String ACTIVITY_NEWS = "��������";
	public static final String ACTIVITY_RECENT = "�����Ƽ�";
	public static final String ACTIVITY_MY = "�ҵ�Ӧ��";
/*	
	public static final String[] ACTIVITY_ARRAY = {
		ACTIVITY_IM,
		ACTIVITY_GAME,
		ACTIVITY_MUSIC,
		ACTIVITY_VIDEO,
		ACTIVITY_SHOPPING,
		ACTIVITY_TRANSPORT,
		ACTIVITY_WEATHER,
		ACTIVITY_WEB,
		ACTIVITY_READING,
		ACTIVITY_TOOLS,
		ACTIVITY_PICTURE,
		ACTIVITY_LIFE,
		ACTIVITY_CAMERA,
		ACTIVITY_NEWS,
		ACTIVITY_RECENT,
		ACTIVITY_MY
		
	};
*/	
	

	//database
	public static final String DB_DATABASE = "androidaid";
	
	public static final String DB_AUTHORITY ="com.android.aid";
	
	public static final int DB_VERSION_1 = 1;
	public static final int DB_VERSION_2 = 2;
	/* DB_VERSION_2 
	 * 2012-08-04 from verCode 10
	 * Add download_reporter Table 
	 */
	public static final int DB_VERSION_3 = 3;
	/* DB_VERSION_2 
	 * 2012-08-06 from verCode 10
	 * Add install_reporter Table 
	 */
	public static final int DB_VERSION_4 = 4;
	/* DB_VERSION_4 
	 * 2012-08-010 from verCode 13
	 * Add app-category in app_my table
	 */
	
	public static final int DB_VERSION_5 = 5;
	/* DB_VERSION_5
	 * 2012-08-010 from verCode 15
	 * Add launch_reporter
	 */
	
	public static final int DB_VERSION = DB_VERSION_5;
	
	public static final String DB_COLUMN_ROWID = "ROWID";	
	
	//database data type
	public static final String DB_DATA_TYPE_TEXT = "TEXT";
	public static final String DB_DATA_TYPE_INT = "INTEGER";
	public static final String DB_DATA_TEXT_VALUE_NULL = "NULL";
	public static final String DB_DATA_INT_VALUE_ZERO = "0";
	// use TEXT for all data to simplify database and http operation
	
	//database tables
	public static final String DB_TABLE_WIDGET_CONFIG = "widget_config";
	public static final String DB_TABLE_APP_REPOSITORY = "app_repository";
	public static final String DB_TABLE_APP_INSTALLED = "app_installed";
	public static final String DB_TABLE_APP_DOWNLOADED = "app_downloaded";
	public static final String DB_TABLE_APP_STATE_MONITOR = "app_state_monitor";
	public static final String DB_TABLE_APP_DOWNLOADING = "app_downloading";
	public static final String DB_TABLE_APP_MY = "app_my";
	public static final String DB_TABLE_CATEGORY_MANAGER = "category_manager";
	
	//uri for DataProvider interface
	public static final String URI_HEADER = "content://"+DB_AUTHORITY;
	public static final String URI_LIMIT_1 = "?"	+ "1";
	public static final String URI_APP_REPOSITORY = 			"/"	+	DB_TABLE_APP_REPOSITORY;
	public static final String URI_APP_INSTALLED = 			"/"	+	DB_TABLE_APP_INSTALLED;
	public static final String URI_APP_DOWNLOADED = 			"/"	+	DB_TABLE_APP_DOWNLOADED;
	public static final String URI_APP_STASTE_MONITOR = 			"/"	+	DB_TABLE_APP_STATE_MONITOR;
	public static final String URI_APP_DOWNLOADING = 			"/"	+	DB_TABLE_APP_DOWNLOADING;
	public static final String URI_APP_MY = 			"/"	+	DB_TABLE_APP_MY;
	public static final String URI_WIDGET_CONFIG = 			"/"	+	DB_TABLE_WIDGET_CONFIG;
	public static final String URI_CATEGORY_MANAGER = 			"/"	+	DB_TABLE_CATEGORY_MANAGER;
	

	//table constructor

	//app_repository
	//table columns
	public static final String DB_COLUMN_APP_CATEGORY = "app_category";
	public static final String DB_COLUMN_APP_PACKAGE = "app_package";
	public static final String DB_COLUMN_APP_LABEL = "app_label";
	public static final String DB_COLUMN_APP_FILE = "app_file";
	public static final String DB_COLUMN_APP_FILE_ORIGINAL = "app_file_original";
	public static final String DB_COLUMN_APP_URL = "app_url";
	public static final String DB_COLUMN_APP_ICON_FILE = "app_icon_file";
	public static final String DB_COLUMN_APP_ICON_URL = "app_icon_url";
	public static final String DB_COLUMN_APP_VERSION_NAME = "app_version_name";
	public static final String DB_COLUMN_APP_VERSION_CODE = "app_version_code";
	public static final String DB_COLUMN_APP_OWNER = "app_owner";
	public static final String DB_COLUMN_APP_VENDOR = "app_vendor";
	public static final String DB_COLUMN_APP_AGENT = "app_agent";
	public static final String DB_COLUMN_APP_STATE = "app_state";
	public static final String DB_COLUMN_APP_PRICE = "app_price";
	public static final String DB_COLUMN_APP_PRICING_POLICY = "app_pricing_policy";
	public static final String DB_COLUMN_APP_DEPLOY_DATE = "app_deploy_date";
	public static final String DB_COLUMN_APP_UPDATE_STAMP = "app_update_stamp";
	public static final String DB_COLUMN_UPDATE_FLAG = "update_flag";
	
	//columns value
	public static final String DB_VALUE_CATEGORY_IM = "�罻����";
	public static final String DB_VALUE_CATEGORY_GAME = "��������";
	public static final String DB_VALUE_CATEGORY_MUSIC = "��������";
	public static final String DB_VALUE_CATEGORY_VIDEO = "ϵͳ����";
	public static final String DB_VALUE_CATEGORY_SHOPPING = "����ͨ��";
	public static final String DB_VALUE_CATEGORY_TRANSPORT = "��֧��";
	public static final String DB_VALUE_CATEGORY_WEATHER = "�Ķ���֪";
	public static final String DB_VALUE_CATEGORY_WEB = "��������";
	public static final String DB_VALUE_CATEGORY_READING = "�����黭";
	public static final String DB_VALUE_CATEGORY_TOOLS = "��ͨ����";
	public static final String DB_VALUE_CATEGORY_PICTURE = "�Ժ�����";
	public static final String DB_VALUE_CATEGORY_LIFE = "����ҽ��";
	public static final String DB_VALUE_CATEGORY_CAMERA = "����";
	public static final String DB_VALUE_CATEGORY_NEWS = "��������";

	public static final String DB_VALUE_CATEGORY_ICON = "icon";
	public static final String DB_VALUE_CATEGORY_ANDROIDAID = "androidaid";

	public static final String DB_VALUE_CATEGORY_MY = "�ҵ�Ӧ��";	
	public static final String DB_VALUE_CATEGORY_ALL = "all";	
	public static final String DB_VALUE_CATEGORY_NULL = "ȡ���Զ���";	
	
	public static final String DB_VALUE_VERSION_UPDATE = "���׽�����";	
	
	public static final String DB_VALUE_FLAG_DELETE = "delete";	
	public static final String DB_VALUE_FLAG_UPDATING = "updating";	
	public static final String DB_VALUE_FLAG_NULL = "null";	
	public static final String DB_VALUE_DOWNLOADING_SUFFIX = ".sav";	
	public static final String DB_VALUE_ICON_SUFFIX = ".png";	
	
	public static final String DB_VALUE_PACKAGE_ANDROIDAID = "com.android.aid";	
//	public static final String DB_VALUE_LABEL_ANDROIDAID = "Android �׽�����";	
	
	public static final String DB_VALUE_LOCATION_CLOUD = "cloud";	
	public static final String DB_VALUE_LOCATION_DOWNLOADED = "downloaded";	
	public static final String DB_VALUE_LOCATION_INSTALLED = "installed";	
	public static final String DB_VALUE_LOCATION_DOWNLOADING = "downloading";	
	public static final String DB_VALUE_LOCATION_INTERNAL = "internal";	
	
	public static final String DB_VALUE_STATE_ONLINE = "Online";	
	public static final String DB_VALUE_STATE_OFFLINE = "Offline";	
	
	
	//table structure
	public static final String[] Db_Table_App_Repository = {
		DB_COLUMN_APP_CATEGORY
		,DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_FILE
		,DB_COLUMN_APP_URL
		,DB_COLUMN_APP_VERSION_NAME
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_APP_ICON_FILE
		,DB_COLUMN_APP_ICON_URL
		,DB_COLUMN_APP_OWNER
		,DB_COLUMN_APP_VENDOR	
		,DB_COLUMN_APP_AGENT
		,DB_COLUMN_APP_STATE
		,DB_COLUMN_APP_PRICE
		,DB_COLUMN_APP_PRICING_POLICY	
		,DB_COLUMN_APP_DEPLOY_DATE
		,DB_COLUMN_APP_UPDATE_STAMP
		,DB_COLUMN_UPDATE_FLAG
		};
	

	//app_installed
	//table columns
	public static final String DB_COLUMN_APP_FIRST_INSTALLED_TIME = "app_install_time";
	public static final String DB_COLUMN_APP_LATEST_UPDATE_TIME = "app_update_time";
	
	//table structure
	public static final String[] Db_Table_App_Installed = {
		DB_COLUMN_APP_CATEGORY
		,DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_VERSION_NAME
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_APP_FIRST_INSTALLED_TIME
		,DB_COLUMN_APP_LATEST_UPDATE_TIME
		,DB_COLUMN_UPDATE_FLAG
		};
	

	//app_downloaded
	//table columns
	public static final String DB_COLUMN_APP_FILE_PATH = "app_file_path";
	
	//table structure
	public static final String[] Db_Table_App_Downloaded = {
		DB_COLUMN_APP_CATEGORY
		,DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_VERSION_NAME
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_APP_FILE_PATH
		,DB_COLUMN_UPDATE_FLAG
		};
	

	
		//app_state_monitor
		//table columns
		public static final String DB_COLUMN_APP_LOCATION = "app_location";
	
	
		
		//table structure
		public static final String[] Db_Table_App_State_Monitor = {
			DB_COLUMN_APP_CATEGORY
			,DB_COLUMN_APP_PACKAGE
			,DB_COLUMN_APP_LABEL
			,DB_COLUMN_APP_LOCATION
			,DB_COLUMN_APP_VERSION_NAME
			,DB_COLUMN_APP_VERSION_CODE
			,DB_COLUMN_APP_FILE
			,DB_COLUMN_APP_URL
			,DB_COLUMN_APP_FIRST_INSTALLED_TIME
			,DB_COLUMN_APP_LATEST_UPDATE_TIME
			,DB_COLUMN_UPDATE_FLAG
			};
	
		
		//app_downloading
		//table columns
	
		public static final String DB_COLUMN_APP_FILE_LENGTH = "app_file_length";	
		public static final String DB_COLUMN_APP_DOWNLOAD_LENGTH = "app_download_length";	
		public static final String DB_COLUMN_APP_DOWNLOAD_START_TIME = "app_download_start";	
		public static final String DB_COLUMN_APP_DOWNLOAD_STOP_TIME = "app_download_stop";	
		public static final String DB_COLUMN_APP_DOWNLOAD_STATE = "app_download_state";	
		public static final String DB_COLUMN_APP_DOWNLOAD_SILENCE = "app_download_silence";	
		
		public static final String DB_VALUE_DOWNLOAD_DOWNLOADING = "downloading";	
		public static final String DB_VALUE_DOWNLOAD_DOWNLOADED = "downloaded";	
		public static final String DB_VALUE_DOWNLOAD_CANCEL = "cancel";	
		public static final String DB_VALUE_DOWNLOAD_PAUSE = "pause";	
		public static final String DB_VALUE_YES = "yes";	
		public static final String DB_VALUE_NO = "no";	
		public static final int    DB_VALUE_QUERY_DOWNLOADING = 1;	
		public static final int    DB_VALUE_QUERY_DOWNLOADING_PAUSE = 2;	
		public static final int    DB_VALUE_QUERY_DOWNLOADING_PAUSE_SILENCE = 3;	
		public static final int    DB_VALUE_QUERY_DOWNLOADING_SILENCE = 4;	
		
		
		//table structure
		public static final String[] Db_Table_App_Downloading = {
			DB_COLUMN_APP_PACKAGE
			,DB_COLUMN_APP_LABEL
			,DB_COLUMN_APP_VERSION_CODE
			,DB_COLUMN_APP_FILE
			,DB_COLUMN_APP_URL
			,DB_COLUMN_APP_FILE_PATH
			,DB_COLUMN_APP_FILE_LENGTH
			,DB_COLUMN_APP_DOWNLOAD_LENGTH
			,DB_COLUMN_APP_DOWNLOAD_START_TIME
			,DB_COLUMN_APP_DOWNLOAD_STOP_TIME
			,DB_COLUMN_APP_DOWNLOAD_STATE
			,DB_COLUMN_APP_DOWNLOAD_SILENCE
			};
		

		//app_my
		//table columns
	
		
		//table structure
		public static final String[] Db_Table_App_My = {
			DB_COLUMN_APP_PACKAGE
			,DB_COLUMN_APP_LABEL
			,DB_COLUMN_APP_CATEGORY // add from DB_VERSION_4
			,DB_COLUMN_UPDATE_FLAG
			};
		

		
	//device_register
	public static final String DB_COLUMN_DEVICE_IMEI = "device_imei";
	public static final String DB_COLUMN_DEVICE_BRAND = "device_brand";
	public static final String DB_COLUMN_DEVICE_MODEL = "device_model";
	public static final String DB_COLUMN_DEVICE_SDK = "device_sdk";
	public static final String DB_COLUMN_DEVICE_RELEASE = "device_release";
	
	//version_repository
	public static final String DB_COLUMN_VERSION_CODE = "version_code";


	
	//category_manager
	//table columns
	public static final String DB_COLUMN_CATEGORY_SERIAL = "category_serial";
//	public static final String DB_COLUMN_APP_CATEGORY = "app_category";
	public static final String DB_COLUMN_CATEGORY_DIR = "category_dir";
	public static final String DB_COLUMN_CATEGORY_STATUS = "category_status";
	public static final String DB_COLUMN_CATEGORY_MAPPING = "category_mapping";
	public static final String DB_COLUMN_CATEGORY_STAMP = "category_stamp";

	public static final String DB_VALUE_CATEGORY_DIR_ICON = "icon/";
	public static final String DB_VALUE_CATEGORY_DIR_ANDROIDAID = "androidaid/";
	public static final String DB_VALUE_CATEGORY_DIR_REPOSITORY = "repository/";
	public static final String DB_VALUE_CATEGORY_DIR_ONLINE = "online/";

	
	//table structure
	public static final String[] Db_Table_Category_Manager = {
		DB_COLUMN_CATEGORY_SERIAL
		,DB_COLUMN_APP_CATEGORY
		,DB_COLUMN_CATEGORY_DIR
		,DB_COLUMN_CATEGORY_STATUS
		,DB_COLUMN_CATEGORY_MAPPING
		,DB_COLUMN_UPDATE_FLAG
		,DB_COLUMN_CATEGORY_STAMP
	};


	//version_repository
	//table columns
	public static final String DB_COLUMN_VERSION_FILE = "version_file";

	
	
	//download_reporter
	// table name 
	public static final String DB_TABLE_DOWNLOAD_REPORTER = "download_reporter";
	
	public static final String URI_DOWNLOAD_REPORTER = 			"/"	+	DB_TABLE_DOWNLOAD_REPORTER;
	
	//table columns

	//table structure
	public static final String[] Db_Table_Download_Reporter = {
		DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_APP_URL
		,DB_COLUMN_APP_DOWNLOAD_START_TIME
		,DB_COLUMN_APP_DOWNLOAD_STOP_TIME
		
	};

	
	//install_reporter
	// table name 
	public static final String DB_TABLE_INSTALL_REPORTER = "install_reporter";
	
	public static final String URI_INSTALL_REPORTER = 			"/"	+	DB_TABLE_INSTALL_REPORTER;
	
	//table columns
	public static final String DB_COLUMN_INSTALL_ACTION = "install_action";
	public static final String DB_COLUMN_INSTALL_ACTION_TIME = "install_action_time";

	//columns value
	public static final String DB_VALUE_INSTALL_ADD = "add";
	public static final String DB_VALUE_INSTALL_REMOVE = "remove";
	public static final String DB_VALUE_INSTALL_REPLACE = "replace";

	//table structure
	public static final String[] Db_Table_Install_Reporter = {
		
		DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_INSTALL_ACTION
		,DB_COLUMN_INSTALL_ACTION_TIME
		
	};

	
	//launch_reporter
	// table name 
	public static final String DB_TABLE_LAUNCH_REPORTER = "launch_reporter";
	
	public static final String URI_LAUNCH_REPORTER = 			"/"	+	DB_TABLE_LAUNCH_REPORTER;
	
	//table columns
	public static final String DB_COLUMN_LAUNCH_TIME = "launch_time";
	public static final String DB_COLUMN_LAUNCH_BY_ME = "launch_by_me";
	
	//columns value
	
	//table structure
	public static final String[] Db_Table_Launch_Reporter = {
		
		DB_COLUMN_APP_PACKAGE
		,DB_COLUMN_APP_LABEL
		,DB_COLUMN_APP_VERSION_CODE
		,DB_COLUMN_LAUNCH_BY_ME
		,DB_COLUMN_LAUNCH_TIME
		
	};

		
/*	
	//widget_config
	//table columns
	public static final String DB_COLUMN_WIDGET_VERSION = "widget_version";
	public static final String DB_COLUMN_WIDGET_FILE = "widget_file";
	public static final String DB_COLUMN_WIDGET_URL = "widget_url";
	public static final String DB_COLUMN_WIDGET_COVERAGE = "widget_coverage";
	public static final String DB_COLUMN_WIDGET_CUSTOMER = "widget_customer";
	public static final String DB_COLUMN_WIDGET_CUSTOMER_BRAND = "widget_customer_brand";
	public static final String DB_COLUMN_WIDGET_CUSTOMER_MODEL = "widget_customer_model";
	public static final String DB_COLUMN_WIDGET_UPDATE_STAMP = "widget_update_stamp";
	//table structure
	public static final String[] Db_Table_Widget_Config = {
		DB_COLUMN_WIDGET_VERSION
		,DB_COLUMN_WIDGET_FILE
		,DB_COLUMN_WIDGET_URL
		,DB_COLUMN_WIDGET_COVERAGE
		,DB_COLUMN_WIDGET_CUSTOMER
		,DB_COLUMN_WIDGET_CUSTOMER_BRAND
		,DB_COLUMN_WIDGET_CUSTOMER_MODEL
		,DB_COLUMN_WIDGET_UPDATE_STAMP
	};
	
*/
	

	//table index via tables name string
	/*
	public static final String[][] Db_Table_Name = {
		{ // db version 0 null
			null
		}
		
		,{ // db version 1
			DB_TABLE_APP_REPOSITORY
			,DB_TABLE_APP_INSTALLED
			,DB_TABLE_APP_DOWNLOADED
			,DB_TABLE_APP_STATE_MONITOR
			,DB_TABLE_APP_DOWNLOADING
			,DB_TABLE_APP_MY
			,DB_TABLE_CATEGORY_MANAGER
		}
		
	};
	*/
	/*
	//table object list
	public static final Object[] Db_Table_Object = {
		Db_Table_App_Repository
		,Db_Table_App_Installed
		,Db_Table_App_Downloaded
		,Db_Table_App_State_Monitor
		,Db_Table_App_Downloading
		,Db_Table_App_My
//		,Db_Table_Widget_Config
		,Db_Table_Category_Manager
	};
	*/
	
	
	//api url
	//public static final String API_URL = "http://www.3gstone.net:8088/androidaid/php/api/";
	//public static final String DOWNLOAD_URL = "http://www.3gstone.net:8088/androidaid/download/";
	public static final String API_URL = "http://www.namo.com.cn:8088/androidaid/php/api/";
	public static final String DOWNLOAD_URL = "http://www.namo.com.cn:8088/androidaid/download/";
	
	//api version
	public static final String API_VERSION = "api_version";
	
	public static final String API_VERSION_VALUE_1 = "1.0";
	
	public static final String API_VERSION_VALUE = API_VERSION_VALUE_1;
	
	//api method
	//public static final String ANDROIDAID_CONFIG_ADDRESS = "http://config.3gstone.net:8088/config/";
	public static final String ANDROIDAID_CONFIG_ADDRESS = "http://www.namo.com.cn:8088/config/";
	public static final String ANDROIDAID_CONFIG_FILE = "androidaid_cfg.html";
	public static final String ANDROIDAID_VERSION_FILE = "androidaid_ver.html";
	public static final String ANDROIDAID_APP_REPO_FILE = "app_repo.json";
	public static final String ANDROIDAID_CATEGORY_FILE = "category_manager.json";
	
	//config key
	public static final String CONFIG_API_URL = "api_url";
	public static final String CONFIG_API_DOMAIN = "api_domain";
	public static final String CONFIG_API_PORT = "api_port";
	public static final String CONFIG_API_ROOT = "api_root";
	public static final String CONFIG_DOWNLOAD_URL = "download_url";
	public static final String CONFIG_DOWNLOAD_DOMAIN = "download_domain";
	public static final String CONFIG_DOWNLOAD_PORT = "download_port";
	public static final String CONFIG_DOWNLOAD_ROOT = "download_root";
	
	
	
	//public static final String API_APP_REPO_QUERY = "app_repo_query.php";
	//public static final String API_APP_REPO_QUERY = "app_repo_query_apkstore.php";
	public static final String API_APP_REPO_QUERY = "app_repo_query_apkstore2.php";
	
	public static final String API_APP_ONLINE_QUERY = "app_online_query.php";
	public static final String API_APP_CATEGORY_QUERY = "app_category_query.php";
	public static final String API_VERSION_REPO_QUERY = "version_repo_query.php";
	public static final String API_CATEGORY_MANAGER_QUERY = "category_manager_query.php";
	public static final String API_DOWNLOAD_REPORTER = "download_reporter.php";
	public static final String API_INSTALL_REPORTER = "install_reporter.php";
	public static final String API_LAUNCH_REPORTER = "launch_reporter.php";
	
	
	//api params
	public static final String API_JSON_APP_ARRAY = "app_array";
	public static final String API_JSON_CATEGORY_ARRAY = "category_array";
	public static final String API_JSON_RESPONSE = "api_response";
			
	//api value
	public static final boolean API_JSON_RESPONSE_TRUE = true;
	public static final boolean API_JSON_RESPONSE_FALSE = false;
	
	
	
	public static final int TIMER_SECOND = 1000;
	public static final int TIMER_SERVICE_SLEEP = 60*5;
	
	public static final String APP_ADD_MY = "�Զ������";
	public static final String APP_DOWNLOADING = "���ع���";
	public static final String APP_DATABASE = "��ݲ�ѯ";
	public static final String APP_SYNCHRONIZATION = "���¼��";
	public static final String APP_SYNCHRONIZATION_CONFIG = "���ø��¼��";
	public static final String APP_SYNCHRONIZATION_REPO = "��ݸ��¼��";
	public static final String APP_SYNCHRONIZATION_VERSION = "�汾���¼��";
	public static final String APP_ADD_MY_CATEGORY = "�Զ������";
	
	public static final String APP_ADD_MY_TITLE = "�Զ���Ӧ�ù���";
	public static final String APP_DOWNLOADING_TITLE = "�ļ����ع���";
	public static final String APP_DATABASE_TITLE  = "��ݹ���";
	public static final String APP_SYNCHRONIZATION_TITLE  = "������";
	public static final String APP_ADD_MY_CATEGORY_TITLE = "����Ӧ�÷���:";
	
	
	
	public static final String TOAST_NEW_VERSOIN_READY = "���\"" + DB_VALUE_VERSION_UPDATE +"\",��������Ӧ�ã�";
	public static final String TOAST_SYNC_SERVICRE_START = "��ݸ�����...��ɼ����������!";
	
	public static final String FILE_SHAREDPREFERENCES = "myPreferences";
	
	public static final String FILE_KEY_CLOUD_SYNC_DATE = "cloud_sync_date";
	public static final String FILE_KEY_CLOUD_SYNC_MODE = "cloud_sync_date";
	public static final String FILE_KEY_LATEST_TASK_PACKAGE = "latest_task_package";

	public static final String FILE_VALUE_CLOUD_SYNC_AUTO = "cloud_sync_auto";
	public static final String FILE_VALUE_CLOUD_SYNC_REQUEST = "cloud_sync_request";
	public static final String FILE_DATA_TASK_PACKAGE_NULL = "null";

	
	public static final String CHAR_SET = "char_set";
	public static final String CHAR_SET_UTF8 = "UTF-8";
	public static final String CHAR_SET_GBK = "GBK";
}
