package io.helidon.mp.incidents.resource;

import java.util.Collections;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import io.helidon.mp.incidents.model.Incident;
import io.helidon.mp.incidents.repo.IncidentManager;


/**
 * A JAX-RS resource to represent CRUD services for Incidents. Examples:
 *
 * Get default all incidents:
 * curl -X GET http://localhost:8080/incidents
 *
 * Get incident for id=11129:
 * curl -X GET http://localhost:8080/incidents/11129
 *
 * Update incident with id=11129
 * curl -X PUT -H "Content-Type: application/json" -d '{ "id": "11129" ,"approved": true, "claimDate": "05-25-2021", "comments": "Manufacturer updated", "customer": "Tech Experts Up", "customerId": "46995", "product": "Toshiba Laptop Computer", "status": "In-progress", "subject": "Keys are sticking", "summary": "Customer reports that keys are sticking after spilling a diet soda on it"}' http://localhost:8080/incidents
 *
 * The message is returned as a JSON object.
 *
 * Add new incident
 * curl -X POST -H "Content-Type: application/json" -d '{"approved": true, "claimDate": "05-25-2021", "comments": "Assign manufacturer for this incident", "customer": "iShop Q", "customerId": "47886", "product": "Docking Station Lenovo Yoga", "status": "New", "subject": "USB ports not recognizing devices", "summary": "Customer reports that none of the USB ports are working after connecting devices"}' http://localhost:8080/incidents
 *
 *
 * Get incidents by status
 * curl -X GET http://localhost:8080/incidents/status/In-Progress
 * 
 * Get incidents by product
 * curl -X GET http://localhost:8080/incidents/product/Toshiba
 * 
 * Get incidents by customer
 * curl -X GET http://localhost:8080/incidents/customer/Tech
 *  
 * Get incidents by claim date
 * curl -X GET http://localhost:8080/incidents/claim/04-28-2020
 * 
 */
@Path("incidents")
@RequestScoped
public class IncidentEndpoint {

	private static final Logger LOGGER = Logger.getLogger(IncidentEndpoint.class.getName());
	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

	@Inject
	private IncidentManager incidentManager;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentById(@PathParam("id") String id) {

		LOGGER.info("getIncidentById " + id);

		if (!isValidQueryStr(id)) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid id string")).build();
		} else if (incidentManager.isIdFound(id)) {
			Incident incident = incidentManager.get(id);
			return Response.ok(incident).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "ID " + id + " not found!")).build();
		}

	}

	@GET
	@Path("status/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentsByStatus(@PathParam("status") String status) {

		LOGGER.info("getIncidentsByStatus " + status);

		if (isValidQueryStr(status)) {
			return Response.ok(incidentManager.getByStatus(status)).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid status string")).build();
		}

	}

	@GET
	@Path("product/{product}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentsByProduct(@PathParam("product") String product) {

		LOGGER.info("getIncidentsByProduct " + product);

		if (isValidQueryStr(product)) {
			return Response.ok(incidentManager.getByProduct(product)).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid product string")).build();
		}

	}

	@GET
	@Path("customer/{customer}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentsByCustomer(@PathParam("customer") String customer) {

		LOGGER.info("getIncidentsByCustomer " + customer);

		if (isValidQueryStr(customer)) {
			return Response.ok(incidentManager.getByCustomer(customer)).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid customer string")).build();
		}

	}

	@GET
	@Path("claim/{claimDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentsByClaimDate(@PathParam("claimDate") String claimDate) {

		LOGGER.info("getIncidentsByClaimDate " + claimDate);

		if (isValidQueryStr(claimDate)) {
			return Response.ok(incidentManager.getByClaimDate(claimDate)).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid claimDate string")).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllIncidents() {
		LOGGER.info("getAllIncidents");

		return Response.ok(incidentManager.getAll()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Incident incident) {
		LOGGER.info("add " + incident.getProduct());

		String incidentId = incidentManager.add(incident);
		return Response.created(UriBuilder.fromResource(this.getClass()).path(incidentId).build()).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Incident incident) {
		LOGGER.info("update " + incident.getId());

		if (!isValidQueryStr(incident.getId())) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid id string")).build();
		} else if (incidentManager.isIdFound(incident.getId())) {
			String incidentId = incidentManager.update(incident);
			return Response.created(UriBuilder.fromResource(this.getClass()).path(incidentId).build()).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "ID " + incident.getId() + " not found!")).build();
		}
		
		
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {

		LOGGER.info("delete " + id);

		if (!isValidQueryStr(id)) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "Invalid id string")).build();
		} else if (incidentManager.isIdFound(id)) {
			incidentManager.deleteById(id);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(buildJsonMsg("errorMessage", "ID " + id + " not found!")).build();
		}

	}

	private JsonObject buildJsonMsg(String key, String msg) {
		JsonObject entity = JSON.createObjectBuilder().add(key, msg).build();
		return entity;
	}

	private boolean isValidQueryStr(String nameStr) {
		if (nameStr == null || nameStr.isEmpty() || nameStr.length() > 100) {
			return false;
		} else {
			return true;
		}
	}

}
