package rebound.creatures.tlc2e.client.gui.components;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JFrame;
import rebound.hci.graphics2d.gui.awt.components.ListOfComponentsScrollablePane;
import rebound.hci.graphics2d.gui.awt.layout.colinear.ColinearAwtLayoutManager;
import rebound.hci.graphics2d.gui.layout.colinear.ColinearLayout;
import rebound.math.geom2d.Direction2D.Axis2D;
import rebound.util.functional.FunctionInterfaces.UnaryProcedure;

public class TLC2EChooserDedicatedWindow
extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected ListOfComponentsScrollablePane<TLC2EChooserEntrySwing> scroller;
	protected UnaryProcedure<Integer> userSelected;
	protected Runnable userCancelled;
	
	public TLC2EChooserDedicatedWindow(List<TLC2EChooserEntrySwing> entryGUIs, @Nullable UnaryProcedure<Integer> userSelected, @Nullable Runnable userCancelled)
	{
		this.userSelected = userSelected;
		this.userCancelled = userCancelled;
		
		setTitle("Please choose a Creatures Engine instance.");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		scroller = TLC2EChooserSwing.create(entryGUIs);
		
		Color bg = TLC2EChooserEntrySwing.BGUnhighlighted;
		this.setBackground(bg);
		
		
		
		JButton okButton = new JButton("Okay");
		JButton cancelButton = new JButton("Cancel");
		
		okButton.setBackground(bg);
		okButton.setForeground(new Color(192, 192, 192));
		cancelButton.setBackground(bg);
		cancelButton.setForeground(new Color(192, 192, 192));
		
		this.getContentPane().add(scroller);
		this.getContentPane().add(okButton);
		this.getContentPane().add(cancelButton);
		
		
		
		
		//<Actions :3
		scroller.setClickListener(this::userAccepted);
		
		scroller.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					e.consume();
					userAccepted();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					e.consume();
					userCancelled();
				}
			}
		});
		
		
		okButton.addActionListener(e -> userAccepted());
		cancelButton.addActionListener(e -> userCancelled());
		
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				userCancelled();
			}
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				userCancelled();
			}
		});
		//Actions :3 >
		
		
		
		
		
		
		getContentPane().setLayout(new ColinearAwtLayoutManager(
		/* <<<
		colinear-layout
		
		
		
		<h>
			<fix amt="0" />
			
			<rem>
				<v>
					<fix amt="0" />
					
					<rem>scroller</rem>
					
					<fix amt="2"/>
					
					<fix amt="25">
						<h>
							<fix amt="2"/>
							<prp amt="0.5">okButton</prp>
							<fix amt="2"/>
							<rem>cancelButton</rem>
							<fix amt="2"/>
						</h>
					</fix>
					
					<fix amt="2" />
				</v>
			</rem>
			
			<fix amt="0" />
		</h>
		
		
		
		 */
		ColinearLayout.clepV(Axis2D.XHorizontal,  ColinearLayout.clefix(0, null), ColinearLayout.clerem(ColinearLayout.clepV(Axis2D.YVertical,  ColinearLayout.clefix(0, null), ColinearLayout.clerem(scroller), ColinearLayout.clefix(2, null), ColinearLayout.clefix(25, ColinearLayout.clepV(Axis2D.XHorizontal,  ColinearLayout.clefix(2, null), ColinearLayout.cleprp(0.5f, okButton), ColinearLayout.clefix(2, null), ColinearLayout.clerem(cancelButton), ColinearLayout.clefix(2, null))), ColinearLayout.clefix(2, null))), ColinearLayout.clefix(0, null))
		// >>>
		));
	}
	
	
	
	
	
	protected boolean done = false;
	
	public void userAccepted()
	{
		userAccepted(scroller.getCurrentlyHighlightedOneIndex());
	}
	
	public void userAccepted(int index)
	{
		if (!done)
		{
			if (userSelected != null)
				userSelected.f(index);
			
			this.dispose();
			done = true;
		}
	}
	
	public void userCancelled()
	{
		if (!done)
		{
			if (userCancelled != null)
				userCancelled.run();
			
			this.dispose();
			done = true;
		}
	}
}
