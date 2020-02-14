package com.dht.interest.investment.histroy;

import java.util.List;

public class HistoryModel {

    /**
     * page : 1
     * rows : [{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-14","ytm_rt":"-3.23%","premium_rt":"-15.13%","convert_value":"177.45","price":"150.590","volume":"56948.90"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-13","ytm_rt":"-3.52%","premium_rt":"-17.64%","convert_value":"186.03","price":"153.210","volume":"107050.80"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-12","ytm_rt":"-4.23%","premium_rt":"-21.53%","convert_value":"203.84","price":"159.950","volume":"184811.97"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-11","ytm_rt":"-3.86%","premium_rt":"-22.40%","convert_value":"201.60","price":"156.440","volume":"212282.62"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-10","ytm_rt":"-4.90%","premium_rt":"-23.94%","convert_value":"218.96","price":"166.550","volume":"218494.66"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-07","ytm_rt":"-9.27%","premium_rt":"-9.73%","convert_value":"242.02","price":"218.460","volume":"130902.55"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-06","ytm_rt":"-8.69%","premium_rt":"-4.26%","convert_value":"220.01","price":"210.630","volume":"113023.50"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-05","ytm_rt":"-6.44%","premium_rt":"-8.43%","convert_value":"200.00","price":"183.140","volume":"131193.97"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-04","ytm_rt":"-6.04%","premium_rt":"-1.74%","convert_value":"181.84","price":"178.680","volume":"106544.44"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-02-03","ytm_rt":"-6.43%","premium_rt":"10.71%","convert_value":"165.32","price":"183.020","volume":"8920.63"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-23","ytm_rt":"-1.78%","premium_rt":"-7.85%","convert_value":"150.30","price":"138.500","volume":"71051.47"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-22","ytm_rt":"-0.20%","premium_rt":"-7.59%","convert_value":"136.63","price":"126.260","volume":"55049.91"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-21","ytm_rt":"-2.56%","premium_rt":"9.38%","convert_value":"132.58","price":"145.020","volume":"46756.16"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-20","ytm_rt":"0.04%","premium_rt":"3.33%","convert_value":"120.51","price":"124.520","volume":"19617.03"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-17","ytm_rt":"0.96%","premium_rt":"7.78%","convert_value":"109.53","price":"118.050","volume":"1414.22"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-16","ytm_rt":"0.86%","premium_rt":"6.61%","convert_value":"111.38","price":"118.740","volume":"2552.84"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-15","ytm_rt":"0.82%","premium_rt":"5.57%","convert_value":"112.72","price":"119.000","volume":"7512.13"}},{"id":"113555","cell":{"bond_id":"113555","last_chg_dt":"2020-01-14","ytm_rt":"0.78%","premium_rt":"5.72%","convert_value":"112.87","price":"119.330","volume":"45162.80"}}]
     * total : 18
     */

    private int page;
    private int total;
    private List<RowsBean> rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 113555
         * cell : {"bond_id":"113555","last_chg_dt":"2020-02-14","ytm_rt":"-3.23%","premium_rt":"-15.13%","convert_value":"177.45","price":"150.590","volume":"56948.90"}
         */

        private String id;
        private HistroyBean cell;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public HistroyBean getBean() {
            return cell;
        }

        public void setBean(HistroyBean cell) {
            this.cell = cell;
        }

    }
}
