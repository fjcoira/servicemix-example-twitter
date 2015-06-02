package org.coira.servicemix.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;

import twitter4j.Status;

/**
 * Este procesador pasa los tweets a un fichero de texto. Sólo se utilizó como clase para pruebas.
 * 
 * @author Fco Javier Coira
 */
class TwitterProcessor
    implements Processor
{

    @Override
    public void process( Exchange exchng )
        throws Exception
    {
        Status tweet = exchng.getIn().getBody( Status.class );

        String text = tweet.getText();
        System.out.println( text );

        if ( tweet.getMediaEntities() != null && tweet.getMediaEntities().length > 0 )
        {
            System.out.println( String.format( "\tMedias asociados: %d. %s", tweet.getMediaEntities().length,
                                               tweet.getMediaEntities()[0].getMediaURL() ) );
        }
        // process text from tweet

        GenericFile gf = new GenericFile();
        gf.setFileName( "tweet" + tweet.getId() + ".txt" );
        gf.setCharset( "UTF-8" );
        gf.setBody( text.getBytes( "UTF-8" ) );
        exchng.getOut().setBody( gf );
    }
}