/**
 * 
 */
package pe.tallanes.sunat.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;


/**
 * @author Omar Yarleque
 *
 */
public class JsfRedirectInvalidSessionStrategy implements InvalidSessionStrategy {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsfRedirectInvalidSessionStrategy.class);
    
    private final String destinationUrl;
    private RedirectStrategy redirectStrategy;
    private boolean createNewSession = true;

    public JsfRedirectInvalidSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }
    
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	LOGGER.info("Starting new session (if required) and redirecting to ''{0}''", destinationUrl);
        if (createNewSession) {
            request.getSession();
        }
        redirectStrategy.sendRedirect(request, response, destinationUrl);
    }
    
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }    
}