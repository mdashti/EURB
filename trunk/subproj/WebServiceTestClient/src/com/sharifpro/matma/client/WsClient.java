package com.sharifpro.matma.client;

import com.sharifpro.matma.ws.UserInfoService;
import com.sharifpro.matma.ws.UserInfoServiceEndpoint;
 
public class WsClient{
 
	public static void main(String[] args) throws Exception {
 
		UserInfoService sis = new UserInfoService();
		UserInfoServiceEndpoint si = sis.getUserInfoServiceEndpointPort();
 
		si.killSession("dashti");
 
    }
 
}