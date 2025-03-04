package com.proyectoHBase.proyectoHbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HBaseDockerTest {
    public static void main(String[] args) {
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "localhost");
            config.set("hbase.zookeeper.property.clientPort", "2181");

            Connection connection = ConnectionFactory.createConnection(config);
            System.out.println("✅ Conectado a HBase en Docker!");
            connection.close();
        } catch (Exception e) {
            System.err.println("❌ Error al conectar a HBase: " + e.getMessage());
        }
    }
}
