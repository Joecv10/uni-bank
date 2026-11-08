
package g3_uci_bank.job_00_orquestador_uci_bank_0_1;

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
 * Job: JOB_00_orquestador_UCI_BANK Purpose: Job que orquesta los demás subjobs
 * que cargan la fuente bank-full.csv<br>
 * Description: Carga de fuente UCI bank-full.csv y procesos de staging quality
 * y modelado <br>
 * 
 * @author Myer, Jav
 * @version 8.0.1.20261022_0848-patch
 * @status
 */
public class JOB_00_orquestador_UCI_BANK implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "JOB_00_orquestador_UCI_BANK.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(JOB_00_orquestador_UCI_BANK.class);

	static {
		System.setProperty("talend.component.record.nullable.check", "true");
		String javaUtilLoggingConfigFile = System.getProperty("java.util.logging.config.file");
		if (javaUtilLoggingConfigFile == null) {
			setupDefaultJavaUtilLogging();
		}
	}

	/**
	 * This class replaces the default {@code System.err} stream used by Java Util
	 * Logging (JUL). You can use your own configuration through the
	 * {@code java.util.logging.config.file} system property, enabling you to
	 * specify an external logging configuration file for tailored logging setup.
	 */
	public static class StandardConsoleHandler extends java.util.logging.StreamHandler {
		public StandardConsoleHandler() {
			// Set System.out as default log output stream
			super(System.out, new java.util.logging.SimpleFormatter());
		}

		/**
		 * Publish a {@code LogRecord}. The logging request was made initially to a
		 * {@code Logger} object, which initialized the {@code LogRecord} and forwarded
		 * it here.
		 *
		 * @param record description of the log event. A null record is silently ignored
		 *               and is not published
		 */
		@Override
		public void publish(java.util.logging.LogRecord record) {
			super.publish(record);
			flush();
		}

		/**
		 * Override {@code StreamHandler.close} to do a flush but not to close the
		 * output stream. That is, we do <b>not</b> close {@code System.out}.
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

	// volatile is needed for OSGi: the thread that calls the blueprint destroy
	// method is not the same that sets the values
	public static volatile String taskExecutionId = null;
	public static volatile String jobExecutionId = null;
	public static volatile boolean sealCounters = false;

	public static final java.util.Set<Thread> threadList = java.util.Collections
			.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
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

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

			if (DB_HOST != null) {

				this.setProperty("DB_HOST", DB_HOST.toString());

			}

			if (DB_NAME != null) {

				this.setProperty("DB_NAME", DB_NAME.toString());

			}

			if (DB_PASSWORD != null) {

				this.setProperty("DB_PASSWORD", DB_PASSWORD.toString());

			}

			if (DB_PORT != null) {

				this.setProperty("DB_PORT", DB_PORT.toString());

			}

			if (DB_USER != null) {

				this.setProperty("DB_USER", DB_USER.toString());

			}

			if (FILE_UCI_BANK != null) {

				this.setProperty("FILE_UCI_BANK", FILE_UCI_BANK.toString());

			}

			if (LOAD_SOURCE_TAG != null) {

				this.setProperty("LOAD_SOURCE_TAG", LOAD_SOURCE_TAG.toString());

			}

			if (PIPELINE_NAME != null) {

				this.setProperty("PIPELINE_NAME", PIPELINE_NAME.toString());

			}

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

		public String DB_HOST;

		public String getDB_HOST() {
			return this.DB_HOST;
		}

		public String DB_NAME;

		public String getDB_NAME() {
			return this.DB_NAME;
		}

		public String DB_PASSWORD;

		public String getDB_PASSWORD() {
			return this.DB_PASSWORD;
		}

		public String DB_PORT;

		public String getDB_PORT() {
			return this.DB_PORT;
		}

		public String DB_USER;

		public String getDB_USER() {
			return this.DB_USER;
		}

		public String FILE_UCI_BANK;

		public String getFILE_UCI_BANK() {
			return this.FILE_UCI_BANK;
		}

		public String LOAD_SOURCE_TAG;

		public String getLOAD_SOURCE_TAG() {
			return this.LOAD_SOURCE_TAG;
		}

		public String PIPELINE_NAME;

		public String getPIPELINE_NAME() {
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
	private final String jobName = "JOB_00_orquestador_UCI_BANK";
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

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_8fFtkNiEEfGYDKHwOCnAyQ", "0.1");
	private org.talend.job.audit.JobAuditLogger runtime_lineage_logger_talendJobLog = null;
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
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
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
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
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
				}
			}
			if (!(e instanceof TalendException)) {
				JOB_00_orquestador_UCI_BANK.this.exception = e;
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(JOB_00_orquestador_UCI_BANK.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
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

	public void tPostjob_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tPostjob_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBClose_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBClose_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tPrejob_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tPrejob_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBConnection_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBConnection_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_14_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_12_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBRow_13_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBRow_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tPostjob_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBClose_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tPrejob_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBConnection_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBRow_8_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tPostjob_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tPostjob_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tPostjob_2", "aJLtfh_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tPostjob_2 begin ] start
				 */

				sh("tPostjob_2");

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				int tos_count_tPostjob_2 = 0;

				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tPostjob_2 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter() throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

							} catch (java.lang.Exception e_tPostjob_2) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tPostjob_2", "tPostjob",
							new ParameterUtil_tPostjob_2().getParameter());
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tPostjob_2", "tPost_jpb_add", "tPostjob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tPostjob_2 begin ] stop
				 */

				/**
				 * [tPostjob_2 main ] start
				 */

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				tos_count_tPostjob_2++;

				/**
				 * [tPostjob_2 main ] stop
				 */

				/**
				 * [tPostjob_2 process_data_begin ] start
				 */

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				/**
				 * [tPostjob_2 process_data_begin ] stop
				 */

				/**
				 * [tPostjob_2 process_data_end ] start
				 */

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				/**
				 * [tPostjob_2 process_data_end ] stop
				 */

				/**
				 * [tPostjob_2 end ] start
				 */

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				ok_Hash.put("tPostjob_2", true);
				end_Hash.put("tPostjob_2", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk6", 0, "ok");
				}
				tDBClose_2Process(globalMap);

				/**
				 * [tPostjob_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tPostjob_2 finally ] start
				 */

				s(currentComponent = "tPostjob_2");

				cLabel = "tPost_jpb_add";

				/**
				 * [tPostjob_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tPostjob_2_SUBPROCESS_STATE", 1);
	}

	public void tDBClose_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBClose_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBClose_2", "z6CZrG_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBClose_2 begin ] start
				 */

				sh("tDBClose_2");

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				int tos_count_tDBClose_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBClose_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBClose_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBClose_2 = new StringBuilder();
							log4jParamters_tDBClose_2.append("Parameters:");
							log4jParamters_tDBClose_2.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBClose_2.append(" | ");
							log4jParamters_tDBClose_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlClose");
							log4jParamters_tDBClose_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBClose_2 - " + (log4jParamters_tDBClose_2));
						}
					}
					new BytesLimit65535_tDBClose_2().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBClose_2 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter() throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlClose"));

							} catch (java.lang.Exception e_tDBClose_2) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBClose_2", "tMysqlClose",
							new ParameterUtil_tDBClose_2().getParameter());
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBClose_2", "tDBClose_end_add", "tMysqlClose");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tDBClose_2 begin ] stop
				 */

				/**
				 * [tDBClose_2 main ] start
				 */

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				java.sql.Connection conn_tDBClose_2 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (conn_tDBClose_2 != null && !conn_tDBClose_2.isClosed()) {

					log.debug("tDBClose_2 - Closing the connection 'tDBConnection_2' to the database.");

					conn_tDBClose_2.close();

					if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_tDBConnection_2"))
							&& routines.system.BundleUtils.inOSGi()) {
						Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").getMethod("checkedShutdown")
								.invoke(null, (Object[]) null);
					}

					log.debug("tDBClose_2 - Connection 'tDBConnection_2' to the database closed.");

				}

				tos_count_tDBClose_2++;

				/**
				 * [tDBClose_2 main ] stop
				 */

				/**
				 * [tDBClose_2 process_data_begin ] start
				 */

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				/**
				 * [tDBClose_2 process_data_begin ] stop
				 */

				/**
				 * [tDBClose_2 process_data_end ] start
				 */

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				/**
				 * [tDBClose_2 process_data_end ] stop
				 */

				/**
				 * [tDBClose_2 end ] start
				 */

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				if (log.isDebugEnabled())
					log.debug("tDBClose_2 - " + ("Done."));

				ok_Hash.put("tDBClose_2", true);
				end_Hash.put("tDBClose_2", System.currentTimeMillis());

				/**
				 * [tDBClose_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBClose_2 finally ] start
				 */

				s(currentComponent = "tDBClose_2");

				cLabel = "tDBClose_end_add";

				/**
				 * [tDBClose_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBClose_2_SUBPROCESS_STATE", 1);
	}

	public void tPrejob_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tPrejob_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tPrejob_2", "ycHG5i_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tPrejob_2 begin ] start
				 */

				sh("tPrejob_2");

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				int tos_count_tPrejob_2 = 0;

				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tPrejob_2 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter() throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

							} catch (java.lang.Exception e_tPrejob_2) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tPrejob_2", "tPrejob",
							new ParameterUtil_tPrejob_2().getParameter());
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tPrejob_2", "tPreJob_start_add", "tPrejob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tPrejob_2 begin ] stop
				 */

				/**
				 * [tPrejob_2 main ] start
				 */

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				tos_count_tPrejob_2++;

				/**
				 * [tPrejob_2 main ] stop
				 */

				/**
				 * [tPrejob_2 process_data_begin ] start
				 */

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				/**
				 * [tPrejob_2 process_data_begin ] stop
				 */

				/**
				 * [tPrejob_2 process_data_end ] start
				 */

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				/**
				 * [tPrejob_2 process_data_end ] stop
				 */

				/**
				 * [tPrejob_2 end ] start
				 */

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				ok_Hash.put("tPrejob_2", true);
				end_Hash.put("tPrejob_2", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk5", 0, "ok");
				}
				tDBConnection_2Process(globalMap);

				/**
				 * [tPrejob_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tPrejob_2 finally ] start
				 */

				s(currentComponent = "tPrejob_2");

				cLabel = "tPreJob_start_add";

				/**
				 * [tPrejob_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tPrejob_2_SUBPROCESS_STATE", 1);
	}

	public void tDBConnection_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBConnection_2", "uL7ba7_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tDBConnection_2 begin ] start
				 */

				sh("tDBConnection_2");

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				int tos_count_tDBConnection_2 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBConnection_2 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBConnection_2 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBConnection_2 = new StringBuilder();
							log4jParamters_tDBConnection_2.append("Parameters:");
							log4jParamters_tDBConnection_2.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("HOST" + " = " + "context.DB_HOST");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PORT" + " = " + "context.DB_PORT");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("DBNAME" + " = " + "context.DB_NAME");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("USER" + " = " + "context.DB_USER");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("PASS" + " = "
									+ String.valueOf(
											routines.system.PasswordEncryptUtil.encryptPassword("context.DB_PASSWORD"))
											.substring(0, 4)
									+ "...");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("USE_SHARED_CONNECTION" + " = " + "true");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("SHARED_CONNECTION_NAME" + " = " + "\"conn_mysql\"");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("AUTO_COMMIT" + " = " + "true");
							log4jParamters_tDBConnection_2.append(" | ");
							log4jParamters_tDBConnection_2.append("UNIFIED_COMPONENTS" + " = " + "tMysqlConnection");
							log4jParamters_tDBConnection_2.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBConnection_2 - " + (log4jParamters_tDBConnection_2));
						}
					}
					new BytesLimit65535_tDBConnection_2().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBConnection_2 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter() throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("DB_VERSION", String.valueOf("MYSQL_8"));
								component_parameters.put("HOST", String.valueOf(context.DB_HOST));
								component_parameters.put("PORT", String.valueOf(context.DB_PORT));
								component_parameters.put("DBNAME", String.valueOf(context.DB_NAME));
								component_parameters.put("PROPERTIES", String.valueOf(
										"noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true"));
								component_parameters.put("USER", String.valueOf(context.DB_USER));
								component_parameters.put("USE_SHARED_CONNECTION", String.valueOf("true"));
								component_parameters.put("SHARED_CONNECTION_NAME", String.valueOf("conn_mysql"));
								component_parameters.put("AUTO_COMMIT", String.valueOf("true"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlConnection"));

							} catch (java.lang.Exception e_tDBConnection_2) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBConnection_2", "tMysqlConnection",
							new ParameterUtil_tDBConnection_2().getParameter());
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBConnection_2", "tDBConn_mysql_add", "tMysqlConnection");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String properties_tDBConnection_2 = "noDatetimeStringSync=true&serverTimezone=America/Guayaquil&allowMultiQueries=true";
				if (properties_tDBConnection_2 == null || properties_tDBConnection_2.trim().length() == 0) {
					properties_tDBConnection_2 = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_tDBConnection_2.contains("rewriteBatchedStatements=")) {
						properties_tDBConnection_2 += "&rewriteBatchedStatements=true";
					}

					if (!properties_tDBConnection_2.contains("allowLoadLocalInfile=")) {
						properties_tDBConnection_2 += "&allowLoadLocalInfile=true";
					}
				}

				String url_tDBConnection_2 = "jdbc:mysql://" + context.DB_HOST + ":" + context.DB_PORT + "/"
						+ context.DB_NAME + "?" + properties_tDBConnection_2;
				String dbUser_tDBConnection_2 = context.DB_USER;

				final String decryptedPassword_tDBConnection_2 = java.util.Optional.ofNullable(context.DB_PASSWORD)
						.orElse("");
				String dbPwd_tDBConnection_2 = decryptedPassword_tDBConnection_2;

				java.sql.Connection conn_tDBConnection_2 = null;

				SharedDBConnectionLog4j.initLogger(log.getName(), "tDBConnection_2");
				String sharedConnectionName_tDBConnection_2 = "conn_mysql";
				conn_tDBConnection_2 = SharedDBConnectionLog4j.getDBConnection("com.mysql.cj.jdbc.Driver",
						url_tDBConnection_2, dbUser_tDBConnection_2, dbPwd_tDBConnection_2,
						sharedConnectionName_tDBConnection_2);
				globalMap.put("conn_tDBConnection_2", conn_tDBConnection_2);
				if (null != conn_tDBConnection_2) {

					log.debug("tDBConnection_2 - Connection is set auto commit to 'true'.");
					conn_tDBConnection_2.setAutoCommit(true);
				}

				globalMap.put("db_tDBConnection_2", context.DB_NAME);

				/**
				 * [tDBConnection_2 begin ] stop
				 */

				/**
				 * [tDBConnection_2 main ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				tos_count_tDBConnection_2++;

				/**
				 * [tDBConnection_2 main ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_begin ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				/**
				 * [tDBConnection_2 process_data_begin ] stop
				 */

				/**
				 * [tDBConnection_2 process_data_end ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				/**
				 * [tDBConnection_2 process_data_end ] stop
				 */

				/**
				 * [tDBConnection_2 end ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				if (log.isDebugEnabled())
					log.debug("tDBConnection_2 - " + ("Done."));

				ok_Hash.put("tDBConnection_2", true);
				end_Hash.put("tDBConnection_2", System.currentTimeMillis());

				/**
				 * [tDBConnection_2 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tDBConnection_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tDBRow_8Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBConnection_2 finally ] start
				 */

				s(currentComponent = "tDBConnection_2");

				cLabel = "tDBConn_mysql_add";

				/**
				 * [tDBConnection_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBConnection_2_SUBPROCESS_STATE", 1);
	}

	public static class row13Struct implements routines.system.IPersistableRow<row13Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row13Struct other) {

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

	public static class row12Struct implements routines.system.IPersistableRow<row12Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row12Struct other) {

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
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

	public static class row10Struct implements routines.system.IPersistableRow<row10Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row10Struct other) {

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
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

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

	public static class row16Struct implements routines.system.IPersistableRow<row16Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row16Struct other) {

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

	public static class row14Struct implements routines.system.IPersistableRow<row14Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row14Struct other) {

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

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];
		static byte[] commonByteArray_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK = new byte[0];

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_G3_UCI_BANK_JOB_00_orquestador_UCI_BANK) {

				try {

					int length = 0;

				}

				finally {
				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

			}

			finally {
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

			}

			finally {
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

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

	public void tDBRow_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBRow_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBRow_8", "mAxdyo_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();
				row14Struct row14 = new row14Struct();
				row16Struct row16 = new row16Struct();
				row3Struct row3 = new row3Struct();
				row10Struct row10 = new row10Struct();
				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				row12Struct row12 = new row12Struct();
				row13Struct row13 = new row13Struct();

				/**
				 * [tDBRow_13 begin ] start
				 */

				sh("tDBRow_13");

				s(currentComponent = "tDBRow_13");

				cLabel = "tDBR_fact_calls_aine";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row13");

				int tos_count_tDBRow_13 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_13 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_13 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_13 = new StringBuilder();
							log4jParamters_tDBRow_13.append("Parameters:");
							log4jParamters_tDBRow_13.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("QUERY" + " = "
									+ "\"INSERT INTO mart_fact_calls_g3 (      source_call_id,      client_key,      campaign_key,      duration,      subscribed,      score,      prediccion,      top20,      decil,      scored_at,      load_ts,      load_source  )  SELECT      b.source_call_id,      dc.client_key,      dca.campaign_key,      b.duration,      b.y,      s.score,      s.prediccion,      s.top20,      s.decil,      s.scored_at,      '\" + TalendDate.formatDate(\"yyyy-MM-dd HH:mm:ss\", TalendDate.getCurrentDate()) + \"',      'G3_UCI_BANK'  FROM stg_bank_OK_g3 b    INNER JOIN mart_dim_client_g3 dc      ON dc.age <=> b.age      AND dc.job <=> b.job      AND dc.marital <=> b.marital      AND dc.education <=> b.education      AND dc.has_default <=> b.has_default      AND dc.housing <=> b.housing      AND dc.loan <=> b.loan    INNER JOIN mart_dim_campaign_g3 dca      ON dca.contact <=> b.contact      AND dca.month <=> b.month      AND dca.day_of_week <=> b.day_of_week      AND dca.campaign <=> b.campaign      AND dca.pdays <=> b.pdays      AND dca.previous <=> b.previous      AND dca.poutcome <=> b.poutcome      AND dca.emp_var_rate <=> b.emp_var_rate      AND dca.cons_price_idx <=> b.cons_price_idx      AND dca.cons_conf_idx <=> b.cons_conf_idx      AND dca.euribor3m <=> b.euribor3m      AND dca.nr_employed <=> b.nr_employed    LEFT JOIN stg_bank_OK_score_g3 s      ON s.source_call_id = b.source_call_id;\"");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_13.append(" | ");
							log4jParamters_tDBRow_13.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_13.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_13 - " + (log4jParamters_tDBRow_13));
						}
					}
					new BytesLimit65535_tDBRow_13().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_13 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row13Struct row13) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"INSERT INTO mart_fact_calls_g3 (\n    source_call_id,\n    client_key,\n    campaign_key,\n    duration,\n    subscribe"
												+ "d,\n    score,\n    prediccion,\n    top20,\n    decil,\n    scored_at,\n    load_ts,\n    load_source\n)\nSELECT\n    b"
												+ ".source_call_id,\n    dc.client_key,\n    dca.campaign_key,\n    b.duration,\n    b.y,\n    s.score,\n    s.prediccion,"
												+ "\n    s.top20,\n    s.decil,\n    s.scored_at,\n    '")
										.toString()
										+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate())
										+ new StringBuilder().append(
												"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\n\nINNER JOIN mart_dim_client_g3 dc\n    ON dc.age <=> b.age\n    AND dc.j"
														+ "ob <=> b.job\n    AND dc.marital <=> b.marital\n    AND dc.education <=> b.education\n    AND dc.has_default <=> b.has_d"
														+ "efault\n    AND dc.housing <=> b.housing\n    AND dc.loan <=> b.loan\n\nINNER JOIN mart_dim_campaign_g3 dca\n    ON dca."
														+ "contact <=> b.contact\n    AND dca.month <=> b.month\n    AND dca.day_of_week <=> b.day_of_week\n    AND dca.campaign <="
														+ "> b.campaign\n    AND dca.pdays <=> b.pdays\n    AND dca.previous <=> b.previous\n    AND dca.poutcome <=> b.poutcome\n "
														+ "   AND dca.emp_var_rate <=> b.emp_var_rate\n    AND dca.cons_price_idx <=> b.cons_price_idx\n    AND dca.cons_conf_idx <"
														+ "=> b.cons_conf_idx\n    AND dca.euribor3m <=> b.euribor3m\n    AND dca.nr_employed <=> b.nr_employed\n\nLEFT JOIN stg_ba"
														+ "nk_OK_score_g3 s\n    ON s.source_call_id = b.source_call_id;")
												.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_13) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_13", "tMysqlRow",
							new ParameterUtil_tDBRow_13().getParameter(row13));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_13", "tDBR_fact_calls_aine", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_13 = null;
				String query_tDBRow_13 = "";
				boolean whetherReject_tDBRow_13 = false;
				int count_tDBRow_13 = 0;
				conn_tDBRow_13 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_13 != null) {
					if (conn_tDBRow_13.getMetaData() != null) {

						log.debug("tDBRow_13 - Uses an existing connection with username '"
								+ conn_tDBRow_13.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_13.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_13", conn_tDBRow_13);
				java.sql.Statement stmt_tDBRow_13 = conn_tDBRow_13.createStatement();
				resourceMap.put("stmt_tDBRow_13", stmt_tDBRow_13);

				/**
				 * [tDBRow_13 begin ] stop
				 */

				/**
				 * [tDBRow_10 begin ] start
				 */

				sh("tDBRow_10");

				s(currentComponent = "tDBRow_10");

				cLabel = "tDBR_dim_campaign_aine";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row12");

				int tos_count_tDBRow_10 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_10 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_10 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_10 = new StringBuilder();
							log4jParamters_tDBRow_10.append("Parameters:");
							log4jParamters_tDBRow_10.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("QUERY" + " = "
									+ "\"INSERT INTO mart_dim_campaign_g3 (      contact,      month,      day_of_week,      campaign,      pdays,      previous,      poutcome,      emp_var_rate,      cons_price_idx,      cons_conf_idx,      euribor3m,      nr_employed,  load_ts,  load_source  )  SELECT DISTINCT      b.contact,      b.month,      b.day_of_week,      b.campaign,      b.pdays,      b.previous,      b.poutcome,      b.emp_var_rate,      b.cons_price_idx,      b.cons_conf_idx,      b.euribor3m,      b.nr_employed,      '\" + TalendDate.formatDate(\"yyyy-MM-dd HH:mm:ss\", TalendDate.getCurrentDate()) + \"',      'G3_UCI_BANK'  FROM stg_bank_OK_g3 b  WHERE NOT EXISTS (      SELECT 1      FROM mart_dim_campaign_g3 d      WHERE d.contact <=> b.contact        AND d.month <=> b.month        AND d.day_of_week <=> b.day_of_week        AND d.campaign <=> b.campaign        AND d.pdays <=> b.pdays        AND d.previous <=> b.previous        AND d.poutcome <=> b.poutcome        AND d.emp_var_rate <=> b.emp_var_rate        AND d.cons_price_idx <=> b.cons_price_idx        AND d.cons_conf_idx <=> b.cons_conf_idx        AND d.euribor3m <=> b.euribor3m        AND d.nr_employed <=> b.nr_employed  );\"");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_10.append(" | ");
							log4jParamters_tDBRow_10.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_10.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_10 - " + (log4jParamters_tDBRow_10));
						}
					}
					new BytesLimit65535_tDBRow_10().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_10 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row12Struct row12) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"INSERT INTO mart_dim_campaign_g3 (\n    contact,\n    month,\n    day_of_week,\n    campaign,\n    pdays,\n    previous"
												+ ",\n    poutcome,\n    emp_var_rate,\n    cons_price_idx,\n    cons_conf_idx,\n    euribor3m,\n    nr_employed,\nload_ts,"
												+ "\nload_source\n)\nSELECT DISTINCT\n    b.contact,\n    b.month,\n    b.day_of_week,\n    b.campaign,\n    b.pdays,\n    "
												+ "b.previous,\n    b.poutcome,\n    b.emp_var_rate,\n    b.cons_price_idx,\n    b.cons_conf_idx,\n    b.euribor3m,\n    b."
												+ "nr_employed,\n    '")
										.toString()
										+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate())
										+ new StringBuilder().append(
												"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\nWHERE NOT EXISTS (\n    SELECT 1\n    FROM mart_dim_campaign_g3 d\n    WH"
														+ "ERE d.contact <=> b.contact\n      AND d.month <=> b.month\n      AND d.day_of_week <=> b.day_of_week\n      AND d.campa"
														+ "ign <=> b.campaign\n      AND d.pdays <=> b.pdays\n      AND d.previous <=> b.previous\n      AND d.poutcome <=> b.poutc"
														+ "ome\n      AND d.emp_var_rate <=> b.emp_var_rate\n      AND d.cons_price_idx <=> b.cons_price_idx\n      AND d.cons_conf"
														+ "_idx <=> b.cons_conf_idx\n      AND d.euribor3m <=> b.euribor3m\n      AND d.nr_employed <=> b.nr_employed\n);")
												.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_10) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_10", "tMysqlRow",
							new ParameterUtil_tDBRow_10().getParameter(row12));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_10", "tDBR_dim_campaign_aine", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_10 = null;
				String query_tDBRow_10 = "";
				boolean whetherReject_tDBRow_10 = false;
				int count_tDBRow_10 = 0;
				conn_tDBRow_10 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_10 != null) {
					if (conn_tDBRow_10.getMetaData() != null) {

						log.debug("tDBRow_10 - Uses an existing connection with username '"
								+ conn_tDBRow_10.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_10.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_10", conn_tDBRow_10);
				java.sql.Statement stmt_tDBRow_10 = conn_tDBRow_10.createStatement();
				resourceMap.put("stmt_tDBRow_10", stmt_tDBRow_10);

				/**
				 * [tDBRow_10 begin ] stop
				 */

				/**
				 * [tDBRow_9 begin ] start
				 */

				sh("tDBRow_9");

				s(currentComponent = "tDBRow_9");

				cLabel = "tDBR_dim_cliente_aine";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tDBRow_9 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_9 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_9 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_9 = new StringBuilder();
							log4jParamters_tDBRow_9.append("Parameters:");
							log4jParamters_tDBRow_9.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("QUERY" + " = "
									+ "\"INSERT INTO mart_dim_client_g3 (      age,      job,      marital,      education,      has_default,      housing,      loan,      load_ts,      load_source  )  SELECT DISTINCT      b.age,      b.job,      b.marital,      b.education,      b.has_default,      b.housing,      b.loan,      '\" + TalendDate.formatDate(\"yyyy-MM-dd HH:mm:ss\", TalendDate.getCurrentDate()) + \"',      'G3_UCI_BANK'  FROM stg_bank_OK_g3 b  WHERE NOT EXISTS (      SELECT 1      FROM mart_dim_client_g3 d      WHERE d.age <=> b.age        AND d.job <=> b.job        AND d.marital <=> b.marital        AND d.education <=> b.education        AND d.has_default <=> b.has_default        AND d.housing <=> b.housing        AND d.loan <=> b.loan  );\"");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_9.append(" | ");
							log4jParamters_tDBRow_9.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_9.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_9 - " + (log4jParamters_tDBRow_9));
						}
					}
					new BytesLimit65535_tDBRow_9().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_9 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row2Struct row2) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"INSERT INTO mart_dim_client_g3 (\n    age,\n    job,\n    marital,\n    education,\n    has_default,\n    housing,\n   "
												+ " loan,\n    load_ts,\n    load_source\n)\nSELECT DISTINCT\n    b.age,\n    b.job,\n    b.marital,\n    b.education,\n   "
												+ " b.has_default,\n    b.housing,\n    b.loan,\n    '")
										.toString()
										+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate())
										+ new StringBuilder().append(
												"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\nWHERE NOT EXISTS (\n    SELECT 1\n    FROM mart_dim_client_g3 d\n    WHER"
														+ "E d.age <=> b.age\n      AND d.job <=> b.job\n      AND d.marital <=> b.marital\n      AND d.education <=> b.education\n"
														+ "      AND d.has_default <=> b.has_default\n      AND d.housing <=> b.housing\n      AND d.loan <=> b.loan\n);")
												.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_9) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_9", "tMysqlRow",
							new ParameterUtil_tDBRow_9().getParameter(row2));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_9", "tDBR_dim_cliente_aine", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_9 = null;
				String query_tDBRow_9 = "";
				boolean whetherReject_tDBRow_9 = false;
				int count_tDBRow_9 = 0;
				conn_tDBRow_9 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_9 != null) {
					if (conn_tDBRow_9.getMetaData() != null) {

						log.debug("tDBRow_9 - Uses an existing connection with username '"
								+ conn_tDBRow_9.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_9.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_9", conn_tDBRow_9);
				java.sql.Statement stmt_tDBRow_9 = conn_tDBRow_9.createStatement();
				resourceMap.put("stmt_tDBRow_9", stmt_tDBRow_9);

				/**
				 * [tDBRow_9 begin ] stop
				 */

				/**
				 * [tRunJob_1 begin ] start
				 */

				sh("tRunJob_1");

				s(currentComponent = "tRunJob_1");

				cLabel = "JOB_03_carga_score";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tRunJob_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_1 = new StringBuilder();
							log4jParamters_tRunJob_1.append("Parameters:");
							log4jParamters_tRunJob_1.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PROCESS" + " = " + "JOB_03_carga_score");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_1.append(" | ");
							log4jParamters_tRunJob_1.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_1 - " + (log4jParamters_tRunJob_1));
						}
					}
					new BytesLimit65535_tRunJob_1().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tRunJob_1 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row1Struct row1) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_DYNAMIC_JOB", String.valueOf("false"));
								component_parameters.put("PROCESS", String.valueOf("JOB_03_carga_score"));
								component_parameters.put("USE_INDEPENDENT_PROCESS", String.valueOf("false"));
								component_parameters.put("DIE_ON_CHILD_ERROR", String.valueOf("true"));
								component_parameters.put("TRANSMIT_WHOLE_CONTEXT", String.valueOf("false"));
								component_parameters.put("CONTEXTPARAMS", String.valueOf("[]"));
								component_parameters.put("PROPAGATE_CHILD_RESULT", String.valueOf("false"));
								component_parameters.put("PRINT_PARAMETER", String.valueOf("false"));
								component_parameters.put("USE_DYNAMIC_CONTEXT", String.valueOf("false"));
								component_parameters.put("USE_BASE64", String.valueOf("true"));

							} catch (java.lang.Exception e_tRunJob_1) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tRunJob_1", "tRunJob",
							new ParameterUtil_tRunJob_1().getParameter(row1));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_1", "JOB_03_carga_score", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_1 begin ] stop
				 */

				/**
				 * [tRunJob_4 begin ] start
				 */

				sh("tRunJob_4");

				s(currentComponent = "tRunJob_4");

				cLabel = "JOB_02_quality";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row10");

				int tos_count_tRunJob_4 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_4 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_4 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_4 = new StringBuilder();
							log4jParamters_tRunJob_4.append("Parameters:");
							log4jParamters_tRunJob_4.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("PROCESS" + " = " + "JOB_02_quality");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_4.append(" | ");
							log4jParamters_tRunJob_4.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_4.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_4 - " + (log4jParamters_tRunJob_4));
						}
					}
					new BytesLimit65535_tRunJob_4().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tRunJob_4 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row10Struct row10) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_DYNAMIC_JOB", String.valueOf("false"));
								component_parameters.put("PROCESS", String.valueOf("JOB_02_quality"));
								component_parameters.put("USE_INDEPENDENT_PROCESS", String.valueOf("false"));
								component_parameters.put("DIE_ON_CHILD_ERROR", String.valueOf("true"));
								component_parameters.put("TRANSMIT_WHOLE_CONTEXT", String.valueOf("false"));
								component_parameters.put("CONTEXTPARAMS", String.valueOf("[]"));
								component_parameters.put("PROPAGATE_CHILD_RESULT", String.valueOf("false"));
								component_parameters.put("PRINT_PARAMETER", String.valueOf("false"));
								component_parameters.put("USE_DYNAMIC_CONTEXT", String.valueOf("false"));
								component_parameters.put("USE_BASE64", String.valueOf("true"));

							} catch (java.lang.Exception e_tRunJob_4) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tRunJob_4", "tRunJob",
							new ParameterUtil_tRunJob_4().getParameter(row10));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_4", "JOB_02_quality", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_4 begin ] stop
				 */

				/**
				 * [tRunJob_3 begin ] start
				 */

				sh("tRunJob_3");

				s(currentComponent = "tRunJob_3");

				cLabel = "tRJ_01_load_raw";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tRunJob_3 = 0;

				if (log.isDebugEnabled())
					log.debug("tRunJob_3 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRunJob_3 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRunJob_3 = new StringBuilder();
							log4jParamters_tRunJob_3.append("Parameters:");
							log4jParamters_tRunJob_3.append("USE_DYNAMIC_JOB" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PROCESS" + " = " + "JOB_01_load_staging");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_INDEPENDENT_PROCESS" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("DIE_ON_CHILD_ERROR" + " = " + "true");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("TRANSMIT_WHOLE_CONTEXT" + " = " + "true");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("CONTEXTPARAMS" + " = " + "[]");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PROPAGATE_CHILD_RESULT" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("PRINT_PARAMETER" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_DYNAMIC_CONTEXT" + " = " + "false");
							log4jParamters_tRunJob_3.append(" | ");
							log4jParamters_tRunJob_3.append("USE_BASE64" + " = " + "true");
							log4jParamters_tRunJob_3.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tRunJob_3 - " + (log4jParamters_tRunJob_3));
						}
					}
					new BytesLimit65535_tRunJob_3().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tRunJob_3 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row3Struct row3) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_DYNAMIC_JOB", String.valueOf("false"));
								component_parameters.put("PROCESS", String.valueOf("JOB_01_load_staging"));
								component_parameters.put("USE_INDEPENDENT_PROCESS", String.valueOf("false"));
								component_parameters.put("DIE_ON_CHILD_ERROR", String.valueOf("true"));
								component_parameters.put("TRANSMIT_WHOLE_CONTEXT", String.valueOf("true"));
								component_parameters.put("CONTEXTPARAMS", String.valueOf("[]"));
								component_parameters.put("PROPAGATE_CHILD_RESULT", String.valueOf("false"));
								component_parameters.put("PRINT_PARAMETER", String.valueOf("false"));
								component_parameters.put("USE_DYNAMIC_CONTEXT", String.valueOf("false"));
								component_parameters.put("USE_BASE64", String.valueOf("true"));

							} catch (java.lang.Exception e_tRunJob_3) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tRunJob_3", "tRunJob",
							new ParameterUtil_tRunJob_3().getParameter(row3));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tRunJob_3", "tRJ_01_load_raw", "tRunJob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [tRunJob_3 begin ] stop
				 */

				/**
				 * [tDBRow_12 begin ] start
				 */

				sh("tDBRow_12");

				s(currentComponent = "tDBRow_12");

				cLabel = "tDBR_CREATE_mart_fact_calls_g3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row16");

				int tos_count_tDBRow_12 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_12 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_12 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_12 = new StringBuilder();
							log4jParamters_tDBRow_12.append("Parameters:");
							log4jParamters_tDBRow_12.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("QUERY" + " = "
									+ "\"CREATE TABLE mart_fact_calls_g3 (      call_key BIGINT AUTO_INCREMENT PRIMARY KEY,      source_call_id BIGINT NOT NULL,      client_key INT NOT NULL,      campaign_key INT NOT NULL,      duration INT,      subscribed VARCHAR(10),        score DECIMAL(5,2),      prediccion TINYINT,      top20 TINYINT,      decil INT,      scored_at DATETIME,      load_ts DATETIME,      load_source VARCHAR(100)  );\"");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_12.append(" | ");
							log4jParamters_tDBRow_12.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_12.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_12 - " + (log4jParamters_tDBRow_12));
						}
					}
					new BytesLimit65535_tDBRow_12().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_12 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row16Struct row16) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"CREATE TABLE mart_fact_calls_g3 (\n    call_key BIGINT AUTO_INCREMENT PRIMARY KEY,\n    source_call_id BIGINT NOT NULL,"
												+ "\n    client_key INT NOT NULL,\n    campaign_key INT NOT NULL,\n    duration INT,\n    subscribed VARCHAR(10),\n\n    sc"
												+ "ore DECIMAL(5,2),\n    prediccion TINYINT,\n    top20 TINYINT,\n    decil INT,\n    scored_at DATETIME,\n    load_ts DAT"
												+ "ETIME,\n    load_source VARCHAR(100)\n);")
										.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_12) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_12", "tMysqlRow",
							new ParameterUtil_tDBRow_12().getParameter(row16));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_12", "tDBR_CREATE_mart_fact_calls_g3", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_12 = null;
				String query_tDBRow_12 = "";
				boolean whetherReject_tDBRow_12 = false;
				int count_tDBRow_12 = 0;
				conn_tDBRow_12 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_12 != null) {
					if (conn_tDBRow_12.getMetaData() != null) {

						log.debug("tDBRow_12 - Uses an existing connection with username '"
								+ conn_tDBRow_12.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_12.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_12", conn_tDBRow_12);
				java.sql.Statement stmt_tDBRow_12 = conn_tDBRow_12.createStatement();
				resourceMap.put("stmt_tDBRow_12", stmt_tDBRow_12);

				/**
				 * [tDBRow_12 begin ] stop
				 */

				/**
				 * [tDBRow_14 begin ] start
				 */

				sh("tDBRow_14");

				s(currentComponent = "tDBRow_14");

				cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row14");

				int tos_count_tDBRow_14 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_14 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_14 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_14 = new StringBuilder();
							log4jParamters_tDBRow_14.append("Parameters:");
							log4jParamters_tDBRow_14.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("QUERY" + " = "
									+ "\"CREATE TABLE IF NOT EXISTS mart_dim_campaign_g3 (      campaign_key INT AUTO_INCREMENT PRIMARY KEY,      contact VARCHAR(30),      month VARCHAR(15),      day_of_week VARCHAR(15),      campaign INT,      pdays INT,      previous INT,      poutcome VARCHAR(30),      emp_var_rate FLOAT(10,4),      cons_price_idx FLOAT(10,4),      cons_conf_idx DOUBLE(10,4),      euribor3m FLOAT(10,4),      nr_employed DOUBLE(12,4),      load_ts DATETIME,      load_source VARCHAR(100)  ) ENGINE=InnoDB;\"");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_14.append(" | ");
							log4jParamters_tDBRow_14.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_14.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_14 - " + (log4jParamters_tDBRow_14));
						}
					}
					new BytesLimit65535_tDBRow_14().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_14 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row14Struct row14) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"CREATE TABLE IF NOT EXISTS mart_dim_campaign_g3 (\n    campaign_key INT AUTO_INCREMENT PRIMARY KEY,\n    contact VARCHA"
												+ "R(30),\n    month VARCHAR(15),\n    day_of_week VARCHAR(15),\n    campaign INT,\n    pdays INT,\n    previous INT,\n    "
												+ "poutcome VARCHAR(30),\n    emp_var_rate FLOAT(10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n"
												+ "    euribor3m FLOAT(10,4),\n    nr_employed DOUBLE(12,4),\n    load_ts DATETIME,\n    load_source VARCHAR(100)\n) ENGINE"
												+ "=InnoDB;")
										.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_14) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_14", "tMysqlRow",
							new ParameterUtil_tDBRow_14().getParameter(row14));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_14", "tDBR_CREATE_mart_dim_campaign_g3", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_14 = null;
				String query_tDBRow_14 = "";
				boolean whetherReject_tDBRow_14 = false;
				int count_tDBRow_14 = 0;
				conn_tDBRow_14 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_14 != null) {
					if (conn_tDBRow_14.getMetaData() != null) {

						log.debug("tDBRow_14 - Uses an existing connection with username '"
								+ conn_tDBRow_14.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_14.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_14", conn_tDBRow_14);
				java.sql.Statement stmt_tDBRow_14 = conn_tDBRow_14.createStatement();
				resourceMap.put("stmt_tDBRow_14", stmt_tDBRow_14);

				/**
				 * [tDBRow_14 begin ] stop
				 */

				/**
				 * [tDBRow_11 begin ] start
				 */

				sh("tDBRow_11");

				s(currentComponent = "tDBRow_11");

				cLabel = "tDBR_CREATE_mart_dim_client_g3";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row4");

				int tos_count_tDBRow_11 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_11 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_11 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_11 = new StringBuilder();
							log4jParamters_tDBRow_11.append("Parameters:");
							log4jParamters_tDBRow_11.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("QUERY" + " = "
									+ "\"CREATE TABLE IF NOT EXISTS mart_dim_client_g3 (      client_key INT AUTO_INCREMENT PRIMARY KEY,      age INT,      job VARCHAR(50),      marital VARCHAR(30),      education VARCHAR(50),      has_default VARCHAR(10),      housing VARCHAR(10),      loan VARCHAR(10),  load_ts DATETIME,  load_source VARCHAR(100)  ) ENGINE=InnoDB;\"");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_11.append(" | ");
							log4jParamters_tDBRow_11.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_11.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_11 - " + (log4jParamters_tDBRow_11));
						}
					}
					new BytesLimit65535_tDBRow_11().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_11 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter(final row4Struct row4) throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"CREATE TABLE IF NOT EXISTS mart_dim_client_g3 (\n    client_key INT AUTO_INCREMENT PRIMARY KEY,\n    age INT,\n    job "
												+ "VARCHAR(50),\n    marital VARCHAR(30),\n    education VARCHAR(50),\n    has_default VARCHAR(10),\n    housing VARCHAR(10"
												+ "),\n    loan VARCHAR(10),\nload_ts DATETIME,\nload_source VARCHAR(100)\n) ENGINE=InnoDB;")
										.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_11) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_11", "tMysqlRow",
							new ParameterUtil_tDBRow_11().getParameter(row4));
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_11", "tDBR_CREATE_mart_dim_client_g3", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_11 = null;
				String query_tDBRow_11 = "";
				boolean whetherReject_tDBRow_11 = false;
				int count_tDBRow_11 = 0;
				conn_tDBRow_11 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_11 != null) {
					if (conn_tDBRow_11.getMetaData() != null) {

						log.debug("tDBRow_11 - Uses an existing connection with username '"
								+ conn_tDBRow_11.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_11.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_11", conn_tDBRow_11);
				java.sql.Statement stmt_tDBRow_11 = conn_tDBRow_11.createStatement();
				resourceMap.put("stmt_tDBRow_11", stmt_tDBRow_11);

				/**
				 * [tDBRow_11 begin ] stop
				 */

				/**
				 * [tDBRow_8 begin ] start
				 */

				sh("tDBRow_8");

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				int tos_count_tDBRow_8 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBRow_8 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBRow_8 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBRow_8 = new StringBuilder();
							log4jParamters_tDBRow_8.append("Parameters:");
							log4jParamters_tDBRow_8.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("CONNECTION" + " = " + "tDBConnection_2");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("QUERY" + " = "
									+ "\"DROP TABLE IF EXISTS raw_bank_g3; DROP TABLE IF EXISTS stg_bank_OK_g3; DROP TABLE IF EXISTS stg_bank_NOTOK_g3; DROP TABLE IF EXISTS stg_bank_OK_score_g3; DROP TABLE IF EXISTS mart_fact_calls_g3; DROP TABLE IF EXISTS mart_dim_client_g3; DROP TABLE IF EXISTS mart_dim_campaign_g3;\"");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("PROPAGATE_RECORD_SET" + " = " + "false");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("USE_PREPAREDSTATEMENT" + " = " + "false");
							log4jParamters_tDBRow_8.append(" | ");
							log4jParamters_tDBRow_8.append("UNIFIED_COMPONENTS" + " = " + "tMysqlRow");
							log4jParamters_tDBRow_8.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBRow_8 - " + (log4jParamters_tDBRow_8));
						}
					}
					new BytesLimit65535_tDBRow_8().limitLog4jByte();
				}
				// QTUP-3575
				if (enableLineage) {
					class ParameterUtil_tDBRow_8 {

						private void a(java.util.List<java.util.Map<String, String>> schema, String... values) {
							java.util.Map<String, String> field = new java.util.HashMap<>();
							field.put("name", values[0]);
							field.put("talend_type", values[1]);
							schema.add(field);
						}

						public java.util.Map<String, String> getParameter() throws Exception {
							java.util.Map<String, String> component_parameters = new java.util.HashMap<>();

							try {

								component_parameters.put("USE_EXISTING_CONNECTION", String.valueOf("true"));
								component_parameters.put("CONNECTION", String.valueOf("tDBConnection_2"));
								component_parameters.put("QUERYSTORE", String.valueOf(""));
								component_parameters.put("QUERY", String.valueOf(new StringBuilder().append(
										"DROP TABLE IF EXISTS raw_bank_g3; DROP TABLE IF EXISTS stg_bank_OK_g3; DROP TABLE IF EXISTS stg_bank_NOTOK_g3; DROP TAB"
												+ "LE IF EXISTS stg_bank_OK_score_g3; DROP TABLE IF EXISTS mart_fact_calls_g3; DROP TABLE IF EXISTS mart_dim_client_g3; DRO"
												+ "P TABLE IF EXISTS mart_dim_campaign_g3;")
										.toString()));
								component_parameters.put("DIE_ON_ERROR", String.valueOf("false"));
								component_parameters.put("PROPAGATE_RECORD_SET", String.valueOf("false"));
								component_parameters.put("USE_PREPAREDSTATEMENT", String.valueOf("false"));
								component_parameters.put("UNIFIED_COMPONENTS", String.valueOf("tMysqlRow"));

							} catch (java.lang.Exception e_tDBRow_8) {
								// do nothing
							}

							return component_parameters;
						}
					}

					talendJobLog.addComponentParameterMessage("tDBRow_8", "tMysqlRow",
							new ParameterUtil_tDBRow_8().getParameter());
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}
				// QTUP-3575

				if (enableLogStash) {
					talendJobLog.addCM("tDBRow_8", "tDBR_DROP", "tMysqlRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.sql.Connection conn_tDBRow_8 = null;
				String query_tDBRow_8 = "";
				boolean whetherReject_tDBRow_8 = false;
				int count_tDBRow_8 = 0;
				conn_tDBRow_8 = (java.sql.Connection) globalMap.get("conn_tDBConnection_2");

				if (log.isDebugEnabled() && conn_tDBRow_8 != null) {
					if (conn_tDBRow_8.getMetaData() != null) {

						log.debug("tDBRow_8 - Uses an existing connection with username '"
								+ conn_tDBRow_8.getMetaData().getUserName() + "'. Connection URL: "
								+ conn_tDBRow_8.getMetaData().getURL() + ".");

					}
				}

				resourceMap.put("conn_tDBRow_8", conn_tDBRow_8);
				java.sql.Statement stmt_tDBRow_8 = conn_tDBRow_8.createStatement();
				resourceMap.put("stmt_tDBRow_8", stmt_tDBRow_8);

				/**
				 * [tDBRow_8 begin ] stop
				 */

				/**
				 * [tDBRow_8 main ] start
				 */

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				row4 = null;

				query_tDBRow_8 = new StringBuilder().append(
						"DROP TABLE IF EXISTS raw_bank_g3; DROP TABLE IF EXISTS stg_bank_OK_g3; DROP TABLE IF EXISTS stg_bank_NOTOK_g3; DROP TAB"
								+ "LE IF EXISTS stg_bank_OK_score_g3; DROP TABLE IF EXISTS mart_fact_calls_g3; DROP TABLE IF EXISTS mart_dim_client_g3; DRO"
								+ "P TABLE IF EXISTS mart_dim_campaign_g3;")
						.toString();
				whetherReject_tDBRow_8 = false;
				log.debug("tDBRow_8 - Executing the query: '" + query_tDBRow_8 + "'.");

				globalMap.put("tDBRow_8_QUERY", query_tDBRow_8);
				try {
					stmt_tDBRow_8.execute(query_tDBRow_8);
					log.debug("tDBRow_8 - Execute the query: '" + query_tDBRow_8 + "' has finished.");

				} catch (java.lang.Exception e) {
					whetherReject_tDBRow_8 = true;

					log.error("tDBRow_8 - " + e.getMessage());

					System.err.print(e.getMessage());
					globalMap.put("tDBRow_8_ERROR_MESSAGE", e.getMessage());

				}

				if (!whetherReject_tDBRow_8) {

					row4 = new row4Struct();

				}

				tos_count_tDBRow_8++;

				/**
				 * [tDBRow_8 main ] stop
				 */

				/**
				 * [tDBRow_8 process_data_begin ] start
				 */

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				/**
				 * [tDBRow_8 process_data_begin ] stop
				 */

// Start of branch "row4"
				if (row4 != null) {

					/**
					 * [tDBRow_11 main ] start
					 */

					s(currentComponent = "tDBRow_11");

					cLabel = "tDBR_CREATE_mart_dim_client_g3";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row4", "tDBRow_8", "tDBR_DROP", "tMysqlRow", "tDBRow_11",
							"tDBR_CREATE_mart_dim_client_g3", "tMysqlRow"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row4 - " + (row4 == null ? "" : row4.toLogString()));
					}

					row14 = null;

					query_tDBRow_11 = new StringBuilder().append(
							"CREATE TABLE IF NOT EXISTS mart_dim_client_g3 (\n    client_key INT AUTO_INCREMENT PRIMARY KEY,\n    age INT,\n    job "
									+ "VARCHAR(50),\n    marital VARCHAR(30),\n    education VARCHAR(50),\n    has_default VARCHAR(10),\n    housing VARCHAR(10"
									+ "),\n    loan VARCHAR(10),\nload_ts DATETIME,\nload_source VARCHAR(100)\n) ENGINE=InnoDB;")
							.toString();
					whetherReject_tDBRow_11 = false;
					log.debug("tDBRow_11 - Executing the query: '" + query_tDBRow_11 + "'.");

					globalMap.put("tDBRow_11_QUERY", query_tDBRow_11);
					try {
						stmt_tDBRow_11.execute(query_tDBRow_11);
						log.debug("tDBRow_11 - Execute the query: '" + query_tDBRow_11 + "' has finished.");

					} catch (java.lang.Exception e) {
						whetherReject_tDBRow_11 = true;

						log.error("tDBRow_11 - " + e.getMessage());

						System.err.print(e.getMessage());
						globalMap.put("tDBRow_11_ERROR_MESSAGE", e.getMessage());

					}

					if (!whetherReject_tDBRow_11) {

						row14 = new row14Struct();

					}

					tos_count_tDBRow_11++;

					/**
					 * [tDBRow_11 main ] stop
					 */

					/**
					 * [tDBRow_11 process_data_begin ] start
					 */

					s(currentComponent = "tDBRow_11");

					cLabel = "tDBR_CREATE_mart_dim_client_g3";

					/**
					 * [tDBRow_11 process_data_begin ] stop
					 */

// Start of branch "row14"
					if (row14 != null) {

						/**
						 * [tDBRow_14 main ] start
						 */

						s(currentComponent = "tDBRow_14");

						cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row14", "tDBRow_11", "tDBR_CREATE_mart_dim_client_g3", "tMysqlRow", "tDBRow_14",
								"tDBR_CREATE_mart_dim_campaign_g3", "tMysqlRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row14 - " + (row14 == null ? "" : row14.toLogString()));
						}

						row16 = null;

						query_tDBRow_14 = new StringBuilder().append(
								"CREATE TABLE IF NOT EXISTS mart_dim_campaign_g3 (\n    campaign_key INT AUTO_INCREMENT PRIMARY KEY,\n    contact VARCHA"
										+ "R(30),\n    month VARCHAR(15),\n    day_of_week VARCHAR(15),\n    campaign INT,\n    pdays INT,\n    previous INT,\n    "
										+ "poutcome VARCHAR(30),\n    emp_var_rate FLOAT(10,4),\n    cons_price_idx FLOAT(10,4),\n    cons_conf_idx DOUBLE(10,4),\n"
										+ "    euribor3m FLOAT(10,4),\n    nr_employed DOUBLE(12,4),\n    load_ts DATETIME,\n    load_source VARCHAR(100)\n) ENGINE"
										+ "=InnoDB;")
								.toString();
						whetherReject_tDBRow_14 = false;
						log.debug("tDBRow_14 - Executing the query: '" + query_tDBRow_14 + "'.");

						globalMap.put("tDBRow_14_QUERY", query_tDBRow_14);
						try {
							stmt_tDBRow_14.execute(query_tDBRow_14);
							log.debug("tDBRow_14 - Execute the query: '" + query_tDBRow_14 + "' has finished.");

						} catch (java.lang.Exception e) {
							whetherReject_tDBRow_14 = true;

							log.error("tDBRow_14 - " + e.getMessage());

							System.err.print(e.getMessage());
							globalMap.put("tDBRow_14_ERROR_MESSAGE", e.getMessage());

						}

						if (!whetherReject_tDBRow_14) {

							row16 = new row16Struct();

						}

						tos_count_tDBRow_14++;

						/**
						 * [tDBRow_14 main ] stop
						 */

						/**
						 * [tDBRow_14 process_data_begin ] start
						 */

						s(currentComponent = "tDBRow_14");

						cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

						/**
						 * [tDBRow_14 process_data_begin ] stop
						 */

// Start of branch "row16"
						if (row16 != null) {

							/**
							 * [tDBRow_12 main ] start
							 */

							s(currentComponent = "tDBRow_12");

							cLabel = "tDBR_CREATE_mart_fact_calls_g3";

							if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

									, "row16", "tDBRow_14", "tDBR_CREATE_mart_dim_campaign_g3", "tMysqlRow",
									"tDBRow_12", "tDBR_CREATE_mart_fact_calls_g3", "tMysqlRow"

							)) {
								talendJobLogProcess(globalMap);
							}

							if (log.isTraceEnabled()) {
								log.trace("row16 - " + (row16 == null ? "" : row16.toLogString()));
							}

							row3 = null;

							query_tDBRow_12 = new StringBuilder().append(
									"CREATE TABLE mart_fact_calls_g3 (\n    call_key BIGINT AUTO_INCREMENT PRIMARY KEY,\n    source_call_id BIGINT NOT NULL,"
											+ "\n    client_key INT NOT NULL,\n    campaign_key INT NOT NULL,\n    duration INT,\n    subscribed VARCHAR(10),\n\n    sc"
											+ "ore DECIMAL(5,2),\n    prediccion TINYINT,\n    top20 TINYINT,\n    decil INT,\n    scored_at DATETIME,\n    load_ts DAT"
											+ "ETIME,\n    load_source VARCHAR(100)\n);")
									.toString();
							whetherReject_tDBRow_12 = false;
							log.debug("tDBRow_12 - Executing the query: '" + query_tDBRow_12 + "'.");

							globalMap.put("tDBRow_12_QUERY", query_tDBRow_12);
							try {
								stmt_tDBRow_12.execute(query_tDBRow_12);
								log.debug("tDBRow_12 - Execute the query: '" + query_tDBRow_12 + "' has finished.");

							} catch (java.lang.Exception e) {
								whetherReject_tDBRow_12 = true;

								log.error("tDBRow_12 - " + e.getMessage());

								System.err.print(e.getMessage());
								globalMap.put("tDBRow_12_ERROR_MESSAGE", e.getMessage());

							}

							if (!whetherReject_tDBRow_12) {

								row3 = new row3Struct();

							}

							tos_count_tDBRow_12++;

							/**
							 * [tDBRow_12 main ] stop
							 */

							/**
							 * [tDBRow_12 process_data_begin ] start
							 */

							s(currentComponent = "tDBRow_12");

							cLabel = "tDBR_CREATE_mart_fact_calls_g3";

							/**
							 * [tDBRow_12 process_data_begin ] stop
							 */

