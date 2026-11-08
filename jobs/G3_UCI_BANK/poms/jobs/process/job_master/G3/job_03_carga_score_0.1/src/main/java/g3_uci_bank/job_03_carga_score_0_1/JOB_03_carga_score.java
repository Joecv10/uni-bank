
package g3_uci_bank.job_03_carga_score_0_1;

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
 * Job: JOB_03_carga_score Purpose: Job carga primero tablas de calidad y luego scores<br>
 * Description: Job carga primero tablas de calidad y luego scores <br>
 * @author Myer, Jav
 * @version 8.0.1.20261022_0848-patch
 * @status 
 */
public class JOB_03_carga_score implements TalendJob {
	static {System.setProperty("TalendJob.log", "JOB_03_carga_score.log");}

	

	
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(JOB_03_carga_score.class);
	

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
	private final String jobName = "JOB_03_carga_score";
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
	

private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName, "_3kouQNlfEfG8YfyhiAOB-A", "0.1");
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
			JOB_03_carga_score.this.exception = e;
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(JOB_03_carga_score.this, new Object[] { e , currentComponent, globalMap});
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

			public void tPostjob_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tPostjob_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tSystem_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tSystem_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void talendJobLog_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					talendJobLog_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tPostjob_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tSystem_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void talendJobLog_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	



public void tPostjob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tPostjob_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tPostjob_1", "7qRgJM_");

	
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
				tSystem_1Process(globalMap);



		

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
	


public void tSystem_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tSystem_1_SUBPROCESS_STATE", 0);

	final boolean execStat = this.execStat;

		mdc("tSystem_1", "EaD5HU_");

	
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
	 * [tSystem_1 begin ] start
	 */

	

	
		
		sh("tSystem_1");
		
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		
		int tos_count_tSystem_1 = 0;
		
                if(log.isDebugEnabled())
            log.debug("tSystem_1 - "  + ("Start to work.") );
            if (log.isDebugEnabled()) {
                class BytesLimit65535_tSystem_1{
                    public void limitLog4jByte() throws Exception{
                    StringBuilder log4jParamters_tSystem_1 = new StringBuilder();
                    log4jParamters_tSystem_1.append("Parameters:");
                            log4jParamters_tSystem_1.append("ROOTDIR" + " = " + "false");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("USE_SINGLE_COMMAND" + " = " + "true");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("COMMAND" + " = " + "\"cmd /c cd /d C:\\\\Users\\\\USER\\\\Desktop\\\\SCORES_PY && python scoring_bank.py\"");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("USE_ARRAY_COMMAND" + " = " + "false");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("OUTPUT" + " = " + "OUTPUT_TO_CONSOLE");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("ERROROUTPUT" + " = " + "OUTPUT_TO_CONSOLE");
                        log4jParamters_tSystem_1.append(" | ");
                            log4jParamters_tSystem_1.append("PARAMS" + " = " + "[]");
                        log4jParamters_tSystem_1.append(" | ");
                if(log.isDebugEnabled())
            log.debug("tSystem_1 - "  + (log4jParamters_tSystem_1) );
                    } 
                } 
            new BytesLimit65535_tSystem_1().limitLog4jByte();
            }
        // QTUP-3575
        if(enableLineage) {
        class ParameterUtil_tSystem_1{
            
            private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
                java.util.Map<String, String> field = new java.util.HashMap<>();
                field.put("name", values[0]);
                field.put("talend_type", values[1]);
                schema.add(field);
            }
        
            public java.util.Map<String, String> getParameter() throws Exception{
                java.util.Map<String, String> component_parameters = new java.util.HashMap<>();
                
                try {
                
                            component_parameters.put("ROOTDIR", String.valueOf("false"));
                            component_parameters.put("USE_SINGLE_COMMAND", String.valueOf("true"));
                            component_parameters.put("COMMAND", String.valueOf("cmd /c cd /d C:\\Users\\USER\\Desktop\\SCORES_PY && python scoring_bank.py"));
                            component_parameters.put("USE_ARRAY_COMMAND", String.valueOf("false"));
                            component_parameters.put("OUTPUT", String.valueOf("OUTPUT_TO_CONSOLE"));
                            component_parameters.put("ERROROUTPUT", String.valueOf("OUTPUT_TO_CONSOLE"));
                            component_parameters.put("PARAMS", String.valueOf("[]"));
                
                
                
                } catch (java.lang.Exception e_tSystem_1) {
                    //do nothing
                }
                
                return component_parameters;
            }
        }
        
        talendJobLog.addComponentParameterMessage("tSystem_1", "tSystem",
          new ParameterUtil_tSystem_1().getParameter());
        talendJobLogProcess(globalMap);
        s(currentComponent);
        }
        // QTUP-3575
		
			if(enableLogStash) {
				talendJobLog.addCM("tSystem_1", "tPy", "tSystem");
				talendJobLogProcess(globalMap);
				s(currentComponent);
			}
			

Runtime runtime_tSystem_1 = Runtime.getRuntime();

String[] env_tSystem_1= null;
java.util.Map<String,String> envMap_tSystem_1= System.getenv();
java.util.Map<String,String> envMapClone_tSystem_1= new java.util.HashMap();
envMapClone_tSystem_1.putAll(envMap_tSystem_1);

	log.info("tSystem_1 - Setting the parameters.");
