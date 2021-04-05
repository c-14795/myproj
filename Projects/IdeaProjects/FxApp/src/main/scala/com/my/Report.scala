package com.my


import org.controlsfx.control.table.TableFilter

import scala.reflect.ClassTag
import scalafx.scene.control.TreeItem
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}

object Report extends JFXApp {

  val data: ObservableBuffer[Result] = new ObservableBuffer[Result]

  case class Table(tableName: String, sourceTablesUsed: List[Table] = Nil)


  val tables = List("Table1", "Table2", "Table3", "Table 4")
  val targetTables: ObservableBuffer[Table] = ObservableBuffer[Table]()
  tables.foreach(a => targetTables.add(Table(a, createTable(tables))))

  def createTable(sourceTables: List[String]): List[Table] = {
    var list: List[Table] = List[Table]()
    sourceTables.foreach(a => list = Table(a) :: list)
    list
  }

  def toTreeItem(p: Table): TreeItem[Table] = {
    if (p.sourceTablesUsed.isEmpty) new TreeItem(p)
    else new TreeItem(p) {
      children = p.sourceTablesUsed map toTreeItem
    }
  }

  def createColumn[T: ClassTag](colName: String, colWidth: Double): TableColumn[T, String] = {
    new TableColumn[T, String] {
      text = colName
      prefWidth = colWidth

    }
  }


  val testDetails: TableView[Result] = new TableView[Result] {

    val col1: TableColumn[Result, String] = createColumn[Result]("Env1 TableName", 200)

    col1.cellValueFactory = {
      _.value.env1TableName
    }


    val col2: TableColumn[Result, String] = createColumn[Result]("Env1", 200)

    col2.cellValueFactory = {
      _.value.env1
    }


    val col3: TableColumn[Result, String] = createColumn[Result]("Env2 TableName", 200)

    col3.cellValueFactory = {
      _.value.env2TableName
    }


    val col4: TableColumn[Result, String] = createColumn[Result]("Env2 TableName", 200)

    col4.cellValueFactory = {
      _.value.env2
    }


    val col5: TableColumn[Result, String] = createColumn[Result]("TableType", 200)

    col5.cellValueFactory = {
      _.value.tableType
    }


    val col6: TableColumn[Result, String] = createColumn[Result]("Result (Env1 - Env2) ", 200)

    col6.cellValueFactory = {
      _.value.result
    }


    columns += (col1, col2, col3, col4, col5, col6)

  }

  val root1: TreeView[Table] = new TreeView[Table] {
    showRoot = false
    root = new TreeItem[Table] {
      expanded = true
      padding = Insets(20)

      children = ObservableBuffer(targetTables map toTreeItem)
    }


    cellFactory = _ =>
      new javafx.scene.control.TreeCell[Table] {

        val self: TreeCell[Table] = this

        override def updateItem(item: Table, empty: Boolean): Unit = {
          super.updateItem(item, empty)
          self.graphic = null
          if (empty) {
            self.text = null
          }
          else {

            if (!this.getTreeItem.isLeaf) {

              val box: HBox = new HBox(20)
              val button: Button = new Button("Run test")

              button.onAction = (_: ActionEvent) => {
                calMe(item.tableName)
              }


              val label: Label = new Label(item.tableName)
              button.padding = Insets(2)
              label.prefHeight.bind(button.prefHeight)
              box.children.addAll(label, button)
              self.graphic = box

            }
            else {

              self.text = item.tableName
            }


          }

        }
      }

  }

  val tableDetails: BorderPane = new BorderPane {
    testDetails.setItems(data)
    val filter = new TableFilter[Result](testDetails)

    center = testDetails


  }

  val menuBar: MenuBar = new MenuBar() {
    menus = List(new Menu("File") {

      items = List(new MenuItem("Results To Csv") {
        onAction = (_: ActionEvent) => {
          exportData()
        }
      },
        new MenuItem("Exit") {
          onAction = (_: ActionEvent) => {
            System.exit(1)
          }
        }
      )
    })
  }


  stage = new PrimaryStage {
    title = "TablesList"
    scene = new Scene() {
      stylesheets = List("resources.css")
      resizable = true
      root = new VBox {
        children.add(menuBar)
        children.add(root1)
        children.add(tableDetails)
      }

    }

  }

  def calMe(tgtTable: String): Unit = {
    data.add(new Result(tgtTable, tgtTable, "STG", "TGT", "TargetTable", "true"))
    println("I have added")

  }

  def exportData() = {
    print("Hi I am called.")
  }


}