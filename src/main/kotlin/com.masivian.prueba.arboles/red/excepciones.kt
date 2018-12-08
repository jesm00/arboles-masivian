package com.masivian.prueba.arboles.red

import java.lang.Exception
import javax.ws.rs.core.Response

abstract class ExcepcionRed(val codigoHttp: Response.Status, mensaje: String, causa: Throwable?)
    : Exception(mensaje, causa)

class ArbolInvalido(mensaje: String)
    : ExcepcionRed(Response.Status.BAD_REQUEST, mensaje, null)