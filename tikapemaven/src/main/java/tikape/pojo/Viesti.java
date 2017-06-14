package tikape.pojo;

public class Viesti {
    private Integer id;
    private String lahettaja;
    private String sisalto;
    private Integer aihe;
    private String alue;
    
    public Viesti(Integer id, String lahettaja, String sisalto, Integer aihe, String alue) {
        this.id=id;
        this.lahettaja=lahettaja;
        this.sisalto=sisalto;
        this.aihe=aihe;
        this.alue=alue;
    }
    
    //getterit
    
    public Integer getId() {
        return this.id;
    }
    
    public Integer getAihe() {
        return this.aihe;
    }
    
    public String getLahettaja() {
        return this.lahettaja;
    }
    
    public String getSisalto() {
        return this.sisalto;
    }
    
    public String getAlue() {
        return this.alue;
    }
}
