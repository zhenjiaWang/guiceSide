package oa.mingdao.com.utils;

import net.sf.json.JSONArray;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Lara Croft on 2020/2/19.
 */
public class IncomeInfoBean {

    private String from;//缓存or 实时计算
    private String raw;//如果使用缓存,增加原始数据

    private Double deductAmount;

    private Double familyCost;//家庭总成本=规划资金+新竹规划但不收费+非规划(系统不再出现非新竹规划产品)
    private Double familyMkv;//家庭总市值=新竹规划+非规划总市值
    private Double familyBalance;//家庭盈亏
    private String familyBalanceRatio;//家庭盈亏率

    private Double performanceMkv;//绩效市值

    private Double redeemAmount;

    //规划资金
    private Double planningFundsAmount;

    //新竹规划=规划资金+(新竹规划&不收费)
    private Double planningCost = 0d;
    private Double planningMkv = 0d;//新竹规划市值
    private Double planningBalance = 0d;

    private String planningCostRatio;
    private String planningMkvRatio;
    private String planningBalanceRatio;

    //新竹收费规划=规划资金(新竹规划&收费)
    private Double planningAndChargeCost = 0d;
    private Double planningAndChargeMkv = 0d;
    private Double planningAndChargeBalance = 0d;

    private String planningAndChargeCostRatio;
    private String planningAndChargeMkvRatio;
    private String planningAndChargeBalanceRatio;

    //权益类
    private Double equityREITsAssetsTypeCost = 0d;//权益类总成本
    private Double equityREITsAssetsTypeMkv = 0d;//权益类市值
    private Double equityREITsAssetsTypeBalance = 0d;//权益类市值盈利

    private String equityREITsAssetsTypeCostRatio;//权益类总成本占比
    private String equityREITsAssetsTypeMkvRatio;//权益类市值占比
    private String equityREITsAssetsTypeBalanceRatio;//权益类市值盈利占比

    //债类类
    private Double debtAssetsTypeCost = 0d;//债类总成本
    private Double debtAssetsTypeMkv = 0d;//债类总市值
    private Double debtAssetsTypeBalance = 0d;//盈利

    private String debtAssetsTypeCostRatio;//债类总成本占比
    private String debtAssetsTypeMkvRatio;//债类总市值占比
    private String debtAssetsTypeBalanceRatio;//盈利占比

    //现金类
    private Double cashAssetsTypeCost = 0d;//现金总成本
    private Double cashAssetsTypeMkv = 0d;//现金总市值
    private Double cashAssetsTypeBalance = 0d;//盈利

    private String cashAssetsTypeCostRatio;//现金总成本占比
    private String cashAssetsTypeMkvRatio;//现金总市值占比
    private String cashAssetsTypeBalanceRatio;//盈利占比

    //股类
//    private Double stockCost = 0d;
//    private Double stockMkv = 0d;
//    private Double stockBalance = 0d;
//
//    private String stockCostRatio;
//    private String stockMkvRatio;
//    private String stockBalanceRatio;

    //商品类
//    private Double commodityCost = 0d;
//    private Double commodityMkv = 0d;
//    private Double commodityBalance = 0d;

//    private String commodityCostRatio;
//    private String commodityMkvRatio;
//    private String commodityBalanceRatio;

    //房地产类
//    private Double estateCost = 0d;
//    private Double estateMkv = 0d;
//    private Double estateBalance = 0d;

//    private String estateCostRatio;
//    private String estateMkvRatio;
//    private String estateBalanceRatio;

    //债券类
//    private Double debtCost = 0d;
//    private Double debtMkv = 0d;
//    private Double debtBalance = 0d;

//    private String debtMkvRatio;
//    private String debtCostRatio;
//    private String debtBalanceRatio;

    //衍生品类
//    private Double derivativesCost = 0d;
//    private Double derivativesMkv = 0d;
//    private Double derivativesBalance = 0d;

//    private String derivativesCostRatio;
//    private String derivativesMkvRatio;
//    private String derivativesBalanceRatio;

    //现金类
//    private Double cashCost = 0d;
//    private Double cashMkv = 0d;
//    private Double cashBalance = 0d;

//    private String cashCostRatio;
//    private String cashMkvRatio;
//    private String cashBalanceRatio;

    private String errorMsg;

