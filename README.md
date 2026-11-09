# UCI Bank – Propensión a suscribir

## 1. Problema de negocio

El banco realiza una campaña telefónica para promocionar depósitos a plazo. En campañas anteriores, solo ~11% de las llamadas terminaron en suscripción, lo que significa que la mayoría del esfuerzo se gasta en clientes que dirán que no.
Nuestro producto asigna a cada cliente un score de propensión de 0 a 100 y genera una lista de llamadas con el 20% de clientes con mayor score, para que el banco llame primero a quienes tienen más probabilidad de suscribir.

## Nota: Fuente de datos

Se usará bank-additional-full.csv del UCI Machine Learning Repository (41,188 llamadas, 20 variables + y), en lugar de bank-full.csv (45,211 registros) que indican las instrucciones. Según los autores del dataset, agregar los 5 indicadores económicos (emp.var.rate, cons.price.idx, cons.conf.idx, euribor3m, nr.employed) mejora sustancialmente la predicción, incluso sin usar duration.
Se excluirá la variable `duration` del modelo productivo para evitar fuga de información (_data leakage_), ya que solo se conoce después de realizar la llamada.
Esta decisión tiene dos consecuencias: el archivo no incluye balance, por lo que la regla DQ “balance sin negativos anómalos” no aplica; y pdays usa 999 para “no contactado antes”, que transformaremos a -1 según la regla del reto. No es posible combinar ambos archivos porque no comparten una columna identificadora.

## 3. Arquitectura

- Talend → SingleStore → Snowflake
- Modelo dimensional: fact_calls, dim_client, dim_campaign

## 4. Calidad de datos

## Calidad de datos (DQ)

**Data Steward:** Grupo 3 · **Dataset:** `bank-additional-full.csv` (UCI Bank Marketing, 41 188 registros, 20 variables + `y`)
**Último run medido:** `run_20261107_174849`

### 1. Enfoque

Seguimos el flujo de DAMA-DMBOK (cap. 13): **perfilado → reglas → score → comparación contra umbral**.

1. **Perfilado** (`J01_ingest_raw_bank`, `J02_profile_raw_bank`) sobre la capa raw: nulos, `unknown`, cardinalidad, mín/máx y frecuencias por columna.
2. **Reglas DQ** en `JOB_02_quality` (`tUniqRow` + `tMap_DQ`): cada registro se evalúa contra 11 reglas.
3. **Score por registro** → tabla `dq_record_g3` (1 = cumple, 0 = no cumple, por regla).
4. **Scorecard por regla** → vista `v_dq_scorecard_g3`, guardada en cada ejecución en `dq_scorecard_g3` con su `run_id`, para ver la tendencia entre ejecuciones.

Fórmula (DMBOK, §2.7.2): `% válido = (registros que cumplen / registros evaluados) × 100`.

### 2. Reglas y umbrales

| ID  | Regla                                               | Dimensión   | Umbral   | Por qué este umbral                                                           |
| --- | --------------------------------------------------- | ----------- | -------- | ----------------------------------------------------------------------------- |
| r01 | Los 21 campos no son nulos ni vacíos                | Completitud | 100 %    | Un valor faltante rompe el entrenamiento del modelo                           |
| r02 | Sin registros completamente duplicados (`tUniqRow`) | Unicidad    | 99.5 %   | Los duplicados pueden caer en train y test e inflar el F1                     |
| r03 | `age` entre 18 y 100                                | Rango       | 99.5 %   | Variable de entrada del modelo                                                |
| r04 | `duration` ≥ 0                                      | Rango       | 99 %     | Solo para análisis: **excluida del modelo** (se conoce después de la llamada) |
| r05 | `campaign` ≥ 1 y `previous` ≥ 0                     | Rango       | 99.5 %   | Variable de entrada del modelo                                                |
| r06 | `pdays` ≥ 0 o `999` (= no contactado antes)         | Rango       | 99.5 %   | Variable de entrada del modelo                                                |
| r07 | `y` ∈ {yes, no}                                     | Validez     | 100 %    | Es la variable objetivo: un `y` erróneo enseña respuestas falsas al modelo    |
| r08 | `month` ∈ jan…dec                                   | Validez     | 99.5 %   | Variable de entrada del modelo                                                |
| r09 | `day_of_week` ∈ mon…fri                             | Validez     | 99.5 %   | Variable de entrada del modelo                                                |
| r10 | `poutcome` ∈ {success, failure, nonexistent}        | Validez     | 99.5 %   | Variable de entrada del modelo                                                |
| r11 | `contact` ∈ {cellular, telephone}                   | Validez     | 99.5 %   | Variable de entrada del modelo                                                |
| —   | **Registros que cumplen todas las reglas**          | Global      | **98 %** | Umbral global de calidad del dataset                                          |

Los umbrales se fijaron según el impacto de cada regla en el uso de los datos (el modelo de propensión), como recomienda DMBOK (§2.7.1, reglas "tied to business impact").

### 3. Resultados (`run_20261107_174849`)

