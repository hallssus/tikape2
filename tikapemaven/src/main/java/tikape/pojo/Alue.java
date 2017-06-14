//luokka alueelle
package tikape.pojo;

import java.sql.Date;

public class Alue {
    
    private String nimi;
    private String luomispaiva;  //täysin varma en ole onko tämä oikein toimiva
    private String viimeisinViesti;
    
    public Alue(String nimi, String paiva) {
        //päivämäärien initalisointi ? tässä vai antaako kutsuessa päivämäärän kuten nyt
        this.luomispaiva=paiva;
        this.nimi= nimi;
        this.viimeisinViesti=null;
    }

    public Alue(String nimi, String luomispaiva, String viimeisinViesti) {
        this.nimi = nimi;
        this.luomispaiva = luomispaiva;
        this.viimeisinViesti = viimeisinViesti;
    }
    
    
    //setterit ja getterit
    
    public void setViimeisinViesti(String paivamaara) {
        this.viimeisinViesti=paivamaara;
    }
    
    public String getNimi(){
        return this.nimi;
    }
    
    public String getViimeisinViesti() {
        return this.viimeisinViesti;
    }
    
    public String getLuomispaiva() {
        return this.luomispaiva;
    }

    @Override
    public String toString() {
        return nimi + this.luomispaiva + this.viimeisinViesti;
    }
    
    
}
