/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Main Water Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl#getHeatSource <em>Heat Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MainWaterHeaterImpl extends CentralWaterHeaterImpl implements IMainWaterHeater {
	/**
	 * The cached value of the '{@link #getHeatSource() <em>Heat Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeatSource()
	 * @generated
	 * @ordered
	 */
	protected IHeatSource heatSource;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MainWaterHeaterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.MAIN_WATER_HEATER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IHeatSource getHeatSource() {
		if (heatSource != null && heatSource.eIsProxy()) {
			InternalEObject oldHeatSource = (InternalEObject)heatSource;
			heatSource = (IHeatSource)eResolveProxy(oldHeatSource);
			if (heatSource != oldHeatSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, oldHeatSource, heatSource));
			}
		}
		return heatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHeatSource basicGetHeatSource() {
		return heatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeatSource(IHeatSource newHeatSource, NotificationChain msgs) {
		IHeatSource oldHeatSource = heatSource;
		heatSource = newHeatSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, oldHeatSource, newHeatSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeatSource(IHeatSource newHeatSource) {
		if (newHeatSource != heatSource) {
			NotificationChain msgs = null;
			if (heatSource != null)
				msgs = ((InternalEObject)heatSource).eInverseRemove(this, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, IHeatSource.class, msgs);
			if (newHeatSource != null)
				msgs = ((InternalEObject)newHeatSource).eInverseAdd(this, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, IHeatSource.class, msgs);
			msgs = basicSetHeatSource(newHeatSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE, newHeatSource, newHeatSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				if (heatSource != null)
					msgs = ((InternalEObject)heatSource).eInverseRemove(this, ITechnologiesPackage.HEAT_SOURCE__WATER_HEATER, IHeatSource.class, msgs);
				return basicSetHeatSource((IHeatSource)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				return basicSetHeatSource(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				if (resolve) return getHeatSource();
				return basicGetHeatSource();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				setHeatSource((IHeatSource)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				setHeatSource((IHeatSource)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.MAIN_WATER_HEATER__HEAT_SOURCE:
				return heatSource != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryCorrectionFactor, final double distributionLossFactor, final double proportion) {
		if (getHeatSource() != null) {
			return getHeatSource().generateHotWaterAndPrimaryGains(parameters, state, store, storeIsPrimary, primaryCorrectionFactor, distributionLossFactor, proportion);
		} else {
			return 0;
		}
	}

	@Override
	public void generateSystemGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double systemLosses) {
		if (getHeatSource() != null) {
			getHeatSource().generateHotWaterSystemGains(parameters, state, store, storeIsPrimary, systemLosses);
		}
	}
} //MainWaterHeaterImpl