package radua.ui.logic.controllers;

import radua.ui.logic.view.IModelView;

public interface IStageWrapper {
	void addNewView(IModelView<?> view);
	void removeView(IModelView<?> view);
}
