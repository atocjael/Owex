/* Decompiler 4ms, total 904ms, lines 33 */
package app.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Drug {
   private StringProperty drugN = new SimpleStringProperty();
   private BooleanProperty drugSelected = new SimpleBooleanProperty();

   public void setDrugN(String n) {
      this.drugN.set(n);
   }

   public void setDrugSelected(boolean b) {
      this.drugSelected.set(b);
   }

   public String getDrugN() {
      return (String)this.drugN.get();
   }

   public BooleanProperty getDrugSelect() {
      return this.drugSelected;
   }

   public Drug(String n, boolean b) {
      this.drugN.set(n);
      this.drugSelected.set(b);
   }
}
