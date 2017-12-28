/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bit2CJavaApi.src.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AMiT
 */
public class NewOrders {
    public List<OrderData> bids;
    public List<OrderData> asks;
    
    public NewOrders(){
        bids = new ArrayList<OrderData>();
        asks = new ArrayList<OrderData>();
    }
}
