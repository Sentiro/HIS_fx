package Data.Patient;

import Data.Others.Invoice;
import Data.Others.RLevel;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

//挂号表信息
public class Register implements Serializable {
   private LocalDateTime date;//挂号日期
   private String department;//科室
   private String doctor;
   private int price;

   private RLevel level;//专家号还是普通的
   private boolean m;//是否需要病历本
   private String chargeCategory;//现金还是银行卡
   private String invoice;
   private boolean status;

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public LocalDateTime getDate() {
      return date;
   }

   public void setDate(LocalDateTime date) {
      this.date = date;
   }

   public String getDepartment() {
      return department;
   }

   public void setDepartment(String department) {
      this.department=department;
   }

   public String getDoctor() {
      return doctor;
   }

   public void setDoctor(String doctor) {
      this.doctor = doctor;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }


   public RLevel getLevel() {
      return level;
   }

   public void setLevel(RLevel level) {
      this.level = level;
   }

   public boolean isM() {
      return m;
   }

   public void setM(boolean m) {
      this.m = m;
   }

   public String getChargeCategory() {
      return chargeCategory;
   }

   public void setChargeCategory(String chargeCategory) {
      this.chargeCategory = chargeCategory;
   }

   public String getInvoice() {
      return invoice;
   }

   public void setInvoice(String invoice) {
      this.invoice = invoice;
   }

   @Override
   public String toString() {
      return "Register{" +
              "date=" + date +
              ", department='" + department + '\'' +
              ", doctor='" + doctor + '\'' +
              ", price=" + price +
              ", level=" + level +
              ", m=" + m +
              ", chargeCategory='" + chargeCategory + '\'' +
              ", invoice=" + invoice +
              '}';
   }
}
