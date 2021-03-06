package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("group.union")
@Doc("At any time, this group will contain all the houses which are in any of its sources.")
public class XUnionGroup extends XGroupWithSources {

}
