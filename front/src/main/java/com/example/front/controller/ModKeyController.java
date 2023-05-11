package com.example.front.controller;

import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.back.service.HotKeyService;
import com.example.back.service.RecordService;
import com.example.back.service.TranslationService;
import com.example.back.support.exceptions.HotKeyConflictException;
import com.example.commons.utils.StringUtil;
import com.example.front.vo.CmdHotKeyVO;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.FXMLController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @since 2023/3/23 0:43
 * @author by liangzj
 */
@FXMLController
public class ModKeyController implements Initializable {
    @Autowired private HotKeyService hotKeyService;
    @Autowired private RecordService recordService;
    @Autowired private TranslationService translationService;

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

        // 打开界面时读取最近加载的配置文件
        configPathInput.setText(recordService.latestPathname());

        refreshColumnData();
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

        refreshColumnData();
    }

    @FXML
    protected void quit() {
        stage.close();
    }

    protected void refreshColumnData() {
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
        tableView.setEditable(true);
        cmdTypeColumn.setCellValueFactory(new PropertyValueFactory<>("cmdTypeDesc"));
        cmdColumn.setCellValueFactory(new PropertyValueFactory<>("cmd"));

        // 编辑翻译
        cmdNameColumn.setCellValueFactory(new PropertyValueFactory<>("cmdTranslation"));
        cmdNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cmdNameColumn.setOnEditCommit(
                evt -> translationService.manual(evt.getRowValue().getCmd(), evt.getNewValue()));

        // 编辑快捷键
        hotKeyColumn.setCellValueFactory(new PropertyValueFactory<>("hotKey"));
        hotKeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hotKeyColumn.setOnEditCommit(this::confirmUpdate);
    }

    /** 确认更新快捷键 */
    private void confirmUpdate(TableColumn.CellEditEvent<CmdHotKeyVO, String> event) {
        try {
            hotKeyService.update(event.getRowValue().getCmd(), event.getNewValue(), false);
        } catch (HotKeyConflictException e) {
            // 检测到冲突，进行二次确认
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(e.getMessage() + ", \n是否确认修改？");
            Optional<ButtonType> buttonType = alert.showAndWait();
            ButtonType confirm = buttonType.orElse(ButtonType.CANCEL);

            if (ButtonType.OK == confirm || ButtonType.YES == confirm) {
                hotKeyService.update(event.getRowValue().getCmd(), event.getNewValue(), true);
            }
        }

        // 刷新列表
        refreshColumnData();
    }

    private CmdHotKeyVO buildVO(CmdHotKeyDTO dto) {
        return new CmdHotKeyVO(
                dto.getCmd(), dto.getCmdTypeDesc(), dto.getTranslation(), dto.getHotKey());
    }
}
