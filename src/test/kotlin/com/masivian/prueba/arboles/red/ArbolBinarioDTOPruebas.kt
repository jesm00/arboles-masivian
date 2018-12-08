package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.ArbolBinario
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@DisplayName("ArbolBinarioDTO")
class ArbolBinarioDTOPruebas
{
    @Nested
    inner class Creacion
    {
        @Test
        fun crea_arbol_con_nulos_correctamente()
        {
            val arbol = ArbolBinarioDTO(null, null, null)
            Assertions.assertNull(arbol.nodo)
            Assertions.assertNull(arbol.izquierdo)
            Assertions.assertNull(arbol.derecho)
        }

        @Test
        fun crea_arbol_sin_nulos_correctamente()
        {
            val nodo = 2
            val vacio = ArbolBinarioDTO(null, null, null)
            val arbol = ArbolBinarioDTO(nodo, vacio.copy(), vacio.copy())
            Assertions.assertEquals(nodo, arbol.nodo)

            Assertions.assertNotNull(arbol.izquierdo)
            Assertions.assertNull(arbol.izquierdo!!.nodo)
            Assertions.assertNull(arbol.izquierdo!!.izquierdo)
            Assertions.assertNull(arbol.izquierdo!!.derecho)

            Assertions.assertNotNull(arbol.derecho)
            Assertions.assertNull(arbol.derecho!!.nodo)
            Assertions.assertNull(arbol.derecho!!.izquierdo)
            Assertions.assertNull(arbol.derecho!!.derecho)
        }

        @Test
        fun lanza_excepcion_correcta_cuando_se_inicializa_con_valores_incorrectos()
        {
            val vacio = ArbolBinarioDTO(null, null, null)
            Assertions.assertThrows(ArbolInvalido::class.java) {
                ArbolBinarioDTO(null, vacio.copy(), vacio.copy())
            }
        }
    }

    @Nested
    inner class Serializacion
    {
        @Test
        fun arbol_con_nulos_genera_json_correcto()
        {
            val arbol = ArbolBinarioDTO(null, null, null)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(arbol)
            val jsonEsperado = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }

        @Test
        fun arbol_sin_nulos_genera_json_correcto()
        {
            val nodo = 2
            val vacio = ArbolBinarioDTO(null, null, null)

            val jsonEsperadoHijos = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            val arbol = ArbolBinarioDTO(nodo, vacio.copy(), vacio.copy())
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(arbol)
            val jsonEsperado = """
                {
                    "left": $jsonEsperadoHijos,
                    "right": $jsonEsperadoHijos,
                    "node": $nodo
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }

        @Test
        fun arbol_con_hijos_no_vacios_genera_json_correcto()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val vacio = ArbolBinarioDTO(null, null, null)

            val jsonEsperadoVacio = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            val izquierdo = ArbolBinarioDTO(nodoIzquierdo, vacio.copy(), vacio.copy())
            val jsonEsperadoIzquierdo = """
                {
                    "left": $jsonEsperadoVacio,
                    "right": $jsonEsperadoVacio,
                    "node": $nodoIzquierdo
                }
            """.trimMargin()

            val derecho = ArbolBinarioDTO(nodoDerecho, vacio.copy(), vacio.copy())
            val jsonEsperadoDerecho = """
                {
                    "left": $jsonEsperadoVacio,
                    "right": $jsonEsperadoVacio,
                    "node": $nodoDerecho
                }
            """.trimMargin()

            val arbol = ArbolBinarioDTO(nodo, izquierdo, derecho)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(arbol)
            val jsonEsperado = """
                {
                    "left": $jsonEsperadoIzquierdo,
                    "right": $jsonEsperadoDerecho,
                    "node": $nodo
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }
    }

    @Nested
    inner class Deserializacion
    {
        @Test
        fun lee_arbol_sin_propiedades_correctamente()
        {
            val json = """
                {
                }
            """.trimMargin()
            val arbolEsperado = ArbolBinarioDTO(null, null, null)
            val arbolLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)

            Assertions.assertEquals(arbolEsperado, arbolLeido)
        }

        @Test
        fun lee_arbol_con_nulos_correctamente()
        {
            val json = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()
            val arbolEsperado = ArbolBinarioDTO(null, null, null)
            val arbolLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)

            Assertions.assertEquals(arbolEsperado, arbolLeido)
        }

        @Test
        fun lee_arbol_sin_nulos_correctamente()
        {
            val nodo = 2

            val jsonHijos = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()
            val json = """
                {
                    "left": $jsonHijos,
                    "right": $jsonHijos,
                    "node": $nodo
                }
            """.trimMargin()
            val vacio = ArbolBinarioDTO(null, null, null)
            val arbolEsperado = ArbolBinarioDTO(nodo, vacio.copy(), vacio.copy())
            val arbolLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)

