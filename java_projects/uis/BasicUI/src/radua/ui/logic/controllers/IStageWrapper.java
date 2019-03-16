package radua.ui.logic.controllers;

import radua.ui.logic.views.IBasicView;

public interface IStageWrapper {
	void addNewView(IBasicView<?> view);
	void removeView(IBasicView<?> view);
}
