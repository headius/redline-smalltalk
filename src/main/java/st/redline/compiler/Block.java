/* Redline Smalltalk, Copyright (c) James C. Ladd. All rights reserved. See LICENSE in the root of this distribution */
package st.redline.compiler;

import java.util.ArrayList;
import java.util.List;

public class Block extends BasePrimary {

	private final List<VariableName> variableNames;
	private Temporaries temporaries;
	private Statements statements;

	public Block() {
		this.variableNames = new ArrayList<VariableName>();
	}

	public void add(VariableName variableName) {
		variableNames.add(variableName);
	}

	public void add(Temporaries temporaries) {
		this.temporaries = temporaries;
	}

	public void add(Statements statements) {
		this.statements = statements;
	}

	public boolean hasStatements() {
		return statements != null;
	}

	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		// we don't visit the rest during class / method analysis, but we do during block analysis.
		if (!visitor.continueBlockVisit())
			return;
		if (temporaries != null)
			temporaries.accept(visitor);
		if (statements != null)
			statements.accept(visitor);
		visitor.visitEnd(this);
	}
}
