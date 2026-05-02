/* Decompiler 94ms, total 437ms, lines 304 */
package app.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import app.ExMain;
import app.tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EntryModifierController {
   @FXML
   private ComboBox<String> patientId;
   @FXML
   private TextField nofacture;
   @FXML
   private TextField exam;
   @FXML
   private TextField surgery;
   @FXML
   private TextField consommable;
   @FXML
   private Label entryId;
   @FXML
   private DatePicker exDate;
   @FXML
   private Button save;
   private String savedPatientId;
   private ExMain mainApp;
   private Stage stage;
   private ArrayList<String> errorExams = new ArrayList<String>();
   private boolean modifyAllowed = true;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void setMain(ExMain main) {
      this.mainApp = main;
      this.patientId.getItems().addAll(this.getPatientIdByPeriode());
      this.patientIdComboChange();
   }

   public void initialize() {
      Node[] nodes = new Node[]{this.patientId, this.nofacture, this.exam, this.surgery, this.consommable, this.exDate};
      Tools.addNavigation(nodes);
      this.consommable.disableProperty().bind(this.surgery.textProperty().isEmpty());
      this.patientId.setEditable(true);
      this.patientId.getEditor().setOnKeyReleased((e) -> {
         String periodiqueText = this.patientId.getEditor().getText().trim();
         if (!periodiqueText.isBlank()) {
            this.patientId.getItems().clear();
            this.patientId.getItems().addAll(this.getTemporalIdByPeriode(periodiqueText));
            this.patientId.show();
         } else {
            this.patientId.getItems().clear();
            this.patientId.getItems().addAll(this.getPatientIdByPeriode());
            this.patientId.show();
         }

      });
   }

   public void patientIdComboChange() {
      this.patientId.valueProperty().addListener((obs, old, nv) -> {
         this.getEntry(nv);
      });
   }

   public ArrayList<String> getPatientIdByPeriode() {
      ArrayList<String> patientIds = new ArrayList<String>();
      if (this.mainApp.getIsOperativePeriodeSet()) {
         Connection conn = Tools.getConnected();
         String sql = "SELECT patientId FROM register WHERE monthYear='" + this.mainApp.getOperativePeriode() + "' ORDER BY patientId ASC";

         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);

            while(rst.next()) {
               patientIds.add(rst.getString("patientId"));
            }
         } catch (SQLException var6) {
            var6.printStackTrace();
         }
      }

      return patientIds;
   }

   public ArrayList<String> getTemporalIdByPeriode(String str) {
      ArrayList<String> patientIds = new ArrayList<String>();
      String var10000 = this.mainApp.getOperativePeriode();
      String sql = "SELECT patientId FROM register WHERE monthYear='" + var10000 + "' AND patientId LIKE '%" + str + "%'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            patientIds.add(rst.getString("patientId"));
         }
      } catch (SQLException var7) {
         var7.printStackTrace();
      }

      return patientIds;
   }

   public void getEntry(String patId) {
      if (!patId.isBlank() && patId != null) {
         String sql = "SELECT COUNT(id) as totalId FROM register WHERE (patientId='" + patId + "' AND monthYear='" + this.mainApp.getOperativePeriode() + "')";
         Connection conn = Tools.getConnected();

         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            rst.next();
            int idNumber = rst.getInt("totalId");
            int modifiedId;
            if (idNumber > 1) {
               //new ArrayList();
               ChoiceDialog<String> dialog = new ChoiceDialog<String>();
               dialog.setContentText("Chose which entry to modify");
               List<String> choices = this.getDifferentsEntries(patId);
               dialog.getItems().addAll(choices);
               Optional<String> entry = dialog.showAndWait();
               String chosenEntry = (String)entry.get();
               String chosenId = chosenEntry.split("/")[0];
               modifiedId = Integer.valueOf(chosenId);
            } else {
               String chosenEntry = (String)this.getDifferentsEntries(patId).get(0);
               String chosenId = chosenEntry.split("/")[0];
               modifiedId = Integer.valueOf(chosenId);
            }

            rst.close();
            this.entryId.setText(String.valueOf(modifiedId));
            this.savedPatientId=patId;
            this.getNonModifiedEntryValues(modifiedId, stm);
         } catch (SQLException var13) {
            var13.printStackTrace();
         }
      }

   }

   public void getNonModifiedEntryValues(int id, Statement stm) {
      String sql = "SELECT * FROM register WHERE id=" + id;

      try {
         ResultSet rst = stm.executeQuery(sql);
         rst.next();
         this.nofacture.setText(rst.getString("nofacture"));
         this.exam.setText(rst.getString("exams"));
         this.surgery.setText(rst.getString("surgery"));
         this.consommable.setText(rst.getString("consommable"));
         String datus = rst.getString("date");
         datus = datus.replace('/', '-');
         String[] dateP = datus.split("-");
         String fDatus = dateP[2] + "-" + dateP[1] + "-" + dateP[0];
         this.exDate.setValue(LocalDate.parse(fDatus));
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

   }

   public ArrayList<String> getDifferentsEntries(String patId) {
      ArrayList<String> infos = new ArrayList<String>();
      String sql = "SELECT * FROM register WHERE patientId='" + patId + "' and monthYear='" + this.mainApp.getOperativePeriode() + "'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            int var10000 = rst.getInt("id");
            String str = var10000 + "/" + rst.getString("patientId") + "/" + rst.getString("exams") + "/" + rst.getString("date");
            infos.add(str);
         }
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

      return infos;
   }

   @FXML
   public void saveModifications() {
      ArrayList<Label> exams = this.mainApp.getRegistrationScreenController().getExamLabels();
      ArrayList<Label> surgeryL = this.mainApp.getRegistrationScreenController().getSurgeryLabels();
      ArrayList<String> examsAndSurgery = new ArrayList<String>();

      
      for(int i = 0; i < exams.size(); ++i) {
         examsAndSurgery.add(((Label)exams.get(i)).getText().trim());
      }

      for(int i = 0; i < surgeryL.size(); ++i) {
         examsAndSurgery.add(((Label)surgeryL.get(i)).getText().trim());
      }

      String modifiedExam = this.exam.getText().trim();
      if(!modifiedExam.isBlank()) {
    	  String[] modifiedExamSplited = modifiedExam.split("/");
    	  for(int i = 0; i < modifiedExamSplited.length; ++i) {
    	         if (examsAndSurgery.indexOf(modifiedExamSplited[i]) == -1) {
    	            this.modifyAllowed = false;
    	            System.out.println("exam error " + modifiedExamSplited[i]);
    	            this.errorExams.add(modifiedExamSplited[i]);
    	         }
    	   }
      }
      
      String modifiedSurgery = this.surgery.getText().trim();
      if(!modifiedSurgery.isBlank()) {
    	  String[] modifiedSurgerySplit = modifiedSurgery.split("/");
    	  for(int i = 0; i < modifiedSurgerySplit.length; ++i) {
    		  if (examsAndSurgery.indexOf(modifiedSurgerySplit[i]) == -1) {
    	            this.modifyAllowed = false;
    	            this.errorExams.add(modifiedSurgerySplit[i]);
    	            System.out.println("surgery error ");
    	         }
    	   }
      }
     

      

      if (this.nofacture.getText().equals("")) {
         this.modifyAllowed = false;
         this.errorExams.add("no fature null");
         System.out.println("no fatcure error");
      }

      if (!this.surgery.getText().equals("") && this.consommable.getText().equals("")) {
         this.modifyAllowed = false;
         this.errorExams.add("Surgery without consommable");
         System.out.println("surgery cons error");
      }

      if (this.modifyAllowed) {
         String sql = "UPDATE register SET patientId=?, exams=?, surgery=?, monthYear=?, date=?, nofacture=?, registerDate=?, consommable=? WHERE id=?";
         String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
         Connection conn = Tools.getConnected();

         try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.savedPatientId);
            pst.setString(2, this.exam.getText().trim());
            pst.setString(3, this.surgery.getText().trim());
            String dat = ((LocalDate)this.exDate.getValue()).toString();
            String da = dat.substring(0, 7);
            pst.setString(4, da);
            String[] dataSplit = dat.split("-");
            String datus = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
            pst.setString(5, datus);
            pst.setString(6, this.nofacture.getText().trim());
            pst.setString(7, today);
            pst.setString(8, this.consommable.getText().trim());
            pst.setInt(9, Integer.valueOf(this.entryId.getText()));
            pst.execute();
            this.mainApp.getAppHomeController().sumUp();
            this.mainApp.getAppHomeController().giveTodayStatics();
            this.mainApp.setIsIntervalSet(false);
            Tools.showEphemereText("Data modified successfully");
            this.clearModifier();
         } catch (SQLException var16) {
            var16.printStackTrace();
         }
      }

   }

   public void clearModifier() {
      this.entryId.setText("");
      this.errorExams.clear();
      this.consommable.setText("");
      this.exam.setText("");
      this.exDate.getEditor().setText("");
      this.modifyAllowed = true;
      this.nofacture.setText("");
      this.patientId.getEditor().setText("");
      this.surgery.setText("");
   }

   @FXML
   public void cancelModifications() {
      this.stage.close();
      this.mainApp.setIsIntervalSet(false);
   }
}
