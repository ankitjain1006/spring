package com.framework.jersy;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.representation.Form;


public class ResourceProxyInvocationHandler implements InvocationHandler {

	
	private String baseUrl;
	private Client client;

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Class<?> declaringClass = method.getDeclaringClass();
		
		// Create the URL
		UriBuilder uriBuilder = UriBuilder.fromUri(baseUrl);
		uriBuilder.path(declaringClass);
		if (method.getAnnotation(Path.class) != null) {
			uriBuilder.path(method);
		}
		
		// Get the media types
		String acceptMediaType = null;
		Produces producesAnnotation = declaringClass.getAnnotation(Produces.class);
		if (producesAnnotation == null) {
			producesAnnotation = method.getAnnotation(Produces.class);
		}
		if (producesAnnotation != null) {
			if (producesAnnotation.value().length > 1) {
				throw new UnsupportedOperationException(method.toGenericString());
			}
			acceptMediaType = producesAnnotation.value()[0];
		}

		String sendMediaType = null;
		Consumes consumesAnnotation = declaringClass.getAnnotation(Consumes.class);
		if (consumesAnnotation == null) {
			consumesAnnotation = method.getAnnotation(Consumes.class);
		}
		if (consumesAnnotation != null) {
			if (consumesAnnotation.value().length > 1) {
				throw new UnsupportedOperationException(method.toGenericString());
			}
			sendMediaType = consumesAnnotation.value()[0];
		}
		
		// Process param annotations
		Map<String,Object> headerParamMap = null;
		Map<String,Object> pathParamMap = new HashMap<String,Object>();;
		List<Cookie> cookieParamList = null;
		Form form = null;
		
		Annotation[][] allParamAnnotations = method.getParameterAnnotations();
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					continue;
				}
				for (Annotation paramAnnotation: allParamAnnotations[i]) {
					if (paramAnnotation instanceof PathParam) {
						PathParam pathParam = (PathParam)paramAnnotation;
						pathParamMap.put(pathParam.value(), args[i]);
						break;
					} else if (paramAnnotation instanceof QueryParam) {
						QueryParam queryParam = (QueryParam)paramAnnotation;
						uriBuilder.queryParam(queryParam.value(), args[i]);
						break;
					} else if (paramAnnotation instanceof HeaderParam) {
						if (headerParamMap == null) {
							headerParamMap = new HashMap<String,Object>();
						}
						HeaderParam headerParam = (HeaderParam)paramAnnotation;
						headerParamMap.put(headerParam.value(), args[i]);
						break;
					} else if (paramAnnotation instanceof CookieParam) {
						if (cookieParamList == null) {
							cookieParamList = new ArrayList<Cookie>();
						}
						if (args[i] instanceof Cookie) {
							cookieParamList.add((Cookie)args[i]);
						} else if (args[i] instanceof String || args[i].getClass().isPrimitive()) {
							CookieParam cookieParam = (CookieParam)paramAnnotation;
							Cookie cookie = new Cookie(cookieParam.value(), args[i].toString());
							cookieParamList.add(cookie);
						} else {
							throw new IllegalArgumentException("Unsupported CookieParam type: " + args[i].getClass().getName());
						}
						break;
					} else if (paramAnnotation instanceof FormParam) {
						if (form == null) {
							form = new Form();
						}
						FormParam formParam = (FormParam)paramAnnotation;
						form.add(formParam.value(), args[i]);
						break;
					}
				}
			}
		}
		
		// Create the web resource
		WebResource resource = client.resource(uriBuilder.buildFromMap(pathParamMap));
		Builder builder = resource.getRequestBuilder();
		
		// Set the media types
		if (sendMediaType != null) {
			builder.type(sendMediaType);
		}
		if (acceptMediaType != null) {
			builder.accept(acceptMediaType);
		}
		
		// Add HTTP headers, if needed
		if (headerParamMap != null) {
			for (String headerName: headerParamMap.keySet()) {
				builder.header(headerName, headerParamMap.get(headerName));
			}
		}
		
		// Add cookies, if needed
		if (cookieParamList != null) {
			for (Cookie cookie: cookieParamList) {
				builder.cookie(cookie);
			}
		}
		
		// Route to a helper method
		for (Annotation annotation: method.getAnnotations()) {
			if (annotation instanceof GET) {
				return get(builder, method, args);
			} else if (annotation instanceof POST) {
				return post(builder, method, args, form);
			} else if (annotation instanceof PUT) {
				return put(builder, method, args);
			} else if (annotation instanceof DELETE) {
				return delete(builder, method, args);
			}
		}
		
		throw new UnsupportedOperationException(method.toGenericString());
		
	}
	

	protected Object get(Builder builder, Method method, Object[] args) throws Throwable {
		ClientResponse response = builder.get(ClientResponse.class);
		
		if (response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
			return response.getEntity(method.getReturnType());
		}
		
		throw new ResourceException(response);
	}
	
	protected Object post(Builder builder, Method method, Object[] args, Form form) throws Throwable {
		
		Object arg = null;
		if (form != null) {
			arg = form;
		} else {
			arg = getArgToPostOrPut(method, args);
		}

		ClientResponse response = builder.post(ClientResponse.class, arg);
		
		if (response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
			return response.getEntity(method.getReturnType());
		}
		
		throw new ResourceException(response);
	}
	
	protected Object put(Builder builder, Method method, Object[] args) throws Throwable {
		
		Object arg = getArgToPostOrPut(method, args);
		ClientResponse response = builder.put(ClientResponse.class, arg);
		
		if (response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
			return response.getEntity(method.getReturnType());
		}
		
		throw new ResourceException(response);
	}
	
	protected Object delete(Builder builder, Method method, Object[] args) throws Throwable {
		ClientResponse response = builder.delete(ClientResponse.class);
		
		if (response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
			return response.getEntity(method.getReturnType());
		}
		
		throw new ResourceException(response);
	}
	
	protected Object getArgToPostOrPut(Method method, Object[] args) {
		Object argToPost = null;
		Annotation[][] allParamAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < args.length; i++) {
			if (allParamAnnotations[i].length == 0) {
				argToPost = args[i];
				break;
			}
		}
		return argToPost;
	}


	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	
}
