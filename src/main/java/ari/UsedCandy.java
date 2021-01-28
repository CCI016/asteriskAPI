package ari;

import ch.loway.oss.ari4java.generated.Channel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import orm.Candy;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import java.util.List;

@ApplicationScoped
public class UsedCandy {
    private  Candy usedCandy;
    private Channel chanel;

    public UsedCandy() {
        this.usedCandy = null;
        this.chanel = null;
    }

    public Candy init() {
        Candy candy = getRandomCandy();
        setCandy(candy);
        return candy;
    }

    public void setCandy(Candy candy) {
        this.usedCandy = candy;
    }

    public void setChanel(Channel chanel) {
        this.chanel = chanel;
    }

    public Channel getChanel() {
        return chanel;
    }

    @Transactional
    public Candy getRandomCandy() {
        List<Candy> candies = Candy.find("isDeleted = ?1", false).list();
        int count = candies.size();
        int generatedLong = (int) (1 + (Math.random() * (count)));
        if (count == 0) {
            return null;
        }
        return candies.get(generatedLong - 1);
    }

    public Candy getUsedCandy() {
        return this.usedCandy;
    }
}
