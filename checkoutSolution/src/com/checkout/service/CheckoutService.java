package com.checkout.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * Rest service used to checkout cart items!
 * 
 * @author Rania
 * 
 */

@Path("/checkout")
public class CheckoutService {

	@GET
	@Produces("application/xml")
	public Response checkoutItems(@QueryParam("items") String items) {
		return Response.status(Status.OK).entity("<result>" + "test" + "</result>").build();
	}

}
