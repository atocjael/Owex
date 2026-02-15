/* Decompiler 35ms, total 379ms, lines 75 */
package app.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Entry {
   private IntegerProperty entryId = new SimpleIntegerProperty();
   private StringProperty entryExam = new SimpleStringProperty();
   private StringProperty entrySurgery = new SimpleStringProperty();
   private StringProperty entryDate = new SimpleStringProperty();
   private BooleanProperty entrySelected = new SimpleBooleanProperty();
   private StringProperty entryPatientId = new SimpleStringProperty();

   public Entry(int id, String pid, String exam, String surgery, String date, boolean selec) {
      this.entryId.set(id);
      this.entryPatientId.set(pid);
      this.entryExam.set(exam);
      this.entrySurgery.set(surgery);
      this.entryDate.set(date);
      this.entrySelected.set(selec);
   }

   public void setEntryId(int id) {
      this.entryId.set(id);
   }

   public void setEntryPatientId(String pid) {
      this.entryPatientId.set(pid);
   }

   public void setEntryExam(String ex) {
      this.entryExam.set(ex);
   }

   public void setEntrySurgery(String sur) {
      this.entrySurgery.set(sur);
   }

   public void setEntryDate(String date) {
      this.entryDate.set(date);
   }

   public void setEntrySelected(boolean sel) {
      this.entrySelected.set(sel);
   }

   public int getEntryId() {
      return this.entryId.get();
   }

   public String getEntryExam() {
      return (String)this.entryExam.get();
   }

   public String getEntrySurgery() {
      return (String)this.entrySurgery.get();
   }

   public String getEntryDate() {
      return (String)this.entryDate.get();
   }

   public String getEntryPatientId() {
      return (String)this.entryPatientId.get();
   }

   public BooleanProperty getEntrySelected() {
      return this.entrySelected;
   }
}
