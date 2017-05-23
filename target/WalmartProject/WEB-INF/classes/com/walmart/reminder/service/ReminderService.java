package com.walmart.reminder.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walmart.reminder.dao.ReminderDAO;
import com.walmart.reminder.model.Reminder;
import com.walmart.reminder.model.Status;

@Path("/ReminderService")
public class ReminderService {
	private final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Add a reminder.
	 * 
	 * @param name The name of the reminder.
	 * @param description The description for the reminder.
	 * @param dueDate The date the reminder is due.
	 * @param status The status of the reminder.
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/addReminder")
	public Response addReminder(@FormParam("name") String name, @FormParam("description") String description, @FormParam("dueDate") String dueDate) {
		if(name == null || name.length() == 0 || description == null || description.length() == 0 || dueDate == null || dueDate.length() == 0) {
			return Response
					.status(Response.Status.PRECONDITION_FAILED).entity("Not all data provided to create a reminder.").build();
		}
		
		Reminder reminder = new Reminder();
		reminder.setName(name);
		reminder.setDescription(description);
		reminder.setDueDate(parseDateString(dueDate));
		reminder.setStatus(Status.NOTDONE);
		
		ReminderDAO.INSTANCE.addReminder(reminder);
		
		return Response
				.status(Response.Status.OK).entity("Reminder added").build();
	}
	
	/**
	 * Update a reminder.
	 * 
	 * @param id The id of the reminder to update.
	 * @param name The name of the reminder.
	 * @param description The description of the reminder.
	 * @param dueDate The date the reminder is due.
	 * @param status The status of the reminder.
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/updateReminder")
	public Response updateReminder(@FormParam("id") long id, @FormParam("name") String name, @FormParam("description") String description, @FormParam("dueDate") String dueDate, @FormParam("status") String status) {
		if(name == null || name.length() == 0 || description == null || description.length() == 0 || dueDate == null || dueDate.length() == 0 || status == null || status.length() == 0) {
			return Response
					.status(Response.Status.PRECONDITION_FAILED).entity("Not all data provided to update the reminder.").build();
		}
		
		Reminder reminder = new Reminder();
		reminder.setId(id);
		reminder.setName(name);
		reminder.setDescription(description);
		reminder.setDueDate(parseDateString(dueDate));
		reminder.setStatus(Status.getStatus(status));
		
		ReminderDAO.INSTANCE.updateReminder(reminder);
		
		return Response
				.status(Response.Status.OK).entity("Reminder updated").build();
	}
	
	
	/**
	 * Return a list of reminders based on due date and/or status.
	 * 
	 * @param dueDate
	 * @param status
	 * @return
	 */
	@POST
	@Path("/getReminders")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getReminders(@FormParam("dueDate") String dueDate, @FormParam("status") String status) {
		Date date = (dueDate != null && dueDate.length() > 0) ? parseDateString(dueDate) : null;
		List<Reminder> reminders = ReminderDAO.INSTANCE.getReminders(date, Status.getStatus(status));
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		return gson.toJson(reminders);
	}
	
	private Date parseDateString(String dueDate) throws IllegalArgumentException {
		try {
			return DATE_FORMATTER.parse(dueDate);
		} catch(ParseException pe) {
			throw new IllegalArgumentException(pe.getMessage());
		}
	}
}
