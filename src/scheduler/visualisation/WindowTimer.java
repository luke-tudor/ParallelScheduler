package scheduler.visualisation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

import javafx.application.Platform;
import scheduler.Scheduler;

/**
 * 
 * @author nathan
 *
 */

public class WindowTimer extends TimerTask {

	private Window _window;
	private Scheduler _scheduler;
	
	public WindowTimer(Window window) {
		super();
		_window = window;
		_scheduler = Scheduler.getInstance();
	}
	
	@Override
	public void run() {
		
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		//LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now)); 
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_window.setTreeNode(_scheduler.getNextTN());
				_window.setProgressBar();
			}
		});
		
		
		
		
		// _window.setTreeNode(call method from scheduler);
		// _window.setnumof nodes done
	}

}
