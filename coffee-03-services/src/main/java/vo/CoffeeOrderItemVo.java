package vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoffeeOrderItemVo {
    private Integer coffeeTypeId;
    private String coffeeTypeName;
    private double coffeePrice;
    private int quantity;
}
