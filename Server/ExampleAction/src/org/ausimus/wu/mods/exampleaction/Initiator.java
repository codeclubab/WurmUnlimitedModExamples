package org.ausimus.wu.mods.exampleaction;

import org.ausimus.wu.mods.exampleaction.actions.ExampleAction;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

/**
 * @author Ausimus
 *
 * * THIS MOD IS AN EXAMPLE!!!
 *
 * This the the main class of an action example mod for Wurm Unlimited (Server).
 *
 * This is a server mod, so lets implement WurmServerMod.
 * @see  WurmServerMod
 *
 * This being a custom action requires us to also implement Initable and ServerStartedListener
 * @see Initable
 * @see ServerStartedListener
 * We also need to Override the super for these methods.
 * @see Override
 */
public class Initiator implements WurmServerMod, Initable, ServerStartedListener
{
    /**
     * Used for initialization. In this example we are using it to initialize ModActions.
     * @see ModActions#init()
     */
    @Override
    public void init()
    {
        ModActions.init();
    }

    /**
     * Fired when the server starts. In this example we are using it to register the action.
     * @see ModActions#registerAction(org.gotti.wurmunlimited.modsupport.actions.ModAction)
     */
    @Override
    public void onServerStarted()
    {
        ModActions.registerAction(new ExampleAction());
    }
}
