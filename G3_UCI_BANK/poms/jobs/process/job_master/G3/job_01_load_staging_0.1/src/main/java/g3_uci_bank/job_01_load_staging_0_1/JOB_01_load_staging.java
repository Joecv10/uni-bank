
package g3_uci_bank.job_01_load_staging_0_1;

import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.Mathematical;
import routines.DataQualityDependencies;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.MDM;
import routines.StringHandling;
import routines.DQTechnical;
import routines.TalendDate;
import routines.DataMasking;
import routines.DqStringHandling;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
 






@SuppressWarnings("unused")

/**
 * Job: JOB_01_load_staging Purpose: Carga de datos de UCI bank-full.csv a staging<br>
 * Description: Carga de datos de UCI bank-full.csv a staging <br>
 * @author Myer, Jav
 * @version 8.0.1.20261022_0848-patch
 * @status 
 */
public class JOB_01_load_staging implements TalendJob {
	static {System.setProperty("TalendJob.log", "JOB_01_load_staging.log");}

	

	
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(JOB_01_load_staging.class);
	

static {
		 System.setProperty("talend.component.record.nullable.check", "true");
         String javaUtilLoggingConfigFile = System.getProperty("java.util.logging.config.file");
         if (javaUtilLoggingConfigFile == null) {
             setupDefaultJavaUtilLogging();
         }
}

/**
* This class replaces the default {@code System.err} stream used by Java Util Logging (JUL).
* You can use your own configuration through the
* {@code java.util.logging.config.file} system property, enabling you to specify an external
* logging configuration file for tailored logging setup.
*/
public static class StandardConsoleHandler extends java.util.logging.StreamHandler {
     public StandardConsoleHandler() {
         // Set System.out as default log output stream
         super(System.out, new java.util.logging.SimpleFormatter());
     }

     /**
      * Publish a {@code LogRecord}.
      * The logging request was made initially to a {@code Logger} object,
      * which initialized the {@code LogRecord} and forwarded it here.
      *
      * @param  record  description of the log event. A null record is
      *                 silently ignored and is not published
      */
      @Override
      public void publish(java.util.logging.LogRecord record) {
            super.publish(record);
            flush();
      }

      /**
      * Override {@code StreamHandler.close} to do a flush but not
      * to close the output stream.  That is, we do <b>not</b>
      * close {@code System.out}.
      */
      @Override
      public void close() {
            flush();
      }
}


protected static void setupDefaultJavaUtilLogging() {
      java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();

      // Get the root logger
      java.util.logging.Logger rootLogger = logManager.getLogger("");

      // Remove existing handlers to set standard console handler only
      java.util.logging.Handler[] handlers = rootLogger.getHandlers();
      for (java.util.logging.Handler handler : handlers) {
            rootLogger.removeHandler(handler);
      }

      rootLogger.addHandler(new StandardConsoleHandler());
      rootLogger.setLevel(java.util.logging.Level.INFO);
}

protected static void logIgnoredError(String message, Throwable cause) {
       log.error(message, cause);

}


	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}
	
	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	
	private final static String utf8Charset = "UTF-8";

    // volatile is needed for OSGi: the thread that calls the blueprint destroy method is not the same that sets the values
	public static volatile String taskExecutionId = null;
	public static volatile String jobExecutionId = null;
	public static volatile boolean sealCounters = false;

    public static final java.util.Set<Thread> threadList = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());	

	//contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String,String> propertyTypes = new java.util.HashMap<>();
		
		public PropertiesWithType(java.util.Properties properties){
			super(properties);
		}
		public PropertiesWithType(){
			super();
		}
		
		public void setContextType(String key, String type) {
			propertyTypes.put(key,type);
		}
	
		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}	
	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();
		

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties){
			super(properties);
		}
		public ContextProperties(){
			super();
		}

		public void synchronizeContext(){
			
			if(DB_HOST != null){
				
					this.setProperty("DB_HOST", DB_HOST.toString());
				
			}
			
			if(DB_NAME != null){
				
					this.setProperty("DB_NAME", DB_NAME.toString());
				
			}
			
			if(DB_PASSWORD != null){
				
					this.setProperty("DB_PASSWORD", DB_PASSWORD.toString());
				
			}
			
			if(DB_PORT != null){
				
					this.setProperty("DB_PORT", DB_PORT.toString());
				
			}
			
			if(DB_USER != null){
				
					this.setProperty("DB_USER", DB_USER.toString());
				
			}
			
			if(FILE_UCI_BANK != null){
				
					this.setProperty("FILE_UCI_BANK", FILE_UCI_BANK.toString());
				
			}
			
			if(LOAD_SOURCE_TAG != null){
				
					this.setProperty("LOAD_SOURCE_TAG", LOAD_SOURCE_TAG.toString());
				
			}
			
			if(PIPELINE_NAME != null){
				
					this.setProperty("PIPELINE_NAME", PIPELINE_NAME.toString());
				
			}
			
		}
		
		//if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if(NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

public String DB_HOST;
public String getDB_HOST(){
	return this.DB_HOST;
}
public String DB_NAME;
public String getDB_NAME(){
	return this.DB_NAME;
}
public String DB_PASSWORD;
public String getDB_PASSWORD(){
	return this.DB_PASSWORD;
}
public String DB_PORT;
public String getDB_PORT(){
	return this.DB_PORT;
}
public String DB_USER;
public String getDB_USER(){
	return this.DB_USER;
}
public String FILE_UCI_BANK;
public String getFILE_UCI_BANK(){
	return this.FILE_UCI_BANK;
}
public String LOAD_SOURCE_TAG;
public String getLOAD_SOURCE_TAG(){
	return this.LOAD_SOURCE_TAG;
}
public String PIPELINE_NAME;
public String getPIPELINE_NAME(){
	return this.PIPELINE_NAME;
}
	}
			
	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.
	public ContextProperties getContext() {
		return this.context;
	}

	protected java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();
	protected java.util.Map<String, String> additionalProperties = new java.util.HashMap<String, String>();

	public java.util.Map<String, String> getDefaultProperties() {
	    return this.defaultProperties;
	}

	public java.util.Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

	private final String jobVersion = "0.1";
	private final String jobName = "JOB_01_load_staging";
	private final String projectName = "G3_UCI_BANK";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");
	
	private void s(final String component) {
		try {
			org.talend.metrics.DataReadTracker.setCurrentComponent(jobName, component);
		} catch (Exception | NoClassDefFoundError e) {
			// ignore
		}
	}
	
	
	private void mdc(final String subJobName, final String subJobPidPrefix) {
		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", subJobName);
		org.slf4j.MDC.put("_subJobPid", subJobPidPrefix + subJobPidCounter.getAndIncrement());
	}
	
	
	private void sh(final String componentId) {
		ok_Hash.put(componentId, false);
		start_Hash.put(componentId, System.currentTimeMillis());
	}

	{
	s("none");
	}

	
	private String cLabel =  null;
	
		private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
        private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
	
		private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
		public  final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
	

private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName, "_oDmO4NiGEfGYDKHwOCnAyQ", "0.1");
private org.talend.job.audit.JobAuditLogger runtime_lineage_logger_talendJobLog = null;
private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
	
	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(), new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}
	
	public void setDataSourceReferences(List serviceReferences) throws Exception{
		
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
		
		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils.getServices(serviceReferences,  javax.sql.DataSource.class).entrySet()) {
                    dataSources.put(entry.getKey(), entry.getValue());
                    talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}


private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

public String getExceptionStackTrace() {
	if ("failure".equals(this.getStatus())) {
		errorMessagePS.flush();
		return baos.toString();
	}
	return null;
}

private Exception exception;

public Exception getException() {
	if ("failure".equals(this.getStatus())) {
		return this.exception;
	}
	return null;
}

private class TalendException extends Exception {

	private static final long serialVersionUID = 1L;

	private java.util.Map<String, Object> globalMap = null;
	private Exception e = null;
	
	private String currentComponent = null;
	private String cLabel =  null;
	
	private String virtualComponentName = null;
	
	public void setVirtualComponentName (String virtualComponentName){
		this.virtualComponentName = virtualComponentName;
	}

	private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
		this.currentComponent= errorComponent;
		this.globalMap = globalMap;
		this.e = e;
	}
	
	private TalendException(Exception e, String errorComponent, String errorComponentLabel, final java.util.Map<String, Object> globalMap) {
		this(e, errorComponent, globalMap);
		this.cLabel = errorComponentLabel;
	}

	public Exception getException() {
		return this.e;
	}

	public String getCurrentComponent() {
		return this.currentComponent;
	}

	
    public String getExceptionCauseMessage(Exception e){
        Throwable cause = e;
        String message = null;
        int i = 10;
        while (null != cause && 0 < i--) {
            message = cause.getMessage();
            if (null == message) {
                cause = cause.getCause();
            } else {
                break;          
            }
        }
        if (null == message) {
            message = e.getClass().getName();
        }   
        return message;
    }

	@Override
	public void printStackTrace() {
		if (!(e instanceof TalendException || e instanceof TDieException)) {
			if(virtualComponentName!=null && currentComponent.indexOf(virtualComponentName+"_")==0){
				globalMap.put(virtualComponentName+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			}
			globalMap.put(currentComponent+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
		}
		if (!(e instanceof TDieException)) {
			if(e instanceof TalendException){
				e.printStackTrace();
			} else {
				e.printStackTrace();
				e.printStackTrace(errorMessagePS);
			}
		}
		if (!(e instanceof TalendException)) {
			JOB_01_load_staging.this.exception = e;
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(JOB_01_load_staging.this, new Object[] { e , currentComponent, globalMap});
					break;
				}
			}

			if(!(e instanceof TDieException)){
		if(enableLogStash) {
			talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
			talendJobLogProcess(globalMap);
		}
			}
		} catch (Exception e) {
			this.e.printStackTrace();
		}
		}
	}
}

			public void tFileInputDelimited_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void talendJobLog_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					talendJobLog_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tFileInputDelimited_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void talendJobLog_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	



