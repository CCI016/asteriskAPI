package ari.listeners;

import ari.AriEvent;
import ari.AriManager;
import ari.UsedCandy;
import ch.loway.oss.ari4java.generated.ActionChannels;
import ch.loway.oss.ari4java.generated.Message;
import ch.loway.oss.ari4java.generated.StasisStart;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import orm.Candy;
import orm.Prompt;

import javax.enterprise.event.Observes;
import java.util.List;

public class StasisStartListener implements EventListener{

    private final UsedCandy usedCandy;
    private final AriManager ariManager;
    public StasisStartListener(UsedCandy usedCandy, AriManager ariManager) {
        this.usedCandy = usedCandy;
        this.ariManager = ariManager;
    }

    @Override
    public void onEvent(@Observes @AriEvent(eventType = "StasisStart") Message message) {
       ariManager.getThreadPoll().execute(() -> process(message));
    }

    @Override
    public void onFailedEvent(RestException e) {
        System.out.println("HZ de ce dar intra aici");
    }

    private void process(Message message)  {
        StasisStart event = (StasisStart) message;
        Candy candy = usedCandy.init();
        usedCandy.setChanel(event.getChannel());
        Prompt prompt;
        ActionChannels channels = null;
        try {
            channels = ariManager.getAri().getActionImpl(ActionChannels.class);
        } catch (ARIException e) {
            e.printStackTrace();
        }
        try {
            if (candy != null) {
                prompt = candy.prompt;
                System.out.println(prompt.ariName);
                channels.play(usedCandy.getChanel().getId(), "sound:" + prompt.ariName.split("\\.")[0], "", 0, 0, "");
            } else {
                throw new Exception("Exception message");
            }
        } catch (Exception e) {
            System.out.println("Couldn't play prompt");
            try {
                channels.continueInDialplan(usedCandy.getChanel().getId(), usedCandy.getChanel().getDialplan().getContext(),
                        usedCandy.getChanel().getDialplan().getExten(),1, "hangup");
            } catch (RestException err) {
                System.out.println("Hangup denied");
            }
        }
    }




}
