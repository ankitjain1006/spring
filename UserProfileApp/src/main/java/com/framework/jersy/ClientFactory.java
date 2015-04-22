package com.framework.jersy;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.CollectionUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;


public class ClientFactory {
	
//	private SSLContextConfig sslContextConfig;
	private SSLContext sslContext;
//	private HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();
	private Map<String,Boolean> features = new HashMap<String,Boolean>();
	private long connectTimeoutMillis = 3000;
	private long readTimeoutMillis = 60000;
	
	private List<ClientFilter> clientFilterList;
	
	public Client createClient() {
/*		
		if (sslContext == null && sslContextConfig != null) {
			sslContext = SSLContextFactory.createSSLContext(sslContextConfig);
		}*/
		
		HTTPSProperties httpsProperties = null;
	/*	if (sslContext != null) {
			httpsProperties = new HTTPSProperties(hostnameVerifier, sslContext);
		}
		*/
		ClientConfig clientConfig = new DefaultClientConfig();
		if (httpsProperties != null) {
			clientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties);
		}
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ClientFactory.class.getClassLoader();
		}
		
		clientConfig.getFeatures().putAll(features);
		
		Client client = Client.create(clientConfig);
		
		if(CollectionUtils.isNotEmpty(clientFilterList)) {
			for(ClientFilter filter : clientFilterList) {
				client.addFilter(filter);
			}
		}
		
		client.setConnectTimeout(new Integer((int)connectTimeoutMillis));
		client.setReadTimeout(new Integer((int)readTimeoutMillis));
		
		return client;
	}
/*
	public SSLContextConfig getSslContextConfig() {
		return sslContextConfig;
	}

	public void setSslContextConfig(SSLContextConfig sslContextConfig) {
		this.sslContextConfig = sslContextConfig;
	}*/
	
	public SSLContext getSslContext() {
		return sslContext;
	}

	public void setSslContext(SSLContext sslContext) {
		this.sslContext = sslContext;
	}
/*
	public HostnameVerifier getHostnameVerifier() {
		return hostnameVerifier;
	}

	public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	public void setDisableHostnameVerification(boolean disableHostnameVerification) {
		hostnameVerifier = new DefaultHostnameVerifier(disableHostnameVerification);
	}*/

	public long getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(long connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public long getReadTimeoutMillis() {
		return readTimeoutMillis;
	}

	public void setReadTimeoutMillis(long readTimeoutMillis) {
		this.readTimeoutMillis = readTimeoutMillis;
	}

	public void setClientFilterList(List<ClientFilter> clientFilterList) {
		this.clientFilterList = clientFilterList;
	}

	public Map<String, Boolean> getFeatures() {
		return features;
	}

	public void setFeatures(Map<String, Boolean> features) {
		this.features = features;
	}

}
