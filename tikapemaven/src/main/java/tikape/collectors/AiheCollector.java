
package tikape.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.database.Collector;
import tikape.pojo.Aihe;


public class AiheCollector implements Collector<Aihe>{

    @Override
    public Aihe collect(ResultSet rs) throws SQLException {
        return new Aihe(rs.getInt("id"), rs.getString("nimi"), rs.getString("luomispaiva"), rs.getString("viimeisin_viesti"), rs.getString("alue"), rs.getInt("viestienlukumaara"));
    }
    
}
