package com.jfx.MavenfXproject;

import java.io.IOException;

import com.jfx.MavenfXproject.Models.ChildModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;
import java.util.ArrayList;

public class PrimaryController {
	
	private int PrimaryCountdown = 30;
    private Thread PrimaryThread;
    private List<ChildModel> childModellist=new ArrayList<ChildModel>();

    @FXML
    private Label motherLabel = new Label();
    @FXML
    private TextField ChildCountdown = new TextField();
    @FXML
    public VBox  motherVBox ;
    @FXML
    public VBox  ChildreturnVBox ;
    
 	@FXML
    private void initialize() {
        // 在初始化時設定 UI 元素的初始狀態
        motherLabel.setText("母視窗倒數：" + PrimaryCountdown + " 秒");
        // 在初始化時自動啟動 startMotherCountdown 方法
        startMotherCountdown();
    }
 	@FXML
    private void startMotherCountdown() {
 		PrimaryThread = new Thread(() -> {
            while (PrimaryCountdown >= 0) {
                Platform.runLater(() -> {
                    motherLabel.setText("母視窗倒數：" + PrimaryCountdown + " 秒");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PrimaryCountdown--;
            }
            // 顯示母視窗倒數結束訊息
            Platform.runLater(() -> {
                motherLabel.setText("母視窗倒數結束！");
            });
        });
 		PrimaryThread.start();
    }
 	@FXML
 	private void addMotherCountdown() {
 		PrimaryCountdown++;
 		Platform.runLater(() -> {
            motherLabel.setText("母視窗倒數：" + PrimaryCountdown + " 秒");
        });
 	}
    // 當按鈕被點擊時調用
    @FXML
    private void openChildWindow() {
        Stage childStage = new Stage();
        try {
        	ChildModel childModel=new ChildModel();
     		childModel.setChildID(childModellist.size()+1);
     		if(!ChildCountdown.getText().trim().isEmpty()) 
     		{
     			childModel.setChildCountdown(Integer.parseInt(ChildCountdown.getText()));
     		}
     		else {
     			childModel.setChildCountdown(15);
     		}
     		childModel.setChildPaused(false);
     		childModellist.add(childModel);
     		
     		FXMLLoader loader = new FXMLLoader(getClass().getResource("Child.fxml"));
            Parent root = loader.load();
            ChildController childController = loader.getController();
     		childController.setParentController(this,childModel);
     		childStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    // 關閉子視窗時通知母視窗
                    Platform.runLater(() -> {
                    	 childController.notifyParentWindow();
                    });
                }
            });
     		
            Scene scene = new Scene(root, 300, 300);
            childStage.setTitle("子視窗"+childModel.getChildID());
            childStage.setScene(scene);
            childStage.show();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
        
        childStage.show();
	}
}
