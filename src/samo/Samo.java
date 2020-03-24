package samo;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.medusa.Gauge.NeedleShape;
import eu.hansolo.medusa.Gauge.NeedleSize;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.LcdFont;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.TickMarkType;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;;
import javafx.scene.input.KeyEvent;


public class Samo extends Application {
    double v=0;
    private double f=1;
    private double g=0, obr=0;
    private boolean e=false,arrow=false;
    @Override
    public void start(Stage stage) throws Exception {

       
       GridPane root = new GridPane();

       //root.gridLinesVisibleProperty(true);
         root.setHgap(7);
        root.setVgap(50); 
        root.setStyle("-fx-background-color: #282625;");
        Gauge gauge = new Gauge();
        Button btn = new Button();
        final Label label = new Label();
        btn.setText("Start the engine");
        Button b1 = new Button();
             
       ////////////////////////////////////PALIWO////////////////////////////////////////////////////////////
        
         
         Gauge gauge2 = GaugeBuilder.create()
                          .skinType(Gauge.SkinType.VERTICAL)
                          .prefSize(300,300)
                          .foregroundBaseColor(Color.rgb(249, 249, 249))
                          .knobColor(Color.BLACK)
                          .maxValue(1.0)
                          .valueVisible(false)
                          .sectionsVisible(true)
                          .sections(new Section(0.0, 0.2, Color.rgb(255, 10, 1)))
                          .minorTickMarksVisible(false)
                          .mediumTickMarksVisible(false)
                          .majorTickMarkType(TickMarkType.BOX)
                          .title("FUEL")
                          .needleSize(NeedleSize.THICK)
                          .needleShape(NeedleShape.ROUND)
                          .needleColor(Color.rgb(255, 61, 10))
                          .shadowsEnabled(true)
                          .angleRange(90)
                          .customTickLabelsEnabled(true)
                          .customTickLabels("E", "", "", "", "", "1/2", "", "", "", "", "F")
                          .build();
        gauge2.setAnimated(true);
        //////////////////////////////////////////////////////////////////////////////OBROTY//////////////////////////////////////////////////////////   
         Gauge obroty = GaugeBuilder.create()
                          .skinType(Gauge.SkinType.VERTICAL)
                          .prefSize(300, 300)
                          .foregroundBaseColor(Color.rgb(249, 249, 249))
                          .knobColor(Color.BLACK)
                          .minValue(0) // Set the start value of the scale  
                          .maxValue(5) // Set the end value of the scale  
                          .valueVisible(false)
                          .sectionsVisible(true)
                          .title("x1000 RPM")
                          .tickLabelDecimals(1)
                          .customTickLabelsEnabled(true)
                          .customTickLabels("0", "", "1", "", "2", "", "3", "", "4", "", "5")
                          .minorTickMarksVisible(false)
                          .mediumTickMarksVisible(false)
                          .majorTickMarkType(TickMarkType.BOX)
                          .build();
        obroty.setAnimated(true);
        
       //////////////////////////////////////////////////////////////PREDKOSC///////////////////////////////////////////////////////// 
        gauge.setSkin(new ModernSkin(gauge));
        gauge.setMinSize(300, 300);
        gauge.setMaxSize(300, 300);
        gauge.setUnit("Km / h");
        gauge.setUnitColor(Color.GREEN);
        gauge.setDecimals(0);
        gauge.setValueColor(Color.WHITE);
        gauge.setTitleColor(Color.WHITE); 
        gauge.setSubTitleColor(Color.WHITE); 
        gauge.setBarColor(Color.rgb(0, 214, 215)); 
        gauge.setNeedleColor(Color.RED); 
        gauge.setMajorTickMarkLengthFactor(60);
        gauge.setForegroundBaseColor(Color.WHITE);
        gauge.setMaxValue(220);
        gauge.setThreshold(50);
        gauge.setThresholdColor(Color.rgb(0, 214, 215));
        gauge.setThresholdVisible(true);
        gauge.setKnobColor(Color.RED);
        gauge.setKnobVisible(true);
        gauge.setAnimated(false);   
        
        
        ////////////////////////////////////////////////////////BIEGI/////////////////////////////////////////////////////////////////////
        Gauge biegi = GaugeBuilder.create()
                    .skinType(Gauge.SkinType.LCD)
                    .lcdDesign(LcdDesign.BLUE_BLUE)
                    .decimals(0)
                    .tickLabelDecimals(1)
                    .minMeasuredValueVisible(false)
                    .maxMeasuredValueVisible(false)
                    .oldValueVisible(false)
                    .lcdFont(LcdFont.LCD)
                    .build();
       
        label.setText("UP!");
        label.setFont(Font.font ("Verdana", 30));
     
        label.setVisible(false);
        label.setTextFill(Color.web("#0076a3"));
       
     
      //  System.out.println(javafx.scene.text.Font.getFamilies());
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        
        HBox deska=new HBox();
        deska.setMinWidth(600);  
        deska.setAlignment(Pos.CENTER);
        deska.setSpacing(40);
        deska.getChildren().addAll(obroty,gauge,gauge2); 
        
        HBox deska2 =new HBox(); 
        deska2.setSpacing(15);
       deska2.getChildren().addAll(biegi,label,btn);
        deska2.setLayoutX(200);
        deska2.setLayoutY(200);
        deska2.setAlignment(Pos.CENTER);
        root.add(deska,1,1);
        root.add(deska2,1,3);
       
    
         Scene scena = new Scene(root, 800, 600); 
         stage.setTitle("p02");
         stage.setScene(scena); 
         
         scena.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(e==true){
                switch (event.getCode()) {
                    case UP:   
                        if(g>0){
                        if((v<220))
                        v=v+1*(1-g);
                        if(obr<3.8){ 
                            obr=(obr+0.00069*v); 
                        }
                        if(obr>=2.3){ 
                            arrow=true;
                            label.setVisible(arrow);
                        }
                        gauge.setValue(Math.abs(v));
                        obroty.setValue(obr);
                        f=f-0.001;
                        gauge2.setValue(f);
                        if(f==0){
                            e=!e;
                    gauge.setAnimated(true);
                    btn.setText("Start the engine");
                    v=0;
                    gauge.setValue(v);
                    gauge2.setValue(0);
                    obroty.setValue(0);
                    biegi.setValue(0);
                    biegi.setValueVisible(false);
                    gauge.setAnimated(false);}
                        }
                        break;
                    case DOWN: 
                        if(v>0){
                        v--;    
                        if(obr>0.9){ 
                            obr=obr-0.03; 
                        }
                        if(obr<2.3){ 
                            arrow=false;
                            label.setVisible(arrow);
                        }
                        obroty.setValue(obr);
                        gauge.setValue(Math.abs(v));}
                        break;
                    case A:
                        if(g<0.6)
                        { g=g+0.1;
                            if(v>3){
                                 v=v-3;
                            }                           
                            gauge.setValue(v);
                            if(obr>1.6){
                                obr=obr-(1+g);
                            }
                            if(obr<2.3){ 
                            arrow=false;
                            label.setVisible(arrow);
                        }
                            obroty.setValue(obr);
                            biegi.setValue(Math.abs(g*10)); 
                            arrow=false;
                            label.setVisible(arrow);
                        }
                        break;
                    case Z:
                        if(g>0){
                           obr=obr-0.05;
                           obroty.setValue(obr);
                           if(obr>=2.3){ 
                            arrow=true;
                            label.setVisible(arrow);
                        }
                           g=g-0.1;
                           biegi.setValue(Math.abs(g*10));
                        }
                        break;
                }
            }
            }
        });
         
//         scena.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event1) {
//                if(e==true){
//                    switch (event1.getCode()){
//                        case UP:
//                        v--; 
//                    }
//
//                }
//            }
//         });
    

        btn.setOnAction(new EventHandler<ActionEvent>() {           
            @Override
            public void handle(ActionEvent event) {
                e=!e;
                if(e==true){
                btn.setText("Stop the engine");    
                gauge2.setValue(f);
                obr=1;
                obroty.setValue(obr);
                g=0.1;
                biegi.setValue(1);
                biegi.setValueVisible(true);}
                else{
                    gauge.setAnimated(true);
                    btn.setText("Start the engine");
                    v=0;
                    gauge.setValue(v);
                    gauge2.setValue(0);
                    obroty.setValue(0);
                    biegi.setValue(0);
                    biegi.setValueVisible(false);
                    gauge.setAnimated(false);
                }
            }
        });
       

         stage.show();     
     }    
     //--------------------------------------
     public static void main(String[] args) 
     {
         launch(args);
     }
    
 }
