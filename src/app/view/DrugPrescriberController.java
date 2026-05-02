/* Decompiler 21ms, total 348ms, lines 96 */
package app.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.ExMain;
import app.model.Drug;
import app.tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DrugPrescriberController {
   @FXML
   private TableView<Drug> drugTable;
   @FXML
   private TableColumn<Drug, String> drugName;
   @FXML
   private TableColumn<Drug, Boolean> check;
   @FXML
   private TextField patientId;
   private Stage stage;
   private ExMain main;

   public void setMain(ExMain m) {
      this.main = m;
      this.drugTable.setItems(this.main.getDrugList());
   }

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      this.drugTable.setEditable(true);
      this.drugName.setCellValueFactory(new PropertyValueFactory<Drug, String>("drugN"));
      this.check.setCellFactory(CheckBoxTableCell.forTableColumn(this.check));
      this.check.setCellValueFactory((t) -> {
         return ((Drug)t.getValue()).getDrugSelect();
      });
   }

   @FXML
   public void prescribeDrugs() {
      if (!this.patientId.getText().isBlank()) {
         String drugs = "";

         for(int i = 0; i < this.drugTable.getItems().size(); ++i) {
            if ((Boolean)this.check.getCellData(i)) {
               drugs = drugs + ((Drug)this.drugTable.getItems().get(i)).getDrugN() + "/";
            }
         }

         drugs = drugs.isBlank() ? "" : drugs.substring(0, drugs.length() - 1);
         String sql = "INSERT INTO prescription (patient, drug, prescriptiontime, prescriptionmonth) VALUES (?,?,?,?)";
         String time = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
         String month = (new SimpleDateFormat("MM/yyyy")).format(Calendar.getInstance().getTime());
         Connection conn = Tools.getConnected();

         try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, this.patientId.getText().trim());
            stm.setString(2, drugs);
            stm.setString(3, time);
            stm.setString(4, month);
            stm.execute();
            Tools.showEphemereText("Drugs prescribed successfully");
            this.clearTable();
         } catch (SQLException var7) {
            var7.printStackTrace();
         }
      }

   }

   public void clearTable() {
      this.patientId.setText("");

      for(int i = 0; i < this.main.getDrugList().size(); ++i) {
         ((Drug)this.main.getDrugList().get(i)).setDrugSelected(false);
      }

   }

   @FXML
   public void cancelPrescription() {
      this.stage.close();
   }
}
