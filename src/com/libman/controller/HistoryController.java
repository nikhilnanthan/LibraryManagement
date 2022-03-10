
package com.libman.controller;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.Session;
import com.libman.model.User;
import com.libman.service.HistoryService;
import com.libman.service.UserService;
import com.libman.util.ConvertPostData;
import com.libman.util.Validate;

@Path("/org/users/history")
public class HistoryController{
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Lend[] getHistory()
	{
		int userId=0;
		int orgId =0;
		try {
		userId = Session.userId;
		orgId =Session.orgId;
		}
		catch(Exception e)
		{
			
		}
		if(Validate.validateInteger(userId) && Validate.validateInteger(orgId))
		{
			//invalid req
		}
		UserService userService = new UserService();
		try {
			if(userService.isUserRole(userId, "staff"))
			{
				//invalid req
			}
			if(!userService.isSameOrg(orgId,userId))
			{
				//invalid req
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		User user = new User();
		Organisation org = new Organisation();
		user.setUserId(userId);
		org.setOrganisationId(orgId);
		HistoryService history = new HistoryService();
		Lend[] lendDetail=null;
		try {
			lendDetail = history.getHistory(user,org);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lendDetail;
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LinkedHashMap setBookReturned(String request)
	{
		Map jsonMap = ConvertPostData.convertPostData(request);
		int lendId = (int)jsonMap.get("lendId");
		String returnDate = (String)jsonMap.get("returnDate");
		int orgId1 = Session.orgId;
		if(Validate.validateInteger(lendId) && Validate.validateInteger(orgId1))
		{
			//invalid req
		}
		Organisation org1 = new Organisation();
		org1.setOrganisationId(orgId1);
		Lend lend = new Lend();
		lend.setLendId(lendId);
		lend.setReturnDate(returnDate);
		UserService userService1 = new UserService();
		String res = userService1.setBookReturned(org1,lend);
		LinkedHashMap<String,String> respMap = new LinkedHashMap<>();
		respMap.put("message",res);
		return respMap;
	}
}
