package ari.listeners;

import ari.AriEvent;
import ari.AriManager;
import ari.UsedCandy;
import ch.loway.oss.ari4java.generated.ActionChannels;
import ch.loway.oss.ari4java.generated.Message;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;

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
        ariManager.getThreadPoll().execute(() -> process(message));
    }

    @Override
    public void onFailedEvent(RestException e) {
    }

    @Transactional
    public void process(Message message) {
        ActionChannels channels = null;
        Candy candy = Candy.findById(usedCandy.getUsedCandy().id);

        System.out.println("Aici intra");

        try {
            channels = ariManager.getAri().getActionImpl(ActionChannels.class);
        } catch (ARIException e) {
            System.out.println("Aici e eroare");
            e.printStackTrace();
        }

        try {
            channels.continueInDialplan(usedCandy.getChanel().getId(), usedCandy.getChanel().getDialplan().getContext(),
                    usedCandy.getChanel().getDialplan().getExten(),1, "hangup");
            updateCandy(candy);
        } catch (RestException e) {
            System.out.println("Hangup denied");
        }

    }

    @Transactional
    public void updateCandy(Candy candy) {
        candy.numberOfPlays = candy.numberOfPlays + 1;
        candy.persist();
    }
}
