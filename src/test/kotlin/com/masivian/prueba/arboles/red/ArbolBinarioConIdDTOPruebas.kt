package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@DisplayName("ArbolBinarioConIdDTO")
class ArbolBinarioConIdDTOPruebas
{
    @Nested
    inner class Serializacion
    {
        @Test
        fun arbol_con_id_nulo_genera_json_correcto()
        {
            val arbol = ArbolBinarioDTO(null, null, null)
            val arbolConId = ArbolBinarioConIdDTO(null, arbol)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(arbolConId)
            val jsonEsperado = """
                {
                    "id": null,
                    "tree":
                    {
                        "left": null,
                        "right": null,
                        "node": null
                    }
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }

        @Test
        fun arbol_con_id_no_nulo_genera_json_correcto()
        {
            val id = 123L
            val arbol = ArbolBinarioDTO(null, null, null)
            val arbolConId = ArbolBinarioConIdDTO(id, arbol)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(arbolConId)
            val jsonEsperado = """
                {
                    "id": $id,
                    "tree":
                    {
                        "left": null,
                        "right": null,
                        "node": null
                    }
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }
    }

    @Nested
    inner class Deserializacion
    {
        @Test
        fun lee_arbol_con_id_nulo_correctamente()
        {
            val json = """
                {
                    "id": null,
                    "tree":
                    {
                        "left": null,
                        "right": null,
                        "node": null
                    }
                }
            """.trimMargin()
            val arbolEsperado = ArbolBinarioDTO(null, null, null)
            val arbolConIdEsperado = ArbolBinarioConIdDTO(null, arbolEsperado)
            val arbolConIdLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioConIdDTO::class.java)

            Assertions.assertEquals(arbolConIdEsperado, arbolConIdLeido)
        }

        @Test
        fun lee_arbol_con_id_no_nulo_correctamente()
        {
            val id = 123L
            val json = """
                {
                    "id": $id,
                    "tree":
                    {
                        "left": null,
                        "right": null,
                        "node": null
                    }
                }
            """.trimMargin()
            val arbolEsperado = ArbolBinarioDTO(null, null, null)
            val arbolConIdEsperado = ArbolBinarioConIdDTO(id, arbolEsperado)
            val arbolConIdLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioConIdDTO::class.java)

            Assertions.assertEquals(arbolConIdEsperado, arbolConIdLeido)
        }

        @Test
        fun lee_arbol_sin_id_correctamente()
        {
            val json = """
                {
                    "tree":
                    {
                        "left": null,
                        "right": null,
                        "node": null
                    }
                }
            """.trimMargin()
            val arbolEsperado = ArbolBinarioDTO(null, null, null)
            val arbolConIdEsperado = ArbolBinarioConIdDTO(null, arbolEsperado)
            val arbolConIdLeido = ConfiguracionJackson.mapper.readValue(json, ArbolBinarioConIdDTO::class.java)

            Assertions.assertEquals(arbolConIdEsperado, arbolConIdLeido)
        }

        @Test
        fun lanza_excepcion_correcta_cuando_tree_no_se_envia_arbol()
        {
            val json = """
                {
                    "id": null
                }
            """.trimMargin()
            Assertions.assertThrows(JsonMappingException::class.java) {
                ConfiguracionJackson.mapper.readValue(json, ArbolBinarioConIdDTO::class.java)
            }
        }
    }

    @Nested
    inner class AArbolBinarioConId
    {
        @Test
        fun arbol_con_id_nulo_se_convierte_correctamente()
        {
            val arbolDTO = ArbolBinarioDTO(null, null, null)
            val arbolConIdDTO = ArbolBinarioConIdDTO(null, arbolDTO)
            val arbolConId = arbolConIdDTO.aArbolBinarioConId()
            Assertions.assertNull(arbolConId.id)
            Assertions.assertEquals(arbolDTO.aArbolBinario(), arbolConId.arbol)
        }

        @Test
        fun arbol_con_id_no_nulo_se_convierte_correctamente()
        {
            val id = 123L
            val arbolDTO = ArbolBinarioDTO(null, null, null)
            val arbolConIdDTO = ArbolBinarioConIdDTO(id, arbolDTO)
            val arbolConId = arbolConIdDTO.aArbolBinarioConId()
            Assertions.assertEquals(id, arbolConId.id)
            Assertions.assertEquals(arbolDTO.aArbolBinario(), arbolConId.arbol)
        }
    }

    @Nested
    inner class DesdeArbolBinarioConId
    {
        @Test
        fun arbol_con_id_nulo_se_convierte_correctamente()
        {
            val arbol = ArbolBinario.vacio<Int>()
            val arbolConId = ArbolBinarioConId(null, arbol)
            val arbolConIdDTO = ArbolBinarioConIdDTO(arbolConId)
            val arbolConIdDTOEsperado = ArbolBinarioConIdDTO(null, ArbolBinarioDTO(arbol))
            Assertions.assertEquals(arbolConIdDTOEsperado, arbolConIdDTO)
        }
        @Test
        fun arbol_con_id_no_nulo_se_convierte_correctamente()
        {
            val id = 123L
            val arbol = ArbolBinario.vacio<Int>()
            val arbolConId = ArbolBinarioConId(id, arbol)
            val arbolConIdDTO = ArbolBinarioConIdDTO(arbolConId)
            val arbolConIdDTOEsperado = ArbolBinarioConIdDTO(id, ArbolBinarioDTO(arbol))
            Assertions.assertEquals(arbolConIdDTOEsperado, arbolConIdDTO)
        }
    }
}