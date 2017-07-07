package com.example.demoopentracing.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Date;


@Path("/hello")
public class HelloWorldEndpoint {

	@Inject
	private BackendService backendService;

	@GET
	@Produces("text/plain")
	public Response doGet() throws InterruptedException {
		String action = backendService.action();
		return Response.ok("Hello from WildFly Swarm! It is now " + new Date() + " : " + action).build();
	}
}
