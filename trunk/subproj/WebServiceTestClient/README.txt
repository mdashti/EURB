For having a web service client, have a look at: http://www.mkyong.com/webservices/jax-ws/jax-ws-soap-handler-in-client-side/

1- Generate web service classes using:
	C:\>wsimport -keep -verbose http://localhost:8888/ws/server?wsdl
	parsing WSDL...
	 
	generating code...
	com/sharifpro/matma/ws/KillSession.java
	com/sharifpro/matma/ws/KillSessionResponse.java
	com/sharifpro/matma/ws/ObjectFactory.java
	com/sharifpro/matma/ws/UpdateProfile.java
	com/sharifpro/matma/ws/UpdateProfileResponse.java
	com/sharifpro/matma/ws/UserInfoService.java
	com/sharifpro/matma/ws/UserInfoServiceEndpoint.java
	com/sharifpro/matma/ws/package-info.java

2- If you needed to change the web service address, only change required is in UserInfoService.java (or similar class in other web services)

3- Write a web service class for calling the web service, like below:

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