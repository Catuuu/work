import com.alibaba.fastjson.JSONObject;
import com.framework.system.SystemConstant;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.weixin.util.CodeUtil;
import com.opensdk.weixin.vo.WeixinCode;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2017/2/11.
 */
public class Test {


    public static void main1(String[] args) throws Exception {

        int year = 2017;
        StringBuffer result = new StringBuffer();
        for (int month = 1; month <= 12; month++) {
            String cur_month = year + "-" + StringUtil.dispRepairLeft("" + month, "0", 2) + "-01";
            String next_month = year + "-" + StringUtil.dispRepairLeft("" + (month + 1), "0", 2) + "-01";
            if (month == 12) {
                next_month = (year + 1) + "-01-01";
            }

            String s = "\n\n\nuse caidashi_" + year + "\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE TABLE [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [order_id] [nvarchar](32) NOT NULL,\n" +
                    "  [member_id] [int] NULL,\n" +
                    "  [order_no] [nvarchar](9) NULL,\n" +
                    "  [fromin] [varchar](20) NULL,\n" +
                    "  [order_type] [int] NULL,\n" +
                    "  [desk_no] [nvarchar](20) NULL,\n" +
                    "  [create_date] [datetime] NOT NULL,\n" +
                    "  [goods_prcie] [numeric](18, 2) NULL,\n" +
                    "  [uc_price] [numeric](18, 2) NULL,\n" +
                    "  [ship_fee] [numeric](18, 2) NULL,\n" +
                    "  [deduction_price] [numeric](18, 2) NULL,\n" +
                    "  [total_price] [numeric](18, 2) NULL,\n" +
                    "  [pay_type_id] [int] NULL,\n" +
                    "  [pay_type_name] [nvarchar](50) NULL,\n" +
                    "  [pay_time] [datetime] NULL,\n" +
                    "  [receiver_name] [nvarchar](30) NULL,\n" +
                    "  [receiver_phone] [nvarchar](50) NULL,\n" +
                    "  [receiver_adress] [nvarchar](200) NULL,\n" +
                    "  [service_time] [datetime] NULL,\n" +
                    "  [order_status] [int] NULL,\n" +
                    "  [back_status] [int] NULL,\n" +
                    "  [tasking_status] [int] NULL,\n" +
                    "  [task_order_time] [datetime] NULL,\n" +
                    "  [task_user_time] [datetime] NULL,\n" +
                    "  [task_time] [datetime] NULL,\n" +
                    "  [order_desc] [nvarchar](50) NULL,\n" +
                    "  [member_desc] [nvarchar](500) NULL,\n" +
                    "  [stores_id] [int] NULL,\n" +
                    "  [bookdinner] [int] NULL,\n" +
                    "  [vip_deduction_price] [numeric](18, 2) NULL,\n" +
                    "  [stores_select] [varchar](500) NULL,\n" +
                    "  [transaction_id] [nvarchar](50) NULL,\n" +
                    "  [receiver_sex] [nvarchar](10) NULL,\n" +
                    "  [user_id] [int] NULL,\n" +
                    "  [box_price] [numeric](18, 2) NULL,\n" +
                    "  [income] [numeric](18, 2) NULL,\n" +
                    "  [task_user_name] [nvarchar](50) NULL,\n" +
                    "  [task_user_phone] [nvarchar](50) NULL,\n" +
                    "  [is_sync] [int] NULL,\n" +
                    "  [fromin_no] [nvarchar](50) NULL,\n" +
                    "  [address_id] [int] NULL,\n" +
                    "  [uc_id] [int] NULL,\n" +
                    "  [serviceFee] [numeric](18, 2) NULL,\n" +
                    "  [fromin_name] [nvarchar](100) NULL,\n" +
                    "  [isstoresprint] [int] NULL,\n" +
                    "  [receiver_lng] [numeric](30, 14) NULL,\n" +
                    "  [receiver_lat] [numeric](30, 14) NULL,\n" +
                    "  [send_name] [nvarchar](50) NULL,\n" +
                    "  [send_time] [datetime] NULL,\n" +
                    "  [print_time] [datetime] NULL,\n" +
                    "  [in_time] [datetime] NULL,\n" +
                    "  [updatetime] [datetime] NULL,\n" +
                    "  [task_order_name] [nvarchar](50) NULL,\n" +
                    "  [task_order_phone] [nvarchar](50) NULL,\n" +
                    "  [task_user_id] [int] NULL,\n" +
                    "  [pack_user_id] [int] NULL,\n" +
                    "  [pack_user_name] [nvarchar](50) NULL,\n" +
                    "  [pack_user_phone] [nvarchar](50) NULL,\n" +
                    "  [pack_user_time] [datetime] NULL,\n" +
                    "  [send_exception] [nvarchar](500) NULL,\n" +
                    "  [fromin_id] [nvarchar](30) NULL,\n" +
                    "  [service_time_str] [nvarchar](20) NULL,\n" +
                    "  [baidu_lng] [numeric](18, 14) NULL,\n" +
                    "  [baidu_lat] [numeric](18, 14) NULL,\n" +
                    "  [battch_isdo] [int] NULL,\n" +
                    "  [out_ship_fee] [numeric](18, 2) NULL,\n" +
                    "  [message_price] [numeric](18, 2) NULL,\n" +
                    "  [pack_exception_time] [datetime] NULL,\n" +
                    "  [send_exception_time] [datetime] NULL,\n" +
                    "  [service_exception_time] [datetime] NULL,\n" +
                    "  [shop_part] [numeric](18, 2) NULL,\n" +
                    "  [platform_part] [numeric](18, 2) NULL,\n" +
                    "  [goods] [nvarchar](4000) NULL,\n" +
                    "  [send_id] [nvarchar](32) NULL,\n" +
                    "  [cancel_type] [nvarchar](30) NULL,\n" +
                    "  [cancel_remark] [nvarchar](400) NULL,\n" +
                    "  [distance] [int] NULL,\n" +
                    "  [duration] [int] NULL,\n" +
                    "  [kilometre] [int] NULL,\n" +
                    "  [send_price] [numeric](18, 2) NULL,\n" +
                    "  [task_order_code] [nvarchar](30) NULL,\n" +
                    "  [task_user_code] [nvarchar](30) NULL,\n" +
                    "  [brand_id] [int] NULL,\n" +
                    "  CONSTRAINT [pk_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] PRIMARY KEY CLUSTERED\n" +
                    "    (\n" +
                    "      [order_id] ASC\n" +
                    "    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\n" +
                    ") ON [PRIMARY]\n" +
                    "\n" +
                    "ALTER TABLE [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_create_date_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([create_date]>='" + cur_month + "' AND [create_date]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_createtime_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [create_date] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_in_time_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [in_time] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_member_id_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [member_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_stores_id_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [stores_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_fromin_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [fromin] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_order_desc_cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [order_desc] ASC\n" +
                    ")\n" +
                    "GO\n\n\n\n";
            result.append(s);


            s = "--订单商品明细表\n" +
                    "CREATE TABLE [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [good_list_id] [nvarchar](32) NOT NULL,\n" +
                    "  [order_id] [nvarchar](32) NOT NULL,\n" +
                    "  [class_id] [int] NULL,\n" +
                    "  [good_id] [int] NULL,\n" +
                    "  [good_name] [varchar](100) NOT NULL,\n" +
                    "  [class_good_name] [nvarchar](100) NULL,\n" +
                    "  [standard_id] [int] NULL,\n" +
                    "  [standard_name] [nvarchar](50) NULL,\n" +
                    "  [sale_time] [datetime] NULL,\n" +
                    "  [count] [int] NULL,\n" +
                    "  [listorder] [int] NULL,\n" +
                    "  [pack_time] [datetime] NULL,\n" +
                    "  [stores_id] [int] NULL,\n" +
                    "  [price] [numeric](18, 2) NULL,\n" +
                    "  [cm_id] [int] NULL,\n" +
                    "  [brand_id] [int] NULL,\n" +
                    "  [create_date] [datetime] NOT NULL,\n" +
                    "  CONSTRAINT pk_goods_list_id_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " PRIMARY KEY NONCLUSTERED (good_list_id),\n" +
                    "  CONSTRAINT fk_ms_goods_list_order_id_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " FOREIGN KEY (order_id) REFERENCES cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "(order_id)\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "ALTER TABLE [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_create_date_cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([create_date]>='" + cur_month + "' AND [create_date]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_create_date_cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [create_date] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_order_id_cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [order_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_good_id_cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [good_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_stores_id_cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_ms_goods_list_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [stores_id] ASC\n" +
                    ")\n" +
                    "GO\n\n\n\n";
            result.append(s);


            s = "--订单操作日志\n" +
                    "CREATE TABLE [dbo].[cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [order_id] [nvarchar](32) NOT NULL,\n" +
                    "  [opt_type] [nvarchar](10) NULL,\n" +
                    "  [order_status] [int] NULL,\n" +
                    "  [order_status_chi] [nvarchar](30) NULL,\n" +
                    "  [opt_note] [nvarchar](200) NULL,\n" +
                    "  [opt_name] [nvarchar](50) NULL,\n" +
                    "  [opt_time] [datetime] NULL,\n" +
                    "  [opt_id] [int] NULL,\n" +
                    "  [ol_id] [varchar](32) NOT NULL,\n" +
                    "  [create_date] [datetime] NOT NULL,\n" +
                    "  CONSTRAINT pk_cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " PRIMARY KEY CLUSTERED (ol_id),\n" +
                    "  CONSTRAINT fk_cds_order_log_order_id_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " FOREIGN KEY (order_id) REFERENCES cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "(order_id)\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "ALTER TABLE [dbo].[cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_create_date_cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([create_date]>='" + cur_month + "' AND [create_date]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_create_date_cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [create_date] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_order_id_cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [order_id] ASC\n" +
                    ")\n" +
                    "GO\n\n\n\n";
            result.append(s);

            s = "--配送操作日志\n" +
                    "CREATE TABLE [dbo].[cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [send_id] [nvarchar](32) NOT NULL,\n" +
                    "  [order_id] [nvarchar](32) NOT NULL,\n" +
                    "  [send_type] [nvarchar](20) NULL,\n" +
                    "  [opt_type] [nvarchar](10) NULL,\n" +
                    "  [opt_note] [nvarchar](200) NULL,\n" +
                    "  [opt_name] [nvarchar](50) NULL,\n" +
                    "  [opt_time] [datetime] NULL,\n" +
                    "  [opt_id] [int] NULL,\n" +
                    "  [send_price] [decimal](10, 2) NULL,\n" +
                    "  [create_date] [datetime] NOT NULL,\n" +
                    "  CONSTRAINT pk_cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " PRIMARY KEY CLUSTERED (send_id),\n" +
                    "  CONSTRAINT fk_cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " FOREIGN KEY (order_id) REFERENCES cds_order_info_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "(order_id)\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "ALTER TABLE [dbo].[cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_create_date_cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([create_date]>='" + cur_month + "' AND [create_date]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_create_date_cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [create_date] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_order_id_cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_order_send_log_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [order_id] ASC\n" +
                    ")\n" +
                    "GO\n\n\n\n";
            result.append(s);

            s = "--调度下达记录\n" +
                    "CREATE TABLE [dbo].[cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [task_id] [nvarchar](32) NOT NULL,\n" +
                    "  [task_num] [int] NULL,\n" +
                    "  [meal_num] [int] NULL,\n" +
                    "  [stores_id] [int] NULL,\n" +
                    "  [good_id] [int] NULL,\n" +
                    "  [good_name] [varchar](64) NOT NULL,\n" +
                    "  [good_count] [int] NULL,\n" +
                    "  [finsh_count] [int] NULL,\n" +
                    "  [createtime] [datetime] NULL,\n" +
                    "  [task_finsh_time] [datetime] NULL,\n" +
                    "  [task_status] [int] NULL,\n" +
                    "  [es_id] [int] NULL,\n" +
                    "  [cm_id] [int] NULL,\n" +
                    "  CONSTRAINT pk_cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " PRIMARY KEY CLUSTERED (task_id)\n" +
                    ")\n" +
                    "GO\n" +
                    "ALTER TABLE [dbo].[cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_createtime_cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([createtime]>='" + cur_month + "' AND [createtime]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_good_id_cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [good_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "CREATE NONCLUSTERED INDEX [index_stores_id_cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [stores_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "CREATE NONCLUSTERED INDEX [index_createtime_cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [createtime] ASC\n" +
                    ")\n" +
                    "GO\n\n\n\n";
            result.append(s);

            s = "--调度下达操作记录\n" +
                    "CREATE TABLE [dbo].[cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "](\n" +
                    "  [tar_id] [nvarchar](32) NOT NULL,\n" +
                    "  [stores_id] [int] NULL,\n" +
                    "  [good_id] [int] NULL,\n" +
                    "  [good_name] [varchar](64) NOT NULL,\n" +
                    "  [good_count] [int] NULL,\n" +
                    "  [createtime] [datetime] NULL,\n" +
                    "  [gtr_status] [int] NULL,\n" +
                    "  [es_id] [int] NULL,\n" +
                    "  [cm_id] [int] NULL,\n" +
                    "  [task_id] [nvarchar](32) NULL,\n" +
                    "  [is_do] [int] NULL,\n" +
                    "  [before_good_count] [int] NULL,\n" +
                    "  [after_good_count] [int] NULL,\n" +
                    "  CONSTRAINT pk_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " PRIMARY KEY CLUSTERED(tar_id),\n" +
                    "  CONSTRAINT fk_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + " FOREIGN KEY (task_id) REFERENCES cds_goods_task_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "(task_id)\n" +
                    ")\n" +
                    "ALTER TABLE [dbo].[cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]  WITH CHECK ADD  CONSTRAINT [chk_createtime_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] CHECK  ([createtime]>='" + cur_month + "' AND [createtime]<'" + next_month + "')\n" +
                    "GO\n" +
                    "\n" +
                    "CREATE NONCLUSTERED INDEX [index_createtime_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [createtime] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "CREATE NONCLUSTERED INDEX [index_good_id_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [good_id] ASC\n" +
                    ")\n" +
                    "GO\n" +
                    "CREATE NONCLUSTERED INDEX [index_stores_id_cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "] ON [dbo].[cds_goods_task_record_" + year + "_" + StringUtil.dispRepairLeft("" + month, "0", 2) + "]\n" +
                    "(\n" +
                    "  [stores_id] ASC\n" +
                    ")\n" +
                    "GO";
            result.append(s);


        }
        System.out.println(result.toString());
    }

    public static void main2(String[] args) {
        for (int i = 1; i < 30; i++) {
            String msg = "" + i;
            if (i < 10) {
                msg = "0" + i;
            }

            String result = "SUM(case when sale_time='2017-06-" + msg + "' then count else 0 end ) as '2017-06-" + msg + "',";
            System.out.println(result);
        }

    }

    public static void main(String[] args) {
        String url = "https://app-api.shop.ele.me/arena/invoke/?method=LoginService.loginByUsername";
        String json = "{\"id\":\"dde828ff-099d-4ebf-b554-9b0d6b7edd1c\",\"method\":\"loginByUsername\",\"service\":\"LoginService\",\"params\"" +
                ":{\"username\":\"cdswhls\",\"password\":\"cdswhls1\",\"captchaCode\":\"\",\"loginedSessionIds\":[]},\"metas\":{\"appName\"" +
                ":\"melody\",\"appVersion\":\"4.4.0\"},\"ncp\":\"2.0.0\"}";
        try {
            httpPostWithJSON(url, json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private static final String APPLICATION_JSON = "application/json;";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
*/
    public static String httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        // String encoderJson = URLEncoder.encode(json);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
       // httpPost.addHeader("Accept", "*/*");
       // httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        //httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
       // httpPost.addHeader("Connection", "keep-alive");

        //httpPost.addHeader("X-Eleme-RequestID", "dde828ff-099d-4ebf-b554-9b0d6b7edd1c");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        httpPost.setEntity(se);
        /*Header[] headers = httpPost.getAllHeaders();
        System.out.println("请求头");
        for (Header header : headers) {
            System.out.println(header.getName() + ":" + header.getValue());
        }*/
        String respContent = null;


        HttpResponse resp = httpClient.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"utf-8");
            /*String cookie = resp.getLastHeader("Set-Cookie").getValue();
            System.out.println(cookie);*/
        }
        /*headers = resp.getAllHeaders();
        System.out.println("响应头");
        for (Header header : headers) {
            System.out.println(header.getName() + ":" + header.getValue());
        }*/

        System.out.println(respContent);

        JSONObject jo = JSONObject.parseObject(respContent);

        return respContent;
    }




}


