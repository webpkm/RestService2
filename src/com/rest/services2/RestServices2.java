package com.rest.services2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Path("/rest")
public class RestServices2 {
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
//		String responseJson = "[{\"name\":\"Harry Potter\",\"city\":\"London\"},{\"name\":\"Don Quixote\",\"city\":\"Madrid\"},{\"name\":\"Joan of Arc\",\"city\":\"Paris\"},{\"name\":\"Rosa Park\",\"city\":\"Alabama\"}]";
//		return Response.status(200).entity(responseJson).build();
		String responseJson = "";
		int status = 0;
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("http://localhost:8080/RestService1/rest/user");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			status = response.getStatusLine().getStatusCode();
			responseJson = this.getResponse(response.getEntity().getContent());
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(status).entity(responseJson).build();
	}
	
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") String userId) {
		String responseJson = "{\"name\":\"Harry Potter\",\"city\":\"London\"}";
		return Response.status(200).entity(responseJson).build();
	}
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(String requestJsonBody) {
//		String responseJson = "{\"status\":200 ,\"message\":\"User Saved successfully!\", \"requestBody\":" + requestJsonBody + "}";
//		return Response.status(200).entity(responseJson).build();
		
		String responseJson = "";
		int status = 0;
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/RestService1/rest/user");
			StringEntity stringEntity = new StringEntity(requestJsonBody);
			stringEntity.setContentType(MediaType.APPLICATION_JSON);

			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			status = response.getStatusLine().getStatusCode();
			responseJson = this.getResponse(response.getEntity().getContent());
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(status).entity(responseJson).build();
	}
	
	@DELETE
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userId") String userId) {
		String responseJson = "{\"status\":200 ,\"message\":\"User deleted successfully!\"}";
		return Response.status(200).entity(responseJson).build();
	}
	
	private static String getResponse(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		StringBuffer stringBuffer = new StringBuffer();
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		return stringBuffer.toString();
	}
}
