package com.wurmonline.server.spells;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Server;
import com.wurmonline.server.TimeConstants;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.shared.constants.ProtoConstants;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ausimus
 *
 * Spells are package private, so you will need to place them in a package matching the file structure that of server.jar
 * that being spells.
 * @see com.wurmonline.server.spells
 *
 * This spell targets an item, but this could be a tile, border, fence, wall, creature, and so on.
 * @see Spell
 */
public class ExampleSpell extends ReligiousSpell
{
    private static Logger logger = Logger.getLogger(ExampleSpell.class.getName());

    public ExampleSpell()
    {
        super(
                // The spell name.
                "Example Spell",
                // The actionId for the spell.
                ModActions.getNextActionId(),
                // How long it takes to cast the spell.
                10,
                // How much favor it costs to cast this spell.
                10,
                // How hard this spell is to cast.
                10,
                // What faith skill you have to have to cast this spell.
                30,
                // How long is the cooldown for this spell is, in this case 30 seconds.
                TimeConstants.SECOND_MILLIS * 30);
        // Does this spell target an item? Can also be Creature, Weapon, Armour, Jewelry, Pendulum, Wound, Tile, TileBorder.
        targetItem = true;
        // The spell description as seen on an altar spell list.
        description = "Example spell that creates some ash in a target container.";
        // Action registry.
        ActionEntry actionEntry = ActionEntry.createEntry(
                // The actionId
                (short) number,
                // The action name
                name,
                // Verb
                "enchanting",
                // Action type.
                new int[]{
                        Actions.ACTION_TYPE_SPELL,
                        Actions.ACTION_TYPE_ALWAYS_USE_ACTIVE_ITEM,
                        Actions.ACTION_TYPE_ENEMY_ALWAYS
                });
        // Register the above action.
        ModActions.registerAction(actionEntry);
    }

    /**
     * These are the preconditions to be able to cast the skill.
     *
     * @param castSkill The skill to cast.
     * @param performer The player casting the spell.
     * @param target    The item target for the spell.
     */
    boolean precondition(Skill castSkill, Creature performer, Item target)
    {
        // We are creating ash and placing it into a container,
        // so lets make sure the performer can only cast it onto a hollow item.
        if (!target.isHollow())
        {
            performer.getCommunicator().sendNormalServerMessage(
                    "You can only cast this on a hollow item", ProtoConstants.M_FAIL);
            return false;
        }
        return true;
    }

    /**
     * doEffect handles the spell logic.
     * In this example we are just creating some ash into a hollow container.
     * The hollow container restriction is handled in preCondition.
     *
     * @param castSkill The skill to cast.
     * @param power     The power of the cast.
     * @param performer The player casting the spell.
     * @param target    The item target for the spell.
     * @see ExampleSpell#precondition(com.wurmonline.server.skills.Skill, com.wurmonline.server.creatures.Creature, com.wurmonline.server.items.Item)
     */
    void doEffect(Skill castSkill, double power, Creature performer, Item target)
    {
        try
        {
            for (int count = 0; count < 4; count++)
            {
                Item newItem = ItemFactory.createItem(
                        // What we are creating
                        ItemList.ash,
                        // Ql of the item, in this example the cast power.
                        (float) power,
                        // Rarity of the item, in this example random.
                        (byte) Server.rand.nextInt(FANTASTIC),
                        // The creators name.
                        performer.getName());
                // Insert the items into the target item.
                target.insertItem(newItem);
            }
            performer.getCommunicator().sendNormalServerMessage(
                    "You create some ash.", ProtoConstants.M_FAIL);
        }
        catch (NoSuchTemplateException | FailedException ex)
        {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}