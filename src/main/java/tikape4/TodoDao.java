package tikape4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {

    private String tietokantaosoite;

    public TodoDao(String tietokantaosoite) {
        this.tietokantaosoite = tietokantaosoite;
    }

    public List<Todo> haeTodot() throws Exception {
        Connection conn = DriverManager.getConnection(tietokantaosoite);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Todo");

        List<Todo> tehtavat = new ArrayList<>();

        while (result.next()) {
            String task = result.getString("task");
            int id = result.getInt("id");
            boolean done = result.getBoolean("done");

            Todo todo = new Todo(id, task, done);
            tehtavat.add(todo);
        }

        conn.close();

        return tehtavat;
    }

    public List<Todo> haeTodot(int tekijanId) throws Exception {
        Connection conn = DriverManager.getConnection(tietokantaosoite);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Todo WHERE tekija_id = " + tekijanId);

        List<Todo> tehtavat = new ArrayList<>();

        while (result.next()) {
            String task = result.getString("task");
            int id = result.getInt("id");
            boolean done = result.getBoolean("done");

            Todo todo = new Todo(id, task, done);
            tehtavat.add(todo);
        }

        conn.close();

        return tehtavat;
    }

    public void lisaa(String tehtava) throws Exception {
        Connection conn = DriverManager.getConnection(tietokantaosoite);
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO Todo (task, done) "
                + "VALUES ('" + tehtava + "', 0)");

        conn.close();

    }

    public void poista(String id) throws Exception {
        Connection conn = DriverManager.getConnection(tietokantaosoite);
        Statement stmt = conn.createStatement();
        try {
            Integer.parseInt(id);
        } catch (Throwable t) {
            return;
        }
        // 1%20OR%201=1
        // 1 OR 1=1
        // DELETE FROM Todo WHERE id = 1 OR 1=1
        stmt.execute("DELETE FROM Todo WHERE id = " + id);
        conn.close();
    }

    public void lisaa(String id, String tehtava) throws Exception {
        Connection conn = DriverManager.getConnection(tietokantaosoite);
        Statement stmt = conn.createStatement();
        stmt.execute(
                "INSERT INTO Todo (task, done, tekija_id) "
                + "VALUES ('" + tehtava + "', 0, " + Integer.parseInt(id) + ")");

        conn.close();

    }
}
