package org.coira.servicemix.example;

import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import twitter4j.Status;

/**
 * Este procesador obtiene los tweets a modo de mensaje, y en caso de que tenga algún fichero media asociado, incluye en
 * la cabecera de salida la petición http para obtener dicho recurso.
 * 
 * @author Fco Javier Coira
 */
class Twitter2Processor
    implements Processor
{
    private static final transient Logger logger = Logger.getLogger( Twitter2Processor.class.getName() );

    @Override
    public void process( Exchange exchng )
        throws Exception
    {
        // el mensaje recibido debe ser un tweet, un estado
        Status tweet = exchng.getIn().getBody( Status.class );

        // mostramos el texto por consola, sólo por tener constancia del mismo
        String msg = String.format( "%s: %s", tweet.getUser().getName(), tweet.getText() );
        logger.info( msg );

        // condición para preparar el mensaje de salida
        if ( tweet.getMediaEntities() != null && tweet.getMediaEntities().length > 0 )
        {
            logger.info( String.format( "\tMedias asociados: %d. %s", tweet.getMediaEntities().length,
                                        tweet.getMediaEntities()[0].getMediaURL() ) );

            // se prepara la cabecera para el tratamiento posterior del mensaje
            exchng.getOut().setHeader( Exchange.HTTP_URI, tweet.getMediaEntities()[0].getMediaURL() );
            exchng.getOut().setHeader( Exchange.HTTP_METHOD, "GET" );
        }
    }
}