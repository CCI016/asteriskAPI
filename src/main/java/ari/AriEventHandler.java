package ari;

import ch.loway.oss.ari4java.generated.Message;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class AriEventHandler implements AriCallback<Message> {

    private static final org.jboss.logging.Logger logger = Logger.getLogger("ListenerBean");
    @Inject
    Event<Message> event;

    @Override
    public void onSuccess(Message message) {
        System.out.println(message.getClass().getSimpleName().split("_")[0].trim());
        event.select(new AriEventBinding() {
            private static final long serialVersionUID = -8285796201390337743L;

            @Override
            public String eventType() {
                return message.getClass().getSimpleName().split("_")[0].trim();
            }
        }).fire(message);
    }

    @Override
    public void onFailure(RestException e) {
        logger.info("No bene");
    }


}
