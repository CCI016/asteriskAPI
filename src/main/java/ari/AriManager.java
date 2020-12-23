package ari;

import ch.loway.oss.ari4java.AriFactory;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.ari_4_0_0.AriBuilder_impl_ari_4_0_0;
import ch.loway.oss.ari4java.tools.ARIException;
import io.quarkus.runtime.StartupEvent;
import orm.AriSettings;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.jboss.logging.Logger;
import ch.loway.oss.ari4java.ARI;

import java.net.URISyntaxException;

@ApplicationScoped
public class AriManager {
    private static final Logger logger = Logger.getLogger("ListenerBean");
    private AriSettings ariSettings;
    private ARI ari;
    void init(@Observes StartupEvent e) {
        ariSettings = AriSettings.findById((long)1); //Small project -> Only one connection is needed

        if(ariSettings == null) {
            logger.error("Ari does not have any settings.");
        } else {
            logger.info("Starting ari setup.");
        }

        ariSetup(ariSettings);
    }

    private void ariSetup(AriSettings ariSettings) {
        logger.info("Setting up new connection: " + ariSettings.applicationName + ", " + ariSettings.ariIP + ", " + ariSettings.ariVersion);

        try {
            ari =  AriFactory.nettyHttp(ariSettings.ariIP, ariSettings.user, ariSettings.password, AriVersion.ARI_4_0_0, ariSettings.applicationName);
        } catch (ARIException | URISyntaxException e) {
            logger.error("CACAETA HUINEA");
        }
    }
}
