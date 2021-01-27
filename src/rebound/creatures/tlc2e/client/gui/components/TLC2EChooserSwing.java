package rebound.creatures.tlc2e.client.gui.components;

import static rebound.math.SmallIntegerMathUtilities.*;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import rebound.hci.graphics2d.gui.awt.components.ListOfComponentsScrollablePane;
import rebound.hci.util.awt.JavaGUIUtilities;

public class TLC2EChooserSwing
{
	//Todo make configurable ^^'
	public static final int EntryComponentHeight = 100;
	public static final int EntryComponentMinimumHeight = 50;  //If the difference between this and the initial height isn't a multiple of the step change, then once people shrink the entries maximally, they won't be able to get back to the original size without closing and finding how to reopen the window! XD''
	public static final int EntryComponentHeightStepChange = 100;
	
	
	public static ListOfComponentsScrollablePane<TLC2EChooserEntrySwing> create(List<TLC2EChooserEntrySwing> entries)
	{
		Color bg = TLC2EChooserEntrySwing.BGUnhighlighted;
		
		for (TLC2EChooserEntrySwing e : entries)
			e.setSize(0, EntryComponentHeight);
		
		ListOfComponentsScrollablePane<TLC2EChooserEntrySwing> scroller = new ListOfComponentsScrollablePane<>(entries, true);
		
		scroller.getViewport().setBackground(bg);
		
		scroller.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				//				boolean ctrl = (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
				//				boolean shift = (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0;
				
				TLC2EChooserEntrySwing c = scroller.getCurrentlyHighlightedOne();
				
				if (e.getKeyCode() == KeyEvent.VK_MINUS)
				{
					e.consume();
					shrinkAllEntriesOneStep(scroller);
				}
				else if (e.getKeyCode() == KeyEvent.VK_EQUALS)
				{
					e.consume();
					expandAllEntriesOneStep(scroller);
				}
				else if (e.getKeyCode() == KeyEvent.VK_C)
				{
					e.consume();
					
					String name = c.getEntryName();
					String text = (name == null ? "" : (name+" - "))+c.getEntryDescription();
					
					JavaGUIUtilities.setClipboardText(text);
				}
			}
		});
		
		return scroller;
	}
	
	
	
	
	
	public static void shrinkAllEntriesOneStep(ListOfComponentsScrollablePane<TLC2EChooserEntrySwing> scroller)
	{
		for (TLC2EChooserEntrySwing entryUI : scroller.getEntries())
		{
			entryUI.setSize(entryUI.getWidth(), greatest(EntryComponentMinimumHeight, entryUI.getHeight() - EntryComponentHeightStepChange));
		}
		
		scroller.contentsHeightsChanged();
	}
	
	
	public static void expandAllEntriesOneStep(ListOfComponentsScrollablePane<TLC2EChooserEntrySwing> scroller)
	{
		for (TLC2EChooserEntrySwing entryUI : scroller.getEntries())
		{
			entryUI.setSize(entryUI.getWidth(), entryUI.getHeight() + EntryComponentHeightStepChange);
		}
		
		scroller.contentsHeightsChanged();
	}
}