final Process ps_tSystem_1 = runtime_tSystem_1.exec( "cmd /c cd /d C:\\Users\\USER\\Desktop\\SCORES_PY && python scoring_bank.py" ,env_tSystem_1);

globalMap.remove("tSystem_1_OUTPUT");
globalMap.remove("tSystem_1_ERROROUTPUT");

Thread normal_tSystem_1 = new Thread() {
	public void run() {
		try {
			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(ps_tSystem_1.getInputStream()));
			String line = "";
			try {
				while((line = reader.readLine()) != null) {
					
						log.debug("tSystem_1 - Sending the standard output to the console.");
					
					System.out.println(line);
				}
			} finally {
				reader.close();
			}
		} catch(java.io.IOException ioe) {
globalMap.put("tSystem_1_ERROR_MESSAGE",ioe.getMessage());
			
				log.error("tSystem_1 - "  + ioe.getMessage());
			
			ioe.printStackTrace();
		}
	}
};
	log.info("tSystem_1 - Executing the command.");
	log.info("tSystem_1 - Command to execute: '" +  "cmd /c cd /d C:\\Users\\USER\\Desktop\\SCORES_PY && python scoring_bank.py"  + "'.");
normal_tSystem_1.start();
	log.info("tSystem_1 - The command has been executed successfully.");

Thread error_tSystem_1 = new Thread() {
	public void run() {
		try {
			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(ps_tSystem_1.getErrorStream()));
			String line = "";
			try {
				while((line = reader.readLine()) != null) {
					
						log.debug("tSystem_1 - Sending the error output to the console.");
					
					System.err.println(line);
				}
			} finally {
				reader.close();
			}
		} catch(java.io.IOException ioe) {
globalMap.put("tSystem_1_ERROR_MESSAGE",ioe.getMessage());
			
				log.error("tSystem_1 - "  + ioe.getMessage());
			
			ioe.printStackTrace();
		}
	}
};
error_tSystem_1.start();
if(ps_tSystem_1.getOutputStream()!=null){
    ps_tSystem_1.getOutputStream().close();
}
ps_tSystem_1.waitFor();
normal_tSystem_1.join();
error_tSystem_1.join();


 



		

/**
 * [tSystem_1 begin ] stop
 */

	
	/**
	 * [tSystem_1 main ] start
	 */

	

	
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		


 


	tos_count_tSystem_1++;

		

/**
 * [tSystem_1 main ] stop
 */

	
	/**
	 * [tSystem_1 process_data_begin ] start
	 */

	

	
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		

 



		

/**
 * [tSystem_1 process_data_begin ] stop
 */

	
	/**
	 * [tSystem_1 process_data_end ] start
	 */

	

	
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		

 



		

/**
 * [tSystem_1 process_data_end ] stop
 */

	
	/**
	 * [tSystem_1 end ] start
	 */

	

	
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		

globalMap.put("tSystem_1_EXIT_VALUE", ps_tSystem_1.exitValue());

 
                if(log.isDebugEnabled())
            log.debug("tSystem_1 - "  + ("Done.") );

ok_Hash.put("tSystem_1", true);
end_Hash.put("tSystem_1", System.currentTimeMillis());




		

/**
 * [tSystem_1 end ] stop
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
	 * [tSystem_1 finally ] start
	 */

	

	
	
	s(currentComponent="tSystem_1");
	
			
			
	
			cLabel="tPy";
		

 



		

/**
 * [tSystem_1 finally ] stop
 */

				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tSystem_1_SUBPROCESS_STATE", 1);
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
    public String contextStr = "Default";
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
        final JOB_03_carga_score JOB_03_carga_scoreClass = new JOB_03_carga_score();
        int exitCode = JOB_03_carga_scoreClass.runJobInTOS(args);
	        if(exitCode==0){
		        log.info("TalendJob: 'JOB_03_carga_score' - Done.");
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
	         

            log.info("TalendJob: 'JOB_03_carga_score' - Start.");

            java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
            for(Object jobInfoKey: jobInfoKeys) {
                org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
            }
            org.slf4j.MDC.put("_pid", pid);
            org.slf4j.MDC.put("_rootPid", rootPid);
            org.slf4j.MDC.put("_fatherPid", fatherPid);
            org.slf4j.MDC.put("_projectName", projectName);
            org.slf4j.MDC.put("_startTimestamp",java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC ).format( java.time.format.DateTimeFormatter.ISO_INSTANT ));
            org.slf4j.MDC.put("_jobRepositoryId","_3kouQNlfEfG8YfyhiAOB-A");
            org.slf4j.MDC.put("_compiledAtTimestamp","2026-11-08T01:32:20.372475300Z");

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
            java.io.InputStream inContext = JOB_03_carga_score.class.getClassLoader().getResourceAsStream("g3_uci_bank/job_03_carga_score_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = JOB_03_carga_score.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
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
            log.info("TalendJob: 'JOB_03_carga_score' - Started.");
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
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : JOB_03_carga_score");
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
        log.info("TalendJob: 'JOB_03_carga_score' - Finished - status: " + status + " returnCode: " + returnCode );

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
 *     77370 characters generated by Qlik Talend Cloud Enterprise Edition 
 *     on the 7 de noviembre de 2026, 20:32:20 ECT
 ************************************************************************************************/