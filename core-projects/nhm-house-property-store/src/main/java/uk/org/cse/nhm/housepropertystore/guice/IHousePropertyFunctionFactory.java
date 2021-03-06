package uk.org.cse.nhm.housepropertystore.guice;

import uk.org.cse.nhm.housepropertystore.HouseProperty;
import uk.org.cse.nhm.housepropertystore.HousePropertyFunction;
import uk.org.cse.nhm.housepropertystore.HousePropertyNumber;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

public interface IHousePropertyFunctionFactory {
	public HousePropertyFunction createFunction(final String variable, final Predicate<String> test);

	public HousePropertyNumber createNumber(
			@Assisted String name);

	public HouseProperty createCategory(String name);
}
