
package tikape.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.collectors.AiheCollector;
import tikape.collectors.AlueCollector;
import tikape.pojo.Aihe;
import tikape.pojo.Alue;



public class AiheDao implements Dao<Aihe, Integer>{
    
    private Database database;

    public AiheDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        List<Aihe> aiheet = this.database.queryAndCollect("SELECT * FROM Aihe WHERE id = ?", new AiheCollector(), key);
        if (aiheet.isEmpty()) {
            return null;
        }

        return aiheet.get(0);
    }
    
    public List<Aihe> etsiKymmenenAlueenNimella(String alue) throws SQLException {
        
        List<Aihe> aiheet = this.database.queryAndCollect("SELECT * FROM Aihe WHERE alue = ? ORDER BY viimeisin_viesti DESC LIMIT 10", new AiheCollector(), alue);
        
        if (aiheet.isEmpty()){
            
            return null;
        }
        
        return aiheet;
    }
    
    public Aihe haeUusinAihe() throws SQLException {
        List<Aihe> aiheet = this.database.queryAndCollect("SELECT * FROM Aihe ORDER BY id DESC",new AiheCollector());
        if (aiheet.isEmpty()){
            return null;
        }
        return aiheet.get(0);
    }

    @Override
    public void save(Aihe aihe) throws SQLException {
        this.database.update("INSERT INTO Aihe (nimi, luomispaiva, alue, viestienlukumaara) VALUES (?, ?, ?, 0)", aihe.getNimi(), aihe.getLuomispaiva(), aihe.getAlue());
    }
    
    public void lisaaAihe(String nimi, String alue) throws SQLException {
        this.database.update("INSERT INTO Aihe (nimi, luomispaiva, alue, viestienlukumaara) VALUES (?, datetime('now','localtime'), ?, 0)", nimi,  alue);
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        return this.database.queryAndCollect("SELECT * FROM Aihe", new AiheCollector());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.database.update("DELETE FROM Aihe WHERE id = ?", key);
    }
    
    
    public Integer palautaIdNimenMukaan(String nimi) throws SQLException {
            List<Aihe> aiheet = this.database.queryAndCollect("SELECT * FROM Aihe WHERE nimi = ?", new AiheCollector(), nimi);
        if (aiheet.isEmpty()) {
            return null;
        }

        return aiheet.get(0).getId();
    }
    
    public List<Aihe> lyhennaNimetAiheista(String alue) throws SQLException {
        if(etsiKymmenenAlueenNimella(alue) == null){
            return null;
        }
        List<Aihe> aiheet = etsiKymmenenAlueenNimella(alue);
        List<Aihe> lyhennetyt = new ArrayList<>();

        for(Aihe aihe : aiheet){
            String uusiNimi = lyhenna(aihe.getNimi());
            lyhennetyt.add(new Aihe(aihe.getId(), uusiNimi, aihe.getLuomispaiva(), aihe.getViimeisinViesti(), aihe.getAlue(), aihe.getViestienlukumaara()));
        }
        return lyhennetyt;
    }
    private String lyhenna(String sana){
        StringBuilder uusiSana = new StringBuilder();
        boolean pitka = true;
        if(sana.length() == 0){
            return "";
        }
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


    
}
