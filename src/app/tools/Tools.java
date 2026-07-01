/* Decompiler 285ms, total 1382ms, lines 484 */
package app.tools;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import app.ExMain;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Tools {
   private static ExMain main;
   private static String url;
   private static String user;
   private static String psw;
   private static String firstUrl;
   private static String clientId;
   private static String clientIpsPath;
   public static SimpleBooleanProperty isServer = new SimpleBooleanProperty(true);

   public static void setMain(ExMain m) {
      main = m;
   }

   public static void getServerProperties() {
      try {
         File f = new File("C:/Owex/serverProperty.txt");
         String server;
         if (f.exists()) {
            server = Files.readString(f.toPath());
            String[] serverInfo = server.split("&");
            url = serverInfo[0] + "/owenexams";
            user = serverInfo[1];
            psw = serverInfo[2];
            clientId = serverInfo[3];
            clientIpsPath = serverInfo[4];
            firstUrl = serverInfo[0];
            verifyIp(serverInfo[0]);
         } else {
            server = "";

            do {
               do {
                  server = JOptionPane.showInputDialog("Give the server properties user&host&password&pc id");
               } while(server == null);
            } while(server.isBlank());

            File dir = new File("C:/Owex");
            dir.mkdir();
            f.createNewFile();
            Files.writeString(f.toPath(), server, new OpenOption[0]);
            String clientIps = "";

            do {
               do {
                  clientIps = JOptionPane.showInputDialog("Give the path to the client Ips");
               } while(clientIps == null);
            } while(clientIps.isBlank());

            registerNewClient(server, clientIps, f);
            getServerProperties();
         }
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void registerNewClient(String server, String clientIps, File fow) {
      File f = new File(clientIps);
      String[] serverSplit = server.split("&");
      String serName = serverSplit[0];
      String user = serverSplit[1];
      String userPsw = serverSplit[2];
      String UserclientId = serverSplit[3];
      String ip = serName.split("//")[1];
      ip = ip.split(":")[0];
      String usIp = "";

      try {
         InetAddress local = InetAddress.getLocalHost();
         usIp = local.getHostAddress();
      } catch (UnknownHostException var15) {
         var15.printStackTrace();
      }

      String userInfo = usIp + "/" + user + "/" + userPsw;

      try {
         String regIps = Files.readString(f.toPath());
         if (regIps != null && !regIps.isBlank()) {
            int index = regIps.split("&").length;
            System.out.println("index = " + index);
            if (index > 0) {
               regIps = regIps + "&" + userInfo;
            }

            UserclientId = String.valueOf(index + 1);
         } else {
            UserclientId = "1";
            regIps = userInfo;
         }

         Files.writeString(f.toPath(), regIps, new OpenOption[0]);
         String serInfo = serName + "&" + user + "&" + userPsw + "&" + UserclientId + "&" + clientIps;
         Files.writeString(fow.toPath(), serInfo, new OpenOption[0]);
      } catch (IOException var16) {
         var16.printStackTrace();
      }

   }

   public static void verifyIp(String serverName) {
      try {
         InetAddress local = InetAddress.getLocalHost();
         String ip = local.getHostAddress();
         String usedIp = serverName.split("//")[1];
         String owexServerIp = usedIp.split(":")[0];
         File fIp = new File(clientIpsPath);
         String allIndexes = Files.readString(fIp.toPath());
         String[] everyIndex;
         String serverIp;
         String[] serverSplit;
         String registeredIp;
         String wantedIndex;
         if (clientId.equals("1")) {
            isServer.set(false);
            System.out.println("Is the server");
            if (!allIndexes.isBlank()) {
               everyIndex = allIndexes.split("&");
               serverIp = everyIndex[0];
               serverSplit = serverIp.split("/");
               registeredIp = serverSplit[0];
               if (!registeredIp.equals(ip)) {
                  wantedIndex = ip + "/" + serverSplit[1] + "/" + serverSplit[2];
                  reconstituteFile(everyIndex, wantedIndex, fIp);
               }

               Connection conn = getFirstConnected();
               String sql = "SELECT user, host FROM mysql.User";
               Statement stm = conn.createStatement();
               ResultSet rst = stm.executeQuery(sql);
               ArrayList<String> myUsers = new ArrayList<String>();
               ArrayList<String> myHosts = new ArrayList<String>();

               while(rst.next()) {
                  myUsers.add(rst.getString("user"));
                  myHosts.add(rst.getString("host"));
               }

               stm.close();
               updateUsers(everyIndex, myUsers, myHosts, conn);
            }
         } else {
            isServer.set(true);
            System.out.println("Not the server");
            if (!allIndexes.isBlank()) {
               System.out.println("not blank");
               if (allIndexes.contains("&")) {
                  System.out.println("more than one");
                  everyIndex = allIndexes.split("&");
                  serverIp = everyIndex[0];
                  serverSplit = serverIp.split("/");
                  registeredIp = serverSplit[0];
                  if (!registeredIp.equals(owexServerIp)) {
                     updateOwex(owexServerIp, registeredIp);
                  }

                  wantedIndex = everyIndex[Integer.valueOf(clientId) - 1];
                  String[] wSplit = wantedIndex.split("/");
                  String name = wSplit[1];
                  String ipa = wSplit[0];
                  String psw = wSplit[2];
                  if (name.equals(user)) {
                     System.out.println("Same user");
                     if (ipa.equals(ip)) {
                        System.out.println("Same adress");
                     } else {
                        System.out.println("other adress");
                        String nouveauC = ip + "/" + name + "/" + psw;
                        reconstituteFile(everyIndex, nouveauC, fIp);
                        getServerProperties();
                     }
                  }
               }
            }
         }
      } catch (SQLException | IOException var17) {
         var17.printStackTrace();
      }

   }

   public static void updateUsers(String[] everyIndex, ArrayList<String> myUsers, ArrayList<String> myHosts, Connection conn) {
      String nopermiUser = "";
      String noUser = "";

      for(int i = 1; i < everyIndex.length; ++i) {
         String oneIndex = everyIndex[i];
         String host = oneIndex.split("/")[0];
         String user = oneIndex.split("/")[1];
         String psw = oneIndex.split("/")[2];
         String serverHost;
         if (myUsers.contains(user)) {
            int index = myUsers.indexOf(user);
            serverHost = (String)myHosts.get(index);
            if (!host.equals(serverHost)) {
               System.out.println("User " + user + " found with no permission");
               nopermiUser = nopermiUser + " " + user;
               String removeSql = "DROP USER '" + user + "'@'" + serverHost + "'";
               String addSql = "CREATE USER IF NOT EXISTS '" + user + "'@'" + host + "' IDENTIFIED BY '" + psw + "'";
               String privileges = "GRANT ALL PRIVILEGES ON *.* TO '" + user + "'@'" + host + "' WITH GRANT OPTION";

               try {
                  Statement stm = conn.createStatement();
                  stm.executeUpdate(removeSql);
                  stm.executeUpdate(addSql);
                  stm.executeUpdate(privileges);
                  stm.close();
               } catch (SQLException var17) {
                  var17.printStackTrace();
               }
            }
         } else {
            System.out.println("User " + user + " not found on mysql.user ");
            noUser = noUser + " " + user;
            String addSql = "CREATE USER IF NOT EXISTS '" + user + "'@'" + host + "' IDENTIFIED BY '" + psw + "'";
            serverHost = "GRANT ALL PRIVILEGES ON *.* TO '" + user + "'@'" + host + "' WITH GRANT OPTION";

            try {
               Statement stm = conn.createStatement();
               stm.executeUpdate(addSql);
               stm.executeUpdate(serverHost);
               stm.close();
            } catch (SQLException var18) {
               var18.printStackTrace();
            }
         }
      }

      String msg;
      if (!nopermiUser.isBlank()) {
         msg = "Users " + nopermiUser + " found on the server with no permissions. New users with permissions created. \nPlease restart the application on the client PC";
         JOptionPane.showMessageDialog((Component)null, msg);
      }

      if (!noUser.isBlank()) {
         msg = "Users " + noUser + " was not found on the server. New users with permissions created. \nPlease restart the application on the client PC";
         JOptionPane.showMessageDialog((Component)null, msg);
      }

   }

   public static void updateOwex(String usedIp, String ip) {
      File f = new File("C:/Owex/serverProperty.txt");

      try {
         String property = Files.readString(f.toPath());
         property = property.replace(usedIp, ip);
         Files.writeString(f.toPath(), property, new OpenOption[0]);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void reconstituteFile(String[] everyIndex, String nouveauC, File f) {
      everyIndex[Integer.valueOf(clientId) - 1] = nouveauC;
      String nouveauS = "";

      for(int i = 0; i < everyIndex.length; ++i) {
         nouveauS = nouveauS + everyIndex[i];
         if (i != everyIndex.length - 1) {
            nouveauS = nouveauS + "&";
         }
      }

      try {
         Files.writeString(f.toPath(), nouveauS, new OpenOption[0]);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static Connection getConnected() {
      Connection conn = null;

      try {
         conn = DriverManager.getConnection(url, user, psw);
      } catch (SQLException var2) {
         var2.printStackTrace();
      }

      return conn;
   }

   public static Connection getFirstConnected() {
      Connection conn = null;

      try {
         conn = DriverManager.getConnection(firstUrl, user, psw);
      } catch (SQLException var3) {
         String message = var3.getMessage();
         System.out.println("Error message " + message);
         if (message.contains("Access denied for user") || message.contains("is not allowed to connect")) {
            JOptionPane.showMessageDialog((Component)null, "Client with no permission. \nStart the application on the server . \nThen restart the application here");
         }
      }

      return conn;
   }

   public static void createBdd() {
      try {
         Connection connect = getFirstConnected();
         String sql = "CREATE DATABASE IF NOT EXISTS owenexams";
         Statement st = connect.createStatement();
         st.executeUpdate(sql);
         connect.close();
      } catch (SQLException var3) {
         var3.printStackTrace();
      }

   }

   public static void prepareTables() {
      createBdd();
      Connection connection = getConnected();

      try {
         Statement stm = connection.createStatement();
         String sql3 = "SELECT table_name FROM information_schema.tables WHERE table_type='BASE TABLE' AND table_schema = 'owenexams'";
         ResultSet rst = stm.executeQuery(sql3);
         ArrayList<String> tables = new ArrayList<String>();

         while(rst.next()) {
            tables.add(rst.getString("table_name"));
         }

         String sqllogtab = "CREATE TABLE `logintable` ( `id` int NOT NULL AUTO_INCREMENT, `pseudo` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `password` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `nom` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `prenom` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `role` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `email` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `telephone` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `title` varchar(45) COLLATE utf8mb3_unicode_ci NOT NULL, `expourcentage` varchar(45) COLLATE utf8mb3_unicode_ci NOT NULL, `surpourcentage` varchar(45) COLLATE utf8mb3_unicode_ci NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `pseudo_UNIQUE` (`pseudo`) ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci";
         String sqllogtest = "INSERT INTO `logintable` (`pseudo`,`password`,`nom`,`prenom`,`role`,`email`,`telephone`, `title`,`expourcentage`,`surpourcentage` ) VALUES ('test','1111','Test_nom','Test_prenom','Admin','test@gmail.com','62225698','T.', '0','0')";
         String sqlClient = "CREATE TABLE `register` ( `id` int NOT NULL AUTO_INCREMENT, `patientId` varchar(100) NOT NULL, `exams` varchar(255) NOT NULL, `surgery` varchar(255) NOT NULL, `doctor` varchar(100) NOT NULL, `monthYear` varchar(100) NOT NULL, `date` varchar(100) NOT NULL, `nofacture` varchar(100) NOT NULL, `registerDate` varchar(100) NOT NULL, `consommable` varchar(255) NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=1209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ";
         String sqlSurgeries = "CREATE TABLE `surgeries` (`id` int NOT NULL AUTO_INCREMENT, `surgery` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `price` int NOT NULL, `shown` tinyint NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci";
         String sqlExams = "CREATE TABLE `exams` (`id` int NOT NULL AUTO_INCREMENT,`exam` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,`price` int NOT NULL,`shown` tinyint NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci";
         String drugTable = "CREATE TABLE `drugs` (`id` int NOT NULL AUTO_INCREMENT,`drug` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,`delegue` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci";
         String presTable = "CREATE TABLE `prescription` (`id` int NOT NULL AUTO_INCREMENT, `patient` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,`drug` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `prescriptiontime` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, `prescriptionmonth` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci";
         String insertExam = "INSERT INTO `exams` (`exam`, `price`,`shown`) VALUES ('Tonometrie', 20000,1), ('Fond d oeil', 40000,1), ('O.C.T', 150000,1), ('Champ visuel', 45000,0), ('Fundus photo', 100000,1), ('B-Scan', 50000,1), ('A-Scan', 50000,0), ('Test a la fluoresceine', 5000,1), ('Gonioscopie', 75000,0),('Fond d oeil - Lentille de Goldmann', 75000,0)";
         String insertSurgery = "INSERT INTO `surgeries`(`surgery`, `price` ,`shown`) VALUES ('MSICS', 400000,1), ('PTERYGIUM', 200000,0), ('CHALAZION', 200000,0), ('BIOPSIE + ANAPATH', 260000,0), ('Injection intravitreenne', 200000,1), ('Corneal FB removal', 30000,0), ('Orbital FB Removal in Bloc', 250000,0), ('Grattage d ulcere corneen a la LF', 50000,0)";
         if (!tables.contains("logintable")) {
            stm.executeUpdate(sqllogtab);
            stm.executeUpdate(sqllogtest);
         }

         if (!tables.contains("register")) {
            stm.executeUpdate(sqlClient);
         }

         if (!tables.contains("exams")) {
            stm.executeUpdate(sqlExams);
            stm.executeUpdate(insertExam);
         }

         if (!tables.contains("surgeries")) {
            stm.executeUpdate(sqlSurgeries);
            stm.executeUpdate(insertSurgery);
         }

         if (!tables.contains("drugs")) {
            stm.executeUpdate(drugTable);
         }

         if (!tables.contains("prescription")) {
            stm.executeUpdate(presTable);
         }

         connection.close();
      } catch (SQLException var14) {
         var14.printStackTrace();
      }

   }

   public static void showEphemereText(String str) {
      Label label = main.getSmsController().getEphemLabel();
      label.setWrapText(true);
      label.setText(str);
      label.setTextFill(Color.WHITE);
      Stage stage = new Stage();
      Scene scene = main.getSmsScene();
      stage.setScene(scene);
      stage.initStyle(StageStyle.UNDECORATED);
      stage.initStyle(StageStyle.TRANSPARENT);
      Rectangle2D bounds = Screen.getPrimary().getBounds();
      Double w = bounds.getWidth();
      Double h = bounds.getHeight();
      stage.setX(w - 310.0D);
      stage.setY(h - 180.0D);
      stage.show();
      PauseTransition wait = new PauseTransition(Duration.seconds(3.0D));
      wait.setOnFinished((e) -> {
         stage.close();
      });
      wait.play();
   }

   public static String creerDocument(String pseudo, String type) {
      String timeStam = (new SimpleDateFormat("dd-MM-yy")).format(Calendar.getInstance().getTime());
      String dossier = main.getCreancesPath();
      File ventef = new File(dossier + File.separator + type + File.separator + timeStam);
      if (!ventef.exists()) {
         ventef.mkdirs();
      }

      String path = ventef.getAbsolutePath();
      String var10000 = type.toLowerCase();
      String fname = var10000 + "_" + pseudo;
      String timeStamp = (new SimpleDateFormat("HH_mm_ss")).format(Calendar.getInstance().getTime());
      String finalPath = path + File.separator + fname + "_" + timeStamp + ".docx";
      return finalPath;
   }

   public static String creerDocument(String pseudo, String type, String nom) {
      String timeStam = (new SimpleDateFormat("dd-MM-yy")).format(Calendar.getInstance().getTime());
      String dossier = main.getCreancesPath();
      File ventef = new File(dossier + File.separator + type + File.separator + timeStam);
      if (!ventef.exists()) {
         ventef.mkdirs();
      }

      String path = ventef.getAbsolutePath();
      String fname = type.toLowerCase() + "_" + pseudo + "_" + nom;
      String timeStamp = (new SimpleDateFormat("HH_mm_ss")).format(Calendar.getInstance().getTime());
      String finalPath = path + File.separator + fname + "_" + timeStamp + ".docx";
      return finalPath;
   }

   public static void addNavigation(Node[] nodes) {
		 for(int i=0; i<nodes.length; i++) {
			 var a = i;
			nodes[i].setOnKeyPressed((e)->{
				if (e.getCode().equals(KeyCode.DOWN)) {
			          if(a<nodes.length-1) {      
			        	  nodes[a+1].requestFocus();
			          }else {
			        	  nodes[0].requestFocus();
			          }
			    }
			});
			
		 }
	 }
   
   
   public static void showAlert(String titre, String info, String type) {
	   Alert alert = null;
	   
	   if(type.equals("info")) {
		   alert= new Alert(AlertType.INFORMATION);
	   }else if(type.equals("error")){
		   alert= new Alert(AlertType.ERROR);
	   }
	   
	   if(alert==null) {
		   return;
	   }
	   
	   alert.setHeaderText(titre);
	   alert.setContentText(info);
	   
	   alert.showAndWait();
	  
   }
   
   
}
