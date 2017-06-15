//luokka alueelle
package tikape.pojo;

import java.sql.Date;

public class Alue {
    
    private String nimi;
    private String luomispaiva;  //täysin varma en ole onko tämä oikein toimiva
    private String viimeisinViesti;
    private int viestienlukumaara;
    private String lyhytnimi;
    
    public Alue(String nimi, String paiva) {
        //päivämäärien initalisointi ? tässä vai antaako kutsuessa päivämäärän kuten nyt
        this.luomispaiva=paiva;
        this.nimi= nimi;
        this.viimeisinViesti=null;
        this.viestienlukumaara = 0;
    }
    
    public Alue(String nimi, String luomispaiva, String viimeisinViesti, int viestienlukumaara) {
        this.nimi = nimi;
        this.luomispaiva = luomispaiva;
        this.viimeisinViesti = viimeisinViesti;
        this.viestienlukumaara = viestienlukumaara;
        this.lyhytnimi = null;
    }
    
    
    //setterit ja getterit
    
    public void setViimeisinViesti(String paivamaara) {
        this.viimeisinViesti=paivamaara;
    }

    public void setViestienLukumaara(int viestienLukumaara) {
        this.viestienlukumaara = viestienLukumaara;
    }

    public int getViestienLukumaara() {
        return viestienlukumaara;
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

    public void setLyhytnimi(String lyhytnimi) {
        this.lyhytnimi = lyhytnimi;
    }

    public String getLyhytnimi() {
        return lyhytnimi;
    }
    
    
    
}
