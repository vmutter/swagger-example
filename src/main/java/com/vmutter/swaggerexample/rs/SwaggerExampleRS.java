package com.vmutter.swaggerexample.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/example")
@Api(value = "/example")
public class SwaggerExampleRS {

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Simple swagger test", notes = "Return a response with a JSON value", response = String.class)
	public Response test() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString("Hello World.");

		return Response.ok(json).build();
	}

}
