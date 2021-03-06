/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.impl.demands.HotWaterDemand09;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.IAppliance;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IShower;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Technology Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getAppliances <em>Appliances</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getLights <em>Lights</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getIndividualHeatSource <em>Individual Heat Source</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getCookers <em>Cookers</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getPrimarySpaceHeater <em>Primary Space Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getSecondarySpaceHeater <em>Secondary Space Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getCentralWaterSystem <em>Central Water System</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getSecondaryWaterHeater <em>Secondary Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getCommunityHeatSource <em>Community Heat Source</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getSolarPhotovoltaic <em>Solar Photovoltaic</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getAdjusters <em>Adjusters</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl#getShower <em>Shower</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TechnologyModelImpl extends MinimalEObjectImpl implements ITechnologyModel {
	/**
	 * <!-- begin-user-doc -->
	 * This corresponds to heating system 693 "Portable Electric Heaters" and 699 "Electric heaters (assumed)" in SAP tables 4a to 4c.
	 * It is used when the primary space heater is missing or broken.
	 * It is also used when the secondary heating is required but missing (see SAP Table 11 below). 
	 * 
	 * We've represented portable electric heaters as an electric room heater, since this should provide identical results.
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public static final IRoomHeater assumedElectricSpaceHeater = ITechnologiesFactory.eINSTANCE.createRoomHeater();
	
	/**
	 * <!-- begin-user-doc -->
	 * If there are no water heaters present, hot water is assumed to be generated by electricity.
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public static final IPointOfUseWaterHeater assumedElectricWaterHeater = ITechnologiesFactory.eINSTANCE.createPointOfUseWaterHeater();
	
	static {
		assumedElectricSpaceHeater.setFuel(FuelType.ELECTRICITY);
		assumedElectricWaterHeater.setEfficiency(Efficiency.fromDouble(1.0));
		assumedElectricSpaceHeater.setFlueType(FlueType.NOT_APPLICABLE);
		assumedElectricSpaceHeater.setThermostatFitted(true);
		
		assumedElectricWaterHeater.setFuelType(FuelType.ELECTRICITY);
		assumedElectricWaterHeater.setEfficiency(Efficiency.fromDouble(1.0));
		assumedElectricWaterHeater.setMultipoint(false);
	}
	
	
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The cached value of the '{@link #getAppliances() <em>Appliances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliances()
	 * @generated
	 * @ordered
	 */
	protected EList<IAppliance> appliances;

	/**
	 * The cached value of the '{@link #getLights() <em>Lights</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLights()
	 * @generated
	 * @ordered
	 */
	protected EList<ILight> lights;

	/**
	 * The cached value of the '{@link #getIndividualHeatSource() <em>Individual Heat Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndividualHeatSource()
	 * @generated
	 * @ordered
	 */
	protected IIndividualHeatSource individualHeatSource;

	/**
	 * The cached value of the '{@link #getCookers() <em>Cookers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCookers()
	 * @generated
	 * @ordered
	 */
	protected EList<ICooker> cookers;

	/**
	 * The cached value of the '{@link #getPrimarySpaceHeater() <em>Primary Space Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimarySpaceHeater()
	 * @generated
	 * @ordered
	 */
	protected IPrimarySpaceHeater primarySpaceHeater;

	/**
	 * The cached value of the '{@link #getSecondarySpaceHeater() <em>Secondary Space Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondarySpaceHeater()
	 * @generated
	 * @ordered
	 */
	protected IRoomHeater secondarySpaceHeater;

	/**
	 * The cached value of the '{@link #getCentralWaterSystem() <em>Central Water System</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCentralWaterSystem()
	 * @generated
	 * @ordered
	 */
	protected ICentralWaterSystem centralWaterSystem;

	/**
	 * The cached value of the '{@link #getSecondaryWaterHeater() <em>Secondary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryWaterHeater()
	 * @generated
	 * @ordered
	 */
	protected IWaterHeater secondaryWaterHeater;

	/**
	 * The cached value of the '{@link #getCommunityHeatSource() <em>Community Heat Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommunityHeatSource()
	 * @generated
	 * @ordered
	 */
	protected ICommunityHeatSource communityHeatSource;

	/**
	 * The cached value of the '{@link #getSolarPhotovoltaic() <em>Solar Photovoltaic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolarPhotovoltaic()
	 * @generated
	 * @ordered
	 */
	protected ISolarPhotovoltaic solarPhotovoltaic;

	/**
	 * The cached value of the '{@link #getAdjusters() <em>Adjusters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdjusters()
	 * @generated
	 * @ordered
	 */
	protected EList<IAdjuster> adjusters;

	/**
	 * The cached value of the '{@link #getShower() <em>Shower</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShower()
	 * @generated
	 * @ordered
	 */
	protected IShower shower;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TechnologyModelImpl() {
		super();
	}

	/**
	 * An adapter which listens to a technology model, and erases its
	 * total operational cost cache when the contents of the model are
	 * changed. It also unhooks itself when the cache is erased, and
	 * is rehooked when the cache is updated in
	 * {#getTotalOperationalCost()}
	 */
	private static final EContentAdapter cacheEraser = new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				// if the notification regards a change to an EObject
				// and it's not a touch (i.e. it is relevant to the
				// value of the object) then we search up the tree
				// until we find the technology model then clear the
				// cached value, and then remove ourself from the
				// adapter list.
				if (notification.isTouch() == false &&
					notification.getNotifier() instanceof EObject) {
					EObject obj = (EObject) notification.getNotifier();
					while (obj != null && !(obj instanceof TechnologyModelImpl)) {
						obj = obj.eContainer();
					}
					if (obj instanceof TechnologyModelImpl) {
						final TechnologyModelImpl tm = (TechnologyModelImpl) obj;
						tm.operationalCostCache = null;
						tm.eAdapters().removeAll(Collections.singleton(this));
					}
				}
			}
		};

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.TECHNOLOGY_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAppliance> getAppliances() {
		if (appliances == null) {
			appliances = new EObjectContainmentEList<IAppliance>(IAppliance.class, this, ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES);
		}
		return appliances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ILight> getLights() {
		if (lights == null) {
			lights = new EObjectContainmentEList<ILight>(ILight.class, this, ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS);
		}
		return lights;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IIndividualHeatSource getIndividualHeatSource() {
		return individualHeatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndividualHeatSource(IIndividualHeatSource newIndividualHeatSource, NotificationChain msgs) {
		IIndividualHeatSource oldIndividualHeatSource = individualHeatSource;
		individualHeatSource = newIndividualHeatSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE, oldIndividualHeatSource, newIndividualHeatSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndividualHeatSource(IIndividualHeatSource newIndividualHeatSource) {
		if (newIndividualHeatSource != individualHeatSource) {
			NotificationChain msgs = null;
			if (individualHeatSource != null)
				msgs = ((InternalEObject)individualHeatSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE, null, msgs);
			if (newIndividualHeatSource != null)
				msgs = ((InternalEObject)newIndividualHeatSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE, null, msgs);
			msgs = basicSetIndividualHeatSource(newIndividualHeatSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE, newIndividualHeatSource, newIndividualHeatSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ICooker> getCookers() {
		if (cookers == null) {
			cookers = new EObjectContainmentEList<ICooker>(ICooker.class, this, ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS);
		}
		return cookers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPrimarySpaceHeater getPrimarySpaceHeater() {
		return primarySpaceHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimarySpaceHeater(IPrimarySpaceHeater newPrimarySpaceHeater, NotificationChain msgs) {
		IPrimarySpaceHeater oldPrimarySpaceHeater = primarySpaceHeater;
		primarySpaceHeater = newPrimarySpaceHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER, oldPrimarySpaceHeater, newPrimarySpaceHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimarySpaceHeater(IPrimarySpaceHeater newPrimarySpaceHeater) {
		if (newPrimarySpaceHeater != primarySpaceHeater) {
			NotificationChain msgs = null;
			if (primarySpaceHeater != null)
				msgs = ((InternalEObject)primarySpaceHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER, null, msgs);
			if (newPrimarySpaceHeater != null)
				msgs = ((InternalEObject)newPrimarySpaceHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER, null, msgs);
			msgs = basicSetPrimarySpaceHeater(newPrimarySpaceHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER, newPrimarySpaceHeater, newPrimarySpaceHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IRoomHeater getSecondarySpaceHeater() {
		return secondarySpaceHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondarySpaceHeater(IRoomHeater newSecondarySpaceHeater, NotificationChain msgs) {
		IRoomHeater oldSecondarySpaceHeater = secondarySpaceHeater;
		secondarySpaceHeater = newSecondarySpaceHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER, oldSecondarySpaceHeater, newSecondarySpaceHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondarySpaceHeater(IRoomHeater newSecondarySpaceHeater) {
		if (newSecondarySpaceHeater != secondarySpaceHeater) {
			NotificationChain msgs = null;
			if (secondarySpaceHeater != null)
				msgs = ((InternalEObject)secondarySpaceHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER, null, msgs);
			if (newSecondarySpaceHeater != null)
				msgs = ((InternalEObject)newSecondarySpaceHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER, null, msgs);
			msgs = basicSetSecondarySpaceHeater(newSecondarySpaceHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER, newSecondarySpaceHeater, newSecondarySpaceHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralWaterSystem getCentralWaterSystem() {
		return centralWaterSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCentralWaterSystem(ICentralWaterSystem newCentralWaterSystem, NotificationChain msgs) {
		ICentralWaterSystem oldCentralWaterSystem = centralWaterSystem;
		centralWaterSystem = newCentralWaterSystem;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM, oldCentralWaterSystem, newCentralWaterSystem);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCentralWaterSystem(ICentralWaterSystem newCentralWaterSystem) {
		if (newCentralWaterSystem != centralWaterSystem) {
			NotificationChain msgs = null;
			if (centralWaterSystem != null)
				msgs = ((InternalEObject)centralWaterSystem).eInverseRemove(this, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__TECHNOLOGY_MODEL, ICentralWaterSystem.class, msgs);
			if (newCentralWaterSystem != null)
				msgs = ((InternalEObject)newCentralWaterSystem).eInverseAdd(this, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__TECHNOLOGY_MODEL, ICentralWaterSystem.class, msgs);
			msgs = basicSetCentralWaterSystem(newCentralWaterSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM, newCentralWaterSystem, newCentralWaterSystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWaterHeater getSecondaryWaterHeater() {
		return secondaryWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondaryWaterHeater(IWaterHeater newSecondaryWaterHeater, NotificationChain msgs) {
		IWaterHeater oldSecondaryWaterHeater = secondaryWaterHeater;
		secondaryWaterHeater = newSecondaryWaterHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER, oldSecondaryWaterHeater, newSecondaryWaterHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondaryWaterHeater(IWaterHeater newSecondaryWaterHeater) {
		if (newSecondaryWaterHeater != secondaryWaterHeater) {
			NotificationChain msgs = null;
			if (secondaryWaterHeater != null)
				msgs = ((InternalEObject)secondaryWaterHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER, null, msgs);
			if (newSecondaryWaterHeater != null)
				msgs = ((InternalEObject)newSecondaryWaterHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER, null, msgs);
			msgs = basicSetSecondaryWaterHeater(newSecondaryWaterHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER, newSecondaryWaterHeater, newSecondaryWaterHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICommunityHeatSource getCommunityHeatSource() {
		return communityHeatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommunityHeatSource(ICommunityHeatSource newCommunityHeatSource, NotificationChain msgs) {
		ICommunityHeatSource oldCommunityHeatSource = communityHeatSource;
		communityHeatSource = newCommunityHeatSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE, oldCommunityHeatSource, newCommunityHeatSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommunityHeatSource(ICommunityHeatSource newCommunityHeatSource) {
		if (newCommunityHeatSource != communityHeatSource) {
			NotificationChain msgs = null;
			if (communityHeatSource != null)
				msgs = ((InternalEObject)communityHeatSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE, null, msgs);
			if (newCommunityHeatSource != null)
				msgs = ((InternalEObject)newCommunityHeatSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE, null, msgs);
			msgs = basicSetCommunityHeatSource(newCommunityHeatSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE, newCommunityHeatSource, newCommunityHeatSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISolarPhotovoltaic getSolarPhotovoltaic() {
		return solarPhotovoltaic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSolarPhotovoltaic(ISolarPhotovoltaic newSolarPhotovoltaic, NotificationChain msgs) {
		ISolarPhotovoltaic oldSolarPhotovoltaic = solarPhotovoltaic;
		solarPhotovoltaic = newSolarPhotovoltaic;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC, oldSolarPhotovoltaic, newSolarPhotovoltaic);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolarPhotovoltaic(ISolarPhotovoltaic newSolarPhotovoltaic) {
		if (newSolarPhotovoltaic != solarPhotovoltaic) {
			NotificationChain msgs = null;
			if (solarPhotovoltaic != null)
				msgs = ((InternalEObject)solarPhotovoltaic).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC, null, msgs);
			if (newSolarPhotovoltaic != null)
				msgs = ((InternalEObject)newSolarPhotovoltaic).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC, null, msgs);
			msgs = basicSetSolarPhotovoltaic(newSolarPhotovoltaic, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC, newSolarPhotovoltaic, newSolarPhotovoltaic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAdjuster> getAdjusters() {
		if (adjusters == null) {
			adjusters = new EObjectContainmentEList<IAdjuster>(IAdjuster.class, this, ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS);
		}
		return adjusters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IShower getShower() {
		return shower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShower(IShower newShower, NotificationChain msgs) {
		IShower oldShower = shower;
		shower = newShower;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER, oldShower, newShower);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShower(IShower newShower) {
		if (newShower != shower) {
			NotificationChain msgs = null;
			if (shower != null)
				msgs = ((InternalEObject)shower).eInverseRemove(this, ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL, IShower.class, msgs);
			if (newShower != null)
				msgs = ((InternalEObject)newShower).eInverseAdd(this, ITechnologiesPackage.SHOWER__TECHNOLOGY_MODEL, IShower.class, msgs);
			msgs = basicSetShower(newShower, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER, newShower, newShower));
	}

	private transient Double operationalCostCache = null;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTotalOperationalCost() {
		if (operationalCostCache == null) {
			final TreeIterator<EObject> children = eAllContents();
			double accumulator = 0;
			while (children.hasNext()) {
				final EObject child = children.next();
				if (child instanceof IOperationalCost) {
					accumulator += ((IOperationalCost)child).getAnnualOperationalCost();
				}
			}
			operationalCostCache = accumulator;
			eAdapters().add(cacheEraser);
		}
				
		return operationalCostCache;
	}

		/**
	 * <!-- begin-user-doc -->
	 * Returns the heat proportion generated by the secondary heater based on the primary heater type, using SAP Table 11.
	 * 
	 * We have assumed that the storage heater "not fan-assisted" row refers to slimline and large volume heaters. 
	 * 
	 * We don't have sufficient data to implement appendix N for heat pumps and micro co-generation, 
	 * so we use the "Heat pump, data from Table 4a" entry for those.
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	private double SAPTable11(ISpaceHeater primaryHeater) {
		if (primaryHeater instanceof IRoomHeater) {
			if (((IRoomHeater) primaryHeater).getFuel() == FuelType.ELECTRICITY) {
				return 0.2;
			}
		} else if (primaryHeater instanceof IStorageHeater) {
			StorageHeaterType type = ((IStorageHeater) primaryHeater).getType();
			
			if (type == StorageHeaterType.OLD_LARGE_VOLUME || type == StorageHeaterType.SLIMLINE) {
				return 0.15;
			}
		}
		
		return 0.1;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Based on Appendix A, section A1 of SAP.
	 * 
	 * Storage heaters (unless integrated) and off-peak underfloor electric always requires a secondary heater.
	 * 
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	private boolean alwaysIncludeSecondaryHeater(ISpaceHeater primaryHeater) {
		// We haven't implemented "integrated storage + direct acting" or "Electric Underfloor Heating", so we just check for any storage heater.
		return primaryHeater instanceof IStorageHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This overridden version of the accept method looks at all the contents of this technology
	 * model, and if any of them are also instances of {@link IVisitorAccepter} calls their accept
	 * method.
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public void accept(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, final AtomicInteger counter, IHeatProportions heatProportions) {
		visitor.visitEnergyTransducer(new HotWaterDemand09(constants, getShower()));
		
		TreeIterator<EObject> eAllContents = this.eAllContents();
		
		heatProportions = getHeatProportions();
		
		while (eAllContents.hasNext()) {
			final EObject o = eAllContents.next();
			if (o instanceof IVisitorAccepter) {
				((IVisitorAccepter) o).accept(constants, parameters, visitor, counter, heatProportions);
			}
		}
		
		if (heatProportions.spaceHeatingProportion(assumedElectricSpaceHeater) > 0.0) {
			assumedElectricSpaceHeater.accept(constants, parameters, visitor, counter, heatProportions);
		}
		
		if (heatProportions.providesHotWater(assumedElectricWaterHeater)) {
			assumedElectricWaterHeater.accept(constants, parameters, visitor, counter, heatProportions);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				if (centralWaterSystem != null)
					msgs = ((InternalEObject)centralWaterSystem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM, null, msgs);
				return basicSetCentralWaterSystem((ICentralWaterSystem)otherEnd, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				if (shower != null)
					msgs = ((InternalEObject)shower).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER, null, msgs);
				return basicSetShower((IShower)otherEnd, msgs);
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
			case ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES:
				return ((InternalEList<?>)getAppliances()).basicRemove(otherEnd, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS:
				return ((InternalEList<?>)getLights()).basicRemove(otherEnd, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE:
				return basicSetIndividualHeatSource(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS:
				return ((InternalEList<?>)getCookers()).basicRemove(otherEnd, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER:
				return basicSetPrimarySpaceHeater(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER:
				return basicSetSecondarySpaceHeater(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				return basicSetCentralWaterSystem(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER:
				return basicSetSecondaryWaterHeater(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE:
				return basicSetCommunityHeatSource(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC:
				return basicSetSolarPhotovoltaic(null, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS:
				return ((InternalEList<?>)getAdjusters()).basicRemove(otherEnd, msgs);
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				return basicSetShower(null, msgs);
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
			case ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES:
				return getAppliances();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS:
				return getLights();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE:
				return getIndividualHeatSource();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS:
				return getCookers();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER:
				return getPrimarySpaceHeater();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER:
				return getSecondarySpaceHeater();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				return getCentralWaterSystem();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER:
				return getSecondaryWaterHeater();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE:
				return getCommunityHeatSource();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC:
				return getSolarPhotovoltaic();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS:
				return getAdjusters();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				return getShower();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES:
				getAppliances().clear();
				getAppliances().addAll((Collection<? extends IAppliance>)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS:
				getLights().clear();
				getLights().addAll((Collection<? extends ILight>)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE:
				setIndividualHeatSource((IIndividualHeatSource)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS:
				getCookers().clear();
				getCookers().addAll((Collection<? extends ICooker>)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER:
				setPrimarySpaceHeater((IPrimarySpaceHeater)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER:
				setSecondarySpaceHeater((IRoomHeater)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				setCentralWaterSystem((ICentralWaterSystem)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER:
				setSecondaryWaterHeater((IWaterHeater)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE:
				setCommunityHeatSource((ICommunityHeatSource)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC:
				setSolarPhotovoltaic((ISolarPhotovoltaic)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS:
				getAdjusters().clear();
				getAdjusters().addAll((Collection<? extends IAdjuster>)newValue);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				setShower((IShower)newValue);
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
			case ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES:
				getAppliances().clear();
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS:
				getLights().clear();
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE:
				setIndividualHeatSource((IIndividualHeatSource)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS:
				getCookers().clear();
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER:
				setPrimarySpaceHeater((IPrimarySpaceHeater)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER:
				setSecondarySpaceHeater((IRoomHeater)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				setCentralWaterSystem((ICentralWaterSystem)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER:
				setSecondaryWaterHeater((IWaterHeater)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE:
				setCommunityHeatSource((ICommunityHeatSource)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC:
				setSolarPhotovoltaic((ISolarPhotovoltaic)null);
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS:
				getAdjusters().clear();
				return;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				setShower((IShower)null);
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
			case ITechnologiesPackage.TECHNOLOGY_MODEL__APPLIANCES:
				return appliances != null && !appliances.isEmpty();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__LIGHTS:
				return lights != null && !lights.isEmpty();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE:
				return individualHeatSource != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COOKERS:
				return cookers != null && !cookers.isEmpty();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER:
				return primarySpaceHeater != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER:
				return secondarySpaceHeater != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM:
				return centralWaterSystem != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER:
				return secondaryWaterHeater != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE:
				return communityHeatSource != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC:
				return solarPhotovoltaic != null;
			case ITechnologiesPackage.TECHNOLOGY_MODEL__ADJUSTERS:
				return adjusters != null && !adjusters.isEmpty();
			case ITechnologiesPackage.TECHNOLOGY_MODEL__SHOWER:
				return shower != null;
		}
		return super.eIsSet(featureID);
	}
	
	@Override
	public ITechnologyModel copy() {
		return EcoreUtil.copy(this);
	}

	/**
	 * 
	 */
	@Override
	public IHeatProportions getHeatProportions() {
		ImmutableMap.Builder<ISpaceHeater, Double> spaceHeatProportionsBuilder = ImmutableMap.builder();

		// Space heating
		// We split the heat demand between heaters based on SAP table 11 and Appendix A. 
		boolean primaryHeaterExists = getPrimarySpaceHeater() != null && !getPrimarySpaceHeater().isBroken();
		boolean secondaryHeaterExists = getSecondarySpaceHeater() != null && !getSecondarySpaceHeater().isBroken();
		boolean includeSecondaryHeater = secondaryHeaterExists || primaryHeaterExists && alwaysIncludeSecondaryHeater(getPrimarySpaceHeater());
		
		/*
		BEISDOC
		NAME: Space Heating Fraction
		DESCRIPTION: The fraction of heat demand which will be met by each heating system.
		TYPE: algorithm/lookup
		UNIT: Dimensionless
		SAP: (201,202,204), Table 11, Appendix A
		BREDEM: User input (frsys1,frsys2,...)
		NOTES: Does not implement SAP Table N9 secondary fraction for heat pumps.
		ID: space-heating-fraction
		CODSIEB
		*/
		if (primaryHeaterExists) {
			if (includeSecondaryHeater) {
				ISpaceHeater secondaryHeater = secondaryHeaterExists ? getSecondarySpaceHeater() : assumedElectricSpaceHeater; 
				
				// primary and secondary heaters exist: lookup in table 11
				double secondaryHeatFraction = SAPTable11(getPrimarySpaceHeater());
				spaceHeatProportionsBuilder.put(getPrimarySpaceHeater(), 1 - secondaryHeatFraction);
				spaceHeatProportionsBuilder.put(secondaryHeater, secondaryHeatFraction);

			} else {
				// primary heater exists, missing secondary heater: 100% primary heater
				spaceHeatProportionsBuilder.put(getPrimarySpaceHeater(), 1.0);
			}
		} else {
			if (secondaryHeaterExists) {
				// missing primary heater, secondary heater exists: assume 90% electric, 10% secondary heater
				// (looked up in table 11 as 'Other Electric Systems')
				spaceHeatProportionsBuilder.put(assumedElectricSpaceHeater, 0.9);
				spaceHeatProportionsBuilder.put(getSecondarySpaceHeater(), 0.1);
					
			} else {
				// no heaters: assume 100% electric
				spaceHeatProportionsBuilder.put(assumedElectricSpaceHeater, 1.0);	
			}
		}
		
		/*
		BEISDOC
		NAME: Is Main Water Heater
		DESCRIPTION: Only the main water heating system contributes hot water.
		TYPE: formula
		UNIT: True/False
		SAP: (217)
		NOTES: SAP allows for two main water heating systems. We do not support this.
		NOTES: We do allow for secondary water heating systems, but they will only supply hot water if a main water heating system is not present or is not functioning.
		NOTES: BREDEM computes a fraction based on volume or water heated and temperature rise. We do not implement this as we do not have the information.
		NOTES: Solar water heating is dealt with separately.
		ID: is-main-weater-heating
		CODSIEB
		*/
		// Water
		// Solar thermal happens first - we don't deal with that here.
		// Other systems are either used completely or not at all.
		final IWaterHeater waterHeater;
		
		if (getCentralWaterSystem() != null && !getCentralWaterSystem().isBroken()) {
			waterHeater = getCentralWaterSystem();
			
		} else if (getSecondaryWaterHeater() != null && !getSecondaryWaterHeater().isBroken()) {
			waterHeater = getSecondaryWaterHeater();
			 
		} else {
			waterHeater = assumedElectricWaterHeater;
		}
		
		final ImmutableMap<ISpaceHeater, Double> spaceHeatProportions = spaceHeatProportionsBuilder.build();
		
		return new IHeatProportions() {

			@Override
			public double spaceHeatingProportion(ISpaceHeater heatSource) {
				if (spaceHeatProportions.containsKey(heatSource)) {
					return spaceHeatProportions.get(heatSource);
				} else {
					return 0.0;
				}	
			}

			@Override
			public boolean providesHotWater(IWaterHeater heatSource) {
				return heatSource == waterHeater;
			}
		};
	}
} //TechnologyModelImpl
