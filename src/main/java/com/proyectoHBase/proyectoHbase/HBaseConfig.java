package com.proyectoHBase.proyectoHbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration // ✅ Usamos el nombre completo para evitar conflictos
public class HBaseConfig {

    @Bean
    public Connection hBaseConnection() throws IOException {
        try {
            Configuration hbaseConfig = HBaseConfiguration.create();
            hbaseConfig.set("hbase.zookeeper.quorum", "hbase");
            hbaseConfig.set("hbase.zookeeper.property.clientPort", "2181");
            hbaseConfig.set("zookeeper.znode.parent", "/hbase"); 
            return ConnectionFactory.createConnection(hbaseConfig);
        } catch (IOException e) {
            System.err.println("Error creando la conexión a HBase: " + e.getMessage());
            throw e;
        }
    }

       @Bean
    public Table hBaseTable(Connection connection) throws Exception {
        String tableName = "sensor_data";
        
        // Crear la tabla si no existe
        Admin admin = connection.getAdmin();
        TableName hbaseTableName = TableName.valueOf(tableName);
        
        if (!admin.tableExists(hbaseTableName)) {
            System.out.println("Creando la tabla " + tableName);

            // Definir la columna familia
            ColumnFamilyDescriptor familyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("cf")).build();
            TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(hbaseTableName)
                .setColumnFamily(familyDescriptor)
                .build();
            
            // Crear la tabla
            admin.createTable(tableDescriptor);
        } else {
            System.out.println("La tabla ya existe");
        }

        // Devolver la tabla (en este caso "your-table-name")
        return connection.getTable(hbaseTableName);
    }
}
