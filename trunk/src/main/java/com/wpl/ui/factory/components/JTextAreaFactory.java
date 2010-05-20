/*
 * Copyright 2010 Kenny Chong (wongpeiling.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wpl.ui.factory.components;

import java.awt.Component;

import javax.swing.JTextArea;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.UiTabSize;
import com.wpl.ui.factory.UiAnnotationHandler;

public class JTextAreaFactory extends JTextComponentFactory {

    @UiAnnotationHandler(UiRows.class)
    protected void handleUiText(JTextArea component, UiRows annotate) {
        component.setRows(annotate.value());
    }

    @UiAnnotationHandler(UiColumns.class)
    protected void handleUiText(JTextArea component, UiColumns annotate) {
        component.setColumns(annotate.value());
    }

    @UiAnnotationHandler(UiTabSize.class)
    protected void handleUiText(JTextArea component, UiTabSize annotate) {
        component.setTabSize(annotate.value());
    }

    @Override
    protected Component createDefaultComponent() {
        return new JTextArea();
    }
}
