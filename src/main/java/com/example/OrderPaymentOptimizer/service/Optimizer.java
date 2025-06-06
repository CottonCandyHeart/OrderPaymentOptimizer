package com.example.OrderPaymentOptimizer.service;

import com.example.OrderPaymentOptimizer.model.DiscountOption;
import com.example.OrderPaymentOptimizer.model.Order;
import com.example.OrderPaymentOptimizer.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Optimizer {
    private List<Order> orders;
    private List<PaymentMethod> paymentMethods;
    private HashMap<String, List<DiscountOption>> discountOptions;
    private Calc calc;

    public Optimizer(List<Order> orders, List<PaymentMethod> paymentMethods){
        this.orders = orders;
        this.paymentMethods = paymentMethods;
        this.discountOptions = new HashMap<>();
        this.calc = new Calc();
    }


    public boolean optimize(){
        countDiscountsForOrders();
        findBestSolutions();

        return true;
    }

    public HashMap<String, BigDecimal> findBestSolutions(){
        boolean isPaid;

        HashMap<String, BigDecimal> usedLimits = new HashMap<>();
        for (PaymentMethod pm : paymentMethods){
            usedLimits.put(pm.getId(), new BigDecimal("0.00"));
        }

        // for each order
        for (Order o : orders){
            isPaid = false;

            // get available discounts
            List<DiscountOption> discountOptionList = discountOptions.get(o.getId());

            if (discountOptionList == null) continue;

            // search discount list
            for (DiscountOption dcto : discountOptionList){

                // check if it's paid partial
                if (dcto.getPaymentMethodId().equals("MIX")){
                    // check if points can be used
                    PaymentMethod payMeth = paymentMethods.stream()
                            .filter(pm -> pm.getId().equals("PUNKTY"))
                            .findFirst()
                            .orElse(null);

                    BigDecimal substPoints = payMeth.getLimit().subtract(usedLimits.get("PUNKTY"));

                    if (calc.haveEnoughLoyalPoints(dcto.getOriginalValue(), substPoints)){

                        // find if there are other credits
                        for (PaymentMethod pm : paymentMethods){
                            if (!pm.getId().equals("PUNKTY")){

                                // check if full price can be paid
                                BigDecimal substCard = pm.getLimit().subtract(usedLimits.get(pm.getId()));
                                if (calc.haveEnoughMoney(dcto.getOriginalValue(), (substCard.add(substPoints)))){

                                    // substract credits
                                    BigDecimal tempValue = dcto.getDiscountedValue();
                                    tempValue = tempValue.subtract(substPoints);

                                    usedLimits.put("PUNKTY", (usedLimits.get("PUNKTY").add(substPoints)));
                                    usedLimits.put(pm.getId(), (usedLimits.get(pm.getId()).add(tempValue)));


                                    isPaid = true;
                                    break;
                                }
                            }
                        }

                        if (isPaid) break;
                    }
                } else {
                    // find payment method
                    PaymentMethod payMeth = paymentMethods.stream()
                            .filter(pm -> pm.getId().equals(dcto.getPaymentMethodId()))
                            .findFirst()
                            .orElse(null);

                    // check if it's possible to use discount
                    if (calc.haveEnoughMoney(dcto.getOriginalValue(), (payMeth.getLimit().subtract(usedLimits
                            .get(dcto.getPaymentMethodId()))))){
                        // count final price
                        usedLimits.put(dcto.getPaymentMethodId(), (usedLimits.get(dcto.getPaymentMethodId())
                                .add(dcto.getDiscountedValue())));

                        isPaid = true;
                        break;
                    }
                }
            }

            // check if price was paid
            if (!isPaid){
                // find any card that allows to pay without discount
                for (PaymentMethod pm : paymentMethods) {
                    // check if it's possible to pay
                    if (calc.haveEnoughMoney(o.getValue(), (pm.getLimit().subtract(usedLimits.get(pm.getId()))))){
                        usedLimits.put(pm.getId(), usedLimits.get(pm.getId()).add(o.getValue()));

                        break;
                    }
                }
            }
        }

        // show results
        for (PaymentMethod pm : paymentMethods){
            System.out.println(pm.getId() + " " + usedLimits.get(pm.getId()));
        }

        return usedLimits;

    }

    public void countDiscountsForOrders(){
        List<DiscountOption> discountOptionList;
        BigDecimal pointsLimit = new BigDecimal("0.00");

        // for each order
        for (Order o : this.orders){
            List<String> promotions = o.getPromotions();
            discountOptionList = new ArrayList<>();

            if (promotions == null) continue;

            // search all payment methods
            for (PaymentMethod pm : paymentMethods){

                // check if payment method is available
                if ( pm.getId().equals("PUNKTY") || promotions.contains(pm.getId()) ){

                    // check if can be fully paid
                    if (calc.haveEnoughMoney(o.getValue(), pm.getLimit())){
                        // count discount
                        DiscountOption dc = getDiscountOpiton(o.getValue(), pm.getDiscount(), pm.getId());
                        // add discount to the list
                        discountOptionList.add(dc);
                    }
                }
            }

            // check if can be paid partial
            discountOptionList = getPartialPayment(o, pointsLimit, discountOptionList);

            // sort array
            discountOptionList = sortListDesc(discountOptionList);
            // add to hashmap
            this.discountOptions.put(o.getId(), discountOptionList);
        }
    }

    public List<DiscountOption>  getPartialPayment(Order o, BigDecimal pointsLimit, List<DiscountOption> discountOptionList){
        for (PaymentMethod pm : paymentMethods) {
            // save points limit for later
            if (pm.getId().equals("PUNKTY")){
                pointsLimit = pm.getLimit();
            } else if (calc.haveEnoughMoney(o.getValue(), (pm.getLimit().add(pointsLimit))) && calc.haveEnoughLoyalPoints(o.getValue(), pointsLimit)) {
                // count discount
                DiscountOption dc = getDiscountOpiton(o.getValue(), 10, ("MIX"));
                // add discount to the list
                discountOptionList.add(dc);
            }
        }

        return discountOptionList;
    }

    public DiscountOption getDiscountOpiton(BigDecimal originValue, int discount, String paymentMethodId){
        BigDecimal discountAmount = calc.countDiscount(originValue, discount);
        BigDecimal discountedValue = originValue.subtract(discountAmount);

        return new DiscountOption(paymentMethodId, originValue, discountedValue, discountAmount);
    }

    public List<DiscountOption> sortListDesc(List<DiscountOption> discountOptionList){
        return discountOptionList.stream()
                .sorted(Comparator.comparing(DiscountOption::getDiscountAmount).reversed())
                .collect(Collectors.toList());
    }

}
