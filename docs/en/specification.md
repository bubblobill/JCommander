# Two-Panel File Manager Application - Specification

## Introduction

### Purpose of the Program

The program aims to bring back the **two-panel file manager** solutions popular in the _'80s and '90s_ in a modernized form.

The essence of two-panel file managers is that the application's interface displays two separate panels, each representing a view of the file system. Typically, this includes:

- A tree structure reflecting the file system hierarchy,
- A list showing the contents of the current working directory,
- An address bar for specifying paths.

Each panel allows independent navigation within the file system, whether through tree or list interactions or by entering a specific path in the address bar.

One of the most useful features—and a common use case—of such solutions is their clarity during file copy and move operations. The side-by-side panels make it easy to see the source and destination of the operation.

## Use Cases

The program supports the following **operations**:

- Navigate **"downward"** (child items: folder or mount point),
- Navigate **"upward"** (parent items: folder, mount point, or root),
- Navigate to the **previous working directory** (the one before the current),
- Navigate to the **next working directory** (the one before the "back" operation),
- **Refresh** the working directory's contents (synchronize with the file system),
- **Copy files** (between directories represented by the two panels),
- **Move/relocate files**,
- **Modify settings**, including:
  - Show/hide the file system tree,
  - Select icon style.

## Proposed Solution

### MVC Architecture

The two panels can be modeled as objects encapsulating their own model, view, and controller (**Model-View-Controller**).

This solution's primary advantage is that **all views automatically update** when the data in the model changes. This occurs without needing to specify how each view should handle updates at the point of data modification. As a result, new views can be added without altering the existing update-handling code.

### Model

The model can be considered a session, encompassing the current working directory and its history.

### View

The view consists of three components: tree, list, and address bar.

### Controller

The controller's role is to update the model data based on user interactions detected in the view.

For this relatively small architecture, an additional module connecting the MVC components isn't necessary. Therefore, the controller is also responsible for creating and linking the model and view components.

## File Format Used

### Configuration File Format

The configuration file enables the program to persist user preferences regarding the program's appearance and functionality (such as the options listed in [Use Cases](#use-cases)).

Each line in the configuration file contains a key-value pair separated by an equals sign (`=`).

#### Example

```
SHOW_TREE_VIEW=true
ICON_STYLE=colorful
...
```
