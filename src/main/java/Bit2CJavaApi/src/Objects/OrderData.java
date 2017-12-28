/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bit2CJavaApi.src.Objects;

import Bit2CJavaApi.src.Enums.PairType;

import java.math.BigDecimal;
/**
 *
 * @author AMiT
 */
public class OrderData {
    public BigDecimal Amount;
    public BigDecimal Price;
    public BigDecimal Total;
    public boolean IsBid;
    public PairType Pair;
}
