package com.example.front.controller;

import static com.example.commons.utils.ThreadUtil.newDaemonThread;

import com.example.back.api.HotKeyService;
import com.example.back.api.TranslationService;
import com.example.back.model.CmdHotKeyVO;
import com.example.commons.utils.StringUtil;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.FXMLController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @since 2023/3/23 0:43
 * @author by liangzj
 */
@FXMLController
public class ModKeyController implements Initializable {
    @Autowired private HotKeyService hotKeyService;
    @Autowired private TranslationService translationService;

    private final Stage stage = AbstractJavaFxApplicationSupport.getStage();
    @FXML private TextField configPathInput;
    @FXML private TableView<CmdHotKeyVO> tableView = new TableView<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmd = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmdName = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> hotKey = new TableColumn<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindColumnData();
    }

    @FXML
    protected void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开");
        File file = fileChooser.showOpenDialog(stage);
        if (Objects.isNull(file) || StringUtil.isBlank(file.getAbsolutePath())) {
            return;
        }

        configPathInput.setText(file.getAbsolutePath());
        initColumnData();
    }

    @FXML
    protected void quit() {
        stage.close();
    }

    protected void initColumnData() {
        String filePath = configPathInput.getText();
        List<CmdHotKeyVO> hotKeys = hotKeyService.load(filePath);

        // 初始化翻译文本
        newDaemonThread(() -> translationService.perfectTranslation(hotKeys)).start();
        tableView.setItems(FXCollections.observableList(hotKeys));
    }

    /** 绑定每列的数据 */
    private void bindColumnData() {
        cmd.setCellValueFactory(new PropertyValueFactory<>("cmd"));
        cmdName.setCellValueFactory(new PropertyValueFactory<>("translation"));
        hotKey.setCellValueFactory(new PropertyValueFactory<>("hotKey"));
    }
}
