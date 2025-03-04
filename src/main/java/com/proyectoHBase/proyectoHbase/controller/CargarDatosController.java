package com.proyectoHBase.proyectoHbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoHBase.proyectoHbase.services.CargarDatos;


@RestController
@RequestMapping("/api/load")
public class CargarDatosController {

    @Autowired
    private CargarDatos dataLoaderService;  // Corregido el tipo aquí

    @PostMapping
    public ResponseEntity<String> loadData(@RequestParam String filePath, @RequestParam int factorF,
            @RequestParam int factorC) {
        try {
            dataLoaderService.loadData(filePath, factorF, factorC);
            return ResponseEntity.ok("Data loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading data");
        }
    }
}