package endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import orm.Candy;
import orm.Prompt;
import orm.Provider;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;

@Path("/getCandy")
@Produces(MediaType.TEXT_PLAIN)
public class CandyEndpoint {

    private final ObjectMapper mapper;

    public CandyEndpoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GET
    @Path("list")
    public String getAllCampaigns() throws JsonProcessingException {
        List<Candy> candies = Candy.find("isDeleted = ?1", false).list();
        return mapper.writeValueAsString(candies);
    }

    @GET
    @Path("providers")
    public String getAllProvider() throws JsonProcessingException {
        List<Provider> providers = Provider.findAll().list();
        return mapper.writeValueAsString(providers);
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

    @GET
    @Path("prompts")
    public String getAllPrompts() throws JsonProcessingException {
        List<Prompt> prompts = Prompt.listAll();
        return mapper.writeValueAsString(prompts);

    }


    @POST
    @Path("updateCandy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String updateCandy(String body) throws JsonProcessingException {
        JsonObject json = Json.createReader(new StringReader(body)).readObject();
        Candy candy;
        if (json.containsKey("id")) {
            candy = Candy.findById(json.getJsonNumber("id").longValue());
            System.out.println(candy.id);
        } else {
            return null;
        }
        if (json.containsKey("name")) {
            if (!json.getString("name").equals("")) {
                candy.name = json.getString("name");
            }
        }
        if (json.containsKey("provider")) {
            candy.provider = Provider.findById(json.getJsonNumber("provider").longValue());
            System.out.println(candy.provider.name);
        }
        candy.persist();
        return mapper.writeValueAsString(candy);
    }

    @POST
    @Path("deleteCandy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String deleteCandy(String body) throws JsonProcessingException {
        JsonObject json = Json.createReader(new StringReader(body)).readObject();
        Candy candy;
        if (json.containsKey("id")) {
            candy = Candy.findById(json.getJsonNumber("id").longValue());
        } else {
            return mapper.writeValueAsString("isDeleted: false");
        }
        candy.isDeleted = true;
        candy.persist();
        return mapper.writeValueAsString(candy.isDeleted);
    }

    @POST
    @Path("addCandy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String addCandy(String body) throws JsonProcessingException {
        JsonObject json = Json.createReader(new StringReader(body)).readObject();
        Candy candy = new Candy();
        candy.name = json.getString("name");

        if (json.containsKey("providerID")) {
            candy.provider = Provider.findById(json.getJsonNumber("providerID").longValue());
        } else {
            Provider provider = new Provider();
            provider.name = json.getString("providerName");
            provider.persist();
        }

        if (json.containsKey("promptID")) {
            candy.prompt = Prompt.findById(json.getJsonNumber("promptID").longValue());
        }
        System.out.println(candy.id + " " + candy.name + " " + candy.provider + " " + candy.prompt);
        candy.isDeleted = false;
        candy.persist();
        return mapper.writeValueAsString(candy);
    }
}