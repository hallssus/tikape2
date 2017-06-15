
package tikape.database;

import java.sql.SQLException;
import java.util.List;
import tikape.collectors.AlueCollector;
import tikape.collectors.ViestiCollector;
import tikape.pojo.Alue;
import tikape.pojo.Viesti;

public class AlueDao implements Dao<Alue, Integer>{
    
    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }
        
    // tässä ja deletessä saattaa olla ongelma, koska perittävä luokka Dao haluaa, 
    // että metodin parametreinä olisi integerinä. Meillä kuitekin pk on string. Katsotaan tuleeko myöhemmin ongelmia.

    
    public Alue ensiNimella(String nimi) throws SQLException {
        List<Alue> alueet = this.database.queryAndCollect("SELECT * FROM Alue WHERE nimi = ?", new AlueCollector(), nimi);
        if (alueet.isEmpty()) {
            return null;
        }

        return alueet.get(0);
    }
    
    public Integer viestienlkm(String nimi) throws SQLException {
        List<Viesti> viestit = this.database.queryAndCollect("SELECT * FROM Viesti WHERE alue = ?", new ViestiCollector(), nimi);
        return viestit.size();
    }
    
    @Override
    public void save(Alue alue) throws SQLException {
        this.database.update("INSERT INTO Alue (nimi, luomispaiva, viestienlukumaara) VALUES (?, datetime('now','localtime'), 0)", alue.getNimi());
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        return this.database.queryAndCollect("SELECT * FROM Alue", new AlueCollector());
    }

    public void poista(String nimi) throws SQLException {
        this.database.update("DELETE FROM Alue WHERE nimi = ?", nimi);
    }
    
    //tässä nämä perityt metodit joita ei suoraan voi käyttää alueessa

    @Override
    public Alue findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
