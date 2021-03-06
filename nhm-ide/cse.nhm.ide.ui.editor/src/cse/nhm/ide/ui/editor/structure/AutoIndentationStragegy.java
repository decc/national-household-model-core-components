package cse.nhm.ide.ui.editor.structure;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

import cse.nhm.ide.ui.editor.structure.Indenter.Tabstops;

public class AutoIndentationStragegy implements IAutoEditStrategy {

	@Override
	public void customizeDocumentCommand(final IDocument d, final DocumentCommand c) {
		if (c.length == 0 && c.text != null && TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1)
			autoIndentAfterNewLine(d, c);
	}

	private void autoIndentAfterNewLine(final IDocument d, final DocumentCommand c) {
		if (c.offset == -1 || d.getLength() == 0)
			return;

		try {
			final int p = (c.offset == d.getLength() ? c.offset  - 1 : c.offset);
			final int lineNumber = d.getLineOfOffset(p);
			final Tabstops stops = new Tabstops();
			for (int i = 0; i<= lineNumber; i++) {
				final IRegion lineInformation = d.getLineInformation(i);
				final String string = d.get(lineInformation.getOffset(),
						i == lineNumber ?
									(c.offset - lineInformation.getOffset())
								:
									lineInformation.getLength());
				stops.eat(string.trim());
			}
			c.text += stops.string;
		} catch (final BadLocationException excp) {
			// stop work
		}
	}
}
