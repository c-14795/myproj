
package com.my;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class FXTableHeightCalculator {

    private final TableView tv;
    private final int threashold;
    private double YPos = 0;

    private FXTableHeightCalculator(TableView table) {
        threashold = table.getItems().size();
        tv = table;
    }

    private double calcHeight() {
        walkTableViewSceneGraph(tv, -1, -1);
        return YPos;
    }

    private void walkTableViewSceneGraph(Node node, int row, int column) {
        int newRow = (node instanceof TableRow) ? ((TableRow) node).getIndex() : row;
        int newColumn = (node instanceof TableCell) ? ((TableCell) node).getIndex() : column;

        if (newRow == threashold) {
            YPos = node.getBoundsInParent().getMaxY();
            return;
        }

        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            parent.getChildrenUnmodifiable().forEach(n -> walkTableViewSceneGraph(n, newRow, newColumn));
        }
    }

    public static double getHeight(TableView table) {
        return new FXTableHeightCalculator(table).calcHeight();
    }

}