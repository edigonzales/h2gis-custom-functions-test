package ch.so.agi.h2.functions;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ch.ehi.ili2db.gui.Config;
import ch.ehi.ili2h2gis.H2gisMain;
import ch.ehi.ili2db.base.Ili2db;
import ch.ehi.ili2db.base.Ili2dbException;

public class App {
//    public String getGreeting() {
//        return "Hello World!";
//    }

    public static void main(String[] args) throws Ili2dbException {
        
        String dbfile = new File("./src/main/resources/bauzonengrenzen").getAbsolutePath();
        String dburl = "jdbc:h2:"+dbfile;
        
        try (Connection c = DriverManager.getConnection(dburl, "", "");
            Statement s = c.createStatement();)
        {

            
            s.execute("CREATE ALIAS IF NOT EXISTS SOGIS_Dummy FOR \"ch.so.agi.h2.functions.MyBuffer.dummy\";");
            s.execute("CREATE ALIAS IF NOT EXISTS SOGIS_Buffer FOR \"ch.so.agi.h2.functions.MyBuffer.buffer\";");
            s.execute("CREATE ALIAS IF NOT EXISTS SOGIS_RemoveInnerRings FOR \"ch.so.agi.h2.functions.MyBuffer.removeInnerRings\";");
            

            
            s.executeUpdate("UPDATE BAUZONENGRENZEN_BAUZONENGRENZE SET geometrie = SOGIS_RemoveInnerRings(GEOMETRIE, 1.0)");

           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Config settings = new Config();
        new H2gisMain().initConfig(settings);
        settings.setFunction(Config.FC_EXPORT);
        settings.setModels("SO_ARP_Bauzonengrenzen_20210120");
        settings.setModeldir(Paths.get("src/main/resources/").toFile().getAbsolutePath()+";"+"http://models.geo.admin.ch");
        settings.setDbfile(dbfile);
        settings.setValidation(true);
        settings.setItfTransferfile(false);
        settings.setDburl(dburl);
        
        String xtfFileName = "/Users/stefan/tmp/bauzonengrenzen_2021-01-29.xtf";
        settings.setXtffile(xtfFileName);
        Ili2db.run(settings, null);

        
        
        System.out.println("Hallo Welt.");

    }
}
