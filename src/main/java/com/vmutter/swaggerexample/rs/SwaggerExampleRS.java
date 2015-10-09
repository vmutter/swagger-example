package com.vmutter.swaggerexample.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/example")
public class SwaggerExampleRS {

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString("Hello World.");

			return Response.ok(json).build();
		} catch (JsonProcessingException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

}
