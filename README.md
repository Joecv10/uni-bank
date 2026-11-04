## Nota: Selección del dataset

### Dataset seleccionado
Se utilizará inicialmente `bank-full.csv` del repositorio UCI Bank Marketing, que contiene **45.211 registros y 17 columnas**.

### Diferencia identificada
El enunciado menciona las variables económicas `emp.var.rate`, `cons.price.idx` y `euribor3m`, que pertenecen a `bank-additional-full.csv`, una versión diferente que contiene **41.188 registros y 21 columnas** (incluyendo el target).

### Pendiente
Confirmar qué versión se utilizará según los requisitos del proyecto.

### Consideración para el modelo
Se excluirá la variable `duration` del modelo productivo para evitar fuga de información (*data leakage*), ya que solo se conoce después de realizar la llamada.
