package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.srch.tools.CmdExecutor;
import com.srch.tools.main.ProcessKillVO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SampleController implements Initializable {

	@FXML
	private TableView<ProcessKillVO> tableView;

	CmdExecutor cmdExecutor = new CmdExecutor();

	@FXML
	private TableColumn<ProcessKillVO, String> protocolColumn;
	@FXML
	private TableColumn<ProcessKillVO, String> localAddressColumn;
	@FXML
	private TableColumn<ProcessKillVO, String> portColumn;
	@FXML
	private TableColumn<ProcessKillVO, String> stateColumn;
	@FXML
	private TableColumn<ProcessKillVO, String> pIdColumn;
	@FXML
	private TableColumn<ProcessKillVO, String> actionColumn;
	ObservableList<ProcessKillVO> objProcList;
	List<String> pIdList = new ArrayList<String>();
	ObservableList<ProcessKillVO> nProcList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		protocolColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("protocl"));
		localAddressColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("localAddress"));
		portColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("port"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("state"));
		pIdColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("pId"));
		actionColumn.setCellValueFactory(new PropertyValueFactory<ProcessKillVO, String>("checkBox"));
		tableView.setItems(getList());
	}

	@FXML
	void killProcess(ActionEvent event) {

		for (ProcessKillVO bean : objProcList) {
			if (bean.getCheckBox().isSelected()) {
				pIdList.add(bean.getPId());
				nProcList.add(bean);
			}
		}
		objProcList.removeAll(nProcList);
		if (!pIdList.isEmpty()) {
			Boolean flag = Boolean.FALSE;
			for (String val : pIdList) {
				String output = cmdExecutor.killProcess(val);
				if (output != null) {
					flag = Boolean.TRUE;
				} else {
					flag = Boolean.FALSE;
				}
			}
			if (flag) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Alert");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				try {
					stage.getIcons().add(new Image(getClass().getResource("/JavaFXv3.png").toURI().toString()));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				alert.setContentText("Port Sucessfully Killed");
				alert.showAndWait();
			}
		} else if (pIdList.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			try {
				stage.getIcons().add(new Image(getClass().getResource("/JavaFXv3.png").toURI().toString()));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alert.setContentText("Please Select a process");
			alert.showAndWait();
		}
		refreshTable();
	}

	public ObservableList<ProcessKillVO> getList() {
		List<ProcessKillVO> procList = null;
		try {
			procList = cmdExecutor.identifyPort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objProcList = FXCollections.observableList(procList);
		return objProcList;
	}

	public void refreshTable() {
		objProcList.clear();
		tableView.setItems(getList());
		System.out.println("Refreshed");
	}

}
