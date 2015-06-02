package org.coira.servicemix.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;

/**
 * Procesador utilizado para preparar la obtención de un recurso vía HTTP GET, para volcarlo sobre un fichero.
 * 
 * @author Fco Javier Coira
 */
class HTTPFileProcessor
    implements Processor
{

    @Override
    public void process( Exchange exchng )
        throws Exception
    {
        // preparación del mensaje
        GenericFile gFile = new GenericFile();
        String fileName = exchng.getIn().getHeader( "CamelHttpUri" ).toString();
        fileName = fileName.substring( fileName.lastIndexOf( '/' ) + 1 );
        gFile.setFileName( fileName );
        gFile.setBody( exchng.getIn().getBody() );

        // asignación del mensaje de salida
        exchng.getOut().setHeader( "CamelFileName", fileName );
        exchng.getOut().setBody( gFile );
    }
}