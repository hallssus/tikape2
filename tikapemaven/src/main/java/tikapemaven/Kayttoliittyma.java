/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikapemaven;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import tikape.database.AiheDao;
import tikape.database.AlueDao;
import tikape.database.Database;
import tikape.database.ViestiDao;
import tikape.pojo.Aihe;
import tikape.pojo.Alue;
import tikape.pojo.Viesti;

/**
 *
 * @author Saana
 */
public class Kayttoliittyma {
    private Database db;
    private ViestiDao viestiDao;
    private AlueDao alueDao;
    private AiheDao aiheDao;
    
    public Kayttoliittyma(Database db) {
        this.db=db;
        this.viestiDao=new ViestiDao(db);
        this.aiheDao=new AiheDao(db);
        this.alueDao= new AlueDao(db);
    }
    
    public void kaynnista() throws SQLException {
        //metodi joka käyttää muita luokan metodeja käyttäjän antamien käskyjen mukaan
        Scanner lukija = new Scanner(System.in);
        System.out.println("Hei");
        while (true) {
    
            System.out.println("Näytä alueet: 1");
            System.out.println("Näytä aiheet: 2");
            System.out.println("Näytä viestit: 3");
            System.out.println("Näytä alueen aiheet: 4");
            System.out.println("Näytä aiheen viestit: 5");
            System.out.println("Näitä alueen viestien lukumäärä: 6");
            System.out.println("Lopeta: 13");
            System.out.println("Mitä haluat tehdä?: ");
            int kasky = Integer.parseInt(lukija.nextLine());
            switch (kasky) {
                case 1:
                    this.tulostaAlueet();
                case 2:
                    this.tulostaAiheet();
                case 3:
                    this.tulostaViestit();
                    
                case 4:
                    System.out.println("Anna alueen nimi: ");
                    String alue=lukija.nextLine();
                    this.tulostaAlueenAiheet(alue);
                    
                case 5:
                    System.out.println("Anna aiheen nimi: ");
                    String aihe = lukija.nextLine();
                    this.tulostaAiheenViestit(aihe);
                    
                case 6: 
                    System.out.println("Anna alue: ");
                    String alue2=lukija.nextLine();
                    this.viestejaAlueella(alue2);
                    
                case 13:
                    break;
                
            }
        }
    }
    //tulostaa kaikki viestit
    
    public void tulostaViestit() throws SQLException {
        List<Viesti> viestit=this.viestiDao.findAll();
        System.out.println(viestit);
    }
    
    //tulostaa kaikki annetun aiheen (nimellä haettuna) viestit. mahd turhake
    
    public void tulostaAiheenViestit(String aihe) throws SQLException {
        List<Viesti> viestit = this.viestiDao.kaikkiAiheenViestit(aihe);
        System.out.println(viestit);
    }
    
    //lisää viestin aiheeseen
    
    public void lisaaViesti(Integer id, String lahettaja, String sisalto, Integer aihe, String alue) throws SQLException {
        Viesti viesti = new Viesti(id, lahettaja, sisalto, aihe, alue);
        this.viestiDao.save(viesti);
    } 
    
    //tulostaa annetun aiheen viestit
    public void tulostaaAiheenViestit(Integer id) throws SQLException {
        List<Viesti> viestit = this.viestiDao.aiheenViestit(id);
        System.out.println(viestit);
    }
    
    //tulostaa kaikki aiheet
    
    public void tulostaAiheet() throws SQLException {
        List<Aihe> aiheet = this.aiheDao.findAll();
        System.out.println(aiheet);
    }
    
    //tulostaa kaikki annetun alueen aiheet
    
    public void tulostaAlueenAiheet(String alue) throws SQLException {
        List<Aihe> aiheet = this.aiheDao.etsiKaikkiAlueenNimella(alue);
        System.out.println(aiheet);
    }
    
    // lisaa aiheen annettuun alueeseen 
    
    public void lisaaAihe(String nimi, String alue) throws SQLException {
        this.aiheDao.lisaaAihe(nimi, alue);
    }
    
    //tulostaa kaikki alueet
    
    public void tulostaAlueet() throws SQLException {
        List<Alue> alueet=this.alueDao.findAll();
        System.out.println(alueet);
    }
    
    //lisää alueen
    
    public void lisaaAlue(String nimi, String luomispaiva) throws SQLException {
        Alue alue = new Alue(nimi, luomispaiva);
        this.alueDao.save(alue);
    }
    
    //palauttaa aiheessa olevien viestien määrän
    public Integer viestejaAiheessa(Integer id) throws SQLException {
        int n=this.viestiDao.aiheenViestit(id).size();
        return n;
    }
    
    //palauttaa alueella olevien viestien määrän 
    public Integer viestejaAlueella(String nimi) throws SQLException {
        int j=0;
        List<Aihe> aiheet = this.aiheDao.etsiKaikkiAlueenNimella(nimi);
        for (int i=0; i<aiheet.size(); i++) {
            j=j+this.viestejaAiheessa(aiheet.get(i).getId());
        }
        return j;
    }
}
