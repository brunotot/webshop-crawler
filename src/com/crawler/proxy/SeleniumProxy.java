package com.crawler.proxy;

public class SeleniumProxy {

	private String ip;
	
	private String port;

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public SeleniumProxy(String ip, String port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
}
