package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;

public class PrintBattleLogImpl implements PrintBattleLog {

    @Override
    public void printBattleLog(Unit attacker, Unit target) {
        String msg = attacker.getName()
                + " атакует "
                + target.getName()
                + " (HP: " + target.getHealth() + ")";
        System.out.println(msg);

        System.out.println(
                attacker.getName()
                        + " атакует "
                        + target.getName()
                        + ", HP цели: "
                        + target.getHealth()
        );
    }
}
