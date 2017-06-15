//aihe-olio
package tikape.pojo;

import java.sql.Date;

public class Aihe {
    private Integer id;
    private String nimi;
    private String luomispaiva;
    private String uusinViesti;
    private String alue;     //tästä en ole yhtään varma miten viittausten toisiin kanssa toimitaan tässä tapauksessa
    private int viestienlukumaara;
    
    public Aihe(Integer id, String nimi, String luomispaiva, String alue) {
        this.id=id;
        this.nimi=nimi;
        this.luomispaiva=luomispaiva;
        this.uusinViesti = null;
        this.alue=alue;
        this.viestienlukumaara = 0;
    }

    public Aihe(Integer id, String nimi, String luomispaiva, String uusinViesti, String alue, int lkm) {
        this.id = id;
        this.nimi = nimi;
        this.luomispaiva = luomispaiva;
        this.uusinViesti = uusinViesti;
        this.alue = alue;
        this.viestienlukumaara = lkm;
    }
    
    
    
    //setterit ja getterit
    
    
    
    public void setViestienlukumaara(int viestienlukumaara) {
        this.viestienlukumaara = viestienlukumaara;
    }

    public int getViestienlukumaara() {
        return viestienlukumaara;
    }

    public void setUusinViesti(String pm) {
        this.uusinViesti=pm;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public String getAlue() {
        return this.alue;
    }
    
    public String getViimeisinViesti() {
        return this.uusinViesti;
    }
    
    public String getLuomispaiva() {
        return this.luomispaiva;
    }
}