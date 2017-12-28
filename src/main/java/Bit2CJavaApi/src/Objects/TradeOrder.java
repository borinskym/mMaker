/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bit2CJavaApi.src.Objects;


import Bit2CJavaApi.src.Enums.OrderStatusType;
import Bit2CJavaApi.src.Enums.PairType;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author AMiT
 */
public class TradeOrder {
    public BigDecimal a;
    public String d;
    public long id;
    public BigDecimal p;
    public PairType pair;
    public boolean isBid;
    public OrderStatusType s;
}
