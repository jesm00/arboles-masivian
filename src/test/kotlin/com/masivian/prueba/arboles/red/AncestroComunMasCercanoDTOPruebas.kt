package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.databind.JsonMappingException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@DisplayName("AncestroComunMasCercanoDTO")
class AncestroComunMasCercanoDTOPruebas
{
    @Nested
    inner class Serializacion
    {
        @Test
        fun ancestro_comun_con_ancestro_nulo_genera_json_correcto()
        {
            val ancestroComun = AncestroComunMasCercanoDTO(null)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(ancestroComun)
            val jsonEsperado = """
                {
                    "lowest-common-ancestor": null
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }

        @Test
        fun ancestro_comun_con_ancestro_no_nulo_genera_json_correcto()
        {
            val ancestro = 123
            val ancestroComun = AncestroComunMasCercanoDTO(ancestro)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(ancestroComun)
            val jsonEsperado = """
                {
                    "lowest-common-ancestor": $ancestro
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }
    }

    @Nested
    inner class Deserializacion
    {
        @Test
        fun lee_ancestro_comun_con_ancestro_nulo_correctamente()
        {
            val json = """
                {
                    "lowest-common-ancestor": null
                }
            """.trimMargin()
            val ancestroComunEsperado = AncestroComunMasCercanoDTO(null)
            val ancestroComunLeido = ConfiguracionJackson.mapper.readValue(json, AncestroComunMasCercanoDTO::class.java)

            Assertions.assertEquals(ancestroComunEsperado, ancestroComunLeido)
        }

        @Test
        fun lee_ancestro_comun_con_ancestro_no_nulo_correctamente()
        {
            val ancestro = 123
            val json = """
                {
                    "lowest-common-ancestor": $ancestro
                }
            """.trimMargin()
            val ancestroComunEsperado = AncestroComunMasCercanoDTO(ancestro)
            val ancestroComunLeido = ConfiguracionJackson.mapper.readValue(json, AncestroComunMasCercanoDTO::class.java)

            Assertions.assertEquals(ancestroComunEsperado, ancestroComunLeido)
        }

        @Test
        fun lanza_excepcion_correcta_cuando_no_tiene_ancestro()
        {
            val json = """
                {
                }
            """.trimMargin()
            Assertions.assertThrows(JsonMappingException::class.java) {
                ConfiguracionJackson.mapper.readValue(json, AncestroComunMasCercanoDTO::class.java)
            }
        }
    }
}