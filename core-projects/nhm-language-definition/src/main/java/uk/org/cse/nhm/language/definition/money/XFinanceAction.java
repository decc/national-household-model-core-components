package uk.org.cse.nhm.language.definition.money;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.tags.Tag;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Category(CategoryType.MONEY)
public abstract class XFinanceAction extends XFlaggedDwellingAction {
	public static class P {
		public static final String ACTION = "action";
		public static final String TAGS = "tags";
		public static final String COUNTERPARTY = "counterparty";
	}

	private XDwellingAction action;
	private List<Tag> tags = new ArrayList<>();
	private String counterparty;

	@Prop(P.ACTION)
	
	@BindPositionalArgument(0)
	@Doc("The action which will be applied before any financial adjustments.")
	@NotNull(message = "financial actions must contain the real action which the finance will be applied to")
	public XDwellingAction getAction() {
		return action;
	}

	public void setAction(final XDwellingAction action) {
		this.action = action;
	}

	@Prop(P.TAGS)
	
@BindNamedArgument
	@Doc("Tags which will be applied to transactions generated by the finance action.")
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}

	@Prop(P.COUNTERPARTY)
	
@BindNamedArgument
	@Doc("Counterparty which will transact with the house. By default this is the enclosing policy.")
	public String getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(final String counterparty) {
		this.counterparty = counterparty;
	}

}