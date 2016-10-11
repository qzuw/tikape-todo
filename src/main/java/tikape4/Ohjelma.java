package tikape4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.port;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Ohjelma {

    public static void main(String[] args) throws Exception {
        port(getHerokuAssignedPort());

        // Database ... 
        
        TodoDao todoDao = new TodoDao("jdbc:sqlite:todo.db");
        
        Spark.get("poista/:id", (req, res) -> {
            todoDao.poista(req.params(":id"));
            res.redirect("/");
            return "ok";
        });

        Spark.get("*", (req, res) -> {
            HashMap data = new HashMap<>();
            data.put("tehtavat", todoDao.haeTodot());

            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("*", (req, res) -> {
            todoDao.lisaa(req.queryParams("tehtava"));
            res.redirect("/");
            return "ok";
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
