package mimuw.idlearn.idlang.GUI.codeblocks;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mimuw.idlearn.idlang.GUI.CodeBox;

import java.util.function.Supplier;

public class CodeBlockSpawner extends HBox {

	private final CodeBox codeBox;
	private final Supplier<CodeBlock> spawn;
	private final Pane dragged;

	/**
	 * Spawns our CodeBlock
	 */
	public void spawnBlock() {
		CodeBlock block = spawn.get();
		block.makeDraggable(codeBox, dragged);

		this.getChildren().add(block);
	}

	/**
	 * Creates a new CodeBlockSpawner
	 *
	 * @param codeBox The box for code our CodeBlocks will interact with
	 * @param spawn   The function for spawning new CodeBlocks
	 */
	public CodeBlockSpawner(CodeBox codeBox, Supplier<CodeBlock> spawn, Pane dragged) {
		super();
		this.codeBox = codeBox;
		this.spawn = spawn;
		this.dragged = dragged;

		spawnBlock();


		// Listens if the child is being moved
		// Non-moving interactions consume the event so this won't be triggered
		this.addEventHandler(
				MouseEvent.MOUSE_PRESSED,
				mouseEvent -> spawnBlock()
		);
	}

}
