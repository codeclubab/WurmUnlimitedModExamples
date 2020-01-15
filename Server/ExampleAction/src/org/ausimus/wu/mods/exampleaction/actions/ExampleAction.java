package org.ausimus.wu.mods.exampleaction.actions;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTypes;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

/**
 * @author Ausimus
 *
 * An example custom action mod.
 * This action uses a target item, but this could be a tile, border, fence, wall, creature, and so on.
 * @see BehaviourProvider
 * @see ActionPerformer
 */
public class ExampleAction implements ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{

    private static short actionId;
    private static ActionEntry actionEntry;

    public ExampleAction()
    {
        // The actionId. We can just let the modLoader generate one for us. Note this value is dynamic and can change.
        actionId = (short) ModActions.getNextActionId();
        // The actionEntry. This is the action we are registering include the id, name, verb, and types.
        actionEntry = ActionEntry.createEntry(actionId, "Example Action", "Exampling", new int[]{});
        // The registration of the above actionEntry.
        ModActions.registerAction(actionEntry);
    }

    /**
     * @return This.
     * @see ModAction#getBehaviourProvider()
     */
    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    /**
     * @return This
     * @see ModAction#getActionPerformer()
     */
    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

    /**
     * @return the actionId.
     * @see ExampleAction#actionId
     */
    @Override
    public short getActionId()
    {
        return actionId;
    }

    /**
     * This is where the custom action gets its generic behaviour.
     *
     * @param performer The player performing the action.
     * @param source    The item source (activated item).
     * @param target    The item target.
     * @return return value.
     * @see BehaviourProvider#getBehavioursFor(com.wurmonline.server.creatures.Creature, com.wurmonline.server.items.Item, com.wurmonline.server.items.Item)
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        return getBehavioursFor(performer, target);
    }

    /**
     * Same as above but without an item source.
     *
     * @param performer The player performing the action.
     * @param target    The item target.
     * @return return value.
     * @see BehaviourProvider#getBehavioursFor(com.wurmonline.server.creatures.Creature, com.wurmonline.server.items.Item)
     */
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target)
    {
        if (!performer.isOnSurface() && target.getTemplateId() == ItemList.traderContract)
            return Collections.singletonList(actionEntry);
        else
            return null;
    }

    /**
     * @param act       The Action class associated with this action.
     * @param performer The player performing the action.
     * @param target    The target item.
     * @param action    The action id.
     * @param counter   The action timer.
     * @return boolean return value.
     */
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        // If we wanted to have a source we could just stop here.
        if (source != null && target.getTemplateId() == ItemList.bodyBody)
        {
            performer.getCommunicator().sendNormalServerMessage("This is the body of " + performer.getName() +
                    " you have " + source.getNameWithGenus() + " activated.");
            return true;
        }
        // Or if we do not have a source continue to the forward action.
        return action(act, performer, target, action, counter);
    }

    /**
     * @param act       The Action class associated with this action.
     * @param performer The player performing the action.
     * @param target    The target item.
     * @param action    The action id.
     * @param counter   The action timer.
     * @return boolean return value.
     */
    @Override
    public boolean action(Action act, Creature performer, Item target, short action, float counter)
    {
        if (target.getTemplateId() == ItemList.bodyBody)
        {
            performer.getCommunicator().sendNormalServerMessage("This is the body of " + performer.getName() + ".");
        }
        return true;
    }
}