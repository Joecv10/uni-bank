
package g3_uci_bank.job_02_quality_0_1;

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
 





//the import part of tJava_1
//import java.util.List;


@SuppressWarnings("unused")

/**
 * Job: JOB_02_quality Purpose: Ejecuta reglas DQ y contabiliza resultados<br>
 * Description: Ejecuta reglas DQ y contabiliza resultados <br>
 * @author Myer, Jav
 * @version 8.0.1.20261022_0848-patch
 * @status 
 */
public class JOB_02_quality implements TalendJob {
	static {System.setProperty("TalendJob.log", "JOB_02_quality.log");}

	

	
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(JOB_02_quality.class);
	

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
	private final String jobName = "JOB_02_quality";
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
	

private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName, "_8qu1gNiHEfGYDKHwOCnAyQ", "0.1");
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
			JOB_02_quality.this.exception = e;
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(JOB_02_quality.this, new Object[] { e , currentComponent, globalMap});
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

			public void tPrejob_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tPrejob_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tJava_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tJava_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tPostjob_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tPostjob_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBInput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tUniqRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBRow_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void talendJobLog_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					talendJobLog_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tPrejob_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBRow_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tJava_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tPostjob_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBInput_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBRow_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void talendJobLog_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	



public void tPrejob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tPrejob_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tPrejob_1", "E5f1rs_");

	
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
	 * [tPrejob_1 begin ] start
	 */

	

	
		
		sh("tPrejob_1");
		
	
	s(currentComponent="tPrejob_1");
	
			
			
	
		int tos_count_tPrejob_1 = 0;
		
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tPrejob_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                
                
                
                } catch (java.lang.Exception e_tPrejob_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tPrejob_1", "tPrejob",
          new ParameterUtil_tPrejob_1().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tPrejob_1", "tPrejob_1", "tPrejob");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

 



		

/**
 * [tPrejob_1 begin ] stop
 */

	
	/**
	 * [tPrejob_1 main ] start
	 */

	

	
	
	s(currentComponent="tPrejob_1");
	
			
			
	

 


	tos_count_tPrejob_1++;

		

/**
 * [tPrejob_1 main ] stop
 */

	
	/**
	 * [tPrejob_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tPrejob_1");
	
			
			
	

 



		

/**
 * [tPrejob_1 process_data_begin ] stop
 */

	
	/**
	 * [tPrejob_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tPrejob_1");
	
			
			
	

 



		

/**
 * [tPrejob_1 process_data_end ] stop
 */

	
	/**
	 * [tPrejob_1 end ] start
	 */

	

	
	
	s(currentComponent="tPrejob_1");
	
			
			
	

 

ok_Hash.put("tPrejob_1", true);
end_Hash.put("tPrejob_1", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
				}
				tDBRow_1Process(globalMap);



		

/**
 * [tPrejob_1 end ] stop
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
	 * [tPrejob_1 finally ] start
	 */

	

	
	
	s(currentComponent="tPrejob_1");
	
			
			
	

 



		

/**
 * [tPrejob_1 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tPrejob_1_SUBPROCESS_STATE", 1);
	}
	


public void tDBRow_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBRow_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tDBRow_1", "Ur2CvW_");

	
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
	 * [tDBRow_1 begin ] start
	 */

	

	
		
		sh("tDBRow_1");
		
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		
		int tos_count_tDBRow_1 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBRow_1 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBRow_1{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBRow_1 = new StringBuilder();
                    log4jParamters_tDBRow_1.append("Parameters:");
                            log4jParamters_tDBRow_1.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("QUERYSTORE" + " = " + "\"\"");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("QUERY" + " = " + "\"CREATE TABLE IF NOT EXISTS stg_bank_OK_g3 (      source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,      age INT(3) DEFAULT NULL,      job VARCHAR(13),      marital VARCHAR(8),      education VARCHAR(19),      has_default VARCHAR(7),      housing VARCHAR(7),      loan VARCHAR(7),      contact VARCHAR(20),      month VARCHAR(3),      day_of_week VARCHAR(3),      duration INT(4),      campaign INT(1),      pdays INT(3),      previous INT(1),      poutcome VARCHAR(11),      emp_var_rate FLOAT(10,4),      cons_price_idx FLOAT(10,4),      cons_conf_idx DOUBLE(10,4),      euribor3m FLOAT(10,4),      nr_employed DOUBLE(12,4),      y VARCHAR(3),      load_ts DATETIME,      load_source VARCHAR(60)  );  CREATE TABLE IF NOT EXISTS stg_bank_NOTOK_g3 (      source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,      age INT(3) DEFAULT NULL,      job VARCHAR(13),      marital VARCHAR(8),      education VARCHAR(19),      has_default VARCHAR(7),      housing VARCHAR(7),      loan VARCHAR(7),      contact VARCHAR(20),      month VARCHAR(3),      day_of_week VARCHAR(3),      duration INT(4),      campaign INT(1),      pdays INT(3),      previous INT(1),      poutcome VARCHAR(11),      emp_var_rate FLOAT(10,4),      cons_price_idx FLOAT(10,4),      cons_conf_idx DOUBLE(10,4),      euribor3m FLOAT(10,4),      nr_employed DOUBLE(12,4),      y VARCHAR(3),  	codigo_regla VARCHAR(15),  	motivo_rechazo VARCHAR(255),  	responsable_remediacion VARCHAR(60),  	estado_remediacion VARCHAR(20),      load_ts DATETIME,      load_source VARCHAR(60)  );\"");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true\"");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("PROPAGATE_RECORD_SET" + " = " + "false");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("USE_PREPAREDSTATEMENT" + " = " + "false");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBRow_1.append(" | ");
                            log4jParamters_tDBRow_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
                        log4jParamters_tDBRow_1.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBRow_1 - "  + (log4jParamters_tDBRow_1) );
                    } 
                } 
            new BytesLimit65535_tDBRow_1().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBRow_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("QUERYSTORE", String.valueOf(""));
                            component_parameters.put("QUERY", String.valueOf(new StringBuilder().append("CREATE TABLE IF NOT EXISTS stg_bank_OK_g3 (\n    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n    age INT(3) DEFA" +"ULT NULL,\n    job VARCHAR(13),\n    marital VARCHAR(8),\n    education VARCHAR(19),\n    has_default VARCHAR(7),\n    h" +"ousing VARCHAR(7),\n    loan VARCHAR(7),\n    contact VARCHAR(20),\n    month VARCHAR(3),\n    day_of_week VARCHAR(3),\n" +"    duration INT(4),\n    campaign INT(1),\n    pdays INT(3),\n    previous INT(1),\n    poutcome VARCHAR(11),\n    emp_" +"var_rate FLOAT(10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n    euribor3m FLOAT(10,4),\n   " +" nr_employed DOUBLE(12,4),\n    y VARCHAR(3),\n    load_ts DATETIME,\n    load_source VARCHAR(60)\n);\nCREATE TABLE IF N" +"OT EXISTS stg_bank_NOTOK_g3 (\n    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n    age INT(3) DEFAULT NULL,\n    " +"job VARCHAR(13),\n    marital VARCHAR(8),\n    education VARCHAR(19),\n    has_default VARCHAR(7),\n    housing VARCHAR(" +"7),\n    loan VARCHAR(7),\n    contact VARCHAR(20),\n    month VARCHAR(3),\n    day_of_week VARCHAR(3),\n    duration IN" +"T(4),\n    campaign INT(1),\n    pdays INT(3),\n    previous INT(1),\n    poutcome VARCHAR(11),\n    emp_var_rate FLOAT(" +"10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n    euribor3m FLOAT(10,4),\n    nr_employed DO" +"UBLE(12,4),\n    y VARCHAR(3),\n	codigo_regla VARCHAR(15),\n	motivo_rechazo VARCHAR(255),\n	responsable_remediacion VARC" +"HAR(60),\n	estado_remediacion VARCHAR(20),\n    load_ts DATETIME,\n    load_source VARCHAR(60)\n);").toString()));
                            component_parameters.put("SPECIFY_DATASOURCE_ALIAS", String.valueOf("false"));
                            component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
                            component_parameters.put("PROPERTIES", String.valueOf("noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true"));
                            component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
                            component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
                            component_parameters.put("COMMIT_EVERY", String.valueOf("10000"));
                            component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));
                
                
                
                } catch (java.lang.Exception e_tDBRow_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBRow_1", "tMysqlRow",
          new ParameterUtil_tDBRow_1().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBRow_1", "tDBR_CREATE", "tMysqlRow");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

	java.sql.Connection conn_tDBRow_1 = null;
	String query_tDBRow_1 = "";
	boolean whetherReject_tDBRow_1 = false;
	int count_tDBRow_1 = 0;
			String driverClass_tDBRow_1 = "com.mysql.cj.jdbc.Driver";
		    java.lang.Class jdbcclazz_tDBRow_1 = java.lang.Class.forName(driverClass_tDBRow_1);
		
        String properties_tDBRow_1 = "noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true";
        if (properties_tDBRow_1 == null || properties_tDBRow_1.trim().length() == 0) {
            properties_tDBRow_1 = "allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBRow_1.contains("allowLoadLocalInfile=")) {
                properties_tDBRow_1 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBRow_1 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBRow_1;
	    		log.debug("tDBRow_1 - Driver ClassName: "+driverClass_tDBRow_1+".");
			
					String dbUser_tDBRow_1 = context.DB_USER;
	        
            		
            		
            		
	final String decryptedPassword_tDBRow_1 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");
        		   	
        	        String dbPwd_tDBRow_1 = decryptedPassword_tDBRow_1;
	        
					
	    		log.debug("tDBRow_1 - Connection attempt to '" + url_tDBRow_1.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' with the username '" + dbUser_tDBRow_1 + "'.");
			
			conn_tDBRow_1 = java.sql.DriverManager.getConnection(url_tDBRow_1,dbUser_tDBRow_1,dbPwd_tDBRow_1);
		
	    		log.debug("tDBRow_1 - Connection to '" + url_tDBRow_1.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' has succeeded.");
			
        resourceMap.put("conn_tDBRow_1", conn_tDBRow_1);
					if(conn_tDBRow_1.getAutoCommit()) {
						
	    		log.debug("tDBRow_1 - Connection is set auto commit to 'false'.");
			
				conn_tDBRow_1.setAutoCommit(false);
			
					}        
					int commitEvery_tDBRow_1 = 10000;
					int commitCounter_tDBRow_1 = 0;
				
        java.sql.Statement stmt_tDBRow_1 = conn_tDBRow_1.createStatement();
        resourceMap.put("stmt_tDBRow_1", stmt_tDBRow_1);

 



		

/**
 * [tDBRow_1 begin ] stop
 */

	
	/**
	 * [tDBRow_1 main ] start
	 */

	

	
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		

query_tDBRow_1 = new StringBuilder().append("CREATE TABLE IF NOT EXISTS stg_bank_OK_g3 (\n    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n    age INT(3) DEFA"
+"ULT NULL,\n    job VARCHAR(13),\n    marital VARCHAR(8),\n    education VARCHAR(19),\n    has_default VARCHAR(7),\n    h"
+"ousing VARCHAR(7),\n    loan VARCHAR(7),\n    contact VARCHAR(20),\n    month VARCHAR(3),\n    day_of_week VARCHAR(3),\n"
+"    duration INT(4),\n    campaign INT(1),\n    pdays INT(3),\n    previous INT(1),\n    poutcome VARCHAR(11),\n    emp_"
+"var_rate FLOAT(10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n    euribor3m FLOAT(10,4),\n   "
+" nr_employed DOUBLE(12,4),\n    y VARCHAR(3),\n    load_ts DATETIME,\n    load_source VARCHAR(60)\n);\nCREATE TABLE IF N"
+"OT EXISTS stg_bank_NOTOK_g3 (\n    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n    age INT(3) DEFAULT NULL,\n    "
+"job VARCHAR(13),\n    marital VARCHAR(8),\n    education VARCHAR(19),\n    has_default VARCHAR(7),\n    housing VARCHAR("
+"7),\n    loan VARCHAR(7),\n    contact VARCHAR(20),\n    month VARCHAR(3),\n    day_of_week VARCHAR(3),\n    duration IN"
+"T(4),\n    campaign INT(1),\n    pdays INT(3),\n    previous INT(1),\n    poutcome VARCHAR(11),\n    emp_var_rate FLOAT("
+"10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n    euribor3m FLOAT(10,4),\n    nr_employed DO"
+"UBLE(12,4),\n    y VARCHAR(3),\n	codigo_regla VARCHAR(15),\n	motivo_rechazo VARCHAR(255),\n	responsable_remediacion VARC"
+"HAR(60),\n	estado_remediacion VARCHAR(20),\n    load_ts DATETIME,\n    load_source VARCHAR(60)\n);").toString();
whetherReject_tDBRow_1 = false;
	    		log.debug("tDBRow_1 - Executing the query: '" + query_tDBRow_1 + "'.");
			
globalMap.put("tDBRow_1_QUERY",query_tDBRow_1);
try {
				stmt_tDBRow_1.execute(query_tDBRow_1);
	    		log.debug("tDBRow_1 - Execute the query: '" + query_tDBRow_1 + "' has finished.");
			
	} catch (java.lang.Exception e) {
		whetherReject_tDBRow_1 = true;
		
				log.error("tDBRow_1 - " + e.getMessage());
			
				System.err.print(e.getMessage());
				globalMap.put("tDBRow_1_ERROR_MESSAGE", e.getMessage());
				
	}
	
	if(!whetherReject_tDBRow_1) {
		
	}
	
		commitCounter_tDBRow_1++;
		if(commitEvery_tDBRow_1 <= commitCounter_tDBRow_1) {
			
	    		log.debug("tDBRow_1 - Connection starting to commit.");
			
			conn_tDBRow_1.commit();
			
	    		log.debug("tDBRow_1 - Connection commit has succeeded.");
			
			commitCounter_tDBRow_1=0;
		}
		

 


	tos_count_tDBRow_1++;

		

/**
 * [tDBRow_1 main ] stop
 */

	
	/**
	 * [tDBRow_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		

 



		

/**
 * [tDBRow_1 process_data_begin ] stop
 */

	
	/**
	 * [tDBRow_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		

 



		

/**
 * [tDBRow_1 process_data_end ] stop
 */

	
	/**
	 * [tDBRow_1 end ] start
	 */

	

	
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		

	globalMap.put("tDBRow_1_NB_LINE", count_tDBRow_1);
        stmt_tDBRow_1.close();
        resourceMap.remove("stmt_tDBRow_1");
    resourceMap.put("statementClosed_tDBRow_1", true);
		if(commitEvery_tDBRow_1>commitCounter_tDBRow_1){

			
	    		log.debug("tDBRow_1 - Connection starting to commit.");
			
			conn_tDBRow_1.commit();
			
	    		log.debug("tDBRow_1 - Connection commit has succeeded.");
			
	
			commitCounter_tDBRow_1=0;
	
		}
	    		log.debug("tDBRow_1 - Closing the connection to the database.");
			
			conn_tDBRow_1.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
	    		log.debug("tDBRow_1 - Connection to the database closed.");
			
    resourceMap.put("finish_tDBRow_1", true);
 
                if(log.isDebugEnabled())
            log.debug("tDBRow_1 - "  + ("Done.") );

ok_Hash.put("tDBRow_1", true);
end_Hash.put("tDBRow_1", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk3", 0, "ok");
				}
				tJava_1Process(globalMap);



		

/**
 * [tDBRow_1 end ] stop
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
	 * [tDBRow_1 finally ] start
	 */

	

	
	
	s(currentComponent="tDBRow_1");
	
			
			
	
			cLabel="tDBR_CREATE";
		

try {
    if (resourceMap.get("statementClosed_tDBRow_1") == null) {
            java.sql.Statement stmtToClose_tDBRow_1 = null;
            if ((stmtToClose_tDBRow_1 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_1")) != null) {
                stmtToClose_tDBRow_1.close();
            }
    }
} finally {
        if(resourceMap.get("finish_tDBRow_1") == null){
            java.sql.Connection ctn_tDBRow_1 = null;
            if((ctn_tDBRow_1 = (java.sql.Connection)resourceMap.get("conn_tDBRow_1")) != null){
                try {
	    		log.debug("tDBRow_1 - Closing the connection to the database.");
			
                    ctn_tDBRow_1.close();
	    		log.debug("tDBRow_1 - Connection to the database closed.");
			
                } catch (java.sql.SQLException sqlEx_tDBRow_1) {
                    String errorMessage_tDBRow_1 = "failed to close the connection in tDBRow_1 :" + sqlEx_tDBRow_1.getMessage();
				log.error("tDBRow_1 - " + sqlEx_tDBRow_1.getMessage());
			
                    System.err.println(errorMessage_tDBRow_1);
                }
            }
        }
    }
 



		

/**
 * [tDBRow_1 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBRow_1_SUBPROCESS_STATE", 1);
	}
	


public void tJava_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tJava_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tJava_1", "1kJXzZ_");

	
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
	 * [tJava_1 begin ] start
	 */

	

	
		
		sh("tJava_1");
		
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		
		int tos_count_tJava_1 = 0;
		
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tJava_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("CODE", String.valueOf("globalMap.put(\"RUN_ID\", \"run_\" + TalendDate.formatDate(\"yyyyMMdd_HHmmss\", TalendDate.getCurrentDate()));"));
                            component_parameters.put("IMPORT", String.valueOf("//import java.util.List;"));
                
                
                
                } catch (java.lang.Exception e_tJava_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tJava_1", "tJava",
          new ParameterUtil_tJava_1().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tJava_1", "tJava_RUN_ID", "tJava");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			


globalMap.put("RUN_ID", "run_" + TalendDate.formatDate("yyyyMMdd_HHmmss", TalendDate.getCurrentDate()));

 



		

/**
 * [tJava_1 begin ] stop
 */

	
	/**
	 * [tJava_1 main ] start
	 */

	

	
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		

 


	tos_count_tJava_1++;

		

/**
 * [tJava_1 main ] stop
 */

	
	/**
	 * [tJava_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		

 



		

/**
 * [tJava_1 process_data_begin ] stop
 */

	
	/**
	 * [tJava_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		

 



		

/**
 * [tJava_1 process_data_end ] stop
 */

	
	/**
	 * [tJava_1 end ] start
	 */

	

	
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		

 

ok_Hash.put("tJava_1", true);
end_Hash.put("tJava_1", System.currentTimeMillis());




		

/**
 * [tJava_1 end ] stop
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
	 * [tJava_1 finally ] start
	 */

	

	
	
	s(currentComponent="tJava_1");
	
			
			
	
			cLabel="tJava_RUN_ID";
		

 



		

/**
 * [tJava_1 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tJava_1_SUBPROCESS_STATE", 1);
	}
	


public void tPostjob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tPostjob_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tPostjob_1", "mxn8zw_");

	
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
	 * [tPostjob_1 begin ] start
	 */

	

	
		
		sh("tPostjob_1");
		
	
	s(currentComponent="tPostjob_1");
	
			
			
	
		int tos_count_tPostjob_1 = 0;
		
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tPostjob_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                
                
                
                } catch (java.lang.Exception e_tPostjob_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tPostjob_1", "tPostjob",
          new ParameterUtil_tPostjob_1().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tPostjob_1", "tPostjob_1", "tPostjob");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

 



		

/**
 * [tPostjob_1 begin ] stop
 */

	
	/**
	 * [tPostjob_1 main ] start
	 */

	

	
	
	s(currentComponent="tPostjob_1");
	
			
			
	

 


	tos_count_tPostjob_1++;

		

/**
 * [tPostjob_1 main ] stop
 */

	
	/**
	 * [tPostjob_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tPostjob_1");
	
			
			
	

 



		

/**
 * [tPostjob_1 process_data_begin ] stop
 */

	
	/**
	 * [tPostjob_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tPostjob_1");
	
			
			
	

 



		

/**
 * [tPostjob_1 process_data_end ] stop
 */

	
	/**
	 * [tPostjob_1 end ] start
	 */

	

	
	
	s(currentComponent="tPostjob_1");
	
			
			
	

 

ok_Hash.put("tPostjob_1", true);
end_Hash.put("tPostjob_1", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk2", 0, "ok");
				}
				tDBInput_2Process(globalMap);



		

/**
 * [tPostjob_1 end ] stop
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
	 * [tPostjob_1 finally ] start
	 */

	

	
	
	s(currentComponent="tPostjob_1");
	
			
			
	

 



		

/**
 * [tPostjob_1 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tPostjob_1_SUBPROCESS_STATE", 1);
	}
	


public static class out_OKStruct implements routines.system.IPersistableRow<out_OKStruct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_02_quality = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[0];

	
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
				
					return "";
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 26;
				}
				public Integer load_tsPrecision(){
				    return 0;
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
				    return 0;
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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
    public int compareTo(out_OKStruct other) {

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

public static class out_NOTOKStruct implements routines.system.IPersistableRow<out_NOTOKStruct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_02_quality = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[0];

	
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
				    return 10;
				}
				public Integer agePrecision(){
				    return 0;
				}
				public String ageDefault(){
				
					return "";
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				    return 10;
				}
				public Integer durationPrecision(){
				    return 0;
				}
				public String durationDefault(){
				
					return "";
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer campaignPrecision(){
				    return 0;
				}
				public String campaignDefault(){
				
					return "";
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer pdaysPrecision(){
				    return 0;
				}
				public String pdaysDefault(){
				
					return "";
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer previousPrecision(){
				    return 0;
				}
				public String previousDefault(){
				
					return "";
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 50;
				}
				public Integer emp_var_ratePrecision(){
				    return 2;
				}
				public String emp_var_rateDefault(){
				
					return "";
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_price_idxPrecision(){
				    return 4;
				}
				public String cons_price_idxDefault(){
				
					return "";
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_conf_idxPrecision(){
				    return 4;
				}
				public String cons_conf_idxDefault(){
				
					return "";
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer euribor3mPrecision(){
				    return 4;
				}
				public String euribor3mDefault(){
				
					return "";
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer nr_employedPrecision(){
				    return 4;
				}
				public String nr_employedDefault(){
				
					return "";
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "";
				
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
				
					return "";
				
				}
				public String yOriginalDbColumnName(){
				
					return "y";
				
				}

				
			    public String codigo_regla;

				public String getCodigo_regla () {
					return this.codigo_regla;
				}

				public Boolean codigo_reglaIsNullable(){
				    return true;
				}
				public Boolean codigo_reglaIsKey(){
				    return false;
				}
				public Integer codigo_reglaLength(){
				    return 15;
				}
				public Integer codigo_reglaPrecision(){
				    return null;
				}
				public String codigo_reglaDefault(){
				
					return null;
				
				}
				public String codigo_reglaComment(){
				
				    return "";
				
				}
				public String codigo_reglaPattern(){
				
					return "";
				
				}
				public String codigo_reglaOriginalDbColumnName(){
				
					return "codigo_regla";
				
				}

				
			    public String motivo_rechazo;

				public String getMotivo_rechazo () {
					return this.motivo_rechazo;
				}

				public Boolean motivo_rechazoIsNullable(){
				    return true;
				}
				public Boolean motivo_rechazoIsKey(){
				    return false;
				}
				public Integer motivo_rechazoLength(){
				    return 255;
				}
				public Integer motivo_rechazoPrecision(){
				    return null;
				}
				public String motivo_rechazoDefault(){
				
					return null;
				
				}
				public String motivo_rechazoComment(){
				
				    return "";
				
				}
				public String motivo_rechazoPattern(){
				
					return "";
				
				}
				public String motivo_rechazoOriginalDbColumnName(){
				
					return "motivo_rechazo";
				
				}

				
			    public String responsable_remediacion;

				public String getResponsable_remediacion () {
					return this.responsable_remediacion;
				}

				public Boolean responsable_remediacionIsNullable(){
				    return true;
				}
				public Boolean responsable_remediacionIsKey(){
				    return false;
				}
				public Integer responsable_remediacionLength(){
				    return 60;
				}
				public Integer responsable_remediacionPrecision(){
				    return null;
				}
				public String responsable_remediacionDefault(){
				
					return null;
				
				}
				public String responsable_remediacionComment(){
				
				    return "";
				
				}
				public String responsable_remediacionPattern(){
				
					return "";
				
				}
				public String responsable_remediacionOriginalDbColumnName(){
				
					return "responsable_remediacion";
				
				}

				
			    public String estado_remediacion;

				public String getEstado_remediacion () {
					return this.estado_remediacion;
				}

				public Boolean estado_remediacionIsNullable(){
				    return true;
				}
				public Boolean estado_remediacionIsKey(){
				    return false;
				}
				public Integer estado_remediacionLength(){
				    return 20;
				}
				public Integer estado_remediacionPrecision(){
				    return null;
				}
				public String estado_remediacionDefault(){
				
					return null;
				
				}
				public String estado_remediacionComment(){
				
				    return "";
				
				}
				public String estado_remediacionPattern(){
				
					return "";
				
				}
				public String estado_remediacionOriginalDbColumnName(){
				
					return "estado_remediacion";
				
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
				    return 26;
				}
				public Integer load_tsPrecision(){
				    return 0;
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
				    return 0;
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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
					
					this.codigo_regla = readString(dis);
					
					this.motivo_rechazo = readString(dis);
					
					this.responsable_remediacion = readString(dis);
					
					this.estado_remediacion = readString(dis);
					
					this.load_ts = readDate(dis);
					
					this.load_source = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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
					
					this.codigo_regla = readString(dis);
					
					this.motivo_rechazo = readString(dis);
					
					this.responsable_remediacion = readString(dis);
					
					this.estado_remediacion = readString(dis);
					
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
					
					// String
				
						writeString(this.codigo_regla,dos);
					
					// String
				
						writeString(this.motivo_rechazo,dos);
					
					// String
				
						writeString(this.responsable_remediacion,dos);
					
					// String
				
						writeString(this.estado_remediacion,dos);
					
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
					
					// String
				
						writeString(this.codigo_regla,dos);
					
					// String
				
						writeString(this.motivo_rechazo,dos);
					
					// String
				
						writeString(this.responsable_remediacion,dos);
					
					// String
				
						writeString(this.estado_remediacion,dos);
					
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
		sb.append(",codigo_regla="+codigo_regla);
		sb.append(",motivo_rechazo="+motivo_rechazo);
		sb.append(",responsable_remediacion="+responsable_remediacion);
		sb.append(",estado_remediacion="+estado_remediacion);
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
        		
        				if(codigo_regla == null){
        					sb.append("<null>");
        				}else{
            				sb.append(codigo_regla);
            			}
            		
        			sb.append("|");
        		
        				if(motivo_rechazo == null){
        					sb.append("<null>");
        				}else{
            				sb.append(motivo_rechazo);
            			}
            		
        			sb.append("|");
        		
        				if(responsable_remediacion == null){
        					sb.append("<null>");
        				}else{
            				sb.append(responsable_remediacion);
            			}
            		
        			sb.append("|");
        		
        				if(estado_remediacion == null){
        					sb.append("<null>");
        				}else{
            				sb.append(estado_remediacion);
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
    public int compareTo(out_NOTOKStruct other) {

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

public static class out_DQStruct implements routines.system.IPersistableRow<out_DQStruct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_02_quality = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[0];

	
			    public String run_id;

				public String getRun_id () {
					return this.run_id;
				}

				public Boolean run_idIsNullable(){
				    return false;
				}
				public Boolean run_idIsKey(){
				    return false;
				}
				public Integer run_idLength(){
				    return null;
				}
				public Integer run_idPrecision(){
				    return null;
				}
				public String run_idDefault(){
				
					return null;
				
				}
				public String run_idComment(){
				
				    return "";
				
				}
				public String run_idPattern(){
				
					return "";
				
				}
				public String run_idOriginalDbColumnName(){
				
					return "run_id";
				
				}

				
			    public Integer r01;

				public Integer getR01 () {
					return this.r01;
				}

				public Boolean r01IsNullable(){
				    return true;
				}
				public Boolean r01IsKey(){
				    return false;
				}
				public Integer r01Length(){
				    return null;
				}
				public Integer r01Precision(){
				    return null;
				}
				public String r01Default(){
				
					return null;
				
				}
				public String r01Comment(){
				
				    return "";
				
				}
				public String r01Pattern(){
				
					return "";
				
				}
				public String r01OriginalDbColumnName(){
				
					return "r01";
				
				}

				
			    public Integer r03;

				public Integer getR03 () {
					return this.r03;
				}

				public Boolean r03IsNullable(){
				    return true;
				}
				public Boolean r03IsKey(){
				    return false;
				}
				public Integer r03Length(){
				    return null;
				}
				public Integer r03Precision(){
				    return null;
				}
				public String r03Default(){
				
					return null;
				
				}
				public String r03Comment(){
				
				    return "";
				
				}
				public String r03Pattern(){
				
					return "";
				
				}
				public String r03OriginalDbColumnName(){
				
					return "r03";
				
				}

				
			    public Integer r04;

				public Integer getR04 () {
					return this.r04;
				}

				public Boolean r04IsNullable(){
				    return true;
				}
				public Boolean r04IsKey(){
				    return false;
				}
				public Integer r04Length(){
				    return null;
				}
				public Integer r04Precision(){
				    return null;
				}
				public String r04Default(){
				
					return null;
				
				}
				public String r04Comment(){
				
				    return "";
				
				}
				public String r04Pattern(){
				
					return "";
				
				}
				public String r04OriginalDbColumnName(){
				
					return "r04";
				
				}

				
			    public Integer r05;

				public Integer getR05 () {
					return this.r05;
				}

				public Boolean r05IsNullable(){
				    return true;
				}
				public Boolean r05IsKey(){
				    return false;
				}
				public Integer r05Length(){
				    return null;
				}
				public Integer r05Precision(){
				    return null;
				}
				public String r05Default(){
				
					return null;
				
				}
				public String r05Comment(){
				
				    return "";
				
				}
				public String r05Pattern(){
				
					return "";
				
				}
				public String r05OriginalDbColumnName(){
				
					return "r05";
				
				}

				
			    public Integer r06;

				public Integer getR06 () {
					return this.r06;
				}

				public Boolean r06IsNullable(){
				    return true;
				}
				public Boolean r06IsKey(){
				    return false;
				}
				public Integer r06Length(){
				    return null;
				}
				public Integer r06Precision(){
				    return null;
				}
				public String r06Default(){
				
					return null;
				
				}
				public String r06Comment(){
				
				    return "";
				
				}
				public String r06Pattern(){
				
					return "";
				
				}
				public String r06OriginalDbColumnName(){
				
					return "r06";
				
				}

				
			    public Integer r07;

				public Integer getR07 () {
					return this.r07;
				}

				public Boolean r07IsNullable(){
				    return true;
				}
				public Boolean r07IsKey(){
				    return false;
				}
				public Integer r07Length(){
				    return null;
				}
				public Integer r07Precision(){
				    return null;
				}
				public String r07Default(){
				
					return null;
				
				}
				public String r07Comment(){
				
				    return "";
				
				}
				public String r07Pattern(){
				
					return "";
				
				}
				public String r07OriginalDbColumnName(){
				
					return "r07";
				
				}

				
			    public Integer r08;

				public Integer getR08 () {
					return this.r08;
				}

				public Boolean r08IsNullable(){
				    return true;
				}
				public Boolean r08IsKey(){
				    return false;
				}
				public Integer r08Length(){
				    return null;
				}
				public Integer r08Precision(){
				    return null;
				}
				public String r08Default(){
				
					return null;
				
				}
				public String r08Comment(){
				
				    return "";
				
				}
				public String r08Pattern(){
				
					return "";
				
				}
				public String r08OriginalDbColumnName(){
				
					return "r08";
				
				}

				
			    public Integer r09;

				public Integer getR09 () {
					return this.r09;
				}

				public Boolean r09IsNullable(){
				    return true;
				}
				public Boolean r09IsKey(){
				    return false;
				}
				public Integer r09Length(){
				    return null;
				}
				public Integer r09Precision(){
				    return null;
				}
				public String r09Default(){
				
					return null;
				
				}
				public String r09Comment(){
				
				    return "";
				
				}
				public String r09Pattern(){
				
					return "";
				
				}
				public String r09OriginalDbColumnName(){
				
					return "r09";
				
				}

				
			    public Integer r10;

				public Integer getR10 () {
					return this.r10;
				}

				public Boolean r10IsNullable(){
				    return true;
				}
				public Boolean r10IsKey(){
				    return false;
				}
				public Integer r10Length(){
				    return null;
				}
				public Integer r10Precision(){
				    return null;
				}
				public String r10Default(){
				
					return null;
				
				}
				public String r10Comment(){
				
				    return "";
				
				}
				public String r10Pattern(){
				
					return "";
				
				}
				public String r10OriginalDbColumnName(){
				
					return "r10";
				
				}

				
			    public Integer r11;

				public Integer getR11 () {
					return this.r11;
				}

				public Boolean r11IsNullable(){
				    return true;
				}
				public Boolean r11IsKey(){
				    return false;
				}
				public Integer r11Length(){
				    return null;
				}
				public Integer r11Precision(){
				    return null;
				}
				public String r11Default(){
				
					return null;
				
				}
				public String r11Comment(){
				
				    return "";
				
				}
				public String r11Pattern(){
				
					return "";
				
				}
				public String r11OriginalDbColumnName(){
				
					return "r11";
				
				}

				
			    public Integer rules_passed;

				public Integer getRules_passed () {
					return this.rules_passed;
				}

				public Boolean rules_passedIsNullable(){
				    return true;
				}
				public Boolean rules_passedIsKey(){
				    return false;
				}
				public Integer rules_passedLength(){
				    return null;
				}
				public Integer rules_passedPrecision(){
				    return null;
				}
				public String rules_passedDefault(){
				
					return null;
				
				}
				public String rules_passedComment(){
				
				    return "";
				
				}
				public String rules_passedPattern(){
				
					return "";
				
				}
				public String rules_passedOriginalDbColumnName(){
				
					return "rules_passed";
				
				}

				
			    public Double dq_score;

				public Double getDq_score () {
					return this.dq_score;
				}

				public Boolean dq_scoreIsNullable(){
				    return true;
				}
				public Boolean dq_scoreIsKey(){
				    return false;
				}
				public Integer dq_scoreLength(){
				    return null;
				}
				public Integer dq_scorePrecision(){
				    return null;
				}
				public String dq_scoreDefault(){
				
					return null;
				
				}
				public String dq_scoreComment(){
				
				    return "";
				
				}
				public String dq_scorePattern(){
				
					return "";
				
				}
				public String dq_scoreOriginalDbColumnName(){
				
					return "dq_score";
				
				}

				
			    public String dq_status;

				public String getDq_status () {
					return this.dq_status;
				}

				public Boolean dq_statusIsNullable(){
				    return true;
				}
				public Boolean dq_statusIsKey(){
				    return false;
				}
				public Integer dq_statusLength(){
				    return null;
				}
				public Integer dq_statusPrecision(){
				    return null;
				}
				public String dq_statusDefault(){
				
					return null;
				
				}
				public String dq_statusComment(){
				
				    return "";
				
				}
				public String dq_statusPattern(){
				
					return "";
				
				}
				public String dq_statusOriginalDbColumnName(){
				
					return "dq_status";
				
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

				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

        	try {

        		int length = 0;
		
					this.run_id = readString(dis);
					
						this.r01 = readInteger(dis);
					
						this.r03 = readInteger(dis);
					
						this.r04 = readInteger(dis);
					
						this.r05 = readInteger(dis);
					
						this.r06 = readInteger(dis);
					
						this.r07 = readInteger(dis);
					
						this.r08 = readInteger(dis);
					
						this.r09 = readInteger(dis);
					
						this.r10 = readInteger(dis);
					
						this.r11 = readInteger(dis);
					
						this.rules_passed = readInteger(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.dq_score = null;
           				} else {
           			    	this.dq_score = dis.readDouble();
           				}
					
					this.dq_status = readString(dis);
					
					this.load_ts = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

        	try {

        		int length = 0;
		
					this.run_id = readString(dis);
					
						this.r01 = readInteger(dis);
					
						this.r03 = readInteger(dis);
					
						this.r04 = readInteger(dis);
					
						this.r05 = readInteger(dis);
					
						this.r06 = readInteger(dis);
					
						this.r07 = readInteger(dis);
					
						this.r08 = readInteger(dis);
					
						this.r09 = readInteger(dis);
					
						this.r10 = readInteger(dis);
					
						this.r11 = readInteger(dis);
					
						this.rules_passed = readInteger(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.dq_score = null;
           				} else {
           			    	this.dq_score = dis.readDouble();
           				}
					
					this.dq_status = readString(dis);
					
					this.load_ts = readDate(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.run_id,dos);
					
					// Integer
				
						writeInteger(this.r01,dos);
					
					// Integer
				
						writeInteger(this.r03,dos);
					
					// Integer
				
						writeInteger(this.r04,dos);
					
					// Integer
				
						writeInteger(this.r05,dos);
					
					// Integer
				
						writeInteger(this.r06,dos);
					
					// Integer
				
						writeInteger(this.r07,dos);
					
					// Integer
				
						writeInteger(this.r08,dos);
					
					// Integer
				
						writeInteger(this.r09,dos);
					
					// Integer
				
						writeInteger(this.r10,dos);
					
					// Integer
				
						writeInteger(this.r11,dos);
					
					// Integer
				
						writeInteger(this.rules_passed,dos);
					
					// Double
				
						if(this.dq_score == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.dq_score);
		            	}
					
					// String
				
						writeString(this.dq_status,dos);
					
					// java.util.Date
				
						writeDate(this.load_ts,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.run_id,dos);
					
					// Integer
				
						writeInteger(this.r01,dos);
					
					// Integer
				
						writeInteger(this.r03,dos);
					
					// Integer
				
						writeInteger(this.r04,dos);
					
					// Integer
				
						writeInteger(this.r05,dos);
					
					// Integer
				
						writeInteger(this.r06,dos);
					
					// Integer
				
						writeInteger(this.r07,dos);
					
					// Integer
				
						writeInteger(this.r08,dos);
					
					// Integer
				
						writeInteger(this.r09,dos);
					
					// Integer
				
						writeInteger(this.r10,dos);
					
					// Integer
				
						writeInteger(this.r11,dos);
					
					// Integer
				
						writeInteger(this.rules_passed,dos);
					
					// Double
				
						if(this.dq_score == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeDouble(this.dq_score);
		            	}
					
					// String
				
						writeString(this.dq_status,dos);
					
					// java.util.Date
				
						writeDate(this.load_ts,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("run_id="+run_id);
		sb.append(",r01="+String.valueOf(r01));
		sb.append(",r03="+String.valueOf(r03));
		sb.append(",r04="+String.valueOf(r04));
		sb.append(",r05="+String.valueOf(r05));
		sb.append(",r06="+String.valueOf(r06));
		sb.append(",r07="+String.valueOf(r07));
		sb.append(",r08="+String.valueOf(r08));
		sb.append(",r09="+String.valueOf(r09));
		sb.append(",r10="+String.valueOf(r10));
		sb.append(",r11="+String.valueOf(r11));
		sb.append(",rules_passed="+String.valueOf(rules_passed));
		sb.append(",dq_score="+String.valueOf(dq_score));
		sb.append(",dq_status="+dq_status);
		sb.append(",load_ts="+String.valueOf(load_ts));
	    sb.append("]");

	    return sb.toString();
    }
        public String toLogString(){
        	StringBuilder sb = new StringBuilder();
        	
        				if(run_id == null){
        					sb.append("<null>");
        				}else{
            				sb.append(run_id);
            			}
            		
        			sb.append("|");
        		
        				if(r01 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r01);
            			}
            		
        			sb.append("|");
        		
        				if(r03 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r03);
            			}
            		
        			sb.append("|");
        		
        				if(r04 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r04);
            			}
            		
        			sb.append("|");
        		
        				if(r05 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r05);
            			}
            		
        			sb.append("|");
        		
        				if(r06 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r06);
            			}
            		
        			sb.append("|");
        		
        				if(r07 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r07);
            			}
            		
        			sb.append("|");
        		
        				if(r08 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r08);
            			}
            		
        			sb.append("|");
        		
        				if(r09 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r09);
            			}
            		
        			sb.append("|");
        		
        				if(r10 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r10);
            			}
            		
        			sb.append("|");
        		
        				if(r11 == null){
        					sb.append("<null>");
        				}else{
            				sb.append(r11);
            			}
            		
        			sb.append("|");
        		
        				if(rules_passed == null){
        					sb.append("<null>");
        				}else{
            				sb.append(rules_passed);
            			}
            		
        			sb.append("|");
        		
        				if(dq_score == null){
        					sb.append("<null>");
        				}else{
            				sb.append(dq_score);
            			}
            		
        			sb.append("|");
        		
        				if(dq_status == null){
        					sb.append("<null>");
        				}else{
            				sb.append(dq_status);
            			}
            		
        			sb.append("|");
        		
        				if(load_ts == null){
        					sb.append("<null>");
        				}else{
            				sb.append(load_ts);
            			}
            		
        			sb.append("|");
        		
        	return sb.toString();
        }

    /**
     * Compare keys
     */
    public int compareTo(out_DQStruct other) {

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

public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_02_quality = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[0];

	
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
				    return 10;
				}
				public Integer agePrecision(){
				    return 0;
				}
				public String ageDefault(){
				
					return "";
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				    return 10;
				}
				public Integer durationPrecision(){
				    return 0;
				}
				public String durationDefault(){
				
					return "";
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer campaignPrecision(){
				    return 0;
				}
				public String campaignDefault(){
				
					return "";
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer pdaysPrecision(){
				    return 0;
				}
				public String pdaysDefault(){
				
					return "";
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer previousPrecision(){
				    return 0;
				}
				public String previousDefault(){
				
					return "";
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 50;
				}
				public Integer emp_var_ratePrecision(){
				    return 2;
				}
				public String emp_var_rateDefault(){
				
					return "";
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_price_idxPrecision(){
				    return 4;
				}
				public String cons_price_idxDefault(){
				
					return "";
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_conf_idxPrecision(){
				    return 4;
				}
				public String cons_conf_idxDefault(){
				
					return "";
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer euribor3mPrecision(){
				    return 4;
				}
				public String euribor3mDefault(){
				
					return "";
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer nr_employedPrecision(){
				    return 4;
				}
				public String nr_employedDefault(){
				
					return "";
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 26;
				}
				public Integer load_tsPrecision(){
				    return 0;
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
				    return 0;
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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
    public int compareTo(row1Struct other) {

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

public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
    final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_02_quality = new byte[0];
    static byte[] commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[0];

	
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
				    return 10;
				}
				public Integer agePrecision(){
				    return 0;
				}
				public String ageDefault(){
				
					return "";
				
				}
				public String ageComment(){
				
				    return "";
				
				}
				public String agePattern(){
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				
					return "";
				
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
				    return 10;
				}
				public Integer durationPrecision(){
				    return 0;
				}
				public String durationDefault(){
				
					return "";
				
				}
				public String durationComment(){
				
				    return "";
				
				}
				public String durationPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer campaignPrecision(){
				    return 0;
				}
				public String campaignDefault(){
				
					return "";
				
				}
				public String campaignComment(){
				
				    return "";
				
				}
				public String campaignPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer pdaysPrecision(){
				    return 0;
				}
				public String pdaysDefault(){
				
					return "";
				
				}
				public String pdaysComment(){
				
				    return "";
				
				}
				public String pdaysPattern(){
				
					return "";
				
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
				    return 10;
				}
				public Integer previousPrecision(){
				    return 0;
				}
				public String previousDefault(){
				
					return "";
				
				}
				public String previousComment(){
				
				    return "";
				
				}
				public String previousPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 50;
				}
				public Integer emp_var_ratePrecision(){
				    return 2;
				}
				public String emp_var_rateDefault(){
				
					return "";
				
				}
				public String emp_var_rateComment(){
				
				    return "";
				
				}
				public String emp_var_ratePattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_price_idxPrecision(){
				    return 4;
				}
				public String cons_price_idxDefault(){
				
					return "";
				
				}
				public String cons_price_idxComment(){
				
				    return "";
				
				}
				public String cons_price_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer cons_conf_idxPrecision(){
				    return 4;
				}
				public String cons_conf_idxDefault(){
				
					return "";
				
				}
				public String cons_conf_idxComment(){
				
				    return "";
				
				}
				public String cons_conf_idxPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer euribor3mPrecision(){
				    return 4;
				}
				public String euribor3mDefault(){
				
					return "";
				
				}
				public String euribor3mComment(){
				
				    return "";
				
				}
				public String euribor3mPattern(){
				
					return "";
				
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
				    return 50;
				}
				public Integer nr_employedPrecision(){
				    return 4;
				}
				public String nr_employedDefault(){
				
					return "";
				
				}
				public String nr_employedComment(){
				
				    return "";
				
				}
				public String nr_employedPattern(){
				
					return "";
				
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
				
					return "";
				
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
				    return 26;
				}
				public Integer load_tsPrecision(){
				    return 0;
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
				    return 0;
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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
			if(length > commonByteArray_G3_UCI_BANK_JOB_02_quality.length) {
				if(length < 1024 && commonByteArray_G3_UCI_BANK_JOB_02_quality.length == 0) {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[1024];
				} else {
   					commonByteArray_G3_UCI_BANK_JOB_02_quality = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length);
			strReturn = new String(commonByteArray_G3_UCI_BANK_JOB_02_quality, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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

		synchronized(commonByteArrayLock_G3_UCI_BANK_JOB_02_quality) {

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
    public int compareTo(row3Struct other) {

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

public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tDBInput_2", "3XDODY_");

	
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



		row3Struct row3 = new row3Struct();
row1Struct row1 = new row1Struct();
out_OKStruct out_OK = new out_OKStruct();
out_NOTOKStruct out_NOTOK = new out_NOTOKStruct();
out_DQStruct out_DQ = new out_DQStruct();






	
	/**
	 * [tDBOutput_3 begin ] start
	 */

	

	
		
		sh("tDBOutput_3");
		
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"out_OK");
			
		int tos_count_tDBOutput_3 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBOutput_3{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBOutput_3 = new StringBuilder();
                    log4jParamters_tDBOutput_3.append("Parameters:");
                            log4jParamters_tDBOutput_3.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("TABLE" + " = " + "\"stg_bank_OK_g3\"");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("TABLE_ACTION" + " = " + "CREATE_IF_NOT_EXISTS");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("DATA_ACTION" + " = " + "INSERT");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true\"");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("USE_BATCH_SIZE" + " = " + "true");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("BATCH_SIZE" + " = " + "10000");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("ADD_COLS" + " = " + "[]");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("USE_FIELD_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("USE_HINT_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("ENABLE_DEBUG_MODE" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
                        log4jParamters_tDBOutput_3.append(" | ");
                            log4jParamters_tDBOutput_3.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
                        log4jParamters_tDBOutput_3.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + (log4jParamters_tDBOutput_3) );
                    } 
                } 
            new BytesLimit65535_tDBOutput_3().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBOutput_3{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final out_OKStruct out_OK) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("TABLE", String.valueOf("stg_bank_OK_g3"));
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
                
                
                
                } catch (java.lang.Exception e_tDBOutput_3) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBOutput_3", "tMysqlOutput",
          new ParameterUtil_tDBOutput_3().getParameter(out_OK));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBOutput_3", "tDBO_stg_bank_OK_g3", "tMysqlOutput");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			







int nb_line_tDBOutput_3 = 0;
int nb_line_update_tDBOutput_3 = 0;
int nb_line_inserted_tDBOutput_3 = 0;
int nb_line_deleted_tDBOutput_3 = 0;
int nb_line_rejected_tDBOutput_3 = 0;

int deletedCount_tDBOutput_3=0;
int updatedCount_tDBOutput_3=0;
int insertedCount_tDBOutput_3=0;
int rowsToCommitCount_tDBOutput_3=0;
int rejectedCount_tDBOutput_3=0;

String tableName_tDBOutput_3 = "stg_bank_OK_g3";
boolean whetherReject_tDBOutput_3 = false;

java.util.Calendar calendar_tDBOutput_3 = java.util.Calendar.getInstance();
calendar_tDBOutput_3.set(1, 0, 1, 0, 0, 0);
long year1_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
calendar_tDBOutput_3.set(10000, 0, 1, 0, 0, 0);
long year10000_tDBOutput_3 = calendar_tDBOutput_3.getTime().getTime();
long date_tDBOutput_3;

java.sql.Connection conn_tDBOutput_3 = null;
		
			
        String properties_tDBOutput_3 = "noDatetimeStringSync=true";
        if (properties_tDBOutput_3 == null || properties_tDBOutput_3.trim().length() == 0) {
            properties_tDBOutput_3 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBOutput_3.contains("rewriteBatchedStatements=")) {
                properties_tDBOutput_3 += "&rewriteBatchedStatements=true";
            }

            if (!properties_tDBOutput_3.contains("allowLoadLocalInfile=")) {
                properties_tDBOutput_3 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBOutput_3 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBOutput_3;

			String driverClass_tDBOutput_3 = "com.mysql.cj.jdbc.Driver";
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Driver ClassName: ")  + (driverClass_tDBOutput_3)  + (".") );
			String dbUser_tDBOutput_3 = context.DB_USER;
			

			
	final String decryptedPassword_tDBOutput_3 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");

			String dbPwd_tDBOutput_3 = decryptedPassword_tDBOutput_3;
			java.lang.Class.forName(driverClass_tDBOutput_3);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection attempts to '")  + (url_tDBOutput_3)  + ("' with the username '")  + (dbUser_tDBOutput_3)  + ("'.") );
			conn_tDBOutput_3 = java.sql.DriverManager.getConnection(url_tDBOutput_3, dbUser_tDBOutput_3, dbPwd_tDBOutput_3);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection to '")  + (url_tDBOutput_3)  + ("' has succeeded.") );
			
	resourceMap.put("conn_tDBOutput_3", conn_tDBOutput_3);
	
			conn_tDBOutput_3.setAutoCommit(false);
			int commitEvery_tDBOutput_3 = 10000;
			int commitCounter_tDBOutput_3 = 0;
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection is set auto commit to '")  + (conn_tDBOutput_3.getAutoCommit())  + ("'.") );

		int count_tDBOutput_3=0;
		
				
                                java.sql.DatabaseMetaData dbMetaData_tDBOutput_3 = conn_tDBOutput_3.getMetaData();
                                    java.sql.ResultSet rsTable_tDBOutput_3 = dbMetaData_tDBOutput_3.getTables(context.DB_NAME, null, null, new String[]{"TABLE"});
                                boolean whetherExist_tDBOutput_3 = false;
                                while(rsTable_tDBOutput_3.next()) {
                                    String table_tDBOutput_3 = rsTable_tDBOutput_3.getString("TABLE_NAME");
                                    if(table_tDBOutput_3.equalsIgnoreCase("stg_bank_OK_g3")) {
                                        whetherExist_tDBOutput_3 = true;
                                        break;
                                    }
                                }
                                if(!whetherExist_tDBOutput_3) {
                                    try (java.sql.Statement stmtCreate_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Creating")  + (" table '")  + (tableName_tDBOutput_3)  + ("'.") );
                                        stmtCreate_tDBOutput_3.execute("CREATE TABLE `" + tableName_tDBOutput_3 + "`(`age` INT(3)  ,`job` VARCHAR(13)  ,`marital` VARCHAR(8)  ,`education` VARCHAR(19)  ,`has_default` VARCHAR(7)  ,`housing` VARCHAR(7)  ,`loan` VARCHAR(7)  ,`contact` VARCHAR(20)  ,`month` VARCHAR(3)  ,`day_of_week` VARCHAR(3)  ,`duration` INT(4)  ,`campaign` INT(1)  ,`pdays` INT(3)  ,`previous` INT(1)  ,`poutcome` VARCHAR(11)  ,`emp_var_rate` FLOAT(10,4)  ,`cons_price_idx` FLOAT(10,4)  ,`cons_conf_idx` DOUBLE(10,4)  ,`euribor3m` FLOAT(10,4)  ,`nr_employed` DOUBLE(12,4)  ,`y` VARCHAR(3)  ,`load_ts` DATETIME ,`load_source` VARCHAR(60)  )");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Create")  + (" table '")  + (tableName_tDBOutput_3)  + ("' has succeeded.") );
                                    }
                                }
			

		
						String insert_tDBOutput_3 = "INSERT INTO `" + "stg_bank_OK_g3" + "` (`age`,`job`,`marital`,`education`,`has_default`,`housing`,`loan`,`contact`,`month`,`day_of_week`,`duration`,`campaign`,`pdays`,`previous`,`poutcome`,`emp_var_rate`,`cons_price_idx`,`cons_conf_idx`,`euribor3m`,`nr_employed`,`y`,`load_ts`,`load_source`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
						int batchSize_tDBOutput_3 = 10000;
						int batchSizeCounter_tDBOutput_3=0;
						    
						java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
						resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);


 



		

/**
 * [tDBOutput_3 begin ] stop
 */





	
	/**
	 * [tDBOutput_4 begin ] start
	 */

	

	
		
		sh("tDBOutput_4");
		
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"out_NOTOK");
			
		int tos_count_tDBOutput_4 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBOutput_4{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBOutput_4 = new StringBuilder();
                    log4jParamters_tDBOutput_4.append("Parameters:");
                            log4jParamters_tDBOutput_4.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("TABLE" + " = " + "\"stg_bank_NOTOK_g3\"");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("TABLE_ACTION" + " = " + "CREATE_IF_NOT_EXISTS");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("DATA_ACTION" + " = " + "INSERT");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true\"");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("USE_BATCH_SIZE" + " = " + "true");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("BATCH_SIZE" + " = " + "10000");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("ADD_COLS" + " = " + "[]");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("USE_FIELD_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("USE_HINT_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("ENABLE_DEBUG_MODE" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
                        log4jParamters_tDBOutput_4.append(" | ");
                            log4jParamters_tDBOutput_4.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
                        log4jParamters_tDBOutput_4.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + (log4jParamters_tDBOutput_4) );
                    } 
                } 
            new BytesLimit65535_tDBOutput_4().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBOutput_4{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final out_NOTOKStruct out_NOTOK) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("TABLE", String.valueOf("stg_bank_NOTOK_g3"));
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
                
                
                
                } catch (java.lang.Exception e_tDBOutput_4) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBOutput_4", "tMysqlOutput",
          new ParameterUtil_tDBOutput_4().getParameter(out_NOTOK));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBOutput_4", "tDBO_stg_bank_NOTOK_g3", "tMysqlOutput");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			







int nb_line_tDBOutput_4 = 0;
int nb_line_update_tDBOutput_4 = 0;
int nb_line_inserted_tDBOutput_4 = 0;
int nb_line_deleted_tDBOutput_4 = 0;
int nb_line_rejected_tDBOutput_4 = 0;

int deletedCount_tDBOutput_4=0;
int updatedCount_tDBOutput_4=0;
int insertedCount_tDBOutput_4=0;
int rowsToCommitCount_tDBOutput_4=0;
int rejectedCount_tDBOutput_4=0;

String tableName_tDBOutput_4 = "stg_bank_NOTOK_g3";
boolean whetherReject_tDBOutput_4 = false;

java.util.Calendar calendar_tDBOutput_4 = java.util.Calendar.getInstance();
calendar_tDBOutput_4.set(1, 0, 1, 0, 0, 0);
long year1_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
calendar_tDBOutput_4.set(10000, 0, 1, 0, 0, 0);
long year10000_tDBOutput_4 = calendar_tDBOutput_4.getTime().getTime();
long date_tDBOutput_4;

java.sql.Connection conn_tDBOutput_4 = null;
		
			
        String properties_tDBOutput_4 = "noDatetimeStringSync=true";
        if (properties_tDBOutput_4 == null || properties_tDBOutput_4.trim().length() == 0) {
            properties_tDBOutput_4 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBOutput_4.contains("rewriteBatchedStatements=")) {
                properties_tDBOutput_4 += "&rewriteBatchedStatements=true";
            }

            if (!properties_tDBOutput_4.contains("allowLoadLocalInfile=")) {
                properties_tDBOutput_4 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBOutput_4 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBOutput_4;

			String driverClass_tDBOutput_4 = "com.mysql.cj.jdbc.Driver";
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Driver ClassName: ")  + (driverClass_tDBOutput_4)  + (".") );
			String dbUser_tDBOutput_4 = context.DB_USER;
			

			
	final String decryptedPassword_tDBOutput_4 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");

			String dbPwd_tDBOutput_4 = decryptedPassword_tDBOutput_4;
			java.lang.Class.forName(driverClass_tDBOutput_4);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection attempts to '")  + (url_tDBOutput_4)  + ("' with the username '")  + (dbUser_tDBOutput_4)  + ("'.") );
			conn_tDBOutput_4 = java.sql.DriverManager.getConnection(url_tDBOutput_4, dbUser_tDBOutput_4, dbPwd_tDBOutput_4);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection to '")  + (url_tDBOutput_4)  + ("' has succeeded.") );
			
	resourceMap.put("conn_tDBOutput_4", conn_tDBOutput_4);
	
			conn_tDBOutput_4.setAutoCommit(false);
			int commitEvery_tDBOutput_4 = 10000;
			int commitCounter_tDBOutput_4 = 0;
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection is set auto commit to '")  + (conn_tDBOutput_4.getAutoCommit())  + ("'.") );

		int count_tDBOutput_4=0;
		
				
                                java.sql.DatabaseMetaData dbMetaData_tDBOutput_4 = conn_tDBOutput_4.getMetaData();
                                    java.sql.ResultSet rsTable_tDBOutput_4 = dbMetaData_tDBOutput_4.getTables(context.DB_NAME, null, null, new String[]{"TABLE"});
                                boolean whetherExist_tDBOutput_4 = false;
                                while(rsTable_tDBOutput_4.next()) {
                                    String table_tDBOutput_4 = rsTable_tDBOutput_4.getString("TABLE_NAME");
                                    if(table_tDBOutput_4.equalsIgnoreCase("stg_bank_NOTOK_g3")) {
                                        whetherExist_tDBOutput_4 = true;
                                        break;
                                    }
                                }
                                if(!whetherExist_tDBOutput_4) {
                                    try (java.sql.Statement stmtCreate_tDBOutput_4 = conn_tDBOutput_4.createStatement()) {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Creating")  + (" table '")  + (tableName_tDBOutput_4)  + ("'.") );
                                        stmtCreate_tDBOutput_4.execute("CREATE TABLE `" + tableName_tDBOutput_4 + "`(`age` INT(10)  ,`job` VARCHAR(13)  ,`marital` VARCHAR(8)  ,`education` VARCHAR(19)  ,`has_default` VARCHAR(7)  ,`housing` VARCHAR(7)  ,`loan` VARCHAR(7)  ,`contact` VARCHAR(20)  ,`month` VARCHAR(3)  ,`day_of_week` VARCHAR(3)  ,`duration` INT(10)  ,`campaign` INT(10)  ,`pdays` INT(10)  ,`previous` INT(10)  ,`poutcome` VARCHAR(11)  ,`emp_var_rate` FLOAT(50,2)  ,`cons_price_idx` FLOAT(50,4)  ,`cons_conf_idx` DOUBLE(50,4)  ,`euribor3m` FLOAT(50,4)  ,`nr_employed` DOUBLE(50,4)  ,`y` VARCHAR(3)  ,`codigo_regla` VARCHAR(15)  ,`motivo_rechazo` VARCHAR(255)  ,`responsable_remediacion` VARCHAR(60)  ,`estado_remediacion` VARCHAR(20)  ,`load_ts` DATETIME ,`load_source` VARCHAR(60)  )");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Create")  + (" table '")  + (tableName_tDBOutput_4)  + ("' has succeeded.") );
                                    }
                                }
			

		
						String insert_tDBOutput_4 = "INSERT INTO `" + "stg_bank_NOTOK_g3" + "` (`age`,`job`,`marital`,`education`,`has_default`,`housing`,`loan`,`contact`,`month`,`day_of_week`,`duration`,`campaign`,`pdays`,`previous`,`poutcome`,`emp_var_rate`,`cons_price_idx`,`cons_conf_idx`,`euribor3m`,`nr_employed`,`y`,`codigo_regla`,`motivo_rechazo`,`responsable_remediacion`,`estado_remediacion`,`load_ts`,`load_source`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
						int batchSize_tDBOutput_4 = 10000;
						int batchSizeCounter_tDBOutput_4=0;
						    
						java.sql.PreparedStatement pstmt_tDBOutput_4 = conn_tDBOutput_4.prepareStatement(insert_tDBOutput_4);
						resourceMap.put("pstmt_tDBOutput_4", pstmt_tDBOutput_4);


 



		

/**
 * [tDBOutput_4 begin ] stop
 */





	
	/**
	 * [tDBOutput_1 begin ] start
	 */

	

	
		
		sh("tDBOutput_1");
		
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"out_DQ");
			
		int tos_count_tDBOutput_1 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBOutput_1{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBOutput_1 = new StringBuilder();
                    log4jParamters_tDBOutput_1.append("Parameters:");
                            log4jParamters_tDBOutput_1.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("TABLE" + " = " + "\"dq_record_g3\"");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("TABLE_ACTION" + " = " + "NONE");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("DATA_ACTION" + " = " + "INSERT");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("DIE_ON_ERROR" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true\"");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("USE_BATCH_SIZE" + " = " + "true");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("BATCH_SIZE" + " = " + "10000");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("ADD_COLS" + " = " + "[]");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("USE_FIELD_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("USE_HINT_OPTIONS" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("ENABLE_DEBUG_MODE" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
                        log4jParamters_tDBOutput_1.append(" | ");
                            log4jParamters_tDBOutput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlOutput");
                        log4jParamters_tDBOutput_1.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + (log4jParamters_tDBOutput_1) );
                    } 
                } 
            new BytesLimit65535_tDBOutput_1().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBOutput_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final out_DQStruct out_DQ) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("TABLE", String.valueOf("dq_record_g3"));
                            component_parameters.put("TABLE_ACTION", String.valueOf("NONE"));
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
                
                
                
                } catch (java.lang.Exception e_tDBOutput_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBOutput_1", "tMysqlOutput",
          new ParameterUtil_tDBOutput_1().getParameter(out_DQ));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBOutput_1", "tDBO_dq_record_g3", "tMysqlOutput");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			







int nb_line_tDBOutput_1 = 0;
int nb_line_update_tDBOutput_1 = 0;
int nb_line_inserted_tDBOutput_1 = 0;
int nb_line_deleted_tDBOutput_1 = 0;
int nb_line_rejected_tDBOutput_1 = 0;

int deletedCount_tDBOutput_1=0;
int updatedCount_tDBOutput_1=0;
int insertedCount_tDBOutput_1=0;
int rowsToCommitCount_tDBOutput_1=0;
int rejectedCount_tDBOutput_1=0;

String tableName_tDBOutput_1 = "dq_record_g3";
boolean whetherReject_tDBOutput_1 = false;

java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
calendar_tDBOutput_1.set(1, 0, 1, 0, 0, 0);
long year1_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
calendar_tDBOutput_1.set(10000, 0, 1, 0, 0, 0);
long year10000_tDBOutput_1 = calendar_tDBOutput_1.getTime().getTime();
long date_tDBOutput_1;

java.sql.Connection conn_tDBOutput_1 = null;
		
			
        String properties_tDBOutput_1 = "noDatetimeStringSync=true";
        if (properties_tDBOutput_1 == null || properties_tDBOutput_1.trim().length() == 0) {
            properties_tDBOutput_1 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBOutput_1.contains("rewriteBatchedStatements=")) {
                properties_tDBOutput_1 += "&rewriteBatchedStatements=true";
            }

            if (!properties_tDBOutput_1.contains("allowLoadLocalInfile=")) {
                properties_tDBOutput_1 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBOutput_1 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBOutput_1;

			String driverClass_tDBOutput_1 = "com.mysql.cj.jdbc.Driver";
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Driver ClassName: ")  + (driverClass_tDBOutput_1)  + (".") );
			String dbUser_tDBOutput_1 = context.DB_USER;
			

			
	final String decryptedPassword_tDBOutput_1 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");

			String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;
			java.lang.Class.forName(driverClass_tDBOutput_1);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection attempts to '")  + (url_tDBOutput_1)  + ("' with the username '")  + (dbUser_tDBOutput_1)  + ("'.") );
			conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1, dbPwd_tDBOutput_1);
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection to '")  + (url_tDBOutput_1)  + ("' has succeeded.") );
			
	resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
	
			conn_tDBOutput_1.setAutoCommit(false);
			int commitEvery_tDBOutput_1 = 10000;
			int commitCounter_tDBOutput_1 = 0;
			
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection is set auto commit to '")  + (conn_tDBOutput_1.getAutoCommit())  + ("'.") );

		int count_tDBOutput_1=0;
		
				
			

		
						String insert_tDBOutput_1 = "INSERT INTO `" + "dq_record_g3" + "` (`run_id`,`r01`,`r03`,`r04`,`r05`,`r06`,`r07`,`r08`,`r09`,`r10`,`r11`,`rules_passed`,`dq_score`,`dq_status`,`load_ts`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
						int batchSize_tDBOutput_1 = 10000;
						int batchSizeCounter_tDBOutput_1=0;
						    
						java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
						resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);


 



		

/**
 * [tDBOutput_1 begin ] stop
 */




	
	/**
	 * [tMap_2 begin ] start
	 */

	

	
		
		sh("tMap_2");
		
	
	s(currentComponent="tMap_2");
	
			
			
	
			cLabel="tMap_DQ";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"row1");
			
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
                boolean init_tMap_2_1 = true;
                boolean init_tMap_2_2 = true;
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tMap_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final row1Struct row1) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("LINK_STYLE", String.valueOf("AUTO"));
                            component_parameters.put("ROWS_BUFFER_SIZE", String.valueOf("2000000"));
                            component_parameters.put("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL", String.valueOf("true"));
                component_parameters.put("mapperData", String.valueOf("{\"inputTables\":[{\"name\":\"row1\",\"metadataTableEntries\":[{\"name\":\"age\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"job\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"marital\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"education\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"has_default\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"housing\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"loan\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"contact\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"month\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"day_of_week\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"duration\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"campaign\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"pdays\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"previous\",\"expression\":\"\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"poutcome\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"emp_var_rate\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_price_idx\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_conf_idx\",\"expression\":\"\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"euribor3m\",\"expression\":\"\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"nr_employed\",\"expression\":\"\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"y\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"load_ts\",\"expression\":\"\",\"type\":\"id_Date\",\"nullable\":false},{\"name\":\"load_source\",\"expression\":\"\",\"type\":\"id_String\",\"nullable\":false}]}],\"outputTables\":[{\"name\":\"out_OK\",\"metadataTableEntries\":[{\"name\":\"age\",\"expression\":\"row1.age\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"job\",\"expression\":\"row1.job\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"marital\",\"expression\":\"row1.marital\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"education\",\"expression\":\"row1.education\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"has_default\",\"expression\":\"row1.has_default\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"housing\",\"expression\":\"row1.housing\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"loan\",\"expression\":\"row1.loan\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"contact\",\"expression\":\"row1.contact\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"month\",\"expression\":\"row1.month\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"day_of_week\",\"expression\":\"row1.day_of_week\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"duration\",\"expression\":\"row1.duration\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"campaign\",\"expression\":\"row1.campaign\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"pdays\",\"expression\":\"row1.pdays\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"previous\",\"expression\":\"row1.previous\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"poutcome\",\"expression\":\"row1.poutcome\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"emp_var_rate\",\"expression\":\"row1.emp_var_rate\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_price_idx\",\"expression\":\"row1.cons_price_idx\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_conf_idx\",\"expression\":\"row1.cons_conf_idx\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"euribor3m\",\"expression\":\"row1.euribor3m\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"nr_employed\",\"expression\":\"row1.nr_employed\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"y\",\"expression\":\"row1.y\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"load_ts\",\"expression\":\"TalendDate.getCurrentDate()\",\"type\":\"id_Date\",\"nullable\":false},{\"name\":\"load_source\",\"expression\":\"row1.load_source\",\"type\":\"id_String\",\"nullable\":false}]},{\"name\":\"out_NOTOK\",\"metadataTableEntries\":[{\"name\":\"age\",\"expression\":\"row1.age\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"job\",\"expression\":\"row1.job\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"marital\",\"expression\":\"row1.marital\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"education\",\"expression\":\"row1.education\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"has_default\",\"expression\":\"row1.has_default\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"housing\",\"expression\":\"row1.housing\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"loan\",\"expression\":\"row1.loan\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"contact\",\"expression\":\"row1.contact\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"month\",\"expression\":\"row1.month\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"day_of_week\",\"expression\":\"row1.day_of_week\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"duration\",\"expression\":\"row1.duration\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"campaign\",\"expression\":\"row1.campaign\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"pdays\",\"expression\":\"row1.pdays\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"previous\",\"expression\":\"row1.previous\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"poutcome\",\"expression\":\"row1.poutcome\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"emp_var_rate\",\"expression\":\"row1.emp_var_rate\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_price_idx\",\"expression\":\"row1.cons_price_idx\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"cons_conf_idx\",\"expression\":\"row1.cons_conf_idx\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"euribor3m\",\"expression\":\"row1.euribor3m\",\"type\":\"id_Float\",\"nullable\":false},{\"name\":\"nr_employed\",\"expression\":\"row1.nr_employed\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"y\",\"expression\":\"row1.y\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"codigo_regla\",\"expression\":\"!Var.r01_completitud ? \\\"DQ-01\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r03_edad_valida ? \\\"DQ-03\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r04_duracion_valida ? \\\"DQ-04\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r05_campania_valida ? \\\"DQ-05\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r06_pdays_valido ? \\\"DQ-06\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r07_objetivo_valido ? \\\"DQ-07\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r08_mes_valido ? \\\"DQ-08\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r09_dia_semana_valido ? \\\"DQ-09\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r10_resultado_anterior_valido ? \\\"DQ-10\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r11_contacto_valido ? \\\"DQ-11\\\" :\\r\\n\\t\\t\\t\\t\\t\\\"DQ-00\\\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"motivo_rechazo\",\"expression\":\"!Var.r01_completitud ? \\\"Registro con campos obligatorios incompletos\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r03_edad_valida ? \\\"Edad inferior a 18 o fuera del rango permitido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r04_duracion_valida ? \\\"Duracion no valida\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r05_campania_valida ? \\\"Numero de contactos de campania no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r06_pdays_valido ? \\\"Valor de pdays no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r07_objetivo_valido ? \\\"Objetivo de suscripcion no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r08_mes_valido ? \\\"Mes no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r09_dia_semana_valido ? \\\"Dia de semana no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r10_resultado_anterior_valido ? \\\"Resultado anterior no valido\\\" :\\r\\n\\t\\t\\t\\t\\t!Var.r11_contacto_valido ? \\\"Tipo de contacto no valido\\\" :\\r\\n\\t\\t\\t\\t\\t\\\"Incumplimiento de regla DQ\\\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"responsable_remediacion\",\"expression\":\"\\\"Data Steward \u2014 Grupo 3\\\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"estado_remediacion\",\"expression\":\"\\\"EN_REVISION\\\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"load_ts\",\"expression\":\"TalendDate.getCurrentDate()\",\"type\":\"id_Date\",\"nullable\":false},{\"name\":\"load_source\",\"expression\":\"row1.load_source\",\"type\":\"id_String\",\"nullable\":false}]},{\"name\":\"out_DQ\",\"metadataTableEntries\":[{\"name\":\"run_id\",\"expression\":\"(String)globalMap.get(\\\"RUN_ID\\\")\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"r01\",\"expression\":\"Var.r01_completitud ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r03\",\"expression\":\"Var.r03_edad_valida ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r04\",\"expression\":\"Var.r04_duracion_valida ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r05\",\"expression\":\"Var.r05_campania_valida ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r06\",\"expression\":\"Var.r06_pdays_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r07\",\"expression\":\"Var.r07_objetivo_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r08\",\"expression\":\"Var.r08_mes_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r09\",\"expression\":\"Var.r09_dia_semana_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r10\",\"expression\":\"Var.r10_resultado_anterior_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"r11\",\"expression\":\"Var.r11_contacto_valido ? 1 : 0\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"rules_passed\",\"expression\":\"Var.n_ok\",\"type\":\"id_Integer\",\"nullable\":false},{\"name\":\"dq_score\",\"expression\":\"Var.n_ok * 100.0 / 10\",\"type\":\"id_Double\",\"nullable\":false},{\"name\":\"dq_status\",\"expression\":\"Var.n_ok == 10 ? \\\"OK\\\" : \\\"NOTOK\\\"\",\"type\":\"id_String\",\"nullable\":false},{\"name\":\"load_ts\",\"expression\":\"TalendDate.getCurrentDate()\",\"type\":\"id_Date\",\"nullable\":false}]}],\"varsTables\":[{\"name\":\"Var\",\"metadataTableEntries\":[{\"name\":\"r01_completitud\",\"expression\":\"row1.age != null\\r\\n\\t\\t\\t\\t&& row1.job != null && !row1.job.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.marital != null && !row1.marital.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.education != null && !row1.education.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.has_default != null && !row1.has_default.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.housing != null && !row1.housing.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.loan != null && !row1.loan.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.contact != null && !row1.contact.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.month != null && !row1.month.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.day_of_week != null && !row1.day_of_week.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.duration != null\\r\\n\\t\\t\\t\\t&& row1.campaign != null\\r\\n\\t\\t\\t\\t&& row1.pdays != null\\r\\n\\t\\t\\t\\t&& row1.previous != null\\r\\n\\t\\t\\t\\t&& row1.poutcome != null && !row1.poutcome.trim().isEmpty()\\r\\n\\t\\t\\t\\t&& row1.emp_var_rate != null\\r\\n\\t\\t\\t\\t&& row1.cons_price_idx != null\\r\\n\\t\\t\\t\\t&& row1.cons_conf_idx != null\\r\\n\\t\\t\\t\\t&& row1.euribor3m != null\\r\\n\\t\\t\\t\\t&& row1.nr_employed != null\\r\\n\\t\\t\\t\\t&& row1.y != null && !row1.y.trim().isEmpty()\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r03_edad_valida\",\"expression\":\"row1.age != null\\r\\n\\t\\t\\t\\t&& row1.age >= 18\\r\\n\\t\\t\\t\\t&& row1.age <= 100\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r04_duracion_valida\",\"expression\":\"row1.duration != null\\r\\n\\t\\t\\t\\t&& row1.duration >= 0\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r05_campania_valida\",\"expression\":\"row1.campaign != null\\r\\n\\t\\t\\t\\t&& row1.campaign >= 1\\r\\n\\t\\t\\t\\t&& row1.previous != null\\r\\n\\t\\t\\t\\t&& row1.previous >= 0\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r06_pdays_valido\",\"expression\":\"row1.pdays != null\\r\\n\\t\\t\\t\\t&& (row1.pdays >= 0 || row1.pdays == 999)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r07_objetivo_valido\",\"expression\":\"row1.y != null\\r\\n\\t\\t\\t\\t&& java.util.Arrays.asList(\\\"yes\\\", \\\"no\\\").contains(row1.y)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r08_mes_valido\",\"expression\":\"row1.month != null\\r\\n\\t\\t\\t\\t&& java.util.Arrays.asList(\\r\\n\\t\\t\\t\\t    \\\"jan\\\", \\\"feb\\\", \\\"mar\\\", \\\"apr\\\", \\\"may\\\", \\\"jun\\\",\\r\\n\\t\\t\\t\\t    \\\"jul\\\", \\\"aug\\\", \\\"sep\\\", \\\"oct\\\", \\\"nov\\\", \\\"dec\\\"\\r\\n\\t\\t\\t\\t).contains(row1.month)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r09_dia_semana_valido\",\"expression\":\"row1.day_of_week != null\\r\\n\\t\\t\\t\\t&& java.util.Arrays.asList(\\r\\n\\t\\t\\t\\t    \\\"mon\\\", \\\"tue\\\", \\\"wed\\\", \\\"thu\\\", \\\"fri\\\"\\r\\n\\t\\t\\t\\t).contains(row1.day_of_week)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r10_resultado_anterior_valido\",\"expression\":\"row1.poutcome != null\\r\\n\\t\\t\\t\\t&& java.util.Arrays.asList(\\r\\n\\t\\t\\t\\t    \\\"success\\\", \\\"failure\\\", \\\"nonexistent\\\"\\r\\n\\t\\t\\t\\t).contains(row1.poutcome)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"r11_contacto_valido\",\"expression\":\"row1.contact != null\\r\\n\\t\\t\\t\\t&& java.util.Arrays.asList(\\r\\n\\t\\t\\t\\t    \\\"cellular\\\", \\\"telephone\\\"\\r\\n\\t\\t\\t\\t).contains(row1.contact)\",\"type\":\"id_Boolean\",\"nullable\":false},{\"name\":\"n_ok\",\"expression\":\"(Var.r01_completitud?1:0) + (Var.r03_edad_valida?1:0) + (Var.r04_duracion_valida?1:0) + (Var.r05_campania_valida?1:0) + (Var.r06_pdays_valido?1:0) + (Var.r07_objetivo_valido?1:0) + (Var.r08_mes_valido?1:0) + (Var.r09_dia_semana_valido?1:0) + (Var.r10_resultado_anterior_valido?1:0) + (Var.r11_contacto_valido?1:0)\",\"type\":\"id_Integer\",\"nullable\":false}]}]}"));
                
                
                
                } catch (java.lang.Exception e_tMap_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tMap_2", "tMap",
          new ParameterUtil_tMap_2().getParameter(row1));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tMap_2", "tMap_DQ", "tMap");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			




// ###############################
// # Lookup's keys initialization
		int count_row1_tMap_2 = 0;
		
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_2__Struct  {
	boolean r01_completitud;
	boolean r03_edad_valida;
	boolean r04_duracion_valida;
	boolean r05_campania_valida;
	boolean r06_pdays_valido;
	boolean r07_objetivo_valido;
	boolean r08_mes_valido;
	boolean r09_dia_semana_valido;
	boolean r10_resultado_anterior_valido;
	boolean r11_contacto_valido;
	int n_ok;
}
Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_out_OK_tMap_2 = 0;
				
out_OKStruct out_OK_tmp = new out_OKStruct();
				int count_out_NOTOK_tMap_2 = 0;
				
out_NOTOKStruct out_NOTOK_tmp = new out_NOTOKStruct();
				int count_out_DQ_tMap_2 = 0;
				
out_DQStruct out_DQ_tmp = new out_DQStruct();
// ###############################

        
        



        









 



		

/**
 * [tMap_2 begin ] stop
 */




	
	/**
	 * [tUniqRow_2 begin ] start
	 */

	

	
		
		sh("tUniqRow_2");
		
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		
			runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,0,0,"row3");
			
		int tos_count_tUniqRow_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tUniqRow_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tUniqRow_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tUniqRow_2 = new StringBuilder();
                    log4jParamters_tUniqRow_2.append("Parameters:");
                            log4jParamters_tUniqRow_2.append("UNIQUE_KEY" + " = " + "[{CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("age")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("job")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("marital")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("education")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("has_default")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("housing")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("loan")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("contact")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("month")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("day_of_week")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("duration")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("campaign")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("pdays")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("previous")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("poutcome")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("emp_var_rate")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("cons_price_idx")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("cons_conf_idx")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("euribor3m")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("nr_employed")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("y")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("load_ts")+"}, {CASE_SENSITIVE="+("false")+", KEY_ATTRIBUTE="+("true")+", SCHEMA_COLUMN="+("load_source")+"}]");
                        log4jParamters_tUniqRow_2.append(" | ");
                            log4jParamters_tUniqRow_2.append("ONLY_ONCE_EACH_DUPLICATED_KEY" + " = " + "false");
                        log4jParamters_tUniqRow_2.append(" | ");
                            log4jParamters_tUniqRow_2.append("IS_VIRTUAL_COMPONENT" + " = " + "false");
                        log4jParamters_tUniqRow_2.append(" | ");
                            log4jParamters_tUniqRow_2.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "false");
                        log4jParamters_tUniqRow_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tUniqRow_2 - "  + (log4jParamters_tUniqRow_2) );
                    } 
                } 
            new BytesLimit65535_tUniqRow_2().limitLog4jByte();
            }
                boolean init_tUniqRow_2_0 = true;
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tUniqRow_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter(final row3Struct row3) throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("UNIQUE_KEY", String.valueOf(new StringBuilder().append("[{CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("age").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("job").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("marital").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("education").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("has_default").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("housing").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("loan").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("contact").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("month").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("day_of_week").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("duration").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("campaign").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("pdays").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("previous").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("poutcome").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("emp_var_rate").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("cons_price_idx").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("cons_conf_idx").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("euribor3m").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("nr_employed").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("y").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("load_ts").append("}, {CASE_SENSITIVE=").append("false").append(", KEY_ATTRIBUTE=").append("true").append(", SCHEMA_COLUMN=").append("load_source").append("}]").toString()));
                            component_parameters.put("ONLY_ONCE_EACH_DUPLICATED_KEY", String.valueOf("false"));
                            component_parameters.put("IS_VIRTUAL_COMPONENT", String.valueOf("false"));
                            component_parameters.put("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL", String.valueOf("false"));
                
                
                
                } catch (java.lang.Exception e_tUniqRow_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tUniqRow_2", "tUniqRow",
          new ParameterUtil_tUniqRow_2().getParameter(row3));
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tUniqRow_2", "tUniqueRow_columnas", "tUniqRow");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

	
		class KeyStruct_tUniqRow_2 {
	
			private static final int DEFAULT_HASHCODE = 1;
		    private static final int PRIME = 31;
		    private int hashCode = DEFAULT_HASHCODE;
		    public boolean hashCodeDirty = true;
	
	        
					Integer age;
					String job;
					String marital;
					String education;
					String has_default;
					String housing;
					String loan;
					String contact;
					String month;
					String day_of_week;
					Integer duration;
					Integer campaign;
					Integer pdays;
					Integer previous;
					String poutcome;
					Float emp_var_rate;
					Float cons_price_idx;
					Double cons_conf_idx;
					Float euribor3m;
					Double nr_employed;
					String y;
					java.util.Date load_ts;
					String load_source;        
	        
		    @Override
			public int hashCode() {
				if (this.hashCodeDirty) {
					final int prime = PRIME;
					int result = DEFAULT_HASHCODE;
			
								result = prime * result + ((this.age == null) ? 0 : this.age.hashCode());
								
								result = prime * result + ((this.job == null) ? 0 : this.job.hashCode());
								
								result = prime * result + ((this.marital == null) ? 0 : this.marital.hashCode());
								
								result = prime * result + ((this.education == null) ? 0 : this.education.hashCode());
								
								result = prime * result + ((this.has_default == null) ? 0 : this.has_default.hashCode());
								
								result = prime * result + ((this.housing == null) ? 0 : this.housing.hashCode());
								
								result = prime * result + ((this.loan == null) ? 0 : this.loan.hashCode());
								
								result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
								
								result = prime * result + ((this.month == null) ? 0 : this.month.hashCode());
								
								result = prime * result + ((this.day_of_week == null) ? 0 : this.day_of_week.hashCode());
								
								result = prime * result + ((this.duration == null) ? 0 : this.duration.hashCode());
								
								result = prime * result + ((this.campaign == null) ? 0 : this.campaign.hashCode());
								
								result = prime * result + ((this.pdays == null) ? 0 : this.pdays.hashCode());
								
								result = prime * result + ((this.previous == null) ? 0 : this.previous.hashCode());
								
								result = prime * result + ((this.poutcome == null) ? 0 : this.poutcome.hashCode());
								
								result = prime * result + ((this.emp_var_rate == null) ? 0 : this.emp_var_rate.hashCode());
								
								result = prime * result + ((this.cons_price_idx == null) ? 0 : this.cons_price_idx.hashCode());
								
								result = prime * result + ((this.cons_conf_idx == null) ? 0 : this.cons_conf_idx.hashCode());
								
								result = prime * result + ((this.euribor3m == null) ? 0 : this.euribor3m.hashCode());
								
								result = prime * result + ((this.nr_employed == null) ? 0 : this.nr_employed.hashCode());
								
								result = prime * result + ((this.y == null) ? 0 : this.y.hashCode());
								
								result = prime * result + ((this.load_ts == null) ? 0 : this.load_ts.hashCode());
								
								result = prime * result + ((this.load_source == null) ? 0 : this.load_source.hashCode());
								
		    		this.hashCode = result;
		    		this.hashCodeDirty = false;		
				}
				return this.hashCode;
			}
			
			@Override
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj == null) return false;
				if (getClass() != obj.getClass()) return false;
				final KeyStruct_tUniqRow_2 other = (KeyStruct_tUniqRow_2) obj;
				
									if (this.age == null) {
										if (other.age != null) 
											return false;
								
									} else if (!this.age.equals(other.age))
								 
										return false;
								
									if (this.job == null) {
										if (other.job != null) 
											return false;
								
									} else if (!this.job.equals(other.job))
								 
										return false;
								
									if (this.marital == null) {
										if (other.marital != null) 
											return false;
								
									} else if (!this.marital.equals(other.marital))
								 
										return false;
								
									if (this.education == null) {
										if (other.education != null) 
											return false;
								
									} else if (!this.education.equals(other.education))
								 
										return false;
								
									if (this.has_default == null) {
										if (other.has_default != null) 
											return false;
								
									} else if (!this.has_default.equals(other.has_default))
								 
										return false;
								
									if (this.housing == null) {
										if (other.housing != null) 
											return false;
								
									} else if (!this.housing.equals(other.housing))
								 
										return false;
								
									if (this.loan == null) {
										if (other.loan != null) 
											return false;
								
									} else if (!this.loan.equals(other.loan))
								 
										return false;
								
									if (this.contact == null) {
										if (other.contact != null) 
											return false;
								
									} else if (!this.contact.equals(other.contact))
								 
										return false;
								
									if (this.month == null) {
										if (other.month != null) 
											return false;
								
									} else if (!this.month.equals(other.month))
								 
										return false;
								
									if (this.day_of_week == null) {
										if (other.day_of_week != null) 
											return false;
								
									} else if (!this.day_of_week.equals(other.day_of_week))
								 
										return false;
								
									if (this.duration == null) {
										if (other.duration != null) 
											return false;
								
									} else if (!this.duration.equals(other.duration))
								 
										return false;
								
									if (this.campaign == null) {
										if (other.campaign != null) 
											return false;
								
									} else if (!this.campaign.equals(other.campaign))
								 
										return false;
								
									if (this.pdays == null) {
										if (other.pdays != null) 
											return false;
								
									} else if (!this.pdays.equals(other.pdays))
								 
										return false;
								
									if (this.previous == null) {
										if (other.previous != null) 
											return false;
								
									} else if (!this.previous.equals(other.previous))
								 
										return false;
								
									if (this.poutcome == null) {
										if (other.poutcome != null) 
											return false;
								
									} else if (!this.poutcome.equals(other.poutcome))
								 
										return false;
								
									if (this.emp_var_rate == null) {
										if (other.emp_var_rate != null) 
											return false;
								
									} else if (!this.emp_var_rate.equals(other.emp_var_rate))
								 
										return false;
								
									if (this.cons_price_idx == null) {
										if (other.cons_price_idx != null) 
											return false;
								
									} else if (!this.cons_price_idx.equals(other.cons_price_idx))
								 
										return false;
								
									if (this.cons_conf_idx == null) {
										if (other.cons_conf_idx != null) 
											return false;
								
									} else if (!this.cons_conf_idx.equals(other.cons_conf_idx))
								 
										return false;
								
									if (this.euribor3m == null) {
										if (other.euribor3m != null) 
											return false;
								
									} else if (!this.euribor3m.equals(other.euribor3m))
								 
										return false;
								
									if (this.nr_employed == null) {
										if (other.nr_employed != null) 
											return false;
								
									} else if (!this.nr_employed.equals(other.nr_employed))
								 
										return false;
								
									if (this.y == null) {
										if (other.y != null) 
											return false;
								
									} else if (!this.y.equals(other.y))
								 
										return false;
								
									if (this.load_ts == null) {
										if (other.load_ts != null) 
											return false;
								
									} else if (!this.load_ts.equals(other.load_ts))
								 
										return false;
								
									if (this.load_source == null) {
										if (other.load_source != null) 
											return false;
								
									} else if (!this.load_source.equals(other.load_source))
								 
										return false;
								
				
				return true;
			}
	  
	        
		}

	
