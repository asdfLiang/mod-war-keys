package com.example.front.controller;

import com.example.back.api.HotKeyService;
import com.example.back.api.TranslationService;
import com.example.back.model.CmdHotKeyVO;

import de.felixroske.jfxsupport.FXMLController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 2023/3/23 0:43
 * @author by liangzj
 */
@FXMLController
public class ModKeyController implements Initializable {

    private final HotKeyService hotKeyService;

    private final TranslationService translationService;

    /** 配置路径输入框 */
    @FXML private TextField configPathInput;

    @FXML private TableView<CmdHotKeyVO> tableView = new TableView<>();

    @FXML private TableColumn cmd = new TableColumn();
    @FXML private TableColumn cmdName = new TableColumn();
    @FXML private TableColumn hotKey = new TableColumn();

    public ModKeyController(HotKeyService hotKeyService, TranslationService translationService) {
        this.hotKeyService = hotKeyService;
        this.translationService = translationService;
    }

    /** 点击读取配置 */
    @FXML
    protected void onReadConfigBtnClick() {
        List<CmdHotKeyVO> hotKeys = hotKeyService.load(configPathInput.getText());

        // 初始化翻译文本
        translationService.tryInitTranslationText(hotKeys);

        tableView.setItems(FXCollections.observableList(hotKeys));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindColumnData();
    }

    /** 绑定每列的数据 */
    private void bindColumnData() {
        cmd.setCellValueFactory(new PropertyValueFactory<CmdHotKeyVO, String>("cmd"));
        cmdName.setCellValueFactory(new PropertyValueFactory<CmdHotKeyVO, String>("cmdName"));
        hotKey.setCellValueFactory(new PropertyValueFactory<CmdHotKeyVO, String>("hotKey"));
    }
}
