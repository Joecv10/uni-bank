<<<<<<< HEAD

# UCI Bank – Propensión a suscribir

## 1. Problema de negocio

El banco realiza una campaña telefónica para promocionar depósitos a plazo. En campañas anteriores, solo ~11% de las llamadas terminaron en suscripción, lo que significa que la mayoría del esfuerzo se gasta en clientes que dirán que no.
Nuestro producto asigna a cada cliente un score de propensión de 0 a 100 y genera una lista de llamadas con el 20% de clientes con mayor score, para que el banco llame primero a quienes tienen más probabilidad de suscribir.

## Nota: Fuente de datos

Se usará bank-additional-full.csv del UCI Machine Learning Repository (41,188 llamadas, 20 variables + y), en lugar de bank-full.csv (45,211 registros) que indican las instrucciones. Según los autores del dataset, agregar los 5 indicadores económicos (emp.var.rate, cons.price.idx, cons.conf.idx, euribor3m, nr.employed) mejora sustancialmente la predicción, incluso sin usar duration.
Se excluirá la variable `duration` del modelo productivo para evitar fuga de información (_data leakage_), ya que solo se conoce después de realizar la llamada.
Esta decisión tiene dos consecuencias: el archivo no incluye balance, por lo que la regla DQ “balance sin negativos anómalos” no aplica; y pdays usa 999 para “no contactado antes”, que transformaremos a -1 según la regla del reto. No es posible combinar ambos archivos porque no comparten una columna identificadora.

### Pendiente

Confirmar qué versión se utilizará según los requisitos del proyecto.

## 3. Arquitectura

- Talend → SingleStore → Snowflake
- Modelo dimensional: fact_calls, dim_client, dim_campaign

## 4. Calidad de datos

- Reglas DQ (mínimo 5)
- % completitud, unicidad, rango
- Umbral definido y resultado medido

## 5. Linaje y catálogo

## 6. Modelo de scoring y KPI

- Propensión 0–100, top 20%
- F1 en el 20% de test (sin usar duration)

## 7. Hallazgos

## 8. Cómo ejecutar el pipeline

## 9. Equipo y roles

## 10. Próximos pasos

=======

> > > > > > > 0d16fb5fd8f4e222a7588901a171f2623485d00c
