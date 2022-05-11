package mimuw.idlearn.idlang.GUI.codeblocks.blocktypes;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mimuw.idlearn.idlang.GUI.codeblocks.BlockBase;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlock;
import mimuw.idlearn.idlang.GUI.codeblocks.ResizableTextField;
import mimuw.idlearn.idlang.GUI.parser.StringToExpression;
import mimuw.idlearn.idlang.logic.base.Expression;
import mimuw.idlearn.idlang.logic.keywords.Assignment;

public class Assign extends CodeBlock {

	private final BlockBase base = new BlockBase(HEIGHT, Color.GRAY);
	TextField varName;
	TextField value;

	/**
	 * @return An equivalent expression
	 */
	@Override
	public Expression<Void> convert() {
		String name = varName.getText();
		String valueText = value.getText();
		Expression<Integer> valueInt = StringToExpression.parse(valueText);
		Assignment<Integer> assign = new Assignment<>(name, valueInt, true);
		return assign;
	}

	/**
	 * Create a new Assignment CodeBlock
	 */
	public Assign() {
		super();

		varName = new ResizableTextField(base);
		final Text equal = new Text(" = ");
		value = new ResizableTextField(base);

		base.addChild(varName);
		base.addChild(equal);
		base.addChild(value);

		this.getChildren().add(base);
	}

	/**
	 * Set the text in our assign block
	 *
	 * @param text1 Variable name
	 * @param text2 Value
	 */
	public void setText(String text1, String text2) {
		varName.setText(text1);
		value.setText(text2);
	}
}
