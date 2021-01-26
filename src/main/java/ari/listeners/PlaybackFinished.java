package ari.listeners;

import ari.AriEvent;
import ari.AriManager;
import ari.UsedCandy;
import ch.loway.oss.ari4java.generated.Message;
import ch.loway.oss.ari4java.tools.RestException;
import io.quarkus.hibernate.orm.panache.Panache;
import orm.Candy;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class PlaybackFinished implements EventListener{

    @Inject
    EntityManager entityManager;
    private final UsedCandy usedCandy;
    private final AriManager ariManager;

    public PlaybackFinished(UsedCandy usedCandy, AriManager ariManager) {
        this.ariManager = ariManager;
        this.usedCandy = usedCandy;
    }

    @Override
    @Transactional
    public void onEvent(@Observes @AriEvent(eventType = "PlaybackFinished") Message message) {
        process(message);
    }

    @Override
    public void onFailedEvent(RestException e) {

    }

    @Transactional
    private void process(Message message) {
        Candy candy = Candy.findById(usedCandy.getUsedCandy().id);
        updateCandy(candy);
    }

    @Transactional
    private void updateCandy(Candy candy) {
        candy.numberOfPlays = candy.numberOfPlays + 1;
        candy.persist();
    }
}