int nb_uniques_tUniqRow_2 = 0;
int nb_duplicates_tUniqRow_2 = 0;
	log.debug("tUniqRow_2 - Start to process the data from datasource.");
KeyStruct_tUniqRow_2 finder_tUniqRow_2 = new KeyStruct_tUniqRow_2();
java.util.Set<KeyStruct_tUniqRow_2> keystUniqRow_2 = new java.util.HashSet<KeyStruct_tUniqRow_2>(); 

 



		

/**
 * [tUniqRow_2 begin ] stop
 */




	
	/**
	 * [tDBInput_2 begin ] start
	 */

	

	
		
		sh("tDBInput_2");
		
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		
		int tos_count_tDBInput_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBInput_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBInput_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBInput_2 = new StringBuilder();
                    log4jParamters_tDBInput_2.append("Parameters:");
                            log4jParamters_tDBInput_2.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("TABLE" + " = " + "\"\"");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("QUERYSTORE" + " = " + "\"\"");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("QUERY" + " = " + "\"select * from raw_bank_g3\"");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true\"");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("ENABLE_STREAM" + " = " + "false");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("TRIM_ALL_COLUMN" + " = " + "false");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("TRIM_COLUMN" + " = " + "[{TRIM="+("false")+", SCHEMA_COLUMN="+("age")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("job")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("marital")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("education")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("has_default")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("housing")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("loan")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("contact")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("month")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("day_of_week")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("duration")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("campaign")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("pdays")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("previous")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("poutcome")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("emp_var_rate")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("cons_price_idx")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("cons_conf_idx")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("euribor3m")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("nr_employed")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("y")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("load_ts")+"}, {TRIM="+("false")+", SCHEMA_COLUMN="+("load_source")+"}]");
                        log4jParamters_tDBInput_2.append(" | ");
                            log4jParamters_tDBInput_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
                        log4jParamters_tDBInput_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBInput_2 - "  + (log4jParamters_tDBInput_2) );
                    } 
                } 
            new BytesLimit65535_tDBInput_2().limitLog4jByte();
            }
                boolean init_tDBInput_2_0 = true;
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBInput_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("TABLE", String.valueOf(""));
                            component_parameters.put("QUERYSTORE", String.valueOf(""));
                            component_parameters.put("QUERY", String.valueOf("select * from raw_bank_g3"));
                            component_parameters.put("SPECIFY_DATASOURCE_ALIAS", String.valueOf("false"));
                            component_parameters.put("PROPERTIES", String.valueOf("noDatetimeStringSync=true"));
                            component_parameters.put("ENABLE_STREAM", String.valueOf("false"));
                            component_parameters.put("TRIM_ALL_COLUMN", String.valueOf("false"));
                            component_parameters.put("TRIM_COLUMN", String.valueOf(new StringBuilder().append("[{TRIM=").append("false").append(", SCHEMA_COLUMN=").append("age").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("job").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("marital").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("education").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("has_default").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("housing").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("loan").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("contact").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("month").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("day_of_week").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("duration").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("campaign").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("pdays").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("previous").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("poutcome").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("emp_var_rate").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("cons_price_idx").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("cons_conf_idx").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("euribor3m").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("nr_employed").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("y").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("load_ts").append("}, {TRIM=").append("false").append(", SCHEMA_COLUMN=").append("load_source").append("}]").toString()));
                            component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlInput"));
                
                
                
                } catch (java.lang.Exception e_tDBInput_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBInput_2", "tMysqlInput",
          new ParameterUtil_tDBInput_2().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBInput_2", "tDBI_raw_bank_g3", "tMysqlInput");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			
	
	
		    java.util.Calendar calendar_tDBInput_2 = java.util.Calendar.getInstance();
		    calendar_tDBInput_2.set(0, 0, 0, 0, 0, 0);
		    java.util.Date year0_tDBInput_2 = calendar_tDBInput_2.getTime();
		    int nb_line_tDBInput_2 = 0;
		    java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "com.mysql.cj.jdbc.Driver";
			    java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = context.DB_USER;
				
				
	final String decryptedPassword_tDBInput_2 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");
				
				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;
				
        String properties_tDBInput_2 = "noDatetimeStringSync=true";
        if (properties_tDBInput_2 == null || properties_tDBInput_2.trim().length() == 0) {
            properties_tDBInput_2 = "";
        }
        String url_tDBInput_2 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBInput_2;
				
	    		log.debug("tDBInput_2 - Driver ClassName: "+driverClass_tDBInput_2+".");
			
	    		log.debug("tDBInput_2 - Connection attempt to '" + url_tDBInput_2.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' with the username '" + dbUser_tDBInput_2 + "'.");
			
				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2,dbUser_tDBInput_2,dbPwd_tDBInput_2);
	    		log.debug("tDBInput_2 - Connection to '" + url_tDBInput_2.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' has succeeded.");
			
		        
		    
			java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

		    String dbquery_tDBInput_2 = "select * from raw_bank_g3";
		    
	    		log.debug("tDBInput_2 - Executing the query: '" + dbquery_tDBInput_2 + "'.");
			

		    globalMap.put("tDBInput_2_QUERY",dbquery_tDBInput_2);

		    java.sql.ResultSet rs_tDBInput_2 = null;

		    try {
		    	rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
		    	java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
		    	int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

		    String tmpContent_tDBInput_2 = null;
		    
		    
		    	log.debug("tDBInput_2 - Retrieving records from the database.");
		    
		    while (rs_tDBInput_2.next()) {
		        nb_line_tDBInput_2++;
		        
							if(colQtyInRs_tDBInput_2 < 1) {
								row3.age = null;
							} else {
		                          
            row3.age = rs_tDBInput_2.getInt(1);
            if(rs_tDBInput_2.wasNull()){
                    row3.age = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 2) {
								row3.job = null;
							} else {
	                         		
        	row3.job = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 3) {
								row3.marital = null;
							} else {
	                         		
        	row3.marital = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 4) {
								row3.education = null;
							} else {
	                         		
        	row3.education = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 5) {
								row3.has_default = null;
							} else {
	                         		
        	row3.has_default = routines.system.JDBCUtil.getString(rs_tDBInput_2, 5, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 6) {
								row3.housing = null;
							} else {
	                         		
        	row3.housing = routines.system.JDBCUtil.getString(rs_tDBInput_2, 6, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 7) {
								row3.loan = null;
							} else {
	                         		
        	row3.loan = routines.system.JDBCUtil.getString(rs_tDBInput_2, 7, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 8) {
								row3.contact = null;
							} else {
	                         		
        	row3.contact = routines.system.JDBCUtil.getString(rs_tDBInput_2, 8, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 9) {
								row3.month = null;
							} else {
	                         		
        	row3.month = routines.system.JDBCUtil.getString(rs_tDBInput_2, 9, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 10) {
								row3.day_of_week = null;
							} else {
	                         		
        	row3.day_of_week = routines.system.JDBCUtil.getString(rs_tDBInput_2, 10, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 11) {
								row3.duration = null;
							} else {
		                          
            row3.duration = rs_tDBInput_2.getInt(11);
            if(rs_tDBInput_2.wasNull()){
                    row3.duration = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 12) {
								row3.campaign = null;
							} else {
		                          
            row3.campaign = rs_tDBInput_2.getInt(12);
            if(rs_tDBInput_2.wasNull()){
                    row3.campaign = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 13) {
								row3.pdays = null;
							} else {
		                          
            row3.pdays = rs_tDBInput_2.getInt(13);
            if(rs_tDBInput_2.wasNull()){
                    row3.pdays = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 14) {
								row3.previous = null;
							} else {
		                          
            row3.previous = rs_tDBInput_2.getInt(14);
            if(rs_tDBInput_2.wasNull()){
                    row3.previous = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 15) {
								row3.poutcome = null;
							} else {
	                         		
        	row3.poutcome = routines.system.JDBCUtil.getString(rs_tDBInput_2, 15, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 16) {
								row3.emp_var_rate = null;
							} else {
		                          
            row3.emp_var_rate = rs_tDBInput_2.getFloat(16);
            if(rs_tDBInput_2.wasNull()){
                    row3.emp_var_rate = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 17) {
								row3.cons_price_idx = null;
							} else {
		                          
            row3.cons_price_idx = rs_tDBInput_2.getFloat(17);
            if(rs_tDBInput_2.wasNull()){
                    row3.cons_price_idx = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 18) {
								row3.cons_conf_idx = null;
							} else {
	                         		
            row3.cons_conf_idx = rs_tDBInput_2.getDouble(18);
            if(rs_tDBInput_2.wasNull()){
                    row3.cons_conf_idx = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 19) {
								row3.euribor3m = null;
							} else {
		                          
            row3.euribor3m = rs_tDBInput_2.getFloat(19);
            if(rs_tDBInput_2.wasNull()){
                    row3.euribor3m = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 20) {
								row3.nr_employed = null;
							} else {
	                         		
            row3.nr_employed = rs_tDBInput_2.getDouble(20);
            if(rs_tDBInput_2.wasNull()){
                    row3.nr_employed = null;
            }
		                    }
							if(colQtyInRs_tDBInput_2 < 21) {
								row3.y = null;
							} else {
	                         		
        	row3.y = routines.system.JDBCUtil.getString(rs_tDBInput_2, 21, false);
		                    }
							if(colQtyInRs_tDBInput_2 < 22) {
								row3.load_ts = null;
							} else {
										
				if(rs_tDBInput_2.getString(22) != null) {
					String dateString_tDBInput_2 = rs_tDBInput_2.getString(22);
					if (!("0000-00-00").equals(dateString_tDBInput_2) && !("0000-00-00 00:00:00").equals(dateString_tDBInput_2)) {
						row3.load_ts = rs_tDBInput_2.getTimestamp(22);
					} else {
						row3.load_ts = (java.util.Date) year0_tDBInput_2.clone();
					}
				} else {
					row3.load_ts =  null;
				}
		                    }
							if(colQtyInRs_tDBInput_2 < 23) {
								row3.load_source = null;
							} else {
	                         		
        	row3.load_source = routines.system.JDBCUtil.getString(rs_tDBInput_2, 23, false);
		                    }
					
						log.debug("tDBInput_2 - Retrieving the record " + nb_line_tDBInput_2 + ".");
					

 



		

/**
 * [tDBInput_2 begin ] stop
 */

	
	/**
	 * [tDBInput_2 main ] start
	 */

	

	
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		

 
      
      
      // QTUP-3575
      if(enableLineage && init_tDBInput_2_0) {
      class SchemaUtil_row3 {
      
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
          
          public java.util.List<java.util.Map<String, String>> getSchema(final row3Struct row3) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(row3 == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","INT","true","","10","0");
              a(s,"job","job","false","id_String","VARCHAR","true","","13","0");
              a(s,"marital","marital","false","id_String","VARCHAR","true","","8","0");
              a(s,"education","education","false","id_String","VARCHAR","true","","19","0");
              a(s,"has_default","has_default","false","id_String","VARCHAR","true","","7","0");
              a(s,"housing","housing","false","id_String","VARCHAR","true","","7","0");
              a(s,"loan","loan","false","id_String","VARCHAR","true","","7","0");
              a(s,"contact","contact","false","id_String","VARCHAR","true","","20","0");
              a(s,"month","month","false","id_String","VARCHAR","true","","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","VARCHAR","true","","3","0");
              a(s,"duration","duration","false","id_Integer","INT","true","","10","0");
              a(s,"campaign","campaign","false","id_Integer","INT","true","","10","0");
              a(s,"pdays","pdays","false","id_Integer","INT","true","","10","0");
              a(s,"previous","previous","false","id_Integer","INT","true","","10","0");
              a(s,"poutcome","poutcome","false","id_String","VARCHAR","true","","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","FLOAT","true","","50","2");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","FLOAT","true","","50","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","DOUBLE","true","","50","4");
              a(s,"euribor3m","euribor3m","false","id_Float","FLOAT","true","","50","4");
              a(s,"nr_employed","nr_employed","false","id_Double","DOUBLE","true","","50","4");
              a(s,"y","y","false","id_String","VARCHAR","true","","3","0");
              a(s,"load_ts","load_ts","false","id_Date","DATETIME","true","yyyy-MM-dd HH:mm:ss","26","0");
              a(s,"load_source","load_source","false","id_String","VARCHAR","true","","60","0");
              return s;
          }
          
      }
      
      if(row3 != null) {
          talendJobLog.addConnectionSchemaMessage("tDBInput_2","tMysqlInput",
            "tUniqRow_2","tUniqRow", "row3" + iterateId, new SchemaUtil_row3().getSchema(row3));
          talendJobLogProcess(globalMap);
          init_tDBInput_2_0 = false;
      }
      
      }
  // QTUP-3575


	tos_count_tDBInput_2++;

		

/**
 * [tDBInput_2 main ] stop
 */

	
	/**
	 * [tDBInput_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		

 



		

/**
 * [tDBInput_2 process_data_begin ] stop
 */


	
	/**
	 * [tUniqRow_2 main ] start
	 */

	

	
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"row3","tDBInput_2","tDBI_raw_bank_g3","tMysqlInput","tUniqRow_2","tUniqueRow_columnas","tUniqRow"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("row3 - " + (row3==null? "": row3.toLogString()));
    			}
    		
row1 = null;			
finder_tUniqRow_2.age = row3.age;
if(row3.job == null){
	finder_tUniqRow_2.job = null;
}else{
	finder_tUniqRow_2.job = row3.job.toLowerCase();
}
if(row3.marital == null){
	finder_tUniqRow_2.marital = null;
}else{
	finder_tUniqRow_2.marital = row3.marital.toLowerCase();
}
if(row3.education == null){
	finder_tUniqRow_2.education = null;
}else{
	finder_tUniqRow_2.education = row3.education.toLowerCase();
}
if(row3.has_default == null){
	finder_tUniqRow_2.has_default = null;
}else{
	finder_tUniqRow_2.has_default = row3.has_default.toLowerCase();
}
if(row3.housing == null){
	finder_tUniqRow_2.housing = null;
}else{
	finder_tUniqRow_2.housing = row3.housing.toLowerCase();
}
if(row3.loan == null){
	finder_tUniqRow_2.loan = null;
}else{
	finder_tUniqRow_2.loan = row3.loan.toLowerCase();
}
if(row3.contact == null){
	finder_tUniqRow_2.contact = null;
}else{
	finder_tUniqRow_2.contact = row3.contact.toLowerCase();
}
if(row3.month == null){
	finder_tUniqRow_2.month = null;
}else{
	finder_tUniqRow_2.month = row3.month.toLowerCase();
}
if(row3.day_of_week == null){
	finder_tUniqRow_2.day_of_week = null;
}else{
	finder_tUniqRow_2.day_of_week = row3.day_of_week.toLowerCase();
}
finder_tUniqRow_2.duration = row3.duration;
finder_tUniqRow_2.campaign = row3.campaign;
finder_tUniqRow_2.pdays = row3.pdays;
finder_tUniqRow_2.previous = row3.previous;
if(row3.poutcome == null){
	finder_tUniqRow_2.poutcome = null;
}else{
	finder_tUniqRow_2.poutcome = row3.poutcome.toLowerCase();
}
finder_tUniqRow_2.emp_var_rate = row3.emp_var_rate;
finder_tUniqRow_2.cons_price_idx = row3.cons_price_idx;
finder_tUniqRow_2.cons_conf_idx = row3.cons_conf_idx;
finder_tUniqRow_2.euribor3m = row3.euribor3m;
finder_tUniqRow_2.nr_employed = row3.nr_employed;
if(row3.y == null){
	finder_tUniqRow_2.y = null;
}else{
	finder_tUniqRow_2.y = row3.y.toLowerCase();
}
finder_tUniqRow_2.load_ts = row3.load_ts;
if(row3.load_source == null){
	finder_tUniqRow_2.load_source = null;
}else{
	finder_tUniqRow_2.load_source = row3.load_source.toLowerCase();
}	
finder_tUniqRow_2.hashCodeDirty = true;
if (!keystUniqRow_2.contains(finder_tUniqRow_2)) {
		KeyStruct_tUniqRow_2 new_tUniqRow_2 = new KeyStruct_tUniqRow_2();

		
new_tUniqRow_2.age = row3.age;
if(row3.job == null){
	new_tUniqRow_2.job = null;
}else{
	new_tUniqRow_2.job = row3.job.toLowerCase();
}
if(row3.marital == null){
	new_tUniqRow_2.marital = null;
}else{
	new_tUniqRow_2.marital = row3.marital.toLowerCase();
}
if(row3.education == null){
	new_tUniqRow_2.education = null;
}else{
	new_tUniqRow_2.education = row3.education.toLowerCase();
}
if(row3.has_default == null){
	new_tUniqRow_2.has_default = null;
}else{
	new_tUniqRow_2.has_default = row3.has_default.toLowerCase();
}
if(row3.housing == null){
	new_tUniqRow_2.housing = null;
}else{
	new_tUniqRow_2.housing = row3.housing.toLowerCase();
}
if(row3.loan == null){
	new_tUniqRow_2.loan = null;
}else{
	new_tUniqRow_2.loan = row3.loan.toLowerCase();
}
if(row3.contact == null){
	new_tUniqRow_2.contact = null;
}else{
	new_tUniqRow_2.contact = row3.contact.toLowerCase();
}
if(row3.month == null){
	new_tUniqRow_2.month = null;
}else{
	new_tUniqRow_2.month = row3.month.toLowerCase();
}
if(row3.day_of_week == null){
	new_tUniqRow_2.day_of_week = null;
}else{
	new_tUniqRow_2.day_of_week = row3.day_of_week.toLowerCase();
}
new_tUniqRow_2.duration = row3.duration;
new_tUniqRow_2.campaign = row3.campaign;
new_tUniqRow_2.pdays = row3.pdays;
new_tUniqRow_2.previous = row3.previous;
if(row3.poutcome == null){
	new_tUniqRow_2.poutcome = null;
}else{
	new_tUniqRow_2.poutcome = row3.poutcome.toLowerCase();
}
new_tUniqRow_2.emp_var_rate = row3.emp_var_rate;
new_tUniqRow_2.cons_price_idx = row3.cons_price_idx;
new_tUniqRow_2.cons_conf_idx = row3.cons_conf_idx;
new_tUniqRow_2.euribor3m = row3.euribor3m;
new_tUniqRow_2.nr_employed = row3.nr_employed;
if(row3.y == null){
	new_tUniqRow_2.y = null;
}else{
	new_tUniqRow_2.y = row3.y.toLowerCase();
}
new_tUniqRow_2.load_ts = row3.load_ts;
if(row3.load_source == null){
	new_tUniqRow_2.load_source = null;
}else{
	new_tUniqRow_2.load_source = row3.load_source.toLowerCase();
}
		
		keystUniqRow_2.add(new_tUniqRow_2);if(row1 == null){ 
	
		log.trace("tUniqRow_2 - Writing the unique record " + (nb_uniques_tUniqRow_2+1) + " into row1.");
	
	row1 = new row1Struct();
}row1.age = row3.age;			row1.job = row3.job;			row1.marital = row3.marital;			row1.education = row3.education;			row1.has_default = row3.has_default;			row1.housing = row3.housing;			row1.loan = row3.loan;			row1.contact = row3.contact;			row1.month = row3.month;			row1.day_of_week = row3.day_of_week;			row1.duration = row3.duration;			row1.campaign = row3.campaign;			row1.pdays = row3.pdays;			row1.previous = row3.previous;			row1.poutcome = row3.poutcome;			row1.emp_var_rate = row3.emp_var_rate;			row1.cons_price_idx = row3.cons_price_idx;			row1.cons_conf_idx = row3.cons_conf_idx;			row1.euribor3m = row3.euribor3m;			row1.nr_employed = row3.nr_employed;			row1.y = row3.y;			row1.load_ts = row3.load_ts;			row1.load_source = row3.load_source;					
		nb_uniques_tUniqRow_2++;
	} else {
	  nb_duplicates_tUniqRow_2++;
	}

 
      
      
      // QTUP-3575
      if(enableLineage && init_tUniqRow_2_0) {
      class SchemaUtil_row1 {
      
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
          
          public java.util.List<java.util.Map<String, String>> getSchema(final row1Struct row1) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(row1 == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","INT","true","","10","0");
              a(s,"job","job","false","id_String","VARCHAR","true","","13","0");
              a(s,"marital","marital","false","id_String","VARCHAR","true","","8","0");
              a(s,"education","education","false","id_String","VARCHAR","true","","19","0");
              a(s,"has_default","has_default","false","id_String","VARCHAR","true","","7","0");
              a(s,"housing","housing","false","id_String","VARCHAR","true","","7","0");
              a(s,"loan","loan","false","id_String","VARCHAR","true","","7","0");
              a(s,"contact","contact","false","id_String","VARCHAR","true","","20","0");
              a(s,"month","month","false","id_String","VARCHAR","true","","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","VARCHAR","true","","3","0");
              a(s,"duration","duration","false","id_Integer","INT","true","","10","0");
              a(s,"campaign","campaign","false","id_Integer","INT","true","","10","0");
              a(s,"pdays","pdays","false","id_Integer","INT","true","","10","0");
              a(s,"previous","previous","false","id_Integer","INT","true","","10","0");
              a(s,"poutcome","poutcome","false","id_String","VARCHAR","true","","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","FLOAT","true","","50","2");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","FLOAT","true","","50","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","DOUBLE","true","","50","4");
              a(s,"euribor3m","euribor3m","false","id_Float","FLOAT","true","","50","4");
              a(s,"nr_employed","nr_employed","false","id_Double","DOUBLE","true","","50","4");
              a(s,"y","y","false","id_String","VARCHAR","true","","3","0");
              a(s,"load_ts","load_ts","false","id_Date","DATETIME","true","yyyy-MM-dd HH:mm:ss","26","0");
              a(s,"load_source","load_source","false","id_String","VARCHAR","true","","60","0");
              return s;
          }
          
      }
      
      if(row1 != null) {
          talendJobLog.addConnectionSchemaMessage("tUniqRow_2","tUniqRow",
            "tMap_2","tMap", "row1" + iterateId, new SchemaUtil_row1().getSchema(row1));
          talendJobLogProcess(globalMap);
          init_tUniqRow_2_0 = false;
      }
      
      }
  // QTUP-3575


	tos_count_tUniqRow_2++;

		

/**
 * [tUniqRow_2 main ] stop
 */

	
	/**
	 * [tUniqRow_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		

 



		

/**
 * [tUniqRow_2 process_data_begin ] stop
 */

// Start of branch "row1"
if(row1 != null) { 



	
	/**
	 * [tMap_2 main ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	
			cLabel="tMap_DQ";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"row1","tUniqRow_2","tUniqueRow_columnas","tUniqRow","tMap_2","tMap_DQ","tMap"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("row1 - " + (row1==null? "": row1.toLogString()));
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
        
Var__tMap_2__Struct Var = Var__tMap_2;
Var.r01_completitud = row1.age != null
				&& row1.job != null && !row1.job.trim().isEmpty()
				&& row1.marital != null && !row1.marital.trim().isEmpty()
				&& row1.education != null && !row1.education.trim().isEmpty()
				&& row1.has_default != null && !row1.has_default.trim().isEmpty()
				&& row1.housing != null && !row1.housing.trim().isEmpty()
				&& row1.loan != null && !row1.loan.trim().isEmpty()
				&& row1.contact != null && !row1.contact.trim().isEmpty()
				&& row1.month != null && !row1.month.trim().isEmpty()
				&& row1.day_of_week != null && !row1.day_of_week.trim().isEmpty()
				&& row1.duration != null
				&& row1.campaign != null
				&& row1.pdays != null
				&& row1.previous != null
				&& row1.poutcome != null && !row1.poutcome.trim().isEmpty()
				&& row1.emp_var_rate != null
				&& row1.cons_price_idx != null
				&& row1.cons_conf_idx != null
				&& row1.euribor3m != null
				&& row1.nr_employed != null
				&& row1.y != null && !row1.y.trim().isEmpty() ;
Var.r03_edad_valida = row1.age != null
				&& row1.age >= 18
				&& row1.age <= 100 ;
Var.r04_duracion_valida = row1.duration != null
				&& row1.duration >= 0 ;
Var.r05_campania_valida = row1.campaign != null
				&& row1.campaign >= 1
				&& row1.previous != null
				&& row1.previous >= 0 ;
Var.r06_pdays_valido = row1.pdays != null
				&& (row1.pdays >= 0 || row1.pdays == 999) ;
Var.r07_objetivo_valido = row1.y != null
				&& java.util.Arrays.asList("yes", "no").contains(row1.y) ;
Var.r08_mes_valido = row1.month != null
				&& java.util.Arrays.asList(
				    "jan", "feb", "mar", "apr", "may", "jun",
				    "jul", "aug", "sep", "oct", "nov", "dec"
				).contains(row1.month) ;
Var.r09_dia_semana_valido = row1.day_of_week != null
				&& java.util.Arrays.asList(
				    "mon", "tue", "wed", "thu", "fri"
				).contains(row1.day_of_week) ;
Var.r10_resultado_anterior_valido = row1.poutcome != null
				&& java.util.Arrays.asList(
				    "success", "failure", "nonexistent"
				).contains(row1.poutcome) ;
Var.r11_contacto_valido = row1.contact != null
				&& java.util.Arrays.asList(
				    "cellular", "telephone"
				).contains(row1.contact) ;
Var.n_ok = (Var.r01_completitud?1:0) + (Var.r03_edad_valida?1:0) + (Var.r04_duracion_valida?1:0) + (Var.r05_campania_valida?1:0) + (Var.r06_pdays_valido?1:0) + (Var.r07_objetivo_valido?1:0) + (Var.r08_mes_valido?1:0) + (Var.r09_dia_semana_valido?1:0) + (Var.r10_resultado_anterior_valido?1:0) + (Var.r11_contacto_valido?1:0) ;// ###############################
        // ###############################
        // # Output tables

out_OK = null;
out_NOTOK = null;
out_DQ = null;


// # Output table : 'out_OK'
// # Filter conditions 
if( 

Var.r01_completitud
&& Var.r03_edad_valida
&& Var.r04_duracion_valida
&& Var.r05_campania_valida
&& Var.r06_pdays_valido
&& Var.r07_objetivo_valido
&& Var.r08_mes_valido
&& Var.r09_dia_semana_valido
&& Var.r10_resultado_anterior_valido
&& Var.r11_contacto_valido

 ) {
count_out_OK_tMap_2++;

out_OK_tmp.age = row1.age ;
out_OK_tmp.job = row1.job ;
out_OK_tmp.marital = row1.marital ;
out_OK_tmp.education = row1.education ;
out_OK_tmp.has_default = row1.has_default ;
out_OK_tmp.housing = row1.housing ;
out_OK_tmp.loan = row1.loan ;
out_OK_tmp.contact = row1.contact ;
out_OK_tmp.month = row1.month ;
out_OK_tmp.day_of_week = row1.day_of_week ;
out_OK_tmp.duration = row1.duration ;
out_OK_tmp.campaign = row1.campaign ;
out_OK_tmp.pdays = row1.pdays ;
out_OK_tmp.previous = row1.previous ;
out_OK_tmp.poutcome = row1.poutcome ;
out_OK_tmp.emp_var_rate = row1.emp_var_rate ;
out_OK_tmp.cons_price_idx = row1.cons_price_idx ;
out_OK_tmp.cons_conf_idx = row1.cons_conf_idx ;
out_OK_tmp.euribor3m = row1.euribor3m ;
out_OK_tmp.nr_employed = row1.nr_employed ;
out_OK_tmp.y = row1.y ;
out_OK_tmp.load_ts = TalendDate.getCurrentDate() ;
out_OK_tmp.load_source = row1.load_source ;
out_OK = out_OK_tmp;
log.debug("tMap_2 - Outputting the record " + count_out_OK_tMap_2 + " of the output table 'out_OK'.");

} // closing filter/reject

// # Output table : 'out_NOTOK'
// # Filter conditions 
if( 

!Var.r01_completitud
				|| !Var.r03_edad_valida
				|| !Var.r04_duracion_valida
				|| !Var.r05_campania_valida
				|| !Var.r06_pdays_valido
				|| !Var.r07_objetivo_valido
				|| !Var.r08_mes_valido
				|| !Var.r09_dia_semana_valido
				|| !Var.r10_resultado_anterior_valido
				|| !Var.r11_contacto_valido

 ) {
count_out_NOTOK_tMap_2++;

out_NOTOK_tmp.age = row1.age ;
out_NOTOK_tmp.job = row1.job ;
out_NOTOK_tmp.marital = row1.marital ;
out_NOTOK_tmp.education = row1.education ;
out_NOTOK_tmp.has_default = row1.has_default ;
out_NOTOK_tmp.housing = row1.housing ;
out_NOTOK_tmp.loan = row1.loan ;
out_NOTOK_tmp.contact = row1.contact ;
out_NOTOK_tmp.month = row1.month ;
out_NOTOK_tmp.day_of_week = row1.day_of_week ;
out_NOTOK_tmp.duration = row1.duration ;
out_NOTOK_tmp.campaign = row1.campaign ;
out_NOTOK_tmp.pdays = row1.pdays ;
out_NOTOK_tmp.previous = row1.previous ;
out_NOTOK_tmp.poutcome = row1.poutcome ;
out_NOTOK_tmp.emp_var_rate = row1.emp_var_rate ;
out_NOTOK_tmp.cons_price_idx = row1.cons_price_idx ;
out_NOTOK_tmp.cons_conf_idx = row1.cons_conf_idx ;
out_NOTOK_tmp.euribor3m = row1.euribor3m ;
out_NOTOK_tmp.nr_employed = row1.nr_employed ;
out_NOTOK_tmp.y = row1.y ;
out_NOTOK_tmp.codigo_regla = !Var.r01_completitud ? "DQ-01" :
					!Var.r03_edad_valida ? "DQ-03" :
					!Var.r04_duracion_valida ? "DQ-04" :
					!Var.r05_campania_valida ? "DQ-05" :
					!Var.r06_pdays_valido ? "DQ-06" :
					!Var.r07_objetivo_valido ? "DQ-07" :
					!Var.r08_mes_valido ? "DQ-08" :
					!Var.r09_dia_semana_valido ? "DQ-09" :
					!Var.r10_resultado_anterior_valido ? "DQ-10" :
					!Var.r11_contacto_valido ? "DQ-11" :
					"DQ-00" ;
out_NOTOK_tmp.motivo_rechazo = !Var.r01_completitud ? "Registro con campos obligatorios incompletos" :
					!Var.r03_edad_valida ? "Edad inferior a 18 o fuera del rango permitido" :
					!Var.r04_duracion_valida ? "Duracion no valida" :
					!Var.r05_campania_valida ? "Numero de contactos de campania no valido" :
					!Var.r06_pdays_valido ? "Valor de pdays no valido" :
					!Var.r07_objetivo_valido ? "Objetivo de suscripcion no valido" :
					!Var.r08_mes_valido ? "Mes no valido" :
					!Var.r09_dia_semana_valido ? "Dia de semana no valido" :
					!Var.r10_resultado_anterior_valido ? "Resultado anterior no valido" :
					!Var.r11_contacto_valido ? "Tipo de contacto no valido" :
					"Incumplimiento de regla DQ" ;
out_NOTOK_tmp.responsable_remediacion = "Data Steward — Grupo 3" ;
out_NOTOK_tmp.estado_remediacion = "EN_REVISION";
out_NOTOK_tmp.load_ts = TalendDate.getCurrentDate() ;
out_NOTOK_tmp.load_source = row1.load_source ;
out_NOTOK = out_NOTOK_tmp;
log.debug("tMap_2 - Outputting the record " + count_out_NOTOK_tMap_2 + " of the output table 'out_NOTOK'.");

} // closing filter/reject

// # Output table : 'out_DQ'
count_out_DQ_tMap_2++;

out_DQ_tmp.run_id = (String)globalMap.get("RUN_ID");
out_DQ_tmp.r01 = Var.r01_completitud ? 1 : 0;
out_DQ_tmp.r03 = Var.r03_edad_valida ? 1 : 0;
out_DQ_tmp.r04 = Var.r04_duracion_valida ? 1 : 0;
out_DQ_tmp.r05 = Var.r05_campania_valida ? 1 : 0;
out_DQ_tmp.r06 = Var.r06_pdays_valido ? 1 : 0;
out_DQ_tmp.r07 = Var.r07_objetivo_valido ? 1 : 0;
out_DQ_tmp.r08 = Var.r08_mes_valido ? 1 : 0;
out_DQ_tmp.r09 = Var.r09_dia_semana_valido ? 1 : 0;
out_DQ_tmp.r10 = Var.r10_resultado_anterior_valido ? 1 : 0;
out_DQ_tmp.r11 = Var.r11_contacto_valido ? 1 : 0;
out_DQ_tmp.rules_passed = Var.n_ok;
out_DQ_tmp.dq_score = Var.n_ok * 100.0 / 10;
out_DQ_tmp.dq_status = Var.n_ok == 10 ? "OK" : "NOTOK";
out_DQ_tmp.load_ts = TalendDate.getCurrentDate();
out_DQ = out_DQ_tmp;
log.debug("tMap_2 - Outputting the record " + count_out_DQ_tMap_2 + " of the output table 'out_DQ'.");

// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_2 = false;










 
      
      
      // QTUP-3575
      if(enableLineage && init_tMap_2_0) {
      class SchemaUtil_out_OK {
      
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
          
          public java.util.List<java.util.Map<String, String>> getSchema(final out_OKStruct out_OK) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(out_OK == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","INT","true","","3","0");
              a(s,"job","job","false","id_String","VARCHAR","true","","13","0");
              a(s,"marital","marital","false","id_String","VARCHAR","true","","8","0");
              a(s,"education","education","false","id_String","VARCHAR","true","","19","0");
              a(s,"has_default","has_default","false","id_String","VARCHAR","true","","7","0");
              a(s,"housing","housing","false","id_String","VARCHAR","true","","7","0");
              a(s,"loan","loan","false","id_String","VARCHAR","true","","7","0");
              a(s,"contact","contact","false","id_String","VARCHAR","true","","20","0");
              a(s,"month","month","false","id_String","VARCHAR","true","","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","VARCHAR","true","","3","0");
              a(s,"duration","duration","false","id_Integer","INT","true","","4","0");
              a(s,"campaign","campaign","false","id_Integer","INT","true","","1","0");
              a(s,"pdays","pdays","false","id_Integer","INT","true","","3","0");
              a(s,"previous","previous","false","id_Integer","INT","true","","1","0");
              a(s,"poutcome","poutcome","false","id_String","VARCHAR","true","","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","FLOAT","true","","10","4");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","FLOAT","true","","10","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","DOUBLE","true","","10","4");
              a(s,"euribor3m","euribor3m","false","id_Float","FLOAT","true","","10","4");
              a(s,"nr_employed","nr_employed","false","id_Double","DOUBLE","true","","12","4");
              a(s,"y","y","false","id_String","VARCHAR","true","","3","0");
              a(s,"load_ts","load_ts","false","id_Date","DATETIME","true","yyyy-MM-dd HH:mm:ss","26","0");
              a(s,"load_source","load_source","false","id_String","VARCHAR","true","","60","0");
              return s;
          }
          
      }
      
      if(out_OK != null) {
          talendJobLog.addConnectionSchemaMessage("tMap_2","tMap",
            "tDBOutput_3","tMysqlOutput", "out_OK" + iterateId, new SchemaUtil_out_OK().getSchema(out_OK));
          talendJobLogProcess(globalMap);
          init_tMap_2_0 = false;
      }
      
      }
  // QTUP-3575
      
      
      // QTUP-3575
      if(enableLineage && init_tMap_2_1) {
      class SchemaUtil_out_NOTOK {
      
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
          
          public java.util.List<java.util.Map<String, String>> getSchema(final out_NOTOKStruct out_NOTOK) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(out_NOTOK == null) {
                  return s;
              }
              a(s,"age","age","false","id_Integer","INT","true","","10","0");
              a(s,"job","job","false","id_String","VARCHAR","true","","13","0");
              a(s,"marital","marital","false","id_String","VARCHAR","true","","8","0");
              a(s,"education","education","false","id_String","VARCHAR","true","","19","0");
              a(s,"has_default","has_default","false","id_String","VARCHAR","true","","7","0");
              a(s,"housing","housing","false","id_String","VARCHAR","true","","7","0");
              a(s,"loan","loan","false","id_String","VARCHAR","true","","7","0");
              a(s,"contact","contact","false","id_String","VARCHAR","true","","20","0");
              a(s,"month","month","false","id_String","VARCHAR","true","","3","0");
              a(s,"day_of_week","day_of_week","false","id_String","VARCHAR","true","","3","0");
              a(s,"duration","duration","false","id_Integer","INT","true","","10","0");
              a(s,"campaign","campaign","false","id_Integer","INT","true","","10","0");
              a(s,"pdays","pdays","false","id_Integer","INT","true","","10","0");
              a(s,"previous","previous","false","id_Integer","INT","true","","10","0");
              a(s,"poutcome","poutcome","false","id_String","VARCHAR","true","","11","0");
              a(s,"emp_var_rate","emp_var_rate","false","id_Float","FLOAT","true","","50","2");
              a(s,"cons_price_idx","cons_price_idx","false","id_Float","FLOAT","true","","50","4");
              a(s,"cons_conf_idx","cons_conf_idx","false","id_Double","DOUBLE","true","","50","4");
              a(s,"euribor3m","euribor3m","false","id_Float","FLOAT","true","","50","4");
              a(s,"nr_employed","nr_employed","false","id_Double","DOUBLE","true","","50","4");
              a(s,"y","y","false","id_String","VARCHAR","true","","3","0");
              a(s,"codigo_regla","codigo_regla","false","id_String","","true","","15","null");
              a(s,"motivo_rechazo","motivo_rechazo","false","id_String","","true","","255","null");
              a(s,"responsable_remediacion","responsable_remediacion","false","id_String","","true","","60","null");
              a(s,"estado_remediacion","estado_remediacion","false","id_String","","true","","20","null");
              a(s,"load_ts","load_ts","false","id_Date","DATETIME","true","yyyy-MM-dd HH:mm:ss","26","0");
              a(s,"load_source","load_source","false","id_String","VARCHAR","true","","60","0");
              return s;
          }
          
      }
      
      if(out_NOTOK != null) {
          talendJobLog.addConnectionSchemaMessage("tMap_2","tMap",
            "tDBOutput_4","tMysqlOutput", "out_NOTOK" + iterateId, new SchemaUtil_out_NOTOK().getSchema(out_NOTOK));
          talendJobLogProcess(globalMap);
          init_tMap_2_1 = false;
      }
      
      }
  // QTUP-3575
      
      
      // QTUP-3575
      if(enableLineage && init_tMap_2_2) {
      class SchemaUtil_out_DQ {
      
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
          
          public java.util.List<java.util.Map<String, String>> getSchema(final out_DQStruct out_DQ) {
              java.util.List<java.util.Map<String, String>> s = new java.util.ArrayList<>();
              if(out_DQ == null) {
                  return s;
              }
              a(s,"run_id","run_id","false","id_String","","false","","null","null");
              a(s,"r01","r01","false","id_Integer","","true","","null","null");
              a(s,"r03","r03","false","id_Integer","","true","","null","null");
              a(s,"r04","r04","false","id_Integer","","true","","null","null");
              a(s,"r05","r05","false","id_Integer","","true","","null","null");
              a(s,"r06","r06","false","id_Integer","","true","","null","null");
              a(s,"r07","r07","false","id_Integer","","true","","null","null");
              a(s,"r08","r08","false","id_Integer","","true","","null","null");
              a(s,"r09","r09","false","id_Integer","","true","","null","null");
              a(s,"r10","r10","false","id_Integer","","true","","null","null");
              a(s,"r11","r11","false","id_Integer","","true","","null","null");
              a(s,"rules_passed","rules_passed","false","id_Integer","","true","","null","null");
              a(s,"dq_score","dq_score","false","id_Double","","true","","null","null");
              a(s,"dq_status","dq_status","false","id_String","","true","","null","null");
              a(s,"load_ts","load_ts","false","id_Date","","true","yyyy-MM-dd HH:mm:ss","null","null");
              return s;
          }
          
      }
      
      if(out_DQ != null) {
          talendJobLog.addConnectionSchemaMessage("tMap_2","tMap",
            "tDBOutput_1","tMysqlOutput", "out_DQ" + iterateId, new SchemaUtil_out_DQ().getSchema(out_DQ));
          talendJobLogProcess(globalMap);
          init_tMap_2_2 = false;
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
	
			
			
	
			cLabel="tMap_DQ";
		

 



		

/**
 * [tMap_2 process_data_begin ] stop
 */

// Start of branch "out_OK"
if(out_OK != null) { 



	
	/**
	 * [tDBOutput_3 main ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"out_OK","tMap_2","tMap_DQ","tMap","tDBOutput_3","tDBO_stg_bank_OK_g3","tMysqlOutput"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("out_OK - " + (out_OK==null? "": out_OK.toLogString()));
    			}
    		



        whetherReject_tDBOutput_3 = false;
                            if(out_OK.age == null) {
pstmt_tDBOutput_3.setNull(1, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_3.setInt(1, out_OK.age);
}

                            if(out_OK.job == null) {
pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(2, out_OK.job);
}

                            if(out_OK.marital == null) {
pstmt_tDBOutput_3.setNull(3, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(3, out_OK.marital);
}

                            if(out_OK.education == null) {
pstmt_tDBOutput_3.setNull(4, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(4, out_OK.education);
}

                            if(out_OK.has_default == null) {
pstmt_tDBOutput_3.setNull(5, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(5, out_OK.has_default);
}

                            if(out_OK.housing == null) {
pstmt_tDBOutput_3.setNull(6, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(6, out_OK.housing);
}

                            if(out_OK.loan == null) {
pstmt_tDBOutput_3.setNull(7, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(7, out_OK.loan);
}

                            if(out_OK.contact == null) {
pstmt_tDBOutput_3.setNull(8, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(8, out_OK.contact);
}

                            if(out_OK.month == null) {
pstmt_tDBOutput_3.setNull(9, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(9, out_OK.month);
}

                            if(out_OK.day_of_week == null) {
pstmt_tDBOutput_3.setNull(10, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(10, out_OK.day_of_week);
}

                            if(out_OK.duration == null) {
pstmt_tDBOutput_3.setNull(11, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_3.setInt(11, out_OK.duration);
}

                            if(out_OK.campaign == null) {
pstmt_tDBOutput_3.setNull(12, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_3.setInt(12, out_OK.campaign);
}

                            if(out_OK.pdays == null) {
pstmt_tDBOutput_3.setNull(13, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_3.setInt(13, out_OK.pdays);
}

                            if(out_OK.previous == null) {
pstmt_tDBOutput_3.setNull(14, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_3.setInt(14, out_OK.previous);
}

                            if(out_OK.poutcome == null) {
pstmt_tDBOutput_3.setNull(15, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(15, out_OK.poutcome);
}

                            if(out_OK.emp_var_rate == null) {
pstmt_tDBOutput_3.setNull(16, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_3.setFloat(16, out_OK.emp_var_rate);
}

                            if(out_OK.cons_price_idx == null) {
pstmt_tDBOutput_3.setNull(17, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_3.setFloat(17, out_OK.cons_price_idx);
}

                            if(out_OK.cons_conf_idx == null) {
pstmt_tDBOutput_3.setNull(18, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_3.setDouble(18, out_OK.cons_conf_idx);
}

                            if(out_OK.euribor3m == null) {
pstmt_tDBOutput_3.setNull(19, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_3.setFloat(19, out_OK.euribor3m);
}

                            if(out_OK.nr_employed == null) {
pstmt_tDBOutput_3.setNull(20, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_3.setDouble(20, out_OK.nr_employed);
}

                            if(out_OK.y == null) {
pstmt_tDBOutput_3.setNull(21, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(21, out_OK.y);
}

                            if(out_OK.load_ts != null) {
date_tDBOutput_3 = out_OK.load_ts.getTime();
if(date_tDBOutput_3 < year1_tDBOutput_3 || date_tDBOutput_3 >= year10000_tDBOutput_3) {
pstmt_tDBOutput_3.setString(22, "0000-00-00 00:00:00");
} else {pstmt_tDBOutput_3.setTimestamp(22, new java.sql.Timestamp(date_tDBOutput_3));
}
} else {
pstmt_tDBOutput_3.setNull(22, java.sql.Types.DATE);
}

                            if(out_OK.load_source == null) {
pstmt_tDBOutput_3.setNull(23, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(23, out_OK.load_source);
}

                    pstmt_tDBOutput_3.addBatch();
                    nb_line_tDBOutput_3++;

                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Adding the record ")  + (nb_line_tDBOutput_3)  + (" to the ")  + ("INSERT")  + (" batch.") );
                      batchSizeCounter_tDBOutput_3++;
                if ( batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3) {
                try {
                        int countSum_tDBOutput_3 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                        for(int countEach_tDBOutput_3: pstmt_tDBOutput_3.executeBatch()) {
                            countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
                        }
                        rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                        insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
                }catch (java.sql.BatchUpdateException e){
                    globalMap.put("tDBOutput_3_ERROR_MESSAGE",e.getMessage());
                    int countSum_tDBOutput_3 = 0;
                    for(int countEach_tDBOutput_3: e.getUpdateCounts()) {
                        countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
                    }
                    rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
                    insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
                    System.err.println(e.getMessage());
            log.error("tDBOutput_3 - "  + (e.getMessage()) );
                }

                batchSizeCounter_tDBOutput_3 = 0;
            }
                commitCounter_tDBOutput_3++;

                if(commitEvery_tDBOutput_3 <= commitCounter_tDBOutput_3) {

                        try {
                                int countSum_tDBOutput_3 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                                for(int countEach_tDBOutput_3: pstmt_tDBOutput_3.executeBatch()) {
                                    countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : 1);
                                }
                                rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                                insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
                        }catch (java.sql.BatchUpdateException e){
                            globalMap.put("tDBOutput_3_ERROR_MESSAGE",e.getMessage());
                            int countSum_tDBOutput_3 = 0;
                            for(int countEach_tDBOutput_3: e.getUpdateCounts()) {
                                countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
                            }
                            rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
                            insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
                            System.err.println(e.getMessage());
            log.error("tDBOutput_3 - "  + (e.getMessage()) );

                        }
                    if(rowsToCommitCount_tDBOutput_3 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_3)  + (" record(s).") );
                    }
                    conn_tDBOutput_3.commit();
                    if(rowsToCommitCount_tDBOutput_3 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection commit has succeeded.") );
                        rowsToCommitCount_tDBOutput_3 = 0;
                    }
                    commitCounter_tDBOutput_3=0;
                }

 


	tos_count_tDBOutput_3++;

		

/**
 * [tDBOutput_3 main ] stop
 */

	
	/**
	 * [tDBOutput_3 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		

 



		

/**
 * [tDBOutput_3 process_data_begin ] stop
 */

	
	/**
	 * [tDBOutput_3 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		

 



		

/**
 * [tDBOutput_3 process_data_end ] stop
 */


} // End of branch "out_OK"




// Start of branch "out_NOTOK"
if(out_NOTOK != null) { 



	
	/**
	 * [tDBOutput_4 main ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"out_NOTOK","tMap_2","tMap_DQ","tMap","tDBOutput_4","tDBO_stg_bank_NOTOK_g3","tMysqlOutput"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("out_NOTOK - " + (out_NOTOK==null? "": out_NOTOK.toLogString()));
    			}
    		



        whetherReject_tDBOutput_4 = false;
                            if(out_NOTOK.age == null) {
pstmt_tDBOutput_4.setNull(1, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_4.setInt(1, out_NOTOK.age);
}

                            if(out_NOTOK.job == null) {
pstmt_tDBOutput_4.setNull(2, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(2, out_NOTOK.job);
}

                            if(out_NOTOK.marital == null) {
pstmt_tDBOutput_4.setNull(3, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(3, out_NOTOK.marital);
}

                            if(out_NOTOK.education == null) {
pstmt_tDBOutput_4.setNull(4, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(4, out_NOTOK.education);
}

                            if(out_NOTOK.has_default == null) {
pstmt_tDBOutput_4.setNull(5, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(5, out_NOTOK.has_default);
}

                            if(out_NOTOK.housing == null) {
pstmt_tDBOutput_4.setNull(6, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(6, out_NOTOK.housing);
}

                            if(out_NOTOK.loan == null) {
pstmt_tDBOutput_4.setNull(7, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(7, out_NOTOK.loan);
}

                            if(out_NOTOK.contact == null) {
pstmt_tDBOutput_4.setNull(8, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(8, out_NOTOK.contact);
}

                            if(out_NOTOK.month == null) {
pstmt_tDBOutput_4.setNull(9, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(9, out_NOTOK.month);
}

                            if(out_NOTOK.day_of_week == null) {
pstmt_tDBOutput_4.setNull(10, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(10, out_NOTOK.day_of_week);
}

                            if(out_NOTOK.duration == null) {
pstmt_tDBOutput_4.setNull(11, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_4.setInt(11, out_NOTOK.duration);
}

                            if(out_NOTOK.campaign == null) {
pstmt_tDBOutput_4.setNull(12, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_4.setInt(12, out_NOTOK.campaign);
}

                            if(out_NOTOK.pdays == null) {
pstmt_tDBOutput_4.setNull(13, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_4.setInt(13, out_NOTOK.pdays);
}

                            if(out_NOTOK.previous == null) {
pstmt_tDBOutput_4.setNull(14, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_4.setInt(14, out_NOTOK.previous);
}

                            if(out_NOTOK.poutcome == null) {
pstmt_tDBOutput_4.setNull(15, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(15, out_NOTOK.poutcome);
}

                            if(out_NOTOK.emp_var_rate == null) {
pstmt_tDBOutput_4.setNull(16, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_4.setFloat(16, out_NOTOK.emp_var_rate);
}

                            if(out_NOTOK.cons_price_idx == null) {
pstmt_tDBOutput_4.setNull(17, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_4.setFloat(17, out_NOTOK.cons_price_idx);
}

                            if(out_NOTOK.cons_conf_idx == null) {
pstmt_tDBOutput_4.setNull(18, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_4.setDouble(18, out_NOTOK.cons_conf_idx);
}

                            if(out_NOTOK.euribor3m == null) {
pstmt_tDBOutput_4.setNull(19, java.sql.Types.FLOAT);
} else {pstmt_tDBOutput_4.setFloat(19, out_NOTOK.euribor3m);
}

                            if(out_NOTOK.nr_employed == null) {
pstmt_tDBOutput_4.setNull(20, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_4.setDouble(20, out_NOTOK.nr_employed);
}

                            if(out_NOTOK.y == null) {
pstmt_tDBOutput_4.setNull(21, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(21, out_NOTOK.y);
}

                            if(out_NOTOK.codigo_regla == null) {
pstmt_tDBOutput_4.setNull(22, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(22, out_NOTOK.codigo_regla);
}

                            if(out_NOTOK.motivo_rechazo == null) {
pstmt_tDBOutput_4.setNull(23, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(23, out_NOTOK.motivo_rechazo);
}

                            if(out_NOTOK.responsable_remediacion == null) {
pstmt_tDBOutput_4.setNull(24, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(24, out_NOTOK.responsable_remediacion);
}

                            if(out_NOTOK.estado_remediacion == null) {
pstmt_tDBOutput_4.setNull(25, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(25, out_NOTOK.estado_remediacion);
}

                            if(out_NOTOK.load_ts != null) {
date_tDBOutput_4 = out_NOTOK.load_ts.getTime();
if(date_tDBOutput_4 < year1_tDBOutput_4 || date_tDBOutput_4 >= year10000_tDBOutput_4) {
pstmt_tDBOutput_4.setString(26, "0000-00-00 00:00:00");
} else {pstmt_tDBOutput_4.setTimestamp(26, new java.sql.Timestamp(date_tDBOutput_4));
}
} else {
pstmt_tDBOutput_4.setNull(26, java.sql.Types.DATE);
}

                            if(out_NOTOK.load_source == null) {
pstmt_tDBOutput_4.setNull(27, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_4.setString(27, out_NOTOK.load_source);
}

                    pstmt_tDBOutput_4.addBatch();
                    nb_line_tDBOutput_4++;

                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Adding the record ")  + (nb_line_tDBOutput_4)  + (" to the ")  + ("INSERT")  + (" batch.") );
                      batchSizeCounter_tDBOutput_4++;
                if ( batchSize_tDBOutput_4 <= batchSizeCounter_tDBOutput_4) {
                try {
                        int countSum_tDBOutput_4 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                        for(int countEach_tDBOutput_4: pstmt_tDBOutput_4.executeBatch()) {
                            countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
                        }
                        rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                        insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
                }catch (java.sql.BatchUpdateException e){
                    globalMap.put("tDBOutput_4_ERROR_MESSAGE",e.getMessage());
                    int countSum_tDBOutput_4 = 0;
                    for(int countEach_tDBOutput_4: e.getUpdateCounts()) {
                        countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
                    }
                    rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
                    insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
                    System.err.println(e.getMessage());
            log.error("tDBOutput_4 - "  + (e.getMessage()) );
                }

                batchSizeCounter_tDBOutput_4 = 0;
            }
                commitCounter_tDBOutput_4++;

                if(commitEvery_tDBOutput_4 <= commitCounter_tDBOutput_4) {

                        try {
                                int countSum_tDBOutput_4 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                                for(int countEach_tDBOutput_4: pstmt_tDBOutput_4.executeBatch()) {
                                    countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : 1);
                                }
                                rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                                insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
                        }catch (java.sql.BatchUpdateException e){
                            globalMap.put("tDBOutput_4_ERROR_MESSAGE",e.getMessage());
                            int countSum_tDBOutput_4 = 0;
                            for(int countEach_tDBOutput_4: e.getUpdateCounts()) {
                                countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
                            }
                            rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
                            insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
                            System.err.println(e.getMessage());
            log.error("tDBOutput_4 - "  + (e.getMessage()) );

                        }
                    if(rowsToCommitCount_tDBOutput_4 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_4)  + (" record(s).") );
                    }
                    conn_tDBOutput_4.commit();
                    if(rowsToCommitCount_tDBOutput_4 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection commit has succeeded.") );
                        rowsToCommitCount_tDBOutput_4 = 0;
                    }
                    commitCounter_tDBOutput_4=0;
                }

 


	tos_count_tDBOutput_4++;

		

/**
 * [tDBOutput_4 main ] stop
 */

	
	/**
	 * [tDBOutput_4 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		

 



		

/**
 * [tDBOutput_4 process_data_begin ] stop
 */

	
	/**
	 * [tDBOutput_4 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		

 



		

/**
 * [tDBOutput_4 process_data_end ] stop
 */


} // End of branch "out_NOTOK"




// Start of branch "out_DQ"
if(out_DQ != null) { 



	
	/**
	 * [tDBOutput_1 main ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		
			if(runStat.update(execStat,enableLogStash,iterateId,1,1
				
					,"out_DQ","tMap_2","tMap_DQ","tMap","tDBOutput_1","tDBO_dq_record_g3","tMysqlOutput"
				
			)) {
				talendJobLogProcess(globalMap);
			}
			
    			if(log.isTraceEnabled()){
    				log.trace("out_DQ - " + (out_DQ==null? "": out_DQ.toLogString()));
    			}
    		



        whetherReject_tDBOutput_1 = false;
                            if(out_DQ.run_id == null) {
pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_1.setString(1, out_DQ.run_id);
}

                            if(out_DQ.r01 == null) {
pstmt_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(2, out_DQ.r01);
}

                            if(out_DQ.r03 == null) {
pstmt_tDBOutput_1.setNull(3, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(3, out_DQ.r03);
}

                            if(out_DQ.r04 == null) {
pstmt_tDBOutput_1.setNull(4, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(4, out_DQ.r04);
}

                            if(out_DQ.r05 == null) {
pstmt_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(5, out_DQ.r05);
}

                            if(out_DQ.r06 == null) {
pstmt_tDBOutput_1.setNull(6, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(6, out_DQ.r06);
}

                            if(out_DQ.r07 == null) {
pstmt_tDBOutput_1.setNull(7, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(7, out_DQ.r07);
}

                            if(out_DQ.r08 == null) {
pstmt_tDBOutput_1.setNull(8, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(8, out_DQ.r08);
}

                            if(out_DQ.r09 == null) {
pstmt_tDBOutput_1.setNull(9, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(9, out_DQ.r09);
}

                            if(out_DQ.r10 == null) {
pstmt_tDBOutput_1.setNull(10, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(10, out_DQ.r10);
}

                            if(out_DQ.r11 == null) {
pstmt_tDBOutput_1.setNull(11, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(11, out_DQ.r11);
}

                            if(out_DQ.rules_passed == null) {
pstmt_tDBOutput_1.setNull(12, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(12, out_DQ.rules_passed);
}

                            if(out_DQ.dq_score == null) {
pstmt_tDBOutput_1.setNull(13, java.sql.Types.DOUBLE);
} else {pstmt_tDBOutput_1.setDouble(13, out_DQ.dq_score);
}

                            if(out_DQ.dq_status == null) {
pstmt_tDBOutput_1.setNull(14, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_1.setString(14, out_DQ.dq_status);
}

                            if(out_DQ.load_ts != null) {
date_tDBOutput_1 = out_DQ.load_ts.getTime();
if(date_tDBOutput_1 < year1_tDBOutput_1 || date_tDBOutput_1 >= year10000_tDBOutput_1) {
pstmt_tDBOutput_1.setString(15, "0000-00-00 00:00:00");
} else {pstmt_tDBOutput_1.setTimestamp(15, new java.sql.Timestamp(date_tDBOutput_1));
}
} else {
pstmt_tDBOutput_1.setNull(15, java.sql.Types.DATE);
}

                    pstmt_tDBOutput_1.addBatch();
                    nb_line_tDBOutput_1++;

                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Adding the record ")  + (nb_line_tDBOutput_1)  + (" to the ")  + ("INSERT")  + (" batch.") );
                      batchSizeCounter_tDBOutput_1++;
                if ( batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1) {
                try {
                        int countSum_tDBOutput_1 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                        for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
                            countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
                        }
                        rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                        insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
                }catch (java.sql.BatchUpdateException e){
                    globalMap.put("tDBOutput_1_ERROR_MESSAGE",e.getMessage());
                    int countSum_tDBOutput_1 = 0;
                    for(int countEach_tDBOutput_1: e.getUpdateCounts()) {
                        countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
                    }
                    rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
                    insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
                    System.err.println(e.getMessage());
            log.error("tDBOutput_1 - "  + (e.getMessage()) );
                }

                batchSizeCounter_tDBOutput_1 = 0;
            }
                commitCounter_tDBOutput_1++;

                if(commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {

                        try {
                                int countSum_tDBOutput_1 = 0;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
                                for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
                                    countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : 1);
                                }
                                rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
                                insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
                        }catch (java.sql.BatchUpdateException e){
                            globalMap.put("tDBOutput_1_ERROR_MESSAGE",e.getMessage());
                            int countSum_tDBOutput_1 = 0;
                            for(int countEach_tDBOutput_1: e.getUpdateCounts()) {
                                countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
                            }
                            rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
                            insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
                            System.err.println(e.getMessage());
            log.error("tDBOutput_1 - "  + (e.getMessage()) );

                        }
                    if(rowsToCommitCount_tDBOutput_1 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_1)  + (" record(s).") );
                    }
                    conn_tDBOutput_1.commit();
                    if(rowsToCommitCount_tDBOutput_1 != 0){
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection commit has succeeded.") );
                        rowsToCommitCount_tDBOutput_1 = 0;
                    }
                    commitCounter_tDBOutput_1=0;
                }

 


	tos_count_tDBOutput_1++;

		

/**
 * [tDBOutput_1 main ] stop
 */

	
	/**
	 * [tDBOutput_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		

 



		

/**
 * [tDBOutput_1 process_data_begin ] stop
 */

	
	/**
	 * [tDBOutput_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		

 



		

/**
 * [tDBOutput_1 process_data_end ] stop
 */


} // End of branch "out_DQ"




	
	/**
	 * [tMap_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	
			cLabel="tMap_DQ";
		

 



		

/**
 * [tMap_2 process_data_end ] stop
 */


} // End of branch "row1"




	
	/**
	 * [tUniqRow_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		

 



		

/**
 * [tUniqRow_2 process_data_end ] stop
 */




	
	/**
	 * [tDBInput_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		

 



		

/**
 * [tDBInput_2 process_data_end ] stop
 */

	
	/**
	 * [tDBInput_2 end ] start
	 */

	

	
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		

	}
}finally{
	if (rs_tDBInput_2 != null) {
		rs_tDBInput_2.close();
	}
	if (stmt_tDBInput_2 != null) {
		stmt_tDBInput_2.close();
	}
		if(conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {
			
	    		log.debug("tDBInput_2 - Closing the connection to the database.");
			
			conn_tDBInput_2.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
	    		log.debug("tDBInput_2 - Connection to the database closed.");
			
		}
		
}
globalMap.put("tDBInput_2_NB_LINE",nb_line_tDBInput_2);
	    		log.debug("tDBInput_2 - Retrieved records count: "+nb_line_tDBInput_2 + " .");
			

 
                if(log.isDebugEnabled())
            log.debug("tDBInput_2 - "  + ("Done.") );

ok_Hash.put("tDBInput_2", true);
end_Hash.put("tDBInput_2", System.currentTimeMillis());




		

/**
 * [tDBInput_2 end ] stop
 */


	
	/**
	 * [tUniqRow_2 end ] start
	 */

	

	
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		

globalMap.put("tUniqRow_2_NB_UNIQUES",nb_uniques_tUniqRow_2);
globalMap.put("tUniqRow_2_NB_DUPLICATES",nb_duplicates_tUniqRow_2);
	log.info("tUniqRow_2 - Unique records count: " + (nb_uniques_tUniqRow_2)+" .");
	log.info("tUniqRow_2 - Duplicate records count: " + (nb_duplicates_tUniqRow_2)+" .");

			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"row3",2,0,
			 			"tDBInput_2","tDBI_raw_bank_g3","tMysqlInput","tUniqRow_2","tUniqueRow_columnas","tUniqRow","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tUniqRow_2 - "  + ("Done.") );

ok_Hash.put("tUniqRow_2", true);
end_Hash.put("tUniqRow_2", System.currentTimeMillis());




		

/**
 * [tUniqRow_2 end ] stop
 */


	
	/**
	 * [tMap_2 end ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	
			cLabel="tMap_DQ";
		


// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_2 - Written records count in the table 'out_OK': " + count_out_OK_tMap_2 + ".");
				log.debug("tMap_2 - Written records count in the table 'out_NOTOK': " + count_out_NOTOK_tMap_2 + ".");
				log.debug("tMap_2 - Written records count in the table 'out_DQ': " + count_out_DQ_tMap_2 + ".");





			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"row1",2,0,
			 			"tUniqRow_2","tUniqueRow_columnas","tUniqRow","tMap_2","tMap_DQ","tMap","output")) {
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
	 * [tDBOutput_3 end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		



				
					try {
						if (batchSizeCounter_tDBOutput_3 != 0) {
							int countSum_tDBOutput_3 = 0;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
							for(int countEach_tDBOutput_3: pstmt_tDBOutput_3.executeBatch()) {
								countSum_tDBOutput_3 += (countEach_tDBOutput_3 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
							}
							rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
							
								insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
							
						}
					}catch (java.sql.BatchUpdateException e){
						globalMap.put(currentComponent+"_ERROR_MESSAGE",e.getMessage());
						
							int countSum_tDBOutput_3 = 0;
							for(int countEach_tDBOutput_3: e.getUpdateCounts()) {
								countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
							}
							rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
							
								insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
								
            log.error("tDBOutput_3 - "  + (e.getMessage()) );
							System.err.println(e.getMessage());
							
					}
					batchSizeCounter_tDBOutput_3 = 0;
					
			
		

		if(pstmt_tDBOutput_3 != null) {
			
				pstmt_tDBOutput_3.close();
				resourceMap.remove("pstmt_tDBOutput_3");
			
		}
		
	resourceMap.put("statementClosed_tDBOutput_3", true);
	
			if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_3)  + (" record(s).") );
			}
			conn_tDBOutput_3.commit();
			if (commitCounter_tDBOutput_3 > 0 && rowsToCommitCount_tDBOutput_3 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection commit has succeeded.") );
				rowsToCommitCount_tDBOutput_3 = 0;
			}
			commitCounter_tDBOutput_3 = 0;
			
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Closing the connection to the database.") );
		conn_tDBOutput_3 .close();
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection to the database has closed.") );
		resourceMap.put("finish_tDBOutput_3", true);
	

	nb_line_deleted_tDBOutput_3=nb_line_deleted_tDBOutput_3+ deletedCount_tDBOutput_3;
	nb_line_update_tDBOutput_3=nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
	nb_line_inserted_tDBOutput_3=nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
	nb_line_rejected_tDBOutput_3=nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;
	
        globalMap.put("tDBOutput_3_NB_LINE",nb_line_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_UPDATED",nb_line_update_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_DELETED",nb_line_deleted_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);
    

	
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Has ")  + ("inserted")  + (" ")  + (nb_line_inserted_tDBOutput_3)  + (" record(s).") );

			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"out_OK",2,0,
			 			"tMap_2","tMap_DQ","tMap","tDBOutput_3","tDBO_stg_bank_OK_g3","tMysqlOutput","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Done.") );

ok_Hash.put("tDBOutput_3", true);
end_Hash.put("tDBOutput_3", System.currentTimeMillis());




		

/**
 * [tDBOutput_3 end ] stop
 */





	
	/**
	 * [tDBOutput_4 end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		



				
					try {
						if (batchSizeCounter_tDBOutput_4 != 0) {
							int countSum_tDBOutput_4 = 0;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
							for(int countEach_tDBOutput_4: pstmt_tDBOutput_4.executeBatch()) {
								countSum_tDBOutput_4 += (countEach_tDBOutput_4 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
							}
							rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
							
								insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
							
						}
					}catch (java.sql.BatchUpdateException e){
						globalMap.put(currentComponent+"_ERROR_MESSAGE",e.getMessage());
						
							int countSum_tDBOutput_4 = 0;
							for(int countEach_tDBOutput_4: e.getUpdateCounts()) {
								countSum_tDBOutput_4 += (countEach_tDBOutput_4 < 0 ? 0 : countEach_tDBOutput_4);
							}
							rowsToCommitCount_tDBOutput_4 += countSum_tDBOutput_4;
							
								insertedCount_tDBOutput_4 += countSum_tDBOutput_4;
								
            log.error("tDBOutput_4 - "  + (e.getMessage()) );
							System.err.println(e.getMessage());
							
					}
					batchSizeCounter_tDBOutput_4 = 0;
					
			
		

		if(pstmt_tDBOutput_4 != null) {
			
				pstmt_tDBOutput_4.close();
				resourceMap.remove("pstmt_tDBOutput_4");
			
		}
		
	resourceMap.put("statementClosed_tDBOutput_4", true);
	
			if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_4)  + (" record(s).") );
			}
			conn_tDBOutput_4.commit();
			if (commitCounter_tDBOutput_4 > 0 && rowsToCommitCount_tDBOutput_4 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection commit has succeeded.") );
				rowsToCommitCount_tDBOutput_4 = 0;
			}
			commitCounter_tDBOutput_4 = 0;
			
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Closing the connection to the database.") );
		conn_tDBOutput_4 .close();
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection to the database has closed.") );
		resourceMap.put("finish_tDBOutput_4", true);
	

	nb_line_deleted_tDBOutput_4=nb_line_deleted_tDBOutput_4+ deletedCount_tDBOutput_4;
	nb_line_update_tDBOutput_4=nb_line_update_tDBOutput_4 + updatedCount_tDBOutput_4;
	nb_line_inserted_tDBOutput_4=nb_line_inserted_tDBOutput_4 + insertedCount_tDBOutput_4;
	nb_line_rejected_tDBOutput_4=nb_line_rejected_tDBOutput_4 + rejectedCount_tDBOutput_4;
	
        globalMap.put("tDBOutput_4_NB_LINE",nb_line_tDBOutput_4);
        globalMap.put("tDBOutput_4_NB_LINE_UPDATED",nb_line_update_tDBOutput_4);
        globalMap.put("tDBOutput_4_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_4);
        globalMap.put("tDBOutput_4_NB_LINE_DELETED",nb_line_deleted_tDBOutput_4);
        globalMap.put("tDBOutput_4_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_4);
    

	
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Has ")  + ("inserted")  + (" ")  + (nb_line_inserted_tDBOutput_4)  + (" record(s).") );

			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"out_NOTOK",2,0,
			 			"tMap_2","tMap_DQ","tMap","tDBOutput_4","tDBO_stg_bank_NOTOK_g3","tMysqlOutput","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Done.") );

ok_Hash.put("tDBOutput_4", true);
end_Hash.put("tDBOutput_4", System.currentTimeMillis());




		

/**
 * [tDBOutput_4 end ] stop
 */





	
	/**
	 * [tDBOutput_1 end ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		



				
					try {
						if (batchSizeCounter_tDBOutput_1 != 0) {
							int countSum_tDBOutput_1 = 0;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Executing the ")  + ("INSERT")  + (" batch.") );
							for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
								countSum_tDBOutput_1 += (countEach_tDBOutput_1 == java.sql.Statement.EXECUTE_FAILED ? 0 : 1);
							}
							rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
							
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("The ")  + ("INSERT")  + (" batch execution has succeeded.") );
							
								insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
							
						}
					}catch (java.sql.BatchUpdateException e){
						globalMap.put(currentComponent+"_ERROR_MESSAGE",e.getMessage());
						
							int countSum_tDBOutput_1 = 0;
							for(int countEach_tDBOutput_1: e.getUpdateCounts()) {
								countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
							}
							rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
							
								insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
								
            log.error("tDBOutput_1 - "  + (e.getMessage()) );
							System.err.println(e.getMessage());
							
					}
					batchSizeCounter_tDBOutput_1 = 0;
					
			
		

		if(pstmt_tDBOutput_1 != null) {
			
				pstmt_tDBOutput_1.close();
				resourceMap.remove("pstmt_tDBOutput_1");
			
		}
		
	resourceMap.put("statementClosed_tDBOutput_1", true);
	
			if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection starting to commit ")  + (rowsToCommitCount_tDBOutput_1)  + (" record(s).") );
			}
			conn_tDBOutput_1.commit();
			if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {
				
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection commit has succeeded.") );
				rowsToCommitCount_tDBOutput_1 = 0;
			}
			commitCounter_tDBOutput_1 = 0;
			
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Closing the connection to the database.") );
		conn_tDBOutput_1 .close();
		
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection to the database has closed.") );
		resourceMap.put("finish_tDBOutput_1", true);
	

	nb_line_deleted_tDBOutput_1=nb_line_deleted_tDBOutput_1+ deletedCount_tDBOutput_1;
	nb_line_update_tDBOutput_1=nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
	nb_line_inserted_tDBOutput_1=nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
	nb_line_rejected_tDBOutput_1=nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;
	
        globalMap.put("tDBOutput_1_NB_LINE",nb_line_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_UPDATED",nb_line_update_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_DELETED",nb_line_deleted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);
    

	
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Has ")  + ("inserted")  + (" ")  + (nb_line_inserted_tDBOutput_1)  + (" record(s).") );

			 		if(runStat.updateStatAndLog(execStat,enableLogStash,resourceMap,iterateId,"out_DQ",2,0,
			 			"tMap_2","tMap_DQ","tMap","tDBOutput_1","tDBO_dq_record_g3","tMysqlOutput","output")) {
						talendJobLogProcess(globalMap);
					}
				
 
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Done.") );

ok_Hash.put("tDBOutput_1", true);
end_Hash.put("tDBOutput_1", System.currentTimeMillis());




		

/**
 * [tDBOutput_1 end ] stop
 */










				}//end the resume

				
				    			if(resumeEntryMethodName == null || globalResumeTicket){
				    				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBInput_2:OnSubjobOk", "", Thread.currentThread().getId() + "", "", "", "", "", "");
								}	    				    			
					    	
								if(execStat){    	
									runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
								} 
							
							tDBRow_2Process(globalMap); 
						



	
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
	 * [tDBInput_2 finally ] start
	 */

	

	
	
	s(currentComponent="tDBInput_2");
	
			
			
	
			cLabel="tDBI_raw_bank_g3";
		

 



		

/**
 * [tDBInput_2 finally ] stop
 */


	
	/**
	 * [tUniqRow_2 finally ] start
	 */

	

	
	
	s(currentComponent="tUniqRow_2");
	
			
			
	
			cLabel="tUniqueRow_columnas";
		

 



		

/**
 * [tUniqRow_2 finally ] stop
 */


	
	/**
	 * [tMap_2 finally ] start
	 */

	

	
	
	s(currentComponent="tMap_2");
	
			
			
	
			cLabel="tMap_DQ";
		

 



		

/**
 * [tMap_2 finally ] stop
 */


	
	/**
	 * [tDBOutput_3 finally ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_3");
	
			
			
	
			cLabel="tDBO_stg_bank_OK_g3";
		



    try {
    if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
                if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_3")) != null) {
                    pstmtToClose_tDBOutput_3.close();
                }
    }
    } finally {
        if(resourceMap.get("finish_tDBOutput_3") == null){
            java.sql.Connection ctn_tDBOutput_3 = null;
            if((ctn_tDBOutput_3 = (java.sql.Connection)resourceMap.get("conn_tDBOutput_3")) != null){
                try {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Closing the connection to the database.") );
                    ctn_tDBOutput_3.close();
                if(log.isDebugEnabled())
            log.debug("tDBOutput_3 - "  + ("Connection to the database has closed.") );
                } catch (java.sql.SQLException sqlEx_tDBOutput_3) {
                    String errorMessage_tDBOutput_3 = "failed to close the connection in tDBOutput_3 :" + sqlEx_tDBOutput_3.getMessage();
            log.error("tDBOutput_3 - "  + (errorMessage_tDBOutput_3) );
                    System.err.println(errorMessage_tDBOutput_3);
                }
            }
        }
    }
 



		

/**
 * [tDBOutput_3 finally ] stop
 */





	
	/**
	 * [tDBOutput_4 finally ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_4");
	
			
			
	
			cLabel="tDBO_stg_bank_NOTOK_g3";
		



    try {
    if (resourceMap.get("statementClosed_tDBOutput_4") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_4 = null;
                if ((pstmtToClose_tDBOutput_4 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_4")) != null) {
                    pstmtToClose_tDBOutput_4.close();
                }
    }
    } finally {
        if(resourceMap.get("finish_tDBOutput_4") == null){
            java.sql.Connection ctn_tDBOutput_4 = null;
            if((ctn_tDBOutput_4 = (java.sql.Connection)resourceMap.get("conn_tDBOutput_4")) != null){
                try {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Closing the connection to the database.") );
                    ctn_tDBOutput_4.close();
                if(log.isDebugEnabled())
            log.debug("tDBOutput_4 - "  + ("Connection to the database has closed.") );
                } catch (java.sql.SQLException sqlEx_tDBOutput_4) {
                    String errorMessage_tDBOutput_4 = "failed to close the connection in tDBOutput_4 :" + sqlEx_tDBOutput_4.getMessage();
            log.error("tDBOutput_4 - "  + (errorMessage_tDBOutput_4) );
                    System.err.println(errorMessage_tDBOutput_4);
                }
            }
        }
    }
 



		

/**
 * [tDBOutput_4 finally ] stop
 */





	
	/**
	 * [tDBOutput_1 finally ] start
	 */

	

	
	
	s(currentComponent="tDBOutput_1");
	
			
			
	
			cLabel="tDBO_dq_record_g3";
		



    try {
    if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
                if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_1")) != null) {
                    pstmtToClose_tDBOutput_1.close();
                }
    }
    } finally {
        if(resourceMap.get("finish_tDBOutput_1") == null){
            java.sql.Connection ctn_tDBOutput_1 = null;
            if((ctn_tDBOutput_1 = (java.sql.Connection)resourceMap.get("conn_tDBOutput_1")) != null){
                try {
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Closing the connection to the database.") );
                    ctn_tDBOutput_1.close();
                if(log.isDebugEnabled())
            log.debug("tDBOutput_1 - "  + ("Connection to the database has closed.") );
                } catch (java.sql.SQLException sqlEx_tDBOutput_1) {
                    String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :" + sqlEx_tDBOutput_1.getMessage();
            log.error("tDBOutput_1 - "  + (errorMessage_tDBOutput_1) );
                    System.err.println(errorMessage_tDBOutput_1);
                }
            }
        }
    }
 



		

/**
 * [tDBOutput_1 finally ] stop
 */










				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}
	


public void tDBRow_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBRow_2_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tDBRow_2", "t4stGI_");

	
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
	 * [tDBRow_2 begin ] start
	 */

	

	
		
		sh("tDBRow_2");
		
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		
		int tos_count_tDBRow_2 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tDBRow_2 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tDBRow_2{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tDBRow_2 = new StringBuilder();
                    log4jParamters_tDBRow_2.append("Parameters:");
                            log4jParamters_tDBRow_2.append("DB_VERSION" + " = " + "MYSQL_8");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("USE_EXISTING_CONNECTION" + " = " + "false");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("HOST" + " = " + "context.DB_HOST");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("PORT" + " = " + "context.DB_PORT");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("DBNAME" + " = " + "context.DB_NAME");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("USER" + " = " + "context.DB_USER");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("PASS" + " = " + String.valueOf(routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD")).substring(0, 4) + "...");     
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("QUERYSTORE" + " = " + "\"\"");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("QUERY" + " = " + "\"INSERT INTO dq_scorecard_g3 (run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts) \" + \"SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts FROM v_dq_scorecard_g3 WHERE run_id = '\" + globalMap.get(\"RUN_ID\") + \"' \" + \"UNION ALL \" + \"SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, IF(pct >= umbral,'PASS','FAIL'), NOW() FROM (\" + \"SELECT '\" + globalMap.get(\"RUN_ID\") + \"' AS run_id, 'r02_unicidad' AS regla, 'Unicidad' AS dimension, \" + globalMap.get(\"tUniqRow_2_NB_UNIQUES\") + \" AS pasan, \" + globalMap.get(\"tUniqRow_2_NB_DUPLICATES\") + \" AS fallan, \" + \"ROUND(100*\" + globalMap.get(\"tUniqRow_2_NB_UNIQUES\") + \"/(\" + globalMap.get(\"tUniqRow_2_NB_UNIQUES\") + \"+\" + globalMap.get(\"tUniqRow_2_NB_DUPLICATES\") + \"),2) AS pct, 99.50 AS umbral) u\"");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("DIE_ON_ERROR" + " = " + "true");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("PROPERTIES" + " = " + "\"noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true\"");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("PROPAGATE_RECORD_SET" + " = " + "false");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("USE_PREPAREDSTATEMENT" + " = " + "false");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("COMMIT_EVERY" + " = " + "10000");
                        log4jParamters_tDBRow_2.append(" | ");
                            log4jParamters_tDBRow_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
                        log4jParamters_tDBRow_2.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tDBRow_2 - "  + (log4jParamters_tDBRow_2) );
                    } 
                } 
            new BytesLimit65535_tDBRow_2().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tDBRow_2{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
                            component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("false"));
                            component_parameters.put("HOST", String.valueOf(context.DB_HOST));
                            component_parameters.put("PORT", String.valueOf(context.DB_PORT));
                            component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
                            component_parameters.put("USER", String.valueOf(context.DB_USER));
                            component_parameters.put("QUERYSTORE", String.valueOf(""));
                            component_parameters.put("QUERY", String.valueOf("INSERT INTO dq_scorecard_g3 (run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts) " + new StringBuilder().append("SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts FROM v_dq_scorecard_g3 WHERE run_id = '" +"").toString() + globalMap.get("RUN_ID") + "' " + "UNION ALL " + "SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, IF(pct >= umbral,'PASS','FAIL'), NOW() FROM (" + "SELECT '" + globalMap.get("RUN_ID") + "' AS run_id, 'r02_unicidad' AS regla, 'Unicidad' AS dimension, " + globalMap.get("tUniqRow_2_NB_UNIQUES") + " AS pasan, " + globalMap.get("tUniqRow_2_NB_DUPLICATES") + " AS fallan, " + "ROUND(100*" + globalMap.get("tUniqRow_2_NB_UNIQUES") + "/(" + globalMap.get("tUniqRow_2_NB_UNIQUES") + "+" + globalMap.get("tUniqRow_2_NB_DUPLICATES") + "),2) AS pct, 99.50 AS umbral) u"));
                            component_parameters.put("SPECIFY_DATASOURCE_ALIAS", String.valueOf("false"));
                            component_parameters.put("DIE_ON_ERROR", String.valueOf("true"));
                            component_parameters.put("PROPERTIES", String.valueOf("noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true"));
                            component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
                            component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
                            component_parameters.put("COMMIT_EVERY", String.valueOf("10000"));
                            component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));
                
                
                
                } catch (java.lang.Exception e_tDBRow_2) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tDBRow_2", "tMysqlRow",
          new ParameterUtil_tDBRow_2().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tDBRow_2", "tDBR_SCORECARD", "tMysqlRow");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

	java.sql.Connection conn_tDBRow_2 = null;
	String query_tDBRow_2 = "";
	boolean whetherReject_tDBRow_2 = false;
	int count_tDBRow_2 = 0;
			String driverClass_tDBRow_2 = "com.mysql.cj.jdbc.Driver";
		    java.lang.Class jdbcclazz_tDBRow_2 = java.lang.Class.forName(driverClass_tDBRow_2);
		
        String properties_tDBRow_2 = "noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true";
        if (properties_tDBRow_2 == null || properties_tDBRow_2.trim().length() == 0) {
            properties_tDBRow_2 = "allowLoadLocalInfile=true";
        }else {
            if (!properties_tDBRow_2.contains("allowLoadLocalInfile=")) {
                properties_tDBRow_2 += "&allowLoadLocalInfile=true";
            }
        }

        String url_tDBRow_2 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/" + context.DB_NAME + "?" + properties_tDBRow_2;
	    		log.debug("tDBRow_2 - Driver ClassName: "+driverClass_tDBRow_2+".");
			
					String dbUser_tDBRow_2 = context.DB_USER;
	        
            		
            		
            		
	final String decryptedPassword_tDBRow_2 = java.util.Optional.ofNullable(context.DB_PASSWORD).orElse("");
        		   	
        	        String dbPwd_tDBRow_2 = decryptedPassword_tDBRow_2;
	        
					
	    		log.debug("tDBRow_2 - Connection attempt to '" + url_tDBRow_2.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' with the username '" + dbUser_tDBRow_2 + "'.");
			
			conn_tDBRow_2 = java.sql.DriverManager.getConnection(url_tDBRow_2,dbUser_tDBRow_2,dbPwd_tDBRow_2);
		
	    		log.debug("tDBRow_2 - Connection to '" + url_tDBRow_2.replaceAll("(?<=trustStorePassword=)[^;]*", "********") + "' has succeeded.");
			
        resourceMap.put("conn_tDBRow_2", conn_tDBRow_2);
					if(conn_tDBRow_2.getAutoCommit()) {
						
	    		log.debug("tDBRow_2 - Connection is set auto commit to 'false'.");
			
				conn_tDBRow_2.setAutoCommit(false);
			
					}        
					int commitEvery_tDBRow_2 = 10000;
					int commitCounter_tDBRow_2 = 0;
				
        java.sql.Statement stmt_tDBRow_2 = conn_tDBRow_2.createStatement();
        resourceMap.put("stmt_tDBRow_2", stmt_tDBRow_2);

 



		

/**
 * [tDBRow_2 begin ] stop
 */

	
	/**
	 * [tDBRow_2 main ] start
	 */

	

	
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		

query_tDBRow_2 = "INSERT INTO dq_scorecard_g3 (run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts) " +
new StringBuilder().append("SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, resultado, load_ts FROM v_dq_scorecard_g3 WHERE run_id = '"
+"").toString() + globalMap.get("RUN_ID") + "' " +
"UNION ALL " +
"SELECT run_id, regla, dimension, pasan, fallan, pct, umbral, IF(pct >= umbral,'PASS','FAIL'), NOW() FROM (" +
"SELECT '" + globalMap.get("RUN_ID") + "' AS run_id, 'r02_unicidad' AS regla, 'Unicidad' AS dimension, " +
globalMap.get("tUniqRow_2_NB_UNIQUES") + " AS pasan, " + globalMap.get("tUniqRow_2_NB_DUPLICATES") + " AS fallan, " +
"ROUND(100*" + globalMap.get("tUniqRow_2_NB_UNIQUES") + "/(" + globalMap.get("tUniqRow_2_NB_UNIQUES") + "+" + globalMap.get("tUniqRow_2_NB_DUPLICATES") + "),2) AS pct, 99.50 AS umbral) u";
whetherReject_tDBRow_2 = false;
	    		log.debug("tDBRow_2 - Executing the query: '" + query_tDBRow_2 + "'.");
			
globalMap.put("tDBRow_2_QUERY",query_tDBRow_2);
try {
				stmt_tDBRow_2.execute(query_tDBRow_2);
	    		log.debug("tDBRow_2 - Execute the query: '" + query_tDBRow_2 + "' has finished.");
			
	} catch (java.lang.Exception e) {
		whetherReject_tDBRow_2 = true;
		
			throw(e);
			
	}
	
		commitCounter_tDBRow_2++;
		if(commitEvery_tDBRow_2 <= commitCounter_tDBRow_2) {
			
	    		log.debug("tDBRow_2 - Connection starting to commit.");
			
			conn_tDBRow_2.commit();
			
	    		log.debug("tDBRow_2 - Connection commit has succeeded.");
			
			commitCounter_tDBRow_2=0;
		}
		

 


	tos_count_tDBRow_2++;

		

/**
 * [tDBRow_2 main ] stop
 */

	
	/**
	 * [tDBRow_2 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		

 



		

/**
 * [tDBRow_2 process_data_begin ] stop
 */

	
	/**
	 * [tDBRow_2 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		

 



		

/**
 * [tDBRow_2 process_data_end ] stop
 */

	
	/**
	 * [tDBRow_2 end ] start
	 */

	

	
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		

	globalMap.put("tDBRow_2_NB_LINE", count_tDBRow_2);
        stmt_tDBRow_2.close();
        resourceMap.remove("stmt_tDBRow_2");
    resourceMap.put("statementClosed_tDBRow_2", true);
		if(commitEvery_tDBRow_2>commitCounter_tDBRow_2){

			
	    		log.debug("tDBRow_2 - Connection starting to commit.");
			
			conn_tDBRow_2.commit();
			
	    		log.debug("tDBRow_2 - Connection commit has succeeded.");
			
	
			commitCounter_tDBRow_2=0;
	
		}
	    		log.debug("tDBRow_2 - Closing the connection to the database.");
			
			conn_tDBRow_2.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
	    		log.debug("tDBRow_2 - Connection to the database closed.");
			
    resourceMap.put("finish_tDBRow_2", true);
 
                if(log.isDebugEnabled())
            log.debug("tDBRow_2 - "  + ("Done.") );

ok_Hash.put("tDBRow_2", true);
end_Hash.put("tDBRow_2", System.currentTimeMillis());




		

/**
 * [tDBRow_2 end ] stop
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
	 * [tDBRow_2 finally ] start
	 */

	

	
	
	s(currentComponent="tDBRow_2");
	
			
			
	
			cLabel="tDBR_SCORECARD";
		

try {
    if (resourceMap.get("statementClosed_tDBRow_2") == null) {
            java.sql.Statement stmtToClose_tDBRow_2 = null;
            if ((stmtToClose_tDBRow_2 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_2")) != null) {
                stmtToClose_tDBRow_2.close();
            }
    }
} finally {
        if(resourceMap.get("finish_tDBRow_2") == null){
            java.sql.Connection ctn_tDBRow_2 = null;
            if((ctn_tDBRow_2 = (java.sql.Connection)resourceMap.get("conn_tDBRow_2")) != null){
                try {
	    		log.debug("tDBRow_2 - Closing the connection to the database.");
			
                    ctn_tDBRow_2.close();
	    		log.debug("tDBRow_2 - Connection to the database closed.");
			
                } catch (java.sql.SQLException sqlEx_tDBRow_2) {
                    String errorMessage_tDBRow_2 = "failed to close the connection in tDBRow_2 :" + sqlEx_tDBRow_2.getMessage();
				log.error("tDBRow_2 - " + sqlEx_tDBRow_2.getMessage());
			
                    System.err.println(errorMessage_tDBRow_2);
                }
            }
        }
    }
 



		

/**
 * [tDBRow_2 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBRow_2_SUBPROCESS_STATE", 1);
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
        final JOB_02_quality JOB_02_qualityClass = new JOB_02_quality();
        int exitCode = JOB_02_qualityClass.runJobInTOS(args);
	        if(exitCode==0){
		        log.info("TalendJob: 'JOB_02_quality' - Done.");
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
	         

            log.info("TalendJob: 'JOB_02_quality' - Start.");

            java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
            for(Object jobInfoKey: jobInfoKeys) {
                org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
            }
            org.slf4j.MDC.put("_pid", pid);
            org.slf4j.MDC.put("_rootPid", rootPid);
            org.slf4j.MDC.put("_fatherPid", fatherPid);
            org.slf4j.MDC.put("_projectName", projectName);
            org.slf4j.MDC.put("_startTimestamp",java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC ).format( java.time.format.DateTimeFormatter.ISO_INSTANT ));
            org.slf4j.MDC.put("_jobRepositoryId","_8qu1gNiHEfGYDKHwOCnAyQ");
            org.slf4j.MDC.put("_compiledAtTimestamp","2026-11-08T01:32:19.819945700Z");

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
            java.io.InputStream inContext = JOB_02_quality.class.getClassLoader().getResourceAsStream("g3_uci_bank/job_02_quality_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = JOB_02_quality.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
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
            log.info("TalendJob: 'JOB_02_quality' - Started.");
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

try {
errorCode = null;tPrejob_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tPrejob_1) {
globalMap.put("tPrejob_1_SUBPROCESS_STATE", -1);

e_tPrejob_1.printStackTrace();

}



		if(enableLogStash) {
	        talendJobLog.addJobStartMessage();
	        try {
	            talendJobLogProcess(globalMap);
	        } catch (java.lang.Exception e) {
	            e.printStackTrace();
	        }
        }

this.globalResumeTicket = false;//to run others jobs


this.globalResumeTicket = true;//to run tPostJob

try {
errorCode = null;tPostjob_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tPostjob_1) {
globalMap.put("tPostjob_1_SUBPROCESS_STATE", -1);

e_tPostjob_1.printStackTrace();

}



        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : JOB_02_quality");
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
        log.info("TalendJob: 'JOB_02_quality' - Finished - status: " + status + " returnCode: " + returnCode );

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
 *     480168 characters generated by Qlik Talend Cloud Enterprise Edition 
 *     on the 7 de noviembre de 2026, 20:32:19 ECT
 ************************************************************************************************/