// Start of branch "row3"
							if (row3 != null) {

								/**
								 * [tRunJob_3 main ] start
								 */

								s(currentComponent = "tRunJob_3");

								cLabel = "tRJ_01_load_raw";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row3", "tDBRow_12", "tDBR_CREATE_mart_fact_calls_g3", "tMysqlRow",
										"tRunJob_3", "tRJ_01_load_raw", "tRunJob"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
								}

								java.util.List<String> paraList_tRunJob_3 = new java.util.ArrayList<String>();

								paraList_tRunJob_3.add("--father_pid=" + pid);

								paraList_tRunJob_3.add("--root_pid=" + rootPid);

								paraList_tRunJob_3.add("--father_node=tRunJob_3");

								paraList_tRunJob_3.add("--context=DEV");

								if (!"".equals(log4jLevel)) {
									paraList_tRunJob_3.add("--log4jLevel=" + log4jLevel);
								}

								if (enableLogStash) {
									paraList_tRunJob_3.add("--audit.enabled=" + enableLogStash);
								}

								paraList_tRunJob_3.add("--skipCBPAuth=true");

								// for feature:10589

								paraList_tRunJob_3.add("--stat_port=" + portStats);

								if (resuming_logs_dir_path != null) {
									paraList_tRunJob_3.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
								}
								String childResumePath_tRunJob_3 = ResumeUtil
										.getChildJobCheckPointPath(resuming_checkpoint_path);
								String tRunJobName_tRunJob_3 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
								if ("tRunJob_3".equals(tRunJobName_tRunJob_3) && childResumePath_tRunJob_3 != null) {
									paraList_tRunJob_3.add("--resuming_checkpoint_path="
											+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
								}
								paraList_tRunJob_3.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_3");

								java.util.Map<String, Object> parentContextMap_tRunJob_3 = new java.util.HashMap<String, Object>();

								context.synchronizeContext();
								class ContextProcessor_tRunJob_3 {
									private void transmitContext_0() {
										parentContextMap_tRunJob_3.put("DB_HOST", context.DB_HOST);
										parentContextMap_tRunJob_3.put("DB_NAME", context.DB_NAME);
										parentContextMap_tRunJob_3.put("DB_PASSWORD", context.DB_PASSWORD);
										parentContextMap_tRunJob_3.put("DB_PORT", context.DB_PORT);
										parentContextMap_tRunJob_3.put("DB_USER", context.DB_USER);
										parentContextMap_tRunJob_3.put("FILE_UCI_BANK", context.FILE_UCI_BANK);
										parentContextMap_tRunJob_3.put("LOAD_SOURCE_TAG", context.LOAD_SOURCE_TAG);
										parentContextMap_tRunJob_3.put("PIPELINE_NAME", context.PIPELINE_NAME);
									}

									public void transmitAllContext() {
										transmitContext_0();
									}
								}
								new ContextProcessor_tRunJob_3().transmitAllContext();
								java.util.Enumeration<?> propertyNames_tRunJob_3 = context.propertyNames();
								while (propertyNames_tRunJob_3.hasMoreElements()) {
									String key_tRunJob_3 = (String) propertyNames_tRunJob_3.nextElement();
									Object value_tRunJob_3 = (Object) context.get(key_tRunJob_3);
									String ctxType_tRunJob_3 = context.getContextType(key_tRunJob_3);
									if ("id_Password".equals(ctxType_tRunJob_3)) {
										paraList_tRunJob_3
												.add("--context_type " + key_tRunJob_3 + "=" + ctxType_tRunJob_3);
									}
									if (value_tRunJob_3 != null) {

										paraList_tRunJob_3
												.add("--context_param " + key_tRunJob_3 + "=" + value_tRunJob_3);

									} else {
										paraList_tRunJob_3.add("--context_param " + key_tRunJob_3 + "="
												+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
									}

								}

								Object obj_tRunJob_3 = null;

								g3_uci_bank.job_01_load_staging_0_1.JOB_01_load_staging childJob_tRunJob_3 = new g3_uci_bank.job_01_load_staging_0_1.JOB_01_load_staging();
								// pass DataSources
								java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_3 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
										.get(KEY_DB_DATASOURCES);
								if (null != talendDataSources_tRunJob_3) {
									java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_3 = new java.util.HashMap<String, javax.sql.DataSource>();
									for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_3 : talendDataSources_tRunJob_3
											.entrySet()) {
										dataSources_tRunJob_3.put(talendDataSourceEntry_tRunJob_3.getKey(),
												talendDataSourceEntry_tRunJob_3.getValue().getRawDataSource());
									}
									childJob_tRunJob_3.setDataSources(dataSources_tRunJob_3);
								}

								childJob_tRunJob_3.parentContextMap = parentContextMap_tRunJob_3;

								log.info(
										"tRunJob_3 - The child job 'g3_uci_bank.job_01_load_staging_0_1.JOB_01_load_staging' starts on the version '0.1' with the context 'DEV'.");

								String[][] childReturn_tRunJob_3 = childJob_tRunJob_3.runJob(
										(String[]) paraList_tRunJob_3.toArray(new String[paraList_tRunJob_3.size()]));

								log.info(
										"tRunJob_3 - The child job 'g3_uci_bank.job_01_load_staging_0_1.JOB_01_load_staging' is done.");

								if (childJob_tRunJob_3.getErrorCode() == null) {
									globalMap
											.put("tRunJob_3_CHILD_RETURN_CODE",
													childJob_tRunJob_3.getStatus() != null
															&& ("failure").equals(childJob_tRunJob_3.getStatus()) ? 1
																	: 0);
								} else {
									globalMap.put("tRunJob_3_CHILD_RETURN_CODE", childJob_tRunJob_3.getErrorCode());
								}
								if (childJob_tRunJob_3.getExceptionStackTrace() != null) {
									globalMap.put("tRunJob_3_CHILD_EXCEPTION_STACKTRACE",
											childJob_tRunJob_3.getExceptionStackTrace());
								}
								errorCode = childJob_tRunJob_3.getErrorCode();
								if (childJob_tRunJob_3.getErrorCode() != null
										|| ("failure").equals(childJob_tRunJob_3.getStatus())) {
									java.lang.Exception ce_tRunJob_3 = childJob_tRunJob_3.getException();
									throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_3 != null)
											? (ce_tRunJob_3.getClass().getName() + ": " + ce_tRunJob_3.getMessage())
											: ""));
								}

								tos_count_tRunJob_3++;

								/**
								 * [tRunJob_3 main ] stop
								 */

								/**
								 * [tRunJob_3 process_data_begin ] start
								 */

								s(currentComponent = "tRunJob_3");

								cLabel = "tRJ_01_load_raw";

								/**
								 * [tRunJob_3 process_data_begin ] stop
								 */

								/**
								 * [tRunJob_4 main ] start
								 */

								s(currentComponent = "tRunJob_4");

								cLabel = "JOB_02_quality";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row10", "tRunJob_3", "tRJ_01_load_raw", "tRunJob", "tRunJob_4",
										"JOB_02_quality", "tRunJob"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row10 - " + (row10 == null ? "" : row10.toLogString()));
								}

								java.util.List<String> paraList_tRunJob_4 = new java.util.ArrayList<String>();

								paraList_tRunJob_4.add("--father_pid=" + pid);

								paraList_tRunJob_4.add("--root_pid=" + rootPid);

								paraList_tRunJob_4.add("--father_node=tRunJob_4");

								paraList_tRunJob_4.add("--context=DEV");

								if (!"".equals(log4jLevel)) {
									paraList_tRunJob_4.add("--log4jLevel=" + log4jLevel);
								}

								if (enableLogStash) {
									paraList_tRunJob_4.add("--audit.enabled=" + enableLogStash);
								}

								paraList_tRunJob_4.add("--skipCBPAuth=true");

								// for feature:10589

								paraList_tRunJob_4.add("--stat_port=" + portStats);

								if (resuming_logs_dir_path != null) {
									paraList_tRunJob_4.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
								}
								String childResumePath_tRunJob_4 = ResumeUtil
										.getChildJobCheckPointPath(resuming_checkpoint_path);
								String tRunJobName_tRunJob_4 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
								if ("tRunJob_4".equals(tRunJobName_tRunJob_4) && childResumePath_tRunJob_4 != null) {
									paraList_tRunJob_4.add("--resuming_checkpoint_path="
											+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
								}
								paraList_tRunJob_4.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_4");

								java.util.Map<String, Object> parentContextMap_tRunJob_4 = new java.util.HashMap<String, Object>();

								Object obj_tRunJob_4 = null;

								g3_uci_bank.job_02_quality_0_1.JOB_02_quality childJob_tRunJob_4 = new g3_uci_bank.job_02_quality_0_1.JOB_02_quality();
								// pass DataSources
								java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_4 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
										.get(KEY_DB_DATASOURCES);
								if (null != talendDataSources_tRunJob_4) {
									java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_4 = new java.util.HashMap<String, javax.sql.DataSource>();
									for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_4 : talendDataSources_tRunJob_4
											.entrySet()) {
										dataSources_tRunJob_4.put(talendDataSourceEntry_tRunJob_4.getKey(),
												talendDataSourceEntry_tRunJob_4.getValue().getRawDataSource());
									}
									childJob_tRunJob_4.setDataSources(dataSources_tRunJob_4);
								}

								childJob_tRunJob_4.parentContextMap = parentContextMap_tRunJob_4;

								log.info(
										"tRunJob_4 - The child job 'g3_uci_bank.job_02_quality_0_1.JOB_02_quality' starts on the version '0.1' with the context 'DEV'.");

								String[][] childReturn_tRunJob_4 = childJob_tRunJob_4.runJob(
										(String[]) paraList_tRunJob_4.toArray(new String[paraList_tRunJob_4.size()]));

								log.info(
										"tRunJob_4 - The child job 'g3_uci_bank.job_02_quality_0_1.JOB_02_quality' is done.");

								if (childJob_tRunJob_4.getErrorCode() == null) {
									globalMap
											.put("tRunJob_4_CHILD_RETURN_CODE",
													childJob_tRunJob_4.getStatus() != null
															&& ("failure").equals(childJob_tRunJob_4.getStatus()) ? 1
																	: 0);
								} else {
									globalMap.put("tRunJob_4_CHILD_RETURN_CODE", childJob_tRunJob_4.getErrorCode());
								}
								if (childJob_tRunJob_4.getExceptionStackTrace() != null) {
									globalMap.put("tRunJob_4_CHILD_EXCEPTION_STACKTRACE",
											childJob_tRunJob_4.getExceptionStackTrace());
								}
								errorCode = childJob_tRunJob_4.getErrorCode();
								if (childJob_tRunJob_4.getErrorCode() != null
										|| ("failure").equals(childJob_tRunJob_4.getStatus())) {
									java.lang.Exception ce_tRunJob_4 = childJob_tRunJob_4.getException();
									throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_4 != null)
											? (ce_tRunJob_4.getClass().getName() + ": " + ce_tRunJob_4.getMessage())
											: ""));
								}

								tos_count_tRunJob_4++;

								/**
								 * [tRunJob_4 main ] stop
								 */

								/**
								 * [tRunJob_4 process_data_begin ] start
								 */

								s(currentComponent = "tRunJob_4");

								cLabel = "JOB_02_quality";

								/**
								 * [tRunJob_4 process_data_begin ] stop
								 */

								/**
								 * [tRunJob_1 main ] start
								 */

								s(currentComponent = "tRunJob_1");

								cLabel = "JOB_03_carga_score";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row1", "tRunJob_4", "JOB_02_quality", "tRunJob", "tRunJob_1",
										"JOB_03_carga_score", "tRunJob"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
								}

								java.util.List<String> paraList_tRunJob_1 = new java.util.ArrayList<String>();

								paraList_tRunJob_1.add("--father_pid=" + pid);

								paraList_tRunJob_1.add("--root_pid=" + rootPid);

								paraList_tRunJob_1.add("--father_node=tRunJob_1");

								paraList_tRunJob_1.add("--context=Default");

								if (!"".equals(log4jLevel)) {
									paraList_tRunJob_1.add("--log4jLevel=" + log4jLevel);
								}

								if (enableLogStash) {
									paraList_tRunJob_1.add("--audit.enabled=" + enableLogStash);
								}

								paraList_tRunJob_1.add("--skipCBPAuth=true");

								// for feature:10589

								paraList_tRunJob_1.add("--stat_port=" + portStats);

								if (resuming_logs_dir_path != null) {
									paraList_tRunJob_1.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
								}
								String childResumePath_tRunJob_1 = ResumeUtil
										.getChildJobCheckPointPath(resuming_checkpoint_path);
								String tRunJobName_tRunJob_1 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
								if ("tRunJob_1".equals(tRunJobName_tRunJob_1) && childResumePath_tRunJob_1 != null) {
									paraList_tRunJob_1.add("--resuming_checkpoint_path="
											+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
								}
								paraList_tRunJob_1.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_1");

								java.util.Map<String, Object> parentContextMap_tRunJob_1 = new java.util.HashMap<String, Object>();

								Object obj_tRunJob_1 = null;

								g3_uci_bank.job_03_carga_score_0_1.JOB_03_carga_score childJob_tRunJob_1 = new g3_uci_bank.job_03_carga_score_0_1.JOB_03_carga_score();
								// pass DataSources
								java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_1 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
										.get(KEY_DB_DATASOURCES);
								if (null != talendDataSources_tRunJob_1) {
									java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_1 = new java.util.HashMap<String, javax.sql.DataSource>();
									for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_1 : talendDataSources_tRunJob_1
											.entrySet()) {
										dataSources_tRunJob_1.put(talendDataSourceEntry_tRunJob_1.getKey(),
												talendDataSourceEntry_tRunJob_1.getValue().getRawDataSource());
									}
									childJob_tRunJob_1.setDataSources(dataSources_tRunJob_1);
								}

								childJob_tRunJob_1.parentContextMap = parentContextMap_tRunJob_1;

								log.info(
										"tRunJob_1 - The child job 'g3_uci_bank.job_03_carga_score_0_1.JOB_03_carga_score' starts on the version '0.1' with the context 'Default'.");

								String[][] childReturn_tRunJob_1 = childJob_tRunJob_1.runJob(
										(String[]) paraList_tRunJob_1.toArray(new String[paraList_tRunJob_1.size()]));

								log.info(
										"tRunJob_1 - The child job 'g3_uci_bank.job_03_carga_score_0_1.JOB_03_carga_score' is done.");

								if (childJob_tRunJob_1.getErrorCode() == null) {
									globalMap
											.put("tRunJob_1_CHILD_RETURN_CODE",
													childJob_tRunJob_1.getStatus() != null
															&& ("failure").equals(childJob_tRunJob_1.getStatus()) ? 1
																	: 0);
								} else {
									globalMap.put("tRunJob_1_CHILD_RETURN_CODE", childJob_tRunJob_1.getErrorCode());
								}
								if (childJob_tRunJob_1.getExceptionStackTrace() != null) {
									globalMap.put("tRunJob_1_CHILD_EXCEPTION_STACKTRACE",
											childJob_tRunJob_1.getExceptionStackTrace());
								}
								errorCode = childJob_tRunJob_1.getErrorCode();
								if (childJob_tRunJob_1.getErrorCode() != null
										|| ("failure").equals(childJob_tRunJob_1.getStatus())) {
									java.lang.Exception ce_tRunJob_1 = childJob_tRunJob_1.getException();
									throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_1 != null)
											? (ce_tRunJob_1.getClass().getName() + ": " + ce_tRunJob_1.getMessage())
											: ""));
								}

								tos_count_tRunJob_1++;

								/**
								 * [tRunJob_1 main ] stop
								 */

								/**
								 * [tRunJob_1 process_data_begin ] start
								 */

								s(currentComponent = "tRunJob_1");

								cLabel = "JOB_03_carga_score";

								/**
								 * [tRunJob_1 process_data_begin ] stop
								 */

								/**
								 * [tDBRow_9 main ] start
								 */

								s(currentComponent = "tDBRow_9");

								cLabel = "tDBR_dim_cliente_aine";

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row2", "tRunJob_1", "JOB_03_carga_score", "tRunJob", "tDBRow_9",
										"tDBR_dim_cliente_aine", "tMysqlRow"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
								}

								row12 = null;

								query_tDBRow_9 = new StringBuilder().append(
										"INSERT INTO mart_dim_client_g3 (\n    age,\n    job,\n    marital,\n    education,\n    has_default,\n    housing,\n   "
												+ " loan,\n    load_ts,\n    load_source\n)\nSELECT DISTINCT\n    b.age,\n    b.job,\n    b.marital,\n    b.education,\n   "
												+ " b.has_default,\n    b.housing,\n    b.loan,\n    '")
										.toString()
										+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate())
										+ new StringBuilder().append(
												"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\nWHERE NOT EXISTS (\n    SELECT 1\n    FROM mart_dim_client_g3 d\n    WHER"
														+ "E d.age <=> b.age\n      AND d.job <=> b.job\n      AND d.marital <=> b.marital\n      AND d.education <=> b.education\n"
														+ "      AND d.has_default <=> b.has_default\n      AND d.housing <=> b.housing\n      AND d.loan <=> b.loan\n);")
												.toString();
								whetherReject_tDBRow_9 = false;
								log.debug("tDBRow_9 - Executing the query: '" + query_tDBRow_9 + "'.");

								globalMap.put("tDBRow_9_QUERY", query_tDBRow_9);
								try {
									stmt_tDBRow_9.execute(query_tDBRow_9);
									log.debug("tDBRow_9 - Execute the query: '" + query_tDBRow_9 + "' has finished.");

								} catch (java.lang.Exception e) {
									whetherReject_tDBRow_9 = true;

									log.error("tDBRow_9 - " + e.getMessage());

									System.err.print(e.getMessage());
									globalMap.put("tDBRow_9_ERROR_MESSAGE", e.getMessage());

								}

								if (!whetherReject_tDBRow_9) {

									row12 = new row12Struct();

								}

								tos_count_tDBRow_9++;

								/**
								 * [tDBRow_9 main ] stop
								 */

								/**
								 * [tDBRow_9 process_data_begin ] start
								 */

								s(currentComponent = "tDBRow_9");

								cLabel = "tDBR_dim_cliente_aine";

								/**
								 * [tDBRow_9 process_data_begin ] stop
								 */

