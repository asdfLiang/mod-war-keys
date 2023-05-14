package com.example.front.controller.cells;

import com.example.front.controller.action.AlertAction;
import com.example.front.controller.action.SystemAction;
import com.example.front.controller.vo.CmdHotKeyVO;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

/**
 * @since 2023/5/14 9:45
 * @author by liangzj
 */
public class ClickCopyTextFiledTableCell<S, T> extends TableCell<S, T> {
    public ClickCopyTextFiledTableCell(TableColumn<CmdHotKeyVO, String> column) {
        this.setOnMouseClicked(
                event -> {
                    if (this.isEmpty() || !(event.getTarget() instanceof Text)) {
                        return;
                    }

                    String cellData =
                            String.valueOf(
                                    column.getCellObservableValue(this.getIndex()).getValue());
                    SystemAction.putClipboard(cellData);

                    AlertAction.info("\"" + cellData + "\"" + "已复制到粘贴板");
                });
    }

    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString());
        setGraphic(null);
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
