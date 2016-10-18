package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc({
	"Evaluates the SAP score equations on page 32 of SAP 2009 version 9.90 (March 2010); ",
	"for reference this is 117-121 * log10(ECF) when ECF >= 3.5, and 100-13.95*ECF otherwise, ",
    "where ECF = 0.47 * (fuel bill excluding appliances and cooking) / (floor area + 45).",
	"Note that this is <emphasis>not</emphasis> exactly the SAP score, unless it is evaluated under a SAP-like counterfactual using <xref linkend=\"global--under\" />.",
	"Outside of such a counterfactual, this will evaluate the scoring equations using the NHM's prediction of fuel cost, which will in turn use",
	"the scenario's specified tariffs, weather, u-values and so on rather than the SAP standards"
})
@Bind("house.fuel-cost-index")
public class XSapScore extends XHouseNumber implements ICalibratedEnergyFunction {
	public static final class P {
		public static final String deflator = "deflator";
	}
	private double deflator = 0.47;

	
	@BindNamedArgument
	@Doc("The energy cost deflator; this is defined in SAP 2009 version 9.90 (March 2010) Table 12, but could change in future SAP versions.")
	@Prop(P.deflator)
	public double getDeflator() {
		return deflator;
	}

	public void setDeflator(final double deflator) {
		this.deflator = deflator;
	}
}