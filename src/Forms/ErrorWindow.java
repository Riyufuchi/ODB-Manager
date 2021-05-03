package Forms;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * Copyright Header
 * 
 * Projetct: ODB Manager
 * Created On: 13.07.2020
 * Created By: Riyufuchi
 * 
 */

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame  
{
	private JPanel contentPanel;
	private JTextArea errorMessageLabel;
	
	public ErrorWindow(String WindowTitle, String ErrorMessage)
    {
        this.setTitle(WindowTitle);
        this.setSize(300,200);
        this.setMinimumSize(new Dimension(300, 200));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        contentPanel = new JPanel(null);
        errorMessageLabel = new JTextArea();
        errorMessageLabel.setText(ErrorMessage);
        errorMessageLabel.setEditable(false);
        errorMessageLabel.setLineWrap(true);
        errorMessageLabel.setWrapStyleWord(true);
        errorMessageLabel.setFont(javax.swing.UIManager.getDefaults().getFont("Label.font"));
        contentPanel.add(errorMessageLabel);
        this.add(contentPanel);
        this.setVisible(true);
        this.addComponentListener(new ComponentAdapter() 
        {
            public void componentResized(ComponentEvent componentEvent) 
            {
            	resize();
            }
        });
    }
	
	private void resize()
	{
		errorMessageLabel.setBounds(0, 0, getWidth(), getHeight());
		this.repaint();
	}
}