    private String irr;

    private JSONArray irrDetail;

    private JSONArray assetsCombo;//基金信息(包含交易记录)

    private List<Long>  ec;

    public Double getPlanningCost() {
        return planningCost;
    }

    public void setPlanningCost(Double planningCost) {
        this.planningCost = planningCost;
    }

    public Double getPlanningMkv() {
        return planningMkv;
    }

    public void setPlanningMkv(Double planningMkv) {
        this.planningMkv = planningMkv;
    }

    public Double getPlanningBalance() {
        return planningBalance;
    }

    public void setPlanningBalance(Double planningBalance) {
        this.planningBalance = planningBalance;
    }

    public String getPlanningCostRatio() {
        return planningCostRatio;
    }

    public void setPlanningCostRatio(String planningCostRatio) {
        this.planningCostRatio = planningCostRatio;
    }

    public String getPlanningMkvRatio() {
        return planningMkvRatio;
    }

    public void setPlanningMkvRatio(String planningMkvRatio) {
        this.planningMkvRatio = planningMkvRatio;
    }

    public String getPlanningBalanceRatio() {
        return planningBalanceRatio;
    }

    public void setPlanningBalanceRatio(String planningBalanceRatio) {
        this.planningBalanceRatio = planningBalanceRatio;
    }
    public Double getEquityREITsAssetsTypeCost() {
        return equityREITsAssetsTypeCost;
    }

    public void setEquityREITsAssetsTypeCost(Double equityREITsAssetsTypeCost) {
        this.equityREITsAssetsTypeCost = equityREITsAssetsTypeCost;
    }

    public Double getEquityREITsAssetsTypeMkv() {
        return equityREITsAssetsTypeMkv;
    }

    public void setEquityREITsAssetsTypeMkv(Double equityREITsAssetsTypeMkv) {
        this.equityREITsAssetsTypeMkv = equityREITsAssetsTypeMkv;
    }

    public Double getEquityREITsAssetsTypeBalance() {
        return equityREITsAssetsTypeBalance;
    }

    public void setEquityREITsAssetsTypeBalance(Double equityREITsAssetsTypeBalance) {
        this.equityREITsAssetsTypeBalance = equityREITsAssetsTypeBalance;
    }

    public String getEquityREITsAssetsTypeCostRatio() {
        return equityREITsAssetsTypeCostRatio;
    }

    public void setEquityREITsAssetsTypeCostRatio(String equityREITsAssetsTypeCostRatio) {
        this.equityREITsAssetsTypeCostRatio = equityREITsAssetsTypeCostRatio;
    }

    /**
     * 10%
     * @return percent+%
     */
    public String getEquityREITsAssetsTypeMkvRatio() {
        return equityREITsAssetsTypeMkvRatio;
    }

    public void setEquityREITsAssetsTypeMkvRatio(String equityREITsAssetsTypeMkvRatio) {
        this.equityREITsAssetsTypeMkvRatio = equityREITsAssetsTypeMkvRatio;
    }

    public String getEquityREITsAssetsTypeBalanceRatio() {
        return equityREITsAssetsTypeBalanceRatio;
    }

    public void setEquityREITsAssetsTypeBalanceRatio(String equityREITsAssetsTypeBalanceRatio) {
        this.equityREITsAssetsTypeBalanceRatio = equityREITsAssetsTypeBalanceRatio;
    }

    public Double getDebtAssetsTypeCost() {
        return debtAssetsTypeCost;
    }

    public void setDebtAssetsTypeCost(Double debtAssetsTypeCost) {
        this.debtAssetsTypeCost = debtAssetsTypeCost;
    }

    public Double getDebtAssetsTypeMkv() {
        return debtAssetsTypeMkv;
    }

    public void setDebtAssetsTypeMkv(Double debtAssetsTypeMkv) {
        this.debtAssetsTypeMkv = debtAssetsTypeMkv;
    }

    public Double getDebtAssetsTypeBalance() {
        return debtAssetsTypeBalance;
    }

    public void setDebtAssetsTypeBalance(Double debtAssetsTypeBalance) {
        this.debtAssetsTypeBalance = debtAssetsTypeBalance;
    }

    public String getDebtAssetsTypeCostRatio() {
        return debtAssetsTypeCostRatio;
    }

