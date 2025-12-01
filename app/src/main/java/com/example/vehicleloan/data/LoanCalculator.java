package com.example.vehicleloan.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanCalculator {

    public static class Result {
        public final BigDecimal loanAmount;
        public final BigDecimal totalInterest;
        public final BigDecimal totalPayment;
        public final BigDecimal monthlyPayment;

        public Result(BigDecimal loanAmount, BigDecimal totalInterest, BigDecimal totalPayment, BigDecimal monthlyPayment) {
            this.loanAmount = loanAmount;
            this.totalInterest = totalInterest;
            this.totalPayment = totalPayment;
            this.monthlyPayment = monthlyPayment;
        }
    }

    public static Result calculate(BigDecimal vehiclePrice, BigDecimal downPayment, BigDecimal years, BigDecimal annualRatePercent) {
        BigDecimal loanAmount = vehiclePrice.subtract(downPayment).setScale(2, RoundingMode.HALF_UP);
        if (loanAmount.compareTo(BigDecimal.ZERO) < 0) loanAmount = BigDecimal.ZERO;

        BigDecimal interestFraction = annualRatePercent.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
        BigDecimal totalInterest = loanAmount.multiply(interestFraction).multiply(years).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalPayment = loanAmount.add(totalInterest).setScale(2, RoundingMode.HALF_UP);
        BigDecimal months = years.multiply(new BigDecimal("12"));
        BigDecimal monthlyPayment = BigDecimal.ZERO;
        if (months.compareTo(BigDecimal.ZERO) > 0) monthlyPayment = totalPayment.divide(months, 2, RoundingMode.HALF_UP);

        return new Result(loanAmount, totalInterest, totalPayment, monthlyPayment);
    }
}