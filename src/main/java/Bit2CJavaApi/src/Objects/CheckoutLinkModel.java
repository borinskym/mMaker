/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bit2CJavaApi.src.Objects;

import Bit2CJavaApi.src.Enums.CoinType;

import java.math.BigDecimal;

/**
 *
 * @author AMiT
 */
public class CheckoutLinkModel {
    public BigDecimal Price;
    public String Description;
    public Bit2CJavaApi.src.Enums.CoinType CoinType;
    public String ReturnURL;
    public String CancelURL;
    public boolean NotifyByEmail;
}
