package tikapemaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.Database;
import tikape.database.ViestiDao;
import tikape.database.AiheDao;
import tikape.database.AlueDao;
import tikape.pojo.*;

public class Main {

    public static void main(String[] args) throws Exception {
        
//        Connection connection = DriverManager.getConnection("jdbc:sqlite:Keskustelupalsta.db");
        
//        Statement statement = connection.createStatement();

//        ResultSet resultSet = statement.executeQuery("SELECT 1");
//
//        if(resultSet.next()) {
//            System.out.println("Hei tietokantamaailma!");
//        } else {
//            System.out.println("Yhteyden muodostaminen epäonnistui.");
//        }
//        
         // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:Keskustelupalsta.db");
        database.setDebugMode(true);

        AiheDao aiheDao = new AiheDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        AlueDao alueDao = new AlueDao(database);
        for(Alue alue: alueDao.findAll()){
            System.out.println(alue.getNimi());
            System.out.println(alue.getLuomispaiva());
            System.out.println(alue.getViimeisinViesti());
        }
        
        List<String> list = new ArrayList<>();
        

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("alueet", alueDao.findAll());
                   
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {

            alueDao.save(new Alue(req.queryParams("nimi"),"tänään"));
            res.redirect("/");
            return "onnistui";
        });
        
        
        
        get("/:a_nimi", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aiheet", aiheDao.etsiKaikkiAlueenNimella(req.params(":a_nimi")));
            map.put("alue", alueDao.ensiNimella(req.params(":a_nimi")));
            return new ModelAndView(map, "aihe");
        }, new ThymeleafTemplateEngine());

        post("/:a_nimi", (req, res) -> {
            System.out.println("no tässähän nämä " + req.params(":a_nimi"));
            aiheDao.lisaaAihe(req.queryParams("nimi"), req.params(":a_nimi"));
            res.redirect(req.params(":a_nimi"));
            return "onnistui taas";
        });
        
        
        
        get("/:a_nimi/:ai_id" , (req,res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.ensiNimella(req.params(":a_nimi")));
            map.put("aihe", aiheDao.findOne(Integer.parseInt(req.params(":ai_id"))));
            map.put("viestit", viestiDao.aiheenViestit(Integer.parseInt(req.params(":ai_id"))));
            
            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());
        
        post("/:a_nimi/:ai_id", (req,res) -> {
            viestiDao.lisaaViesti(req.queryParams("lahettaja"), req.queryParams("sisalto"), req.params(":a_nimi"), req.params(":ai_id"));   
            
            res.redirect(req.params(":ai_id"));
            return "moi";        
        });
        

    }
}