    public void setDebtAssetsTypeCostRatio(String debtAssetsTypeCostRatio) {
        this.debtAssetsTypeCostRatio = debtAssetsTypeCostRatio;
    }

    public String getDebtAssetsTypeMkvRatio() {
        return debtAssetsTypeMkvRatio;
    }

    public void setDebtAssetsTypeMkvRatio(String debtAssetsTypeMkvRatio) {
        this.debtAssetsTypeMkvRatio = debtAssetsTypeMkvRatio;
    }

    public String getDebtAssetsTypeBalanceRatio() {
        return debtAssetsTypeBalanceRatio;
    }

    public void setDebtAssetsTypeBalanceRatio(String debtAssetsTypeBalanceRatio) {
        this.debtAssetsTypeBalanceRatio = debtAssetsTypeBalanceRatio;
    }

    public Double getCashAssetsTypeCost() {
        return cashAssetsTypeCost;
    }

    public void setCashAssetsTypeCost(Double cashAssetsTypeCost) {
        this.cashAssetsTypeCost = cashAssetsTypeCost;
    }

    public Double getCashAssetsTypeMkv() {
        return cashAssetsTypeMkv;
    }

    public void setCashAssetsTypeMkv(Double cashAssetsTypeMkv) {
        this.cashAssetsTypeMkv = cashAssetsTypeMkv;
    }

    public Double getCashAssetsTypeBalance() {
        return cashAssetsTypeBalance;
    }

    public void setCashAssetsTypeBalance(Double cashAssetsTypeBalance) {
        this.cashAssetsTypeBalance = cashAssetsTypeBalance;
    }

    public String getCashAssetsTypeCostRatio() {
        return cashAssetsTypeCostRatio;
    }

    public void setCashAssetsTypeCostRatio(String cashAssetsTypeCostRatio) {
        this.cashAssetsTypeCostRatio = cashAssetsTypeCostRatio;
    }

    public String getCashAssetsTypeMkvRatio() {
        return cashAssetsTypeMkvRatio;
    }

    public void setCashAssetsTypeMkvRatio(String cashAssetsTypeMkvRatio) {
        this.cashAssetsTypeMkvRatio = cashAssetsTypeMkvRatio;
    }

    public String getCashAssetsTypeBalanceRatio() {
        return cashAssetsTypeBalanceRatio;
    }

