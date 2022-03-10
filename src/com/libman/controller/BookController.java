package com.libman.controller;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.libman.model.Organisation;
import com.libman.model.Session;
import com.libman.model.User;
import com.libman.service.BookService;
import com.libman.service.LendBookService;
import com.libman.service.UserService;
import com.libman.util.ConvertPostData;
import com.libman.util.Json;
import com.libman.util.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libman.model.Book;
import com.libman.model.Lend;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/org/books/")
public class BookController{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Book[] getBookDetail(@QueryParam("search") @DefaultValue("none") String search,
			 @QueryParam("category") @DefaultValue("none") String category ,
			 @QueryParam("type") @DefaultValue("none") String q)
	{	
	if(q.equals("books")) {
		UserService userService = new UserService();
		Organisation org = new Organisation();
		
		BookService bookObj = new BookService();
	
			int userId=0;
			int orgId=0;
			userId = Session.userId;
			System.out.println(userId);
			orgId = Session.orgId;
			if(!Validate.validateInteger(userId) || !Validate.validateInteger(orgId))
			{
				//invalid req
			}
		
			try {
				if(!userService.isSameOrg(orgId, userId))
				{
					//invalid Req
				}
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			org.setOrganisationId(orgId);
			
			
			Book bookDetail[] = null;
			try {
				bookDetail = bookObj.getAllBooks(org);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return bookDetail;
	}
	else if(!search.equals("none")) {
				BookService serviceBook = new BookService();
				int orgId2 = Session.orgId;
				String bookTitle = search;
				System.out.println(bookTitle);
				Organisation org = new Organisation();
				Book book = new Book();
				if(!Validate.validateInteger(orgId2) || !Validate.validateString(bookTitle))
				{
					//invalid req
				}
				org.setOrganisationId(orgId2);
				book.setBookTitle(bookTitle);
				Book bookDetail[] = serviceBook.showBookByTitle(org,book);
				return bookDetail;
		}

		else if(!category.equals("none")){
// get book based on category
			BookService serviceBook = new BookService();
			Organisation orgCat = new Organisation();
			Book bookCat = new Book();
			int orgId1 = Session.orgId;
			category=category.toLowerCase();
			if(category==null || !Validate.validateInteger(orgId1) || !Validate.validateString(category))
			{
				//invalid req
			}
			orgCat.setOrganisationId(orgId1);
			bookCat.setCategory(category);
			
			Book bookDetail[] = null;
			try {
				bookDetail = serviceBook.showBookByCategory(orgCat,bookCat);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return bookDetail; 
		}
		return null;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getSingleBookDetail(@PathParam("id") String id) {
			String strBookId = id;
			BookService bookService = new BookService();
			Book book = new Book();
			Organisation org = new Organisation();
			BookService bookObj = new BookService();
			if(!Validate.validateStringHasInt(strBookId))
			{
				return null;
			}
			else {
				int bookId=Integer.parseInt(strBookId);
				int orgId =Session.orgId;
				System.out.println(orgId);
				book.setBookId(bookId);
				org.setOrganisationId(orgId);
				try {
					if(!bookService.isBookSameOrg(orgId,bookId)  || !Validate.validateInteger(bookId))
					{
						//invalid Req
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Book bookResult = bookObj.getBookDetail(book, org);
				return bookResult;
			}
			
	}

	
	@Path("/lend")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap lendBook(String request)
	{
		Map jsonMap = ConvertPostData.convertPostData(request);
		int bookId = (int) jsonMap.get("bookId");
		int userId = Session.userId;
		int orgId1 = Session.orgId;
		if(!Validate.validateInteger(userId) || !Validate.validateInteger(orgId1) || !Validate.validateInteger(bookId))
		{
			//invalid req
		}
		UserService userService = new UserService();
		BookService bookService = new BookService();
		try {
			if(userService.isUserRole(userId, "user"))
			{
				//invalid req
			}
			if(!userService.isSameOrg(orgId1, userId))
			{
				//invalid req
			}
			if(!bookService.isBookSameOrg(orgId1,bookId))
			{
				//invalid req
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BookService serviceBook = new BookService();
		User user1 = new User();                  //lend a book
		user1.setUserId(userId);
		Book book1 = new Book();
	    book1.setBookId(bookId);
	    Organisation org = new Organisation();
	    org.setOrganisationId(orgId1);
	    LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
	    if(serviceBook .isBookAvailable(book1))
	    {
		    String issuedDate = (String) jsonMap.get("issuedDate");
		    String returnDate = (String) jsonMap.get("returnDate");
		    Lend lend = new Lend();
		    lend.setIssuedDate(issuedDate);
		    lend.setReturnDate(returnDate);
		    LendBookService lendBookObj = new LendBookService();
		    String result1 =lendBookObj.lendBook(user1, book1, lend,org);
		    respMap.put("message",result1);
	    }
	    else
	    {
	    	
	    }
	    return respMap;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LinkedHashMap createBook(String request) {
		Map jsonMap = ConvertPostData.convertPostData(request);
	    int userId1 = Session.userId;
	    int orgId = Session.orgId;
		UserService userService1 = new UserService();
	    if(!Validate.validateInteger(orgId))
		{
			//invalid req
		}
	    if(!Validate.validateInteger(userId1))
		{
			//invalid req
		}
		try {
			if(!userService1.isUserRole(userId1, "staff"))
			{
				//invalid req
			}
			if(!userService1.isSameOrg(orgId, userId1))
			{
				//invalid req
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   
	    String bookTitle = (String) jsonMap.get("bookTitle");
	    String category = (String)jsonMap.get("category");
	    String strEditionNo = (String) jsonMap.get("editionNo");
	    String strIsbnNo =  (String) jsonMap.get("isbnNo");
	    String authorName = (String) jsonMap.get("authorName");
	    String emailId = (String) jsonMap.get("pubEmail");
	    String strPublishedYear = (String) jsonMap.get("pubYear");
	    String strPrice = (String) jsonMap.get("price");
	    if(!Validate.validateString(bookTitle) || !Validate.validateString(category) || 
	    Validate.validateString(authorName) || Validate.ValidateEmailId(emailId) )
	    {
	    	//invalid req
	    }
	    Organisation org = new Organisation();
	    User user = new User();
	    Book book = new Book();
	    Long isbnNo =  Long.parseLong(strIsbnNo);
	    int editionNo = Integer.parseInt(strEditionNo);
	    int publishedYear = Integer.parseInt(strPublishedYear);
	    int price = Integer.parseInt(strPrice);
	    org.setOrganisationId(orgId);
	    book.setBookTitle(bookTitle);
	    book.setCategory(category);
	    book.setEditionNo(editionNo);
	    book.setAuthorName(authorName);
	    user.setEmailId(emailId);
	    book.setIsbnNo(isbnNo);
	    book.setPublishedYear(publishedYear);
	    book.setPrice(price);
	    BookService serviceBook = new BookService();
	    String result = serviceBook.createBook(org, book, user);
	 	LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
	 	respMap.put("message",result);
	 	return respMap;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
		public LinkedHashMap updateBook(String request) {
		Map jsonMap = ConvertPostData.convertPostData(request);
		String bookTitle = (String) jsonMap.get("bookTitle");
	    String category = (String)jsonMap.get("category");
		String authorName = (String) jsonMap.get("authorName");
	    String emailId = (String) jsonMap.get("pubEmail");
	    String strPublishedYear = (String) jsonMap.get("pubYear").toString();;
	    String strPrice= (String) jsonMap.get("price").toString();;
	    String strEditionNo= (String) jsonMap.get("editionNo").toString();;
	    String strIsbnNo= (String) jsonMap.get("isbnNo").toString();
		int userId = Session.userId;
		int orgId = Session.orgId;
		if(!Validate.validateInteger(userId) || !Validate.validateInteger(orgId))
		{
			//invalid req
		}
		UserService userService = new UserService();
		BookService bookService = new BookService();
		int bookId = (int) jsonMap.get("bookId");
		try {
			if(!userService.isUserRole(userId, "staff"))
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
	    int editionNo = Integer.parseInt(strEditionNo);
	    long isbnNo = Long.parseLong(strIsbnNo);
	    int publishedYear = Integer.parseInt(strPublishedYear);
	    int price =  Integer.parseInt(strPrice);
	    if(!Validate.validateString(bookTitle) || !Validate.validateString(category) || 
	    		Validate.validateInteger(editionNo) || Validate.validateISBN(isbnNo) || Validate.validateString(authorName)
	    		|| Validate.validateInteger(publishedYear)
	    		 || Validate.validateInteger(price))
	    {
	    	//invalid req
	    }
	    User user = new User();
	    Book book = new Book();
	    book.setBookId(bookId);
	    book.setBookTitle(bookTitle);
	    book.setCategory(category);
	    book.setEditionNo(editionNo);
	    book.setAuthorName(authorName);
	    user.setEmailId(emailId);
	    book.setPublishedYear(publishedYear);
	    book.setPrice(price);
	    book.setIsbnNo(isbnNo);
	    BookService serviceBook = new BookService();
	    String result = serviceBook.updateBook(book,user);
	    LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
	  	respMap.put("message",result);
	  	return respMap;
	}
	
	@Path("/return")
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
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LinkedHashMap deleteBook(@PathParam("id") String id)
	{
		int userId = Session.userId;
		String strBookId = id;
		int orgId = Session.orgId;
		if(Validate.validateInteger(userId) && Validate.validateInteger(orgId) && strBookId==null)
		{
			//invalid req
		}
		UserService userService = new UserService();
		BookService bookService = new BookService();
		int bookId = Integer.parseInt(strBookId);
		try {
			if(!userService.isUserRole(userId, "staff"))
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
		Organisation org = new Organisation();
		Book book = new Book();
		org.setOrganisationId(orgId);
		book.setBookId(bookId);
		BookService serviceBook = new BookService();
		String result = serviceBook.deleteBook(org,book);
		LinkedHashMap<String,Comparable> respMap = new LinkedHashMap<>();
		respMap.put("message",result);
		return respMap;
	}
	
}
