package com.proyectoHBase.proyectoHbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration // ✅ Usamos el nombre completo para evitar conflictos
public class HBaseConfig {

    @Bean
    public Connection hBaseConnection() throws IOException {
        try {
            Configuration hbaseConfig = HBaseConfiguration.create();
            hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
            hbaseConfig.set("hbase.zookeeper.property.clientPort", "2181");
            hbaseConfig.set("hbase.client.hostname.override", "localhost"); 
            return ConnectionFactory.createConnection(hbaseConfig);
        } catch (IOException e) {
            System.err.println("Error creando la conexión a HBase: " + e.getMessage());
            throw e;
        }
    }

    @Bean
    public Table hBaseTable(Connection connection) throws Exception {
        // Puedes inyectar la conexión y devolver una tabla específica si la necesitas
        return connection.getTable(TableName.valueOf("your-table-name"));
    }
}
