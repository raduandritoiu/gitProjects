package com.joeberkovitz.moccasin.editor
{
    import com.joeberkovitz.moccasin.document.UndoHistory;
    import com.joeberkovitz.moccasin.event.ControllerEvent;
    import com.joeberkovitz.moccasin.event.SelectEvent;
    import com.joeberkovitz.moccasin.event.UndoEvent;
    
    import mx.collections.XMLListCollection;
    import mx.controls.MenuBar;
    import mx.events.MenuEvent;
    import mx.events.PropertyChangeEvent;
    
    /**
     * Basic menu bar for Moccasin application functionality.
     */
    public class EditorMenuBar extends MenuBar
    {
        private var _editor:MoccasinEditor;
        
        private var menuBarProvider:XMLListCollection;

        protected var menuBarDefinition:XMLList =
            <>
                <menuitem id="file" label="File">
                    <menuitem id="save" label="Save"/>
<!--                <menuitem id="print" label="Print..."/>  -->
                </menuitem>
                <menuitem id="edit" label="Edit">
                    <menuitem id="undo" label="Undo"/>
                    <menuitem id="redo" label="Redo"/>
                    <menuitem type="separator"/>
                    <menuitem id="cut" label="Cut"/>
                    <menuitem id="copy" label="Copy"/>
                    <menuitem id="paste" label="Paste"/>
                    <menuitem type="separator"/>
                    <menuitem id="delete" label="Delete"/>
                </menuitem>
            </>;

        public function EditorMenuBar()
        {
            initializeMenuItems();
            menuBarProvider = new XMLListCollection(menuBarDefinition); 
            dataProvider = menuBarProvider;
            labelField = "@label";
            addEventListener(MenuEvent.ITEM_CLICK, handleItemClick);
        }
        
        [Bindable]
        public function get editor():MoccasinEditor
        {
            return _editor;
        }
        
        public function set editor(e:MoccasinEditor):void
        {
            if (editor != null)
            {
                _editor.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, handleEditorPropertyChange);
                _editor.controller.removeEventListener(ControllerEvent.DOCUMENT_CHANGE, handleDocumentChange);
                _editor.controller.removeEventListener(SelectEvent.CHANGE_SELECTION, handleChangeSelection);
            }
            _editor = e;
            if (editor != null)
            {
                _editor.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, handleEditorPropertyChange);
                _editor.controller.addEventListener(ControllerEvent.DOCUMENT_CHANGE, handleDocumentChange);
                _editor.controller.addEventListener(SelectEvent.CHANGE_SELECTION, handleChangeSelection);
            }
            if (editor.controller.document != null)
            {
                handleDocumentChange(null);
            }
            updateMenuItems();
        }
        
        protected function initializeMenuItems():void
        {
        }
        
        protected function handleEditorPropertyChange(e:PropertyChangeEvent):void
        {
            // need to conditionalize on various properties
        }
        
        protected function handleChangeSelection(e:SelectEvent):void
        {
            // override to provide context sensitive menu
        }
        
        protected function updateMenuItems():void
        {
            if (editor.controller.document != null)
            {
                updateUndoItems();
            }
        }
        
        protected function getMenuItem(menuName:String, itemName:String):XML
        {
            for each (var child:XML in menuBarDefinition)
            {
                if (child.@id.toString() == menuName)
                {
                    for each (var item:XML in child.menuitem)
                    {
                        if (item.@id.toString() == itemName)
                        {
                            return item;
                        } 
                    }
                }
            }
            return null;
        }

        protected function setItemEnabled(menuName:String, itemName:String, enabled:Boolean):void
        {
            getMenuItem(menuName, itemName).@enabled = enabled ? "true" : "false";
        }
        
        protected function setItemToggled(menuName:String, itemName:String, toggled:Boolean):void
        {
            getMenuItem(menuName, itemName).@toggled = toggled ? "true" : "false";
        }
        
        protected function setItemLabel(menuName:String, itemName:String, label:String):void
        {
            getMenuItem(menuName, itemName).@label = label;
        }
        
        private function handleDocumentChange(e:ControllerEvent):void
        {
            _editor.controller.document.undoHistory.addEventListener(UndoEvent.UNDO_HISTORY_CHANGE, handleUndoChange);
            updateMenuItems();
        }

        private function handleUndoChange(e:UndoEvent):void
        {
            updateUndoItems();
        }
        
        private function updateUndoItems():void
        {
            var history:UndoHistory = _editor.controller.document.undoHistory;
            setItemEnabled("edit", "undo", history.canUndo);
            setItemLabel("edit", "undo", "Undo " + history.undoName);
            setItemEnabled("edit", "redo", history.canRedo);
            setItemLabel("edit", "redo", "Redo " + history.redoName);
        }

        private function handleItemClick(e:MenuEvent):void
        {
            handleCommand(e.item.@id.toString());
        }
        
        protected function handleCommand(commandName:String):void
        {
            switch(commandName)
            {
            case "save":
                editor.saveDocument();
                break;
                
            case "print":
                editor.printDocument();
                break;
                
            case "undo":
                editor.controller.undo();
                break;
                
            case "redo":
                editor.controller.redo();
                break;
                
            case "cut":
                editor.controller.document.undoHistory.openGroup("Cut");
                editor.controller.cutClipboard();
                break;
                
            case "copy":
                editor.controller.copyClipboard();
                break;
                
            case "paste":
                editor.controller.document.undoHistory.openGroup("Paste");
                editor.controller.pasteClipboard();
                break;
                
            case "delete":
                editor.controller.document.undoHistory.openGroup("Delete");
                editor.controller.removeSelection();
                break;
            }
        }
    }
}
