package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId
import com.masivian.prueba.arboles.dao.ErrorDesconocidoBD
import com.masivian.prueba.arboles.dao.ErrorMapeoBD
import com.masivian.prueba.arboles.dao.RepositorioArboles
import com.masivian.prueba.arboles.red.*
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import java.net.URI
import java.util.*
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

@DisplayName("RecursoAncestroComunMasCercano")
class RecursoAncestroComunMasCercanoPruebas
{
    companion object
    {
        private val BASE_URI: URI = UriBuilder.fromUri("http://0.0.0.0/").port(80).build()
    }

    private val idArbol = 123L

    @Nested
    inner class PorRed
    {
        private val mockRecursoAncestroComunMasCercano: RecursoAncestroComunMasCercano = mock(RecursoAncestroComunMasCercano::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
        }

        private val mockRecursoArbol: RecursoArbol = mock(RecursoArbol::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
        }.apply {
            doReturn(mockRecursoAncestroComunMasCercano)
                .`when`(this)
                .darRecursoAncestroComunMasCercano()
        }

        private val mockRecursoArboles: RecursoArboles = mock(RecursoArboles::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
        }.apply {
            doReturn(mockRecursoArbol)
                .`when`(this)
                .darRecursoArbolEspecifico(idArbol)
        }

        private val servidorGrizzly by lazy {
            GrizzlyHttpServerFactory.createHttpServer(BASE_URI, ConfiguracionJersey.darAplicacion())
        }

        private val clientePruebas = ClientBuilder.newClient()
            .register(JacksonJaxbJsonProvider().apply {
                setMapper(ConfiguracionJackson.mapper)
            })
            .target(BASE_URI)

        @BeforeEach
        fun arrancarServidorPruebas()
        {
            ConfiguracionJersey.recursoArboles = mockRecursoArboles
            servidorGrizzly.start()
        }

        @AfterEach
        fun pararServidorPruebas()
        {
            servidorGrizzly.shutdownNow()
        }

        @[Nested DisplayName("Al consultar LCA")]
        inner class Consultar
        {
            @Test
            fun llama_la_funcion_consultar_y_retorna_objeto_correcto_como_json_cuando_recurso_retorna_correctamente()
            {
                val nodoInicial = 1
                val nodoFinal = 2
                val ancestroComunMasCercanoDTO = AncestroComunMasCercanoDTO(1)
                Mockito.doReturn(ancestroComunMasCercanoDTO)
                    .`when`(mockRecursoAncestroComunMasCercano)
                    .darAncestroComunMasCercano(nodoInicial, nodoFinal)

                val responseArbolRecibido: Response = clientePruebas
                    .path("${RecursoArboles.RUTA}/$idArbol/${RecursoAncestroComunMasCercano.RUTA}")
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_INICIAL, nodoInicial)
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_FINAL, nodoFinal)
                    .request()
                    .get()

                Assertions.assertEquals(Response.Status.OK.statusCode, responseArbolRecibido.status)
                Assertions.assertEquals(ancestroComunMasCercanoDTO, responseArbolRecibido.readEntity(AncestroComunMasCercanoDTO::class.java))

                Mockito.verify(mockRecursoAncestroComunMasCercano, Mockito.times(1)).darAncestroComunMasCercano(nodoInicial, nodoFinal)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_ParametroInvalido()
            {
                val mensajeError = "Mensaje de pruebas"
                val errorEsperado = ErrorDTO(mensajeError)
                val nodoInicial = 1
                val nodoFinal = 2

                Mockito.doThrow(ParametroInvalido(mensajeError))
                    .`when`(mockRecursoAncestroComunMasCercano)
                    .darAncestroComunMasCercano(nodoInicial, nodoFinal)

                val response: Response = clientePruebas
                    .path("${RecursoArboles.RUTA}/$idArbol/${RecursoAncestroComunMasCercano.RUTA}")
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_INICIAL, nodoInicial)
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_FINAL, nodoFinal)
                    .request()
                    .get()

