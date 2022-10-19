package org.example;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import org.example.config.AppConfig;
import org.example.models.FileInfo;

public class DownloadManager {

    @FXML
    private TableView<FileInfo> TableView;

    private String filename;
    int index=0;
    @FXML
    private TextField urlTextField;
    @FXML
    void downloadButtonClicked(ActionEvent event) {

       String Url=this.urlTextField.getText();

       F_name(Url);
       String status="STARTING";
       String action="OPEN";
       String path= AppConfig.Download_Path+ File.separator+filename;

        FileInfo file=new FileInfo((index+1)+"",filename,Url,status,action,path);

        this.index=this.index+1;

        DownloadThread thread=new DownloadThread(file,this);

        this.TableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);
        this.urlTextField.setText("");


        thread.start();




    }
    //GETING FILE NAME FROM URL
    void F_name(String Url){
        try{
         filename = Paths.get(new URI(Url).getPath()).getFileName().toString();
        }
        catch(URISyntaxException e){
           e.printStackTrace();
        }
    }

    public void updateUI(FileInfo metaFile) {
        System.out.println(metaFile);
      FileInfo fileInfo= this.TableView.getItems().get(Integer.parseInt(metaFile.getIndex())-1);
      fileInfo.setStatus(metaFile.getStatus());
      this.TableView.refresh();
        System.out.println("____________________________________");

    }
    //MAPING ATTRIBUTS OF FILE TO TABLE
    @FXML
    public void initialize(){
        System.out.println("initialized");
        TableColumn<FileInfo,String > sn = (TableColumn<FileInfo, String>) this.TableView.getColumns().get(0);
        sn.setCellValueFactory(p -> {
            return p.getValue().indexProperty();
        });
        TableColumn<FileInfo,String > filename = (TableColumn<FileInfo, String>) this.TableView.getColumns().get(1);
        filename.setCellValueFactory(p -> {
            return p.getValue().nameProperty();
        });
        TableColumn<FileInfo,String > url = (TableColumn<FileInfo, String>) this.TableView.getColumns().get(2);
        url.setCellValueFactory(p -> {
            return p.getValue().urlProperty();
        });
        TableColumn<FileInfo,String > status = (TableColumn<FileInfo, String>) this.TableView.getColumns().get(3);
        status.setCellValueFactory(p -> {
            return p.getValue().statusProperty();
        });
        TableColumn<FileInfo,String > action = (TableColumn<FileInfo, String>) this.TableView.getColumns().get(4);
        action.setCellValueFactory(p -> {
            return p.getValue().actionProperty();
        });



    }
}
