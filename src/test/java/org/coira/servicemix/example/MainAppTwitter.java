package org.coira.servicemix.example;

import org.apache.camel.main.Main;
import org.coira.servicemix.example.TwitterRouteBuilder;

/**
 * Clase utilizada para probar la ruta de ejemplo
 */
public class MainAppTwitter
{

    /**
     * Un main() para probar fácilmente desde el IDE
     */
    public static void main( String... args )
        throws Exception
    {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder( new TwitterRouteBuilder() );
        main.run( args );
    }

}
