/* ============================================================
   PROYECTO: UCI BANK MARKETING
   GRUPO: G3 
   PROCESO: ETL CON TALEND DATA INTEGRATION

   ESTRUCTURA DEL PROCESO:

   1. RAW: Carga de datos originales desde el CSV.
   2. STG: Separación de registros válidos (OK) e inválidos (NOTOK).
   3. MART: Creación de dimensiones.
   4. MART: Creación de tabla de hechos.
   5. MART: Carga de dimensiones y tabla de hechos.
   ============================================================ */


/* ============================================================
   1. CAPA RAW - CARGA DE DATOS ORIGINALES
   ============================================================

   Los datos originales del archivo CSV de UCI Bank Marketing
   se cargan directamente mediante Talend.

   La creación de esta tabla es automática al cargar el csv.

CREATE TABLE IF NOT EXISTS raw_bank_g3 (
    age INT(3),
    job VARCHAR(13),
    marital VARCHAR(8),
    education VARCHAR(19),
    has_default VARCHAR(7),
    housing VARCHAR(7),
    loan VARCHAR(7),
    contact VARCHAR(20),
    month VARCHAR(3),
    day_of_week VARCHAR(3),
    duration INT(4),
    campaign INT(1),
    pdays INT(3),
    previous INT(1),
    poutcome VARCHAR(11),
    emp_var_rate FLOAT(10,4),
    cons_price_idx FLOAT(10,4),
    cons_conf_idx DOUBLE(10,4),
    euribor3m FLOAT(10,4),
    nr_employed DOUBLE(12,4),
    y VARCHAR(3),
    load_ts DATETIME,
    load_source VARCHAR(60)
);
*/


/* ============================================================
   2. CAPA STG - TABLAS DE DATOS PROCESADOS
   ============================================================

   En esta etapa los registros provenientes de RAW se
   procesan y clasifican en dos grupos:

   - OK: Registros válidos que continúan hacia la capa MART.
   - NOTOK: Registros inválidos que se almacenan para su
            identificación y posterior remediación.
*/


/* ------------------------------------------------------------
   2.1. STG - TABLA DE REGISTROS VÁLIDOS (OK)
   ------------------------------------------------------------ */

CREATE TABLE IF NOT EXISTS stg_bank_OK_g3 (
    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    age INT(3) DEFAULT NULL,
    job VARCHAR(13),
    marital VARCHAR(8),
    education VARCHAR(19),
    has_default VARCHAR(7),
    housing VARCHAR(7),
    loan VARCHAR(7),
    contact VARCHAR(20),
    month VARCHAR(3),
    day_of_week VARCHAR(3),
    duration INT(4),
    campaign INT(1),
    pdays INT(3),
    previous INT(1),
    poutcome VARCHAR(11),
    emp_var_rate FLOAT(10,4),
    cons_price_idx FLOAT(10,4),
    cons_conf_idx DOUBLE(10,4),
    euribor3m FLOAT(10,4),
    nr_employed DOUBLE(12,4),
    y VARCHAR(3),
    load_ts DATETIME,
    load_source VARCHAR(60)
);


/* ------------------------------------------------------------
   2.2. STG - TABLA DE REGISTROS INVÁLIDOS (NOTOK)
   ------------------------------------------------------------ */

CREATE TABLE IF NOT EXISTS stg_bank_NOTOK_g3 (
    source_call_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    age INT(3) DEFAULT NULL,
    job VARCHAR(13),
    marital VARCHAR(8),
    education VARCHAR(19),
    has_default VARCHAR(7),
    housing VARCHAR(7),
    loan VARCHAR(7),
    contact VARCHAR(20),
    month VARCHAR(3),
    day_of_week VARCHAR(3),
    duration INT(4),
    campaign INT(1),
    pdays INT(3),
    previous INT(1),
    poutcome VARCHAR(11),
    emp_var_rate FLOAT(10,4),
    cons_price_idx FLOAT(10,4),
    cons_conf_idx DOUBLE(10,4),
    euribor3m FLOAT(10,4),
    nr_employed DOUBLE(12,4),
    y VARCHAR(3),
    codigo_regla VARCHAR(15),
    motivo_rechazo VARCHAR(255),
    responsable_remediacion VARCHAR(60),
    estado_remediacion VARCHAR(20),
    load_ts DATETIME,
    load_source VARCHAR(60)
);


