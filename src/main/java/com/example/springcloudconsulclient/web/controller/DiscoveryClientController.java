package com.example.springcloudconsulclient.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class DiscoveryClientController {


    private final DiscoveryClient discoveryClient;

    private final String currentApplicationName;


    @Autowired
    public DiscoveryClientController(DiscoveryClient discoveryClient, @Value("${spring.application.name}") String currentApplicationName) {

        this.discoveryClient = discoveryClient;
        this.currentApplicationName = currentApplicationName;
    }

    /**
     * 获取当前应用信息
     */

    public ServiceInstance getCurrentApplicationInstance() {

        return discoveryClient.getLocalServiceInstance();
    }


    /**
     * 获取所有服务名
     *
     * @return
     */
    @GetMapping("/list/services")
    public List<String> listServices() {

        return discoveryClient.getServices();
    }

    @GetMapping("/list/service-instance")
    public List<ServiceInstance> listServiceInstance() {


        List<String> services = listServices();

        List<ServiceInstance> serviceInstances = new LinkedList<>();


        services.forEach(serviceName -> {
            serviceInstances.addAll(discoveryClient.getInstances(serviceName));
        });

        return serviceInstances;
    }


}







































