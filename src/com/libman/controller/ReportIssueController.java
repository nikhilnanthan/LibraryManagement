//$Id$
package com.libman.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.libman.model.Book;
import com.libman.model.Issue;
import com.libman.model.Organisation;
import com.libman.model.Session;
import com.libman.model.User;
import com.libman.service.BookService;
import com.libman.service.ReportIssueService;
import com.libman.service.UserService;
import com.libman.util.ConvertPostData;
import com.libman.util.Validate;


@Path("/org/users/report")
public class ReportIssueController{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap createReport(String request)
	{
		Map jsonMap = ConvertPostData.convertPostData(request);
		int orgId = Session.orgId;
		int bookId = (int) jsonMap.get("bookId");
		int userId = Session.userId;
		if(Validate.validateInteger(userId) && Validate.validateInteger(orgId))
		{
			//invalid req
		}
		UserService userService = new UserService();
		BookService bookService = new BookService();
		try {
			if(!userService.isUserRole(userId, "user"))
			{
				//invalid req
			}
			if(!userService.isSameOrg(orgId, userId))
			{
				//invalid req
			}
			if(!bookService.isBookSameOrg(orgId,bookId))
			{
				//invalid req 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String report = (String) jsonMap.get("issue");
		if(!Validate.validateString(report))
		{
			
		}
		Issue issue = new Issue();
		issue.setReport(report);
		Book book = new Book();
		book.setBookId(bookId);
		User user = new User();
		user.setUserId(userId);
		Organisation org = new Organisation();
		org.setOrganisationId(orgId);
		ReportIssueService reportIssueObj = new ReportIssueService();
		String result=reportIssueObj.reportIssue(user, book, issue, org);
		LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
		respMap.put("message",result);
		return respMap;		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Issue[] getIssue(@QueryParam("type") String type)
	{
		if(type.equals("alluserreport")){
		int orgId =Session.orgId;
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
			if(!userService.isSameOrg(orgId, userId))
			{
				//invalid req
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Organisation org = new Organisation();
		org.setOrganisationId(orgId);
		ReportIssueService reportIssueObj = new ReportIssueService();
		Issue[] issueDetail = null;
		try {
			issueDetail = reportIssueObj.getAllReports(org);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return issueDetail;
		}
		else if(type.equals("userreport")){
			Organisation org1 = new Organisation();
			User user = new User();
			int orgId1 =Session.orgId;
			int userId1 =Session.userId;
			user.setUserId(userId1);
			org1.setOrganisationId(orgId1);
			ReportIssueService issueObj = new ReportIssueService();
			Issue issueDetail[] = null;
			try {
				issueDetail = issueObj.getSingleUserReports(org1,user);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return issueDetail;
		}
		return null;
	}
}
