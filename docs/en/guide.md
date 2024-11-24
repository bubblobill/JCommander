# Two-Panel File Manager Application - User Manual

## Basics and Terminology

When the program is opened, the main window immediately appears on the primary display.

The main window is divided into five sections:

1. Ribbon menu  
2. Navigation bar  
3. Left work panel  
4. Toolbar  
5. Right work panel  

![img.png](../res/frame.png)

At any given moment during operation, exactly one work panel is active, and one is passive.  
Initially, the left work panel is active, and the right panel is passive.

## Features

The program provides the following features:

* File system browsing
* Navigation within the working directory
* Parent directory navigation
* Jump to a specific path
* Select items
* Create a folder
* Delete items
* Copy items
* Move items

### File System Browsing

Each work panel allows independent browsing of the file system structure.  
The program provides two views for browsing:

* Tree view  
* Folder view  

By default, both views are displayed side by side in the panel.  
Interacting with one view updates the other, providing a consistent view of the current location.

In the tree view, all folders and files along the path from the system root to the current location are listed.  
Double-clicking an item in the tree view allows renaming a folder or file, provided the operating system permits this action.

The folder view displays only the current location (working directory).  
Double-clicking a folder switches the view to that folder.

### Navigation Within the Working Directory

You can switch back to a previous working directory within the panel and also redo these switches.

### Parent Directory Navigation

From any working directory (except the system root), you can switch to its parent directory.

### Jump to a Specific Path

By entering the desired path in the panel's address bar, the working directory switches to that path if valid.  
Both the tree view and folder view update automatically.

### Select Items

Items (folders and files) in the active working directory can only be selected through the folder view.

### Create a Folder

Creates a new folder named "New Directory" in the working directory of the active panel.

### Delete Items

Deletes the selected items from the working directory of the active panel.  

Before the operation is executed, a warning dialog appears, allowing the user to confirm or cancel the deletion.

### Copy Items

Copies the selected items from the working directory of the active panel to the working directory of the passive panel.

### Move Items

Moves the selected items from the working directory of the active panel to the working directory of the passive panel.

## Modifying Settings

The "Settings" menu in the ribbon contains two options:

* **"Show Tree View"**: When enabled, displays the tree view alongside the folder view in the panels.  
* **"Highlight Active Pane"**: When enabled, the active panel is highlighted with a red border.
