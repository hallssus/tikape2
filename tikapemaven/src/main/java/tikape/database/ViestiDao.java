
package tikape.database;

import tikape.database.Dao;
import tikape.database.Database;
import java.sql.SQLException;
import java.util.List;
import tikape.collectors.ViestiCollector;
import tikape.pojo.Viesti;

public class ViestiDao implements Dao<Viesti, Integer>{
    
    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        List<Viesti> viestit = this.database.queryAndCollect("SELECT * FROM Viesti WHERE id = ?", new ViestiCollector(), key);
        if (viestit.isEmpty()) {
            return null;
        }

        return viestit.get(0);
    }
    
    public List<Viesti> aiheenViestit(Integer key) throws SQLException {
        List<Viesti> viestit = this.database.queryAndCollect("SELECT * FROM Viesti WHERE aihe = ?", new ViestiCollector(), key);
        if (viestit.isEmpty()) {
            return null;
        }

        return viestit;
    }
    

    @Override
    public void save(Viesti viesti) throws SQLException {
        this.database.update("INSERT INTO Viesti (lahettaja, sisalto, aihe, alue) VALUES (?, ?, ?, ?)", viesti.getLahettaja(), viesti.getSisalto(), viesti.getAihe(), viesti.getAlue());
    }
    
    public void lisaaViesti(String lahettaja, String sisalto, String alue, String aihe) throws SQLException {
        this.database.update("INSERT INTO Viesti (lahettaja, sisalto, aihe, alue) VALUES (?, ?, ?, ?)", lahettaja, sisalto, aihe, alue);
        
        this.database.update("UPDATE Alue SET viimeisin_viesti = datetime('now','localtime') WHERE nimi = ?", alue);
        this.database.update("UPDATE Aihe SET viimeisin_viesti = datetime('now','localtime') WHERE nimi = ?", aihe);
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        return this.database.queryAndCollect("SELECT * FROM Viesti", new ViestiCollector());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.database.update("DELETE FROM Viesti WHERE id = ?", key);
    }
    
}