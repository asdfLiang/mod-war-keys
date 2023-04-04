package com.example.front.controller;

import com.example.back.api.HotKeyService;
import com.example.back.api.TranslationService;
import com.example.back.model.CmdHotKeyVO;
import com.example.commons.utils.StringUtil;

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

    @FXML private TableColumn<CmdHotKeyVO, String> cmd = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> cmdName = new TableColumn<>();
    @FXML private TableColumn<CmdHotKeyVO, String> hotKey = new TableColumn<>();

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
        newDaemonThread(translationService::perfectTranslation).start();
        if (StringUtil.isNotBlank(configPathInput.getText())) {
            onReadConfigBtnClick();
        }
    }

    /** 绑定每列的数据 */
    private void bindColumnData() {
        cmd.setCellValueFactory(new PropertyValueFactory<>("cmd"));
        cmdName.setCellValueFactory(new PropertyValueFactory<>("cmdName"));
        hotKey.setCellValueFactory(new PropertyValueFactory<>("hotKey"));
    }

    private Thread newDaemonThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
