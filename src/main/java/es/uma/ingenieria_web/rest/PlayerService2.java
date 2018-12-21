package es.uma.ingenieria_web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uma.ingenieria_web.model.Player;
import es.uma.ingenieria_web.service.DataService;

@Path("/player")
public class PlayerService2 {
	
	@Context
	ServletContext context;
	
//	@GET
//	@Produces({MediaType.APPLICATION_JSON})
//	public Response getPlayers() {
// 
//		return Response.ok(this.players).build();
//	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getPlayer(@PathParam("id") int id) {
		
		List<Player> players = DataService.getInstance().getPlayers();
		Player res = null;
		for (Player p : players) {
			if (p.getId()== id) res = p;
		}
		System.out.println(res);
		return Response.ok().entity(res).build();
	}
	
//	@GET
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public Response getPlayerByName(@QueryParam("name") String name) {
//		Player res = null;
//		for (Player p : players) {
//			if (p.getName().equals(name)) res = p;
//		}
//		return Response.ok().entity(res).build();
//	}
//	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getPlayerByPosition(@QueryParam("position") String position) {
		List<Player> players = DataService.getInstance().getPlayers();
		System.out.println("PUA");
		List<Player> res;
		if (position == null) res = players;
		else {
			res = new ArrayList<Player>();
			for (Player p : players) {
				if (p.getPosition().equals(position)) res.add(p);
			}
		}
		return Response.ok().entity(res).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addPlayer(Player player) {
		DataService.getInstance().addPlayer(player);
		return Response.ok().entity("Jugador added.").build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.TEXT_PLAIN)
	public Response addPlayerFromForm(
			@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("position") String position) {
		
		int idInt = Integer.valueOf(id);
		DataService.getInstance().addPlayer(new Player(idInt, name, position));
		return Response.ok().entity("Movie added from form.").build();
	}

}
