package com.masivian.prueba.arboles.red.aws

import java.io.IOException
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.masivian.prueba.arboles.red.recursos.ConfiguracionJersey
import java.io.InputStream
import java.io.OutputStream

@Suppress("unused")
class StreamLambdaHandler : RequestStreamHandler
{
    @Throws(IOException::class)
    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context)
    {
        handler.proxyStream(inputStream, outputStream, context)
    }

    companion object
    {
        private val handler = JerseyLambdaContainerHandler.getAwsProxyHandler(ConfiguracionJersey.darAplicacion())
    }
}