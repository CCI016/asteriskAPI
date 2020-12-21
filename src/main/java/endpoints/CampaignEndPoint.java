package endpoints;

import orm.Campaign;
import orm.ContentProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/campaigns")
@Produces(MediaType.TEXT_PLAIN)
public class CampaignEndPoint {

    @GET
    public List<Campaign> getAllCampaigns() {
        return Campaign.listAll();
    }
}