// Start of branch "row12"
								if (row12 != null) {

									/**
									 * [tDBRow_10 main ] start
									 */

									s(currentComponent = "tDBRow_10");

									cLabel = "tDBR_dim_campaign_aine";

									if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

											, "row12", "tDBRow_9", "tDBR_dim_cliente_aine", "tMysqlRow", "tDBRow_10",
											"tDBR_dim_campaign_aine", "tMysqlRow"

									)) {
										talendJobLogProcess(globalMap);
									}

									if (log.isTraceEnabled()) {
										log.trace("row12 - " + (row12 == null ? "" : row12.toLogString()));
									}

									row13 = null;

									query_tDBRow_10 = new StringBuilder().append(
											"INSERT INTO mart_dim_campaign_g3 (\n    contact,\n    month,\n    day_of_week,\n    campaign,\n    pdays,\n    previous"
													+ ",\n    poutcome,\n    emp_var_rate,\n    cons_price_idx,\n    cons_conf_idx,\n    euribor3m,\n    nr_employed,\nload_ts,"
													+ "\nload_source\n)\nSELECT DISTINCT\n    b.contact,\n    b.month,\n    b.day_of_week,\n    b.campaign,\n    b.pdays,\n    "
													+ "b.previous,\n    b.poutcome,\n    b.emp_var_rate,\n    b.cons_price_idx,\n    b.cons_conf_idx,\n    b.euribor3m,\n    b."
													+ "nr_employed,\n    '")
											.toString()
											+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate())
											+ new StringBuilder().append(
													"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\nWHERE NOT EXISTS (\n    SELECT 1\n    FROM mart_dim_campaign_g3 d\n    WH"
															+ "ERE d.contact <=> b.contact\n      AND d.month <=> b.month\n      AND d.day_of_week <=> b.day_of_week\n      AND d.campa"
															+ "ign <=> b.campaign\n      AND d.pdays <=> b.pdays\n      AND d.previous <=> b.previous\n      AND d.poutcome <=> b.poutc"
															+ "ome\n      AND d.emp_var_rate <=> b.emp_var_rate\n      AND d.cons_price_idx <=> b.cons_price_idx\n      AND d.cons_conf"
															+ "_idx <=> b.cons_conf_idx\n      AND d.euribor3m <=> b.euribor3m\n      AND d.nr_employed <=> b.nr_employed\n);")
													.toString();
									whetherReject_tDBRow_10 = false;
									log.debug("tDBRow_10 - Executing the query: '" + query_tDBRow_10 + "'.");

									globalMap.put("tDBRow_10_QUERY", query_tDBRow_10);
									try {
										stmt_tDBRow_10.execute(query_tDBRow_10);
										log.debug("tDBRow_10 - Execute the query: '" + query_tDBRow_10
												+ "' has finished.");

									} catch (java.lang.Exception e) {
										whetherReject_tDBRow_10 = true;

										log.error("tDBRow_10 - " + e.getMessage());

										System.err.print(e.getMessage());
										globalMap.put("tDBRow_10_ERROR_MESSAGE", e.getMessage());

									}

									if (!whetherReject_tDBRow_10) {

										row13 = new row13Struct();

									}

									tos_count_tDBRow_10++;

									/**
									 * [tDBRow_10 main ] stop
									 */

									/**
									 * [tDBRow_10 process_data_begin ] start
									 */

									s(currentComponent = "tDBRow_10");

									cLabel = "tDBR_dim_campaign_aine";

									/**
									 * [tDBRow_10 process_data_begin ] stop
									 */

