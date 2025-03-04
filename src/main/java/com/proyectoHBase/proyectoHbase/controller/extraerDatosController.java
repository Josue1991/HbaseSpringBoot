package com.proyectoHBase.proyectoHbase.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoHBase.proyectoHbase.services.ExtraerDatos;


@RestController
@RequestMapping("/api/extraer")
public class extraerDatosController {
    @Autowired
    private ExtraerDatos dataExtractorService;

    @GetMapping
    public ResponseEntity<List<String>> extractData(@RequestParam String sensorIdPrefix, @RequestParam String measureColumn) {
        try {
            List<String> data = dataExtractorService.extractData(sensorIdPrefix, measureColumn);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}