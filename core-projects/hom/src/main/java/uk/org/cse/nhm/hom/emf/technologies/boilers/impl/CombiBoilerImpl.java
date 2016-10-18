/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.ecore.EClass;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Combi Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class CombiBoilerImpl extends BoilerImpl implements ICombiBoiler {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CombiBoilerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.COMBI_BOILER;
	}

	/**
	 * Because in SAP table 4c (efficiency adjustments) combi water efficiency is not affected by presence
	 * of interlock, we override the method from the basic boiler here.
	 */
	@Override
	protected double getWaterHeatingEfficiency(IConstants constants, double qWater, double qSpace) {
		return getSeasonalEfficiency(qWater, qSpace);
	}
} //CombiBoilerImpl