package ari;

import orm.Candy;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UsedCandy {
    private  Candy usedCandy = new Candy();

    public Candy init() {
        Candy candy = getRandomCandy();
        setCandy(candy);
        return candy;
    }

    private void setCandy(Candy candy) {
        this.usedCandy = candy;
    }

    private  Candy getRandomCandy() {
        List<Candy> candies = Candy.find("isDeleted = ?1", false).list();
        long count  = Candy.find("isDeleted = ?1" , false).count();
        int generatedLong = (int) (1 + (Math.random() * ((int)count - 1)));
        return candies.get(generatedLong);
    }

    public Candy getUsedCandy() {
        return this.usedCandy;
    }
}
