package com.framework.jersy;

import java.lang.reflect.Proxy;

import com.sun.jersey.api.client.Client;

public class ResourceProxyFactory {
	
	public static Object create(Client client, Class<?>[] interfaces, String baseUrl)
	  {
		ResourceProxyInvocationHandler handler = new ResourceProxyInvocationHandler();
	    handler.setBaseUrl(baseUrl);
	    handler.setClient(client);

	    ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    if (cl == null) {
	      cl = ResourceProxyFactory.class.getClassLoader();
	    }

	    return Proxy.newProxyInstance(cl, interfaces, handler);
	  }
}