public static class out2Struct implements routines.system.IPersistableRow<out2Struct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[0];

	
			    public Integer age;

				public Integer getAge () {
					return this.age;
				}

				public Boolean ageIsNullable(){
				    return true;
				}
				public Boolean ageIsKey(){
				    return false;
				}
				public Integer ageLength(){
				    return 3;
				}
				public Integer agePrecision(){
				    return 0;
				}
				public String ageDefault(){
				
					return null;
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String ageOriginalDbColumnName(){
				
					return "age";
				
				}

				
			    public String job;

				public String getJob () {
					return this.job;
				}

				public Boolean jobIsNullable(){
				    return true;
				}
				public Boolean jobIsKey(){
				    return false;
				}
				public Integer jobLength(){
				    return 13;
				}
				public Integer jobPrecision(){
				    return 0;
				}
				public String jobDefault(){
				
					return null;
				
				}
				public String jobComment(){
				
				    return "";
				
				}
				public String jobPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String jobOriginalDbColumnName(){
				
					return "job";
				
				}

				
			    public String marital;

				public String getMarital () {
					return this.marital;
				}

				public Boolean maritalIsNullable(){
				    return true;
				}
				public Boolean maritalIsKey(){
				    return false;
				}
				public Integer maritalLength(){
				    return 8;
				}
				public Integer maritalPrecision(){
				    return 0;
				}
				public String maritalDefault(){
				
					return null;
				
				}
				public String maritalComment(){
				
				    return "";
				
				}
				public String maritalPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String maritalOriginalDbColumnName(){
				
					return "marital";
				
				}

				
			    public String education;

				public String getEducation () {
					return this.education;
				}

				public Boolean educationIsNullable(){
				    return true;
				}
				public Boolean educationIsKey(){
				    return false;
				}
				public Integer educationLength(){
				    return 19;
				}
				public Integer educationPrecision(){
				    return 0;
				}
				public String educationDefault(){
				
					return null;
				
				}
				public String educationComment(){
				
				    return "";
				
				}
				public String educationPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String educationOriginalDbColumnName(){
				
					return "education";
				
				}

				
			    public String has_default;

				public String getHas_default () {
					return this.has_default;
				}

				public Boolean has_defaultIsNullable(){
				    return true;
				}
				public Boolean has_defaultIsKey(){
				    return false;
				}
				public Integer has_defaultLength(){
				    return 7;
				}
				public Integer has_defaultPrecision(){
				    return 0;
				}
				public String has_defaultDefault(){
				
					return null;
				
				}
				public String has_defaultComment(){
				
				    return "";
				
				}
				public String has_defaultPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String has_defaultOriginalDbColumnName(){
				
					return "has_default";
				
				}

				
			    public String housing;

				public String getHousing () {
					return this.housing;
				}

				public Boolean housingIsNullable(){
				    return true;
				}
				public Boolean housingIsKey(){
				    return false;
				}
				public Integer housingLength(){
				    return 7;
				}
				public Integer housingPrecision(){
				    return 0;
				}
				public String housingDefault(){
				
					return null;
				
				}
				public String housingComment(){
				
				    return "";
				
				}
				public String housingPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String housingOriginalDbColumnName(){
				
					return "housing";
				
				}

				
			    public String loan;

				public String getLoan () {
					return this.loan;
				}

				public Boolean loanIsNullable(){
				    return true;
				}
				public Boolean loanIsKey(){
				    return false;
				}
				public Integer loanLength(){
				    return 7;
				}
				public Integer loanPrecision(){
				    return 0;
				}
				public String loanDefault(){
				
					return null;
				
				}
				public String loanComment(){
				
				    return "";
				
				}
				public String loanPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String loanOriginalDbColumnName(){
				
					return "loan";
				
				}

				
			    public String contact;

				public String getContact () {
					return this.contact;
				}

				public Boolean contactIsNullable(){
				    return true;
				}
				public Boolean contactIsKey(){
				    return false;
				}
				public Integer contactLength(){
				    return 20;
				}
				public Integer contactPrecision(){
				    return 0;
				}
				public String contactDefault(){
				
					return null;
				
				}
				public String contactComment(){
				
				    return "";
				
				}
				public String contactPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String contactOriginalDbColumnName(){
				
					return "contact";
				
				}

				
			    public String month;

				public String getMonth () {
					return this.month;
				}

				public Boolean monthIsNullable(){
				    return true;
				}
				public Boolean monthIsKey(){
				    return false;
				}
				public Integer monthLength(){
				    return 3;
				}
				public Integer monthPrecision(){
				    return 0;
				}
				public String monthDefault(){
				
					return null;
				
				}
				public String monthComment(){
				
				    return "";
				
				}
				public String monthPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String monthOriginalDbColumnName(){
				
					return "month";
				
				}

				
			    public String day_of_week;

				public String getDay_of_week () {
					return this.day_of_week;
				}

				public Boolean day_of_weekIsNullable(){
				    return true;
				}
				public Boolean day_of_weekIsKey(){
				    return false;
				}
				public Integer day_of_weekLength(){
				    return 3;
				}
				public Integer day_of_weekPrecision(){
				    return 0;
				}
				public String day_of_weekDefault(){
				
					return null;
				
				}
				public String day_of_weekComment(){
				
				    return "";
				
				}
				public String day_of_weekPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String day_of_weekOriginalDbColumnName(){
				
					return "day_of_week";
				
				}

				
			    public Integer duration;

				public Integer getDuration () {
					return this.duration;
				}

				public Boolean durationIsNullable(){
				    return true;
				}
				public Boolean durationIsKey(){
				    return false;
				}
				public Integer durationLength(){
				    return 4;
				}
				public Integer durationPrecision(){
				    return 0;
				}
				public String durationDefault(){
				
					return null;
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String durationOriginalDbColumnName(){
				
					return "duration";
				
				}

				
			    public Integer campaign;

				public Integer getCampaign () {
					return this.campaign;
				}

				public Boolean campaignIsNullable(){
				    return true;
				}
				public Boolean campaignIsKey(){
				    return false;
				}
				public Integer campaignLength(){
				    return 1;
				}
				public Integer campaignPrecision(){
				    return 0;
				}
				public String campaignDefault(){
				
					return null;
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String campaignOriginalDbColumnName(){
				
					return "campaign";
				
				}

				
			    public Integer pdays;

				public Integer getPdays () {
					return this.pdays;
				}

				public Boolean pdaysIsNullable(){
				    return true;
				}
				public Boolean pdaysIsKey(){
				    return false;
				}
				public Integer pdaysLength(){
				    return 3;
				}
				public Integer pdaysPrecision(){
				    return 0;
				}
				public String pdaysDefault(){
				
					return null;
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String pdaysOriginalDbColumnName(){
				
					return "pdays";
				
				}

				
			    public Integer previous;

				public Integer getPrevious () {
					return this.previous;
				}

				public Boolean previousIsNullable(){
				    return true;
				}
				public Boolean previousIsKey(){
				    return false;
				}
				public Integer previousLength(){
				    return 1;
				}
				public Integer previousPrecision(){
				    return 0;
				}
				public String previousDefault(){
				
					return null;
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String previousOriginalDbColumnName(){
				
					return "previous";
				
				}

				
			    public String poutcome;

				public String getPoutcome () {
					return this.poutcome;
				}

				public Boolean poutcomeIsNullable(){
				    return true;
				}
				public Boolean poutcomeIsKey(){
				    return false;
				}
				public Integer poutcomeLength(){
				    return 11;
				}
				public Integer poutcomePrecision(){
				    return 0;
				}
				public String poutcomeDefault(){
				
					return null;
				
				}
				public String poutcomeComment(){
				
				    return "";
				
				}
				public String poutcomePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String poutcomeOriginalDbColumnName(){
				
					return "poutcome";
				
				}

				
			    public Float emp_var_rate;

				public Float getEmp_var_rate () {
					return this.emp_var_rate;
				}

				public Boolean emp_var_rateIsNullable(){
				    return true;
				}
				public Boolean emp_var_rateIsKey(){
				    return false;
				}
				public Integer emp_var_rateLength(){
				    return 10;
				}
				public Integer emp_var_ratePrecision(){
				    return 4;
				}
				public String emp_var_rateDefault(){
				
					return null;
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String emp_var_rateOriginalDbColumnName(){
				
					return "emp_var_rate";
				
				}

				
			    public Float cons_price_idx;

				public Float getCons_price_idx () {
					return this.cons_price_idx;
				}

				public Boolean cons_price_idxIsNullable(){
				    return true;
				}
				public Boolean cons_price_idxIsKey(){
				    return false;
				}
				public Integer cons_price_idxLength(){
				    return 10;
				}
				public Integer cons_price_idxPrecision(){
				    return 4;
				}
				public String cons_price_idxDefault(){
				
					return null;
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String cons_price_idxOriginalDbColumnName(){
				
					return "cons_price_idx";
				
				}

				
			    public Double cons_conf_idx;

				public Double getCons_conf_idx () {
					return this.cons_conf_idx;
				}

				public Boolean cons_conf_idxIsNullable(){
				    return true;
				}
				public Boolean cons_conf_idxIsKey(){
				    return false;
				}
				public Integer cons_conf_idxLength(){
				    return 10;
				}
				public Integer cons_conf_idxPrecision(){
				    return 4;
				}
				public String cons_conf_idxDefault(){
				
					return null;
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String cons_conf_idxOriginalDbColumnName(){
				
					return "cons_conf_idx";
				
				}

				
			    public Float euribor3m;

				public Float getEuribor3m () {
					return this.euribor3m;
				}

				public Boolean euribor3mIsNullable(){
				    return true;
				}
				public Boolean euribor3mIsKey(){
				    return false;
				}
				public Integer euribor3mLength(){
				    return 10;
				}
				public Integer euribor3mPrecision(){
				    return 4;
				}
				public String euribor3mDefault(){
				
					return null;
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String euribor3mOriginalDbColumnName(){
				
					return "euribor3m";
				
				}

				
			    public Double nr_employed;

				public Double getNr_employed () {
					return this.nr_employed;
				}

				public Boolean nr_employedIsNullable(){
				    return true;
				}
				public Boolean nr_employedIsKey(){
				    return false;
				}
				public Integer nr_employedLength(){
				    return 12;
				}
				public Integer nr_employedPrecision(){
				    return 4;
				}
				public String nr_employedDefault(){
				
					return null;
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String nr_employedOriginalDbColumnName(){
				
					return "nr_employed";
				
				}

				
			    public String y;

				public String getY () {
					return this.y;
				}

				public Boolean yIsNullable(){
				    return true;
				}
				public Boolean yIsKey(){
				    return false;
				}
				public Integer yLength(){
				    return 3;
				}
				public Integer yPrecision(){
				    return 0;
				}
				public String yDefault(){
				
					return null;
				
				}
				public String yComment(){
				
				    return "";
				
				}
				public String yPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String yOriginalDbColumnName(){
				
					return "y";
				
				}

				
			    public java.util.Date load_ts;

				public java.util.Date getLoad_ts () {
					return this.load_ts;
				}

				public Boolean load_tsIsNullable(){
				    return true;
				}
				public Boolean load_tsIsKey(){
				    return false;
				}
				public Integer load_tsLength(){
				    return null;
				}
				public Integer load_tsPrecision(){
				    return null;
				}
				public String load_tsDefault(){
				
					return null;
				
				}
				public String load_tsComment(){
				
				    return "";
				
				}
				public String load_tsPattern(){
				
					return "yyyy-MM-dd HH:mm:ss";
				
				}
				public String load_tsOriginalDbColumnName(){
				
					return "load_ts";
				
				}

				
			    public String load_source;

				public String getLoad_source () {
					return this.load_source;
				}

				public Boolean load_sourceIsNullable(){
				    return true;
				}
				public Boolean load_sourceIsKey(){
				    return false;
				}
				public Integer load_sourceLength(){
				    return 60;
				}
				public Integer load_sourcePrecision(){
				    return null;
				}
				public String load_sourceDefault(){
				
					return null;
				
				}
				public String load_sourceComment(){
				
				    return "";
				
				}
				public String load_sourcePattern(){
				
					return "";
				
				}
				public String load_sourceOriginalDbColumnName(){
				
					return "load_source";
				
				}

				


	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

	private java.util.Date readDate(ObjectInputStream dis) throws IOException{
		java.util.Date dateReturn = null;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			dateReturn = null;
		} else {
	    	dateReturn = new Date(dis.readLong());
		}
		return dateReturn;
	}
	
	private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		java.util.Date dateReturn = null;
        int length = 0;
        length = unmarshaller.readByte();
		if (length == -1) {
			dateReturn = null;
		} else {
	    	dateReturn = new Date(unmarshaller.readLong());
		}
		return dateReturn;
	}

    private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException{
		if(date1 == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeLong(date1.getTime());
    	}
    }
    
    private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(date1 == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeLong(date1.getTime());
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging) {

        	try {

        		int length = 0;
		
						this.age = readInteger(dis);
					
					this.job = readString(dis);
					
					this.marital = readString(dis);
					
					this.education = readString(dis);
					
					this.has_default = readString(dis);
					
					this.housing = readString(dis);
					
					this.loan = readString(dis);
					
					this.contact = readString(dis);
					
					this.month = readString(dis);
					
					this.day_of_week = readString(dis);
					
						this.duration = readInteger(dis);
					
						this.campaign = readInteger(dis);
					
						this.pdays = readInteger(dis);
					
						this.previous = readInteger(dis);
					
					this.poutcome = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.emp_var_rate = null;
           				} else {
           			    	this.emp_var_rate = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_price_idx = null;
           				} else {
           			    	this.cons_price_idx = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_conf_idx = null;
           				} else {
           			    	this.cons_conf_idx = dis.readDouble();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.euribor3m = null;
           				} else {
           			    	this.euribor3m = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.nr_employed = null;
           				} else {
           			    	this.nr_employed = dis.readDouble();
           				}
					
					this.y = readString(dis);
					
					this.load_ts = readDate(dis);
					
					this.load_source = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging) {

        	try {

        		int length = 0;
		
						this.age = readInteger(dis);
					
					this.job = readString(dis);
					
					this.marital = readString(dis);
					
					this.education = readString(dis);
					
					this.has_default = readString(dis);
					
					this.housing = readString(dis);
					
					this.loan = readString(dis);
					
					this.contact = readString(dis);
					
					this.month = readString(dis);
					
					this.day_of_week = readString(dis);
					
						this.duration = readInteger(dis);
					
						this.campaign = readInteger(dis);
					
						this.pdays = readInteger(dis);
					
						this.previous = readInteger(dis);
					
					this.poutcome = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.emp_var_rate = null;
           				} else {
           			    	this.emp_var_rate = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_price_idx = null;
           				} else {
           			    	this.cons_price_idx = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_conf_idx = null;
           				} else {
           			    	this.cons_conf_idx = dis.readDouble();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.euribor3m = null;
           				} else {
           			    	this.euribor3m = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.nr_employed = null;
           				} else {
           			    	this.nr_employed = dis.readDouble();
           				}
					
					this.y = readString(dis);
					
					this.load_ts = readDate(dis);
					
					this.load_source = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.age,dos);
					
					// String
				
						writeString(this.job,dos);
					
					// String
				
						writeString(this.marital,dos);
					
					// String
				
						writeString(this.education,dos);
					
					// String
				
						writeString(this.has_default,dos);
					
					// String
				
						writeString(this.housing,dos);
					
					// String
				
						writeString(this.loan,dos);
					
					// String
				
						writeString(this.contact,dos);
					
					// String
				
						writeString(this.month,dos);
					
					// String
				
						writeString(this.day_of_week,dos);
					
					// Integer
				
						writeInteger(this.duration,dos);
					
					// Integer
				
						writeInteger(this.campaign,dos);
					
					// Integer
				
						writeInteger(this.pdays,dos);
					
					// Integer
				
						writeInteger(this.previous,dos);
					
					// String
				
						writeString(this.poutcome,dos);
					
					// Float
				
						if(this.emp_var_rate == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.emp_var_rate);
		            	}
					
					// Float
				
						if(this.cons_price_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.cons_price_idx);
		            	}
					
					// Double
				
						if(this.cons_conf_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.cons_conf_idx);
		            	}
					
					// Float
				
						if(this.euribor3m == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.euribor3m);
		            	}
					
					// Double
				
						if(this.nr_employed == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.nr_employed);
		            	}
					
					// String
				
						writeString(this.y,dos);
					
					// java.util.Date
				
						writeDate(this.load_ts,dos);
					
					// String
				
						writeString(this.load_source,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.age,dos);
					
					// String
				
						writeString(this.job,dos);
					
					// String
				
						writeString(this.marital,dos);
					
					// String
				
						writeString(this.education,dos);
					
					// String
				
						writeString(this.has_default,dos);
					
					// String
				
						writeString(this.housing,dos);
					
					// String
				
						writeString(this.loan,dos);
					
					// String
				
						writeString(this.contact,dos);
					
					// String
				
						writeString(this.month,dos);
					
					// String
				
						writeString(this.day_of_week,dos);
					
					// Integer
				
						writeInteger(this.duration,dos);
					
					// Integer
				
						writeInteger(this.campaign,dos);
					
					// Integer
				
						writeInteger(this.pdays,dos);
					
					// Integer
				
						writeInteger(this.previous,dos);
					
					// String
				
						writeString(this.poutcome,dos);
					
					// Float
				
						if(this.emp_var_rate == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.emp_var_rate);
		            	}
					
					// Float
				
						if(this.cons_price_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.cons_price_idx);
		            	}
					
					// Double
				
						if(this.cons_conf_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.cons_conf_idx);
		            	}
					
					// Float
				
						if(this.euribor3m == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.euribor3m);
		            	}
					
					// Double
				
						if(this.nr_employed == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.nr_employed);
		            	}
					
					// String
				
						writeString(this.y,dos);
					
					// java.util.Date
				
						writeDate(this.load_ts,dos);
					
					// String
				
						writeString(this.load_source,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("age="+String.valueOf(age));
		sb.append(",job="+job);
		sb.append(",marital="+marital);
		sb.append(",education="+education);
		sb.append(",has_default="+has_default);
		sb.append(",housing="+housing);
		sb.append(",loan="+loan);
		sb.append(",contact="+contact);
		sb.append(",month="+month);
		sb.append(",day_of_week="+day_of_week);
		sb.append(",duration="+String.valueOf(duration));
		sb.append(",campaign="+String.valueOf(campaign));
		sb.append(",pdays="+String.valueOf(pdays));
		sb.append(",previous="+String.valueOf(previous));
		sb.append(",poutcome="+poutcome);
		sb.append(",emp_var_rate="+String.valueOf(emp_var_rate));
		sb.append(",cons_price_idx="+String.valueOf(cons_price_idx));
		sb.append(",cons_conf_idx="+String.valueOf(cons_conf_idx));
		sb.append(",euribor3m="+String.valueOf(euribor3m));
		sb.append(",nr_employed="+String.valueOf(nr_employed));
		sb.append(",y="+y);
		sb.append(",load_ts="+String.valueOf(load_ts));
		sb.append(",load_source="+load_source);
	    sb.append("]");

	    return sb.toString();
    }
        public String toLogString(){
        	StringBuilder sb = new StringBuilder();
        	
        				if(age == null){
        					sb.append("<null>");
        				}else{
            				sb.append(age);
            			}
            		
        			sb.append("|");
        		
        				if(job == null){
        					sb.append("<null>");
        				}else{
            				sb.append(job);
            			}
            		
        			sb.append("|");
        		
        				if(marital == null){
        					sb.append("<null>");
        				}else{
            				sb.append(marital);
            			}
            		
        			sb.append("|");
        		
        				if(education == null){
        					sb.append("<null>");
        				}else{
            				sb.append(education);
            			}
            		
        			sb.append("|");
        		
        				if(has_default == null){
        					sb.append("<null>");
        				}else{
            				sb.append(has_default);
            			}
            		
        			sb.append("|");
        		
        				if(housing == null){
        					sb.append("<null>");
        				}else{
            				sb.append(housing);
            			}
            		
        			sb.append("|");
        		
        				if(loan == null){
        					sb.append("<null>");
        				}else{
            				sb.append(loan);
            			}
            		
        			sb.append("|");
        		
        				if(contact == null){
        					sb.append("<null>");
        				}else{
            				sb.append(contact);
            			}
            		
        			sb.append("|");
        		
        				if(month == null){
        					sb.append("<null>");
        				}else{
            				sb.append(month);
            			}
            		
        			sb.append("|");
        		
        				if(day_of_week == null){
        					sb.append("<null>");
        				}else{
            				sb.append(day_of_week);
            			}
            		
        			sb.append("|");
        		
        				if(duration == null){
        					sb.append("<null>");
        				}else{
            				sb.append(duration);
            			}
            		
        			sb.append("|");
        		
        				if(campaign == null){
        					sb.append("<null>");
        				}else{
            				sb.append(campaign);
            			}
            		
        			sb.append("|");
        		
        				if(pdays == null){
        					sb.append("<null>");
        				}else{
            				sb.append(pdays);
            			}
            		
        			sb.append("|");
        		
        				if(previous == null){
        					sb.append("<null>");
        				}else{
            				sb.append(previous);
            			}
            		
        			sb.append("|");
        		
        				if(poutcome == null){
        					sb.append("<null>");
        				}else{
            				sb.append(poutcome);
            			}
            		
        			sb.append("|");
        		
        				if(emp_var_rate == null){
        					sb.append("<null>");
        				}else{
            				sb.append(emp_var_rate);
            			}
            		
        			sb.append("|");
        		
        				if(cons_price_idx == null){
        					sb.append("<null>");
        				}else{
            				sb.append(cons_price_idx);
            			}
            		
        			sb.append("|");
        		
        				if(cons_conf_idx == null){
        					sb.append("<null>");
        				}else{
            				sb.append(cons_conf_idx);
            			}
            		
        			sb.append("|");
        		
        				if(euribor3m == null){
        					sb.append("<null>");
        				}else{
            				sb.append(euribor3m);
            			}
            		
        			sb.append("|");
        		
        				if(nr_employed == null){
        					sb.append("<null>");
        				}else{
            				sb.append(nr_employed);
            			}
            		
        			sb.append("|");
        		
        				if(y == null){
        					sb.append("<null>");
        				}else{
            				sb.append(y);
            			}
            		
        			sb.append("|");
        		
        				if(load_ts == null){
        					sb.append("<null>");
        				}else{
            				sb.append(load_ts);
            			}
            		
        			sb.append("|");
        		
        				if(load_source == null){
        					sb.append("<null>");
        				}else{
            				sb.append(load_source);
            			}
            		
        			sb.append("|");
        		
        	return sb.toString();
        }

    /**
     * Compare keys
     */
    public int compareTo(out2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[0];

	
			    public Integer age;

				public Integer getAge () {
					return this.age;
				}

				public Boolean ageIsNullable(){
				    return true;
				}
				public Boolean ageIsKey(){
				    return false;
				}
				public Integer ageLength(){
				    return 2;
				}
				public Integer agePrecision(){
				    return 0;
				}
				public String ageDefault(){
				
					return null;
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String ageOriginalDbColumnName(){
				
					return "age";
				
				}

				
			    public String job;

				public String getJob () {
					return this.job;
				}

				public Boolean jobIsNullable(){
				    return true;
				}
				public Boolean jobIsKey(){
				    return false;
				}
				public Integer jobLength(){
				    return 13;
				}
				public Integer jobPrecision(){
				    return 0;
				}
				public String jobDefault(){
				
					return null;
				
				}
				public String jobComment(){
				
				    return "";
				
				}
				public String jobPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String jobOriginalDbColumnName(){
				
					return "job";
				
				}

				
			    public String marital;

				public String getMarital () {
					return this.marital;
				}

				public Boolean maritalIsNullable(){
				    return true;
				}
				public Boolean maritalIsKey(){
				    return false;
				}
				public Integer maritalLength(){
				    return 8;
				}
				public Integer maritalPrecision(){
				    return 0;
				}
				public String maritalDefault(){
				
					return null;
				
				}
				public String maritalComment(){
				
				    return "";
				
				}
				public String maritalPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String maritalOriginalDbColumnName(){
				
					return "marital";
				
				}

				
			    public String education;

				public String getEducation () {
					return this.education;
				}

				public Boolean educationIsNullable(){
				    return true;
				}
				public Boolean educationIsKey(){
				    return false;
				}
				public Integer educationLength(){
				    return 19;
				}
				public Integer educationPrecision(){
				    return 0;
				}
				public String educationDefault(){
				
					return null;
				
				}
				public String educationComment(){
				
				    return "";
				
				}
				public String educationPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String educationOriginalDbColumnName(){
				
					return "education";
				
				}

				
			    public String Column4;

				public String getColumn4 () {
					return this.Column4;
				}

				public Boolean Column4IsNullable(){
				    return true;
				}
				public Boolean Column4IsKey(){
				    return false;
				}
				public Integer Column4Length(){
				    return 7;
				}
				public Integer Column4Precision(){
				    return 0;
				}
				public String Column4Default(){
				
					return null;
				
				}
				public String Column4Comment(){
				
				    return "";
				
				}
				public String Column4Pattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String Column4OriginalDbColumnName(){
				
					return "Column4";
				
				}

				
			    public String housing;

				public String getHousing () {
					return this.housing;
				}

				public Boolean housingIsNullable(){
				    return true;
				}
				public Boolean housingIsKey(){
				    return false;
				}
				public Integer housingLength(){
				    return 7;
				}
				public Integer housingPrecision(){
				    return 0;
				}
				public String housingDefault(){
				
					return null;
				
				}
				public String housingComment(){
				
				    return "";
				
				}
				public String housingPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String housingOriginalDbColumnName(){
				
					return "housing";
				
				}

				
			    public String loan;

				public String getLoan () {
					return this.loan;
				}

				public Boolean loanIsNullable(){
				    return true;
				}
				public Boolean loanIsKey(){
				    return false;
				}
				public Integer loanLength(){
				    return 7;
				}
				public Integer loanPrecision(){
				    return 0;
				}
				public String loanDefault(){
				
					return null;
				
				}
				public String loanComment(){
				
				    return "";
				
				}
				public String loanPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String loanOriginalDbColumnName(){
				
					return "loan";
				
				}

				
			    public String contact;

				public String getContact () {
					return this.contact;
				}

				public Boolean contactIsNullable(){
				    return true;
				}
				public Boolean contactIsKey(){
				    return false;
				}
				public Integer contactLength(){
				    return 9;
				}
				public Integer contactPrecision(){
				    return 0;
				}
				public String contactDefault(){
				
					return null;
				
				}
				public String contactComment(){
				
				    return "";
				
				}
				public String contactPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String contactOriginalDbColumnName(){
				
					return "contact";
				
				}

				
			    public String month;

				public String getMonth () {
					return this.month;
				}

				public Boolean monthIsNullable(){
				    return true;
				}
				public Boolean monthIsKey(){
				    return false;
				}
				public Integer monthLength(){
				    return 3;
				}
				public Integer monthPrecision(){
				    return 0;
				}
				public String monthDefault(){
				
					return null;
				
				}
				public String monthComment(){
				
				    return "";
				
				}
				public String monthPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String monthOriginalDbColumnName(){
				
					return "month";
				
				}

				
			    public String day_of_week;

				public String getDay_of_week () {
					return this.day_of_week;
				}

				public Boolean day_of_weekIsNullable(){
				    return true;
				}
				public Boolean day_of_weekIsKey(){
				    return false;
				}
				public Integer day_of_weekLength(){
				    return 3;
				}
				public Integer day_of_weekPrecision(){
				    return 0;
				}
				public String day_of_weekDefault(){
				
					return null;
				
				}
				public String day_of_weekComment(){
				
				    return "";
				
				}
				public String day_of_weekPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String day_of_weekOriginalDbColumnName(){
				
					return "day_of_week";
				
				}

				
			    public Integer duration;

				public Integer getDuration () {
					return this.duration;
				}

				public Boolean durationIsNullable(){
				    return true;
				}
				public Boolean durationIsKey(){
				    return false;
				}
				public Integer durationLength(){
				    return 4;
				}
				public Integer durationPrecision(){
				    return 0;
				}
				public String durationDefault(){
				
					return null;
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String durationOriginalDbColumnName(){
				
					return "duration";
				
				}

				
			    public Integer campaign;

				public Integer getCampaign () {
					return this.campaign;
				}

				public Boolean campaignIsNullable(){
				    return true;
				}
				public Boolean campaignIsKey(){
				    return false;
				}
				public Integer campaignLength(){
				    return 1;
				}
				public Integer campaignPrecision(){
				    return 0;
				}
				public String campaignDefault(){
				
					return null;
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String campaignOriginalDbColumnName(){
				
					return "campaign";
				
				}

				
			    public Integer pdays;

				public Integer getPdays () {
					return this.pdays;
				}

				public Boolean pdaysIsNullable(){
				    return true;
				}
				public Boolean pdaysIsKey(){
				    return false;
				}
				public Integer pdaysLength(){
				    return 3;
				}
				public Integer pdaysPrecision(){
				    return 0;
				}
				public String pdaysDefault(){
				
					return null;
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String pdaysOriginalDbColumnName(){
				
					return "pdays";
				
				}

				
			    public Integer previous;

				public Integer getPrevious () {
					return this.previous;
				}

				public Boolean previousIsNullable(){
				    return true;
				}
				public Boolean previousIsKey(){
				    return false;
				}
				public Integer previousLength(){
				    return 1;
				}
				public Integer previousPrecision(){
				    return 0;
				}
				public String previousDefault(){
				
					return null;
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String previousOriginalDbColumnName(){
				
					return "previous";
				
				}

				
			    public String poutcome;

				public String getPoutcome () {
					return this.poutcome;
				}

				public Boolean poutcomeIsNullable(){
				    return true;
				}
				public Boolean poutcomeIsKey(){
				    return false;
				}
				public Integer poutcomeLength(){
				    return 11;
				}
				public Integer poutcomePrecision(){
				    return 0;
				}
				public String poutcomeDefault(){
				
					return null;
				
				}
				public String poutcomeComment(){
				
				    return "";
				
				}
				public String poutcomePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String poutcomeOriginalDbColumnName(){
				
					return "poutcome";
				
				}

				
			    public Float emp_var_rate;

				public Float getEmp_var_rate () {
					return this.emp_var_rate;
				}

				public Boolean emp_var_rateIsNullable(){
				    return true;
				}
				public Boolean emp_var_rateIsKey(){
				    return false;
				}
				public Integer emp_var_rateLength(){
				    return 3;
				}
				public Integer emp_var_ratePrecision(){
				    return 2;
				}
				public String emp_var_rateDefault(){
				
					return null;
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String emp_var_rateOriginalDbColumnName(){
				
					return "emp_var_rate";
				
				}

				
			    public Float cons_price_idx;

				public Float getCons_price_idx () {
					return this.cons_price_idx;
				}

				public Boolean cons_price_idxIsNullable(){
				    return true;
				}
				public Boolean cons_price_idxIsKey(){
				    return false;
				}
				public Integer cons_price_idxLength(){
				    return 6;
				}
				public Integer cons_price_idxPrecision(){
				    return 4;
				}
				public String cons_price_idxDefault(){
				
					return null;
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String cons_price_idxOriginalDbColumnName(){
				
					return "cons_price_idx";
				
				}

				
			    public Double cons_conf_idx;

				public Double getCons_conf_idx () {
					return this.cons_conf_idx;
				}

				public Boolean cons_conf_idxIsNullable(){
				    return true;
				}
				public Boolean cons_conf_idxIsKey(){
				    return false;
				}
				public Integer cons_conf_idxLength(){
				    return 5;
				}
				public Integer cons_conf_idxPrecision(){
				    return 2;
				}
				public String cons_conf_idxDefault(){
				
					return null;
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String cons_conf_idxOriginalDbColumnName(){
				
					return "cons_conf_idx";
				
				}

				
			    public Float euribor3m;

				public Float getEuribor3m () {
					return this.euribor3m;
				}

				public Boolean euribor3mIsNullable(){
				    return true;
				}
				public Boolean euribor3mIsKey(){
				    return false;
				}
				public Integer euribor3mLength(){
				    return 5;
				}
				public Integer euribor3mPrecision(){
				    return 4;
				}
				public String euribor3mDefault(){
				
					return null;
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String euribor3mOriginalDbColumnName(){
				
					return "euribor3m";
				
				}

				
			    public Double nr_employed;

				public Double getNr_employed () {
					return this.nr_employed;
				}

				public Boolean nr_employedIsNullable(){
				    return true;
				}
				public Boolean nr_employedIsKey(){
				    return false;
				}
				public Integer nr_employedLength(){
				    return null;
				}
				public Integer nr_employedPrecision(){
				    return 0;
				}
				public String nr_employedDefault(){
				
					return null;
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String nr_employedOriginalDbColumnName(){
				
					return "nr_employed";
				
				}

				
			    public String y;

				public String getY () {
					return this.y;
				}

				public Boolean yIsNullable(){
				    return true;
				}
				public Boolean yIsKey(){
				    return false;
				}
				public Integer yLength(){
				    return 2;
				}
				public Integer yPrecision(){
				    return 0;
				}
				public String yDefault(){
				
					return null;
				
				}
				public String yComment(){
				
				    return "";
				
				}
				public String yPattern(){
				
					return "dd-MM-yyyy";
				
				}
				public String yOriginalDbColumnName(){
				
					return "y";
				
				}

				


	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_01_load_staging.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_01_load_staging = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_01_load_staging, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging) {

        	try {

        		int length = 0;
		
						this.age = readInteger(dis);
					
					this.job = readString(dis);
					
					this.marital = readString(dis);
					
					this.education = readString(dis);
					
					this.Column4 = readString(dis);
					
					this.housing = readString(dis);
					
					this.loan = readString(dis);
					
					this.contact = readString(dis);
					
					this.month = readString(dis);
					
					this.day_of_week = readString(dis);
					
						this.duration = readInteger(dis);
					
						this.campaign = readInteger(dis);
					
						this.pdays = readInteger(dis);
					
						this.previous = readInteger(dis);
					
					this.poutcome = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.emp_var_rate = null;
           				} else {
           			    	this.emp_var_rate = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_price_idx = null;
           				} else {
           			    	this.cons_price_idx = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_conf_idx = null;
           				} else {
           			    	this.cons_conf_idx = dis.readDouble();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.euribor3m = null;
           				} else {
           			    	this.euribor3m = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.nr_employed = null;
           				} else {
           			    	this.nr_employed = dis.readDouble();
           				}
					
					this.y = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_01_load_staging) {

        	try {

        		int length = 0;
		
						this.age = readInteger(dis);
					
					this.job = readString(dis);
					
					this.marital = readString(dis);
					
					this.education = readString(dis);
					
					this.Column4 = readString(dis);
					
					this.housing = readString(dis);
					
					this.loan = readString(dis);
					
					this.contact = readString(dis);
					
					this.month = readString(dis);
					
					this.day_of_week = readString(dis);
					
						this.duration = readInteger(dis);
					
						this.campaign = readInteger(dis);
					
						this.pdays = readInteger(dis);
					
						this.previous = readInteger(dis);
					
					this.poutcome = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.emp_var_rate = null;
           				} else {
           			    	this.emp_var_rate = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_price_idx = null;
           				} else {
           			    	this.cons_price_idx = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.cons_conf_idx = null;
           				} else {
           			    	this.cons_conf_idx = dis.readDouble();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.euribor3m = null;
           				} else {
           			    	this.euribor3m = dis.readFloat();
           				}
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.nr_employed = null;
           				} else {
           			    	this.nr_employed = dis.readDouble();
           				}
					
					this.y = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.age,dos);
					
					// String
				
						writeString(this.job,dos);
					
					// String
				
						writeString(this.marital,dos);
					
					// String
				
						writeString(this.education,dos);
					
					// String
				
						writeString(this.Column4,dos);
					
					// String
				
						writeString(this.housing,dos);
					
					// String
				
						writeString(this.loan,dos);
					
					// String
				
						writeString(this.contact,dos);
					
					// String
				
						writeString(this.month,dos);
					
					// String
				
						writeString(this.day_of_week,dos);
					
					// Integer
				
						writeInteger(this.duration,dos);
					
					// Integer
				
						writeInteger(this.campaign,dos);
					
					// Integer
				
						writeInteger(this.pdays,dos);
					
					// Integer
				
						writeInteger(this.previous,dos);
					
					// String
				
						writeString(this.poutcome,dos);
					
					// Float
				
						if(this.emp_var_rate == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.emp_var_rate);
		            	}
					
					// Float
				
						if(this.cons_price_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.cons_price_idx);
		            	}
					
					// Double
				
						if(this.cons_conf_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.cons_conf_idx);
		            	}
					
					// Float
				
						if(this.euribor3m == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.euribor3m);
		            	}
					
					// Double
				
						if(this.nr_employed == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.nr_employed);
		            	}
					
					// String
				
						writeString(this.y,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.age,dos);
					
					// String
				
						writeString(this.job,dos);
					
					// String
				
						writeString(this.marital,dos);
					
					// String
				
						writeString(this.education,dos);
					
					// String
				
						writeString(this.Column4,dos);
					
					// String
				
						writeString(this.housing,dos);
					
					// String
				
						writeString(this.loan,dos);
					
					// String
				
						writeString(this.contact,dos);
					
					// String
				
						writeString(this.month,dos);
					
					// String
				
						writeString(this.day_of_week,dos);
					
					// Integer
				
						writeInteger(this.duration,dos);
					
					// Integer
				
						writeInteger(this.campaign,dos);
					
					// Integer
				
						writeInteger(this.pdays,dos);
					
					// Integer
				
						writeInteger(this.previous,dos);
					
					// String
				
						writeString(this.poutcome,dos);
					
					// Float
				
						if(this.emp_var_rate == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.emp_var_rate);
		            	}
					
					// Float
				
						if(this.cons_price_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.cons_price_idx);
		            	}
					
					// Double
				
						if(this.cons_conf_idx == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.cons_conf_idx);
		            	}
					
					// Float
				
						if(this.euribor3m == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeFloat(this.euribor3m);
		            	}
					
					// Double
				
						if(this.nr_employed == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.nr_employed);
		            	}
					
					// String
				
						writeString(this.y,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("age="+String.valueOf(age));
		sb.append(",job="+job);
		sb.append(",marital="+marital);
		sb.append(",education="+education);
		sb.append(",Column4="+Column4);
		sb.append(",housing="+housing);
		sb.append(",loan="+loan);
		sb.append(",contact="+contact);
		sb.append(",month="+month);
		sb.append(",day_of_week="+day_of_week);
		sb.append(",duration="+String.valueOf(duration));
		sb.append(",campaign="+String.valueOf(campaign));
		sb.append(",pdays="+String.valueOf(pdays));
		sb.append(",previous="+String.valueOf(previous));
		sb.append(",poutcome="+poutcome);
		sb.append(",emp_var_rate="+String.valueOf(emp_var_rate));
		sb.append(",cons_price_idx="+String.valueOf(cons_price_idx));
		sb.append(",cons_conf_idx="+String.valueOf(cons_conf_idx));
		sb.append(",euribor3m="+String.valueOf(euribor3m));
		sb.append(",nr_employed="+String.valueOf(nr_employed));
		sb.append(",y="+y);
	    sb.append("]");

	    return sb.toString();
    }
        public String toLogString(){
        	StringBuilder sb = new StringBuilder();
        	
        				if(age == null){
        					sb.append("<null>");
        				}else{
            				sb.append(age);
            			}
            		
        			sb.append("|");
        		
        				if(job == null){
        					sb.append("<null>");
        				}else{
            				sb.append(job);
            			}
            		
        			sb.append("|");
        		
        				if(marital == null){
        					sb.append("<null>");
        				}else{
            				sb.append(marital);
            			}
            		
        			sb.append("|");
        		
        				if(education == null){
        					sb.append("<null>");
        				}else{
            				sb.append(education);
            			}
            		
        			sb.append("|");
        		
        				if(Column4 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(Column4);
            			}
            		
        			sb.append("|");
        		
        				if(housing == null){
        					sb.append("<null>");
        				}else{
            				sb.append(housing);
            			}
            		
        			sb.append("|");
        		
        				if(loan == null){
        					sb.append("<null>");
        				}else{
            				sb.append(loan);
            			}
            		
        			sb.append("|");
        		
        				if(contact == null){
        					sb.append("<null>");
        				}else{
            				sb.append(contact);
            			}
            		
        			sb.append("|");
        		
        				if(month == null){
        					sb.append("<null>");
        				}else{
            				sb.append(month);
            			}
            		
        			sb.append("|");
        		
        				if(day_of_week == null){
        					sb.append("<null>");
        				}else{
            				sb.append(day_of_week);
            			}
            		
        			sb.append("|");
        		
        				if(duration == null){
        					sb.append("<null>");
        				}else{
            				sb.append(duration);
            			}
            		
        			sb.append("|");
        		
        				if(campaign == null){
        					sb.append("<null>");
        				}else{
            				sb.append(campaign);
            			}
            		
        			sb.append("|");
        		
        				if(pdays == null){
        					sb.append("<null>");
        				}else{
            				sb.append(pdays);
            			}
            		
        			sb.append("|");
        		
        				if(previous == null){
        					sb.append("<null>");
        				}else{
            				sb.append(previous);
            			}
            		
        			sb.append("|");
        		
        				if(poutcome == null){
        					sb.append("<null>");
        				}else{
            				sb.append(poutcome);
            			}
            		
        			sb.append("|");
        		
        				if(emp_var_rate == null){
        					sb.append("<null>");
        				}else{
            				sb.append(emp_var_rate);
            			}
            		
        			sb.append("|");
        		
        				if(cons_price_idx == null){
        					sb.append("<null>");
        				}else{
            				sb.append(cons_price_idx);
            			}
            		
        			sb.append("|");
        		
        				if(cons_conf_idx == null){
        					sb.append("<null>");
        				}else{
            				sb.append(cons_conf_idx);
            			}
            		
        			sb.append("|");
        		
        				if(euribor3m == null){
        					sb.append("<null>");
        				}else{
            				sb.append(euribor3m);
            			}
            		
        			sb.append("|");
        		
        				if(nr_employed == null){
        					sb.append("<null>");
        				}else{
            				sb.append(nr_employed);
            			}
            		
        			sb.append("|");
        		
        				if(y == null){
        					sb.append("<null>");
        				}else{
            				sb.append(y);
            			}
            		
        			sb.append("|");
        		
        	return sb.toString();
        }

    /**
     * Compare keys
     */
    public int compareTo(row2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public void tFileInputDelimited_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tFileInputDelimited_2", "tPZqaj_");

	
		String iterateId = "";
	
	
	String currentComponent = "";
	s("none");
	String cLabel =  null;
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		row2Struct row2 = new row2Struct();
out2Struct out2 = new out2Struct();





	
	/**
	 * [tDBOutput_2 begin ] start
	 */

	

	
		
		sh("tDBOutput_2");
		
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"out2");
			
		int tos_count_tDBOutput_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBOutput_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBOutput_2 = new StringBuilder();
                    log4jParamters_tDBOutput_2.append("Parameters:");
                            log4jParamters_tDBOutput_2.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("TABLE" + " = " + "\"raw_bank_g3\"");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("TABLE_ACTION" + " = " + "CREATE_IF_NOT_EXISTS");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("DATA_ACTION" + " = " + "INSERT");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true\"");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("USE_BATCH_SIZE" + " = " + "true");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("BATCH_SIZE" + " = " + "10000");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("ADD_COLS" + " = " + "[]");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("USE_FIELD_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("USE_HINT_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("ENABLE_DEBUG_MODE" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
                        log4jParamters_tDBOutput_2.append(" | ");
                            log4jParamters_tDBOutput_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
                        log4jParamters_tDBOutput_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + (log4jParamters_tDBOutput_2) );
                    } 
                } 
            new BytesLimit65535_tDBOutput_2().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBOutput_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final out2Struct out2) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("TABLE", String.valueOf("raw_bank_g3"));
                            component_parameters.put("TABLE_ACTION", String.valueOf("CREATE_IF_NOT_EXISTS"));
                            component_parameters.put("DATA_ACTION", String.valueOf("INSERT"));
                            component_parameters.put("SPECIFY_DATASOURCE_ALIAS", String.valueOf("false"));
                            component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
                            component_parameters.put("PROPERTIES", String.valueOf("noDatetimeStringSync=true"));
                            component_parameters.put("USE_BATCH_SIZE", String.valueOf("true"));
                            component_parameters.put("BATCH_SIZE", String.valueOf("10000"));
                            component_parameters.put("COMMIT_EVERY", String.valueOf("10000"));
                            component_parameters.put("ADD_COLS", String.valueOf("[]"));
                            component_parameters.put("USE_FIELD_OPTIONS", String.valueOf("false"));
                            component_parameters.put("USE_HINT_OPTIONS", String.valueOf("false"));
                            component_parameters.put("ENABLE_DEBUG_MODE", String.valueOf("false"));
                            component_parameters.put("ON_DUPLICATE_KEY_UPDATE", String.valueOf("false"));
                            component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlOutput"));
                
                
                
                } catch (java.lang.Exception e_tDBOutput_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBOutput_2", "tMysqlOutput",
          new ParameterUtil_tDBOutput_2().getParameter(out2));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBOutput_2", "tDBO_raw_bank_g3", "tMysqlOutput");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			







