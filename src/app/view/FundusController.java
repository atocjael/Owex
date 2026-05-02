package app.view;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import app.ExMain;
import app.tools.Tools;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FundusController {

    @FXML
    private RadioButton acquiredT;

    @FXML
    private TextField woolText;

    @FXML
    private RadioButton refPresent;

    @FXML
    private CheckBox vbaseCheck;

    @FXML
    private RadioButton oraN;

    @FXML
    private RadioButton copperLight;

    @FXML
    private RadioButton tHazy;

    @FXML
    private RadioButton vbaseN;

    @FXML
    private RadioButton pDepression;

    @FXML
    private TextField anevrismText;

    @FXML
    private CheckBox cnevusCheck;

    @FXML
    private CheckBox gunnCheck;

    @FXML
    private RadioButton oraY;

    @FXML
    private ComboBox<String> pEye;

    @FXML
    private RadioButton sLarge;

    @FXML
    private Tab identification;

    @FXML
    private RadioButton pvdPartial;

    @FXML
    private CheckBox cupAssymetry;

    @FXML
    private RadioButton iCellophane;

    @FXML
    private CheckBox bonnetCheck;

    @FXML
    private RadioButton vbaseY;

    @FXML
    private RadioButton pElevation;

    @FXML
    private TextField cvenusText;

    @FXML
    private TextField avRatio;

    @FXML
    private CheckBox flameCheck;

    @FXML
    private RadioButton detachment;

    @FXML
    private CheckBox woolCheck;

    @FXML
    private TextField emboliText;

    @FXML
    private RadioButton retinoschisis;

    @FXML
    private CheckBox dotCheck;

    @FXML
    private TextField infectiousText;

    @FXML
    private CheckBox aNarrowing;

    @FXML
    private RadioButton mHazy;

    @FXML
    private RadioButton Tnormal;

    @FXML
    private TextField hRatio;

    @FXML
    private CheckBox sheathCheck;

    @FXML
    private CheckBox salusCheck;

    @FXML
    private RadioButton rHyperemia;

    @FXML
    private CheckBox neoCheck;

    @FXML
    private RadioButton pvdComplete;

    @FXML
    private RadioButton cPale;

    @FXML
    private TextField flameText;

    @FXML
    private VBox maculaChecks;

    @FXML
    private TextField vRatio;

    @FXML
    private RadioButton aHole;

    @FXML
    private RadioButton tuft;

    @FXML
    private RadioButton refAbsent;

    @FXML
    private RadioButton mSharp;

    @FXML
    private CheckBox riskCheck;

    @FXML
    private RadioButton pFlat;

    @FXML
    private CheckBox oraCheck;

    @FXML
    private CheckBox vDilation;

    @FXML
    private RadioButton pvdAbsent;

    @FXML
    private RadioButton silverLight;

    @FXML
    private RadioButton mElevated;

    @FXML
    private CheckBox boatCheck;

    @FXML
    private CheckBox pNeoCheck;

    @FXML
    private RadioButton iSatin;

    @FXML
    private RadioButton pvdTraction;

    @FXML
    private CheckBox irmaCheck;

    @FXML
    private RadioButton rPale;

    @FXML
    private TextField pName;

    @FXML
    private CheckBox beadingCheck;

    @FXML
    private RadioButton tear;

    @FXML
    private RadioButton sSmall;

    @FXML
    private Tab retina;

    @FXML
    private TextField pAge;

    @FXML
    private RadioButton nLight;

    @FXML
    private Tab periphery;

    @FXML
    private RadioButton cNormal;

    @FXML
    private RadioButton sNormal;

    @FXML
    private RadioButton congenitalT;

    @FXML
    private CheckBox benignCheck;

    @FXML
    private RadioButton rPink;

    @FXML
    private CheckBox acuteCheck;

    @FXML
    private Tab onh;

    @FXML
    private CheckBox anevrismCheck;

    @FXML
    private RadioButton cTigroid;

    @FXML
    private RadioButton pavingstone;

    @FXML
    private TextField neoText;

    @FXML
    private ComboBox<String> pGender, fundusIndication;

    @FXML
    private TextField dotText;

    @FXML
    private CheckBox emboliCheck;

    @FXML
    private CheckBox tortCheck;

    @FXML
    private CheckBox infectiousCheck;

    @FXML
    private RadioButton wwop;

    @FXML
    private RadioButton lattice;

    @FXML
    private VBox discOtherSigns, retinaBox, vesselBox, pBox;

    @FXML
    private Tab macula;

    @FXML
    private Tab vessels;
    
    @FXML
    private Button changeEyeBtn, saveBtn , onhBtn, finishBtn, maculaBtn, retinaBtn, vesselsBtn, peripheryBtn;
    
    @FXML
    private GridPane vascGrid, embGrid; 
    
    @FXML
    private ToggleGroup discSize, margins, rimColor, freflex, rprofile, ireflex, pvd, transparency, bcolor, arterialR,
    serrata, vitreousb;
    
    @FXML
    private TabPane globTabs;
    
    @FXML
    private CheckBox pavCheck, wwopCheck, latticeCheck, tuftCheck, schisisCheck, tearCheck, holeCheck, detCheck;
    
    private BooleanProperty isPatientInfoSet= new SimpleBooleanProperty();
    
    //variable pour stocker le resultat final ---length 2 au max si l oeil atete change -- 
    private ArrayList<ArrayList<ArrayList<String>>> fundusResults= new ArrayList<ArrayList<ArrayList<String>>>();
    
    //suivre le changement de l'oeil - on ne peut que changer une seule fois 
    private BooleanProperty isEyeChanged= new SimpleBooleanProperty();
    //boolean controlling the closing and eye change ---- 
    private BooleanProperty isOnhCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isMaculaCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isRetinaCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isVesselsCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isPeripheryCompleted= new SimpleBooleanProperty();
    
    private ArrayList<ArrayList<String>> activeEye= new ArrayList<ArrayList<String>>();
    
    private ExMain main;
    
    private Stage stage;
    
    private boolean allowFinish=false, allowOneEye=false, allowEyeIncomplet=false, allowQuit=false;
    
    private String filename="";
    
    
    public void setMain(ExMain m) {this.main=m;}
    public void setStage(Stage stg) {
    	this.stage=stg;
    	stage.setOnCloseRequest((e)->{
    		if(this.isPatientInfoSet.get()) {
    			finishCompletion(e);
    		}
    	});
    }
    
    public void initialize() {
    	//------------------------Info tabs -------------------------
    	//initialisation des valeurs
    	isPatientInfoSet.set(false);
    	isEyeChanged.set(false);
    	
    	bindTabs(); //les 4 tabs ne peuvent souvrir que si les infos de patients sont deja conserve
    	pName.requestFocus();
    	Node[] pInfos= {pName, pAge};
    	Tools.addNavigation(pInfos);
    	
    	//initialisons les genres et les yeux 
    	pEye.getItems().add("OD");
    	pEye.getItems().add("OS");
    	pEye.getSelectionModel().select("OD");
    	
    	pGender.getItems().add("M");
    	pGender.getItems().add("F");
    	pGender.getSelectionModel().select("M");
    	
    	this.fundusIndication.getItems().addAll(List.of("Exploration","Diabetes", "Arterial Hypertension", "Intracranial Hypertension"));
    	fundusIndication.getSelectionModel().select(0);
    	
    	this.initializeToggle();
    	
    	
    	//sur tab retina se rassurer k on a cocher le checkbox afin de pouvoir indiquer l emplacement de la lesion 
    	setGridBindings(embGrid);
    	setGridBindings(vascGrid);
    	
    	periphery.setOnSelectionChanged((e)->{
    		if(periphery.isSelected()) {
    			if(this.isEyeChanged.get()) {
    				this.peripheryBtn.setText("Save and Finish");
    			}else {
    				this.peripheryBtn.setText("Save and Change eye");
    			}
    		}
    	});
    	
    }
   
    
    //-------------Methodes lies au fxml ---------------------------------------------------------------
    
    @FXML
    public void savePatientInfos() {
    	//verifier si le nom - est complete ---
    	if(this.isPatientInfoCompleted()) {
    		ArrayList<ArrayList<String>> oneEye= new ArrayList<ArrayList<String>>();
    		this.fundusResults.add(oneEye);
    		
    		//on verifie si l oeil a tet change pr savoir ou ;ettre les resultas
    		if(this.isEyeChanged.get()) {
    			this.activeEye=this.fundusResults.get(1);
    		}else {
    			this.activeEye=this.fundusResults.get(0);
    		}
    		
    		//save patient infos 
    		ArrayList<String> pinfo= new ArrayList<String>();
    		pinfo.add(pName.getText().trim());
    		pinfo.add(pAge.getText().trim());
    		pinfo.add(fundusIndication.getSelectionModel().getSelectedItem().trim());
    		pinfo.add(pGender.getSelectionModel().getSelectedItem().trim());
    		pinfo.add(pEye.getSelectionModel().getSelectedItem().trim());
    		
    		this.activeEye.add(pinfo);
    		
    		String[] toReplace= {"@names@", "@age@", "@indication@", "@sexe@", "@eye@"};
    		String[] replaceWith= pinfo.toArray(new String[0]);
    		filename = Tools.creerDocument(pName.getText().trim()+"_"+pEye.getSelectionModel().getSelectedItem().trim(), "PROTOCOLS");
    		
    		Thread th= new Thread(()->{
    			registerPart(toReplace, replaceWith, true);
    			Platform.runLater(()->{
    				main.getAppHomeController().removeIndicator("Patienyt info successfully registered");
    				this.isPatientInfoSet.set(true);
    	    		globTabs.getSelectionModel().select(onh);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		main.getAppHomeController().showIndicator();
    	}else {
    		System.out.println("Complete all info");
    	}
    	//enregistrer 
    }
    
   
    
    
    
    @FXML
    public void saveOnhResults() {
    	ToggleGroup[] onhToggle={discSize, margins, rimColor};
    	if(verifyToggle(onhToggle) && !vRatio.getText().isBlank() && !hRatio.getText().isBlank()) {
    		ArrayList<String> onhResults= new ArrayList<String>();
    		
    		onhResults.addAll(getSelectedValues(onhToggle));
    		
    		onhResults.add(vRatio.getText().trim());
    		onhResults.add(hRatio.getText().trim());
    		onhResults.add((cupAssymetry.isSelected())?"Asymétrie entre OD/OS >0.2 ":"No significant assymerty between");
    		
    		onhResults.add(getSelectedCkecks(discOtherSigns));
    		
    		this.activeEye.add(onhResults);
    		
    		String[] toReplace= {"@size@", "@margin@", "@rim@", "@cdv@", "@cdh@", "@asy@" ,"@signesonh@"};
    		String[] replaceWith= onhResults.toArray(new String[0]);
    		
    		Thread th= new Thread(()->{
    			registerPart(toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				main.getAppHomeController().removeIndicator("ONH info successfully registered");
    				onhBtn.setDisable(true);
    	    		globTabs.getSelectionModel().select(macula);
    	    		isOnhCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		main.getAppHomeController().showIndicator();
    	}else {
    		System.out.println("Complete disc size, margins, rim color and C/D ratio It's mandatory");
    	}
    }
    
    @FXML
    public void saveMaculaResults() {
    	ToggleGroup[] mToggle= {freflex, rprofile, ireflex, pvd};
    	ArrayList<String> results= new ArrayList<String>();
    	if(this.verifyToggle(mToggle)) {
    		results.addAll(getSelectedValues(mToggle));
    		results.add(this.getSelectedCkecks(maculaChecks));
    		
    		activeEye.add(results);
    		
    		String[] toReplace= {"@mreflex@", "@profile@", "@ilm@", "@dvp@", "@lesion@"};
    		String[] replaceWith= results.toArray(new String[0]);
    		
    		Thread th= new Thread(()->{
    			registerPart(toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				main.getAppHomeController().removeIndicator("Macula info successfully registered");
    				maculaBtn.setDisable(true);
    	    		globTabs.getSelectionModel().select(retina);
    	    		isMaculaCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		main.getAppHomeController().showIndicator();
    		
    		
    	}else {
    		System.out.println("It's mandatory to complete reflex - profile - ILm and PVD states");
    	}
    }
    
    @FXML
    public void saveRetinaResults() {
    	ToggleGroup[] rToggle= {transparency, bcolor};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(rToggle)) {
    		r.addAll(this.getSelectedValues(rToggle));
    		r.add(this.getResultsFromGrid(vascGrid));
    		r.add(this.getResultsFromGrid(embGrid));
    	}
    	
    	activeEye.add(r);
    	
    	String[] toReplace= {"@fond@", "@color@", "@diabetes@", "@signesretina@"};
		String[] replaceWith= r.toArray(new String[0]);
		
		Thread th= new Thread(()->{
			registerPart(toReplace, replaceWith, false);
			Platform.runLater(()->{
				main.getAppHomeController().removeIndicator("Retina info successfully registered");
				retinaBtn.setDisable(true);
				globTabs.getSelectionModel().select(vessels);
				isRetinaCompleted.set(true);
			});
		});
		
		th.setDaemon(true);
		th.start();
		
		main.getAppHomeController().showIndicator();
		
		
    }
    
    @FXML
    public void saveVesselsResults() {
    	ToggleGroup[] vToggle= {arterialR};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(vToggle) && !this.avRatio.getText().isBlank() ) {
    		r.addAll(this.getSelectedValues(vToggle));
    		r.add(this.avRatio.getText().trim());
    		
    		CheckBox[] av= {aNarrowing, vDilation};
    		CheckBox[] cross= {gunnCheck, salusCheck, bonnetCheck};
    		CheckBox[] other= {tortCheck, sheathCheck};
    		
    		r.add(this.getSelectedCkecks(av));
    		r.add(this.getSelectedCkecks(cross));
    		r.add(this.getSelectedCkecks(other));
    	}
    	
    	activeEye.add(r);
    	
    	String[] toReplace= {"@reflet@","@av@", "@retrecissement@", "@croisement@", "@autres@"};
		String[] replaceWith= r.toArray(new String[0]);
		
		Thread th= new Thread(()->{
			registerPart(toReplace, replaceWith, false);
			Platform.runLater(()->{
				main.getAppHomeController().removeIndicator("Vessels info successfully registered");
				vesselsBtn.setDisable(true);
				globTabs.getSelectionModel().select(periphery);
				this.isVesselsCompleted.set(true);
			});
		});
		
		th.setDaemon(true);
		th.start();
		
		main.getAppHomeController().showIndicator();
    }
    
    @FXML
    public void savePeripheryResults() {
    	ToggleGroup[] pToggle= {serrata, vitreousb};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(pToggle)) {
    		r.addAll(this.getSelectedValues(pToggle));
    		
    		
    		
    		CheckBox[] benign={pavCheck, wwopCheck};
    		CheckBox[] risk= {latticeCheck, tuftCheck, schisisCheck};
    		CheckBox[] acute= {tearCheck, holeCheck, detCheck, pNeoCheck};
    		
    		r.add(this.getSelectedCkecks(benign));
    		r.add(this.getSelectedCkecks(risk));
    		r.add(this.getSelectedCkecks(acute));
    	}
    	activeEye.add(r);
    	
    	String[] toReplace= {"@ora@", "@base@", "@benign@", "@high@", "@acute@"};
		String[] replaceWith= r.toArray(new String[0]);
		
		Thread th= new Thread(()->{
			registerPart(toReplace, replaceWith, false);
			Platform.runLater(()->{
				main.getAppHomeController().removeIndicator("Periphery info successfully registered");
				
				peripheryBtn.setDisable(true);
				this.isPeripheryCompleted.set(true);
				
				if(!isEyeChanged.get()) {
					this.changeEyeBtn.fire();
				}else {
					this.finishBtn.fire();
				}
			});
		});
		
		th.setDaemon(true);
		th.start();
		
		main.getAppHomeController().showIndicator();
    }
    
    @FXML
    public void changeEye() {
    	if(isPatientInfoSet.get()) {//on verifie si les infos etaient deja enregistre ds ce csd le vqleur est true
    		if(this.verifyTabsCompletion() || this.allowEyeIncomplet) { 
    			//la condition cihaut verifie si les tabs sont tous completes pour l'oeil actif
    			//ou si on a decide de continuer sans completer l'autre oeil lors de la fermeture
    			int i=pEye.getSelectionModel().getSelectedIndex();
        		if(i==0) {
        			i++;
        		}else {
        			i--;
        		}
        		
        		pEye.getSelectionModel().select(i);
        		isEyeChanged.set(true);
        		savePatientInfos();
        		
        		//on efface les donnees ds les tabs / reinitiqlise les boutons 
        		reset(false);
        		initializeToggle();
        		setPermittersFalse();
    		}else {
    			String sms=this.getNotCompletedMessage();
    			this.showMessage(sms, sms, ()->this.changeEye(), ()->stopEyeChange(), false);
    		}
    		
    	}else {
    		System.out.println("Infos not yet registered - change it manually");
    	}
    }
    
    @FXML
    public void finishCompletionBtn() {
    	System.out.println("finidh colled "+ allowFinish);
    	
    	if(verifyTabsCompletion() || allowFinish) {//verifie si ts les tqbs de l'oeil on ete rempli --si oui
			if(isEyeChanged.get() || allowOneEye) {
				reset(true);  //reinitialiser 
    		}else {
    			String sms="Only one eye is compeleted.\nDo you want to go on with one eye ?";
    			this.showMessage(sms, "eye", ()->this.finishCompletionBtn(), ()->this.changeEye(),false);
    			//on informe k l autre eyen est pqs rempli et on demqnde de remplir ou lq permision de continuer
    		}
    	}else if(allowQuit){
    		
    		reset(true);
    		fundusResults.clear();
    		setPermittersFalse();
    		initializeToggle();
        	globTabs.getSelectionModel().select(0);
    	}else {
    		String sms=this.getNotCompletedMessage();
   		 	this.showMessage(sms, "tabs", ()->this.finishCompletionBtn(), ()->this.stopCompletion(),true);
    	}
    }
    
    
    
   
    
    // *** autres methodes de la classe-----------------------------------------------------------------------
    
    
    public void bindTabs() {
    	Tab[] tabs= {onh, macula, retina, vessels, periphery};
    	Node[] pInfos= {pName, fundusIndication, pGender, pAge, pEye};
    	
    	for (Tab tab:tabs){
    		tab.disableProperty().bind(isPatientInfoSet.not());
    	}
    	
    	for(Node n : pInfos) {
    		n.disableProperty().bind(isPatientInfoSet);
    	}
    	
    	changeEyeBtn.disableProperty().bind(isEyeChanged.or(isPatientInfoSet.not()));
    	saveBtn.disableProperty().bind(isPatientInfoSet);
    	finishBtn.disableProperty().bind(isPatientInfoSet.not());
    }
    
    
    public void finishCompletion(Event e) {
    	//TODO -verifie si l oeil en cours d enregistrement est bien enregistre
    	//si oui on finit le stage sinon propose de completer en vide ou de continuer ou d'annuler l enreg
    	if(!verifyTabsCompletion() && !allowFinish && !allowQuit) {  
    		String sms=this.getNotCompletedMessage();
   		 	this.showMessage(sms, "tabs", ()->this.finishCompletion(e), ()->this.stopCompletion(e),true);	
    	}
    }
   
    
    
    public void showMessage(String message, String type,  Runnable onYes, Runnable onNo, boolean bool) {
    	 
		 Alert alert = new Alert(AlertType.CONFIRMATION);
         alert.setHeaderText(message);
         alert.setTitle("Confirm action");
         ButtonType da = new ButtonType("Yes");
         ButtonType net = new ButtonType("No");
         ButtonType udalit = new ButtonType("Quit");
        // Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image(IolMain.class.getResourceAsStream("images/logot.png")));
         alert.getButtonTypes().clear();
         alert.getButtonTypes().add(da);
         alert.getButtonTypes().add(net);
         
         if(bool) {
        	 alert.getButtonTypes().add(udalit);
         }
         
         
         Optional<ButtonType> option = alert.showAndWait();
         if (option.get() == da) {
        	registerEmptyTabs(onYes, type);  //on complete les tabs vide et on donne les permissions
         }else if(option.get()==net) {  //on appuie sur non ---
        	 onNo.run();
         }else if(option.get()==udalit) { // on appuie sur Quit 
        	 try {
        		 closeWord();
        		 
                 boolean deleted=Files.deleteIfExists(Paths.get(filename));
                 
                 if (deleted) {
                     main.getAppHomeController().populateIolDirectory();
                 } 
             } catch (IOException e) {
                e.printStackTrace();
             }
        	 allowQuit=true;
        	 onYes.run();
         }
    }
    
    
	public void registerEmptyTabs(Runnable onYes, String type) {
    	ArrayList<String[][]> emptyTabs= this.getEmptyTabsReplacements();
    	
    	Thread th= new Thread(()->{
    		for(String[][] strT : emptyTabs) {
        		String[] toReplace= strT[0];
        		String[] replaceWith=strT[1];
        		
        		this.registerPart(toReplace, replaceWith, false);
        	}
    		
    		Platform.runLater(()->{
    			main.getAppHomeController().removeIndicator("Not completed tabs left empty successfully");
    			
    			switch(type) {
    			case "tabs":
    				this.allowFinish=true;
    				break;
    			case "eye":
    				this.allowOneEye=true;
    				break;
    			default:
    				this.allowEyeIncomplet=true;
    				break;
    			}
    			
    			System.out.println(type+"  "+allowFinish+" "+allowOneEye+" "+allowEyeIncomplet);
    			
    			onYes.run();
    		});
    	});
    	
    	th.setDaemon(true);
    	th.start();
    	
    	this.main.getAppHomeController().showIndicator();
    	
	}
    
    public ArrayList<String[][]> getEmptyTabsReplacements(){
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isVesselsCompleted, isPeripheryCompleted};
    	String[] onhr= {"@size@", "@margin@", "@rim@", "@cdv@", "@cdh@", "@asy@" ,"@signesonh@"};
    	String[] macular= {"@mreflex@", "@profile@", "@ilm@", "@dvp@", "@lesion@"};
    	String[] retinar= {"@fond@", "@color@", "@diabetes@", "@signesretina@"};
    	String[] vesselsr= {"@reflet@","@av@", "@retrecissement@", "@croisement@", "@autres@"};
    	String[] peripheryr= {"@ora@", "@base@", "@benign@", "@high@", "@acute@"};
    	
    	String[][] toReplaces= {onhr, macular, retinar, vesselsr, peripheryr};
    	
    	int index=0;
    	
    	ArrayList<String[][]> emptyTabs= new ArrayList<String[][]>();
    	
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			String[] toReplace=toReplaces[index];
    			String[] replaceW= new String[toReplace.length];
    			
    			for(int i=0; i<toReplace.length; i++) {
    				replaceW[i]="";
    			}
    			
    			String[][] etab= {toReplace, replaceW};
    			
    			print_r(etab, "");
    			emptyTabs.add(etab);
    			b.set(true);
    		}
    		
    		index++;
    	}
    	
    	return emptyTabs; 
    }
    
    
    
	public void stopCompletion() {
    	
    }
	
	public void stopCompletion(Event e) {
    	e.consume();
    }

    public void stopEyeChange() {
    	
    }
    
    public boolean isPatientInfoCompleted() { //verifie si les infos du patient sont complet
    	return (!pName.getText().isBlank() && !pGender.getSelectionModel().getSelectedItem().isBlank() &&
    			!pEye.getSelectionModel().getSelectedItem().isBlank() &&!pAge.getText().isBlank()); 
    }
    
    
    public static void print_r(Object obj, String indent) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            System.out.println(indent + "[");
            for (Object item : list) {
                print_r(item, indent + "    ");
            }
            System.out.println(indent + "]");
        } else {
            System.out.println(indent + obj);
        }
    }
    
    public String getSelectedCkecks(VBox myVBox) {
      String selectedItems ="";
        for (Node node : myVBox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (cb.isSelected()) {
                    selectedItems+=(selectedItems.isBlank())?cb.getText():" , "+cb.getText();
                }
            }
        }
        
        if(selectedItems.isBlank()) {
        	selectedItems="Aucun";
        }
        
        return selectedItems;
    }
    
    public String getSelectedCkecks(CheckBox[] checks) {
        String selectedItems ="";
          for (CheckBox node : checks) {
             if (node.isSelected()) {
                  selectedItems+=(selectedItems.isBlank())?node.getText():" , "+node.getText();
             }  
          }
          
          if(selectedItems.isBlank()) {
          	selectedItems="Aucun";
          }
          
          return selectedItems;
      }
    
    
    
    public String getSelectedValue(ToggleGroup t) {
    	if(t.getSelectedToggle()!=null) {
    		RadioButton r=(RadioButton)t.getSelectedToggle();
    		return r.getText().trim();
    	}else {
    		return "";
    	}
    }
    
    public ArrayList<String> getSelectedValues(ToggleGroup[] tg){
    	ArrayList<String> values= new ArrayList<String>();
    	for(ToggleGroup t: tg) {
    		values.add(this.getSelectedValue(t));
    	}
    	
    	return values;
    }
    
    public boolean verifyToggle(ToggleGroup [] tg) {
    	boolean selected=true;
    	
    	for(ToggleGroup t:tg) {
    		if(t.getSelectedToggle()==null) {
    			selected=false;
    		}
    	}
    	
    	return selected;
    }
    
    public void unselectToggle(ToggleGroup[] tg) {
    	for(ToggleGroup t:tg) {
    		if(t.getSelectedToggle()!=null) {
    			t.getSelectedToggle().setSelected(false);
    		}
    	}
    }
    
    public void unselectVbox(Parent vb) {
    	 for (Node node : vb.getChildrenUnmodifiable()) {
             if (node instanceof CheckBox) {
                 CheckBox cb = (CheckBox) node;
                 if (cb.isSelected()) {
                     cb.setSelected(false);
                 }
             }
             
             if(node instanceof Parent) {
            	 unselectVbox((Parent)node);
             }
         }
    }
    
    public void clearTextField(Parent vb) {
    	for (Node node : vb.getChildrenUnmodifiable()) {
            if (node instanceof TextField) {
                TextField cb = (TextField) node;
                cb.setText("");
            }
            
            if(node instanceof Parent) {
            	clearTextField((Parent)node);
            }
        }
    }
    
    public void clearTab(String tabname) {
    	switch(tabname) {
    	case "onh":
    		ToggleGroup[] onhToggle={discSize, margins, rimColor};
    		unselectToggle(onhToggle);
    		unselectVbox(discOtherSigns);
    		hRatio.setText("");
    		vRatio.setText("");
    		cupAssymetry.setSelected(false);
    		
    		break;
    		
    	case "macula":
    		ToggleGroup[] mToggle= {freflex, rprofile, ireflex, pvd};
    		unselectToggle(mToggle);
    		unselectVbox(maculaChecks);
    		break;
    		
    	case "retina":
    		ToggleGroup[] rToggle= {transparency, bcolor};
    		unselectToggle(rToggle);
    		unselectVbox(retinaBox);
    		clearTextField(retinaBox);
    		break;
    		
    	case "vessels":
    		avRatio.setText("");
    		ToggleGroup[] vToggle= {arterialR};
    		this.unselectToggle(vToggle);
    		this.unselectVbox(vesselBox);
    		break;
    		
    	case "periphery":
    		ToggleGroup[] pToggle= {serrata, vitreousb};
    		this.unselectToggle(pToggle);
    		this.unselectVbox(pBox);
    	}
    }
    
    public void setGridBindings(GridPane gp) {
    	 for (int i = 0; i < gp.getRowCount(); i++) {
    	        Node checkNode = getNodeByRowColumnIndex(i, 0, gp); // Colonne 0
    	        Node textNode = getNodeByRowColumnIndex(i, 1, gp);  // Colonne 1

    	        if (checkNode instanceof CheckBox && textNode instanceof TextField) {
    	            ((TextField) textNode).disableProperty().bind(((CheckBox) checkNode).selectedProperty().not());
    	        }
    	    }
    	
    }
    
    public String getResultsFromGrid(GridPane gp){
    	String res="";
    	for (int i = 0; i < gp.getRowCount(); i++) {
 	        Node checkNode = getNodeByRowColumnIndex(i, 0, gp); // Colonne 0
 	        Node textNode = getNodeByRowColumnIndex(i, 1, gp);  // Colonne 1

 	        if (checkNode instanceof CheckBox && textNode instanceof TextField) {
 	          if(((CheckBox) checkNode).isSelected()) {
 	        	  
 	        	     //((CheckBox) checkNode).getText()+"/"+((TextField) textNode).getText();
 	        	  String rep=((CheckBox) checkNode).getText()+" "+((TextField) textNode).getText();
 	        	  res+=(res.isBlank())?rep:","+rep;
 	          }
 	        }
 	    }
    	
    	if(res.isBlank()) {
    		res="Aucun";
    	}
    	return res;
    	
    	
    }
    
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
        	int r=(GridPane.getRowIndex(node)==null)?0:GridPane.getRowIndex(node);
        	int c=(GridPane.getColumnIndex(node)==null)?0:GridPane.getColumnIndex(node);
        	
            if (r == row && c== column) {
                return node;
            }
        }
        return null;
    }
    
    
    public void clearAllTabs() {
    	//TODO efface les valeurs choisi ds les tabs et reactive les boutons des tabs 
    	String[] tabs= {"onh", "macula", "retina", "vessels", "periphery"};
    	for(String  t: tabs) {
    		clearTab(t);
    	}
    	
    	enableBtns();
    }
    
    
    
    public boolean verifyTabsCompletion() {
    	
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isPeripheryCompleted,isVesselsCompleted};
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public String getNotCompletedMessage() {
    	String message="Be careful! \n The following tabs are not completed for this eye.\n";
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isVesselsCompleted, isPeripheryCompleted};
    	String[] tabs= {"onh", "macula", "retina", "vessels", "periphery"};
    	int index=0;
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			message+=" → "+tabs[index]+"\n";
    		}
    		index++;
    	}
    	
    	message+="Do you want to leave them uncompleted?";
    	return message;
    }
    
    public void createFundusProtocol() {
		reset(true);
		fundusResults.clear();
    	globTabs.getSelectionModel().select(0);
    }
    
    public void enableBtns() {
    	Button[] btns= {onhBtn, maculaBtn, retinaBtn, vesselsBtn, peripheryBtn};
    	for(Button btn: btns) {
    		btn.setDisable(false);
    	}
    }
    
    public void reset(boolean bool) {
    	//TODO remettre les tabs a defaut - les valeurs des booleans a defaut - if true on efface le patient ---finish method --if false on laisse info --changeEye method
    	System.out.println("---reset called-------");
    	ArrayList<BooleanProperty> properties= new ArrayList<BooleanProperty>(List.of(isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isPeripheryCompleted,isVesselsCompleted));
    	
    	if(bool) {
    		System.out.println("---patient-------");
    		properties.addAll(List.of(isPatientInfoSet, isEyeChanged));
    		TextField [] pInfos= {pName, pAge};
    		for(TextField tf : pInfos) {
        		tf.setText("");
        	}
    	}
    	
    	//reset booleans 
    	for(BooleanProperty b: properties) { 
    		b.set(false);
    	}
    	
    	//clear tabs 
    	clearAllTabs(); //efface tous les choix et active les btns
    	setPermittersFalse(); //rend false les allowxxx
    	initializeToggle();  //remets les valeurs par defaut
    	
    	if(bool) {
    		System.out.println("---tabs change------");
    		globTabs.getSelectionModel().select(0); //on montre info
    		pName.requestFocus();
    	}else {
    		globTabs.getSelectionModel().select(1); //onh
    	}
    	
    }
    
    public void initializeToggle() {
    	// ------------------------ONH-----------------------------------   
    	discSize.selectToggle(this.sNormal);
    	margins.selectToggle(this.mSharp);
    	rimColor.selectToggle(this.rPink);
    	
    	vRatio.setText("0.4");
    	hRatio.setText("0.3");
    	
    	//-------------------------Macula -------------------------------
    	freflex.selectToggle(refPresent);
    	rprofile.selectToggle(pFlat);
    	ireflex.selectToggle(iCellophane);
    	pvd.selectToggle(this.pvdAbsent);
    	
    	//----------------------retina----------------------------
    	transparency.selectToggle(Tnormal);
    	bcolor.selectToggle(cNormal);
    	
    	//**------------------------------vessels------------------
    	arterialR.selectToggle(nLight);
    	avRatio.setText("2/3");
    	
    	//***-------------------------periphery-------------------
    	serrata.selectToggle(oraY);
    	vitreousb.selectToggle(vbaseY);
    }
    
    public void setPermittersFalse() {
    	allowFinish=false; allowOneEye=false;allowEyeIncomplet=false; allowQuit=false;
    }
    
    public void registerPart(String[] toReplace, String[] replaceWith, boolean patientReg) {
    	closeWord();
    	
    	 try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
        Document fundus = new Document();
        if(patientReg) {
        	InputStream fundusTemplate = ExMain.class.getResourceAsStream("sources/fundus.docx");
            fundus.loadFromStream(fundusTemplate, FileFormat.Docx_2013);
        }else {
        	fundus.loadFromFile(filename);
        }
        

        for(int j = 0; j < toReplace.length; ++j) {
           fundus.replace(toReplace[j], replaceWith[j], true, true);
        }

        fundus.saveToFile(filename);
    }
    
    
    
    public static void closeWord() {
	    try {
	        
	        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", "WINWORD.EXE");
	        pb.start();
	        
	    } catch (Exception ex) {
	        System.err.println("Erreur lors de la fermeture d'Excel : " + ex.getMessage());
	    }
	}
    

}
