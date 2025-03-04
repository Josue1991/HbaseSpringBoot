package com.proyectoHBase.proyectoHbase.controller;

import org.apache.hadoop.hbase.client.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HBaseController {

    private final Connection connection;

    public HBaseController(Connection connection) {
        this.connection = connection;
    }

    @GetMapping("/status")
    public String checkConnection() {
        try {
            return connection != null ? "Connection is active" : "Connection is not active";
        } catch (Exception e) {
            return "Error checking connection: " + e.getMessage();
        }
    }
}