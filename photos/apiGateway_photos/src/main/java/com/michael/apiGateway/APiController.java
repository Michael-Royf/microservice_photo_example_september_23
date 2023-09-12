package com.michael.apiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/controller")
public class APiController {
    @Autowired
    private Environment environment;

    @GetMapping("/ip")
    public String getGatewayIp() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        int port = Integer.parseInt(environment.getProperty("server.port"));

        return "API Gateway IP: " + ipAddress + ", Port: " + port;
    }
}