                Assertions.assertEquals(Response.Status.BAD_REQUEST.statusCode, response.status)
                Assertions.assertEquals(errorEsperado, response.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoAncestroComunMasCercano, Mockito.times(1)).darAncestroComunMasCercano(nodoInicial, nodoFinal)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_EntidadInexistente()
            {
                val excepcion =  EntidadInexistente("Tree", idArbol.toString())
                val mensajeError = excepcion.message!!
                val errorEsperado = ErrorDTO(mensajeError)
                val nodoInicial = 1
                val nodoFinal = 2

                Mockito.doThrow(excepcion)
                    .`when`(mockRecursoAncestroComunMasCercano)
                    .darAncestroComunMasCercano(nodoInicial, nodoFinal)

                val response: Response = clientePruebas
                    .path("${RecursoArboles.RUTA}/$idArbol/${RecursoAncestroComunMasCercano.RUTA}")
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_INICIAL, nodoInicial)
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_FINAL, nodoFinal)
                    .request()
                    .get()

                Assertions.assertEquals(Response.Status.NOT_FOUND.statusCode, response.status)
                Assertions.assertEquals(errorEsperado, response.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoAncestroComunMasCercano, Mockito.times(1)).darAncestroComunMasCercano(nodoInicial, nodoFinal)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_ErrorEnDAONoEsperado()
            {
                val mensajeError = "Mensaje de pruebas"
                val errorEsperado = ErrorDTO(mensajeError)
                val nodoInicial = 1
                val nodoFinal = 2

                Mockito.doThrow(ErrorEnDAONoEsperado(ErrorDesconocidoBD(mensajeError, null)))
                    .`when`(mockRecursoAncestroComunMasCercano)
                    .darAncestroComunMasCercano(nodoInicial, nodoFinal)

                val response: Response = clientePruebas
                    .path("${RecursoArboles.RUTA}/$idArbol/${RecursoAncestroComunMasCercano.RUTA}")
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_INICIAL, nodoInicial)
                    .queryParam(RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_FINAL, nodoFinal)
                    .request()
                    .get()

                Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.statusCode, response.status)
                Assertions.assertEquals(errorEsperado, response.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoAncestroComunMasCercano, Mockito.times(1)).darAncestroComunMasCercano(nodoInicial, nodoFinal)
            }
        }
    }

    @Nested
    inner class PorCodigo
    {
        private val mockRepositorioArboles: RepositorioArboles = mock(RepositorioArboles::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
        }

        private val recursoAncestroComunMasCercano: RecursoAncestroComunMasCercano
                = RecursoAncestroComunMasCercanoConRepositorio(idArbol, mockRepositorioArboles)

        @Nested
        inner class DarAncestroComunMasCercano
        {
            @Test
            fun retorna_dto_con_ancestro_null_cuando_el_repositorio_retorna_un_arbol_y_el_arbol_retorna_ancestro_null()
            {
                Mockito.doReturn(ArbolBinarioConId(idArbol, ArbolBinario.vacio<Int>()))
                    .`when`(mockRepositorioArboles)
                    .buscarArbolBinarioPorId(idArbol)

                val ancestroDTORetornado = recursoAncestroComunMasCercano.darAncestroComunMasCercano(1, 2)

                Assertions.assertEquals(AncestroComunMasCercanoDTO(null), ancestroDTORetornado)
                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).buscarArbolBinarioPorId(idArbol)
            }

            @Test
            fun retorna_dto_con_ancestro_correcto_cuando_el_repositorio_retorna_un_arbol_y_el_arbol_retorna_ancestro_null()
            {
                val nodoInicial = 1
                val nodoFinal = 2
                val nodoComun = 3
                val arbol = ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.nodo(
                        nodoInicial,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.nodo(
                        nodoFinal,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    )
                )
                Mockito.doReturn(ArbolBinarioConId(idArbol, arbol))
                    .`when`(mockRepositorioArboles)
                    .buscarArbolBinarioPorId(idArbol)

                val ancestroDTORetornado = recursoAncestroComunMasCercano.darAncestroComunMasCercano(nodoInicial, nodoFinal)

                Assertions.assertEquals(AncestroComunMasCercanoDTO(nodoComun), ancestroDTORetornado)
                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).buscarArbolBinarioPorId(idArbol)
            }

            @Test
            fun lanza_excepcion_ParametroInvalido_cuando_nodoInicial_es_nulo() {
                Assertions.assertThrows(ParametroInvalido::class.java) {
                    recursoAncestroComunMasCercano.darAncestroComunMasCercano(null, 2)
                }
            }

            @Test
            fun lanza_excepcion_ParametroInvalido_cuando_nodoFinal_es_nulo() {
                Assertions.assertThrows(ParametroInvalido::class.java) {
                    recursoAncestroComunMasCercano.darAncestroComunMasCercano(1, null)
                }
            }

            @Test
            fun lanza_excepcion_EntidadInexistente_cuando_repositorio_retorna_null()
            {
                Mockito.doReturn(null)
                    .`when`(mockRepositorioArboles)
                    .buscarArbolBinarioPorId(idArbol)

                Assertions.assertThrows(EntidadInexistente::class.java){
                    recursoAncestroComunMasCercano.darAncestroComunMasCercano(1, 2)
                }

                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).buscarArbolBinarioPorId(idArbol)
            }

            @Test
            fun lanza_excepcion_ErrorEnDAONoEsperado_cuando_repositorio_lanza_ErrorDesconocidoBD()
            {
                Mockito.doThrow(ErrorDesconocidoBD("Prueba", null))
                    .`when`(mockRepositorioArboles)
                    .buscarArbolBinarioPorId(idArbol)

                Assertions.assertThrows(ErrorEnDAONoEsperado::class.java){
                    recursoAncestroComunMasCercano.darAncestroComunMasCercano(1, 2)
                }

                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).buscarArbolBinarioPorId(idArbol)
            }

            @Test
            fun lanza_excepcion_ErrorEnDAONoEsperado_cuando_repositorio_lanza_ErrorMapeoBD()
            {
                Mockito.doThrow(ErrorMapeoBD("Prueba", null))
                    .`when`(mockRepositorioArboles)
                    .buscarArbolBinarioPorId(idArbol)

                Assertions.assertThrows(ErrorEnDAONoEsperado::class.java){
                    recursoAncestroComunMasCercano.darAncestroComunMasCercano(1, 2)
                }

                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).buscarArbolBinarioPorId(idArbol)
            }
        }
    }
}