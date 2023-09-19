package com.jfx.MavenfXproject;

import com.jfx.MavenfXproject.Models.ChildModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ChildController {
    private PrimaryController parentController;
    private ChildModel childModel;
    private Thread childThread;
    private boolean isRunning = true; 

    private Label returnmothertext;
    private Label returnchildtext;
 	@FXML
    private Label childLabel = new Label();
 	
 	public void setParentController(PrimaryController parentController, ChildModel childModel) {
        this.parentController = parentController;
        this.childModel = childModel; // 儲存傳入的物件

        // 在初始化時設定 UI 元素的初始狀態
     		childLabel.setText("子視窗"+childModel.getChildID()+"倒數：" + childModel.getChildCountdown() + " 秒");
     		if(!parentController.motherVBox.getChildren().contains(returnmothertext))
     		{
     			returnmothertext = new Label(childLabel.getText());
         		parentController.motherVBox.getChildren().add(returnmothertext);
     		}
     		childThread = new Thread(() -> {
                while (childModel.getChildCountdown() > 0 && isRunning) {
                	if (childThread.currentThread().isInterrupted()) {
                        isRunning = false; // 設置標誌以退出Thread
                        break;
                    }
                    Platform.runLater(() -> {
                        childLabel.setText("子視窗"+childModel.getChildID()+"倒數：" + childModel.getChildCountdown() + " 秒");
                        returnmothertext.setText(childLabel.getText());
                    });
                    
                    try {
                    	childThread.sleep(1000);
                    } 
                    catch (InterruptedException e) {
                        isRunning = false; // 設置標誌以退出Thread
                        return;
                        //e.printStackTrace();
                    }
                    
                    childModel.setChildCountdown(childModel.getChildCountdown() - 1);

                    if (childModel.getChildCountdown() == 5) {
                        // 子視窗倒數結束，暫停5秒
                    	childModel.setChildPaused(true);
                        // 子視窗暫停時顯示訊息
                        Platform.runLater(() -> {
                            childLabel.setText("子視窗"+childModel.getChildID()+"暫停中...");
                            returnmothertext.setText(childLabel.getText());
                        });
                        try {
                        	childThread.sleep(5000);
                        } catch (InterruptedException e) {
                            isRunning = false; // 設置標誌以退出Thread
                            return;
                            //e.printStackTrace();
                        }
                        // 恢復倒數
                    	childModel.setChildPaused(false);
                    }

                }
                // 顯示子視窗倒數結束訊息
                Platform.runLater(() -> {
                    childLabel.setText("子視窗"+childModel.getChildID()+"倒數結束！");
                    returnmothertext.setText(childLabel.getText());
                });
            });

            childThread.start();
    }

 	@FXML
 	private void returnchildtext() {
 		if(parentController.ChildreturnVBox.getChildren().contains(returnchildtext))
 		{
 			Platform.runLater(() -> {
 				returnchildtext.setText(childLabel.getText());
            });
 			return;
 		}
 		returnchildtext = new Label(childLabel.getText());
 		parentController.ChildreturnVBox.getChildren().add(returnchildtext);
		return;
 	}
 	
    public void notifyParentWindow() {
    	if(childThread==null)return;
    	if(childThread.isAlive()) 
    	{
    		childThread.interrupt();
    	}
    	
    	if(!parentController.motherVBox.getChildren().contains(returnmothertext))
		{
			returnmothertext = new Label("子視窗"+childModel.getChildID()+"已關閉");
			parentController.motherVBox.getChildren().add(returnmothertext);
		}
    	 Platform.runLater(() -> {
             returnmothertext.setText("子視窗"+childModel.getChildID()+"已關閉");
         });
    }
}