package tikape4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import spark.Spark;

public class Ohjelma {

    public static void main(String[] args) throws Exception {
        Spark.get("*", (req, res) -> "hello");
    }

}
