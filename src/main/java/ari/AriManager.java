package ari;

import ch.loway.oss.ari4java.AriFactory;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.ActionEvents;
import ch.loway.oss.ari4java.generated.AsteriskInfo;
import ch.loway.oss.ari4java.generated.ari_4_0_0.AriBuilder_impl_ari_4_0_0;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import orm.AriSettings;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import ch.loway.oss.ari4java.ARI;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class AriManager {
    @Inject
    AriEventHandler ariEventHandler;

    private static final Logger logger = Logger.getLogger("ListenerBean");
    private ARI ari;
    private ExecutorService threadPoll;

    public AriManager() {
        this.threadPoll = Executors.newCachedThreadPool();
    }


    void init(@Observes StartupEvent e) {
        AriSettings ariSettings = AriSettings.findById((long)1); //Small project -> Only one connection is needed

        if(ariSettings == null) {
            logger.error("Ari does not have any settings.");
        } else {
            logger.info("Starting ari setup.");
        }

        ariSetup(ariSettings);
    }


    void stop(@Observes ShutdownEvent ev) {
        logger.info("The application is stopping...");

        try {
            ari.cleanup();
        } catch (ARIException e) {
            logger.warn("ARI Something went wrong while closing ws connection.", e);
        }
    }


    private ARI buildAri(AriSettings s) throws ARIException, URISyntaxException {
        return AriFactory.nettyHttp(s.ariIP, s.user, s.password, AriVersion.ARI_4_0_0, s.applicationName);
    }

    private void cleanupSubscription(String applicationName) {
        if (ari != null) {
            try {
                ari.cleanup();
            } catch (Exception e) {
                logger.warn("ARI  Cannot close connection. Set to pending list.");
            }
        }
    }


    private void ariSetup(AriSettings s) {

        try {
            cleanupSubscription(s.applicationName);
            ari = buildAri(s);
            logger.info("Setting up new connection: " + s.applicationName + ", " + s.ariIP + ", " + s.ariVersion);
            try {
                AsteriskInfo info = ari.asterisk().getInfo(null);
                logger.info("Connection success. Ari version: " + info.getSystem().getVersion());
            } catch (Exception e) {
                e.printStackTrace();
            }
            initWebSocketConnection(ari);
        } catch (ARIException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initWebSocketConnection(ARI ari) throws ARIException {
        ari.getActionImpl(ActionEvents.class).eventWebsocket(ari.getAppName(), false, ariEventHandler);
    }

    public ARI getAri() {
        return ari;
    }

    public ExecutorService getThreadPoll() {
        return threadPoll;
    }

    public void setThreadPoll(ExecutorService threadPoll) {
        this.threadPoll = threadPoll;
    }
}