            Assertions.assertEquals(arbolEsperado, arbolLeido)
        }

        @Test
        fun lee_arbol_con_hijos_vacios_nulos_correctamente()
        {
            val nodo = 2

            val jsonHijos = """
                {}
            """.trimMargin()
            val json = """
                {
                    "left": $jsonHijos,
                    "right": $jsonHijos,
                    "node": $nodo
                }
            """.trimMargin()
            val vacio = ArbolBinarioDTO(null, null, null)
            val arbolEsperado = ArbolBinarioDTO(nodo, vacio.copy(), vacio.copy())
            val arbolLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)

            Assertions.assertEquals(arbolEsperado, arbolLeido)
        }

        @Test
        fun lee_arbol_con_hijos_no_vacios_correctamente()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val vacio = ArbolBinarioDTO(null, null, null)

            val jsonVacio = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            val izquierdo = ArbolBinarioDTO(nodoIzquierdo, vacio.copy(), vacio.copy())
            val jsonIzquierdo = """
                {
                    "left": $jsonVacio,
                    "right": $jsonVacio,
                    "node": $nodoIzquierdo
                }
            """.trimMargin()

            val derecho = ArbolBinarioDTO(nodoDerecho, vacio.copy(), vacio.copy())
            val jsonDerecho = """
                {
                    "left": $jsonVacio,
                    "right": $jsonVacio,
                    "node": $nodoDerecho
                }
            """.trimMargin()
            val json = """
                {
                    "left": $jsonIzquierdo,
                    "right": $jsonDerecho,
                    "node": $nodo
                }
            """.trimMargin()

            val arbolEsperado = ArbolBinarioDTO(nodo, izquierdo, derecho)
            val arbolLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)

            Assertions.assertEquals(arbolEsperado, arbolLeido)
        }

        @Test
        fun lanza_excepcion_correcta_cuando_arbol_tiene_hijo_izquierdo_pero_nodo_null()
        {
            val nodoIzquierdo = 3

            val jsonVacio = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            val jsonIzquierdo = """
                {
                    "left": $jsonVacio,
                    "right": $jsonVacio,
                    "node": $nodoIzquierdo
                }
            """.trimMargin()

            val json = """
                {
                    "left": $jsonIzquierdo,
                    "right": $jsonVacio,
                    "node": null
                }
            """.trimMargin()

            val excepcion = Assertions.assertThrows(JsonMappingException::class.java) {
                ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)
            }
            Assertions.assertTrue(excepcion.cause is ArbolInvalido)
        }

        @Test
        fun lanza_excepcion_correcta_cuando_arbol_tiene_hijo_derecho_pero_nodo_null()
        {
            val nodoDerecho = 4

            val jsonVacio = """
                {
                    "left": null,
                    "right": null,
                    "node": null
                }
            """.trimMargin()

            val jsonDerecho = """
                {
                    "left": $jsonVacio,
                    "right": $jsonVacio,
                    "node": $nodoDerecho
                }
            """.trimMargin()
            val json = """
                {
                    "left": $jsonVacio,
                    "right": $jsonDerecho,
                    "node": null
                }
            """.trimMargin()

            val excepcion = Assertions.assertThrows(JsonMappingException::class.java) {
                ConfiguracionJackson.mapper.readValue(json, ArbolBinarioDTO::class.java)
            }
            Assertions.assertTrue(excepcion.cause is ArbolInvalido)
        }
    }

    @Nested
    inner class AArbolBinario
    {
        @Test
        fun arbol_con_nulos_se_convierte_a_arbol_vacio()
        {
            val arbolDTO = ArbolBinarioDTO(null, null, null)
            val arbol = arbolDTO.aArbolBinario()
            Assertions.assertTrue(arbol.esVacio)
        }

        @Test
        fun arbol_con_nodo_e_hijos_nulos_se_convierte_a_arbol_correcto()
        {
            val nodo = 2
            val arbolDTO = ArbolBinarioDTO(nodo, null, null)
            val arbol = arbolDTO.aArbolBinario()

            Assertions.assertFalse(arbol.esVacio)
            Assertions.assertEquals(nodo, arbol.nodo)
            Assertions.assertNotNull(arbol.hijoIzquierdo)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.esVacio)
            Assertions.assertNotNull(arbol.hijoDerecho)
            Assertions.assertTrue(arbol.hijoDerecho!!.esVacio)
        }

        @Test
        fun arbol_con_nodo_e_hijos_vacios_se_convierte_a_arbol_correcto()
        {
            val nodo = 2
            val arbolVacioDTO = ArbolBinarioDTO(null, null, null)
            val arbolDTO = ArbolBinarioDTO(nodo, arbolVacioDTO.copy(), arbolVacioDTO.copy())
            val arbol = arbolDTO.aArbolBinario()

            Assertions.assertFalse(arbol.esVacio)
            Assertions.assertEquals(nodo, arbol.nodo)
            Assertions.assertNotNull(arbol.hijoIzquierdo)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.esVacio)
            Assertions.assertNotNull(arbol.hijoDerecho)
            Assertions.assertTrue(arbol.hijoDerecho!!.esVacio)
        }

        @Test
        fun arbol_sin_nulos_se_convierte_a_arbol_correcto()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val arbolVacioDTO = ArbolBinarioDTO(null, null, null)
            val arbolIzquierdoDTO = ArbolBinarioDTO(nodoIzquierdo,null, null)
            val arbolDerechoDTO = ArbolBinarioDTO(nodoDerecho, arbolVacioDTO.copy(), arbolVacioDTO.copy())
            val arbolDTO = ArbolBinarioDTO(nodo, arbolIzquierdoDTO, arbolDerechoDTO)

            val arbol = arbolDTO.aArbolBinario()

            Assertions.assertFalse(arbol.esVacio)
            Assertions.assertEquals(nodo, arbol.nodo)

            Assertions.assertNotNull(arbol.hijoIzquierdo)
            Assertions.assertFalse(arbol.hijoIzquierdo!!.esVacio)
            Assertions.assertEquals(nodoIzquierdo, arbol.hijoIzquierdo!!.nodo)
            Assertions.assertNotNull(arbol.hijoIzquierdo!!.hijoIzquierdo)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.hijoIzquierdo!!.esVacio)
            Assertions.assertNotNull(arbol.hijoIzquierdo!!.hijoDerecho)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.hijoDerecho!!.esVacio)

            Assertions.assertNotNull(arbol.hijoDerecho)
            Assertions.assertFalse(arbol.hijoDerecho!!.esVacio)
            Assertions.assertEquals(nodoDerecho, arbol.hijoDerecho!!.nodo)
            Assertions.assertNotNull(arbol.hijoDerecho!!.hijoIzquierdo)
            Assertions.assertTrue(arbol.hijoDerecho!!.hijoIzquierdo!!.esVacio)
            Assertions.assertNotNull(arbol.hijoDerecho!!.hijoDerecho)
            Assertions.assertTrue(arbol.hijoDerecho!!.hijoDerecho!!.esVacio)
        }
    }


    @Nested
    inner class DesdeArbolBinario
    {
        @Test
        fun arbol_vacio_se_convierte_a_arbol_con_nulos()
        {
            val arbol = ArbolBinario.vacio<Int>()
            val arbolDTO = ArbolBinarioDTO(arbol)
            val arbolDTOEsperado = ArbolBinarioDTO(null, null, null)
            Assertions.assertEquals(arbolDTOEsperado, arbolDTO)
        }

        @Test
        fun arbol_con_nodo_e_hijos_vacios_se_convierte_a_arbol_correcto()
        {
            val nodo = 2
            val arbol = ArbolBinario.nodo(nodo, ArbolBinario.vacio(), ArbolBinario.vacio())
            val arbolDTO = ArbolBinarioDTO(arbol)
            val arbolVacioDTO = ArbolBinarioDTO(null, null, null)
            val arbolDTOEsperado = ArbolBinarioDTO(nodo, arbolVacioDTO, arbolVacioDTO)
            Assertions.assertEquals(arbolDTOEsperado, arbolDTO)
        }

        @Test
        fun arbol_sin_nulos_se_convierte_a_arbol_correcto()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val arbol = ArbolBinario.nodo(
                nodo,
                ArbolBinario.nodo(
                    nodoIzquierdo,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoDerecho,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )
            val arbolDTO = ArbolBinarioDTO(arbol)
            val arbolVacioDTO = ArbolBinarioDTO(null, null, null)
            val arbolDTOEsperado = ArbolBinarioDTO(
                nodo,
                ArbolBinarioDTO(
                    nodoIzquierdo,
                    arbolVacioDTO,
                    arbolVacioDTO
                ),
                ArbolBinarioDTO(
                    nodoDerecho,
                    arbolVacioDTO,
                    arbolVacioDTO
                )
            )
            Assertions.assertEquals(arbolDTOEsperado, arbolDTO)
        }
    }
}