/* ============================================================
   3. CAPA MART - CREACIÓN DE DIMENSIONES
   ============================================================

   Se construye el modelo dimensional mediante dos tablas:

   - DIM_CLIENT: Almacena las características de los clientes.
   - DIM_CAMPAIGN: Almacena las características de las
                   campañas y el contexto económico.

   Ambas dimensiones utilizan claves sustitutas generadas
   automáticamente mediante AUTO_INCREMENT.
*/


/* ------------------------------------------------------------
   3.1. MART - DIMENSIÓN DE CLIENTES
   ------------------------------------------------------------ */

CREATE TABLE IF NOT EXISTS mart_dim_client_g3 (
    client_key INT AUTO_INCREMENT PRIMARY KEY,
    age INT,
    job VARCHAR(50),
    marital VARCHAR(30),
    education VARCHAR(50),
    has_default VARCHAR(10),
    housing VARCHAR(10),
    loan VARCHAR(10),
    load_ts DATETIME,
    load_source VARCHAR(100)
) ENGINE=InnoDB;


/* ------------------------------------------------------------
   3.2. MART - DIMENSIÓN DE CAMPAÑAS
   ------------------------------------------------------------ */

CREATE TABLE IF NOT EXISTS mart_dim_campaign_g3 (
    campaign_key INT AUTO_INCREMENT PRIMARY KEY,
    contact VARCHAR(30),
    month VARCHAR(15),
    day_of_week VARCHAR(15),
    campaign INT,
    pdays INT,
    previous INT,
    poutcome VARCHAR(30),
    emp_var_rate FLOAT(10,4),
    cons_price_idx FLOAT(10,4),
    cons_conf_idx DOUBLE(10,4),
    euribor3m FLOAT(10,4),
    nr_employed DOUBLE(12,4),
    load_ts DATETIME,
    load_source VARCHAR(100)
) ENGINE=InnoDB;


/* ============================================================
   4. CAPA MART - CREACIÓN DE TABLA DE HECHOS
   ============================================================

   La tabla de hechos almacena la información consolidada
   de cada llamada.

   Contiene:

   - Identificador de la llamada original.
   - Claves de las dimensiones de clientes y campañas.
   - Duración y resultado de la llamada.
   - Resultados del modelo predictivo.
   - Información de scoring y trazabilidad de carga.
*/


/* ------------------------------------------------------------
   4.1. MART - TABLA DE HECHOS DE LLAMADAS
   ------------------------------------------------------------ */

CREATE TABLE mart_fact_calls_g3 (
    call_key BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_call_id BIGINT NOT NULL,
    client_key INT NOT NULL,
    campaign_key INT NOT NULL,
    duration INT,
    subscribed VARCHAR(10),
    score DECIMAL(5,2),
    prediccion TINYINT,
    top20 TINYINT,
    decil INT,
    scored_at DATETIME,
    load_ts DATETIME,
    load_source VARCHAR(100)
);


/* ============================================================
   5. CAPA MART - CARGA DE DIMENSIONES
   ============================================================

   Se insertan los registros válidos provenientes de STG
   en las dimensiones correspondientes.

   Se utiliza SELECT DISTINCT para obtener combinaciones
   únicas y NOT EXISTS para evitar insertar registros
   que ya se encuentren almacenados.

   El operador <=> permite comparar valores incluyendo NULL.
*/


/* ------------------------------------------------------------
   5.1. CARGA DE LA DIMENSIÓN DE CLIENTES
   ------------------------------------------------------------ */

INSERT INTO mart_dim_client_g3 (
    age,
    job,
    marital,
    education,
    has_default,
    housing,
    loan,
    load_ts,
    load_source
)
SELECT DISTINCT
    b.age,
    b.job,
    b.marital,
    b.education,
    b.has_default,
    b.housing,
    b.loan,
    '" + TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate()) + "',
    'G3_UCI_BANK'
FROM stg_bank_OK_g3 b
WHERE NOT EXISTS (
    SELECT 1
    FROM mart_dim_client_g3 d
    WHERE d.age <=> b.age
      AND d.job <=> b.job
      AND d.marital <=> b.marital
      AND d.education <=> b.education
      AND d.has_default <=> b.has_default
      AND d.housing <=> b.housing
      AND d.loan <=> b.loan
);


