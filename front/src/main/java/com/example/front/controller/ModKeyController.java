package com.example.front.controller;

import com.example.back.model.CmdHotKeyDTO;
import com.example.back.service.HotKeyService;
import com.example.commons.utils.StringUtil;
import com.example.front.vo.CmdHotKeyVO;

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
import org.springframework.util.CollectionUtils;

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

    private final Stage stage = AbstractJavaFxApplicationSupport.getStage();
    @FXML private TextField configPathInput;
    @FXML private TableView<CmdHotKeyVO> tableView = new TableView<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmdTypeColumn = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmdColumn = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmdNameColumn = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> hotKeyColumn = new TableColumn<>();

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
        if (StringUtil.isBlank(filePath)) return;

        // 读取指令
        List<CmdHotKeyDTO> hotKeys = hotKeyService.load(filePath);
        if (CollectionUtils.isEmpty(hotKeys)) return;
        List<CmdHotKeyVO> hotKeyVOS = hotKeys.stream().map(this::buildVO).toList();

        // 展示指令
        tableView.setItems(FXCollections.observableList(hotKeyVOS));
    }

    /** 绑定每列的数据 */
    protected void bindColumnData() {
        cmdTypeColumn.setCellValueFactory(new PropertyValueFactory<>("cmdTypeDesc"));
        cmdColumn.setCellValueFactory(new PropertyValueFactory<>("cmd"));
        cmdNameColumn.setCellValueFactory(new PropertyValueFactory<>("cmdTranslation"));
        hotKeyColumn.setCellValueFactory(new PropertyValueFactory<>("hotKey"));
    }

    private CmdHotKeyVO buildVO(CmdHotKeyDTO dto) {
        return new CmdHotKeyVO(
                dto.getCmd(), dto.getCmdTypeDesc(), dto.getTranslation(), dto.getHotKey());
    }
}
