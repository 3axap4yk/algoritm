package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {

    private final PrintBattleLog printBattleLog = new PrintBattleLogImpl();

    private boolean hasAliveUnits(Army army) {
        for (Unit unit : army.getUnits()) {
            if (unit.isAlive()) return true;
        }
        return false;
    }

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {

        while (hasAliveUnits(playerArmy) && hasAliveUnits(computerArmy)) {

            List<Unit> queue = new ArrayList<>();

            for (Unit u : playerArmy.getUnits()) if (u.isAlive()) queue.add(u);
            for (Unit u : computerArmy.getUnits()) if (u.isAlive()) queue.add(u);

            queue.sort((a, b) -> Integer.compare(b.getBaseAttack(), a.getBaseAttack()));

            for (Unit unit : queue) {
                if (!unit.isAlive()) continue;

                Unit target = unit.getProgram().attack();

                if (target != null) {
                    printBattleLog.printBattleLog(unit, target);
                }

                if (!hasAliveUnits(playerArmy) || !hasAliveUnits(computerArmy)) {
                    return;
                }
            }
        }
    }
}
