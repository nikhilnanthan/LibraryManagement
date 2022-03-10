package com.libman.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.Session;
import com.libman.model.User;
import com.libman.service.ReportIssueService;
import com.libman.service.UserService;
import com.libman.util.Validate;


@Path("/org/users/dashboard")
public class DashboardController {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList getDashboardDetails(){
		int orgId = Session.orgId;
		int userId = Session.userId;
		if(Validate.validateInteger(userId) && Validate.validateInteger(orgId))
		{
			//invalid req
		}
		UserService userService = new UserService();
		try {
			if(!userService.isUserRole(userId, "staff"))
			{
				//invalid req
			}
			if(!userService.isSameOrg(orgId,userId))
			{
				//invalid req
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Organisation org = new Organisation();
		org.setOrganisationId(orgId);
		UserService serviceUser = new UserService();
		serviceUser.updatePenalty(org);
		ReportIssueService serviceIssue = new ReportIssueService();
		Lend[] userPenaltyList=null;
		User[] userBannedList=null;
		User[] noReadersPublisher =null;
		try {
			userPenaltyList = serviceUser.getUserPenalty(org);
			userBannedList = serviceUser.getBannedUser(org);
			noReadersPublisher = userService.getNoReadersPublisher(org);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			   ArrayList<Object> objectList = new ArrayList<>();
			   objectList.add(userPenaltyList);
			   objectList.add(userBannedList);
			   objectList.add(noReadersPublisher);	
		
		return objectList;
	}
}
