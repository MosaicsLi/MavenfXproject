package com.jfx.MavenfXproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class ChuldController {
    private int childCountdown = 15;  // 子視窗倒數15秒
    private boolean childPaused = false; // 子視窗是否暫停
 	@FXML
    private Label childLabel = new Label();


 	@FXML
 	private void initialize() {
    // 在初始化時設定 UI 元素的初始狀態
 		childLabel.setText("子視窗倒數：" + childCountdown + " 秒");
 		
 		Thread childThread = new Thread(() -> {
            while (childCountdown > 0) {
                Platform.runLater(() -> {
                    childLabel.setText("子視窗倒數：" + childCountdown + " 秒");
                });
                
                try {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                childCountdown--;

                if (childCountdown == 5) {
                    // 子視窗倒數結束，暫停5秒
                    childPaused = true;
                    // 子視窗暫停時顯示訊息
                    Platform.runLater(() -> {
                        childLabel.setText("子視窗暫停中...");
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 恢復倒數
                    childPaused = false;
                }

            }
            // 顯示子視窗倒數結束訊息
            Platform.runLater(() -> {
                childLabel.setText("子視窗倒數結束！");
            });
        });

        childThread.start();
 	}
}