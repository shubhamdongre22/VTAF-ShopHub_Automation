//package com.ShopHubPages;
//import org.openqa.selenium.By;
//
//import com.generics.Pojo;
//
//public class CheckingContactUSPage {
//
//
//
//		private Pojo objPojo;
//		By contactUs = By.xpath("//a[text()='Contact Us']");
//
//
//		public CheckingContactUSPage(Pojo pojo) {
//			objPojo=pojo;
//		}
//
//		public void VerifyFooter(String footerTxt) {
//			String actualtxt=objPojo.getObjWrapperFunctions().getText(contactUs, "text");
//			if(footerTxt.equals(actualtxt)) {
//				objPojo.getObjUtilities().logReporter("Contact Us verified",true);
//			}else{
//				objPojo.getObjUtilities().logReporter("Contact Us Not verified",false);
//
//			}
//
//		}
//}
//
