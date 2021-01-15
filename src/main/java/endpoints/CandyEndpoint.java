package endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import orm.Candy;
import orm.Provider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/getCandy")
//@Produces(MediaType.TEXT_PLAIN)
public class CandyEndpoint {

    private final ObjectMapper mapper;

    public CandyEndpoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GET
    @Path("list")
    public String getAllCampaigns() throws JsonProcessingException {
        List<Candy> candies = Candy.find("isDeleted = 0").list();
        return mapper.writeValueAsString(candies);
    }

    @GET
    @Path("filterByNameAndProvider")
    public String getAllCampaignsByName(@QueryParam("name") String name, @QueryParam("provider") String provider) throws JsonProcessingException {
        List<Candy> candies = null;

        if (name != null && provider == null) {
            candies = Candy.find("name = ?1", name).list();
        } else if (name == null && provider != null) {
            Provider provider1 = Provider.findById(Long.parseLong(provider));
            candies = Candy.find("provider = ?1", provider1).list();
        } else if (name != null) {
            Provider provider1 = Provider.findById(Long.parseLong(provider));
            candies = Candy.find("name = ?1 and provider = ?2", name, provider1).list();
        }

        if (candies == null) {
            return "{}";
        }

        return mapper.writeValueAsString(candies);
    }
}