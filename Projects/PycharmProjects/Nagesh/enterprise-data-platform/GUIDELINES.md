# Coding guidelines and standards for data pipelines

* **When a new table to be loaded always create a new class and write all the transformations in that class.**
* **When a set of tables are to be loaded with same  pattern  try to create an abstract class like _app/data_warehouse/pipeline.py_ and extend it in classes where each table load logic is  to be implemented.**
* **Every pipeline/table trigger should be present in _app/`layer`/main.py_**
* **While naming a method/class/function/package/module make sure name  reflects an overview of what it  _does or categorizes_**
* **If a functionality is found generic across the package try to push it to utils file by  creating a method for that functionality in that package/layer**
* **If a functionality is found to be re-usable across the layers, try to make it generic across the project**
* **Log wherever possible**
* **Never hard code strings, try to make use of config file as much as possible**
* **Main file(enterprise-data-platform/main.py) should be the point of trigger for every pipeline**
* **Minimalise exception handling, handle all exceptions at one place as python gives traceback to the line where exception/error has occurred**

**Always follow [PEP-8 Coding standards](https://www.python.org/dev/peps/pep-0008/)**

