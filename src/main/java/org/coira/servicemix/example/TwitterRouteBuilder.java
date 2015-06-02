package org.coira.servicemix.example;

import java.io.IOException;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;

/**
 * Configurardor de la ruta para el ejemplo.
 * 
 * @author Fco Javier Coira
 */
public class TwitterRouteBuilder
    extends RouteBuilder
{
    @Override
    public void configure()
        throws Exception
    {
        initTwitter();

        from( "twitter://search?type=polling&keywords=GameofThrones&delay=100" ).delay( 5000 )
        // procesado de los tweets
        .process( new Twitter2Processor() )
        // filtrado de los mensajes no deseados
        .filter( header( Exchange.HTTP_METHOD ).isEqualTo( "GET" ) )
        // se obtiene el fichero
        .to( "http://dummy" )
        // se prepara para guardarlo en disco
        .process( new HTTPFileProcessor() )
        // se guarda en disco
        .to( "file:twitter-example/images" );
    }

    /**
     * Configura el componente para el acceso a twitter.
     */
    private void initTwitter()
    {
        TwitterComponent tc = new TwitterComponent();
        getContext().addComponent( "twitter", tc );

        Properties p = new Properties();
        try
        {
            p.load( getClass().getResourceAsStream( "/twitter4j.properties" ) );
        }
        catch ( IOException i )
        {
            i.printStackTrace();
        }

        tc.setAccessToken( p.getProperty( "oauth.accessToken" ) );
        tc.setAccessTokenSecret( p.getProperty( "oauth.accessTokenSecret" ) );
        tc.setConsumerKey( p.getProperty( "oauth.consumerKey" ) );
        tc.setConsumerSecret( p.getProperty( "oauth.consumerSecret" ) );
    }
}