| Regla                  | Dimensión   |    Cumplen | Fallan |         % | Umbral | Resultado |
| ---------------------- | ----------- | ---------: | -----: | --------: | -----: | :-------: |
| r01_completitud        | Completitud |     41 176 |      0 |    100.00 |    100 |   PASS    |
| r02_unicidad           | Unicidad    |     41 176 |     12 |     99.97 |   99.5 |   PASS    |
| r03_edad               | Rango       |     41 171 |      5 |     99.99 |   99.5 |   PASS    |
| r04_duracion           | Rango       |     41 176 |      0 |    100.00 |     99 |   PASS    |
| r05_campania           | Rango       |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| r06_pdays              | Rango       |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| r07_objetivo           | Validez     |     41 176 |      0 |    100.00 |    100 |   PASS    |
| r08_mes                | Validez     |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| r09_dia_semana         | Validez     |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| r10_poutcome           | Validez     |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| r11_contacto           | Validez     |     41 176 |      0 |    100.00 |   99.5 |   PASS    |
| **TOTAL registros OK** | Global      | **41 171** |  **5** | **99.99** | **98** | **PASS**  |

**Resumen de los campos clave:** completitud **100 %** · unicidad **99.97 %** · rango **≥ 99.99 %** en todas las reglas.

Conteo del flujo: 41 188 leídos → 41 176 únicos (12 duplicados descartados) → **41 171 OK** (`stg_bank_OK_g3`) + **5 NOTOK** (`stg_bank_NOTOK_g3`).

### 4. Hallazgos del perfilado

- **5 registros con `age` = 17** (mín. del perfil): fallan r03 y van a `stg_bank_NOTOK_g3` con su código de regla, motivo, responsable y estado de remediación.
- **12 duplicados exactos** en las 21 columnas, eliminados por `tUniqRow`. Son registros idénticos; no afirmamos que sean el mismo cliente.
- **`unknown` es una categoría válida del dataset** (`bank-additional-names.txt`), no un nulo: `default` 20.87 %, `education` 4.20 %, `housing` 2.40 %, `loan` 2.40 %, `job` 0.80 %, `marital` 0.19 %. Se conserva como categoría propia.
- **`pdays` = 999 en el 96.32 %** de los registros: significa "no contactado antes", por lo que se valida como valor especial y no como fuera de rango.
- **`month` tiene solo 10 valores**: no hay llamadas en enero ni febrero. La regla r08 acepta los 12 meses, por lo que esto no se considera error.
- **Desbalance del objetivo:** solo el **11.27 %** respondió `yes`, por lo que usamos F1 como KPI.
- **`duration` se excluye del modelo productivo** (solo se conoce después de la llamada, según la documentación del dataset); se mantiene solo para análisis.
- **Variables económicas** (`emp_var_rate`, `cons_price_idx`, `cons_conf_idx`, `euribor3m`, `nr_employed`): se validan por completitud, sin inventar límites de rango.

### 5. Relación con el scoring del modelo

El modelo de propensión (`modelo.py` / `scoring_bank.py`) **solo usa los registros que pasan todas las reglas** (`stg_bank_OK_g3`) y **no usa `duration`**: el script se detiene si la encuentra entre las variables. Los 41 171 registros OK tienen un score 0–100 en `stg_bank_OK_score_g3`, y el 20 % con mayor score queda marcado como lista prioritaria de llamadas (`top20`).

### 6. Objetos en SingleStore (`DSADEV`)

| Objeto              | Qué contiene                                                                                  |
| ------------------- | --------------------------------------------------------------------------------------------- |
| `dq_record_g3`      | Resultado de cada regla por registro (0/1), `rules_passed`, `dq_score`, `dq_status`, `run_id` |
| `v_dq_scorecard_g3` | Vista que calcula el % por regla y lo compara con el umbral                                   |
| `dq_scorecard_g3`   | Scorecard guardado en cada ejecución (histórico por `run_id`)                                 |
| `stg_bank_NOTOK_g3` | Registros rechazados, con código de regla, motivo, responsable y estado de remediación        |

Consultar el último scorecard:

```sql
SELECT * FROM dq_scorecard_g3
WHERE run_id = (SELECT MAX(run_id) FROM dq_scorecard_g3)
ORDER BY regla;
```

### 7. Notas y limitaciones

- `run_id` usa la hora de Ecuador (UTC−5); `load_ts` usa la hora del servidor (UTC).
- En `stg_bank_NOTOK_g3`, `codigo_regla` guarda **la primera** regla que falla. El detalle completo de todas las reglas por registro está en `dq_record_g3`.
- La unicidad se mide antes del tMap con los contadores de `tUniqRow`, por eso se agrega al scorecard en un paso aparte (`tDBR_SCORECARD`).

## 5. Linaje y catálogo

## 6. Modelo de scoring y KPI

- Propensión 0–100, top 20%
- F1 en el 20% de test (sin usar duration)

## 7. Hallazgos

## 8. Cómo ejecutar el pipeline

## 9. Equipo y roles

## 10. Próximos pasos

=======
