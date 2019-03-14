package radua.ui.controllers;

import radua.ui.views.IBasicView;

public interface IStageWrapper {
	void addNewView(IBasicView<?> view);
	void removeView(IBasicView<?> view);
}
