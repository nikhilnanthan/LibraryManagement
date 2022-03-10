package com.libman.controller;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.Session;
import com.libman.model.User;
import com.libman.service.SignInUpService;
import com.libman.service.UserService;
import com.libman.util.ConvertPostData;
import com.libman.util.Json;
import com.libman.util.Validate;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("/org/users")
public class UserController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User[] getAllUsers(@QueryParam("role") @DefaultValue("none") String role,
							  @QueryParam("search") @DefaultValue("none") String search){
		
		if(!role.equals("none")) {
			Organisation org = new Organisation();
			User user = new User();
			int orgId = 1;
			if(!Validate.validateInteger(orgId) || !Validate.validateString(role))
			{
					
			}
			org.setOrganisationId(orgId);
			user.setRole(role);
			UserService serviceUser = new UserService();
			User[] userDetail=null;
			try {
				userDetail = serviceUser.getAllUsers(org,user);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return userDetail;
		}
		else if(!search.equals("none")) {
			int orgId = Session.orgId;
			UserService userService = new UserService();
			Organisation org = new Organisation();
			User user = new User();
			user.setFirstName(search);
			org.setOrganisationId(orgId);
			User user1[] = userService.getUserByName(org,user);
			return user1;
		}
		
		else
		{
			return null;
		}
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getSingleUserDetail(@PathParam("userId") String strUserId) {

		int staffId = 1;
		int orgId = 1;
		int userId = Integer.parseInt(strUserId);
		UserService userService = new UserService();
		User user = new User();
		Organisation org = new Organisation();
		user.setUserId(userId);
		org.setOrganisationId(orgId);
		User userDetail = userService.getUserDetail(org, user);
		return userDetail;
	}
	
	

	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap updateUserStatus(String request){
		
		Map jsonMap = ConvertPostData.convertPostData(request);
		if(jsonMap.get("q").equals("ban")) {
			int userId = (int) jsonMap.get("userId");
			UserService userService = new UserService();
			User user = new User();
			Organisation org = new Organisation();
			int orgId = Session.orgId;
			int staffId = Session.userId;
			if(Validate.validateInteger(userId) && Validate.validateInteger(orgId) && Validate.validateInteger(staffId))
			{
				//invalid req
			}
			user.setUserId(userId);
			org.setOrganisationId(orgId);
			ObjectMapper objectMapper = Json.jsonObject();
			LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
				try {
					if(userService.banOrUnBanUser(user, org)) {
						respMap.put("message","Done");
					}
					else {
						respMap.put("message","error");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return respMap;
		}
		
		else if(jsonMap.get("q").equals("pay")) {
			int orgId2 = Session.orgId;
			int userId2 = (int) jsonMap.get("userId");
			Organisation org2 = new Organisation();
			User user2 = new User();
			user2.setUserId(userId2);
			org2.setOrganisationId(orgId2);
			UserService userService2 = new UserService();
			String res1 = userService2.setPenalty(org2,user2);
			ObjectMapper objectMapper = Json.jsonObject();
			LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
			respMap.put("message","Done");
			return respMap;
		}
		return null;
	}
	
	@Path("/sign-in")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap signIn(String request){
			Map jsonMap = ConvertPostData.convertPostData(request);
			String emailId=(String) jsonMap.get("emailId");
			String password=(String) jsonMap.get("password");
			String orgName = (String) jsonMap.get("organisation");
			String role=(String) jsonMap.get("role");
			if((emailId==null || password==null || orgName==null || role==null ) || 
					(!Validate.ValidateEmailId(emailId) || !Validate.validatePassword(password) || 
							!Validate.validateString(orgName)))
			{
				
			}
			Organisation org = new Organisation();
			
			org.setOrganisationName(orgName);
			User user = new User();
			user.setEmailId(emailId);
			user.setPassWord(password);
			user.setRole(role);
			SignInUpService signInObj = new SignInUpService();
			String result = signInObj.signIn(user,org);
			System.out.println(result);
			LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
			if(result.equals("success"))
			{
					System.out.println(result);
					String userId = String.valueOf(user.getUserId());
					String orgId = String.valueOf(org.getOrganisationId());
				    Session.orgId=org.getOrganisationId();
				    Session.userId=user.getUserId();
				    respMap.put("message",result);
				    respMap.put("role",role);
				    respMap.put("orgId",orgId);
				    respMap.put("userId",userId);
			}
			else
			{
				respMap.put("message",result);
			}
			return respMap;
		}
	
	@Path("/sign-up")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap signUp(String request) {
			Map jsonMap = ConvertPostData.convertPostData(request);
			String firstName=(String) jsonMap.get("firstName");
			String lastName=(String) jsonMap.get("lastName");
			String ph1 =(String)jsonMap.get("phoneNo1");
		
			String ph2 = (String)jsonMap.get("phoneNo2");
			
			String address=(String)jsonMap.get("address");
			String ageString = (String)jsonMap.get("age");
	
			String emailId=(String)jsonMap.get("emailId");
			String passWord=(String)jsonMap.get("password");
			String role=(String)jsonMap.get("role");
			String organisationName =(String) jsonMap.get("organisation");
			if(firstName==null || lastName==null || ph1 ==null || address==null || ageString == null ||
					emailId==null || passWord==null || organisationName==null || role==null ||
					!Validate.validateString(firstName) || !Validate.validateString(lastName) || !Validate.validateString(address)
					|| !Validate.validateString(organisationName) || !Validate.validatePassword(passWord) || ph1.equals(ph2))
			{
				ObjectMapper objectMapper = Json.jsonObject();
				LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
				respMap.put("message","Invalid Credentials");
				return respMap;
			}
			else {
				Long phoneNo1=Long.parseLong(ph1);
				Long phoneNo2=Long.parseLong(ph2);
				int age= Integer.parseInt(ageString);
				User user = new User();
				Organisation org = new Organisation();
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setAddress(address);
				user.setPhoneNo1(phoneNo1);
				user.setPhoneNo2(phoneNo2);
				user.setAddress(address);
				user.setEmailId(emailId);
				user.setAge(age);
				user.setRole(role);
				user.setPassWord(passWord);
				org.setOrganisationName(organisationName);
				String result = null;
				SignInUpService signUp = new SignInUpService();
				LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
				try {
					result = signUp.signUp(user,org);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result.equals("created")) {
					String userId = String.valueOf(user.getUserId());
					String orgId = String.valueOf(org.getOrganisationId());
				   	respMap.put("message",result);
				   	respMap.put("role",role);
				}
				else {
					respMap.put("message",result);
				}
				System.out.println(result);
				return respMap;
		}
	}
	
	@Path("/logout")
	@POST
	public void logout()
		{
			Session.orgId=0;
			Session.userId=0;
			Session.role="none";
		}

	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap deleteUser(){
		int orgId = 1;
		int userId = 11;
		int staffId = 13;
		if(Validate.validateInteger(userId) && Validate.validateInteger(orgId) && Validate.validateInteger(staffId))
		{
			//invalid req
		}
		User user = new User();
		user.setUserId(userId);
		Organisation org = new Organisation();
		org.setOrganisationId(orgId);
		UserService userService = new UserService();
		LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
		try {
			if(!userService.isUserRole(staffId,"staff"))
			{
				// return false;
			}
			else
			{
				String result = userService.deleteUser(user,org);
				System.out.println(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respMap;
	}
}