int nb_line_tDBOutput_2 = 0;
int nb_line_update_tDBOutput_2 = 0;
int nb_line_inserted_tDBOutput_2 = 0;
int nb_line_deleted_tDBOutput_2 = 0;
int nb_line_rejected_tDBOutput_2 = 0;

int deletedCount_tDBOutput_2=0;
int updatedCount_tDBOutput_2=0;
int insertedCount_tDBOutput_2=0;
int rowsToCommitCount_tDBOutput_2=0;
int rejectedCount_tDBOutput_2=0;

String tableName_tDBOutput_2 = "raw_bank_g3";
boolean whetherReject_tDBOutput_2 = false;

java.util.Calendar calendar_tDBOutput_2 = java.util.Calendar.getInstance();
calendar_tDBOutput_2.set(1, 0, 1, 0, 0, 0);
long year1_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
calendar_tDBOutput_2.set(10000, 0, 1, 0, 0, 0);
long year10000_tDBOutput_2 = calendar_tDBOutput_2.getTime().getTime();
long date_tDBOutput_2;

java.sql.Connection conn_tDBOutput_2 = null;
		
			
        String properties_tDBOutput_2 = "noDatetimeStringSync=true";
        if (properties_tDBOutput_2 == null || properties_tDBOutput_2.trim().length() == 0) {
            properties_tDBOutput_2 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBOutput_2.contains("rewriteBatchedStatements=")) {
                properties_tDBOutput_2 += "&rewriteBatchedStatements=true";
            }

            if (!properties_tDBOutput_2.contains("allowLoadLocalInfile=")) {
                properties_tDBOutput_2 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBOutput_2 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBOutput_2;

			String driverClass_tDBOutput_2 = "com.mysql.cj.jdbc.Driver";
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Driver ClassName: ")  + (driverClass_tDBOutput_2)  + (".") );
			String dbUser_tDBOutput_2 = context.DB_USER;
			

			
	final String decryptedPassword_tDBOutput_2 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");

			String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;
			java.lang.Class.forName(driverClass_tDBOutput_2);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection attempts to '")  + (url_tDBOutput_2)  + ("' with the username '")  + (dbUser_tDBOutput_2)  + ("'.") );
			conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2, dbPwd_tDBOutput_2);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection to '")  + (url_tDBOutput_2)  + ("' has succeeded.") );
			
	resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);
	
			conn_tDBOutput_2.setAutoCommit(false);
			int commitEvery_tDBOutput_2 = 10000;
			int commitCounter_tDBOutput_2 = 0;
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection is set auto commit to '")  + (conn_tDBOutput_2.getAutoCommit())  + ("'.") );

		int count_tDBOutput_2=0;
		
				
                                java.sql.DatabaseMetaData dbMetaData_tDBOutput_2 = conn_tDBOutput_2.getMetaData();
                                    java.sql.ResultSet rsTable_tDBOutput_2 = dbMetaData_tDBOutput_2.getTables(context.DB_NAME, null, null, new String[]{"TABLE"});
                                boolean whetherExist_tDBOutput_2 = false;
                                while(rsTable_tDBOutput_2.next()) {
                                    String table_tDBOutput_2 = rsTable_tDBOutput_2.getString("TABLE_NAME");
                                    if(table_tDBOutput_2.equalsIgnoreCase("raw_bank_g3")) {
                                        whetherExist_tDBOutput_2 = true;
                                        break;
                                    }
                                }
                                if(!whetherExist_tDBOutput_2) {
                                    try (java.sql.Statement stmtCreate_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Creating")  + (" table '")  + (tableName_tDBOutput_2)  + ("'.") );
                                        stmtCreate_tDBOutput_2.execute("CREATE TABLE `" + tableName_tDBOutput_2 + "`(`age` INT(3)  ,`job` VARCHAR(13)  ,`marital` VARCHAR(8)  ,`education` VARCHAR(19)  ,`has_default` VARCHAR(7)  ,`housing` VARCHAR(7)  ,`loan` VARCHAR(7)  ,`contact` VARCHAR(20)  ,`month` VARCHAR(3)  ,`day_of_week` VARCHAR(3)  ,`duration` INT(4)  ,`campaign` INT(1)  ,`pdays` INT(3)  ,`previous` INT(1)  ,`poutcome` VARCHAR(11)  ,`emp_var_rate` FLOAT(10,4)  ,`cons_price_idx` FLOAT(10,4)  ,`cons_conf_idx` DOUBLE(10,4)  ,`euribor3m` FLOAT(10,4)  ,`nr_employed` DOUBLE(12,4)  ,`y` VARCHAR(3)  ,`load_ts` DATETIME ,`load_source` VARCHAR(60)  )");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Create")  + (" table '")  + (tableName_tDBOutput_2)  + ("' has succeeded.") );
                                    }
                                }
			

		
						String insert_tDBOutput_2 = "INSERT INTO `" + "raw_bank_g3" + "` (`age`,`job`,`marital`,`education`,`has_default`,`housing`,`loan`,`contact`,`month`,`day_of_week`,`duration`,`campaign`,`pdays`,`previous`,`poutcome`,`emp_var_rate`,`cons_price_idx`,`cons_conf_idx`,`euribor3m`,`nr_employed`,`y`,`load_ts`,`load_source`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
						int batchSize_tDBOutput_2 = 10000;
						int batchSizeCounter_tDBOutput_2=0;
						    
						java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
						resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);


 



		

