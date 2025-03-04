package com.proyectoHBase.proyectoHbase.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargarDatos {

    private static final Logger logger = LoggerFactory.getLogger(CargarDatos.class);
    private final Connection connection;

    @Autowired
    public CargarDatos(Connection connection) {
        this.connection = connection;
    }

    public void loadData(String filePath, int factorF, int factorC) throws IOException {
        Table table = connection.getTable(TableName.valueOf("sensor_data"));

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(values.length < 3){
                    logger.warn("Línea con formato inesperado: {}", line);
                    continue;
                }
                String sensorId = values[0];
                String measure = values[2];

                for (int i = 0; i < factorF; i++) {
                    for (int j = 0; j < factorC; j++) {
                        try {
                            Put put = new Put(Bytes.toBytes(sensorId + i));
                            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("measure" + j), Bytes.toBytes(measure));
                            table.put(put);
                        } catch (Exception e) {
                            logger.error("Error insertando datos para sensor {}: {}", sensorId, e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al leer el archivo {}: {}", filePath, e.getMessage(), e);
            throw e;
        } finally {
            table.close();
        }
    }
}