    public void setCashAssetsTypeBalanceRatio(String cashAssetsTypeBalanceRatio) {
        this.cashAssetsTypeBalanceRatio = cashAssetsTypeBalanceRatio;
    }
//
//    public Double getStockCost() {
//        return stockCost;
//    }
//
//    public void setStockCost(Double stockCost) {
//        this.stockCost = stockCost;
//    }
//
//    public Double getStockMkv() {
//        return stockMkv;
//    }
//
//    public void setStockMkv(Double stockMkv) {
//        this.stockMkv = stockMkv;
//    }
//
//    public Double getStockBalance() {
//        return stockBalance;
//    }
//
//    public void setStockBalance(Double stockBalance) {
//        this.stockBalance = stockBalance;
//    }
//
//    public String getStockCostRatio() {
//        return stockCostRatio;
//    }
//
//    public void setStockCostRatio(String stockCostRatio) {
//        this.stockCostRatio = stockCostRatio;
//    }
//
//    public String getStockMkvRatio() {
//        return stockMkvRatio;
//    }
//
//    public void setStockMkvRatio(String stockMkvRatio) {
//        this.stockMkvRatio = stockMkvRatio;
//    }
//
//    public String getStockBalanceRatio() {
//        return stockBalanceRatio;
//    }
//
//    public void setStockBalanceRatio(String stockBalanceRatio) {
//        this.stockBalanceRatio = stockBalanceRatio;
//    }
//
//    public Double getCommodityCost() {
//        return commodityCost;
//    }
//
//    public void setCommodityCost(Double commodityCost) {
//        this.commodityCost = commodityCost;
//    }
//
//    public Double getCommodityMkv() {
//        return commodityMkv;
//    }
//
//    public void setCommodityMkv(Double commodityMkv) {
//        this.commodityMkv = commodityMkv;
//    }
//
//    public Double getCommodityBalance() {
//        return commodityBalance;
//    }
//
//    public void setCommodityBalance(Double commodityBalance) {
//        this.commodityBalance = commodityBalance;
//    }
//
//    public String getCommodityCostRatio() {
//        return commodityCostRatio;
//    }
//
//    public void setCommodityCostRatio(String commodityCostRatio) {
//        this.commodityCostRatio = commodityCostRatio;
//    }
//
//    public String getCommodityMkvRatio() {
//        return commodityMkvRatio;
//    }
//
//    public void setCommodityMkvRatio(String commodityMkvRatio) {
//        this.commodityMkvRatio = commodityMkvRatio;
//    }
//
//    public String getCommodityBalanceRatio() {
//        return commodityBalanceRatio;
//    }
//
//    public void setCommodityBalanceRatio(String commodityBalanceRatio) {
//        this.commodityBalanceRatio = commodityBalanceRatio;
//    }
//
//    public Double getEstateCost() {
//        return estateCost;
//    }
//
//    public void setEstateCost(Double estateCost) {
//        this.estateCost = estateCost;
//    }
//
//    public Double getEstateMkv() {
//        return estateMkv;
//    }
//
//    public void setEstateMkv(Double estateMkv) {
//        this.estateMkv = estateMkv;
//    }
//
//    public Double getEstateBalance() {
//        return estateBalance;
//    }
//
//    public void setEstateBalance(Double estateBalance) {
//        this.estateBalance = estateBalance;
//    }
//
//    public String getEstateCostRatio() {
//        return estateCostRatio;
//    }
//
//    public void setEstateCostRatio(String estateCostRatio) {
//        this.estateCostRatio = estateCostRatio;
//    }
//
//    public String getEstateMkvRatio() {
//        return estateMkvRatio;
//    }
//
//    public void setEstateMkvRatio(String estateMkvRatio) {
//        this.estateMkvRatio = estateMkvRatio;
//    }
//
//    public String getEstateBalanceRatio() {
//        return estateBalanceRatio;
//    }
//
//    public void setEstateBalanceRatio(String estateBalanceRatio) {
//        this.estateBalanceRatio = estateBalanceRatio;
//    }
//
//    public Double getDebtCost() {
//        return debtCost;
//    }
//
//    public void setDebtCost(Double debtCost) {
//        this.debtCost = debtCost;
//    }
//
//    public Double getDebtMkv() {
//        return debtMkv;
//    }
//
//    public void setDebtMkv(Double debtMkv) {
//        this.debtMkv = debtMkv;
//    }
//
//    public Double getDebtBalance() {
//        return debtBalance;
//    }
//
//    public void setDebtBalance(Double debtBalance) {
//        this.debtBalance = debtBalance;
//    }
//
//    public String getDebtMkvRatio() {
//        return debtMkvRatio;
//    }
//
//    public void setDebtMkvRatio(String debtMkvRatio) {
//        this.debtMkvRatio = debtMkvRatio;
//    }
//
//    public String getDebtCostRatio() {
//        return debtCostRatio;
//    }
//
//    public void setDebtCostRatio(String debtCostRatio) {
//        this.debtCostRatio = debtCostRatio;
//    }
//
//    public String getDebtBalanceRatio() {
//        return debtBalanceRatio;
//    }
//
//    public void setDebtBalanceRatio(String debtBalanceRatio) {
//        this.debtBalanceRatio = debtBalanceRatio;
//    }
//
//    public Double getDerivativesCost() {
//        return derivativesCost;
//    }
//
//    public void setDerivativesCost(Double derivativesCost) {
//        this.derivativesCost = derivativesCost;
//    }
//
//    public Double getDerivativesMkv() {
//        return derivativesMkv;
//    }
//
//    public void setDerivativesMkv(Double derivativesMkv) {
//        this.derivativesMkv = derivativesMkv;
//    }
//
//    public Double getDerivativesBalance() {
//        return derivativesBalance;
//    }
//
//    public void setDerivativesBalance(Double derivativesBalance) {
//        this.derivativesBalance = derivativesBalance;
//    }
//
//    public String getDerivativesCostRatio() {
//        return derivativesCostRatio;
//    }
//
//    public void setDerivativesCostRatio(String derivativesCostRatio) {
//        this.derivativesCostRatio = derivativesCostRatio;
//    }
//
//    public String getDerivativesMkvRatio() {
//        return derivativesMkvRatio;
//    }
//
//    public void setDerivativesMkvRatio(String derivativesMkvRatio) {
//        this.derivativesMkvRatio = derivativesMkvRatio;
//    }
//
//    public String getDerivativesBalanceRatio() {
//        return derivativesBalanceRatio;
//    }
//
//    public void setDerivativesBalanceRatio(String derivativesBalanceRatio) {
//        this.derivativesBalanceRatio = derivativesBalanceRatio;
//    }
//
//    public Double getCashCost() {
//        return cashCost;
//    }
//
//    public void setCashCost(Double cashCost) {
//        this.cashCost = cashCost;
//    }
//
//    public Double getCashMkv() {
//        return cashMkv;
//    }
//
//    public void setCashMkv(Double cashMkv) {
//        this.cashMkv = cashMkv;
//    }
//
//    public Double getCashBalance() {
//        return cashBalance;
//    }
//
//    public void setCashBalance(Double cashBalance) {
//        this.cashBalance = cashBalance;
//    }
//
//    public String getCashCostRatio() {
//        return cashCostRatio;
//    }
//
//    public void setCashCostRatio(String cashCostRatio) {
//        this.cashCostRatio = cashCostRatio;
//    }
//
//    public String getCashMkvRatio() {
//        return cashMkvRatio;
//    }
//
//    public void setCashMkvRatio(String cashMkvRatio) {
//        this.cashMkvRatio = cashMkvRatio;
//    }
//
//    public String getCashBalanceRatio() {
//        return cashBalanceRatio;
//    }
//
//    public void setCashBalanceRatio(String cashBalanceRatio) {
//        this.cashBalanceRatio = cashBalanceRatio;
//    }

//    public String getBalanceText() {
//        return balanceText;
//    }
//
//    public void setBalanceText(String balanceText) {
//        this.balanceText = balanceText;
//    }
//
//    public String getCostAmountText() {
//        return costAmountText;
//    }
//
//    public void setCostAmountText(String costAmountText) {
//        this.costAmountText = costAmountText;
//    }
//
//    public String getMkValueAmountText() {
//        return mkValueAmountText;
//    }
//
//    public void setMkValueAmountText(String mkValueAmountText) {
//        this.mkValueAmountText = mkValueAmountText;
//    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        if (StringUtils.isBlank(this.errorMsg)) {
            this.errorMsg = errorMsg;
        } else if ( !this.errorMsg.contains(errorMsg) ){
            this.errorMsg = this.errorMsg + "," + errorMsg;
        }
    }

    public String getIrr() {
        return irr;
    }

    public void setIrr(String irr) {
        this.irr = irr;
    }

    public JSONArray getIrrDetail() {
        return irrDetail;
    }

    public void setIrrDetail(JSONArray irrDetail) {
        this.irrDetail = irrDetail;
    }

    public JSONArray getAssetsCombo() {
        return assetsCombo;
    }

    public void setAssetsCombo(JSONArray assetsCombo) {
        this.assetsCombo = assetsCombo;
    }


    public Double getPlanningAndChargeCost() {
        return planningAndChargeCost;
    }

    public void setPlanningAndChargeCost(Double planningAndChargeCost) {
        this.planningAndChargeCost = planningAndChargeCost;
    }

    public Double getPlanningAndChargeMkv() {
        return planningAndChargeMkv;
    }

    public void setPlanningAndChargeMkv(Double planningAndChargeMkv) {
        this.planningAndChargeMkv = planningAndChargeMkv;
    }

    public Double getPlanningAndChargeBalance() {
        return planningAndChargeBalance;
    }

    public void setPlanningAndChargeBalance(Double planningAndChargeBalance) {
        this.planningAndChargeBalance = planningAndChargeBalance;
    }

    public String getPlanningAndChargeCostRatio() {
        return planningAndChargeCostRatio;
    }

    public void setPlanningAndChargeCostRatio(String planningAndChargeCostRatio) {
        this.planningAndChargeCostRatio = planningAndChargeCostRatio;
    }

    public String getPlanningAndChargeMkvRatio() {
        return planningAndChargeMkvRatio;
    }

    public void setPlanningAndChargeMkvRatio(String planningAndChargeMkvRatio) {
        this.planningAndChargeMkvRatio = planningAndChargeMkvRatio;
    }

    public String getPlanningAndChargeBalanceRatio() {
        return planningAndChargeBalanceRatio;
    }

    public void setPlanningAndChargeBalanceRatio(String planningAndChargeBalanceRatio) {
        this.planningAndChargeBalanceRatio = planningAndChargeBalanceRatio;
    }

    public Double getPlanningFundsAmount() {
        return planningFundsAmount;
    }

    public void setPlanningFundsAmount(Double planningFundsAmount) {
        this.planningFundsAmount = planningFundsAmount;
    }

    public Double getFamilyCost() {
        return familyCost;
    }

    public void setFamilyCost(Double familyCost) {
        this.familyCost = familyCost;
    }

    public Double getFamilyMkv() {
        return familyMkv;
    }

    public void setFamilyMkv(Double familyMkv) {
        this.familyMkv = familyMkv;
    }

    public Double getFamilyBalance() {
        return familyBalance;
    }

    public void setFamilyBalance(Double familyBalance) {
        this.familyBalance = familyBalance;
    }

    public String getFamilyBalanceRatio() {
        return familyBalanceRatio;
    }

    public void setFamilyBalanceRatio(String familyBalanceRatio) {
        this.familyBalanceRatio = familyBalanceRatio;
    }

    public Double getRedeemAmount() {
        return redeemAmount;
    }

    public void setRedeemAmount(Double redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    public Double getPerformanceMkv() {
        return performanceMkv;
    }

    public void setPerformanceMkv(Double performanceMkv) {
        this.performanceMkv = performanceMkv;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public List<Long> getEc() {
        return ec;
    }

    public void setEc(List<Long> ec) {
        this.ec = ec;
    }

    public Double getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Double deductAmount) {
        this.deductAmount = deductAmount;
    }

    public void build() {
        // this.balance =  precision(this.mkValueAmount - this.costAmount);
//        this.balance = precision(this.planningMkv - this.planningCost);
//        this.balanceRatio = this.formatDouble(this.division(this.balance , this.planningCost), true);
//        this.balanceText = this.formatDouble(this.balance, false);
//        this.costAmountText = this.formatDouble(this.planningCost, false);
//        this.mkValueAmountText = this.formatDouble(this.mkValueAmount, false);
        this.familyBalance = precision(this.familyMkv - this.familyCost);
        this.familyBalanceRatio = this.formatDouble(this.division(this.familyBalance, this.familyCost), true);

        try {
            String fields = "planning,planningAndCharge,equityREITsAssetsType,debtAssetsType,cashAssetsType" ;
                   // ",stock,commodity,estate,debt,derivatives,cash";
            Double cost, mkv, balance;
            String costRatio, mkvRatio, balanceRatio;
            for (String prop : fields.split(",")) {
                cost = BeanUtils.getValue(this, prop + "Cost", Double.class);
                mkv = BeanUtils.getValue(this, prop + "Mkv", Double.class);
                if (cost == null) cost = 0d;
                if (mkv == null) mkv = 0d;
                balance =precision(mkv - cost);

                balanceRatio = this.formatDouble(this.division(balance, cost), true);
                costRatio = this.formatDouble(this.division(cost, this.planningCost), true);
                //mkvRatio = this.formatDouble(this.division(mkv, this.mkValueAmount), true);
                mkvRatio = this.formatDouble(this.division(mkv, this.planningMkv), true);

                BeanUtils.setValue(this, prop + "Balance", balance);
                BeanUtils.setValue(this, prop + "BalanceRatio", balanceRatio);
                BeanUtils.setValue(this, prop + "CostRatio", costRatio);
                BeanUtils.setValue(this, prop + "MkvRatio", mkvRatio);
            }
        } catch (Exception ex) {//BeanUtils.setProperty 大坑  value 类型不对不报错...........
            ex.printStackTrace();
        }
    }

    private Double division(Double a, Double b) {
        if (b == 0) {
            return 0d;
        }
        return a / b;
    }
    private static Double precision(Double v) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(v));
    }


    private String formatDouble(Double v, boolean percent) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (percent) {
            v = v * 100;
            String str = df.format(v) + "%";
            if (str.equals("-0.00%")) str = "0.00%";
            return str;
        } else {
            return df.format(v);
        }
    }
}
