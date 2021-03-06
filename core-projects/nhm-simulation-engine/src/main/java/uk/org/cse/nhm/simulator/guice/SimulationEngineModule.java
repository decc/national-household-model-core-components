package uk.org.cse.nhm.simulator.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

import uk.org.cse.nhm.simulator.IEventQueue;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.factories.IBooleanFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IDefaultFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IFinanceFactory;
import uk.org.cse.nhm.simulator.factories.IGroupFactory;
import uk.org.cse.nhm.simulator.factories.IHookFactory;
import uk.org.cse.nhm.simulator.factories.IHouseValueFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.factories.ISamplerFactory;
import uk.org.cse.nhm.simulator.factories.ITriggerFactory;
import uk.org.cse.nhm.simulator.impl.SimpleEventQueue;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Simulator;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.impl.FixedInterestLoanObligation;
import uk.org.cse.nhm.simulator.reset.IResetFactory;

/**
 * This module configures the DI context for most of the things you would want to use
 * @author hinton
 *
 */
public class SimulationEngineModule extends AbstractModule {
	private static final Class<SimulationScoped> SimulationScope = SimulationScoped.class;

    @Override
	protected void configure() {
//		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
		// bind key singletons
		bind(ISimulator.class).to(Simulator.class).in(SimulationScope);
		
		bind(IEventQueue.class).to(SimpleEventQueue.class).in(SimulationScope);
		
        install(new StateModule());
		
		install(new FactoryModuleBuilder().build(IGroupFactory.class));
		install(new FactoryModuleBuilder().
				build(IActionFactory.class));
		install(new FactoryModuleBuilder().
				implement(IObligation.class, 
						Names.named(IFinanceFactory.FIXED_REPAYMENT), 
						FixedInterestLoanObligation.class).
				build(IFinanceFactory.class));
		install(new FactoryModuleBuilder().build(ISamplerFactory.class));
		install(new FactoryModuleBuilder().build(ITriggerFactory.class));
		install(new FactoryModuleBuilder().build(IBooleanFunctionFactory.class));
		install(new FactoryModuleBuilder().build(IObjectFunctionFactory.class));
		install(new FactoryModuleBuilder().build(IHouseValueFunctionFactory.class));
		install(new FactoryModuleBuilder().build(IResetFactory.class));
		install(new FactoryModuleBuilder().build(IHookFactory.class));
        install(new FactoryModuleBuilder().build(IDefaultFunctionFactory.class));
	}
}
