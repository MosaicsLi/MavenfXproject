package com.jfx.MavenfXproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PrimaryController {
	
	private int PrimaryCountdown = 30;
    private Thread PrimaryThread;

    @FXML
    private Label motherLabel = new Label();
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
            Scene scene = new Scene(App.loadFXML("chuld"), 300, 300);
            childStage.setTitle("視窗");
            childStage.setScene(scene);
            childStage.show();
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
        
        childStage.show();
	}
}
