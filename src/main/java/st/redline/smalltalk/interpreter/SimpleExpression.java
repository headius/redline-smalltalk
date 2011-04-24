/*
Redline Smalltalk is licensed under the MIT License

Redline Smalltalk Copyright (c) 2010 James C. Ladd

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package st.redline.smalltalk.interpreter;

import java.util.ArrayList;
import java.util.List;

public class SimpleExpression implements Expression {

	private Primary primary;
	private MessageExpression messageExpression;
	private final List<MessageElement> messageElements;
	private boolean resultLeftOnStack;

	public SimpleExpression() {
		messageElements = new ArrayList<MessageElement>();
		resultLeftOnStack = false;
	}

	public boolean isResultLeftOnStack() {
		return resultLeftOnStack;
	}

	public void leaveResultOnStack() {
		resultLeftOnStack = true;
	}

	public void add(Primary primary) {
		this.primary = primary;
	}

	public void add(MessageExpression messageExpression) {
		this.messageExpression = messageExpression;
	}

	public void add(MessageElement messageElement) {
		messageElements.add(messageElement);
	}

	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		primary.accept(visitor);
		if (messageExpression != null)
			messageExpression.accept(visitor);
		for (MessageElement messageElement : messageElements)
			messageElement.accept(visitor);
		visitor.visitEnd(this);
	}
}