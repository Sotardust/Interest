package com.dht.interest.investment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 */
public class BondBean implements Parcelable {

    /**
     * bond_id : 113555
     * bond_nm : 振德转债
     * stock_id : sh603301
     * stock_nm : 振德医疗
     * convert_price : 20.04
     * convert_price_valid_from : null
     * convert_dt : 2020-06-25
     * maturity_dt : 2025-12-18
     * next_put_dt : 2023-12-18
     * put_dt : null
     * put_notes : null
     * put_price : 100.000
     * put_inc_cpn_fl : y
     * put_convert_price_ratio : 37.63
     * put_count_days : 30
     * put_total_days : 30
     * put_real_days : 0
     * repo_discount_rt : 0.00
     * repo_valid_from : null
     * repo_valid_to : null
     * redeem_price : 118.000
     * redeem_inc_cpn_fl : n
     * redeem_price_ratio : 130.000
     * redeem_count_days : 15
     * redeem_total_days : 30
     * redeem_real_days : 0
     * redeem_dt : null
     * redeem_flag : X
     * orig_iss_amt : 4.400
     * curr_iss_amt : 4.400
     * rating_cd : AA-
     * issuer_rating_cd : AA-
     * guarantor : 股票质押担保
     * force_redeem : null
     * real_force_redeem_price : null
     * convert_cd : 未到转股期
     * repo_cd : null
     * ration : null
     * ration_cd : 753301
     * apply_cd : 754301
     * online_offline_ratio : null
     * qflag : N
     * qflag2 : N
     * ration_rt : 75.380
     * fund_rt : buy
     * margin_flg :
     * pb : 4.40
     * total_shares : 140000000.0
     * sqflg : Y
     * sprice : 37.28
     * svolume : 75476.22
     * sincrease_rt : -8.74%
     * qstatus : 00
     * last_time : 15:00:11
     * convert_value : 186.03
     * premium_rt : -17.64%
     * year_left : 5.849
     * ytm_rt : -3.52%
     * ytm_rt_tax : -4.18%
     * price : 153.210
     * full_price : 153.210
     * increase_rt : -4.21%
     * volume : 107050.80
     * convert_price_valid : Y
     * adj_scnt : 0
     * adj_cnt : 0
     * redeem_icon :
     * redeem_style : Y
     * owned : 0
     * noted : 0
     * ref_yield_info :
     * adjust_tip :
     * left_put_year : -
     * short_maturity_dt : 25-12-18
     * dblow : 135.57
     * force_redeem_price : 26.05
     * put_convert_price : 14.03
     * convert_amt_ratio : 8.4%
     * stock_net_value : 0.00
     * stock_cd : 603301
     * pre_bond_id : sh113555
     * repo_valid : 有效期：-
     * convert_cd_tip : 未到转股期；2020-06-25 开始转股
     * price_tips : 全价：153.210 最后更新：15:00:11
     */

    public String bond_id;
    public String bond_nm;
    public String stock_id;
    public String stock_nm;
    public String convert_price;
    public Object convert_price_valid_from;
    public String convert_dt;
    public String maturity_dt;
    public String next_put_dt;
    public Object put_dt;
    public Object put_notes;
    public String put_price;
    public String put_inc_cpn_fl;
    public String put_convert_price_ratio;
    public int put_count_days;
    public int put_total_days;
    public int put_real_days;
    public String repo_discount_rt;
    public Object repo_valid_from;
    public Object repo_valid_to;
    public String redeem_price;
    public String redeem_inc_cpn_fl;
    public String redeem_price_ratio;
    public int redeem_count_days;
    public int redeem_total_days;
    public int redeem_real_days;
    public Object redeem_dt;
    public String redeem_flag;
    public String orig_iss_amt;
    public String curr_iss_amt;
    public String rating_cd;
    public String issuer_rating_cd;
    public String guarantor;
    public Object force_redeem;
    public Object real_force_redeem_price;
    public String convert_cd;
    public Object repo_cd;
    public Object ration;
    public String ration_cd;
    public String apply_cd;
    public Object online_offline_ratio;
    public String qflag;
    public String qflag2;
    public String ration_rt;
    public String fund_rt;
    public String margin_flg;
    public String pb;
    public String total_shares;
    public String sqflg;
    public String sprice;
    public String svolume;
    public String sincrease_rt;
    public String qstatus;
    public String last_time;
    public String convert_value;
    public String premium_rt;
    public String year_left;
    public String ytm_rt;
    public String ytm_rt_tax;
    public String price;
    public String full_price;
    public String increase_rt;
    public String volume;
    public String convert_price_valid;
    public int adj_scnt;
    public int adj_cnt;
    public String redeem_icon;
    public String redeem_style;
    public int owned;
    public int noted;
    public String ref_yield_info;
    public String adjust_tip;
    public String left_put_year;
    public String short_maturity_dt;
    public String dblow;
    public String force_redeem_price;
    public String put_convert_price;
    public String convert_amt_ratio;
    public String stock_net_value;
    public String stock_cd;
    public String pre_bond_id;
    public String repo_valid;
    public String convert_cd_tip;
    public String price_tips;

