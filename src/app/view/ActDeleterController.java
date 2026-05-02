/* Decompiler 17ms, total 793ms, lines 98 */
package app.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.ExMain;
import app.tools.Tools;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ActDeleterController {
   @FXML
   private ComboBox<String> actCombo;
   @FXML
   private ComboBox<String> typeCombo;
   @FXML
   private Button delete;
   private ExMain main;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void setMain(ExMain m) {
      this.main = m;
   }

   public void initialize() {
      BooleanBinding bool = this.actCombo.valueProperty().isNull().and(this.typeCombo.valueProperty().isNull());
      ArrayList<String> exams = this.getExamNames();
      this.actCombo.getItems().addAll(exams);
      this.typeCombo.getItems().add("Exam");
      this.typeCombo.getItems().add("Surgery");
      this.delete.disableProperty().bind(bool);
   }

   @FXML
   public void deleteAct() {
      String act = (String)this.actCombo.getValue();
      String type = (String)this.typeCombo.getValue();
      String table = type == "Exam" ? "exams" : "surgeries";
      String field = type == "Exam" ? "exam" : "surgery";
      String sql = "DELETE FROM " + table + " WHERE " + field + "='" + act + "'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         stm.executeUpdate(sql);
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

      this.stage.close();
      this.main.getRegistrationScreenController().populateMainGrid();
      this.main.getAppHomeController().setUpSumGrid();
   }

   @FXML
   public void cancelDelete() {
      this.stage.close();
   }

   public ArrayList<String> getExamNames() {
      ArrayList<String> examList = new ArrayList<String>();
      Connection conn = Tools.getConnected();
      String exSql = "SELECT exam FROM exams";
      String surSql = "SELECT surgery FROM surgeries";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(exSql);

         while(rst.next()) {
            examList.add(rst.getString("exam"));
         }

         rst.close();
         rst = stm.executeQuery(surSql);

         while(rst.next()) {
            examList.add(rst.getString("surgery"));
         }

         rst.close();
      } catch (SQLException var7) {
         var7.printStackTrace();
      }

      return examList;
   }
}
