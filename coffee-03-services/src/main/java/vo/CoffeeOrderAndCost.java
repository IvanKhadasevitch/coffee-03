package vo;

import entities.CoffeeOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoffeeOrderAndCost {
    private CoffeeOrder coffeeOrder;
    private Cost cost;
}