    protected BondBean(Parcel in) {
        bond_id = in.readString();
        bond_nm = in.readString();
        stock_id = in.readString();
        stock_nm = in.readString();
        convert_price = in.readString();
        convert_dt = in.readString();
        maturity_dt = in.readString();
        next_put_dt = in.readString();
        put_price = in.readString();
        put_inc_cpn_fl = in.readString();
        put_convert_price_ratio = in.readString();
        put_count_days = in.readInt();
        put_total_days = in.readInt();
        put_real_days = in.readInt();
        repo_discount_rt = in.readString();
        redeem_price = in.readString();
        redeem_inc_cpn_fl = in.readString();
        redeem_price_ratio = in.readString();
        redeem_count_days = in.readInt();
        redeem_total_days = in.readInt();
        redeem_real_days = in.readInt();
        redeem_flag = in.readString();
        orig_iss_amt = in.readString();
        curr_iss_amt = in.readString();
        rating_cd = in.readString();
        issuer_rating_cd = in.readString();
        guarantor = in.readString();
        convert_cd = in.readString();
        ration_cd = in.readString();
        apply_cd = in.readString();
        qflag = in.readString();
        qflag2 = in.readString();
        ration_rt = in.readString();
        fund_rt = in.readString();
        margin_flg = in.readString();
        pb = in.readString();
        total_shares = in.readString();
        sqflg = in.readString();
        sprice = in.readString();
        svolume = in.readString();
        sincrease_rt = in.readString();
        qstatus = in.readString();
        last_time = in.readString();
        convert_value = in.readString();
        premium_rt = in.readString();
        year_left = in.readString();
        ytm_rt = in.readString();
        ytm_rt_tax = in.readString();
        price = in.readString();
        full_price = in.readString();
        increase_rt = in.readString();
        volume = in.readString();
        convert_price_valid = in.readString();
        adj_scnt = in.readInt();
        adj_cnt = in.readInt();
        redeem_icon = in.readString();
        redeem_style = in.readString();
        owned = in.readInt();
        noted = in.readInt();
        ref_yield_info = in.readString();
        adjust_tip = in.readString();
        left_put_year = in.readString();
        short_maturity_dt = in.readString();
        dblow = in.readString();
        force_redeem_price = in.readString();
        put_convert_price = in.readString();
        convert_amt_ratio = in.readString();
        stock_net_value = in.readString();
        stock_cd = in.readString();
        pre_bond_id = in.readString();
        repo_valid = in.readString();
        convert_cd_tip = in.readString();
        price_tips = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bond_id);
        dest.writeString(bond_nm);
        dest.writeString(stock_id);
        dest.writeString(stock_nm);
        dest.writeString(convert_price);
        dest.writeString(convert_dt);
        dest.writeString(maturity_dt);
        dest.writeString(next_put_dt);
        dest.writeString(put_price);
        dest.writeString(put_inc_cpn_fl);
        dest.writeString(put_convert_price_ratio);
        dest.writeInt(put_count_days);
        dest.writeInt(put_total_days);
        dest.writeInt(put_real_days);
        dest.writeString(repo_discount_rt);
        dest.writeString(redeem_price);
        dest.writeString(redeem_inc_cpn_fl);
        dest.writeString(redeem_price_ratio);
        dest.writeInt(redeem_count_days);
        dest.writeInt(redeem_total_days);
        dest.writeInt(redeem_real_days);
        dest.writeString(redeem_flag);
        dest.writeString(orig_iss_amt);
        dest.writeString(curr_iss_amt);
        dest.writeString(rating_cd);
        dest.writeString(issuer_rating_cd);
        dest.writeString(guarantor);
        dest.writeString(convert_cd);
        dest.writeString(ration_cd);
        dest.writeString(apply_cd);
        dest.writeString(qflag);
        dest.writeString(qflag2);
        dest.writeString(ration_rt);
        dest.writeString(fund_rt);
        dest.writeString(margin_flg);
        dest.writeString(pb);
        dest.writeString(total_shares);
        dest.writeString(sqflg);
        dest.writeString(sprice);
        dest.writeString(svolume);
        dest.writeString(sincrease_rt);
        dest.writeString(qstatus);
        dest.writeString(last_time);
        dest.writeString(convert_value);
        dest.writeString(premium_rt);
        dest.writeString(year_left);
        dest.writeString(ytm_rt);
        dest.writeString(ytm_rt_tax);
        dest.writeString(price);
        dest.writeString(full_price);
        dest.writeString(increase_rt);
        dest.writeString(volume);
        dest.writeString(convert_price_valid);
        dest.writeInt(adj_scnt);
        dest.writeInt(adj_cnt);
        dest.writeString(redeem_icon);
        dest.writeString(redeem_style);
        dest.writeInt(owned);
        dest.writeInt(noted);
        dest.writeString(ref_yield_info);
        dest.writeString(adjust_tip);
        dest.writeString(left_put_year);
        dest.writeString(short_maturity_dt);
        dest.writeString(dblow);
        dest.writeString(force_redeem_price);
        dest.writeString(put_convert_price);
        dest.writeString(convert_amt_ratio);
        dest.writeString(stock_net_value);
        dest.writeString(stock_cd);
        dest.writeString(pre_bond_id);
        dest.writeString(repo_valid);
        dest.writeString(convert_cd_tip);
        dest.writeString(price_tips);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BondBean> CREATOR = new Creator<BondBean>() {
        @Override
        public BondBean createFromParcel(Parcel in) {
            return new BondBean(in);
        }

        @Override
        public BondBean[] newArray(int size) {
            return new BondBean[size];
        }
    };
}
