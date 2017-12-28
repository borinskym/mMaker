/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bit2CJavaApi.src.Objects;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author AMiT
 */
public class OrderBook {
    public List<List<BigDecimal>> asks;
    public List<List<BigDecimal>> bids;
}
