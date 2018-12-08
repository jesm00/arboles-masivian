package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.databind.JsonMappingException
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
import org.mockito.Mockito.mock
import java.net.URI
import java.util.*
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

@DisplayName("RecursoArbolesConRepositorio")
class RecursoArbolesPruebas
{
    companion object
    {
        private val BASE_URI: URI = UriBuilder.fromUri("http://0.0.0.0/").port(80).build()
    }

    private val nodo = 2
    private val nodoIzquierdo = 3
    private val nodoDerecho = 4

    private val arbolEntrada = ArbolBinario.nodo(
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

    private val arbolDTOEntrada = ArbolBinarioDTO(arbolEntrada)

    private val arbolSalida = ArbolBinarioConId(
        5L,
        arbolEntrada
    )

    private val arbolDTOSalida = ArbolBinarioConIdDTO(arbolSalida)

    @Nested
    inner class PorRed
    {
        val mockRecursoArboles: RecursoArboles = mock(RecursoArboles::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
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

        @[Nested DisplayName("Al crear")]
        inner class Crear
        {
            @Test
            fun llama_la_funcion_crear_y_retorna_objeto_correcto_como_json_cuando_recurso_retorna_correctamente()
            {
                Mockito.doReturn(arbolDTOSalida)
                    .`when`(mockRecursoArboles)
                    .crearArbol(arbolDTOEntrada)

                val responseArbolRecibido: Response = clientePruebas
                    .path(RecursoArboles.RUTA)
                    .request()
                    .post(Entity.entity(arbolDTOEntrada, MediaType.APPLICATION_JSON_TYPE))

                Assertions.assertEquals(Response.Status.OK.statusCode, responseArbolRecibido.status)
                Assertions.assertEquals(arbolDTOSalida, responseArbolRecibido.readEntity(ArbolBinarioConIdDTO::class.java))

                Mockito.verify(mockRecursoArboles, Mockito.times(1)).crearArbol(arbolDTOEntrada)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_ArbolInvalido()
            {
                val mensajeError = "Mensaje de pruebas"
                val errorEsperado = ErrorDTO(mensajeError)
                Mockito.doThrow(ArbolInvalido(mensajeError))
                    .`when`(mockRecursoArboles)
                    .crearArbol(arbolDTOEntrada)

                val responseArbolRecibido: Response = clientePruebas
                    .path(RecursoArboles.RUTA)
                    .request()
                    .post(Entity.entity(arbolDTOEntrada, MediaType.APPLICATION_JSON_TYPE))

                Assertions.assertEquals(Response.Status.BAD_REQUEST.statusCode, responseArbolRecibido.status)
                Assertions.assertEquals(errorEsperado, responseArbolRecibido.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoArboles, Mockito.times(1)).crearArbol(arbolDTOEntrada)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_JsonMappingException()
            {
                val mensajeError = "Mensaje de pruebas"
                val errorEsperado = ErrorDTO(mensajeError)
                @Suppress("DEPRECATION")
                Mockito.doThrow(JsonMappingException(mensajeError))
                    .`when`(mockRecursoArboles)
                    .crearArbol(arbolDTOEntrada)

                val responseArbolRecibido: Response = clientePruebas
                    .path(RecursoArboles.RUTA)
                    .request()
                    .post(Entity.entity(arbolDTOEntrada, MediaType.APPLICATION_JSON_TYPE))

                Assertions.assertEquals(Response.Status.BAD_REQUEST.statusCode, responseArbolRecibido.status)
                Assertions.assertEquals(errorEsperado, responseArbolRecibido.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoArboles, Mockito.times(1)).crearArbol(arbolDTOEntrada)
            }

            @Test
            fun retorna_objeto_response_de_error_correcto_cuando_recurso_lanza_ErrorEnDAONoEsperado()
            {
                val mensajeError = "Mensaje de pruebas DAO"
                val errorEsperado = ErrorDTO(mensajeError)
                Mockito.doThrow(ErrorEnDAONoEsperado(ErrorDesconocidoBD(mensajeError, null)))
                    .`when`(mockRecursoArboles)
                    .crearArbol(arbolDTOEntrada)

                val responseArbolRecibido: Response = clientePruebas
                    .path(RecursoArboles.RUTA)
                    .request()
                    .post(Entity.entity(arbolDTOEntrada, MediaType.APPLICATION_JSON_TYPE))

                Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.statusCode, responseArbolRecibido.status)
                Assertions.assertEquals(errorEsperado, responseArbolRecibido.readEntity(ErrorDTO::class.java))

                Mockito.verify(mockRecursoArboles, Mockito.times(1)).crearArbol(arbolDTOEntrada)
            }
        }
    }

    @Nested
    inner class PorCodigo
    {
        val mockRepositorioArboles: RepositorioArboles = mock(RepositorioArboles::class.java) {
            throw RuntimeException("Llamado a\n\t${it.method.declaringClass.simpleName}.${it.method.name}(${Arrays.toString(it.arguments)})\n\tno esta mockeado\n[$it]")
        }

        val recursoArboles: RecursoArboles = RecursoArbolesConRepositorio(mockRepositorioArboles)

        @Nested
        inner class Crear
        {
            @Test
            fun retorna_el_dto_correcto_cuando_el_repositorio_crea_el_arbol_correctamente()
            {
                Mockito.doReturn(arbolSalida)
                    .`when`(mockRepositorioArboles)
                    .crearArbolBinario(arbolEntrada)

                val arbolDTORetornado = recursoArboles.crearArbol(arbolDTOEntrada)

                Assertions.assertEquals(arbolDTOSalida, arbolDTORetornado)
                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).crearArbolBinario(arbolEntrada)
            }

            @Test
            fun lanza_excepcion_ErrorEnDAONoEsperado_cuando_repositorio_lanza_ErrorDesconocidoBD()
            {
                Mockito.doThrow(ErrorDesconocidoBD("Prueba", null))
                    .`when`(mockRepositorioArboles)
                    .crearArbolBinario(arbolEntrada)

                Assertions.assertThrows(ErrorEnDAONoEsperado::class.java){
                    recursoArboles.crearArbol(arbolDTOEntrada)
                }

                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).crearArbolBinario(arbolEntrada)
            }

            @Test
            fun lanza_excepcion_ErrorEnDAONoEsperado_cuando_repositorio_lanza_ErrorMapeoBD()
            {
                Mockito.doThrow(ErrorMapeoBD("Prueba", null))
                    .`when`(mockRepositorioArboles)
                    .crearArbolBinario(arbolEntrada)

                Assertions.assertThrows(ErrorEnDAONoEsperado::class.java){
                    recursoArboles.crearArbol(arbolDTOEntrada)
                }

                Mockito.verify(mockRepositorioArboles, Mockito.times(1)).crearArbolBinario(arbolEntrada)
            }
        }
    }
}