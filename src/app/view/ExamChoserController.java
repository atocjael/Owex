/* Decompiler 12ms, total 395ms, lines 72 */
package app.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.ExMain;
import app.tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ExamChoserController {
   @FXML
   private ComboBox<String> examCombo;
   private ExMain mainApp;
   private Stage stage;

   public void setMain(ExMain main) {
      this.mainApp = main;
   }

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      Connection conn = Tools.getConnected();
      String sql = "SELECT DISTINCT exam FROM exams where shown=false";
      String sql2 = "SELECT DISTINCT surgery FROM surgeries where shown=false";
      ArrayList<String> exams = new ArrayList<String>();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.examCombo.getItems().add(rst.getString("exam"));
            exams.add(rst.getString("exam"));
         }

         rst.close();
         rst = stm.executeQuery(sql2);

         while(rst.next()) {
            this.examCombo.getItems().add(rst.getString("surgery"));
         }
      } catch (SQLException var7) {
         var7.printStackTrace();
      }

      this.examCombo.valueProperty().addListener((obs, old, nv) -> {
         if (!nv.equals("")) {
            String var10000 = exams.contains(nv) ? "exams" : "surgeries";
            String sqlu = "UPDATE " + var10000 + " SET shown=TRUE where " + (exams.contains(nv) ? "exam" : "surgery") + "='" + nv + "'";

            try {
               Statement st = conn.createStatement();
               st.executeUpdate(sqlu);
               this.mainApp.getRegistrationScreenController().populateMainGrid();
            } catch (SQLException var8) {
               var8.printStackTrace();
            }

            this.stage.close();
         }

      });
   }
}
