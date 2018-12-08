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
