package model.exitReciever;

import controller.Controller;
import controller.ControllerInterface;
import model.Model;

/**
 *
 * @author Adin Epstein
 * @since 25/12/16 this class send to the CLI a command to exit from the game
 *        loop
 *
 *
 */
public class MyExiter extends AbstractExiter {

	private ControllerInterface controllerInterface;
	private Model model;

	public MyExiter(ControllerInterface controller, Model model) {
		this.controllerInterface = controller;
		this.model = model;
	}

	@Override
	public void exiting() {

		controllerInterface.getController().stop();
		model.getDBReciver().getGdm().close();

		if (controllerInterface.getServer() != null)
			controllerInterface.getServer().stopServer();
	}

}
