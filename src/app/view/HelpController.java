package app.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import app.ExMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class HelpController {

	@FXML
	private Label sisLabel, dataLabel, creanceLabel;
	
	@FXML
	private ScrollPane sisPane, dataPane, creancepane;
	
	public void initialize() {
		sisPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sisLabel.prefWidthProperty().bind(sisPane.widthProperty().subtract(20));
        try {
        	InputStream sis =ExMain.class.getResourceAsStream("sources/sisadmin.txt");
        	if(sis!=null) {
        		BufferedReader bf=new BufferedReader(new InputStreamReader(sis, StandardCharsets.UTF_8));
        		String contenu = bf.lines().collect(Collectors.joining("\n"));
        		sisLabel.setText(contenu);
        		bf.close();
        	}else {
        		sisLabel.setText("Got nothing");
        	}
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
