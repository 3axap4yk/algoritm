package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> result = new ArrayList<>();

        // для каждого ряда
        for (List<Unit> row : unitsByRow) {
            // быстрый доступ по координате y
            Map<Integer, Unit> unitsByY = new HashMap<>();

            for (Unit unit : row) {
                unitsByY.put(unit.getyCoordinate(), unit);
            }

            // проверяем каждого юнита
            for (Unit unit : row) {
                int y = unit.getyCoordinate();

                // если цель — левая армия, значит проверяем слева
                if (isLeftArmyTarget) {
                    if (!unitsByY.containsKey(y - 1)) {
                        result.add(unit);
                    }
                }
                // иначе цель — правая армия, зачит проверяем справа
                else {
                    if (!unitsByY.containsKey(y + 1)) {
                        result.add(unit);
                    }
                }
            }
        }

        return result;
    }
}
