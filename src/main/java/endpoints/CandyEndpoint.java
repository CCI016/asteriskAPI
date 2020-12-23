package endpoints;

import orm.Candy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/candy")
@Produces(MediaType.TEXT_PLAIN)
public class CandyEndpoint {

    @GET
    public List<Candy> getAllCampaigns() {
        return Candy.listAll();
    }
}