
package tikape.collectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.database.Collector;
import tikape.pojo.Alue;

public class AlueCollector implements Collector<Alue>{

    @Override
    public Alue collect(ResultSet rs) throws SQLException {
return new Alue(rs.getString("nimi"), rs.getString("luomispaiva"), rs.getString("viimeisin_viesti"), rs.getInt("viestienlukumaara"));    }
    
}
