# Prueba Masivian: Árboles
## Despligue
Servicio configurado para desplegarse en aws lambda. Actualmente disponible en https://gdefe0h3f3.execute-api.us-east-1.amazonaws.com/Prod/trees/
## Entidades

### Árbol
Propiedades:

- node:
    - Tipo: Int
    - Nullable: Sí
    - Descripción: Nodo contenido en este fragmento del árbol

- left:
    - Tipo: Árbol
    - Nullable: Sí
    - Descripción: Árbol izquierdo hijo

- right:
    - Tipo: Árbol
    - Nullable: Sí
    - Descripción: Árbol derecho hijo

Ejemplos:

    - Árbol vacío:
        {}
    - Árbol vacío explicito:
        {
            "node": null,
            "left": null,
            "right": null
        }
    - Árbol no vacío:
        {
            "node": 1,
            "left":
            {
                "node": 2,
                "left": {},
                "right": {}
            },
            "right":
            {
                "node": 3,
                "left": {},
                "right": {}
            }
        }

### Árbol con id:

Respresenta un árbol creado en el sistema con su id correspondiente.

Propiedades:

- id:
    - Tipo: Long
    - Nullable: No
    - Descripción: Id que identifica el árbol como recurso

- tree:
    - Tipo: Árbol
    - Nullable: No
    - Descripción: Árbol asociado

Ejemplos:

    - Árbol vacío:
        {
            "id": 3,
            "tree":
            {
                "node": null,
                "left": null,
                "right": null
            }
        }
    - Árbol no vacío:
        {
            "id": 3,
            "tree":
            {
                "node": 1,
                "left":
                {
                    "node": 2,
                    "left": {},
                    "right": {}
                },
                "right":
                {
                    "node": 3,
                    "left": {},
                    "right": {}
                }
            }
        }

### Ancestro común más cercano:

Respresenta un ancestro común más cercano entre dos nodos.

Propiedades:

- lowest-common-ancestor:
    - Tipo: Int
    - Nullable: Sí
    - Descripción: Ancestro común más cercano encontrado o null de no existir un ancestro común

Ejemplos:

    - Ancestro encontrado:
        {
            "lowest-common-ancestor": 3
        }

    - Ancestro no encontrado:
        {
            "lowest-common-ancestor": null
        }

### Error

Propiedades:
- message
    - Tipo: String
    - Nullable: Sí
    - Descripción: Mensaje de error para errores controlados

## Servicios

### Crear árbol
- Descripción: Permite crear un árbol en el sistema y retorna el árbol creado con el id asignado
- Ruta: /trees
- Método: POST
- Entradas:
    - Body: Árbol a crear. Debe ser de tipo árbol
    - Query: N/A
    - Path: N/A
    - Headers esperados:
        - Content-Type: application/json
        - Accept: application/json
- Respuestas:
    - Exitosa:
        - Body: Árbol con id
        - Código http: 200
    - Error:
        - Body: Error
        - Código http: 500 o 400 dependiendo del error

### Consultar ancestro común más cercano
- Descripción: Permite consultar el ancestro común más cercano entre dos nodos dados
- Ruta: /trees/{id_arbol}/lowest-common-ancestor/?initial-node={initial-node}&final-node={final-node}
- Método: POST
- Entradas:
    - Body: N/A
    - Query:
        - initial-node: Uno de los nodos para los que se va a buscar el ancestro común más cercano. Debe ser un entero
        - final-node: Uno de los nodos para los que se va a buscar el ancestro común más cercano. Debe ser un entero
    - Path:
        - id_arbol: Id del árbol sobre el que se va a buscar el ancestro común más cercano
    - Headers esperados:
        - Content-Type: application/json
        - Accept: application/json
- Respuestas:
    - Exitosa:
        - Body: Ancestro común más cercano
        - Código http: 200
    - Árbol no encontrado:
        - Body: Error
        - Código http: 500 o 400 dependiendo del error
    - Error:
        - Body: Error
        - Código http: 500 o 400 dependiendo del error