package org.ausimus.wu.mods.examplespell;

import com.wurmonline.server.deities.Deity;
import com.wurmonline.server.spells.ExampleSpell;
import com.wurmonline.server.spells.Spells;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wurmonline.server.deities.Deities.*;

/**
 * @author Ausimus
 *
 * * THIS MOD IS AN EXAMPLE!!!
 *
 * This the the main class of an spell example mod for Wurm Unlimited (Server).
 *
 * This is a server mod, so lets implement WurmServerMod.
 * @see  WurmServerMod
 *
 * This being a custom spell requires us to also implement Initable and ServerStartedListener.
 * This mod has a lot in common with actions (which a spell is), just with some extra logic to handle spell specific stuff.
 *
 * @see Initable
 * @see ServerStartedListener
 * We also need to Override the super for these methods.
 * @see Override
 */
public class Initiator implements WurmServerMod, Initable, ServerStartedListener
{
    private static Logger logger = Logger.getLogger(Initiator.class.getName());

    /**
     * Used for initialization. In this example we are using it to initialize ModActions.
     * Thou this is a spell, it is still an action at heart.
     * @see ModActions#init()
     */
    @Override
    public void init()
    {
        ModActions.init();
    }

    /**
     * Fired when the server starts. In this example we are using it to register the spell.
     */
    @Override
    public void onServerStarted()
    {
        // Instantiate the spell.
        ExampleSpell exampleSpell = new ExampleSpell();
        try
        {
            // We can use the ReflectionUtil to register the spell into the spell list.
            ReflectionUtil.callPrivateMethod(Spells.class, ReflectionUtil.getMethod(
                    Spells.class, "addSpell"), exampleSpell);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
        // Now we need to tell the server what gods should have this spell.
        for (Deity deity : getDeities())
        {
            // In this example we are just adding it to Mag.
            if (deity.number == DEITY_MAGRANON)
                deity.addSpell(exampleSpell);
        }
    }
}
