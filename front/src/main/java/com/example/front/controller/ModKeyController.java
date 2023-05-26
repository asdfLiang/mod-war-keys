package com.example.front.controller;

import com.example.common.exceptions.BaseException;
import com.example.front.controller.action.AlertAction;
import com.example.front.controller.cells.ClickCopyTextFiledTableCell;
import com.example.front.controller.vo.CmdHotKeyVO;
import com.example.service.HotKeyService;
import com.example.service.RecordService;
import com.example.service.TranslationService;
import com.example.service.manager.dto.CmdHotKeyDTO;
import com.example.service.support.exceptions.HotKeyConflictException;

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

import org.apache.commons.lang3.StringUtils;
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
    private final Stage stage = AbstractJavaFxApplicationSupport.getStage();
    @Autowired private HotKeyService hotKeyService;
    @Autowired private RecordService recordService;
    @Autowired private TranslationService translationService;
    @FXML private TextField configPathInput;
    @FXML private TableView<CmdHotKeyVO> tableView = new TableView<>();
    @FXML private TableColumn<CmdHotKeyVO, String> raceColumn = new TableColumn<>();
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
        if (Objects.isNull(file) || StringUtils.isBlank(file.getAbsolutePath())) {
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
        if (StringUtils.isBlank(filePath)) return;

        // 读取指令
        List<CmdHotKeyDTO> hotKeys = hotKeyService.load(filePath);
        if (CollectionUtils.isEmpty(hotKeys)) return;

        List<CmdHotKeyVO> hotKeyVOS = hotKeys.stream().map(this::buildVO).toList();

        // 展示指令
        tableView.setItems(FXCollections.observableList(hotKeyVOS));
        tableView.refresh();
    }

    /** 绑定每列的数据 */
    protected void bindColumnData() {
        tableView.setEditable(true);

        raceColumn.setCellValueFactory(new PropertyValueFactory<>("raceDesc"));
        cmdTypeColumn.setCellValueFactory(new PropertyValueFactory<>("unitTypeDesc"));

        // 指令，点击复制
        cmdColumn.setCellValueFactory(new PropertyValueFactory<>("cmd"));
        cmdColumn.setCellFactory(column -> new ClickCopyTextFiledTableCell<>(cmdColumn));

        // 指令翻译，可编辑
        cmdNameColumn.setCellValueFactory(new PropertyValueFactory<>("cmdTranslation"));
        cmdNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cmdNameColumn.setOnEditCommit(this::manualTranslation);

        // 快捷键，可编辑
        hotKeyColumn.setCellValueFactory(new PropertyValueFactory<>("hotKey"));
        hotKeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hotKeyColumn.setOnEditCommit(event -> reconfirmUpdate(event, false));
    }

    /** 手动翻译 */
    private void manualTranslation(TableColumn.CellEditEvent<CmdHotKeyVO, String> event) {
        translationService.manual(event.getRowValue().getCmd(), event.getNewValue());

        // 刷新列表
        refreshColumnData();
    }

    /**
     * 二次确认更新
     *
     * @param event 更新事件
     * @param confirmed 是否确认更新
     */
    private void reconfirmUpdate(
            TableColumn.CellEditEvent<CmdHotKeyVO, String> event, boolean confirmed) {
        try {
            hotKeyService.update(event.getRowValue().getCmd(), event.getNewValue(), confirmed);
        } catch (HotKeyConflictException e) {
            // 检测到冲突，进行二次确认
            ButtonType confirm = AlertAction.confirm(e.getMessage() + ", \n是否确认修改？");

            if (ButtonType.OK == confirm || ButtonType.YES == confirm) {
                reconfirmUpdate(event, true);
                return;
            }
        } catch (BaseException e) {
            AlertAction.error(e.getMessage());
        }

        // 刷新列表
        refreshColumnData();
    }

    private CmdHotKeyVO buildVO(CmdHotKeyDTO dto) {
        return new CmdHotKeyVO(
                dto.getCmd(),
                dto.getCmdType().getRace().getDesc(),
                dto.getCmdType().getUnitType().getDesc(),
                dto.getTranslation(),
                dto.getHotKey());
    }
}
