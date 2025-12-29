package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    private static final int MAX_PER_TYPE = 11;

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {

        List<Unit> result = new ArrayList<>();
        Map<String, Integer> typeCount = new HashMap<>();

        int points = 0;
        int y = 0;

        // сортировка по эффективности
        unitList.sort((a, b) ->
                Double.compare(
                        (double) b.getBaseAttack() / b.getCost(),
                        (double) a.getBaseAttack() / a.getCost()
                )
        );

        for (Unit template : unitList) {

            String type = template.getUnitType();
            int count = typeCount.getOrDefault(type, 0);

            if (count >= MAX_PER_TYPE) continue;
            if (points + template.getCost() > maxPoints) continue;

            String name = type + " " + (count + 1);

            Unit unit = new Unit(
                    name,
                    template.getUnitType(),
                    template.getHealth(),
                    template.getBaseAttack(),
                    template.getCost(),
                    template.getAttackType(),
                    template.getAttackBonuses(),
                    template.getDefenceBonuses(),
                    0,   // x — всегда 0
                    y++  // y — уникальный
            );

            unit.setAlive(true);
            result.add(unit);

            points += unit.getCost();
            typeCount.put(type, count + 1);
        }

        Army army = new Army();
        army.setUnits(result);
        army.setPoints(points);

        return army;
    }
}
