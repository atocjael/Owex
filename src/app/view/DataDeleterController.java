/* Decompiler 25ms, total 337ms, lines 100 */
package app.view;

import app.ExMain;
import app.model.Entry;
import app.tools.Tools;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DataDeleterController {
   @FXML
   private TableView<Entry> entryTable;
   @FXML
   private TableColumn<Entry, String> exam;
   @FXML
   private TableColumn<Entry, String> surgery;
   @FXML
   private TableColumn<Entry, String> date;
   @FXML
   private TableColumn<Entry, String> patientId;
   @FXML
   private TableColumn<Entry, Boolean> check;
   private ExMain mainApp;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void setMainApp(ExMain main) {
      this.mainApp = main;
      this.entryTable.setItems(this.mainApp.getEntryList());
   }

   public void initialize() {
      this.entryTable.setEditable(true);
      this.exam.setCellValueFactory(new PropertyValueFactory<Entry, String>("entryExam"));
      this.surgery.setCellValueFactory(new PropertyValueFactory<Entry, String>("entrySurgery"));
      this.date.setCellValueFactory(new PropertyValueFactory<Entry, String>("entryDate"));
      this.patientId.setCellValueFactory(new PropertyValueFactory<Entry, String>("entryPatientId"));
      this.check.setCellFactory(CheckBoxTableCell.forTableColumn(this.check));
      this.check.setCellValueFactory((t) -> {
         return ((Entry)t.getValue()).getEntrySelected();
      });
   }

   @FXML
   public void deleteSelectedEntries() {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Owex - Message");
      alert.setHeaderText("DELETING DATA");
      alert.setContentText("Are you sure you want to delete these entries? \nNote that they will be definitely lost!");
      ButtonType cancel = new ButtonType("Cancel");
      ButtonType delete = new ButtonType("Delete");
      alert.getButtonTypes().clear();
      alert.getButtonTypes().add(cancel);
      alert.getButtonTypes().add(delete);
      Optional<ButtonType> option = alert.showAndWait();
      if (((ButtonType)option.get()).equals(delete)) {
         Connection conn = Tools.getConnected();

         for(int i = 0; i < this.entryTable.getItems().size(); ++i) {
            if ((Boolean)this.check.getCellData(i)) {
               Entry var10000 = (Entry)this.entryTable.getItems().get(i);
               String sql = "DELETE FROM register WHERE id=" + var10000.getEntryId();

               try {
                  Statement stm = conn.createStatement();
                  stm.executeUpdate(sql);
               } catch (SQLException var9) {
                  var9.printStackTrace();
               }
            }
         }
      }

      Tools.showEphemereText("Entries deleted");
      this.mainApp.getAppHomeController().sumUp();
      this.mainApp.setIsIntervalSet(false);
      this.mainApp.getAppHomeController().giveTodayStatics();
      this.stage.close();
   }

   @FXML
   public void cancelDelete() {
      this.mainApp.setIsIntervalSet(false);
      this.stage.close();
   }
}
