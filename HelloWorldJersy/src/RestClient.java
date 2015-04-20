import javax.ws.rs.core.MediaType;

import com.galaxe.enterprise.UserProfileBusinessRequestBO;
import com.galaxe.enterprise.UserProfileBusinessResponseBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;



public class RestClient {
    public static void main(String[] args) {
        //ClientConfig config = new DefaultClientConfig();
        Client client = Client.create();
                //Http basic authentication 
    //    client.addFilter(new HTTPBasicAuthFilter("username","password"));
        WebResource service = client.resource("http://localhost:8080/UserProfileBusinessApp/businessapi");
      
        UserProfileBusinessResponseBO response =service.path("userprofile/authentication").accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(UserProfileBusinessResponseBO.class,new UserProfileBusinessRequestBO());
       System.out.println(response);
        // Get plain text
        /*System.out.println(service.path("rs").path("helloWorld").accept(
                MediaType.TEXT_PLAIN).get(String.class));
        // Get XML
        System.out.println(service.path("rs").path("helloWorld").accept(
                MediaType.TEXT_XML).get(String.class));
        // Get HTML
        System.out.println(service.path("rs").path("helloWorld").accept(
                MediaType.TEXT_HTML).get(String.class));*/

    }
}