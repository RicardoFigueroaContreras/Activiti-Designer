/**
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
package org.activiti.designer.property.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

public class IOParameterDialog extends Dialog implements ITabbedPropertyConstants {
	
	public String source;
	public String sourceExpression;
	public String target;
	public String targetExpression;
	
	protected String savedSource;
	protected String savedSourceExpression;
	protected String savedTarget;
	protected String savedTargetExpression;

	public IOParameterDialog(Shell parent, TableItem[] fieldList) {
		// Pass the default styles here
		this(parent, fieldList, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public IOParameterDialog(Shell parent, TableItem[] fieldList, String savedSource, String savedSourceExpression,
			String savedTarget, String savedTargetExpression) {
    // Pass the default styles here
    this(parent, fieldList, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    this.savedSource = savedSource;
    this.savedSourceExpression = savedSourceExpression;
    this.savedTarget = savedTarget;
    this.savedTargetExpression = savedTargetExpression;
  }

	public IOParameterDialog(Shell parent, TableItem[] fieldList, int style) {
		// Let users override the default styles
		super(parent, style);
		setText("Form property configuration");
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public String open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		shell.setText(getText());
		shell.setSize(700, 400);
		Point location = getParent().getShell().getLocation();
		Point size = getParent().getShell().getSize();
		shell.setLocation((location.x + size.x - 300) / 2, (location.y + size.y - 150) / 2);
		createContents(shell);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return null;
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell
	 *            the dialog window
	 */
	private void createContents(final Shell shell) {
	  FormLayout layout = new FormLayout();
	  layout.marginHeight = 5;
	  layout.marginWidth = 5;
	  shell.setLayout(layout);
	  FormData data;
	  
	  final Text sourceText = new Text(shell, SWT.BORDER);
	  if(savedSource != null) {
	    sourceText.setText(savedSource);
	  }
    data = new FormData();
    data.left = new FormAttachment(0, 180);
    data.right = new FormAttachment(70, 0);
    data.top = new FormAttachment(0, 10);
    sourceText.setLayoutData(data);
    
    createLabel("Source", shell, sourceText);
    
    final Text sourceExpressionText = new Text(shell, SWT.BORDER);
	  if(savedSourceExpression != null) {
	  	sourceExpressionText.setText(savedSourceExpression);
	  }
    data = new FormData();
    data.left = new FormAttachment(0, 180);
    data.right = new FormAttachment(70, 0);
    data.top = new FormAttachment(sourceText, 10);
    sourceExpressionText.setLayoutData(data);
    
    createLabel("Source expression", shell, sourceExpressionText);
    
    final Text targetText = new Text(shell, SWT.BORDER);
    if(savedTarget != null) {
      targetText.setText(savedTarget);
    }
    data = new FormData();
    data.left = new FormAttachment(0, 180);
    data.right = new FormAttachment(70, 0);
    data.top = new FormAttachment(sourceExpressionText, 10);
    targetText.setLayoutData(data);
    
    createLabel("Target", shell, targetText);
    
    final Text targetExpressionText = new Text(shell, SWT.BORDER);
    if(savedTargetExpression != null) {
    	targetExpressionText.setText(savedTargetExpression);
    }
    data = new FormData();
    data.left = new FormAttachment(0, 180);
    data.right = new FormAttachment(70, 0);
    data.top = new FormAttachment(targetText, 10);
    targetExpressionText.setLayoutData(data);
    
    createLabel("Target expression", shell, targetExpressionText);
    
    // Create the cancel button and add a handler
    // so that pressing it will set input to null
    Button cancel = new Button(shell, SWT.PUSH);
    cancel.setText("Cancel");
    data = new FormData();
    data.left = new FormAttachment(0, 180);
    data.right = new FormAttachment(50, 0);
    data.top = new FormAttachment(targetExpressionText, 20);
    cancel.setLayoutData(data);
    cancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        shell.close();
      }
    });

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(cancel, -HSPACE);
    data.top = new FormAttachment(cancel, 0, SWT.TOP);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if(StringUtils.isEmpty(sourceText.getText()) && StringUtils.isEmpty(sourceExpressionText.getText())) {
					MessageDialog.openError(shell, "Validation error", "Source or source expression must be filled.");
					return;
				}
				if(StringUtils.isEmpty(targetText.getText()) && StringUtils.isEmpty(targetExpressionText.getText())) {
          MessageDialog.openError(shell, "Validation error", "Target or target expression must be filled.");
          return;
        }
				source = sourceText.getText();
				sourceExpression = sourceExpressionText.getText();
				target = targetText.getText();
				targetExpression = targetExpressionText.getText();
				shell.close();
			}
		});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
	}
	
	private void createLabel(String text, Shell shell, Control control) {
	  CLabel idLabel = new CLabel(shell, SWT.NONE);
    idLabel.setText(text);
    FormData data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(control, -HSPACE);
    data.top = new FormAttachment(control, 0, SWT.TOP);
    idLabel.setLayoutData(data);
    idLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}
}
