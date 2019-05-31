package com.melardev.chat.ui.swing;

import com.melardev.chat.ui.mediators.UiMediator;

public interface AppWindow {

    public UiMediator getUiMediator();

    public void setUiMediator(UiMediator uiMediator);
}
