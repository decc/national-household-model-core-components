/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Light</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Lights are things in a house which satisfy the lighting demand produced by the algorithm
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion <em>Proportion</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight()
 * @model
 * @generated
 */
public interface ILight extends INamed, IVisitorAccepter {
	public static final double INCANDESCENT_EFFICIENCY = 6.8139; // watts
	public static final double CFL_EFFICIENCY = INCANDESCENT_EFFICIENCY / 2.0;
	
	/**
	 * Returns the value of the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Efficiency</em>' attribute.
	 * @see #setEfficiency(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight_Efficiency()
	 * @model required="true"
	 * @generated
	 */
	double getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(double value);

	/**
	 * Returns the value of the '<em><b>Proportion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proportion</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fraction of total light demand that this light will emit
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Proportion</em>' attribute.
	 * @see #setProportion(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight_Proportion()
	 * @model required="true"
	 * @generated
	 */
	double getProportion();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion <em>Proportion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proportion</em>' attribute.
	 * @see #getProportion()
	 * @generated
	 */
	void setProportion(double value);

} // ILight