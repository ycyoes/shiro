package org.jsecurity.samples.spring.web;

import org.jsecurity.context.SecurityContext;
import org.jsecurity.session.Session;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Insert JavaDoc here.
 */
public class JnlpController extends AbstractController {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private String jnlpView;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    public void setJnlpView(String jnlpView) {
        this.jnlpView = jnlpView;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Session session = SecurityContext.current().getSession();
        Assert.notNull( session, "Expected a non-null JSecurity session." );

        StringBuilder sb = new StringBuilder();
        sb.append( "http://" );
        sb.append( request.getServerName() );
        if( request.getServerPort() != 80 ) {
            sb.append( ":" );
            sb.append( request.getServerPort() );
        }
        sb.append( "/jsecurity-spring-sample/" );

        // prevent JNLP caching by setting response headers
        response.setHeader( "cache-control", "no-cache" );
        response.setHeader( "pragma", "no-cache" );

        Map<String,Object> model = new HashMap<String,Object>();
        model.put( "codebaseUrl", sb.toString() );
        model.put( "sessionId", session.getSessionId() );
        return new ModelAndView( jnlpView, model );
    }
}
