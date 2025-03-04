package com.proyectoHBase.proyectoHbase.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraerDatos {
    private final Connection connection;

    @Autowired
    public ExtraerDatos(Connection connection) {
        this.connection = connection;
    }

    public List<String> extractData(String sensorIdPrefix, String measureColumn) throws IOException {
        Table table = connection.getTable(TableName.valueOf("sensor_data"));
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("cf"));
        scan.setFilter(new PrefixFilter(Bytes.toBytes(sensorIdPrefix)));

        ResultScanner scanner = table.getScanner(scan);
        List<String> results = new ArrayList<>();
        for (Result result : scanner) {
            String sensorId = Bytes.toString(result.getRow());
            String measure = Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes(measureColumn)));
            results.add(sensorId + "," + measure);
        }
        return results;
    }
}