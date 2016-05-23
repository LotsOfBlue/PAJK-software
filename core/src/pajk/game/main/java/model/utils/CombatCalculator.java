package pajk.game.main.java.model.utils;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.items.Bow;
import pajk.game.main.java.model.items.Tome;
import pajk.game.main.java.model.units.Unit;

/**
 * @author Jonatan
 */
public class CombatCalculator {
    /**
     * Calculates the damage attackerUnit would do to defenderUnit with a normal attack (crit and miss exluded).
     * Takes attacker weaponDamage, might or strength, weaponAdvantage & defender resistance or defence into consideration.
     * @param attackerUnit the unit performing the attack.
     * @param defenderUnit the unit being attacked.
     * @return the damage that attacker will deal (if the attack hit)
     */
    public static int calcDamageThisToThat(Unit attackerUnit, Unit defenderUnit) {
        int damage;
        if (attackerUnit.getWeapon() instanceof Tome) {
            damage = attackerUnit.getWeapon().getDamage()
                    + attackerUnit.getStrength()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getResistance();
        } else {
            damage = attackerUnit.getWeapon().getDamage()
                    + attackerUnit.getStrength()
                    + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                    - defenderUnit.getDefence();
        }
        //The lower bound of damage is 0
        if (damage < 0) {
            return 0;
        } else {
            return damage;
        }
    }

    /**
     * This method calculates the hit chance the attacker has on the defender.
     * Takes attacker weapon accuracy, skill and weapon advantage vs defender speed & terrain evasion bonus
     * @param attackerUnit is the unit trying to hit
     * @param defenderUnit is the unit being hit
     * @param board is the board that the units are on
     * @return the chance (in percent) of the attacker to hit the defender
     */
    public static int getHitChance(Unit attackerUnit, Unit defenderUnit, Board board){
        int chance = (attackerUnit.getWeapon().getAccuracy()
                + attackerUnit.getSkill()
                + getWeaponAdvantageThisToThat(attackerUnit, defenderUnit)
                - defenderUnit.getSpeed()
                - board.getPos(defenderUnit).getEvasion());
        if(chance < 0){
            return 0;
        } else {
            return chance;
        }
    }

    /**
     * This method calculates the crit chance the attacker has on the defender
     * Takes the attacker weapon crit chance, skill and luck vs defender luck.
     * @param attackerUnit is the unit hitting
     * @param defenderUnit is the unit being hit
     * @return the chance (in percent) of the attacker to crit the defender
     */
    public static int getCritChance(Unit attackerUnit, Unit defenderUnit){
        int chance = (attackerUnit.getWeapon().getCritChance()
                + attackerUnit.getSkill()
                + attackerUnit.getLuck()
                - defenderUnit.getLuck());
        if(chance < 0){
            return 0;
        } else {
            return chance;
        }
    }


    /**
     * This method calculates the weapon advantage the attacker has on the defender, wich can be posetive and negative.
     * Works as axe beats pike beats sword beats axe, bow beats flying and magic beats magic.
     * @param attackerUnit is the unit hitting
     * @param defenderUnit is the unit being hit
     * @return the advantage the attacker has on the defender (+ & -)
     */

    public static int getWeaponAdvantageThisToThat(Unit attackerUnit, Unit defenderUnit) {
        //Bow types has inate advantage against flying
        if(attackerUnit.getWeapon() instanceof Bow && defenderUnit.getMovementType() == Unit.MovementType.FLYING){
            return 5;
        }
        //ask for active unit weapon bonus vs defender weapon
        return attackerUnit.getWeapon().getAdvantageModifier(defenderUnit.getWeapon());

    }
}