/**
 * [tDBOutput_2 begin ] stop
 */




	
	/**
	 * [tMap_2 begin ] start
	 */

	

	
		
		sh("tMap_2");
		
	
	s(currentComponent="tMap_2");
	
			
			
	
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"row2");
			
		int tos_count_tMap_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tMap_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tMap_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tMap_2 = new StringBuilder();
                    log4jParamters_tMap_2.append("Parameters:");
                            log4jParamters_tMap_2.append("LINK_STYLE" + " = " + "AUTO");
                        log4jParamters_tMap_2.append(" | ");
                            log4jParamters_tMap_2.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
                        log4jParamters_tMap_2.append(" | ");
                            log4jParamters_tMap_2.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
                        log4jParamters_tMap_2.append(" | ");
                            log4jParamters_tMap_2.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
                        log4jParamters_tMap_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tMap_2 - "  + (log4jParamters_tMap_2) );
                    } 
                } 
            new BytesLimit65535_tMap_2().limitLog4jByte();
            }
                boolean init_tMap_2_0 = true;
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tMap_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final row2Struct row2) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("LINK_STYLE", String.valueOf("AUTO"));
                            component_parameters.put("ROWS_BUFFER_SIZE", String.valueOf("2000000"));
                            component_parameters.put("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL", String.valueOf("true"));
                component_parameters.put("mapperData", String.valueOf("{\"inputTables\":[{\"name\":\"row2\",\"metadataTableEntries\":[{\"name\":\"age\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"job\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"marital\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"education\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"Column4\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"housing\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"loan\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"contact\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"month\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"day_of_week\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"duration\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"campaign\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"pdays\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"previous\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"poutcome\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"emp_var_rate\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_price_idx\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_conf_idx\",\"expression\":\"\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"euribor3m\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"nr_employed\",\"expression\":\"\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"y\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false}]}],\"outputTables\":[{\"name\":\"out2\",\"metadataTableEntries\":[{\"name\":\"age\",\"expression\":\"row2.age\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"job\",\"expression\":\"row2.job\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"marital\",\"expression\":\"row2.marital\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"education\",\"expression\":\"row2.education\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"has_default\",\"expression\":\"row2.Column4\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"housing\",\"expression\":\"row2.housing\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"loan\",\"expression\":\"row2.loan\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"contact\",\"expression\":\"row2.contact\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"month\",\"expression\":\"row2.month\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"day_of_week\",\"expression\":\"row2.day_of_week\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"duration\",\"expression\":\"row2.duration\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"campaign\",\"expression\":\"row2.campaign\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"pdays\",\"expression\":\"row2.pdays\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"previous\",\"expression\":\"row2.previous\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"poutcome\",\"expression\":\"row2.poutcome\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"emp_var_rate\",\"expression\":\"row2.emp_var_rate\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_price_idx\",\"expression\":\"row2.cons_price_idx\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_conf_idx\",\"expression\":\"row2.cons_conf_idx\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"euribor3m\",\"expression\":\"row2.euribor3m\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"nr_employed\",\"expression\":\"row2.nr_employed\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"y\",\"expression\":\"row2.y\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"load_ts\",\"expression\":\"TalendDate.getCurrentDate()\",\"type\":\"id_Date\",\"nullable\":false},{\"name\":\"load_source\",\"expression\":\"context.LOAD_SOURCE_TAG\",\"type\":\"id_String\",\"nullable\":false}]}],\"varsTables\":[{\"name\":\"Var\",\"metadataTableEntries\":[]}]}"));
                
                
                
                } catch (java.lang.Exception e_tMap_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tMap_2", "tMap",
          new ParameterUtil_tMap_2().getParameter(row2));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tMap_2", "tMap_1", "tMap");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			




// ###############################
// # Lookup's keys initialization
		int count_row2_tMap_2 = 0;
		
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_2__Struct  {
}
Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out2_tMap_2 = 0;
				
out2Struct out2_tmp = new out2Struct();
// ###############################

        
        



        









 



		