// Start of branch "row13"
									if (row13 != null) {

										/**
										 * [tDBRow_13 main ] start
										 */

										s(currentComponent = "tDBRow_13");

										cLabel = "tDBR_fact_calls_aine";

										if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

												, "row13", "tDBRow_10", "tDBR_dim_campaign_aine", "tMysqlRow",
												"tDBRow_13", "tDBR_fact_calls_aine", "tMysqlRow"

										)) {
											talendJobLogProcess(globalMap);
										}

										if (log.isTraceEnabled()) {
											log.trace("row13 - " + (row13 == null ? "" : row13.toLogString()));
										}

										query_tDBRow_13 = new StringBuilder().append(
												"INSERT INTO mart_fact_calls_g3 (\n    source_call_id,\n    client_key,\n    campaign_key,\n    duration,\n    subscribe"
														+ "d,\n    score,\n    prediccion,\n    top20,\n    decil,\n    scored_at,\n    load_ts,\n    load_source\n)\nSELECT\n    b"
														+ ".source_call_id,\n    dc.client_key,\n    dca.campaign_key,\n    b.duration,\n    b.y,\n    s.score,\n    s.prediccion,"
														+ "\n    s.top20,\n    s.decil,\n    s.scored_at,\n    '")
												.toString()
												+ TalendDate.formatDate("yyyy-MM-dd HH:mm:ss",
														TalendDate.getCurrentDate())
												+ new StringBuilder().append(
														"',\n    'G3_UCI_BANK'\nFROM stg_bank_OK_g3 b\n\nINNER JOIN mart_dim_client_g3 dc\n    ON dc.age <=> b.age\n    AND dc.j"
																+ "ob <=> b.job\n    AND dc.marital <=> b.marital\n    AND dc.education <=> b.education\n    AND dc.has_default <=> b.has_d"
																+ "efault\n    AND dc.housing <=> b.housing\n    AND dc.loan <=> b.loan\n\nINNER JOIN mart_dim_campaign_g3 dca\n    ON dca."
																+ "contact <=> b.contact\n    AND dca.month <=> b.month\n    AND dca.day_of_week <=> b.day_of_week\n    AND dca.campaign <="
																+ "> b.campaign\n    AND dca.pdays <=> b.pdays\n    AND dca.previous <=> b.previous\n    AND dca.poutcome <=> b.poutcome\n "
																+ "   AND dca.emp_var_rate <=> b.emp_var_rate\n    AND dca.cons_price_idx <=> b.cons_price_idx\n    AND dca.cons_conf_idx <"
																+ "=> b.cons_conf_idx\n    AND dca.euribor3m <=> b.euribor3m\n    AND dca.nr_employed <=> b.nr_employed\n\nLEFT JOIN stg_ba"
																+ "nk_OK_score_g3 s\n    ON s.source_call_id = b.source_call_id;")
														.toString();
										whetherReject_tDBRow_13 = false;
										log.debug("tDBRow_13 - Executing the query: '" + query_tDBRow_13 + "'.");

										globalMap.put("tDBRow_13_QUERY", query_tDBRow_13);
										try {
											stmt_tDBRow_13.execute(query_tDBRow_13);
											log.debug("tDBRow_13 - Execute the query: '" + query_tDBRow_13
													+ "' has finished.");

										} catch (java.lang.Exception e) {
											whetherReject_tDBRow_13 = true;

											log.error("tDBRow_13 - " + e.getMessage());

											System.err.print(e.getMessage());
											globalMap.put("tDBRow_13_ERROR_MESSAGE", e.getMessage());

										}

										tos_count_tDBRow_13++;

										/**
										 * [tDBRow_13 main ] stop
										 */

										/**
										 * [tDBRow_13 process_data_begin ] start
										 */

										s(currentComponent = "tDBRow_13");

										cLabel = "tDBR_fact_calls_aine";

										/**
										 * [tDBRow_13 process_data_begin ] stop
										 */

										/**
										 * [tDBRow_13 process_data_end ] start
										 */

										s(currentComponent = "tDBRow_13");

										cLabel = "tDBR_fact_calls_aine";

										/**
										 * [tDBRow_13 process_data_end ] stop
										 */

									} // End of branch "row13"

									/**
									 * [tDBRow_10 process_data_end ] start
									 */

									s(currentComponent = "tDBRow_10");

									cLabel = "tDBR_dim_campaign_aine";

									/**
									 * [tDBRow_10 process_data_end ] stop
									 */

								} // End of branch "row12"

								/**
								 * [tDBRow_9 process_data_end ] start
								 */

								s(currentComponent = "tDBRow_9");

								cLabel = "tDBR_dim_cliente_aine";

								/**
								 * [tDBRow_9 process_data_end ] stop
								 */

								/**
								 * [tRunJob_1 process_data_end ] start
								 */

								s(currentComponent = "tRunJob_1");

								cLabel = "JOB_03_carga_score";

								/**
								 * [tRunJob_1 process_data_end ] stop
								 */

								/**
								 * [tRunJob_4 process_data_end ] start
								 */

								s(currentComponent = "tRunJob_4");

								cLabel = "JOB_02_quality";

								/**
								 * [tRunJob_4 process_data_end ] stop
								 */

								/**
								 * [tRunJob_3 process_data_end ] start
								 */

								s(currentComponent = "tRunJob_3");

								cLabel = "tRJ_01_load_raw";

								/**
								 * [tRunJob_3 process_data_end ] stop
								 */

							} // End of branch "row3"

							/**
							 * [tDBRow_12 process_data_end ] start
							 */

							s(currentComponent = "tDBRow_12");

							cLabel = "tDBR_CREATE_mart_fact_calls_g3";

							/**
							 * [tDBRow_12 process_data_end ] stop
							 */

						} // End of branch "row16"

						/**
						 * [tDBRow_14 process_data_end ] start
						 */

						s(currentComponent = "tDBRow_14");

						cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

						/**
						 * [tDBRow_14 process_data_end ] stop
						 */

					} // End of branch "row14"

					/**
					 * [tDBRow_11 process_data_end ] start
					 */

					s(currentComponent = "tDBRow_11");

					cLabel = "tDBR_CREATE_mart_dim_client_g3";

					/**
					 * [tDBRow_11 process_data_end ] stop
					 */

				} // End of branch "row4"

				/**
				 * [tDBRow_8 process_data_end ] start
				 */

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				/**
				 * [tDBRow_8 process_data_end ] stop
				 */

				/**
				 * [tDBRow_8 end ] start
				 */

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				globalMap.put("tDBRow_8_NB_LINE", count_tDBRow_8);
				stmt_tDBRow_8.close();
				resourceMap.remove("stmt_tDBRow_8");
				resourceMap.put("statementClosed_tDBRow_8", true);
				resourceMap.put("finish_tDBRow_8", true);

				if (log.isDebugEnabled())
					log.debug("tDBRow_8 - " + ("Done."));

				ok_Hash.put("tDBRow_8", true);
				end_Hash.put("tDBRow_8", System.currentTimeMillis());

				/**
				 * [tDBRow_8 end ] stop
				 */

				/**
				 * [tDBRow_11 end ] start
				 */

				s(currentComponent = "tDBRow_11");

				cLabel = "tDBR_CREATE_mart_dim_client_g3";

				globalMap.put("tDBRow_11_NB_LINE", count_tDBRow_11);
				stmt_tDBRow_11.close();
				resourceMap.remove("stmt_tDBRow_11");
				resourceMap.put("statementClosed_tDBRow_11", true);
				resourceMap.put("finish_tDBRow_11", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row4", 2, 0, "tDBRow_8",
						"tDBR_DROP", "tMysqlRow", "tDBRow_11", "tDBR_CREATE_mart_dim_client_g3", "tMysqlRow",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_11 - " + ("Done."));

				ok_Hash.put("tDBRow_11", true);
				end_Hash.put("tDBRow_11", System.currentTimeMillis());

				/**
				 * [tDBRow_11 end ] stop
				 */

				/**
				 * [tDBRow_14 end ] start
				 */

				s(currentComponent = "tDBRow_14");

				cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

				globalMap.put("tDBRow_14_NB_LINE", count_tDBRow_14);
				stmt_tDBRow_14.close();
				resourceMap.remove("stmt_tDBRow_14");
				resourceMap.put("statementClosed_tDBRow_14", true);
				resourceMap.put("finish_tDBRow_14", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row14", 2, 0,
						"tDBRow_11", "tDBR_CREATE_mart_dim_client_g3", "tMysqlRow", "tDBRow_14",
						"tDBR_CREATE_mart_dim_campaign_g3", "tMysqlRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_14 - " + ("Done."));

				ok_Hash.put("tDBRow_14", true);
				end_Hash.put("tDBRow_14", System.currentTimeMillis());

				/**
				 * [tDBRow_14 end ] stop
				 */

				/**
				 * [tDBRow_12 end ] start
				 */

				s(currentComponent = "tDBRow_12");

				cLabel = "tDBR_CREATE_mart_fact_calls_g3";

				globalMap.put("tDBRow_12_NB_LINE", count_tDBRow_12);
				stmt_tDBRow_12.close();
				resourceMap.remove("stmt_tDBRow_12");
				resourceMap.put("statementClosed_tDBRow_12", true);
				resourceMap.put("finish_tDBRow_12", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row16", 2, 0,
						"tDBRow_14", "tDBR_CREATE_mart_dim_campaign_g3", "tMysqlRow", "tDBRow_12",
						"tDBR_CREATE_mart_fact_calls_g3", "tMysqlRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_12 - " + ("Done."));

				ok_Hash.put("tDBRow_12", true);
				end_Hash.put("tDBRow_12", System.currentTimeMillis());

				/**
				 * [tDBRow_12 end ] stop
				 */

				/**
				 * [tRunJob_3 end ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "tRJ_01_load_raw";

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tDBRow_12", "tDBR_CREATE_mart_fact_calls_g3", "tMysqlRow", "tRunJob_3", "tRJ_01_load_raw",
						"tRunJob", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tRunJob_3 - " + ("Done."));

				ok_Hash.put("tRunJob_3", true);
				end_Hash.put("tRunJob_3", System.currentTimeMillis());

				/**
				 * [tRunJob_3 end ] stop
				 */

				/**
				 * [tRunJob_4 end ] start
				 */

				s(currentComponent = "tRunJob_4");

				cLabel = "JOB_02_quality";

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row10", 2, 0,
						"tRunJob_3", "tRJ_01_load_raw", "tRunJob", "tRunJob_4", "JOB_02_quality", "tRunJob",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tRunJob_4 - " + ("Done."));

				ok_Hash.put("tRunJob_4", true);
				end_Hash.put("tRunJob_4", System.currentTimeMillis());

				/**
				 * [tRunJob_4 end ] stop
				 */

				/**
				 * [tRunJob_1 end ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "JOB_03_carga_score";

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tRunJob_4", "JOB_02_quality", "tRunJob", "tRunJob_1", "JOB_03_carga_score", "tRunJob",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tRunJob_1 - " + ("Done."));

				ok_Hash.put("tRunJob_1", true);
				end_Hash.put("tRunJob_1", System.currentTimeMillis());

				/**
				 * [tRunJob_1 end ] stop
				 */

				/**
				 * [tDBRow_9 end ] start
				 */

				s(currentComponent = "tDBRow_9");

				cLabel = "tDBR_dim_cliente_aine";

				globalMap.put("tDBRow_9_NB_LINE", count_tDBRow_9);
				stmt_tDBRow_9.close();
				resourceMap.remove("stmt_tDBRow_9");
				resourceMap.put("statementClosed_tDBRow_9", true);
				resourceMap.put("finish_tDBRow_9", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tRunJob_1", "JOB_03_carga_score", "tRunJob", "tDBRow_9", "tDBR_dim_cliente_aine", "tMysqlRow",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_9 - " + ("Done."));

				ok_Hash.put("tDBRow_9", true);
				end_Hash.put("tDBRow_9", System.currentTimeMillis());

				/**
				 * [tDBRow_9 end ] stop
				 */

				/**
				 * [tDBRow_10 end ] start
				 */

				s(currentComponent = "tDBRow_10");

				cLabel = "tDBR_dim_campaign_aine";

				globalMap.put("tDBRow_10_NB_LINE", count_tDBRow_10);
				stmt_tDBRow_10.close();
				resourceMap.remove("stmt_tDBRow_10");
				resourceMap.put("statementClosed_tDBRow_10", true);
				resourceMap.put("finish_tDBRow_10", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row12", 2, 0,
						"tDBRow_9", "tDBR_dim_cliente_aine", "tMysqlRow", "tDBRow_10", "tDBR_dim_campaign_aine",
						"tMysqlRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_10 - " + ("Done."));

				ok_Hash.put("tDBRow_10", true);
				end_Hash.put("tDBRow_10", System.currentTimeMillis());

				/**
				 * [tDBRow_10 end ] stop
				 */

				/**
				 * [tDBRow_13 end ] start
				 */

				s(currentComponent = "tDBRow_13");

				cLabel = "tDBR_fact_calls_aine";

				globalMap.put("tDBRow_13_NB_LINE", count_tDBRow_13);
				stmt_tDBRow_13.close();
				resourceMap.remove("stmt_tDBRow_13");
				resourceMap.put("statementClosed_tDBRow_13", true);
				resourceMap.put("finish_tDBRow_13", true);
				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row13", 2, 0,
						"tDBRow_10", "tDBR_dim_campaign_aine", "tMysqlRow", "tDBRow_13", "tDBR_fact_calls_aine",
						"tMysqlRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tDBRow_13 - " + ("Done."));

				ok_Hash.put("tDBRow_13", true);
				end_Hash.put("tDBRow_13", System.currentTimeMillis());

				/**
				 * [tDBRow_13 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBRow_8 finally ] start
				 */

				s(currentComponent = "tDBRow_8");

				cLabel = "tDBR_DROP";

				if (resourceMap.get("statementClosed_tDBRow_8") == null) {
					java.sql.Statement stmtToClose_tDBRow_8 = null;
					if ((stmtToClose_tDBRow_8 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_8")) != null) {
						stmtToClose_tDBRow_8.close();
					}
				}

				/**
				 * [tDBRow_8 finally ] stop
				 */

				/**
				 * [tDBRow_11 finally ] start
				 */

				s(currentComponent = "tDBRow_11");

				cLabel = "tDBR_CREATE_mart_dim_client_g3";

				if (resourceMap.get("statementClosed_tDBRow_11") == null) {
					java.sql.Statement stmtToClose_tDBRow_11 = null;
					if ((stmtToClose_tDBRow_11 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_11")) != null) {
						stmtToClose_tDBRow_11.close();
					}
				}

				/**
				 * [tDBRow_11 finally ] stop
				 */

				/**
				 * [tDBRow_14 finally ] start
				 */

				s(currentComponent = "tDBRow_14");

				cLabel = "tDBR_CREATE_mart_dim_campaign_g3";

				if (resourceMap.get("statementClosed_tDBRow_14") == null) {
					java.sql.Statement stmtToClose_tDBRow_14 = null;
					if ((stmtToClose_tDBRow_14 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_14")) != null) {
						stmtToClose_tDBRow_14.close();
					}
				}

				/**
				 * [tDBRow_14 finally ] stop
				 */

				/**
				 * [tDBRow_12 finally ] start
				 */

				s(currentComponent = "tDBRow_12");

				cLabel = "tDBR_CREATE_mart_fact_calls_g3";

				if (resourceMap.get("statementClosed_tDBRow_12") == null) {
					java.sql.Statement stmtToClose_tDBRow_12 = null;
					if ((stmtToClose_tDBRow_12 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_12")) != null) {
						stmtToClose_tDBRow_12.close();
					}
				}

				/**
				 * [tDBRow_12 finally ] stop
				 */

				/**
				 * [tRunJob_3 finally ] start
				 */

				s(currentComponent = "tRunJob_3");

				cLabel = "tRJ_01_load_raw";

				/**
				 * [tRunJob_3 finally ] stop
				 */

				/**
				 * [tRunJob_4 finally ] start
				 */

				s(currentComponent = "tRunJob_4");

				cLabel = "JOB_02_quality";

				/**
				 * [tRunJob_4 finally ] stop
				 */

				/**
				 * [tRunJob_1 finally ] start
				 */

				s(currentComponent = "tRunJob_1");

				cLabel = "JOB_03_carga_score";

				/**
				 * [tRunJob_1 finally ] stop
				 */

				/**
				 * [tDBRow_9 finally ] start
				 */

				s(currentComponent = "tDBRow_9");

				cLabel = "tDBR_dim_cliente_aine";

				if (resourceMap.get("statementClosed_tDBRow_9") == null) {
					java.sql.Statement stmtToClose_tDBRow_9 = null;
					if ((stmtToClose_tDBRow_9 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_9")) != null) {
						stmtToClose_tDBRow_9.close();
					}
				}

				/**
				 * [tDBRow_9 finally ] stop
				 */

				/**
				 * [tDBRow_10 finally ] start
				 */

				s(currentComponent = "tDBRow_10");

				cLabel = "tDBR_dim_campaign_aine";

				if (resourceMap.get("statementClosed_tDBRow_10") == null) {
					java.sql.Statement stmtToClose_tDBRow_10 = null;
					if ((stmtToClose_tDBRow_10 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_10")) != null) {
						stmtToClose_tDBRow_10.close();
					}
				}

				/**
				 * [tDBRow_10 finally ] stop
				 */

				/**
				 * [tDBRow_13 finally ] start
				 */

				s(currentComponent = "tDBRow_13");

				cLabel = "tDBR_fact_calls_aine";

				if (resourceMap.get("statementClosed_tDBRow_13") == null) {
					java.sql.Statement stmtToClose_tDBRow_13 = null;
					if ((stmtToClose_tDBRow_13 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_13")) != null) {
						stmtToClose_tDBRow_13.close();
					}
				}

				/**
				 * [tDBRow_13 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBRow_8_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				sh("talendJobLog");

				s(currentComponent = "talendJobLog");

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

					if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNTIMEPARAMETER) {
						builder_talendJobLog.connectorType(jcm.component_name).connectorId(jcm.component_id);

						for (java.util.Map.Entry<String, String> entry : jcm.component_parameters.entrySet()) {
							builder_talendJobLog.custom("P_" + entry.getKey(), entry.getValue());
						}

						log_context_talendJobLog = builder_talendJobLog.build();

						runtime_lineage_logger_talendJobLog.componentParameters(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNTIMESCHEMA) {
						builder_talendJobLog.sourceConnectorType(jcm.sourceComponentName).sourceId(jcm.sourceId)
								.connectionName(jcm.current_connector).schema(jcm.component_schema.toString())
								.targetConnectorType(jcm.targetComponentName).targetId(jcm.targetId);

						if (jcm.current_connector_type != null) {
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

				s(currentComponent = "talendJobLog");

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				s(currentComponent = "talendJobLog");

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException) && !(e instanceof TDieException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
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
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final JOB_00_orquestador_UCI_BANK JOB_00_orquestador_UCI_BANKClass = new JOB_00_orquestador_UCI_BANK();
		int exitCode = JOB_00_orquestador_UCI_BANKClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'JOB_00_orquestador_UCI_BANK' - Done.");
		}

		System.exit(exitCode);

	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20261022_0848-patch"));

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

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());
		}

		getjobInfo();

		log.info("TalendJob: 'JOB_00_orquestador_UCI_BANK' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_8fFtkNiEEfGYDKHwOCnAyQ");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2026-11-08T01:32:21.075533100Z");

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
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() == null) {
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
						if (jobInfo.isEmpty()) {
							getjobInfo();
						}
					}
					org.talend.metrics.CBPClient.startListenIfNotStarted(enableCBP, isRooutJob, jobType,
							jobInfo.getProperty("artifactId"), true, jobInfo.getProperty("cmdLineVersion"),
							jobInfo.getProperty("patch"), jobInfo.getProperty("date"));
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
		if ((lineageDirPath != null && !lineageDirPath.isEmpty())
				|| (payloadFilePath != null && !payloadFilePath.isEmpty())) {
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
			if (lineageFilePath == null || lineageFilePath.isEmpty()) {
				String finalLineageDirPath = lineageDirPath;
				if (lineageDirPath == null || lineageDirPath.isEmpty()) {
					finalLineageDirPath = payloadFilePath + "_dir";
				}

				System.setProperty("runtime.lineage.appender.file.path",
						new StringBuilder().append(finalLineageDirPath)
								.append((finalLineageDirPath.endsWith("/") || finalLineageDirPath.endsWith("\\")) ? ""
										: java.io.File.separator)
								.append(projectName).append(java.io.File.separatorChar).append(jobName)
								.append(java.io.File.separatorChar).append(jobVersion)
								.append(java.io.File.separatorChar).append("runtime_log_")
								.append(new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()))
								.append(".json").toString());
			}

			System.getProperties().stringPropertyNames().stream()
					.filter(it -> it.startsWith("runtime.lineage.") && !"runtime.lineage.outputpath".equals(it))
					.forEach(key -> p_talendJobLog.setProperty(key.substring("runtime.lineage.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator.setLevel(p_talendJobLog.getProperty("root.logger"),
					org.apache.logging.log4j.Level.DEBUG);

			runtime_lineage_logger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(p_talendJobLog);
		}

		if (enableLogStash) {
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

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
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
					contextStr = (String) jobProperties.get("context");
				}

				// extract params from caller
				String taskExecutionIdForSOAP = null;
				String jobExecutionIdForSOAP = null;
				boolean skipCBPAuth = false;
				boolean isSoapService = false;
				for (String arg : args) {
					// for SOAP service, the taskExecutionId and jobExecutionId are passed in as
					// context params from Talend Runtime
					if (arg.startsWith("--context_param")
							&& (arg.contains("taskExecutionId") || arg.contains("jobExecutionId"))) {
						String keyValue = arg.replace("--context_param", "");
						String[] parts = keyValue.split("=");
						String[] cleanParts = java.util.Arrays.stream(parts).filter(s -> !s.isEmpty())
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
			if (isStandaloneMS) {
				context.putAll(this.getDefaultProperties());
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = JOB_00_orquestador_UCI_BANK.class.getClassLoader().getResourceAsStream(
					"g3_uci_bank/job_00_orquestador_uci_bank_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = JOB_00_orquestador_UCI_BANK.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
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
					if (isStandaloneMS) {
						// override context key-value pairs if provided using --context=contextName
						defaultProps.load(inContext);
						context.putAll(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}
			// override key-value pairs if provided via --config.location=file1.file2 OR
			// --config.additional-location=file1,file2
			if (isStandaloneMS) {
				context.putAll(this.getAdditionalProperties());
			}

			// QAPPINT-2186: Apply cloud context properties for microservice jobs (REST or
			// SOAP)
			// Only execute if running in microservice context and cloud execution is
			// detected
			if (isStandaloneMS) {
				try {
					String appClassName = System.getProperty("talend.microservice.app.class");
					if (appClassName != null && !appClassName.isEmpty()) {
						Class<?> appClass = Class.forName(appClassName);
						java.lang.reflect.Method getPropsMethod = appClass.getMethod("getCloudContextProperties");
						@SuppressWarnings("unchecked")
						java.util.Map<String, String> cloudContextProps = (java.util.Map<String, String>) getPropsMethod
								.invoke(null);
						if (cloudContextProps != null && !cloudContextProps.isEmpty()) {
							context.putAll(cloudContextProps);
						}
					}
				} catch (Exception e) {
					// Cloud context not available (e.g. app class/method not found) or error
					// retrieving
					// properties - do not fail the job, but keep this diagnosable.
					System.err.println("Unable to retrieve cloud context properties: " + e.getMessage());
				}
			}

			// override key-value pairs if provide via command line like
			// --key1=value1,--key2=value2
			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}

			class ContextProcessing {
				private void processContext_0() {
					context.setContextType("DB_HOST", "id_String");
					if (context.getStringValue("DB_HOST") == null) {
						context.DB_HOST = null;
					} else {
						context.DB_HOST = (String) context.getProperty("DB_HOST");
					}
					context.setContextType("DB_NAME", "id_String");
					if (context.getStringValue("DB_NAME") == null) {
						context.DB_NAME = null;
					} else {
						context.DB_NAME = (String) context.getProperty("DB_NAME");
					}
					context.setContextType("DB_PASSWORD", "id_String");
					if (context.getStringValue("DB_PASSWORD") == null) {
						context.DB_PASSWORD = null;
					} else {
						context.DB_PASSWORD = (String) context.getProperty("DB_PASSWORD");
					}
					context.setContextType("DB_PORT", "id_String");
					if (context.getStringValue("DB_PORT") == null) {
						context.DB_PORT = null;
					} else {
						context.DB_PORT = (String) context.getProperty("DB_PORT");
					}
					context.setContextType("DB_USER", "id_String");
					if (context.getStringValue("DB_USER") == null) {
						context.DB_USER = null;
					} else {
						context.DB_USER = (String) context.getProperty("DB_USER");
					}
					context.setContextType("FILE_UCI_BANK", "id_String");
					if (context.getStringValue("FILE_UCI_BANK") == null) {
						context.FILE_UCI_BANK = null;
					} else {
						context.FILE_UCI_BANK = (String) context.getProperty("FILE_UCI_BANK");
					}
					context.setContextType("LOAD_SOURCE_TAG", "id_String");
					if (context.getStringValue("LOAD_SOURCE_TAG") == null) {
						context.LOAD_SOURCE_TAG = null;
					} else {
						context.LOAD_SOURCE_TAG = (String) context.getProperty("LOAD_SOURCE_TAG");
					}
					context.setContextType("PIPELINE_NAME", "id_String");
					if (context.getStringValue("PIPELINE_NAME") == null) {
						context.PIPELINE_NAME = null;
					} else {
						context.PIPELINE_NAME = (String) context.getProperty("PIPELINE_NAME");
					}
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
			if (parentContextMap.containsKey("DB_HOST")) {
				context.DB_HOST = (String) parentContextMap.get("DB_HOST");
			}
			if (parentContextMap.containsKey("DB_NAME")) {
				context.DB_NAME = (String) parentContextMap.get("DB_NAME");
			}
			if (parentContextMap.containsKey("DB_PASSWORD")) {
				context.DB_PASSWORD = (String) parentContextMap.get("DB_PASSWORD");
			}
			if (parentContextMap.containsKey("DB_PORT")) {
				context.DB_PORT = (String) parentContextMap.get("DB_PORT");
			}
			if (parentContextMap.containsKey("DB_USER")) {
				context.DB_USER = (String) parentContextMap.get("DB_USER");
			}
			if (parentContextMap.containsKey("FILE_UCI_BANK")) {
				context.FILE_UCI_BANK = (String) parentContextMap.get("FILE_UCI_BANK");
			}
			if (parentContextMap.containsKey("LOAD_SOURCE_TAG")) {
				context.LOAD_SOURCE_TAG = (String) parentContextMap.get("LOAD_SOURCE_TAG");
			}
			if (parentContextMap.containsKey("PIPELINE_NAME")) {
				context.PIPELINE_NAME = (String) parentContextMap.get("PIPELINE_NAME");
			}
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", ResumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'JOB_00_orquestador_UCI_BANK' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
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

		this.globalResumeTicket = true;// to run tPreJob

		try {
			errorCode = null;
			tPrejob_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tPrejob_2) {
			globalMap.put("tPrejob_2_SUBPROCESS_STATE", -1);

			e_tPrejob_2.printStackTrace();

		}

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		this.globalResumeTicket = true;// to run tPostJob

		try {
			errorCode = null;
			tPostjob_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tPostjob_2) {
			globalMap.put("tPostjob_2_SUBPROCESS_STATE", -1);

			e_tPostjob_2.printStackTrace();

		}

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory)
					+ " bytes memory increase when running : JOB_00_orquestador_UCI_BANK");
		}
		if (enableLogStash) {
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

		if (!isChildJob) {
			// TODO TMC need to make sure the key or provide a way to pass it to job,
			// consider java command length too long risk in windows OS
			String taskId = System.getProperty("tmc.task.id");// not the same key with CBP
			String taskVersion = System.getProperty("tmc.task.version");
			String taskExecId = System.getProperty("tmc.task.execution.id");
			String lineageFilePath = System.getProperty("runtime.lineage.appender.file.path");
			try {
				org.talend.studio.ProducerHelper.newPayload(payloadFilePath, lineageFilePath, taskExecId, taskId,
						taskVersion, lineageDirPath == null ? true : false);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		int returnCode = 0;

		if (!inOSGi) {
			if (needSendForCBP && org.talend.metrics.CBPClient.getInstanceForCurrentVM() != null) {
				s("none");
				if (!isChildJob) {
					org.talend.metrics.CBPClient.getInstanceForCurrentVM().setStatus(status);
				}
				org.talend.metrics.CBPClient.getInstanceForCurrentVM().sendData();
			}
		}

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'JOB_00_orquestador_UCI_BANK' - Finished - status: " + status + " returnCode: "
				+ returnCode);

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

		closeSqlDbConnections();
		// check for orphan threads if still alive after undeploy
		for (java.util.Iterator<Thread> it = threadList.iterator(); it.hasNext();) {
			Thread thread = it.next();
			if (thread != null && thread.isAlive()) {
				boolean suppressLogs = Boolean
						.parseBoolean(System.getProperty("thread.terminate.logs.suppress", "false"));
				if (!suppressLogs) {
					System.err.println(
							"Initiating thread cleanup prior to bundle undeployment. This is a precautionary step to ensure no memory leaks.");
					System.err.println("Forcefully interrupting thread with ID = " + thread.getId()
							+ ". This may result in expected errors due to abrupt termination. Please verify if the thread was performing critical operations.");
				}
				thread.interrupt();
			}
			it.remove();
		}
		// end of destroy()
	}

	private void closeSqlDbConnections() {
		try {
			Object obj_conn;
			obj_conn = globalMap.remove("conn_tDBConnection_2");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
		} catch (java.lang.Exception e) {
		}
	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
		connections.put("conn_tDBConnection_2", globalMap.get("conn_tDBConnection_2"));

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
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
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
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
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
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
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
 * 228013 characters generated by Qlik Talend Cloud Enterprise Edition on the 7
 * de noviembre de 2026, 20:32:21 ECT
 ************************************************************************************************/