/* ------------------------------------------------------------
   5.2. CARGA DE LA DIMENSIÓN DE CAMPAÑAS
   ------------------------------------------------------------ */

INSERT INTO mart_dim_campaign_g3 (
    contact,
    month,
    day_of_week,
    campaign,
    pdays,
    previous,
    poutcome,
    emp_var_rate,
    cons_price_idx,
    cons_conf_idx,
    euribor3m,
    nr_employed,
    load_ts,
    load_source
)
SELECT DISTINCT
    b.contact,
    b.month,
    b.day_of_week,
    b.campaign,
    b.pdays,
    b.previous,
    b.poutcome,
    b.emp_var_rate,
    b.cons_price_idx,
    b.cons_conf_idx,
    b.euribor3m,
    b.nr_employed,
    '" + TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate()) + "',
    'G3_UCI_BANK'
FROM stg_bank_OK_g3 b
WHERE NOT EXISTS (
    SELECT 1
    FROM mart_dim_campaign_g3 d
    WHERE d.contact <=> b.contact
      AND d.month <=> b.month
      AND d.day_of_week <=> b.day_of_week
      AND d.campaign <=> b.campaign
      AND d.pdays <=> b.pdays
      AND d.previous <=> b.previous
      AND d.poutcome <=> b.poutcome
      AND d.emp_var_rate <=> b.emp_var_rate
      AND d.cons_price_idx <=> b.cons_price_idx
      AND d.cons_conf_idx <=> b.cons_conf_idx
      AND d.euribor3m <=> b.euribor3m
      AND d.nr_employed <=> b.nr_employed
);


/* ============================================================
   6. CAPA MART - CARGA DE TABLA DE HECHOS
   ============================================================

   Se construye la tabla de hechos utilizando los registros
   válidos de stg_bank_OK_g3.

   Se realizan las siguientes operaciones:

   1. Recuperación de los registros válidos.
   2. Asociación con la dimensión de clientes.
   3. Asociación con la dimensión de campañas.
   4. Incorporación de los resultados del scoring.
   5. Inserción de la información consolidada en la tabla
      de hechos.

   INNER JOIN: Relaciona las llamadas con sus dimensiones.

   LEFT JOIN: Incorpora los resultados predictivos cuando
              existe información disponible.
*/


/* ------------------------------------------------------------
   6.1. CARGA DE LA TABLA DE HECHOS DE LLAMADAS
   ------------------------------------------------------------ */

INSERT INTO mart_fact_calls_g3 (
    source_call_id,
    client_key,
    campaign_key,
    duration,
    subscribed,
    score,
    prediccion,
    top20,
    decil,
    scored_at,
    load_ts,
    load_source
)
SELECT
    b.source_call_id,
    dc.client_key,
    dca.campaign_key,
    b.duration,
    b.y,
    s.score,
    s.prediccion,
    s.top20,
    s.decil,
    s.scored_at,
    '" + TalendDate.formatDate("yyyy-MM-dd HH:mm:ss", TalendDate.getCurrentDate()) + "',
    'G3_UCI_BANK'
FROM stg_bank_OK_g3 b

INNER JOIN mart_dim_client_g3 dc
    ON dc.age <=> b.age
    AND dc.job <=> b.job
    AND dc.marital <=> b.marital
    AND dc.education <=> b.education
    AND dc.has_default <=> b.has_default
    AND dc.housing <=> b.housing
    AND dc.loan <=> b.loan

INNER JOIN mart_dim_campaign_g3 dca
    ON dca.contact <=> b.contact
    AND dca.month <=> b.month
    AND dca.day_of_week <=> b.day_of_week
    AND dca.campaign <=> b.campaign
    AND dca.pdays <=> b.pdays
    AND dca.previous <=> b.previous
    AND dca.poutcome <=> b.poutcome
    AND dca.emp_var_rate <=> b.emp_var_rate
    AND dca.cons_price_idx <=> b.cons_price_idx
    AND dca.cons_conf_idx <=> b.cons_conf_idx
    AND dca.euribor3m <=> b.euribor3m
    AND dca.nr_employed <=> b.nr_employed

LEFT JOIN stg_bank_OK_score_g3 s
    ON s.source_call_id = b.source_call_id;


/* ============================================================
   FIN DEL SCRIPT
   PROYECTO: UCI BANK MARKETING - GRUPO G3
   ============================================================ */