/**
 * [tMap_2 begin ] stop
 */




	
	/**
	 * [tFileInputDelimited_2 begin ] start
	 */

	

	
		
		sh("tFileInputDelimited_2");
		
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		
		int tos_count_tFileInputDelimited_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tFileInputDelimited_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tFileInputDelimited_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tFileInputDelimited_2 = new StringBuilder();
                    log4jParamters_tFileInputDelimited_2.append("Parameters:");
                            log4jParamters_tFileInputDelimited_2.append("USE_EXISTING_DYNAMIC" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("FILENAME" + " = " + "context.FILE_UCI_BANK");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("CSV_OPTION" + " = " + "true");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("CSVROWSEPARATOR" + " = " + "\"\\n\"");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("FIELDSEPARATOR" + " = " + "\";\"");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("ESCAPE_CHAR" + " = " + "\"\"\"");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("TEXT_ENCLOSURE" + " = " + "\"\\\"\"");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("HEADER" + " = " + "1");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("FOOTER" + " = " + "0");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("LIMIT" + " = " + "");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("REMOVE_EMPTY_ROW" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("UNCOMPRESS" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("ADVANCED_SEPARATOR" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("TRIMALL" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("TRIMSELECT" + " = " + "[{TRIM="+("false")+", SCHEMA_COLUMN="+("age")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("job")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("marital")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("education")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("Column4")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("housing")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("loan")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("contact")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("month")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("day_of_week")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("duration")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("campaign")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("pdays")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("previous")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("poutcome")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("emp_var_rate")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("cons_price_idx")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("cons_conf_idx")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("euribor3m")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("nr_employed")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("y")+"}]");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("CHECK_FIELDS_NUM" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("CHECK_DATE" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("ENCODING" + " = " + "\"UTF-8\"");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("ENABLE_DECODE" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                            log4jParamters_tFileInputDelimited_2.append("USE_HEADER_AS_IS" + " = " + "false");
                        log4jParamters_tFileInputDelimited_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tFileInputDelimited_2 - "  + (log4jParamters_tFileInputDelimited_2) );
                    } 
                } 
            new BytesLimit65535_tFileInputDelimited_2().limitLog4jByte();
            }
                boolean init_tFileInputDelimited_2_0 = true;
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tFileInputDelimited_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("USE_EXISTING_DYNAMIC", String.valueOf("false"));
                            component_parameters.put("FILENAME", String.valueOf(context.FILE_UCI_BANK));
                            component_parameters.put("CSV_OPTION", String.valueOf("true"));
                            component_parameters.put("CSVROWSEPARATOR", String.valueOf("\\n"));
                            component_parameters.put("FIELDSEPARATOR", String.valueOf(";"));
                            component_parameters.put("ESCAPE_CHAR", String.valueOf("\""));
                            component_parameters.put("TEXT_ENCLOSURE", String.valueOf("\""));
                            component_parameters.put("HEADER", String.valueOf("1"));
                            component_parameters.put("FOOTER", String.valueOf("0"));
                            component_parameters.put("LIMIT", String.valueOf(""));
                            component_parameters.put("REMOVE_EMPTY_ROW", String.valueOf("false"));
                            component_parameters.put("UNCOMPRESS", String.valueOf("false"));
                            component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
                            component_parameters.put("ADVANCED_SEPARATOR", String.valueOf("false"));
                            component_parameters.put("TRIMALL", String.valueOf("false"));
                            component_parameters.put("TRIMSELECT", String.valueOf(new StringBuilder().append("[{TRIM=").append("false").append(", SCHEMA_COLUMN=").append("age").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("job").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("marital").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("education").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("Column4").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("housing").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("loan").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("contact").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("month").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("day_of_week").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("duration").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("campaign").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("pdays").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("previous").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("poutcome").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("emp_var_rate").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("cons_price_idx").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("cons_conf_idx").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("euribor3m").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("nr_employed").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("y").append("}]").toString()));
                            component_parameters.put("CHECK_FIELDS_NUM", String.valueOf("false"));
                            component_parameters.put("CHECK_DATE", String.valueOf("false"));
                            component_parameters.put("ENCODING", String.valueOf("UTF-8"));
                            component_parameters.put("ENABLE_DECODE", String.valueOf("false"));
                            component_parameters.put("DESTINATION", String.valueOf(""));
                            component_parameters.put("USE_HEADER_AS_IS", String.valueOf("false"));
                
                
                
                } catch (java.lang.Exception e_tFileInputDelimited_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tFileInputDelimited_2", "tFileInputDelimited",
          new ParameterUtil_tFileInputDelimited_2().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tFileInputDelimited_2", "bank_full_additional", "tFileInputDelimited");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			
	
	
	
 
	
	
	final routines.system.RowState rowstate_tFileInputDelimited_2 = new routines.system.RowState();
	
	
				int nb_line_tFileInputDelimited_2 = 0;
				int footer_tFileInputDelimited_2 = 0;
				int totalLinetFileInputDelimited_2 = 0;
				int limittFileInputDelimited_2 = -1;
				int lastLinetFileInputDelimited_2 = -1;	
				
				char fieldSeparator_tFileInputDelimited_2[] = null;
				
				//support passing value (property: Field Separator) by 'context.fs' or 'globalMap.get("fs")'. 
				if ( ((String)";").length() > 0 ){
					fieldSeparator_tFileInputDelimited_2 = ((String)";").toCharArray();
				}else {			
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
			
				char rowSeparator_tFileInputDelimited_2[] = null;
			
				//support passing value (property: Row Separator) by 'context.rs' or 'globalMap.get("rs")'. 
				if ( ((String)"\n").length() > 0 ){
					rowSeparator_tFileInputDelimited_2 = ((String)"\n").toCharArray();
				}else {
					throw new IllegalArgumentException("Row Separator must be assigned a char."); 
				}
			
				Object filename_tFileInputDelimited_2 = /** Start field tFileInputDelimited_2:FILENAME */context.FILE_UCI_BANK/** End field tFileInputDelimited_2:FILENAME */;		
				com.talend.csv.CSVReader csvReadertFileInputDelimited_2 = null;
	
				try{
					
						String[] rowtFileInputDelimited_2=null;
						int currentLinetFileInputDelimited_2 = 0;
	        			int outputLinetFileInputDelimited_2 = 0;
						try {//TD110 begin
							if(filename_tFileInputDelimited_2 instanceof java.io.InputStream){
							
			int footer_value_tFileInputDelimited_2 = 0;
			if(footer_value_tFileInputDelimited_2 > 0){
				throw new java.lang.Exception("When the input source is a stream,footer shouldn't be bigger than 0.");
			}
		
								csvReadertFileInputDelimited_2=new com.talend.csv.CSVReader((java.io.InputStream)filename_tFileInputDelimited_2, fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
							}else{
								csvReadertFileInputDelimited_2=new com.talend.csv.CSVReader(String.valueOf(filename_tFileInputDelimited_2),fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
		        			}
					
					
					csvReadertFileInputDelimited_2.setTrimWhitespace(false);
					if ( (rowSeparator_tFileInputDelimited_2[0] != '\n') && (rowSeparator_tFileInputDelimited_2[0] != '\r') )
	        			csvReadertFileInputDelimited_2.setLineEnd(""+rowSeparator_tFileInputDelimited_2[0]);
						
	        				csvReadertFileInputDelimited_2.setQuoteChar('\"');
						
	            				//?????doesn't work for other escapeChar
	            				//the default escape mode is double escape
	            				csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());
							      
		
			
						if(footer_tFileInputDelimited_2 > 0){
						for(totalLinetFileInputDelimited_2=0;totalLinetFileInputDelimited_2 < 1; totalLinetFileInputDelimited_2++){
							csvReadertFileInputDelimited_2.readNext();
						}
						csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);
			            while (csvReadertFileInputDelimited_2.readNext()) {
							
	                
	                		totalLinetFileInputDelimited_2++;
	                
							
	                
			            }
	            		int lastLineTemptFileInputDelimited_2 = totalLinetFileInputDelimited_2 - footer_tFileInputDelimited_2   < 0? 0 : totalLinetFileInputDelimited_2 - footer_tFileInputDelimited_2 ;
	            		if(lastLinetFileInputDelimited_2 > 0){
	                		lastLinetFileInputDelimited_2 = lastLinetFileInputDelimited_2 < lastLineTemptFileInputDelimited_2 ? lastLinetFileInputDelimited_2 : lastLineTemptFileInputDelimited_2; 
	            		}else {
	                		lastLinetFileInputDelimited_2 = lastLineTemptFileInputDelimited_2;
	            		}
	         
			          	csvReadertFileInputDelimited_2.close();
				        if(filename_tFileInputDelimited_2 instanceof java.io.InputStream){
				 			csvReadertFileInputDelimited_2=new com.talend.csv.CSVReader((java.io.InputStream)filename_tFileInputDelimited_2, fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
		        		}else{
							csvReadertFileInputDelimited_2=new com.talend.csv.CSVReader(String.valueOf(filename_tFileInputDelimited_2),fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
						}
						csvReadertFileInputDelimited_2.setTrimWhitespace(false);
						if ( (rowSeparator_tFileInputDelimited_2[0] != '\n') && (rowSeparator_tFileInputDelimited_2[0] != '\r') )	
	        				csvReadertFileInputDelimited_2.setLineEnd(""+rowSeparator_tFileInputDelimited_2[0]);
						
							csvReadertFileInputDelimited_2.setQuoteChar('\"');
						
	        				//?????doesn't work for other escapeChar
	        				//the default escape mode is double escape
	        				csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());
							  
	        		}
	        
			        if(limittFileInputDelimited_2 != 0){
			        	for(currentLinetFileInputDelimited_2=0;currentLinetFileInputDelimited_2 < 1;currentLinetFileInputDelimited_2++){
			        		csvReadertFileInputDelimited_2.readNext();
			        	}
			        }
			        csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);
	        
	    		} catch(java.lang.Exception e) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",e.getMessage());
					
						
							log.error("tFileInputDelimited_2 - " +e.getMessage());
						
						System.err.println(e.getMessage());
					
	    		}//TD110 end
	        
			    
			    	log.info("tFileInputDelimited_2 - Retrieving records from the datasource.");
			    
	        	while ( limittFileInputDelimited_2 != 0 && csvReadertFileInputDelimited_2!=null && csvReadertFileInputDelimited_2.readNext() ) { 
	        		rowstate_tFileInputDelimited_2.reset();
	        
		        	rowtFileInputDelimited_2=csvReadertFileInputDelimited_2.getValues();
		        	
					
	        	
	        	
	        		currentLinetFileInputDelimited_2++;
	            
		            if(lastLinetFileInputDelimited_2 > -1 && currentLinetFileInputDelimited_2 > lastLinetFileInputDelimited_2) {
		                break;
	    	        }
	        	    outputLinetFileInputDelimited_2++;
	            	if (limittFileInputDelimited_2 > 0 && outputLinetFileInputDelimited_2 > limittFileInputDelimited_2) {
	                	break;
	            	}  
	                                                                      
					
	    							row2 = null;			
								
								boolean whetherReject_tFileInputDelimited_2 = false;
								row2 = new row2Struct();
								try {			
									
				char fieldSeparator_tFileInputDelimited_2_ListType[] = null;
				//support passing value (property: Field Separator) by 'context.fs' or 'globalMap.get("fs")'. 
				if ( ((String)";").length() > 0 ){
					fieldSeparator_tFileInputDelimited_2_ListType = ((String)";").toCharArray();
				}else {			
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
				if(rowtFileInputDelimited_2.length == 1 && ("\015").equals(rowtFileInputDelimited_2[0])){//empty line when row separator is '\n'
					
							row2.age = null;
					
							row2.job = null;
					
							row2.marital = null;
					
							row2.education = null;
					
							row2.Column4 = null;
					
							row2.housing = null;
					
							row2.loan = null;
					
							row2.contact = null;
					
							row2.month = null;
					
							row2.day_of_week = null;
					
							row2.duration = null;
					
							row2.campaign = null;
					
							row2.pdays = null;
					
							row2.previous = null;
					
							row2.poutcome = null;
					
							row2.emp_var_rate = null;
					
							row2.cons_price_idx = null;
					
							row2.cons_conf_idx = null;
					
							row2.euribor3m = null;
					
							row2.nr_employed = null;
					
							row2.y = null;
					
				}else{
					
	                int columnIndexWithD_tFileInputDelimited_2 = 0; //Column Index 
	                
						columnIndexWithD_tFileInputDelimited_2 = 0;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.age = ParserUtils.parseTo_Integer(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"age", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.age = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.age = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 1;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.job = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.job = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 2;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.marital = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.marital = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 3;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.education = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.education = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 4;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.Column4 = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.Column4 = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 5;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.housing = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.housing = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 6;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.loan = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.loan = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 7;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.contact = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.contact = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 8;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.month = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.month = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 9;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.day_of_week = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.day_of_week = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 10;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.duration = ParserUtils.parseTo_Integer(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"duration", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.duration = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.duration = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 11;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.campaign = ParserUtils.parseTo_Integer(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"campaign", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.campaign = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.campaign = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 12;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.pdays = ParserUtils.parseTo_Integer(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"pdays", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.pdays = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.pdays = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 13;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.previous = ParserUtils.parseTo_Integer(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"previous", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.previous = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.previous = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 14;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.poutcome = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.poutcome = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 15;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.emp_var_rate = ParserUtils.parseTo_Float(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"emp_var_rate", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.emp_var_rate = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.emp_var_rate = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 16;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.cons_price_idx = ParserUtils.parseTo_Float(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"cons_price_idx", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.cons_price_idx = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.cons_price_idx = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 17;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.cons_conf_idx = ParserUtils.parseTo_Double(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"cons_conf_idx", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.cons_conf_idx = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.cons_conf_idx = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 18;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.euribor3m = ParserUtils.parseTo_Float(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"euribor3m", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.euribor3m = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.euribor3m = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 19;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
								
									if(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {
									
										row2.nr_employed = ParserUtils.parseTo_Double(rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"nr_employed", "row2", rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2], ex_tFileInputDelimited_2), ex_tFileInputDelimited_2));
										}
    								}else{
    									
											
												row2.nr_employed = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row2.nr_employed = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_2 = 20;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length){
						
						
							
									row2.y = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];
									
							
						
						}else{
						
							
								row2.y = null;
							
						
						}
						
						
					
				}
				
									
									if(rowstate_tFileInputDelimited_2.getException()!=null) {
										throw rowstate_tFileInputDelimited_2.getException();
									}
									
									
	    						} catch (java.lang.Exception e) {
globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",e.getMessage());
							        whetherReject_tFileInputDelimited_2 = true;
        							
											log.error("tFileInputDelimited_2 - " +e.getMessage());
										
                							System.err.println(e.getMessage());
                							row2 = null;
                						
            							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());
            							
	    						}
	
							
			log.debug("tFileInputDelimited_2 - Retrieving the record " + (nb_line_tFileInputDelimited_2+1) + ".");
		

 



		

/**
 * [tFileInputDelimited_2 begin ] stop
 */

	
	/**
	 * [tFileInputDelimited_2 main ] start
	 */

	

	
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		

 
      
      
      // QTUP-3575
      if(enableLineage && init_tFileInputDelimited_2_0) {
      class SchemaUtil_row2 {
      
          private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
              java.util.Map<String, String> field = new java.util.HashMap<>();
              field.put("name", values[0]);
              field.put("origin_name", values[1]);
              field.put("iskey", values[2]);
              field.put("talend_type", values[3]);
              field.put("type", values[4]);
              field.put("nullable", values[5]);
              field.put("pattern", values[6]);
              field.put("length", values[7]);
              field.put("precision", values[8]);
              schema.add(field);
          }
          
          public java.util.List<java.util.Map<String, String>> getSchema(final row2Struct row2) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(row2 == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","null","true","dd-MM-yyyy","2","0");
              a(s,"job","job","false","id_String","null","true","dd-MM-yyyy","13","0");
              a(s,"marital","marital","false","id_String","null","true","dd-MM-yyyy","8","0");
              a(s,"education","education","false","id_String","null","true","dd-MM-yyyy","19","0");
              a(s,"Column4","Column4","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"housing","housing","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"loan","loan","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"contact","contact","false","id_String","null","true","dd-MM-yyyy","9","0");
              a(s,"month","month","false","id_String","null","true","dd-MM-yyyy","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","null","true","dd-MM-yyyy","3","0");
              a(s,"duration","duration","false","id_Integer","null","true","dd-MM-yyyy","4","0");
              a(s,"campaign","campaign","false","id_Integer","null","true","dd-MM-yyyy","1","0");
              a(s,"pdays","pdays","false","id_Integer","null","true","dd-MM-yyyy","3","0");
              a(s,"previous","previous","false","id_Integer","null","true","dd-MM-yyyy","1","0");
              a(s,"poutcome","poutcome","false","id_String","null","true","dd-MM-yyyy","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","null","true","dd-MM-yyyy","3","2");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","null","true","dd-MM-yyyy","6","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","null","true","dd-MM-yyyy","5","2");
              a(s,"euribor3m","euribor3m","false","id_Float","null","true","dd-MM-yyyy","5","4");
              a(s,"nr_employed","nr_employed","false","id_Double","null","true","dd-MM-yyyy","null","0");
              a(s,"y","y","false","id_String","null","true","dd-MM-yyyy","2","0");
              return s;
          }
          
      }
      
      if(row2 != null) {
          talendJobLog.addConnectionSchemaMessage("tFileInputDelimited_2","tFileInputDelimited",
            "tMap_2","tMap", "row2" + iterateId, new SchemaUtil_row2().getSchema(row2));
          talendJobLogProcess(globalMap);
          init_tFileInputDelimited_2_0 = false;
      }
      
      }
  // QTUP-3575


	tos_count_tFileInputDelimited_2++;

		

/**
 * [tFileInputDelimited_2 main ] stop
 */

	
	/**
	 * [tFileInputDelimited_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		

 



		

/**
 * [tFileInputDelimited_2 process_data_begin ] stop
 */

// Start of branch "row2"
if(row2 != null) { 



	
	/**
	 * [tMap_2 main ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"row2","tFileInputDelimited_2","bank_full_additional","tFileInputDelimited","tMap_2","tMap_1","tMap"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("row2 - " + (row2==null? "": row2.toLogString()));
    			}
    		

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;
		
		// ###############################
		// # Input tables (lookups)
		
		boolean rejectedInnerJoin_tMap_2 = false;
		boolean mainRowRejected_tMap_2 = false;
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
        // ###############################
        // # Output tables

out2 = null;


// # Output table : 'out2'
count_out2_tMap_2++;

out2_tmp.age = row2.age ;
out2_tmp.job = row2.job ;
out2_tmp.marital = row2.marital ;
out2_tmp.education = row2.education ;
out2_tmp.has_default = row2.Column4 ;
out2_tmp.housing = row2.housing ;
out2_tmp.loan = row2.loan ;
out2_tmp.contact = row2.contact ;
out2_tmp.month = row2.month ;
out2_tmp.day_of_week = row2.day_of_week ;
out2_tmp.duration = row2.duration ;
out2_tmp.campaign = row2.campaign ;
out2_tmp.pdays = row2.pdays ;
out2_tmp.previous = row2.previous ;
out2_tmp.poutcome = row2.poutcome ;
out2_tmp.emp_var_rate = row2.emp_var_rate ;
out2_tmp.cons_price_idx = row2.cons_price_idx ;
out2_tmp.cons_conf_idx = row2.cons_conf_idx ;
out2_tmp.euribor3m = row2.euribor3m ;
out2_tmp.nr_employed = row2.nr_employed ;
out2_tmp.y = row2.y ;
out2_tmp.load_ts = TalendDate.getCurrentDate() ;
out2_tmp.load_source = context.LOAD_SOURCE_TAG ;
out2 = out2_tmp;
log.debug("tMap_2 - Outputting the record " + count_out2_tMap_2 + " of the output table 'out2'.");

// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_2 = false;










 
      
      
      // QTUP-3575
      if(enableLineage && init_tMap_2_0) {
      class SchemaUtil_out2 {
      
          private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
              java.util.Map<String, String> field = new java.util.HashMap<>();
              field.put("name", values[0]);
              field.put("origin_name", values[1]);
              field.put("iskey", values[2]);
              field.put("talend_type", values[3]);
              field.put("type", values[4]);
              field.put("nullable", values[5]);
              field.put("pattern", values[6]);
              field.put("length", values[7]);
              field.put("precision", values[8]);
              schema.add(field);
          }
          
          public java.util.List<java.util.Map<String, String>> getSchema(final out2Struct out2) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(out2 == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","null","true","dd-MM-yyyy","3","0");
              a(s,"job","job","false","id_String","null","true","dd-MM-yyyy","13","0");
              a(s,"marital","marital","false","id_String","null","true","dd-MM-yyyy","8","0");
              a(s,"education","education","false","id_String","null","true","dd-MM-yyyy","19","0");
              a(s,"has_default","has_default","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"housing","housing","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"loan","loan","false","id_String","null","true","dd-MM-yyyy","7","0");
              a(s,"contact","contact","false","id_String","null","true","dd-MM-yyyy","20","0");
              a(s,"month","month","false","id_String","null","true","dd-MM-yyyy","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","null","true","dd-MM-yyyy","3","0");
              a(s,"duration","duration","false","id_Integer","null","true","dd-MM-yyyy","4","0");
              a(s,"campaign","campaign","false","id_Integer","null","true","dd-MM-yyyy","1","0");
              a(s,"pdays","pdays","false","id_Integer","null","true","dd-MM-yyyy","3","0");
              a(s,"previous","previous","false","id_Integer","null","true","dd-MM-yyyy","1","0");
              a(s,"poutcome","poutcome","false","id_String","null","true","dd-MM-yyyy","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","null","true","dd-MM-yyyy","10","4");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","null","true","dd-MM-yyyy","10","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","null","true","dd-MM-yyyy","10","4");
              a(s,"euribor3m","euribor3m","false","id_Float","null","true","dd-MM-yyyy","10","4");
              a(s,"nr_employed","nr_employed","false","id_Double","null","true","dd-MM-yyyy","12","4");
              a(s,"y","y","false","id_String","null","true","dd-MM-yyyy","3","0");
              a(s,"load_ts","load_ts","false","id_Date","","true","yyyy-MM-dd HH:mm:ss","null","null");
              a(s,"load_source","load_source","false","id_String","","true","","60","null");
              return s;
          }
          
      }
      
      if(out2 != null) {
          talendJobLog.addConnectionSchemaMessage("tMap_2","tMap",
            "tDBOutput_2","tMysqlOutput", "out2" + iterateId, new SchemaUtil_out2().getSchema(out2));
          talendJobLogProcess(globalMap);
          init_tMap_2_0 = false;
      }
      
      }
  // QTUP-3575


	tos_count_tMap_2++;

		

/**
 * [tMap_2 main ] stop
 */

	
	/**
	 * [tMap_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	

 



		

/**
 * [tMap_2 process_data_begin ] stop
 */

// Start of branch "out2"
if(out2 != null) { 



	
	/**
	 * [tDBOutput_2 main ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"out2","tMap_2","tMap_1","tMap","tDBOutput_2","tDBO_raw_bank_g3","tMysqlOutput"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("out2 - " + (out2==null? "": out2.toLogString()));
    			}
    		



        whetherReject_tDBOutput_2 = false;
                            if(out2.age == null) {
pstmt_tDBOutput_2.setNull(1, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_2.setInt(1, out2.age);
}

                            if(out2.job == null) {
pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(2, out2.job);
}

                            if(out2.marital == null) {
pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(3, out2.marital);
}

                            if(out2.education == null) {
pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(4, out2.education);
}

                            if(out2.has_default == null) {
pstmt_tDBOutput_2.setNull(5, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(5, out2.has_default);
}

                            if(out2.housing == null) {
pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(6, out2.housing);
}

                            if(out2.loan == null) {
pstmt_tDBOutput_2.setNull(7, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(7, out2.loan);
}

                            if(out2.contact == null) {
pstmt_tDBOutput_2.setNull(8, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(8, out2.contact);
}

                            if(out2.month == null) {
pstmt_tDBOutput_2.setNull(9, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(9, out2.month);
}

                            if(out2.day_of_week == null) {
pstmt_tDBOutput_2.setNull(10, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(10, out2.day_of_week);
}

                            if(out2.duration == null) {
pstmt_tDBOutput_2.setNull(11, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_2.setInt(11, out2.duration);
}

                            if(out2.campaign == null) {
pstmt_tDBOutput_2.setNull(12, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_2.setInt(12, out2.campaign);
}

                            if(out2.pdays == null) {
pstmt_tDBOutput_2.setNull(13, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_2.setInt(13, out2.pdays);
}

                            if(out2.previous == null) {
pstmt_tDBOutput_2.setNull(14, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_2.setInt(14, out2.previous);
}

                            if(out2.poutcome == null) {
pstmt_tDBOutput_2.setNull(15, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(15, out2.poutcome);
}

                            if(out2.emp_var_rate == null) {
pstmt_tDBOutput_2.setNull(16, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_2.setFloat(16, out2.emp_var_rate);
}

                            if(out2.cons_price_idx == null) {
pstmt_tDBOutput_2.setNull(17, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_2.setFloat(17, out2.cons_price_idx);
}

                            if(out2.cons_conf_idx == null) {
pstmt_tDBOutput_2.setNull(18, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_2.setDouble(18, out2.cons_conf_idx);
}

                            if(out2.euribor3m == null) {
pstmt_tDBOutput_2.setNull(19, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_2.setFloat(19, out2.euribor3m);
}

                            if(out2.nr_employed == null) {
pstmt_tDBOutput_2.setNull(20, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_2.setDouble(20, out2.nr_employed);
}

                            if(out2.y == null) {
pstmt_tDBOutput_2.setNull(21, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(21, out2.y);
}

                            if(out2.load_ts != null) {
date_tDBOutput_2 = out2.load_ts.getTime();
if(date_tDBOutput_2 < year1_tDBOutput_2 || date_tDBOutput_2 >= year10000_tDBOutput_2) {
pstmt_tDBOutput_2.setString(22, "0000-00-00 00:00:00");
} else {pstmt_tDBOutput_2.setTimestamp(22, new java.sql.Timestamp(date_tDBOutput_2));
}
} else {
pstmt_tDBOutput_2.setNull(22, java.sql.Types.DATE);
}

                            if(out2.load_source == null) {
pstmt_tDBOutput_2.setNull(23, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_2.setString(23, out2.load_source);
}

                    pstmt_tDBOutput_2.addBatch();
                    nb_line_tDBOutput_2++;

                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Adding the record ")  + (nb_line_tDBOutput_2)  + (" to the ")  + ("INSERT")  + (" batch.") );
                      batchSizeCounter_tDBOutput_2++;
                if ( batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2) {
                try {
                        int countSum_tDBOutput_2 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                        for(int countEach_tDBOutput_2: pstmt_tDBOutput_2.executeBatch()) {
                            countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
                        }
                        rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                        insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
                }catch (java.sql.BatchUpdateException e){
                    globalMap.put("tDBOutput_2_ERROR_MESSAGE",e.getMessage());
                    int countSum_tDBOutput_2 = 0;
                    for(int countEach_tDBOutput_2: e.getUpdateCounts()) {
                        countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
                    }
                    rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
                    insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
                    System.err.println(e.getMessage());
            log.error("tDBOutput_2 - "  + (e.getMessage()) );
                }

                batchSizeCounter_tDBOutput_2 = 0;
            }
                commitCounter_tDBOutput_2++;

                if(commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {

                        try {
                                int countSum_tDBOutput_2 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                                for(int countEach_tDBOutput_2: pstmt_tDBOutput_2.executeBatch()) {
                                    countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : 1);
                                }
                                rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                                insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
                        }catch (java.sql.BatchUpdateException e){
                            globalMap.put("tDBOutput_2_ERROR_MESSAGE",e.getMessage());
                            int countSum_tDBOutput_2 = 0;
                            for(int countEach_tDBOutput_2: e.getUpdateCounts()) {
                                countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
                            }
                            rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
                            insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
                            System.err.println(e.getMessage());
            log.error("tDBOutput_2 - "  + (e.getMessage()) );

                        }
                    if(rowsToCommitCount_tDBOutput_2 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_2)  + (" record(s).") );
                    }
                    conn_tDBOutput_2.commit();
                    if(rowsToCommitCount_tDBOutput_2 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection commit has succeeded.") );
                        rowsToCommitCount_tDBOutput_2 = 0;
                    }
                    commitCounter_tDBOutput_2=0;
                }

 


	tos_count_tDBOutput_2++;

		

/**
 * [tDBOutput_2 main ] stop
 */

	
	/**
	 * [tDBOutput_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		

 



		

/**
 * [tDBOutput_2 process_data_begin ] stop
 */

	
	/**
	 * [tDBOutput_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		

 



		

/**
 * [tDBOutput_2 process_data_end ] stop
 */


} // End of branch "out2"




	
	/**
	 * [tMap_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	

 



		

/**
 * [tMap_2 process_data_end ] stop
 */


} // End of branch "row2"




	
	/**
	 * [tFileInputDelimited_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		

 



		

/**
 * [tFileInputDelimited_2 process_data_end ] stop
 */

	
	/**
	 * [tFileInputDelimited_2 end ] start
	 */

	

	
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		


				nb_line_tFileInputDelimited_2++;
			}
			
			}finally{
    			if(!(filename_tFileInputDelimited_2 instanceof java.io.InputStream)){
    				if(csvReadertFileInputDelimited_2!=null){
    					csvReadertFileInputDelimited_2.close();
    				}
    			}
    			if(csvReadertFileInputDelimited_2!=null){
    				globalMap.put("tFileInputDelimited_2_NB_LINE",nb_line_tFileInputDelimited_2);
    			}
				
					log.info("tFileInputDelimited_2 - Retrieved records count: "+ nb_line_tFileInputDelimited_2 + ".");
				
			}
						  

 
                if(log.isDebugEnabled())
            log.debug("tFileInputDelimited_2 - "  + ("Done.") );

ok_Hash.put("tFileInputDelimited_2", true);
end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());




		

/**
 * [tFileInputDelimited_2 end ] stop
 */


	
	/**
	 * [tMap_2 end ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	


// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_2 - Written records count in the table 'out2': " + count_out2_tMap_2 + ".");





			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"row2",2,0,
			 			"tFileInputDelimited_2","bank_full_additional","tFileInputDelimited","tMap_2","tMap_1","tMap","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tMap_2 - "  + ("Done.") );

ok_Hash.put("tMap_2", true);
end_Hash.put("tMap_2", System.currentTimeMillis());




		

/**
 * [tMap_2 end ] stop
 */


	
	/**
	 * [tDBOutput_2 end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		



				
					try {
						if (batchSizeCounter_tDBOutput_2 != 0) {
							int countSum_tDBOutput_2 = 0;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
							for(int countEach_tDBOutput_2: pstmt_tDBOutput_2.executeBatch()) {
								countSum_tDBOutput_2 += (countEach_tDBOutput_2 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
							}
							rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
							
								insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
							
						}
					}catch (java.sql.BatchUpdateException e){
						globalMap.put(currentComponent+"_ERROR_MESSAGE",e.getMessage());
						
							int countSum_tDBOutput_2 = 0;
							for(int countEach_tDBOutput_2: e.getUpdateCounts()) {
								countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
							}
							rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;
							
								insertedCount_tDBOutput_2 += countSum_tDBOutput_2;
								
            log.error("tDBOutput_2 - "  + (e.getMessage()) );
							System.err.println(e.getMessage());
							
					}
					batchSizeCounter_tDBOutput_2 = 0;
					
			
		

		if(pstmt_tDBOutput_2 != null) {
			
				pstmt_tDBOutput_2.close();
				resourceMap.remove("pstmt_tDBOutput_2");
			
		}
		
	resourceMap.put("statementClosed_tDBOutput_2", true);
	
			if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_2)  + (" record(s).") );
			}
			conn_tDBOutput_2.commit();
			if (commitCounter_tDBOutput_2 > 0 && rowsToCommitCount_tDBOutput_2 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection commit has succeeded.") );
				rowsToCommitCount_tDBOutput_2 = 0;
			}
			commitCounter_tDBOutput_2 = 0;
			
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Closing the connection to the database.") );
		conn_tDBOutput_2 .close();
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection to the database has closed.") );
		resourceMap.put("finish_tDBOutput_2", true);
	

	nb_line_deleted_tDBOutput_2=nb_line_deleted_tDBOutput_2+ deletedCount_tDBOutput_2;
	nb_line_update_tDBOutput_2=nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
	nb_line_inserted_tDBOutput_2=nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
	nb_line_rejected_tDBOutput_2=nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;
	
        globalMap.put("tDBOutput_2_NB_LINE",nb_line_tDBOutput_2);
        globalMap.put("tDBOutput_2_NB_LINE_UPDATED",nb_line_update_tDBOutput_2);
        globalMap.put("tDBOutput_2_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_2);
        globalMap.put("tDBOutput_2_NB_LINE_DELETED",nb_line_deleted_tDBOutput_2);
        globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);
    

	
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Has ")  + ("inserted")  + (" ")  + (nb_line_inserted_tDBOutput_2)  + (" record(s).") );

			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"out2",2,0,
			 			"tMap_2","tMap_1","tMap","tDBOutput_2","tDBO_raw_bank_g3","tMysqlOutput","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Done.") );

ok_Hash.put("tDBOutput_2", true);
end_Hash.put("tDBOutput_2", System.currentTimeMillis());




		

/**
 * [tDBOutput_2 end ] stop
 */







				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				    if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
					   log.fatal(currentComponent + " " + e.getMessage(),e);
					}
				
				TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tFileInputDelimited_2 finally ] start
	 */

	

	
	
	s(currentComponent="tFileInputDelimited_2");
	
			
			
	
			cLabel="bank_full_additional";
		

 



		

/**
 * [tFileInputDelimited_2 finally ] stop
 */


	
	/**
	 * [tMap_2 finally ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	

 



		

/**
 * [tMap_2 finally ] stop
 */


	
	/**
	 * [tDBOutput_2 finally ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_2");
	
			
			
	
			cLabel="tDBO_raw_bank_g3";
		



    try {
    if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
                if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_2")) != null) {
                    pstmtToClose_tDBOutput_2.close();
                }
    }
    } finally {
        if(resourceMap.get("finish_tDBOutput_2") == null){
            java.sql.Connection ctn_tDBOutput_2 = null;
            if((ctn_tDBOutput_2 = (java.sql.Connection)resourceMap.get("conn_tDBOutput_2")) != null){
                try {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Closing the connection to the database.") );
                    ctn_tDBOutput_2.close();
                if(log.isDebugEnabled())
            log.debug("tDBOutput_2 - "  + ("Connection to the database has closed.") );
                } catch (java.sql.SQLException sqlEx_tDBOutput_2) {
                    String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :" + sqlEx_tDBOutput_2.getMessage();
            log.error("tDBOutput_2 - "  + (errorMessage_tDBOutput_2) );
                    System.err.println(errorMessage_tDBOutput_2);
                }
            }
        }
    }
 



		

/**
 * [tDBOutput_2 finally ] stop
 */







				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 1);
	}
	


public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;


	
		String iterateId = "";
	
	
	String currentComponent = "";
	s("none");
	String cLabel =  null;
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;





	
	/**
	 * [talendJobLog begin ] start
	 */

	

	
		
		sh("talendJobLog");
		
	
	s(currentComponent="talendJobLog");
	
			
			
	
		int tos_count_talendJobLog = 0;
		

	for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
		org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
			.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid).custom("father_pid", fatherPid).custom("root_pid", rootPid);
		org.talend.logging.audit.Context log_context_talendJobLog = null;
		
		
		if(jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE){
			long timeMS = jcm.end_time - jcm.start_time;
			String duration = String.valueOf(timeMS);
			
			log_context_talendJobLog = builder_talendJobLog
				.sourceId(jcm.sourceId).sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
				.targetId(jcm.targetId).targetLabel(jcm.targetLabel).targetConnectorType(jcm.targetComponentName)
				.connectionName(jcm.current_connector).rows(jcm.row_count).duration(duration).build();
			auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
			log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
			auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
			long timeMS = jcm.end_time - jcm.start_time;
			String duration = String.valueOf(timeMS);
		
			log_context_talendJobLog = builder_talendJobLog
				.timestamp(jcm.moment).duration(duration).status(jcm.status).build();
			auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
			log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
				.connectorType(jcm.component_name).connectorId(jcm.component_id).connectorLabel(jcm.component_label).build();
			auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {//log current component input line
			long timeMS = jcm.end_time - jcm.start_time;
			String duration = String.valueOf(timeMS);
			
			log_context_talendJobLog = builder_talendJobLog
				.connectorType(jcm.component_name).connectorId(jcm.component_id).connectorLabel(jcm.component_label)
				.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
				.rows(jcm.total_row_number).duration(duration).build();
			auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {//log current component output/reject line
			long timeMS = jcm.end_time - jcm.start_time;
			String duration = String.valueOf(timeMS);
			
			log_context_talendJobLog = builder_talendJobLog
				.connectorType(jcm.component_name).connectorId(jcm.component_id).connectorLabel(jcm.component_label)
				.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
				.rows(jcm.total_row_number).duration(duration).build();
			auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
			java.lang.Exception e_talendJobLog = jcm.exception;
			if(e_talendJobLog!=null) {
				try(java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
					e_talendJobLog.printStackTrace(pw_talendJobLog);
					builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
				}
			}

			if(jcm.extra_info!=null) {
				builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
			}
				
			log_context_talendJobLog = builder_talendJobLog
				.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
				.connectorId(jcm.component_id)
				.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label).build();

			auditLogger_talendJobLog.exception(log_context_talendJobLog);
		}
		
		
		
		if(jcm.log_type == JobStructureCatcherUtils.LogType.RUNTIMEPARAMETER) {
			builder_talendJobLog.connectorType(jcm.component_name).connectorId(jcm.component_id);
			
			for(java.util.Map.Entry<String,String> entry : jcm.component_parameters.entrySet()) {
				builder_talendJobLog.custom("P_" + entry.getKey(), entry.getValue());
			}
			
			log_context_talendJobLog = builder_talendJobLog.build();
			
			runtime_lineage_logger_talendJobLog.componentParameters(log_context_talendJobLog);
		} else if(jcm.log_type == JobStructureCatcherUtils.LogType.RUNTIMESCHEMA) {
			builder_talendJobLog.sourceConnectorType(jcm.sourceComponentName).sourceId(jcm.sourceId)
				.connectionName(jcm.current_connector).schema(jcm.component_schema.toString())
				.targetConnectorType(jcm.targetComponentName).targetId(jcm.targetId);
				
			if(jcm.current_connector_type != null) {
				builder_talendJobLog.custom("lineStyle", jcm.current_connector_type);
			}
			log_context_talendJobLog = builder_talendJobLog.build();
			runtime_lineage_logger_talendJobLog.schema(log_context_talendJobLog);
		}
		
	}

 



		

/**
 * [talendJobLog begin ] stop
 */

	
	/**
	 * [talendJobLog main ] start
	 */

	

	
	
	s(currentComponent="talendJobLog");
	
			
			
	

 


	tos_count_talendJobLog++;

		

/**
 * [talendJobLog main ] stop
 */

	
	/**
	 * [talendJobLog process_data_begin ] start
	 */

	

	
	
	s(currentComponent="talendJobLog");
	
			
			
	

 



		

/**
 * [talendJobLog process_data_begin ] stop
 */

	
	/**
	 * [talendJobLog process_data_end ] start
	 */

	

	
	
	s(currentComponent="talendJobLog");
	
			
			
	

 



		

/**
 * [talendJobLog process_data_end ] stop
 */

	
	/**
	 * [talendJobLog end ] start
	 */

	

	
	
	s(currentComponent="talendJobLog");
	
			
			
	

 

ok_Hash.put("talendJobLog", true);
end_Hash.put("talendJobLog", System.currentTimeMillis());




		

/**
 * [talendJobLog end ] stop
 */

				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				    if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
					   log.fatal(currentComponent + " " + e.getMessage(),e);
					}
				
				TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [talendJobLog finally ] start
	 */

	

	
	
	s(currentComponent="talendJobLog");
	
			
			
	

 



		

/**
 * [talendJobLog finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}
	
    public String resuming_logs_dir_path = null;
    public String resuming_checkpoint_path = null;
    public String parent_part_launcher = null;
    private String resumeEntryMethodName = null;
    private boolean globalResumeTicket = false;

    public boolean watch = false;
    // portStats is null, it means don't execute the statistics
    public Integer portStats = null;
    public int portTraces = 4334;
    public String clientHost;
    public String defaultClientHost = "localhost";
    public String contextStr = "DEV";
    public boolean isDefaultContext = true;
    public String pid = "0";
    public String rootPid = null;
    public String fatherPid = null;
    public String fatherNode = null;
    public long startTime = 0;
    public boolean isChildJob = false;
    public String log4jLevel = "";
    
    private boolean enableLogStash;
    private boolean enableLineage;

    private boolean execStat = true;
    
    private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
        protected java.util.Map<String, String> initialValue() {
            java.util.Map<String,String> threadRunResultMap = new java.util.HashMap<String, String>();
            threadRunResultMap.put("errorCode", null);
            threadRunResultMap.put("status", "");
            return threadRunResultMap;
        };
    };


    protected PropertiesWithType context_param = new PropertiesWithType();
    public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

    public String status= "";
    
    
    private final static java.util.Properties jobInfo = new java.util.Properties();
    private final static java.util.Map<String,String> mdcInfo = new java.util.HashMap<>();
    private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();


    public static void main(String[] args){
        final JOB_01_load_staging JOB_01_load_stagingClass = new JOB_01_load_staging();
        int exitCode = JOB_01_load_stagingClass.runJobInTOS(args);
	        if(exitCode==0){
		        log.info("TalendJob: 'JOB_01_load_staging' - Done.");
	        }

		
		System.exit(exitCode);
		
    }
	

		
	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if(path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
			readJobInfo(new java.io.File(BUILD_PATH));
	}

    private void readJobInfo(java.io.File jobInfoFile){
	
        if(jobInfoFile.exists()) {
            try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
            	jobInfo.load(is);
            } catch (IOException e) {
            	 
                log.debug("Read jobInfo.properties file fail: " + e.getMessage());
                
            }
        }
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s",
				projectName,jobName,jobInfo.getProperty("gitCommitId"), "8.0.1.20261022_0848-patch"));
		
    }


    public String[][] runJob(String[] args) {

        int exitCode = runJobInTOS(args);
        String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

        return bufferValue;
    }

    public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;
    	
        return hastBufferOutput;
    }

    public int runJobInTOS(String[] args) {
	   	// QAPPINT-2186: Set isStandaloneMS=true if running in microservice context
        if (System.getProperty("talend.microservice.app.class") != null) {
            isStandaloneMS = true;
        }

	   	// reset status
	   	status = "";

        String lastStr = "";
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--context_param")) {
                lastStr = arg;
            } else if (lastStr.equals("")) {
                evalParam(arg);
            } else {
                evalParam(lastStr + " " + arg);
                lastStr = "";
            }
        }

        enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

            if(!"".equals(log4jLevel)){

                if("trace".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.TRACE);
                }else if("debug".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.DEBUG);
                }else if("info".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.INFO);
                }else if("warn".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.WARN);
                }else if("error".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.ERROR);
                }else if("fatal".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.FATAL);
                }else if ("off".equalsIgnoreCase(log4jLevel)){
                    org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.OFF);
                }
                org.apache.logging.log4j.core.config.Configurator.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());
            }

	         
	         getjobInfo();
	         

            log.info("TalendJob: 'JOB_01_load_staging' - Start.");

            java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
            for(Object jobInfoKey: jobInfoKeys) {
                org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
            }
            org.slf4j.MDC.put("_pid", pid);
            org.slf4j.MDC.put("_rootPid", rootPid);
            org.slf4j.MDC.put("_fatherPid", fatherPid);
            org.slf4j.MDC.put("_projectName", projectName);
            org.slf4j.MDC.put("_startTimestamp",java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC ).format( java.time.format.DateTimeFormatter.ISO_INSTANT ));
            org.slf4j.MDC.put("_jobRepositoryId","_oDmO4NiGEfGYDKHwOCnAyQ");
            org.slf4j.MDC.put("_compiledAtTimestamp","2026-11-08T01:32:16.892052800Z");

            java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
            String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
            if (mxNameTable.length == 2) {
                org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
            } else {
                org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
            }


        final boolean enableCBP = false;
        boolean inOSGi = routines.system.BundleUtils.inOSGi();

        boolean needSendForCBP = false;
        if (!inOSGi) {
            if(org.talend.metrics.CBPClient.getInstanceForCurrentVM() == null) {
                try {
                    needSendForCBP = true;

                    String jobType = "DI";
                    boolean isRooutJob = fatherPid == null ? true : false;
                    if (isStandaloneMS || inOSGi) {
                        jobType = "DATA_SERVICE";
                        // for APPINT cases everything is packaged as single job
                        isRooutJob = true;
                    } else {
                        // print only for DI job for microservice it is already printed
                        // for OSGI runtime will take care
                        if(jobInfo.isEmpty()) {
                            getjobInfo();
                        }
                    }
                    org.talend.metrics.CBPClient.startListenIfNotStarted(enableCBP, isRooutJob, jobType, jobInfo.getProperty("artifactId"), true, 
                        jobInfo.getProperty("cmdLineVersion"), jobInfo.getProperty("patch"), jobInfo.getProperty("date"));
                } catch (java.lang.Exception e) {
                    errorCode = 1;
                    status = "failure";
                    e.printStackTrace();
                    return 1;
                }
	    }
	}
        
		
		    final String lineageDirPath = System.getProperty("runtime.lineage.outputpath");
		    final String payloadFilePath = System.getProperty("tmc.task.payload.path");
		    if((lineageDirPath != null && !lineageDirPath.isEmpty()) || (payloadFilePath !=null && !payloadFilePath.isEmpty())) {
		        enableLineage = true;
		        
    			java.util.Properties p_talendJobLog = new java.util.Properties();
    			p_talendJobLog.setProperty("root.logger", "runtime");
    			p_talendJobLog.setProperty("encoding", "UTF-8");
    			p_talendJobLog.setProperty("application.name", "Talend Studio");
    			p_talendJobLog.setProperty("service.name", "Talend Studio Job");
    			p_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
    			p_talendJobLog.setProperty("propagate.appender.exceptions", "none");
    			p_talendJobLog.setProperty("log.appender", "file");
    			p_talendJobLog.setProperty("appender.file.path", "runtime.json");
    			p_talendJobLog.setProperty("appender.file.maxsize", "52428800");
    			p_talendJobLog.setProperty("appender.file.maxbackup", "20");
    			p_talendJobLog.setProperty("host", "false");
    			
    			final String lineageFilePath = System.getProperty("runtime.lineage.appender.file.path");
    			if(lineageFilePath==null || lineageFilePath.isEmpty()) {
    			    String finalLineageDirPath = lineageDirPath;
    			    if(lineageDirPath == null || lineageDirPath.isEmpty()) {
                       finalLineageDirPath = payloadFilePath + "_dir";
                    }
    			
					System.setProperty("runtime.lineage.appender.file.path", 
						new StringBuilder().append(finalLineageDirPath)
						.append((finalLineageDirPath.endsWith("/") || finalLineageDirPath.endsWith("\\")) ? "" : java.io.File.separator)
						.append(projectName)
						.append(java.io.File.separatorChar)
						.append(jobName)
						.append(java.io.File.separatorChar)
						.append(jobVersion)
						.append(java.io.File.separatorChar)
						.append("runtime_log_")
						.append(new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()))
						.append(".json")
						.toString()
					);
    			}
    			
    			System.getProperties().stringPropertyNames().stream()
    					.filter(it -> it.startsWith("runtime.lineage.") && !"runtime.lineage.outputpath".equals(it))
    					.forEach(key -> p_talendJobLog.setProperty(key.substring("runtime.lineage.".length()), System.getProperty(key)));

    			
    			
    			
    			org.apache.logging.log4j.core.config.Configurator.setLevel(p_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);
    			
    			
    			runtime_lineage_logger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory.createJobAuditLogger(p_talendJobLog);
			}
		
			if(enableLogStash) {
				java.util.Properties properties_talendJobLog = new java.util.Properties();
				properties_talendJobLog.setProperty("root.logger", "audit");
				properties_talendJobLog.setProperty("encoding", "UTF-8");
				properties_talendJobLog.setProperty("application.name", "Talend Studio");
				properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
				properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
				properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
				properties_talendJobLog.setProperty("log.appender", "file");
				properties_talendJobLog.setProperty("appender.file.path", "audit.json");
				properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
				properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
				properties_talendJobLog.setProperty("host", "false");

				System.getProperties().stringPropertyNames().stream()
					.filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()), System.getProperty(key)));

				
				
				
				org.apache.logging.log4j.core.config.Configurator.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);
				
				auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory.createJobAuditLogger(properties_talendJobLog);
			}
		

        if(clientHost == null) {
            clientHost = defaultClientHost;
        }

        if(pid == null || "0".equals(pid)) {
            pid = TalendString.getAsciiRandomString(6);
        }

            org.slf4j.MDC.put("_pid", pid);

        if (rootPid==null) {
            rootPid = pid;
        }

            org.slf4j.MDC.put("_rootPid", rootPid);

        if (fatherPid==null) {
            fatherPid = pid;
        }else{
            isChildJob = true;
        }
            org.slf4j.MDC.put("_fatherPid", fatherPid);

        if (portStats != null) {
            // portStats = -1; //for testing
            if (portStats < 0 || portStats > 65535) {
                // issue:10869, the portStats is invalid, so this client socket can't open
                System.err.println("The statistics socket port " + portStats + " is invalid.");
                execStat = false;
            }
        } else {
            execStat = false;
        }

        java.util.Dictionary<String, Object> jobProperties = null;
        try {
            if (inOSGi) {
                jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
    
                if (jobProperties != null && jobProperties.get("context") != null) {
                    contextStr = (String)jobProperties.get("context");
                }

                // extract params from caller
                String taskExecutionIdForSOAP = null;
                String jobExecutionIdForSOAP = null;
                boolean skipCBPAuth = false;
                boolean isSoapService = false;
                for(String arg : args) {
                    // for SOAP service, the taskExecutionId and jobExecutionId are passed in as context params from Talend Runtime
                    if(arg.startsWith("--context_param")
                            && (arg.contains("taskExecutionId") || arg.contains("jobExecutionId"))) {
                        String keyValue = arg.replace("--context_param", "");
                        String[] parts = keyValue.split("=");
                        String[] cleanParts = java.util.Arrays.stream(parts)
                                .filter(s -> !s.isEmpty())
                                .toArray(String[]::new);
                        if (cleanParts.length == 2) {
                            String key = cleanParts[0];
                            String value = cleanParts[1];
                            if ("taskExecutionId".equals(key.trim()) && null != value) {
                                taskExecutionIdForSOAP = value.trim();
                            } else if ("jobExecutionId".equals(key.trim()) && null != value) {
                                jobExecutionIdForSOAP = value.trim();
                            }
                        }
                    } else if (arg.equalsIgnoreCase("--sealCounters=true")) {
                        sealCounters = true;
                    } else if (arg.equalsIgnoreCase("--skipCBPAuth=true")) {
                        skipCBPAuth = true;
                    } else if (arg.equalsIgnoreCase("--isSOAPService=true")) {
                        isSoapService = true;
                        skipCBPAuth = true;
                    }
                }
            }

            // first load default key-value pairs from application.properties
            if(isStandaloneMS) {
                context.putAll(this.getDefaultProperties());
            }
            //call job/subjob with an existing context, like: --context=production. if without this parameter, there will use the default context instead.
            java.io.InputStream inContext = JOB_01_load_staging.class.getClassLoader().getResourceAsStream("g3_uci_bank/job_01_load_staging_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = JOB_01_load_staging.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    //defaultProps is in order to keep the original context value
                    if(context != null && context.isEmpty()) {
    	                defaultProps.load(inContext);
    	                if (inOSGi && jobProperties != null) {
                             java.util.Enumeration<String> keys = jobProperties.keys();
                             while (keys.hasMoreElements()) {
                                 String propKey = keys.nextElement();
                                 if (defaultProps.containsKey(propKey)) {
                                     defaultProps.put(propKey, (String) jobProperties.get(propKey));
                                 }
                             }
    	                }
    	                context = new ContextProperties(defaultProps);
                    }
                    if(isStandaloneMS) {
                        // override context key-value pairs if provided using --context=contextName
                        defaultProps.load(inContext);
                        context.putAll(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                //print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }
            // override key-value pairs if provided via --config.location=file1.file2 OR --config.additional-location=file1,file2
            if(isStandaloneMS) {
                context.putAll(this.getAdditionalProperties());
            }

            // QAPPINT-2186: Apply cloud context properties for microservice jobs (REST or SOAP)
            // Only execute if running in microservice context and cloud execution is detected
            if (isStandaloneMS) {
                try {
                    String appClassName = System.getProperty("talend.microservice.app.class");
                    if (appClassName != null && !appClassName.isEmpty()) {
                        Class<?> appClass = Class.forName(appClassName);
                        java.lang.reflect.Method getPropsMethod = appClass.getMethod("getCloudContextProperties");
                        @SuppressWarnings("unchecked")
                        java.util.Map<String, String> cloudContextProps = (java.util.Map<String, String>) getPropsMethod.invoke(null);
                        if (cloudContextProps != null && !cloudContextProps.isEmpty()) {
                            context.putAll(cloudContextProps);
                        }
                    }
                } catch (Exception e) {
                    // Cloud context not available (e.g. app class/method not found) or error retrieving
                    // properties - do not fail the job, but keep this diagnosable.
                    System.err.println("Unable to retrieve cloud context properties: " + e.getMessage());
                }
            }

            // override key-value pairs if provide via command line like --key1=value1,--key2=value2
            if(!context_param.isEmpty()) {
                context.putAll(context_param);
				//set types for params from parentJobs
				for (Object key: context_param.keySet()){
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
            }

            class ContextProcessing {
                private void processContext_0() {
                        context.setContextType("DB_HOST", "id_String");
                        if(context.getStringValue("DB_HOST") == null) {
                            context.DB_HOST = null;
                        } else {
                            context.DB_HOST=(String) context.getProperty("DB_HOST");
                        }
                        context.setContextType("DB_NAME", "id_String");
                        if(context.getStringValue("DB_NAME") == null) {
                            context.DB_NAME = null;
                        } else {
                            context.DB_NAME=(String) context.getProperty("DB_NAME");
                        }
                        context.setContextType("DB_PASSWORD", "id_String");
                        if(context.getStringValue("DB_PASSWORD") == null) {
                            context.DB_PASSWORD = null;
                        } else {
                            context.DB_PASSWORD=(String) context.getProperty("DB_PASSWORD");
                        }
                        context.setContextType("DB_PORT", "id_String");
                        if(context.getStringValue("DB_PORT") == null) {
                            context.DB_PORT = null;
                        } else {
                            context.DB_PORT=(String) context.getProperty("DB_PORT");
                        }
                        context.setContextType("DB_USER", "id_String");
                        if(context.getStringValue("DB_USER") == null) {
                            context.DB_USER = null;
                        } else {
                            context.DB_USER=(String) context.getProperty("DB_USER");
                        }
                        context.setContextType("FILE_UCI_BANK", "id_String");
                        if(context.getStringValue("FILE_UCI_BANK") == null) {
                            context.FILE_UCI_BANK = null;
                        } else {
                            context.FILE_UCI_BANK=(String) context.getProperty("FILE_UCI_BANK");
                        }
                        context.setContextType("LOAD_SOURCE_TAG", "id_String");
                        if(context.getStringValue("LOAD_SOURCE_TAG") == null) {
                            context.LOAD_SOURCE_TAG = null;
                        } else {
                            context.LOAD_SOURCE_TAG=(String) context.getProperty("LOAD_SOURCE_TAG");
                        }
                        context.setContextType("PIPELINE_NAME", "id_String");
                        if(context.getStringValue("PIPELINE_NAME") == null) {
                            context.PIPELINE_NAME = null;
                        } else {
                            context.PIPELINE_NAME=(String) context.getProperty("PIPELINE_NAME");
                        }
                } 
                public void processAllContext() {
                        processContext_0();
                }
            }

            new ContextProcessing().processAllContext();
        } catch (java.io.IOException ie) {
            System.err.println("Could not load context "+contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {if (parentContextMap.containsKey("DB_HOST")) {
                context.DB_HOST = (String) parentContextMap.get("DB_HOST");
            }if (parentContextMap.containsKey("DB_NAME")) {
                context.DB_NAME = (String) parentContextMap.get("DB_NAME");
            }if (parentContextMap.containsKey("DB_PASSWORD")) {
                context.DB_PASSWORD = (String) parentContextMap.get("DB_PASSWORD");
            }if (parentContextMap.containsKey("DB_PORT")) {
                context.DB_PORT = (String) parentContextMap.get("DB_PORT");
            }if (parentContextMap.containsKey("DB_USER")) {
                context.DB_USER = (String) parentContextMap.get("DB_USER");
            }if (parentContextMap.containsKey("FILE_UCI_BANK")) {
                context.FILE_UCI_BANK = (String) parentContextMap.get("FILE_UCI_BANK");
            }if (parentContextMap.containsKey("LOAD_SOURCE_TAG")) {
                context.LOAD_SOURCE_TAG = (String) parentContextMap.get("LOAD_SOURCE_TAG");
            }if (parentContextMap.containsKey("PIPELINE_NAME")) {
                context.PIPELINE_NAME = (String) parentContextMap.get("PIPELINE_NAME");
            }
        }


        //Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        //Resume: jobStart
        resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","","","",ResumeUtil.convertToJsonText(context,ContextProperties.class,parametersToEncrypt));

            org.slf4j.MDC.put("_context", contextStr);
            log.info("TalendJob: 'JOB_01_load_staging' - Started.");
            java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

if(execStat) {
    try {
        runStat.openSocket(!isChildJob);
        runStat.setAllPID(rootPid, fatherPid, pid, jobName);
        runStat.startThreadStat(clientHost, portStats);
        runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
    } catch (java.io.IOException ioException) {
        ioException.printStackTrace();
    }
}



	
	    java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
	    globalMap.put("concurrentHashMap", concurrentHashMap);
	

    long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    long endUsedMemory = 0;
    long end = 0;

    startTime = System.currentTimeMillis();


this.globalResumeTicket = true;//to run tPreJob




		if(enableLogStash) {
	        talendJobLog.addJobStartMessage();
	        try {
	            talendJobLogProcess(globalMap);
	        } catch (java.lang.Exception e) {
	            e.printStackTrace();
	        }
        }

this.globalResumeTicket = false;//to run others jobs

try {
errorCode = null;tFileInputDelimited_2Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tFileInputDelimited_2) {
globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", -1);

e_tFileInputDelimited_2.printStackTrace();

}

this.globalResumeTicket = true;//to run tPostJob




        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : JOB_01_load_staging");
        }
		if(enableLogStash) {
	        talendJobLog.addJobEndMessage(startTime, end, status);
	        try {
	            talendJobLogProcess(globalMap);
	        } catch (java.lang.Exception e) {
	            e.printStackTrace();
	        }
        }



if (execStat) {
    runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
    runStat.stopThreadStat();
}
    
    if(!isChildJob) {
        //TODO TMC need to make sure the key or provide a way to pass it to job, consider java command length too long risk in windows OS
        String taskId = System.getProperty("tmc.task.id");//not the same key with CBP
        String taskVersion = System.getProperty("tmc.task.version");
        String taskExecId = System.getProperty("tmc.task.execution.id");
        String lineageFilePath = System.getProperty("runtime.lineage.appender.file.path");
        try {
            org.talend.studio.ProducerHelper.newPayload(payloadFilePath, lineageFilePath, taskExecId, taskId, taskVersion, lineageDirPath == null ? true : false);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    int returnCode = 0;


    if (!inOSGi) {
        if(needSendForCBP && org.talend.metrics.CBPClient.getInstanceForCurrentVM() != null) {
            s("none");
            if(!isChildJob) {
                org.talend.metrics.CBPClient.getInstanceForCurrentVM().setStatus(status);
            }
            org.talend.metrics.CBPClient.getInstanceForCurrentVM().sendData();
        }
    }

    if(errorCode == null) {
         returnCode = status != null && status.equals("failure") ? 1 : 0;
    } else {
         returnCode = errorCode.intValue();
    }
    resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","" + returnCode,"","","");
    resumeUtil.flush();


        org.slf4j.MDC.remove("_subJobName");
        org.slf4j.MDC.remove("_subJobPid");
        org.slf4j.MDC.remove("_systemPid");
        log.info("TalendJob: 'JOB_01_load_staging' - Finished - status: " + status + " returnCode: " + returnCode );

    return returnCode;

  }

    // only for OSGi env
    public void destroy() {

    // add CBP code for OSGI Executions
    if (sealCounters && jobExecutionId != null) {
        // call to checkAuthorization was made or was being made
        try {
	       org.talend.metrics.DataReadTracker.setExecutionId(taskExecutionId, jobExecutionId, false);
	       org.talend.metrics.DataReadTracker.sealCounter();
	       org.talend.metrics.DataReadTracker.reset();
	    } catch (Exception | NoClassDefFoundError e) {
	       // ignore
	    }
	}

	// check for orphan threads if still alive after undeploy
	for (java.util.Iterator<Thread> it = threadList.iterator(); it.hasNext();) {
	    Thread thread = it.next();
	    if (thread != null && thread.isAlive()) {
	        boolean suppressLogs = Boolean.parseBoolean(System.getProperty("thread.terminate.logs.suppress", "false"));
	        if (!suppressLogs) {
	            System.err.println("Initiating thread cleanup prior to bundle undeployment. This is a precautionary step to ensure no memory leaks.");
	            System.err.println("Forcefully interrupting thread with ID = " + thread.getId() + ". This may result in expected errors due to abrupt termination. Please verify if the thread was performing critical operations.");
	        }
	        thread.interrupt();
	    }
	    it.remove();
	}
	// end of destroy()
    }













    private java.util.Map<String, Object> getSharedConnections4REST() {
        java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();






        return connections;
    }

    private void evalParam(String arg) {
        if (arg.startsWith("--resuming_logs_dir_path")) {
            resuming_logs_dir_path = arg.substring(25);
        } else if (arg.startsWith("--resuming_checkpoint_path")) {
            resuming_checkpoint_path = arg.substring(27);
        } else if (arg.startsWith("--parent_part_launcher")) {
            parent_part_launcher = arg.substring(23);
        } else if (arg.startsWith("--watch")) {
            watch = true;
        } else if (arg.startsWith("--stat_port=")) {
            String portStatsStr = arg.substring(12);
            if (portStatsStr != null && !portStatsStr.equals("null")) {
                portStats = Integer.parseInt(portStatsStr);
            }
        } else if (arg.startsWith("--trace_port=")) {
            portTraces = Integer.parseInt(arg.substring(13));
        } else if (arg.startsWith("--client_host=")) {
            clientHost = arg.substring(14);
        } else if (arg.startsWith("--context=")) {
            contextStr = arg.substring(10);
            isDefaultContext = false;
        } else if (arg.startsWith("--father_pid=")) {
            fatherPid = arg.substring(13);
        } else if (arg.startsWith("--root_pid=")) {
            rootPid = arg.substring(11);
        } else if (arg.startsWith("--father_node=")) {
            fatherNode = arg.substring(14);
        } else if (arg.startsWith("--pid=")) {
            pid = arg.substring(6);
        } else if (arg.startsWith("--context_type")) {
            String keyValue = arg.substring(15);
			int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.setContextType(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }

            }

		} else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }
            }
        } else if (arg.startsWith("--context_file")) {
        	String keyValue = arg.substring(15);
        	String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
        	java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
            try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int index = -1;
                    if ( (index = line.indexOf('=')) > -1) {
							if (line.startsWith("--context_param")) {
								if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
									context_param.put(line.substring(16, index), routines.system.PasswordEncryptUtil.decryptPassword(
											line.substring(index + 1)));
								} else {
									context_param.put(line.substring(16, index), line.substring(index + 1));
								}
							}else {//--context_type
								context_param.setContextType(line.substring(15, index), line.substring(index + 1));
							}
                    }
                }
            } catch (java.io.IOException e) {
            	System.err.println("Could not load the context file: " + filePath);
                e.printStackTrace();
            }
        } else if (arg.startsWith("--log4jLevel=")) {
            log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {//for trunjob call
		    final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
    }
    
    private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

    private final String[][] escapeChars = {
        {"\\\\","\\"},{"\\n","\n"},{"\\'","\'"},{"\\r","\r"},
        {"\\f","\f"},{"\\b","\b"},{"\\t","\t"}
        };
    private String replaceEscapeChars (String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0],currIndex);
				if (index>=0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0], strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
    }

    public Integer getErrorCode() {
        return errorCode;
    }


    public String getStatus() {
        return status;
    }

    ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 *     225474 characters generated by Qlik Talend Cloud Enterprise Edition 
 *     on the 7 de noviembre de 2026, 20:32:17 ECT
 ************************************************************************************************/