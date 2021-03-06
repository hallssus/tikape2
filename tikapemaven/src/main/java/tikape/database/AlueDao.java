
package tikape.database;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Alue> lyhennaNimetAlueista() throws SQLException {
        List<Alue> alueet = findAll();
        for(Alue alue : alueet){
            String uusiNimi = lyhenna(alue.getNimi());
            alue.setLyhytnimi(uusiNimi);
            System.out.println(alue.getNimi() + " " + alue.getLyhytnimi());
        }
        return alueet;
    }
    
    public boolean onkoHyvaNimi(String nimi) throws SQLException {
        String uusinimi = nimi.trim();
        if (uusinimi.isEmpty()){
            return false;
        }
        for (Alue alue: findAll()){
            if (alue.getNimi().equals(nimi)){
                return false;
            }
        }
        return true;
    }
    
    private String lyhenna(String sana){
        StringBuilder uusiSana = new StringBuilder();
        if(sana.length() == 0){
            return "";
        }
        boolean pitka = true;
        for(int i = 0; i < 20; i++){
            uusiSana.append(sana.charAt(i));
            if(i == sana.length() - 1){
                pitka = false;
                break;
            }
            
        }
        if(pitka){
            uusiSana.append("...");
        }
        return uusiSana.toString();
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
