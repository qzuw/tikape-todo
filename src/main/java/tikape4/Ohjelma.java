package tikape4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import spark.Spark;
import static spark.Spark.port;

public class Ohjelma {

    public static void main(String[] args) throws Exception {
        port(getHerokuAssignedPort());


        Spark.get("*", (req, res) -> {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:todo.db");
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Todo");
            
            String vastaus = "";
            while (result.next()) {
                vastaus += result.getString("task") + "<br/>";
            }
            
            conn.close();

            return vastaus;
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
